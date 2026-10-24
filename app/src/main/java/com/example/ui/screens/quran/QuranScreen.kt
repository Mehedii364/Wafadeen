package com.example.ui.screens.quran

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.BookmarkDao
import com.example.data.local.BookmarkEntity
import com.example.data.model.Ayah
import com.example.data.model.RevelationType
import com.example.data.model.Surah
import com.example.repository.QuranRepository
import com.example.ui.theme.DeepEmerald
import com.example.ui.theme.QuranicArabicTextStyle
import com.example.ui.theme.RestrainedGold
import com.example.util.PrayerTimeCalculator
import kotlinx.coroutines.launch

@Composable
fun QuranScreen(
    bookmarkDao: BookmarkDao,
    onSurahClick: (Int) -> Unit,
    onPlaySurahAudio: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("সূরা সূচি (১১৪)", "আয়াত অনুসন্ধান ও পঠন", "সংরক্ষিত বিষয়")

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("quran_screen")
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = MaterialTheme.colorScheme.primary,
                    height = 3.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        )
                    }
                )
            }
        }

        when (selectedTab) {
            0 -> SurahListTab(
                bookmarkDao = bookmarkDao,
                onSurahClick = onSurahClick,
                onPlaySurahAudio = onPlaySurahAudio
            )
            1 -> AyahSearchAndReadTab(
                bookmarkDao = bookmarkDao,
                onSurahClick = onSurahClick,
                onPlayAudio = onPlaySurahAudio
            )
            2 -> BookmarkedQuranTab(
                bookmarkDao = bookmarkDao,
                onSurahClick = onSurahClick,
                onPlayAudio = onPlaySurahAudio
            )
        }
    }
}

@Composable
fun SurahListTab(
    bookmarkDao: BookmarkDao,
    onSurahClick: (Int) -> Unit,
    onPlaySurahAudio: (String, String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ALL") } // ALL, MECCAN, MEDINAN, BOOKMARKED

    val allBookmarks by bookmarkDao.getBookmarksByType("QURAN").collectAsState(initial = emptyList())
    val bookmarkedSurahIds = remember(allBookmarks) {
        allBookmarks.filter { it.id.startsWith("surah_") }.map { it.id }.toSet()
    }

    val filteredSurahs = remember(searchQuery, selectedFilter, bookmarkedSurahIds) {
        var list = QuranRepository.allSurahs

        // Apply Revelation / Bookmark filter
        list = when (selectedFilter) {
            "MECCAN" -> list.filter { it.revelationType == RevelationType.MECCAN }
            "MEDINAN" -> list.filter { it.revelationType == RevelationType.MEDINAN }
            "BOOKMARKED" -> list.filter { "surah_${it.number}" in bookmarkedSurahIds }
            else -> list
        }

        // Apply Text Query
        val q = searchQuery.trim().lowercase()
        if (q.isNotEmpty()) {
            list = list.filter {
                it.nameBn.lowercase().contains(q) ||
                it.nameEn.lowercase().contains(q) ||
                it.nameAr.contains(searchQuery.trim()) ||
                it.meaningBn.lowercase().contains(q) ||
                it.number.toString() == q ||
                PrayerTimeCalculator.toBengaliDigits(it.number) == q
            }
        }
        list
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("সূরার নাম, অর্থ বা নম্বর দিয়ে খুঁজুন…") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "খুঁজুন",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "মুছুন",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("quran_search_field")
        )

        // Filter chips (All, Meccan, Medinan, Bookmarked)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedFilter == "ALL",
                    onClick = { selectedFilter = "ALL" },
                    label = { Text("সকল সূরা (১১৪)") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
            item {
                FilterChip(
                    selected = selectedFilter == "MECCAN",
                    onClick = { selectedFilter = "MECCAN" },
                    label = { Text("মাক্কী (৮৬)") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = RestrainedGold.copy(alpha = 0.25f),
                        selectedLabelColor = RestrainedGold
                    )
                )
            }
            item {
                FilterChip(
                    selected = selectedFilter == "MEDINAN",
                    onClick = { selectedFilter = "MEDINAN" },
                    label = { Text("মাদানী (২৮)") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = DeepEmerald.copy(alpha = 0.2f),
                        selectedLabelColor = DeepEmerald
                    )
                )
            }
            item {
                FilterChip(
                    selected = selectedFilter == "BOOKMARKED",
                    onClick = { selectedFilter = "BOOKMARKED" },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    label = { Text("বুকমার্ককৃত (${bookmarkedSurahIds.size})") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = RestrainedGold,
                        selectedLabelColor = DeepEmerald
                    )
                )
            }
        }

        // Result count info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "প্রদর্শিত সূরা: ${PrayerTimeCalculator.toBengaliDigits(filteredSurahs.size)}টি",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "তেলাওয়াতকারী: শায়খ মিশারি রশিদ",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = RestrainedGold
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredSurahs, key = { it.number }) { surah ->
                val surahBookmarkId = "surah_${surah.number}"
                val isBookmarked = surahBookmarkId in bookmarkedSurahIds
                val scope = rememberCoroutineScope()
                val context = LocalContext.current

                SurahListItemCard(
                    surah = surah,
                    isBookmarked = isBookmarked,
                    onBookmarkToggle = {
                        scope.launch {
                            if (isBookmarked) {
                                bookmarkDao.deleteBookmarkById(surahBookmarkId)
                                Toast.makeText(context, "সূরা ${surah.nameBn} বুকমার্ক সরানো হয়েছে", Toast.LENGTH_SHORT).show()
                            } else {
                                bookmarkDao.insertBookmark(
                                    BookmarkEntity(
                                        id = surahBookmarkId,
                                        contentType = "QURAN",
                                        contentId = "surah_${surah.number}",
                                        title = "সূরা ${surah.nameBn} (${surah.nameAr})",
                                        arabicText = surah.nameAr,
                                        translation = "অর্থ: ${surah.meaningBn} • ${PrayerTimeCalculator.toBengaliDigits(surah.totalAyahs)} আয়াত • ${if (surah.revelationType == RevelationType.MECCAN) "মাক্কী" else "মাদানী"}",
                                        reference = "সূরা নম্বর ${surah.number}"
                                    )
                                )
                                Toast.makeText(context, "সূরা ${surah.nameBn} বুকমার্ক করা হয়েছে", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    onClick = { onSurahClick(surah.number) },
                    onPlayClick = {
                        surah.audioUrl?.let { url ->
                            onPlaySurahAudio(url, "সূরা ${surah.nameBn} (${surah.nameAr})")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SurahListItemCard(
    surah: Surah,
    isBookmarked: Boolean,
    onBookmarkToggle: () -> Unit,
    onClick: () -> Unit,
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (isBookmarked) RestrainedGold.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                RoundedCornerShape(14.dp)
            )
            .testTag("surah_item_${surah.number}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Surah Number Badge
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isBookmarked) RestrainedGold.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = PrayerTimeCalculator.toBengaliDigits(surah.number),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (isBookmarked) RestrainedGold else MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = surah.nameBn,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "(${surah.nameEn})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val revTypeBn = if (surah.revelationType == RevelationType.MECCAN) "মাক্কী" else "মাদানী"
                    Text(
                        text = "$revTypeBn • ${PrayerTimeCalculator.toBengaliDigits(surah.totalAyahs)} আয়াত • ${surah.meaningBn}",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Arabic Surah Name & Actions
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = surah.nameAr,
                    style = QuranicArabicTextStyle.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = RestrainedGold,
                    textAlign = TextAlign.End
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBookmarkToggle,
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("bookmark_surah_${surah.number}")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "বুকমার্ক",
                            tint = if (isBookmarked) RestrainedGold else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    if (surah.audioUrl != null) {
                        IconButton(
                            onClick = onPlayClick,
                            modifier = Modifier
                                .size(32.dp)
                                .testTag("play_surah_${surah.number}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "তেলাওয়াত শুনুন",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AyahSearchAndReadTab(
    bookmarkDao: BookmarkDao,
    onSurahClick: (Int) -> Unit,
    onPlayAudio: (String, String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val suggestedKeywords = listOf("আল্লাহ", "সালাত", "রহমত", "জান্নাত", "ধৈর্য", "তওবা", "আখেরাত", "মাতা-পিতা", "মৃত্যু")

    val searchResults = remember(searchQuery) {
        val q = searchQuery.trim()
        if (q.isEmpty()) {
            QuranRepository.getAllAvailableAyahs()
        } else {
            QuranRepository.searchAyahs(q)
        }
    }

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar for verses
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("আয়াত খুঁজুন (যেমন: আল্লাহ, সালাত, রহমত)...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "খুঁজুন",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "মুছুন",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("ayah_search_field")
        )

        // Quick Search Keyword Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(suggestedKeywords) { keyword ->
                FilterChip(
                    selected = searchQuery == keyword,
                    onClick = {
                        searchQuery = if (searchQuery == keyword) "" else keyword
                    },
                    label = { Text(keyword) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        // Header info
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (searchQuery.isEmpty()) "গুরুত্বপূর্ণ ও নিত্যপঠিত আয়াতসমূহ" else "অনুসন্ধানের ফলাফল: ${PrayerTimeCalculator.toBengaliDigits(searchResults.size)}টি আয়াত",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = RestrainedGold
            )
            Text(
                text = "আরবি ও বাংলা অর্থ সহ",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (searchResults.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "\"$searchQuery\" সম্পর্কিত কোনো আয়াত পাওয়া যায়নি",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "অন্য কোনো বাংলা শব্দ, আরবি অক্ষর বা সূরার নাম লিখে অনুসন্ধান করুন।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(searchResults, key = { "${it.first.number}_${it.second.ayahNumber}" }) { (surah, ayah) ->
                    val ayahBookmarkId = "quran_${ayah.id}"
                    val isBookmarked by bookmarkDao.isBookmarked(ayahBookmarkId).collectAsState(initial = false)

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                if (isBookmarked) RestrainedGold.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                                RoundedCornerShape(16.dp)
                            )
                            .testTag("search_ayah_card_${ayah.id}")
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Header with Surah title & Actions
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onSurahClick(surah.number) }
                                        .padding(vertical = 2.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primaryContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = PrayerTimeCalculator.toBengaliDigits(ayah.ayahNumber),
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = "সূরা ${surah.nameBn} (${surah.nameAr})",
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "আয়াত ${PrayerTimeCalculator.toBengaliDigits(ayah.ayahNumber)} • ${surah.nameEn}",
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Row {
                                    if (ayah.audioUrl != null) {
                                        IconButton(
                                            onClick = {
                                                onPlayAudio(ayah.audioUrl, "সূরা ${surah.nameBn} - আয়াত ${ayah.ayahNumber}")
                                            },
                                            modifier = Modifier.size(34.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.PlayArrow,
                                                contentDescription = "তেলাওয়াত",
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }

                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                if (isBookmarked) {
                                                    bookmarkDao.deleteBookmarkById(ayahBookmarkId)
                                                    Toast.makeText(context, "বুকমার্ক সরানো হয়েছে", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    bookmarkDao.insertBookmark(
                                                        BookmarkEntity(
                                                            id = ayahBookmarkId,
                                                            contentType = "QURAN",
                                                            contentId = "${ayah.surahNumber}:${ayah.ayahNumber}",
                                                            title = "সূরা ${surah.nameBn} - আয়াত ${PrayerTimeCalculator.toBengaliDigits(ayah.ayahNumber)}",
                                                            arabicText = ayah.arabicText,
                                                            translation = ayah.translationBn,
                                                            reference = "সূরা ${surah.nameBn} (${ayah.surahNumber}:${ayah.ayahNumber})"
                                                        )
                                                    )
                                                    Toast.makeText(context, "আয়াতটি বুকমার্ক করা হয়েছে", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        },
                                        modifier = Modifier.size(34.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                            contentDescription = "বুকমার্ক",
                                            tint = if (isBookmarked) RestrainedGold else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            clipboardManager.setText(
                                                AnnotatedString("${ayah.arabicText}\n\n[উচ্চারণ]\n${ayah.transliterationBn}\n\n[বাংলা অর্থ]\n${ayah.translationBn}\n\n— সূরা ${surah.nameBn}: ${ayah.ayahNumber}")
                                            )
                                            Toast.makeText(context, "কপি করা হয়েছে", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(34.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "কপি",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Arabic Uthmani Text
                            Text(
                                text = ayah.arabicText,
                                style = QuranicArabicTextStyle.copy(
                                    fontSize = 22.sp,
                                    lineHeight = 38.sp,
                                    textAlign = TextAlign.End,
                                    textDirection = TextDirection.Rtl
                                ),
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.fillMaxWidth()
                            )

                            // Bengali Transliteration (উচ্চারণ)
                            if (ayah.transliterationBn.isNotBlank()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(RestrainedGold.copy(alpha = 0.08f), RoundedCornerShape(8.dp))
                                        .border(1.dp, RestrainedGold.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                        .padding(10.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "উচ্চারণ:",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = RestrainedGold,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp
                                            )
                                        )
                                        Text(
                                            text = ayah.transliterationBn,
                                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }

                            // Bengali Translation (বাংলা অর্থ)
                            if (ayah.translationBn.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f), RoundedCornerShape(8.dp))
                                        .padding(10.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "বাংলা অর্থ:",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp
                                            )
                                        )
                                        Text(
                                            text = ayah.translationBn,
                                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Jump to Surah Button
                            OutlinedButton(
                                onClick = { onSurahClick(surah.number) },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuBook,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "সম্পূর্ণ সূরা পড়ুন",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookmarkedQuranTab(
    bookmarkDao: BookmarkDao,
    onSurahClick: (Int) -> Unit,
    onPlayAudio: (String, String) -> Unit
) {
    val quranBookmarks by bookmarkDao.getBookmarksByType("QURAN").collectAsState(initial = emptyList())
    var filterType by remember { mutableStateOf("ALL") } // ALL, SURAHS, AYAHS

    val displayedBookmarks = remember(quranBookmarks, filterType) {
        when (filterType) {
            "SURAHS" -> quranBookmarks.filter { it.id.startsWith("surah_") }
            "AYAHS" -> quranBookmarks.filter { it.id.startsWith("quran_") }
            else -> quranBookmarks
        }
    }

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = filterType == "ALL",
                    onClick = { filterType = "ALL" },
                    label = { Text("সকল সংরক্ষিত (${quranBookmarks.size})") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
            item {
                FilterChip(
                    selected = filterType == "SURAHS",
                    onClick = { filterType = "SURAHS" },
                    label = { Text("সংরক্ষিত সূরা (${quranBookmarks.count { it.id.startsWith("surah_") }})") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = RestrainedGold,
                        selectedLabelColor = DeepEmerald
                    )
                )
            }
            item {
                FilterChip(
                    selected = filterType == "AYAHS",
                    onClick = { filterType = "AYAHS" },
                    label = { Text("সংরক্ষিত আয়াত (${quranBookmarks.count { it.id.startsWith("quran_") }})") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        if (displayedBookmarks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "কোনো সংরক্ষিত সূরা বা আয়াত নেই",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "সূরা তালিকা বা আয়াত পাঠের সময় বুকমার্ক আইকনে ট্যাপ করে আপনার প্রিয় সূরা ও আয়াতসমূহ সংরক্ষণ করুন।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(displayedBookmarks, key = { it.id }) { bookmark ->
                    val isSurahBookmark = bookmark.id.startsWith("surah_")
                    val surahNumber = if (isSurahBookmark) {
                        bookmark.contentId.replace("surah_", "").toIntOrNull() ?: 1
                    } else {
                        bookmark.contentId.split(":").firstOrNull()?.toIntOrNull() ?: 1
                    }

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                RestrainedGold.copy(alpha = 0.35f),
                                RoundedCornerShape(14.dp)
                            )
                            .testTag("quran_bookmark_${bookmark.id}")
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(if (isSurahBookmark) RestrainedGold.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primaryContainer)
                                            .padding(horizontal = 6.dp, vertical = 3.dp)
                                    ) {
                                        Text(
                                            text = if (isSurahBookmark) "সূরা" else "আয়াত",
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = if (isSurahBookmark) RestrainedGold else MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = bookmark.title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                Row {
                                    IconButton(
                                        onClick = {
                                            val text = "${bookmark.title}\n\n${bookmark.arabicText ?: ""}\n\n${bookmark.translation ?: ""}"
                                            clipboardManager.setText(AnnotatedString(text))
                                            Toast.makeText(context, "কপি করা হয়েছে", Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "কপি",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                bookmarkDao.deleteBookmark(bookmark)
                                                Toast.makeText(context, "বুকমার্ক মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
                                            }
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Bookmark,
                                            contentDescription = "মুছুন",
                                            tint = RestrainedGold,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }

                            if (!bookmark.arabicText.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = bookmark.arabicText,
                                    style = QuranicArabicTextStyle.copy(
                                        fontSize = 20.sp,
                                        lineHeight = 36.sp,
                                        textAlign = TextAlign.End,
                                        textDirection = TextDirection.Rtl
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            if (!bookmark.translation.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = bookmark.translation,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = bookmark.reference ?: "",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                OutlinedButton(
                                    onClick = { onSurahClick(surahNumber) },
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MenuBook,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isSurahBookmark) "সূরাটি পড়ুন" else "সূরায় যান",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
