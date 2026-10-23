package com.example.ui.screens.ramadan

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.NisabType
import com.example.data.model.PrayerTimes
import com.example.data.model.ZakatInput
import com.example.data.model.ZakatResult
import com.example.ui.components.ArabicTextDisplay
import com.example.ui.components.SectionHeader
import com.example.ui.theme.DeepEmerald
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RestrainedGold

@Composable
fun RamadanScreen(
    prayerTimes: PrayerTimes,
    selectedCity: String,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("রমজান ও রোজা", "যাকাত ক্যালকুলেটর", "তারাবীহ ও কদর")

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("ramadan_screen")
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
            0 -> RamadanFastTab(prayerTimes = prayerTimes, selectedCity = selectedCity)
            1 -> ZakatCalculatorTab()
            2 -> TaraweehAndQadrTab()
        }
    }
}

@Composable
fun RamadanFastTab(prayerTimes: PrayerTimes, selectedCity: String) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        // Suhoor & Iftar Card for current city
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
                        .padding(18.dp)
                ) {
                    Text(
                        text = "আজকের সেহরি ও ইফতারের সময়সূচি",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = RestrainedGold
                    )
                    Text(
                        text = "স্থান: $selectedCity • ${prayerTimes.hijriDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 20.dp, vertical = 12.dp)
                        ) {
                            Text(text = "সেহরির শেষ সময়", style = MaterialTheme.typography.labelSmall, color = RestrainedGold)
                            Text(text = prayerTimes.fajr, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = Color.White)
                            Text(text = "(ফজরের ওয়াক্ত)", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = Color.White.copy(alpha = 0.7f))
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 20.dp, vertical = 12.dp)
                        ) {
                            Text(text = "ইফতারের সময়", style = MaterialTheme.typography.labelSmall, color = RestrainedGold)
                            Text(text = prayerTimes.maghrib, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold), color = Color.White)
                            Text(text = "(মাগরিবের ওয়াক্ত)", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = Color.White.copy(alpha = 0.7f))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "* স্থানীয় মসজিদের সাথে কয়েক মিনিট সতর্কতামূলক পার্থক্য থাকতে পারে। নির্ভরযোগ্য স্থানীয় ঘোষণা অনুসরণ করুন।",
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                        color = Color.White.copy(alpha = 0.75f)
                    )
                }
            }
        }

        // Iftar Dua Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ইফতারের সময় পাঠ করার সহিহ দু'আ",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = RestrainedGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ArabicTextDisplay(
                        arabicText = "ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الأَجْرُ إِنْ شَاءَ اللَّهُ",
                        transliteration = "যাহাবায-যামা'উ ওয়াবতাল্লাতিল 'উরূকু ওয়া সাবাতাল আজরু ইনশাআল্লাহ।",
                        translation = "পিপাসা দূরীভূত হলো, শিরা-উপশিরা সিক্ত হলো এবং ইনশাআল্লাহ সওয়াবও সুনিশ্চিত হলো।"
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "সূত্র: সুনানে আবু দাউদ ২৩৫৭ (হাদিসটি সহিহ বা হাসান সনদে বর্ণিত)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Roza Niyyah & Guidance
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "রোজার নিয়ত ও করণীয়",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "নিয়ত শব্দের অর্থ সংকল্প। অন্তরে দৃঢ় সংকল্প করাই নিয়ত। মুখে কোনো নির্দিষ্ট বাক্য উচ্চারণ করা ফরজ নয়। রাতে বা সেহরি খাওয়ার সময় অন্তরে আল্লাহর সন্তুষ্টির উদ্দেশ্যে রোজা রাখার ইচ্ছা করলেই নিয়ত সম্পন্ন হয়।\n\nরোজার মূল উদ্দেশ্য তাকওয়া বা খোদাভীতি অর্জন করা। রাসুলুল্লাহ (সা.) বলেছেন: 'যে ব্যক্তি মিথ্যা কথা ও পাপাচার ত্যাগ করল না, তার পানাহার বর্জনে আল্লাহর কোনো প্রয়োজন নেই।' (সহিহ বুখারী ১৯০৩)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ZakatCalculatorTab() {
    var goldGramPrice by remember { mutableStateOf("11500") }
    var silverGramPrice by remember { mutableStateOf("120") }
    var selectedNisab by remember { mutableStateOf(NisabType.SILVER) }

    var cashInput by remember { mutableStateOf("") }
    var goldValueInput by remember { mutableStateOf("") }
    var silverValueInput by remember { mutableStateOf("") }
    var merchandiseInput by remember { mutableStateOf("") }
    var receivablesInput by remember { mutableStateOf("") }
    var debtsInput by remember { mutableStateOf("") }

    val goldPrice = goldGramPrice.toDoubleOrNull() ?: 11500.0
    val silverPrice = silverGramPrice.toDoubleOrNull() ?: 120.0

    // Nisab thresholds: Silver 595 grams, Gold 85 grams
    val nisabThreshold = if (selectedNisab == NisabType.SILVER) (595.0 * silverPrice) else (85.0 * goldPrice)

    val cash = cashInput.toDoubleOrNull() ?: 0.0
    val goldVal = goldValueInput.toDoubleOrNull() ?: 0.0
    val silverVal = silverValueInput.toDoubleOrNull() ?: 0.0
    val merchandise = merchandiseInput.toDoubleOrNull() ?: 0.0
    val receivables = receivablesInput.toDoubleOrNull() ?: 0.0
    val debts = debtsInput.toDoubleOrNull() ?: 0.0

    val grossAssets = cash + goldVal + silverVal + merchandise + receivables
    val netWealth = (grossAssets - debts).coerceAtLeast(0.0)
    val isZakatObligatory = netWealth >= nisabThreshold
    val zakatPayable = if (isZakatObligatory) (netWealth * 0.025) else 0.0

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxSize().testTag("zakat_calculator_tab")
    ) {
        // Nisab basis disclosure card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "যাকাতের নিসাব ভিত্তি ও শর্তাবলী",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = RestrainedGold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "যাকাত ফরজ হওয়ার জন্য মৌলিক প্রয়োজনাতিরিক্ত সম্পদ এক চান্দ্র বছর (হাওল) নিসাব পরিমাণ বা তার বেশি থাকা আবশ্যক।\n\n• রূপার নিসাব: ৫২.৫ তোলা = ৫৯৫ গ্রাম (বর্তমান মূল্য প্রায় ${(595.0 * silverPrice).toInt()} ৳ - জমহুর ও গরিবদের জন্য অধিক কল্যাণকর)।\n• স্বর্ণের নিসাব: ৭.৫ তোলা = ৮৫ গ্রাম (বর্তমান মূল্য প্রায় ${(85.0 * goldPrice).toInt()} ৳)।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedNisab == NisabType.SILVER,
                            onClick = { selectedNisab = NisabType.SILVER },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                        )
                        Text(text = "রূপার নিসাব (৫৯৫ গ্রাম - সুপারিশকৃত)", style = MaterialTheme.typography.bodySmall)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedNisab == NisabType.GOLD,
                            onClick = { selectedNisab = NisabType.GOLD },
                            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                        )
                        Text(text = "স্বর্ণের নিসাব (৮৫ গ্রাম)", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        // Asset input fields
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "আপনার যাকাতযোগ্য সম্পদের বিবরণ (টাকায়)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    ZakatInputField("নগদ টাকা (হাতে ও ব্যাংকে জমা)", cashInput) { cashInput = it }
                    ZakatInputField("স্বর্ণের বর্তমান আনুমানিক বাজারমূল্য", goldValueInput) { goldValueInput = it }
                    ZakatInputField("রূপার বর্তমান বাজারমূল্য", silverValueInput) { silverValueInput = it }
                    ZakatInputField("ব্যবসায়িক পণ্যের বিক্রয়যোগ্য মূল্য", merchandiseInput) { merchandiseInput = it }
                    ZakatInputField("প্রাপ্য ঋণ (যা ফেরত পাওয়ার আশা আছে)", receivablesInput) { receivablesInput = it }
                    ZakatInputField("তাৎক্ষণিক পরিশোধযোগ্য ঋণ (বিয়োগ হবে)", debtsInput) { debtsInput = it }
                }
            }
        }

        // Results Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isZakatObligatory) DeepEmerald else MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if (isZakatObligatory) RestrainedGold else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        RoundedCornerShape(18.dp)
                    )
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "হিসাবের সারসংক্ষেপ",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isZakatObligatory) RestrainedGold else MaterialTheme.colorScheme.onSurface
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "মোট নীট যাকাতযোগ্য সম্পদ:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isZakatObligatory) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${netWealth.toInt()} ৳",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (isZakatObligatory) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "নিসাব সীমা (${if (selectedNisab == NisabType.SILVER) "রূপা" else "স্বর্ণ"}):",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isZakatObligatory) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${nisabThreshold.toInt()} ৳",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isZakatObligatory) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (isZakatObligatory) RestrainedGold.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface,
                                RoundedCornerShape(10.dp)
                            )
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = if (isZakatObligatory) "প্রদেয় যাকাত (২.৫% হারে):" else "যাকাত ফরজ নয়",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = if (isZakatObligatory) RestrainedGold else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (isZakatObligatory) "সম্পদ নিসাব অতিক্রম করেছে" else "সম্পদ নিসাব সীমার নিচে রয়েছে",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isZakatObligatory) Color.White.copy(alpha = 0.85f) else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Text(
                                text = "${zakatPayable.toInt()} ৳",
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (isZakatObligatory) RestrainedGold else MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ZakatInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, style = MaterialTheme.typography.bodySmall) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TaraweehAndQadrTab() {
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
                        text = "তারাবীহ নামাজের রাকাত সংখ্যা ও পর্যালোচনা",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = RestrainedGold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "তারাবীহর নামাজ রমজানের অত্যন্ত বরকতপূর্ণ সুন্নাত।\n\n• বিশ (২০) রাকাত: খোলাফায়ে রাশেদীন বিশেষত উমর (রা.)-এর আমলে সাহাবায়ে কেরামের ঐকমত্য এবং হানাফি, শাফেঈ ও হাম্বলি মাযহাবের জমহুর ফুকাহায়ে কেরামের সর্বসম্মত আমল। মক্কা ও মদিনার হারামাইন শরিফেও যুগ যুগ ধরে ২০ রাকাত আদায় হয়ে আসছে।\n• আট (৮) রাকাত: কোনো কোনো গবেষক আয়েশা (রা.)-এর হাদিসের ওপর ভিত্তি করে ৮ রাকাত আদায় করেন।\n\nএটি নফল ও কিয়ামুল লাইল জাতীয় সুন্নাত হওয়ায় এ নিয়ে ঝগড়া-বিবাদ করা বর্জনীয়।",
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
                        text = "লাইলাতুল ক্বদর ও শেষ দশকের আমল",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "রমজানের শেষ দশকে বিশেষ করে বেজোড় রাতসমূহে (২১, ২৩, ২৫, ২৭ ও ২৯তম রাতে) কদরের রাত অনুসন্ধান করতে বলা হয়েছে। এ রাতের একটি ইবাদত ১,০০০ মাসের (৮৩ বছর ৪ মাস) চেয়েও উত্তম।\n\nকদরের রাতের বিশেষ দু'আ:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ArabicTextDisplay(
                        arabicText = "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
                        transliteration = "আল্লাহুম্মা ইন্নাকা 'আফুউউন তুহিব্বুল 'আফওয়া ফা'ফু 'আন্নী।",
                        translation = "হে আল্লাহ! নিশ্চয়ই আপনি ক্ষমাশীল, ক্ষমা করতে ভালোবাসেন; অতএব আমাকে ক্ষমা করে দিন।"
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "সূত্র: সুনানে তিরমিজি ৩৫১৩ (সহিহ)",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
