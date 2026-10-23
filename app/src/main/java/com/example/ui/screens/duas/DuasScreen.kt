package com.example.ui.screens.duas

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.data.model.Dua
import com.example.data.model.DuaCategory
import com.example.data.model.DuaType
import com.example.repository.DuaRepository
import com.example.ui.theme.DuaArabicTextStyle
import com.example.ui.theme.RestrainedGold
import kotlinx.coroutines.launch

@Composable
fun DuasScreen(
    bookmarkDao: BookmarkDao,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf<DuaCategory?>(null) }

    val categories = listOf(
        null to "সকল দোয়া",
        DuaCategory.ISTIGHFAR to "তওবা ও ইস্তিগফার",
        DuaCategory.MORNING_EVENING to "সকাল-সন্ধ্যার জিকির",
        DuaCategory.AFTER_SALAH to "নামাজের পরের আমল",
        DuaCategory.QURANIC_RABBANA to "কুরআনি রব্বানা দোয়া",
        DuaCategory.DAILY_LIFE to "দৈনন্দিন জীবনের দোয়া"
    )

    val filteredDuas = remember(selectedCategory) {
        if (selectedCategory == null) DuaRepository.duas else DuaRepository.getDuasByCategory(selectedCategory!!)
    }

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("duas_screen")
    ) {
        // Category Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { (cat, title) ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = cat },
                    label = { Text(title) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredDuas, key = { it.id }) { dua ->
                val isBookmarked by bookmarkDao.isBookmarked("dua_${dua.id}")
                    .collectAsState(initial = false)

                DuaItemCard(
                    dua = dua,
                    isBookmarked = isBookmarked,
                    onBookmarkToggle = {
                        scope.launch {
                            val bookmarkId = "dua_${dua.id}"
                            if (isBookmarked) {
                                bookmarkDao.deleteBookmarkById(bookmarkId)
                                Toast.makeText(context, "বুকমার্ক থেকে সরানো হয়েছে", Toast.LENGTH_SHORT).show()
                            } else {
                                bookmarkDao.insertBookmark(
                                    BookmarkEntity(
                                        id = bookmarkId,
                                        contentType = "DUA",
                                        contentId = dua.id,
                                        title = dua.titleBn,
                                        arabicText = dua.arabicText,
                                        translation = dua.meaningBn,
                                        reference = dua.reference
                                    )
                                )
                                Toast.makeText(context, "সংরক্ষণ করা হয়েছে", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    onCopyClick = {
                        clipboardManager.setText(
                            AnnotatedString("${dua.titleBn}\n\n${dua.arabicText}\n\nউচ্চারণ: ${dua.transliterationBn}\n\nঅর্থ: ${dua.meaningBn}\n\nসূত্র: ${dua.reference}")
                        )
                        Toast.makeText(context, "কপি করা হয়েছে", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
fun DuaItemCard(
    dua: Dua,
    isBookmarked: Boolean,
    onBookmarkToggle: () -> Unit,
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
            .testTag("dua_card_${dua.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dua.titleBn,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                val typeBadge = when (dua.type) {
                    DuaType.QURANIC -> "কুরআনি দোয়া"
                    DuaType.PROPHETIC_SUNNAH -> "সহিহ সুন্নাহ"
                    DuaType.PERMISSIBLE_SUPPLICATION -> "মুস্তাহাব মুনাজাত"
                }
                Box(
                    modifier = Modifier
                        .background(RestrainedGold.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = typeBadge,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                        color = RestrainedGold,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Arabic text
            Text(
                text = dua.arabicText,
                style = DuaArabicTextStyle.copy(
                    fontSize = 20.sp,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.End,
                    textDirection = TextDirection.Rtl
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Transliteration (বাংলা উচ্চারণ)
            if (dua.transliterationBn.isNotBlank()) {
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
                            text = dua.transliterationBn,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                lineHeight = 22.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Meaning (বাংলা অর্থ)
            if (dua.meaningBn.isNotBlank()) {
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
                            text = dua.meaningBn,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 22.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            if (dua.contextBn.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "ফজিলত: ${dua.contextBn}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "সূত্র: ${dua.reference}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Row {
                    IconButton(onClick = onBookmarkToggle, modifier = Modifier.size(36.dp)) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "বুকমার্ক",
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
        }
    }
}
