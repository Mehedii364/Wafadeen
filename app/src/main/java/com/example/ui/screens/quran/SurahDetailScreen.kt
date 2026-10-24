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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.repository.QuranRepository
import com.example.ui.theme.DeepEmerald
import com.example.ui.theme.QuranicArabicTextStyle
import com.example.ui.theme.RestrainedGold
import com.example.util.PrayerTimeCalculator
import kotlinx.coroutines.launch

@Composable
fun SurahDetailScreen(
    surahNumber: Int,
    bookmarkDao: BookmarkDao,
    onPlayAudio: (url: String, title: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val surah = remember(surahNumber) {
        QuranRepository.allSurahs.firstOrNull { it.number == surahNumber }
    }
    val allAyahs = remember(surahNumber) {
        QuranRepository.getAyahsForSurah(surahNumber)
    }

    var ayahSearchQuery by remember { mutableStateOf("") }
    var showPronunciation by remember { mutableStateOf(true) }
    var showMeaning by remember { mutableStateOf(true) }
    var showEnglish by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    val surahBookmarkId = "surah_$surahNumber"
    val isSurahBookmarked by bookmarkDao.isBookmarked(surahBookmarkId).collectAsState(initial = false)

    val displayedAyahs = remember(allAyahs, ayahSearchQuery) {
        val q = ayahSearchQuery.trim().lowercase()
        if (q.isEmpty()) {
            allAyahs
        } else {
            allAyahs.filter {
                it.translationBn.lowercase().contains(q) ||
                it.transliterationBn.lowercase().contains(q) ||
                it.arabicText.contains(ayahSearchQuery.trim()) ||
                it.translationEn.lowercase().contains(q) ||
                it.ayahNumber.toString() == q
            }
        }
    }

    if (surah == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("সূরা পাওয়া যায়নি")
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("surah_detail_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Surah Header Banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DeepEmerald),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, RestrainedGold.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Surah Number & Bookmark Action Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(RestrainedGold.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "সূরা নং ${PrayerTimeCalculator.toBengaliDigits(surah.number)}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = RestrainedGold
                            )
                        }

                        // Bookmark Surah Button
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (isSurahBookmarked) {
                                        bookmarkDao.deleteBookmarkById(surahBookmarkId)
                                        Toast.makeText(context, "সূরাটি বুকমার্ক তালিকা হতে মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
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
                                        Toast.makeText(context, "সম্পূর্ণ সূরা বুকমার্ক করা হয়েছে", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("bookmark_surah_button")
                        ) {
                            Icon(
                                imageVector = if (isSurahBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = if (isSurahBookmarked) "বুকমার্ক সরানো" else "সূরা বুকমার্ক করুন",
                                tint = if (isSurahBookmarked) RestrainedGold else Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = surah.nameAr,
                        style = QuranicArabicTextStyle.copy(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = RestrainedGold
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "সূরা ${surah.nameBn} (${surah.nameEn})",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    val revTypeBn = if (surah.revelationType == RevelationType.MECCAN) "মাক্কী সূরা" else "মাদানী সূরা"
                    Text(
                        text = "$revTypeBn • ${PrayerTimeCalculator.toBengaliDigits(surah.totalAyahs)} আয়াত • অর্থ: ${surah.meaningBn}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    if (surah.audioUrl != null) {
                        Button(
                            onClick = {
                                onPlayAudio(surah.audioUrl, "সূরা ${surah.nameBn} (${surah.nameAr})")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = RestrainedGold),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("play_full_surah_audio")
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = DeepEmerald
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "সম্পূর্ণ সূরা অডিও তেলাওয়াত শুনুন",
                                color = DeepEmerald,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // In-Surah Ayah Search Field
        if (allAyahs.isNotEmpty()) {
            item {
                OutlinedTextField(
                    value = ayahSearchQuery,
                    onValueChange = { ayahSearchQuery = it },
                    placeholder = { Text("এই সূরায় খুঁজুন (আরবি, উচ্চারণ বা অর্থ)...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "খুঁজুন",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    trailingIcon = {
                        if (ayahSearchQuery.isNotEmpty()) {
                            IconButton(onClick = { ayahSearchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "মুছুন",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("surah_ayah_search_field")
                )
            }
        }

        // Display Options Filter Bar (বাংলা উচ্চারণ, বাংলা অর্থ, ইংরেজি)
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "পঠন ও অনুবাদ নিয়ন্ত্রণ:",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = RestrainedGold
                        )
                        if (allAyahs.isNotEmpty()) {
                            Text(
                                text = "প্রদর্শিত: ${PrayerTimeCalculator.toBengaliDigits(displayedAyahs.size)}টি আয়াত",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        item {
                            FilterChip(
                                selected = showPronunciation,
                                onClick = { showPronunciation = !showPronunciation },
                                label = { Text("বাংলা উচ্চারণ") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = RestrainedGold.copy(alpha = 0.2f),
                                    selectedLabelColor = RestrainedGold
                                )
                            )
                        }
                        item {
                            FilterChip(
                                selected = showMeaning,
                                onClick = { showMeaning = !showMeaning },
                                label = { Text("বাংলা অর্থ") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                        item {
                            FilterChip(
                                selected = showEnglish,
                                onClick = { showEnglish = !showEnglish },
                                label = { Text("English Translation") }
                            )
                        }
                    }
                }
            }
        }

        // Bismillah Header (except for Surah At-Tawbah #9)
        if (surahNumber != 9) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                        style = QuranicArabicTextStyle.copy(
                            fontSize = 24.sp,
                            color = RestrainedGold,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }

        if (allAyahs.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "সূরাটির পূর্ণ অডিও তেলাওয়াত উপলব্ধ",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "এই সূরার আয়াতভিত্তিক টেক্সট ও অনুবাদ বর্তমানে সংযোজন প্রক্রিয়াধীন। আপনি উপরের 'সম্পূর্ণ সূরা অডিও তেলাওয়াত শুনুন' বাটনে চাপ দিয়ে শেখ মিশারি রশিদের কণ্ঠে বিশুদ্ধ তেলাওয়াত উপভোগ করতে পারেন।",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else if (displayedAyahs.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "\"$ayahSearchQuery\" দিয়ে কোনো আয়াত খুঁজে পাওয়া যায়নি।",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(displayedAyahs, key = { it.id }) { ayah ->
                val ayahBookmarkId = "quran_${ayah.id}"
                val isBookmarked by bookmarkDao.isBookmarked(ayahBookmarkId)
                    .collectAsState(initial = false)

                AyahCard(
                    ayah = ayah,
                    showPronunciation = showPronunciation,
                    showMeaning = showMeaning,
                    showEnglish = showEnglish,
                    isBookmarked = isBookmarked,
                    onBookmarkToggle = {
                        scope.launch {
                            if (isBookmarked) {
                                bookmarkDao.deleteBookmarkById(ayahBookmarkId)
                                Toast.makeText(context, "আয়াতটি বুকমার্ক হতে সরানো হয়েছে", Toast.LENGTH_SHORT).show()
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
                    onPlayClick = {
                        ayah.audioUrl?.let { url ->
                            onPlayAudio(url, "সূরা ${surah.nameBn} - আয়াত ${ayah.ayahNumber}")
                        }
                    },
                    onCopyClick = {
                        clipboardManager.setText(
                            AnnotatedString("${ayah.arabicText}\n\n[বাংলা উচ্চারণ]\n${ayah.transliterationBn}\n\n[বাংলা অর্থ]\n${ayah.translationBn}\n\n— সূরা ${surah.nameBn}: ${ayah.ayahNumber}")
                        )
                        Toast.makeText(context, "আয়াত ও অর্থ কপি করা হয়েছে", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
fun AyahCard(
    ayah: Ayah,
    showPronunciation: Boolean,
    showMeaning: Boolean,
    showEnglish: Boolean,
    isBookmarked: Boolean,
    onBookmarkToggle: () -> Unit,
    onPlayClick: () -> Unit,
    onCopyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                RoundedCornerShape(16.dp)
            )
            .testTag("ayah_card_${ayah.ayahNumber}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Ayah Header & Action Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
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

                Row {
                    if (ayah.audioUrl != null) {
                        IconButton(onClick = onPlayClick, modifier = Modifier.size(36.dp)) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "আয়াত তেলাওয়াত শুনুন",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    IconButton(onClick = onBookmarkToggle, modifier = Modifier.size(36.dp)) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = if (isBookmarked) "বুকমার্ক সরান" else "আয়াত বুকমার্ক করুন",
                            tint = if (isBookmarked) RestrainedGold else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(onClick = onCopyClick, modifier = Modifier.size(36.dp)) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "কপি করুন",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Arabic Text
            Text(
                text = ayah.arabicText,
                style = QuranicArabicTextStyle.copy(
                    fontSize = 22.sp,
                    lineHeight = 40.sp,
                    textAlign = TextAlign.End,
                    textDirection = TextDirection.Rtl
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            // Bengali Transliteration (বাংলা উচ্চারণ)
            if (showPronunciation && ayah.transliterationBn.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(RestrainedGold.copy(alpha = 0.08f), RoundedCornerShape(8.dp))
                        .border(1.dp, RestrainedGold.copy(alpha = 0.25f), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Text(
                            text = "বাংলা উচ্চারণ:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = RestrainedGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = ayah.transliterationBn,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                lineHeight = 22.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Bengali Translation (বাংলা অর্থ)
            if (showMeaning && ayah.translationBn.isNotBlank()) {
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
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = ayah.translationBn,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 22.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // English Translation
            if (showEnglish && ayah.translationEn.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Meaning: ${ayah.translationEn}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}
