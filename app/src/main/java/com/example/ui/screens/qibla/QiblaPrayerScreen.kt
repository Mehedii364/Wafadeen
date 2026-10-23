package com.example.ui.screens.qibla

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CityLocation
import com.example.data.model.PrayerTimes
import com.example.ui.components.SectionHeader
import com.example.ui.theme.DeepEmerald
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RestrainedGold
import com.example.util.PrayerTimeCalculator
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun QiblaPrayerScreen(
    currentPrayerTimes: PrayerTimes,
    selectedCity: String,
    onCitySelected: (CityLocation) -> Unit,
    isHanafiAsr: Boolean,
    onToggleAsrMethod: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("নামাজের সময়সূচি", "কিবলা কম্পাস")

    val cities = PrayerTimeCalculator.CITIES
    val activeCity = remember(selectedCity) {
        cities.firstOrNull { it.nameEn.equals(selectedCity, ignoreCase = true) || it.nameBn == selectedCity }
            ?: cities[0]
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("qibla_screen")
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

        // City Selector Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cities) { city ->
                FilterChip(
                    selected = activeCity.nameEn == city.nameEn,
                    onClick = { onCitySelected(city) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    label = { Text("${city.nameBn} (${city.nameEn})") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        when (selectedTab) {
            0 -> PrayerScheduleTab(
                prayerTimes = currentPrayerTimes,
                city = activeCity,
                isHanafiAsr = isHanafiAsr,
                onToggleAsrMethod = onToggleAsrMethod
            )
            1 -> QiblaCompassTab(city = activeCity)
        }
    }
}

@Composable
fun PrayerScheduleTab(
    prayerTimes: PrayerTimes,
    city: CityLocation,
    isHanafiAsr: Boolean,
    onToggleAsrMethod: (Boolean) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DeepEmerald),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${city.nameBn} (${city.country})",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "তারিখ: ${prayerTimes.dateFormatted} • ${prayerTimes.hijriDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = RestrainedGold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "পদ্ধতি: ${prayerTimes.calculationMethod}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Asr Juristic Method Switcher
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "আসরের ওয়াক্ত পদ্ধতি: ${if (isHanafiAsr) "হানাফি" else "শাফেঈ / জমহুর"}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isHanafiAsr) "দ্বিগুণ ছায়া বৃদ্ধি (বাংলাদেশ ও ভারতীয় উপমহাদেশে প্রচলিত)" else "একগুণ ছায়া বৃদ্ধি",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = isHanafiAsr,
                        onCheckedChange = onToggleAsrMethod,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = RestrainedGold,
                            checkedTrackColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }

        // Daily Prayers Table
        item {
            PrayerTimeRow("ফজর (Fajr)", "সুবহে সাদিক থেকে সূর্যোদয়ের পূর্বে", prayerTimes.fajr, Icons.Default.Mosque)
        }
        item {
            PrayerTimeRow("সূর্যোদয় (Sunrise)", "ইশরাকের ওয়াক্ত শুরু", prayerTimes.sunrise, Icons.Default.LocationOn, isSun = true)
        }
        item {
            PrayerTimeRow("জোহর (Dhuhr)", "সূর্য ঢলে পড়ার পর থেকে", prayerTimes.dhuhr, Icons.Default.Mosque)
        }
        item {
            PrayerTimeRow("আসর (Asr)", prayerTimes.asrJuristicMethod, prayerTimes.asr, Icons.Default.Mosque)
        }
        item {
            PrayerTimeRow("মাগরিব (Maghrib)", "সূর্যাস্ত ও ইফতারের সময়", prayerTimes.maghrib, Icons.Default.Mosque)
        }
        item {
            PrayerTimeRow("এশা (Isha)", "রাতের সালাত ও বিতর ওয়াজিব", prayerTimes.isha, Icons.Default.Mosque)
        }
    }
}

@Composable
fun PrayerTimeRow(
    title: String,
    subtitle: String,
    time: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSun: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (isSun) RestrainedGold.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isSun) RestrainedGold else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = time,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = if (isSun) RestrainedGold else MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun QiblaCompassTab(city: CityLocation) {
    val qiblaAngle = remember(city) {
        PrayerTimeCalculator.calculateQiblaBearing(city.latitude, city.longitude)
    }

    val context = LocalContext.current
    var deviceAzimuth by remember { mutableFloatStateOf(0f) }
    var hasSensor by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        val rotationSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
            ?: sensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION)

        if (sensorManager != null && rotationSensor != null) {
            hasSensor = true
            val listener = object : SensorEventListener {
                val rotationMatrix = FloatArray(9)
                val orientation = FloatArray(3)

                override fun onSensorChanged(event: SensorEvent?) {
                    if (event == null) return
                    if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
                        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                        SensorManager.getOrientation(rotationMatrix, orientation)
                        val azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
                        deviceAzimuth = (azimuth + 360f) % 360f
                    } else if (event.sensor.type == Sensor.TYPE_ORIENTATION) {
                        deviceAzimuth = event.values[0]
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }
            sensorManager.registerListener(listener, rotationSensor, SensorManager.SENSOR_DELAY_UI)
            onDispose { sensorManager.unregisterListener(listener) }
        } else {
            hasSensor = false
            onDispose {}
        }
    }

    val relativeAngle = (qiblaAngle.toFloat() - deviceAzimuth + 360f) % 360f
    val animatedRelativeAngle by animateFloatAsState(targetValue = relativeAngle, label = "qibla_rotation")

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize().testTag("qibla_compass_tab")
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${city.nameBn} হতে কিবলার দিক",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "$qiblaAngle°",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = RestrainedGold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "উত্তর দিক হতে ঘড়ির কাঁটার দিকে $qiblaAngle° কোণে পবিত্র কাবা শরিফ অবস্থিত (পশ্চিম-উত্তর-পশ্চিম দিক)।",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Compass Dial Visual
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(260.dp)
                    .clip(CircleShape)
                    .background(DeepEmerald)
                    .border(4.dp, RestrainedGold.copy(alpha = 0.5f), CircleShape)
            ) {
                // Compass markings
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val radius = size.minDimension / 2f
                    val center = Offset(size.width / 2f, size.height / 2f)

                    // Draw cardinal points lines
                    for (i in 0 until 360 step 30) {
                        val angleRad = Math.toRadians(i.toDouble())
                        val isCardinal = i % 90 == 0
                        val tickLength = if (isCardinal) 20f else 10f
                        val start = Offset(
                            (center.x + (radius - tickLength) * sin(angleRad)).toFloat(),
                            (center.y - (radius - tickLength) * cos(angleRad)).toFloat()
                        )
                        val end = Offset(
                            (center.x + radius * sin(angleRad)).toFloat(),
                            (center.y - radius * cos(angleRad)).toFloat()
                        )
                        drawLine(
                            color = if (isCardinal) Color(0xFFC5A253) else Color.White.copy(alpha = 0.3f),
                            start = start,
                            end = end,
                            strokeWidth = if (isCardinal) 3f else 1.5f
                        )
                    }
                }

                // Rotating Needle toward Qibla
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(if (hasSensor) animatedRelativeAngle else qiblaAngle.toFloat()),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.height(18.dp))
                        Icon(
                            imageVector = Icons.Default.Mosque,
                            contentDescription = "কাবা শরিফ",
                            tint = RestrainedGold,
                            modifier = Modifier.size(34.dp)
                        )
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(60.dp)
                                .background(RestrainedGold, RoundedCornerShape(2.dp))
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "কিবলা",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "$qiblaAngle°",
                        style = MaterialTheme.typography.labelSmall.copy(color = RestrainedGold)
                    )
                }
            }
        }

        // Calibration Note
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = RestrainedGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (hasSensor)
                            "সঠিক কিবলা পেতে ফোনটিকে অনুভূমিকভাবে রেখে বাতাসে '8' আকারে ঘুরিয়ে কম্পাস সেন্সরটি ক্যালিব্রেট করুন।"
                        else
                            "ডিভাইসে কম্পাস সেন্সর অনুপস্থিত থাকলে মানচিত্র বা সূর্য/ধ্রুবতারার সাপেক্ষে $qiblaAngle° কোণে মুখ করুন।",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
