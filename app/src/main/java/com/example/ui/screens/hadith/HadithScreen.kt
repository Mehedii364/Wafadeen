package com.example.ui.screens.hadith

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.BookmarkDao
import com.example.data.local.BookmarkEntity
import com.example.data.model.Hadith
import com.example.repository.HadithRepository
import com.example.ui.components.VerificationBadge
import com.example.ui.theme.DuaArabicTextStyle
import com.example.ui.theme.RestrainedGold
import kotlinx.coroutines.launch

@Composable
fun HadithScreen(
    bookmarkDao: BookmarkDao,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTopic by remember { mutableStateOf("সকল") }
    var verificationDialogHadith by remember { mutableStateOf<Hadith?>(null) }
    var showDailyHadithCard by remember { mutableStateOf(true) }

    val hadiths = remember(searchQuery, selectedTopic) {
        HadithRepository.searchHadiths(searchQuery, selectedTopic)
    }

    val dailyHadith = remember {
        try {
            HadithRepository.getDailyHadith()
        } catch (_: Exception) {
            null
        }
    }

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("hadith_screen")
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("হাদিস নম্বর, বিষয়, বর্ণনাকারী বা শব্দ দিয়ে খুঁজুন…", fontSize = 13.sp) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
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
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .testTag("hadith_search_field")
        )

        // Topic Filter Chips (Horizontal Scroll)
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HadithRepository.allTopics.forEach { topic ->
                val isSelected = topic == selectedTopic
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedTopic = topic },
                    label = {
                        Text(
                            text = topic,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.testTag("hadith_topic_chip_$topic")
                )
            }
        }

        // Daily Hadith Hero Card (Collapsible)
        if (dailyHadith != null && showDailyHadithCard && searchQuery.isBlank() && selectedTopic == "সকল") {
            DailyHadithHero(
                hadith = dailyHadith,
                onDismiss = { showDailyHadithCard = false },
                onViewDetails = { verificationDialogHadith = dailyHadith }
            )
        }

        // Header info: Total Count & Active Filter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                    contentDescription = null,
                    tint = RestrainedGold,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (selectedTopic == "সকল") "সহিহ বুখারী সংকলন" else selectedTopic,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = "${hadiths.size}টি সহিহ হাদিস",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = RestrainedGold
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(hadiths, key = { it.id }) { hadith ->
                val isBookmarked by bookmarkDao.isBookmarked("hadith_${hadith.id}")
                    .collectAsState(initial = false)

                HadithCard(
                    hadith = hadith,
                    isBookmarked = isBookmarked,
                    onBookmarkToggle = {
                        scope.launch {
                          val bookmarkId = "hadith_${hadith.id}"
                          if (isBookmarked) {
                              bookmarkDao.deleteBookmarkById(bookmarkId)
                              Toast.makeText(context, "বুকমার্ক থেকে সরানো হয়েছে", Toast.LENGTH_SHORT).show()
                          } else {
                              bookmarkDao.insertBookmark(
                                  BookmarkEntity(
                                      id = bookmarkId,
                                      contentType = "HADITH",
                                      contentId = "${hadith.hadithNumber}",
                                      title = "${hadith.collection} - হাদিস ${hadith.hadithNumber}",
                                      arabicText = hadith.arabicMatn,
                                      translation = hadith.banglaTranslation,
                                      reference = hadith.referenceText
                                  )
                              )
                              Toast.makeText(context, "সংরক্ষণ করা হয়েছে", Toast.LENGTH_SHORT).show()
                          }
                        }
                    },
                    onCopyFullClick = {
                        val textToCopy = buildString {
                            appendLine(hadith.collection)
                            appendLine("হাদিস নম্বর: ${hadith.hadithNumber} (${hadith.numberingSystem})")
                            appendLine("কিতাব: ${hadith.bookNameBn} (${hadith.bookNameAr})")
                            appendLine("বিষয়: ${hadith.topic}")
                            appendLine("বর্ণনাকারী: ${hadith.narratorBn} [${hadith.narratorAr}]")
                            appendLine()
                            appendLine(hadith.arabicMatn)
                            appendLine()
                            appendLine("বাংলা উচ্চারণ (আনুমানিক):")
                            appendLine(hadith.banglaTransliteration)
                            appendLine()
                            appendLine("বাংলা অর্থ:")
                            appendLine(hadith.banglaTranslation)
                            if (hadith.explanationBn != null) {
                                appendLine()
                                appendLine("সংক্ষিপ্ত ব্যাখ্যা:")
                                appendLine(hadith.explanationBn)
                            }
                            appendLine()
                            appendLine("মান: ${hadith.grading}")
                            appendLine("সূত্র: ${hadith.referenceText}")
                            appendLine("যাচাইকরণ লিংক: ${hadith.sourceUrl}")
                        }
                        clipboardManager.setText(AnnotatedString(textToCopy))
                        Toast.makeText(context, "হাদিসটি পূর্ণ বিবরণসহ কপি করা হয়েছে", Toast.LENGTH_SHORT).show()
                    },
                    onCopyArabicClick = {
                        clipboardManager.setText(AnnotatedString(hadith.arabicMatn))
                        Toast.makeText(context, "মূল আরবি পাঠ কপি করা হয়েছে", Toast.LENGTH_SHORT).show()
                    },
                    onShareClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "${hadith.collection} • হাদিস ${hadith.hadithNumber}\n\n${hadith.arabicMatn}\n\nবাংলা অর্থ:\n${hadith.banglaTranslation}\n\nবর্ণনাকারী: ${hadith.narratorBn}\nসূত্র: ${hadith.referenceText}\nযাচাই লিংক: ${hadith.sourceUrl}"
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "হাদিস শেয়ার করুন"))
                    },
                    onOpenVerification = {
                        verificationDialogHadith = hadith
                    }
                )
            }
        }
    }

    // Source & Verification Dialog
    verificationDialogHadith?.let { hadith ->
        HadithVerificationDialog(
            hadith = hadith,
            onDismiss = { verificationDialogHadith = null },
            onOpenSourceUrl = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(hadith.sourceUrl))
                context.startActivity(intent)
            }
        )
    }
}

@Composable
fun DailyHadithHero(
    hadith: Hadith,
    onDismiss: () -> Unit,
    onViewDetails: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(RestrainedGold.copy(alpha = 0.5f))
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .testTag("daily_hadith_hero_card")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = RestrainedGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "আজকের নির্বাচিত সহিহ হাদিস",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = RestrainedGold
                    )
                }
                IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Hide daily hadith",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "হাদিস ${hadith.hadithNumber} • ${hadith.bookNameBn} • বিষয়: ${hadith.topic}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = hadith.banglaTranslation,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, lineHeight = 20.sp),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "বর্ণনাকারী: ${hadith.narratorBn}",
                    style = MaterialTheme.typography.labelSmall.copy(fontStyle = FontStyle.Italic),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextButton(
                    onClick = onViewDetails,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text("যাচাইকরণ বিবরণ", fontSize = 11.sp, color = RestrainedGold)
                }
            }
        }
    }
}

@Composable
fun HadithCard(
    hadith: Hadith,
    isBookmarked: Boolean,
    onBookmarkToggle: () -> Unit,
    onCopyFullClick: () -> Unit,
    onCopyArabicClick: () -> Unit,
    onShareClick: () -> Unit,
    onOpenVerification: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showTransliteration by remember { mutableStateOf(false) }

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
            .testTag("hadith_card_${hadith.hadithNumber}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Collection, Hadith No, Verification Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "হাদিস নং ${hadith.hadithNumber}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = RestrainedGold
                        )
                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = hadith.topic,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${hadith.bookNameBn} (${hadith.bookNameAr})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                VerificationBadge(status = hadith.verificationStatus)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Arabic Matn Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                        RoundedCornerShape(12.dp)
                    )
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(14.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "মূল আরবি হাদিস",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(
                            onClick = onCopyArabicClick,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "আরবি কপি করুন",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = hadith.arabicMatn,
                        style = DuaArabicTextStyle.copy(
                            fontSize = 20.sp,
                            lineHeight = 36.sp,
                            textAlign = TextAlign.End,
                            textDirection = TextDirection.Rtl
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Narrator Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "বর্ণনাকারী: ${hadith.narratorBn}",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                if (hadith.narratorAr.isNotBlank()) {
                    Text(
                        text = hadith.narratorAr,
                        style = MaterialTheme.typography.labelSmall.copy(textDirection = TextDirection.Rtl),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dedicated Bengali Meaning Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                        RoundedCornerShape(10.dp)
                    )
                    .border(
                        1.dp,
                        RestrainedGold.copy(alpha = 0.4f),
                        RoundedCornerShape(10.dp)
                    )
                    .padding(12.dp)
            ) {
                Column {
                    Text(
                        text = "বাংলা অর্থ",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        ),
                        color = RestrainedGold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = hadith.banglaTranslation,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            lineHeight = 22.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Bengali Transliteration (Toggleable with explicit disclaimer)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showTransliteration = !showTransliteration }
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (showTransliteration) "বাংলা উচ্চারণ লুকান ▲" else "বাংলা উচ্চারণ দেখুন (আনুমানিক) ▼",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            AnimatedVisibility(visible = showTransliteration) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ) {
                    Text(
                        text = "বাংলা উচ্চারণ (আনুমানিক):",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = hadith.banglaTransliteration,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            fontStyle = FontStyle.Italic
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "* আরবি অক্ষরের বিশুদ্ধ মাখরাজ ও সিফাত কেবল আরবি থেকেই শুদ্ধভাবে শেখা সম্ভব।",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontStyle = FontStyle.Italic
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                }
            }

            // Clearly separated Brief Explanation Box (if available)
            if (!hadith.explanationBn.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ) {
                    Column {
                        Text(
                            text = "সংক্ষিপ্ত ব্যাখ্যা ও শিক্ষা:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = hadith.explanationBn,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Footer: Grading, Reference & Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "মান: ${hadith.grading}",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = hadith.referenceText,
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Source & Verification info
                    IconButton(
                        onClick = onOpenVerification,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("verification_info_button_${hadith.hadithNumber}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "উৎস ও যাচাইকরণ",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Share button
                    IconButton(
                        onClick = onShareClick,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "শেয়ার করুন",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Copy Full button
                    IconButton(
                        onClick = onCopyFullClick,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "কপি করুন",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Bookmark button
                    IconButton(
                        onClick = onBookmarkToggle,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("bookmark_button_${hadith.hadithNumber}")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "বুকমার্ক",
                            tint = if (isBookmarked) RestrainedGold else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HadithVerificationDialog(
    hadith: Hadith,
    onDismiss: () -> Unit,
    onOpenSourceUrl: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "হাদিস যাচাই ও উৎস প্রমাণ",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "গ্রন্থ: ${hadith.collection}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = "অধ্যায়: ${hadith.bookNameBn} (${hadith.bookNameAr}) - অধ্যায় নং ${hadith.bookNumber}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "হাদিস নম্বর: ${hadith.hadithNumber} (${hadith.numberingSystem})",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "মান: ${hadith.grading} (${hadith.gradingSource})",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                )
                Text(
                    text = "যাচাইকারী সংস্করণ: ${hadith.sourceName}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "পর্যালোচনা নোট: ${hadith.reviewNotes}",
                    style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "অনলাইন রেফারেন্স: ${hadith.sourceUrl}",
                    style = MaterialTheme.typography.labelSmall,
                    color = RestrainedGold
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onOpenSourceUrl,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Sunnah.com এ দেখুন")
                }
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("বন্ধ করুন")
            }
        }
    )
}
