package com.example.ui.screens.salah

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DailyPrayerStructure
import com.example.data.model.SalahCategory
import com.example.data.model.SalahGuideItem
import com.example.repository.SalahRepository
import com.example.ui.components.ArabicTextDisplay
import com.example.ui.components.SectionHeader
import com.example.ui.theme.DeepEmerald
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RestrainedGold

@Composable
fun LearnSalahScreen(
    onPlayAudio: (url: String, title: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("নামাজের নিয়ম ও কিরাত", "পাঁচ ওয়াক্তের রাকাত", "অজু ও পবিত্রতা", "মাযহাব ও সতর্কতা")

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("learn_salah_screen")
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = MaterialTheme.colorScheme.primary,
                    height = 3.dp
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> PrayerStepsTab(onPlayAudio = onPlayAudio)
            1 -> DailyPrayersStructureTab()
            2 -> PurificationTab()
            3 -> FiqhAndDifferencesTab()
        }
    }
}

@Composable
fun PrayerStepsTab(onPlayAudio: (String, String) -> Unit) {
    val prayerSteps = remember {
        SalahRepository.salahGuideItems.filter { it.category == SalahCategory.PRAYER_STEPS }
    }
    var selectedFilterIndex by remember { mutableIntStateOf(0) }
    val filters = listOf("সকল ধাপ (১-১৬)", "দাঁড়ানো ও কিরাত", "রুকু ও সোজা হওয়া", "সেজদাহ ও বৈঠক", "সালাম ও মোনাজাত")

    val filteredSteps = remember(selectedFilterIndex, prayerSteps) {
        when (selectedFilterIndex) {
            1 -> prayerSteps.filter { it.id in listOf("step_jaynamaz", "step_takbir", "step_sana", "step_taawwudh", "step_fatihah") }
            2 -> prayerSteps.filter { it.id in listOf("step_ruku", "step_qawmah") }
            3 -> prayerSteps.filter { it.id in listOf("step_sujud", "step_jalsah", "step_sujud2", "step_tashahhud") }
            4 -> prayerSteps.filter { it.id in listOf("step_durood", "step_duamasurah", "step_salam", "step_qunut", "step_post_prayer") }
            else -> prayerSteps
        }
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "জায়নামাজে দাঁড়ানো থেকে সালাম পর্যন্ত",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "চিত্রসহ নামাজ শিক্ষা",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = RestrainedGold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "প্রতিটি রুকন ও অবস্থানের বাস্তব চিত্র/ছবি, সহিহ আরবি তেলাওয়াত, স্পষ্ট বাংলা উচ্চারণ, প্রাঞ্জল বাংলা অর্থ এবং হানাফি ও জমহুর ফিকহের নির্ভরযোগ্য ব্যাখ্যা।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Filter chips for quick navigation
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filters.size) { index ->
                    FilterChip(
                        selected = selectedFilterIndex == index,
                        onClick = { selectedFilterIndex = index },
                        label = {
                            Text(
                                text = filters[index],
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        items(filteredSteps) { step ->
            SalahStepCard(step = step, onPlayAudio = onPlayAudio)
        }
    }
}

@Composable
fun SalahStepCard(
    step: SalahGuideItem,
    onPlayAudio: (String, String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                RoundedCornerShape(16.dp)
            )
            .testTag("salah_card_${step.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Title and Audio Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = step.titleBn,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (!step.postureName.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "অবস্থান: ${step.postureName}",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = RestrainedGold
                        )
                    }
                }

                if (step.audioUrl != null) {
                    Button(
                        onClick = { onPlayAudio(step.audioUrl, step.titleBn) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "উচ্চারণ শুনুন",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "তেলাওয়াত",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            // Posture Illustration Image (if available)
            if (step.imageResId != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            RoundedCornerShape(12.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(id = step.imageResId),
                        contentDescription = "${step.titleBn} - শারীরিক অবস্থান ও পদ্ধতি",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                    // Visual tag overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                            .background(
                                DeepEmerald.copy(alpha = 0.85f),
                                RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "সঠিক নিয়ম ও অবস্থান",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = RestrainedGold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Arabic, Bengali Pronunciation & Bengali Meaning
            if (!step.arabicText.isNullOrBlank()) {
                ArabicTextDisplay(
                    arabicText = step.arabicText,
                    transliteration = step.transliterationBn,
                    translation = step.meaningBn
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Step Explanation
            Text(
                text = step.explanationBn,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Madhhab Notes
            if (!step.hanafiNote.isNullOrBlank() || !step.otherMadhhabsNote.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            RestrainedGold.copy(alpha = 0.1f),
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            RestrainedGold.copy(alpha = 0.35f),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(10.dp)
                ) {
                    Column {
                        if (!step.hanafiNote.isNullOrBlank()) {
                            Text(
                                text = "হানাফি ফিকহ: ${step.hanafiNote}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        if (!step.otherMadhhabsNote.isNullOrBlank()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "অন্যান্য মাযহাব: ${step.otherMadhhabsNote}",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            if (!step.reference.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "দলিল / হাদিস সূত্র: ${step.reference}",
                    style = MaterialTheme.typography.labelSmall,
                    color = RestrainedGold
                )
            }
        }
    }
}

@Composable
fun DailyPrayersStructureTab() {
    val prayers = SalahRepository.dailyPrayersStructure

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "পাঁচ ওয়াক্ত ফরজ, সুন্নাত ও বিতরের রাকাত তালিকা",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "হানাফি ফিকহ অনুসারে দৈনিক ১৭ রাকাত ফরজ ও ওয়াজিব এবং নির্ধারিত সুন্নাতে মুয়াক্কাদার তালিকা।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        items(prayers) { prayer ->
            PrayerStructureCard(prayer = prayer)
        }
    }
}

@Composable
fun PrayerStructureCard(prayer: DailyPrayerStructure) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                RoundedCornerShape(16.dp)
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = prayer.nameBn,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "মোট ${prayer.totalRakats} রাকাত",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = RestrainedGold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "ওয়াক্ত: ${prayer.timeDescriptionBn}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Breakdown Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (prayer.sunnahBefore > 0) {
                    RakatBadge(title = "সুন্নাত", count = prayer.sunnahBefore)
                }
                RakatBadge(title = "ফরজ", count = prayer.fard, isFard = true)
                if (prayer.sunnahAfter > 0) {
                    RakatBadge(title = "সুন্নাত", count = prayer.sunnahAfter)
                }
                if (prayer.witr > 0) {
                    RakatBadge(title = "বিতর (ওয়াজিব)", count = prayer.witr, isWitr = true)
                }
                if (prayer.nafl > 0) {
                    RakatBadge(title = "নফল", count = prayer.nafl)
                }
            }

            if (!prayer.specialNotesBn.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = prayer.specialNotesBn,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun RakatBadge(title: String, count: Int, isFard: Boolean = false, isWitr: Boolean = false) {
    val bg = when {
        isFard -> MaterialTheme.colorScheme.primary
        isWitr -> RestrainedGold
        else -> MaterialTheme.colorScheme.surface
    }
    val fg = when {
        isFard -> MaterialTheme.colorScheme.onPrimary
        isWitr -> MaterialTheme.colorScheme.surface
        else -> MaterialTheme.colorScheme.onSurface
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(bg, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(text = "$count রাকাত", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold), color = fg)
        Text(text = title, style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp), color = fg.copy(alpha = 0.9f))
    }
}

@Composable
fun PurificationTab() {
    val purifications = remember {
        SalahRepository.salahGuideItems.filter { it.category == SalahCategory.PURIFICATION }
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(purifications) { item ->
            SalahStepCard(step = item, onPlayAudio = { _, _ -> })
        }
    }
}

@Composable
fun FiqhAndDifferencesTab() {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "মাযহাবীয় মতভেদ ও আদব",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = RestrainedGold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "ইসলামের চার সম্মানিত মাযহাব (হানাফি, শাফেঈ, মালেকি ও হাম্বলি)-এর কোনো মাসআলাতেই মৌলিক আকীদা বা ফরজ নামাজে কোনো বিরোধ নেই। প্রতিটি মতভেদই সহিহ হাদিসের ভিত্তিতে উম্মাহর জন্য সহজতা ও প্রশস্ততাস্বরূপ।\n\nবাংলাদেশে হানাফি ফিকহ সর্বাধিক অনুসৃত। তবে অন্য কোনো মাযহাবের আমল দেখলে তাকে বাতিল বা ভুল মনে না করে পারস্পরিক শ্রদ্ধা বজায় রাখা মুসলিম ঐক্যের দাবি।",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "সিজদায়ে সাহু (ভুল সংশোধনের সিজদা)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "নামাজের কোনো ওয়াজিব ভুলে ছুটে গেলে বা বিলম্বিত হলে সিজদায়ে সাহু ওয়াজিব হয়।\n\nপদ্ধতি:\nশেষ বৈঠকে তাশাহহুদ পাঠের পর কেবল ডান দিকে এক সালাম ফিরিয়ে 'আল্লাহু আকবার' বলে দুটি অতিরিক্ত সিজদা আদায় করা। এরপর পুনরায় বসে তাশাহহুদ, দরূদ ও দু'আ মাসূরা পড়ে উভয় দিকে সালাম ফিরিয়ে নামাজ শেষ করা।",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "মুসাফিরের নামাজ (কসর)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "কেউ নিজ এলাকা থেকে কমপক্ষে ৪৮ মাইল (প্রায় ৭৮ কিমি) দূরবর্তী স্থানে সফরের নিয়তে বের হলে সে মুসাফির হিসেবে গণ্য হয়।\n\nমুসাফির ব্যক্তি ৪ রাকাতবিশিষ্ট ফরজ নামাজ (জোহর, আসর, এশা) ২ রাকাত করে কসর আদায় করবে। ফজর, মাগরিব ও বিতরের নামাজ পূর্ণই আদায় করতে হয়।",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
