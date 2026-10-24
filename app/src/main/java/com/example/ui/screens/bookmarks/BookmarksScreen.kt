package com.example.ui.screens.bookmarks

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
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
import com.example.ui.theme.DuaArabicTextStyle
import com.example.ui.theme.RestrainedGold
import kotlinx.coroutines.launch

@Composable
fun BookmarksScreen(
    bookmarkDao: BookmarkDao,
    modifier: Modifier = Modifier
) {
    val allBookmarks by bookmarkDao.getAllBookmarks().collectAsState(initial = emptyList())
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    val filterOptions = listOf(
        null to "সকল",
        "QURAN" to "কুরআন",
        "HADITH" to "হাদিস",
        "DUA" to "দোয়া"
    )

    val displayedBookmarks = remember(allBookmarks, selectedFilter) {
        if (selectedFilter == null) allBookmarks else allBookmarks.filter { it.contentType == selectedFilter }
    }

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("bookmarks_screen")
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filterOptions) { (type, label) ->
                FilterChip(
                    selected = selectedFilter == type,
                    onClick = { selectedFilter = type },
                    label = { Text(label) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
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
                        text = "কোনো সংরক্ষিত বিষয় নেই",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "কুরআন, হাদিস বা দোয়ার পাশে থাকা বুকমার্ক আইকনে চাপ দিয়ে প্রিয় আমলসমূহ সংরক্ষণ করুন।",
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
                    BookmarkItemCard(
                        bookmark = bookmark,
                        onDelete = {
                            scope.launch {
                                bookmarkDao.deleteBookmark(bookmark)
                                Toast.makeText(context, "মুছে ফেলা হয়েছে", Toast.LENGTH_SHORT).show()
                            }
                        },
                        onCopy = {
                            val text = "${bookmark.title}\n\n${bookmark.arabicText ?: ""}\n\n${bookmark.translation ?: ""}\n\n[${bookmark.reference ?: ""}]"
                            clipboardManager.setText(AnnotatedString(text))
                            Toast.makeText(context, "কপি করা হয়েছে", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BookmarkItemCard(
    bookmark: BookmarkEntity,
    onDelete: () -> Unit,
    onCopy: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                RoundedCornerShape(14.dp)
            )
            .testTag("bookmark_card_${bookmark.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val typeName = when {
                    bookmark.id.startsWith("surah_") -> "পবিত্র কুরআন (সূরা)"
                    bookmark.contentType == "QURAN" -> "কুরআনের আয়াত"
                    bookmark.contentType == "HADITH" -> "সহিহ হাদিস"
                    bookmark.contentType == "DUA" -> "মাসনূন দোয়া"
                    else -> "সংরক্ষিত আমল"
                }

                Text(
                    text = "$typeName • ${bookmark.title}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = RestrainedGold,
                    modifier = Modifier.weight(1f)
                )

                Row {
                    IconButton(onClick = onCopy, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "কপি",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.BookmarkRemove,
                            contentDescription = "মুছুন",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            if (!bookmark.arabicText.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = bookmark.arabicText,
                    style = DuaArabicTextStyle.copy(
                        fontSize = 18.sp,
                        lineHeight = 32.sp,
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

            if (!bookmark.reference.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "সূত্র: ${bookmark.reference}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
