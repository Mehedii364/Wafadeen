package com.example.ui.screens.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.PrayerTimes
import com.example.data.model.VerificationStatus
import com.example.ui.components.NextPrayerBanner
import com.example.ui.components.SectionHeader
import com.example.ui.components.VerificationBadge
import com.example.ui.navigation.Screen
import com.example.ui.theme.DeepEmerald
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RestrainedGold

@Composable
fun HomeScreen(
    prayerTimes: PrayerTimes,
    selectedCity: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Header & Greeting
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        RoundedCornerShape(20.dp)
                    )
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "আস-সালামু আলাইকুম",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Wafa Deen — দ্বীন শিক্ষার বিশ্বস্ত সঙ্গী",
                                style = MaterialTheme.typography.bodySmall,
                                color = RestrainedGold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mosque,
                                contentDescription = "Wafa Deen",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        // Next Prayer & Times Banner
        item {
            NextPrayerBanner(
                prayerTimes = prayerTimes,
                cityName = selectedCity,
                onViewAllClick = { onNavigate(Screen.Qibla.route) },
                modifier = Modifier.testTag("prayer_banner")
            )
        }

        // Quick Features Navigation Section
        item {
            SectionHeader(title = "দ্বীনি শিক্ষার মূল বিভাগসমূহ")
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    HomeGridCard(
                        title = "নামাজ শিক্ষা",
                        subtitle = "চিত্রসহ নামাজ, উচ্চারণ ও অর্থ",
                        icon = Icons.Default.Mosque,
                        onClick = { onNavigate(Screen.Salah.route) },
                        modifier = Modifier.weight(1f).testTag("card_salah")
                    )
                    HomeGridCard(
                        title = "আল-কুরআন",
                        subtitle = "উচ্চারণ, অর্থ ও অডিও তেলাওয়াত",
                        icon = Icons.AutoMirrored.Filled.MenuBook,
                        onClick = { onNavigate(Screen.Quran.route) },
                        modifier = Modifier.weight(1f).testTag("card_quran")
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    HomeGridCard(
                        title = "সহিহ হাদিস",
                        subtitle = "বুখারী শরীফ ও নির্ভরযোগ্য সংকলন",
                        icon = Icons.Default.MenuBook,
                        onClick = { onNavigate(Screen.Hadith.route) },
                        modifier = Modifier.weight(1f).testTag("card_hadith")
                    )
                    HomeGridCard(
                        title = "দোয়া ও জিকির",
                        subtitle = "দৈনন্দিন ও সকাল-সন্ধ্যার আমল",
                        icon = Icons.Default.PanTool,
                        onClick = { onNavigate(Screen.Duas.route) },
                        modifier = Modifier.weight(1f).testTag("card_duas")
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    HomeGridCard(
                        title = "ডিজিটাল তাসবীহ",
                        subtitle = "কাউন্টার ও জিকির সংরক্ষণ",
                        icon = Icons.Default.RadioButtonChecked,
                        onClick = { onNavigate(Screen.Tasbih.route) },
                        modifier = Modifier.weight(1f).testTag("card_tasbih")
                    )
                    HomeGridCard(
                        title = "কিবলা ও ওয়াক্ত",
                        subtitle = "নির্ভুল কিবলা কম্পাস ও সময়",
                        icon = Icons.Default.CompassCalibration,
                        onClick = { onNavigate(Screen.Qibla.route) },
                        modifier = Modifier.weight(1f).testTag("card_qibla")
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    HomeGridCard(
                        title = "রমজান ও ক্যালেন্ডার",
                        subtitle = "সেহরি, ইফতার ও যাকাত ক্যালকুলেটর",
                        icon = Icons.Default.CalendarMonth,
                        onClick = { onNavigate(Screen.Ramadan.route) },
                        modifier = Modifier.weight(1f).testTag("card_ramadan")
                    )
                    HomeGridCard(
                        title = "সংরক্ষিত আমল",
                        subtitle = "বুকমার্ককৃত আয়াত ও হাদিস",
                        icon = Icons.Default.Bookmark,
                        onClick = { onNavigate(Screen.Bookmarks.route) },
                        modifier = Modifier.weight(1f).testTag("card_bookmarks")
                    )
                }
            }
        }

        // Daily Authentic Hadith / Inspiration Card
        item {
            SectionHeader(title = "আজকের সহিহ হাদিস")
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, RestrainedGold.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "সহিহ বুখারী • হাদিস ১",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = RestrainedGold
                        )
                        VerificationBadge(status = VerificationStatus.VERIFIED)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.End)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "“নিশ্চয়ই সমস্ত কাজের ফলাফল নিয়তের ওপর নির্ভরশীল। প্রত্যেক ব্যক্তি তাই পাবে যা সে নিয়ত করেছে।”",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "বর্ণনাকারী: উমর ইবনুল খাত্তাব (রা.)",
                        style = MaterialTheme.typography.labelSmall,
                        color = RestrainedGold
                    )
                }
            }
        }

        // Source Verification & Offline Guarantee Note
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = "যাচাইকরণ",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "১০০% অফলাইন ও নির্ভরযোগ্য উৎস",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "কোনো অ্যাকাউন্ট বা ইন্টারনেটের প্রয়োজন নেই। ইসলামিক ফাউন্ডেশন বাংলাদেশ ও সহিহ বুখারী সূত্রে যাচাইকৃত।",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeGridCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.border(
            1.dp,
            MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
            RoundedCornerShape(16.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(MaterialTheme.colorScheme.primary, DeepEmerald)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = RestrainedGold,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 15.sp),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )
        }
    }
}
