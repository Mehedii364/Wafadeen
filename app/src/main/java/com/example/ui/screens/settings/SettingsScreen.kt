package com.example.ui.screens.settings

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PreferencesManager
import com.example.ui.components.SectionHeader
import com.example.ui.theme.DeepEmerald
import com.example.ui.theme.RestrainedGold
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    preferencesManager: PreferencesManager,
    modifier: Modifier = Modifier
) {
    val currentLanguage by preferencesManager.languageFlow.collectAsState(initial = "bn")
    val currentTheme by preferencesManager.themeFlow.collectAsState(initial = "system")
    val currentAsrMethod by preferencesManager.asrMethodFlow.collectAsState(initial = "Hanafi")

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var showFeedbackDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("settings_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Language Selector Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        RoundedCornerShape(16.dp)
                    )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = RestrainedGold
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(
                            text = "ভাষা নির্বাচন (Language)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LanguageOption(
                        title = "বাংলা (ডিফল্ট)",
                        selected = currentLanguage == "bn",
                        onSelect = { scope.launch { preferencesManager.setLanguage("bn") } }
                    )
                    LanguageOption(
                        title = "English (English)",
                        selected = currentLanguage == "en",
                        onSelect = { scope.launch { preferencesManager.setLanguage("en") } }
                    )
                    LanguageOption(
                        title = "العربية (Arabic)",
                        selected = currentLanguage == "ar",
                        onSelect = { scope.launch { preferencesManager.setLanguage("ar") } }
                    )
                }
            }
        }

        // Theme Selector Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        RoundedCornerShape(16.dp)
                    )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ColorLens,
                            contentDescription = null,
                            tint = RestrainedGold
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(
                            text = "থিম ও ডিসপ্লে (Theme)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LanguageOption(
                        title = "সিস্টেম ডিফল্ট (System Default)",
                        selected = currentTheme == "system",
                        onSelect = { scope.launch { preferencesManager.setTheme("system") } }
                    )
                    LanguageOption(
                        title = "ডার্ক মোড (Obsidian & Emerald Dark)",
                        selected = currentTheme == "dark",
                        onSelect = { scope.launch { preferencesManager.setTheme("dark") } }
                    )
                    LanguageOption(
                        title = "লাইট মোড (Warm Light & Emerald)",
                        selected = currentTheme == "light",
                        onSelect = { scope.launch { preferencesManager.setTheme("light") } }
                    )
                }
            }
        }

        // Asr Juristic Calculation
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "আসরের ওয়াক্ত হিসাব পদ্ধতি",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LanguageOption(
                        title = "হানাফি পদ্ধতি (ছায়া দ্বিগুণ বৃদ্ধি - বাংলাদেশ মান)",
                        selected = currentAsrMethod == "Hanafi",
                        onSelect = { scope.launch { preferencesManager.setAsrMethod("Hanafi") } }
                    )
                    LanguageOption(
                        title = "শাফেঈ / জমহুর পদ্ধতি (ছায়া একগুণ বৃদ্ধি)",
                        selected = currentAsrMethod == "Standard",
                        onSelect = { scope.launch { preferencesManager.setAsrMethod("Standard") } }
                    )
                }
            }
        }

        // Content Verification & Sources Disclosure
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(
                            text = "উৎস ও তথ্যের বিশুদ্ধতা (Verification Manifest)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "• আল-কুরআন: কিং ফাহাদ কমপ্লেক্স স্বীকৃত মূল উসমানী লিপি এবং ইসলামিক ফাউন্ডেশন বাংলাদেশ ও সহিহ ইন্টারন্যাশনাল অনুবাদ।\n• সহিহ হাদিস: সহিহুল বুখারী (ইমাম মুহাম্মদ ইবনে ইসমাইল আল-বুখারী সংকলিত মূল আরবি মতন ও দারুসসালাম রেফারেন্স)।\n• নামাজের নিয়ম ও মাসায়েল: হানাফি ফিকহের প্রামাণ্য গ্রন্থাবলি (আল-হিদায়া, ফাতাওয়া হিন্দিয়া) এবং জমহুর ওলামাদের সিদ্ধান্ত।\n• নামাজের সময় গণনা: ইসলামিক ফাউন্ডেশন বাংলাদেশ ও করাচি ১৮° টুইলাইট কোণভিত্তিক বিশুদ্ধ জ্যোতির্বৈজ্ঞানিক হিসাব।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { showFeedbackDialog = true },
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Feedback, contentDescription = null)
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(text = "বিষয়বস্তু পর্যালোচনা বা সংশোধন মতামত")
                    }
                }
            }
        }

        // Developer Attribution & Offline Privacy Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DeepEmerald),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, RestrainedGold.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Wafa Deen",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = RestrainedGold
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Developer attribution: Wafa Zone by Mehedi364",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Package ID: site.wafazone.wafadeen • সংস্করণ ১.০ (Native Android)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "১০০% অফলাইন নিশ্চয়তা: আপনার কোনো ব্যক্তিগত ডাটা, বুকমার্ক বা হিসাব ডিভাইসের বাইরে যায় না। কোনো অ্যাকাউন্ট, ট্র্যাকার বা বিজ্ঞাপনের উপস্থিতি নেই।",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = Color.White.copy(alpha = 0.75f)
                    )
                }
            }
        }
    }

    if (showFeedbackDialog) {
        AlertDialog(
            onDismissRequest = { showFeedbackDialog = false },
            title = { Text("বিষয়বস্তু পর্যালোচনা ও সংশোধন") },
            text = {
                Text(
                    "Wafa Deen অ্যাপে কোনো আরবি বানান, অনুবাদ বা ফিকহ বিষয়ক সংশোধনের প্রয়োজন মনে হলে পর্যালোচনা দলের সাথে যোগাযোগ করতে পারেন:\n\nEmail: contact@wafazone.site\nGitHub: github.com/wafazone/wafadeen"
                )
            },
            confirmButton = {
                TextButton(onClick = { showFeedbackDialog = false }) {
                    Text("ঠিক আছে")
                }
            }
        )
    }
}

@Composable
fun LanguageOption(
    title: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}
