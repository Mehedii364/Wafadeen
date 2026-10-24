package com.example.ui.screens.qibla

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CalculationMethod
import com.example.data.model.CityLocation
import com.example.data.model.PrayerTimes
import com.example.data.model.PrayerType
import com.example.service.PrayerNotificationManager
import com.example.service.SalahAudioRegistry
import com.example.ui.theme.DeepEmerald
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.RestrainedGold
import com.example.util.CompassSensorManager
import com.example.util.DeviceLocationManager
import com.example.util.PrayerTimeCalculator
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun QiblaPrayerScreen(
    currentPrayerTimes: PrayerTimes,
    selectedCity: String,
    onCitySelected: (CityLocation) -> Unit,
    isHanafiAsr: Boolean,
    onToggleAsrMethod: (Boolean) -> Unit,
    onPlayAudio: (url: String, trackTitle: String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Location manager & Compass Sensor manager
    val locationManager = remember { DeviceLocationManager(context) }
    val compassManager = remember { CompassSensorManager(context) }

    val liveLocationState by locationManager.locationState.collectAsState()
    val compassState by compassManager.compassData.collectAsState()

    var isRefreshingLocation by remember { mutableStateOf(false) }
    var useLiveGps by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedCalculationMethod by remember { mutableStateOf(CalculationMethod.KARACHI_IFB) }
    val tabs = listOf("কিবলা কম্পাস (লাইভ)", "নামাজের সময়সূচি")

    val cities = PrayerTimeCalculator.CITIES
    val activeCity = remember(selectedCity) {
        cities.firstOrNull { it.nameEn.equals(selectedCity, ignoreCase = true) || it.nameBn == selectedCity }
            ?: cities[0]
    }

    // Permission launcher for location
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            useLiveGps = true
            isRefreshingLocation = true
            scope.launch {
                locationManager.refreshLocation {
                    isRefreshingLocation = false
                }
            }
        }
    }

    // Auto-check location permission on launch
    LaunchedEffect(Unit) {
        if (locationManager.hasLocationPermission()) {
            useLiveGps = true
            locationManager.refreshLocation()
        }
    }

    // Sensor lifecycle
    DisposableEffect(Unit) {
        compassManager.startListening()
        onDispose {
            compassManager.stopListening()
            locationManager.stopLocationUpdates()
        }
    }

    // Coordinates to use (Live GPS vs City)
    val effectiveLatitude = if (useLiveGps && liveLocationState.isLiveGps) {
        liveLocationState.latitude
    } else {
        activeCity.latitude
    }

    val effectiveLongitude = if (useLiveGps && liveLocationState.isLiveGps) {
        liveLocationState.longitude
    } else {
        activeCity.longitude
    }

    val effectiveTzOffset = if (useLiveGps && liveLocationState.isLiveGps) {
        liveLocationState.timeZoneOffsetHours
    } else {
        activeCity.timeZoneOffsetHours
    }

    // Qibla Bearing and Distance to Kaaba
    val qiblaBearing = remember(effectiveLatitude, effectiveLongitude) {
        PrayerTimeCalculator.calculateQiblaBearing(effectiveLatitude, effectiveLongitude)
    }

    val distanceToKaabaKm = remember(effectiveLatitude, effectiveLongitude) {
        PrayerTimeCalculator.calculateDistanceToKaaba(effectiveLatitude, effectiveLongitude)
    }

    // Exact Prayer times calculated dynamically
    val dynamicPrayerTimes = remember(effectiveLatitude, effectiveLongitude, effectiveTzOffset, isHanafiAsr, useLiveGps, selectedCalculationMethod) {
        if (useLiveGps && liveLocationState.isLiveGps) {
            PrayerTimeCalculator.calculatePrayerTimesForCoordinates(
                calendar = Calendar.getInstance(),
                latitude = effectiveLatitude,
                longitude = effectiveLongitude,
                timeZoneOffsetHours = effectiveTzOffset,
                isHanafiAsr = isHanafiAsr,
                calculationMethod = selectedCalculationMethod,
                locationNameBn = liveLocationState.locationNameBn,
                locationNameEn = liveLocationState.locationNameEn
            )
        } else {
            PrayerTimeCalculator.calculatePrayerTimes(
                location = activeCity,
                isHanafiAsr = isHanafiAsr,
                calculationMethod = selectedCalculationMethod
            )
        }
    }

    // Update alignment haptics
    LaunchedEffect(compassState.azimuth, qiblaBearing) {
        compassManager.checkAlignmentFeedback(qiblaBearing)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("qibla_screen")
    ) {
        // Tab selector
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

        // Live GPS / City Status Banner
        LocationStatusHeader(
            isLiveGps = useLiveGps && liveLocationState.isLiveGps,
            hasPermission = locationManager.hasLocationPermission(),
            locationName = if (useLiveGps && liveLocationState.isLiveGps) liveLocationState.locationNameBn else "${activeCity.nameBn} (${activeCity.nameEn})",
            latitude = effectiveLatitude,
            longitude = effectiveLongitude,
            accuracy = if (useLiveGps && liveLocationState.isLiveGps) liveLocationState.accuracy else 0f,
            isRefreshing = isRefreshingLocation,
            onRefreshLocation = {
                if (locationManager.hasLocationPermission()) {
                    useLiveGps = true
                    isRefreshingLocation = true
                    scope.launch {
                        locationManager.refreshLocation {
                            isRefreshingLocation = false
                        }
                    }
                } else {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            },
            onSwitchToCity = {
                useLiveGps = false
                locationManager.setFallbackCity(
                    cityNameBn = activeCity.nameBn,
                    cityNameEn = activeCity.nameEn,
                    latitude = activeCity.latitude,
                    longitude = activeCity.longitude,
                    tzOffset = activeCity.timeZoneOffsetHours
                )
            }
        )

        // Show City Selector Chips when not using live GPS or when user wants to change city
        AnimatedVisibility(visible = !useLiveGps || !liveLocationState.isLiveGps) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cities) { city ->
                    FilterChip(
                        selected = activeCity.nameEn == city.nameEn,
                        onClick = {
                            useLiveGps = false
                            onCitySelected(city)
                        },
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
        }

        when (selectedTab) {
            0 -> QiblaCompassLiveTab(
                qiblaBearing = qiblaBearing,
                distanceToKaabaKm = distanceToKaabaKm,
                compassData = compassState,
                locationName = if (useLiveGps && liveLocationState.isLiveGps) liveLocationState.locationNameBn else activeCity.nameBn,
                isLiveGps = useLiveGps && liveLocationState.isLiveGps,
                onManualAzimuthChange = { compassManager.setManualAzimuth(it) }
            )
            1 -> PrayerScheduleTab(
                prayerTimes = dynamicPrayerTimes,
                city = activeCity,
                isLiveGps = useLiveGps && liveLocationState.isLiveGps,
                locationDisplayName = if (useLiveGps && liveLocationState.isLiveGps) liveLocationState.locationNameBn else "${activeCity.nameBn} (${activeCity.country})",
                isHanafiAsr = isHanafiAsr,
                onToggleAsrMethod = onToggleAsrMethod,
                selectedMethod = selectedCalculationMethod,
                onSelectMethod = { selectedCalculationMethod = it },
                onPlayAudio = onPlayAudio
            )
        }
    }
}

@Composable
fun LocationStatusHeader(
    isLiveGps: Boolean,
    hasPermission: Boolean,
    locationName: String,
    latitude: Double,
    longitude: Double,
    accuracy: Float,
    isRefreshing: Boolean,
    onRefreshLocation: () -> Unit,
    onSwitchToCity: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isLiveGps) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(if (isLiveGps) DeepEmerald else MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isLiveGps) Icons.Default.MyLocation else Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = if (isLiveGps) RestrainedGold else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isLiveGps) "লাইভ জিপিএস লোকেশন" else "শহর নির্বাচন মোড",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = if (isLiveGps) DeepEmerald else MaterialTheme.colorScheme.primary
                        )
                        if (isLiveGps && accuracy > 0) {
                            Text(
                                text = " (±${PrayerTimeCalculator.toBengaliDigits(accuracy.toInt())} মি.)",
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Text(
                        text = locationName,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isRefreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                IconButton(
                    onClick = onRefreshLocation,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (hasPermission) Icons.Default.Refresh else Icons.Default.MyLocation,
                        contentDescription = "লোকেশন আপডেট করুন",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                if (isLiveGps) {
                    Text(
                        text = "শহর",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable { onSwitchToCity() }
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun QiblaCompassLiveTab(
    qiblaBearing: Double,
    distanceToKaabaKm: Double,
    compassData: com.example.util.CompassData,
    locationName: String,
    isLiveGps: Boolean,
    onManualAzimuthChange: (Float) -> Unit
) {
    val deviceAzimuth = compassData.azimuth
    val relativeAngle = (qiblaBearing.toFloat() - deviceAzimuth + 360f) % 360f
    val shortestDiff = if (relativeAngle > 180f) 360f - relativeAngle else relativeAngle
    val isAligned = shortestDiff <= 3.0f

    val animatedRelativeAngle by animateFloatAsState(
        targetValue = relativeAngle,
        animationSpec = spring(stiffness = 300f),
        label = "qibla_relative_rotation"
    )

    val animatedHeadingAngle by animateFloatAsState(
        targetValue = -deviceAzimuth,
        animationSpec = spring(stiffness = 300f),
        label = "compass_dial_rotation"
    )

    var showManualControls by remember { mutableStateOf(!compassData.hasSensors) }
    var manualSliderValue by remember { mutableFloatStateOf(deviceAzimuth) }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier
            .fillMaxSize()
            .testTag("qibla_compass_tab")
    ) {
        // Alignment Banner Card
        item {
            val bannerColor by animateColorAsState(
                targetValue = if (isAligned) DeepEmerald else MaterialTheme.colorScheme.surfaceVariant,
                label = "banner_color"
            )
            val textColor = if (isAligned) Color.White else MaterialTheme.colorScheme.onSurface

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = bannerColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(if (isAligned) 6.dp else 1.dp, RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (isAligned) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "সারিবদ্ধ",
                                    tint = RestrainedGold,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                            }
                            Text(
                                text = if (isAligned)
                                    "কিবলার সাথে সঠিকভাবে সারিবদ্ধ!"
                                else
                                    getDirectionalGuideText(relativeAngle),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = textColor
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isAligned)
                                "আপনি সরাসরি পবিত্র কাবা শরিফের অভিমুখী হয়েছেন (ভিব্রেশন ফিডব্যাক সক্রিয়)।"
                            else
                                "পবিত্র কাবা শরিফ উত্তর হতে ${PrayerTimeCalculator.toBengaliDigits(qiblaBearing, 1)}° কোণে অবস্থিত।",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isAligned) RestrainedGold else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${PrayerTimeCalculator.toBengaliDigits(qiblaBearing, 1)}°",
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (isAligned) RestrainedGold else MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "কিবলার দিক",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isAligned) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }

        // Tilt/Flat warning notice if phone is pitched vertically
        if (!compassData.isFlatEnough && compassData.hasSensors) {
            item {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.8f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "সঠিক কিবলা পেতে ফোনটিকে মাটির সমান্তরাল বা ফ্ল্যাট রাখুন।",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }

        // The Custom Compass Dial
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(280.dp)
                    .clip(CircleShape)
                    .background(DeepEmerald)
                    .border(
                        width = if (isAligned) 6.dp else 4.dp,
                        brush = if (isAligned)
                            Brush.sweepGradient(listOf(RestrainedGold, Color(0xFFFFE082), RestrainedGold))
                        else
                            Brush.radialGradient(listOf(RestrainedGold.copy(alpha = 0.7f), DeepEmerald)),
                        shape = CircleShape
                    )
            ) {
                // 1. Rotating Outer Rose Dial (Rotates opposite to device heading to keep North pointing up)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(animatedHeadingAngle),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val radius = size.minDimension / 2f
                        val center = Offset(size.width / 2f, size.height / 2f)

                        // 360 degree ticks
                        for (deg in 0 until 360 step 15) {
                            val rad = Math.toRadians(deg.toDouble())
                            val isCardinal = deg % 90 == 0
                            val isSemiCardinal = deg % 45 == 0 && !isCardinal
                            val tickLength = when {
                                isCardinal -> 22f
                                isSemiCardinal -> 14f
                                else -> 8f
                            }
                            val start = Offset(
                                (center.x + (radius - tickLength - 6f) * sin(rad)).toFloat(),
                                (center.y - (radius - tickLength - 6f) * cos(rad)).toFloat()
                            )
                            val end = Offset(
                                (center.x + (radius - 6f) * sin(rad)).toFloat(),
                                (center.y - (radius - 6f) * cos(rad)).toFloat()
                            )

                            drawLine(
                                color = when {
                                    deg == 0 -> Color(0xFFEF5350) // Red for North
                                    isCardinal -> Color(0xFFC5A253)
                                    else -> Color.White.copy(alpha = 0.35f)
                                },
                                start = start,
                                end = end,
                                strokeWidth = if (isCardinal) 3.5f else 1.5f,
                                cap = StrokeCap.Round
                            )
                        }

                        // Cardinal Direction Letters
                        // North triangle indicator
                        val northTip = Offset(center.x, 16f)
                        val northLeft = Offset(center.x - 10f, 32f)
                        val northRight = Offset(center.x + 10f, 32f)
                        val northPath = Path().apply {
                            moveTo(northTip.x, northTip.y)
                            lineTo(northLeft.x, northLeft.y)
                            lineTo(northRight.x, northRight.y)
                            close()
                        }
                        drawPath(northPath, color = Color(0xFFEF5350))
                    }

                    // Cardinal labels: উ (North), পূ (East), দ (South), প (West)
                    CardinalLabelsOverlay()
                }

                // 2. Rotating Qibla Needle (Pointing directly to Mecca relative to screen)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(animatedRelativeAngle),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        // Kaaba Icon indicator with alignment aura
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(if (isAligned) Color(0xFFC5A253) else Color(0x33C5A253))
                                .border(1.5.dp, RestrainedGold, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mosque,
                                contentDescription = "কাবা শরিফ",
                                tint = if (isAligned) DeepEmerald else RestrainedGold,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Gold pointer line
                        Box(
                            modifier = Modifier
                                .width(if (isAligned) 5.dp else 3.5.dp)
                                .height(56.dp)
                                .background(
                                    Brush.verticalGradient(listOf(RestrainedGold, RestrainedGold.copy(alpha = 0.2f))),
                                    RoundedCornerShape(3.dp)
                                )
                        )
                    }
                }

                // 3. Center Compass Hub & Degree Readout
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF072B20))
                        .border(2.dp, if (isAligned) RestrainedGold else Color(0x55C5A253), CircleShape)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${PrayerTimeCalculator.toBengaliDigits(deviceAzimuth.toInt())}°",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            color = Color.White
                        )
                        Text(
                            text = getCardinalDirectionBn(deviceAzimuth),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp
                            ),
                            color = RestrainedGold
                        )
                    }
                }
            }
        }

        // Telemetry Data Grid Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TelemetryCard(
                    title = "কিবলার কোণ",
                    value = "${PrayerTimeCalculator.toBengaliDigits(qiblaBearing, 1)}°",
                    subtitle = "উত্তর হতে ঘড়ির কাঁটায়",
                    icon = Icons.Default.Explore,
                    modifier = Modifier.weight(1f)
                )

                TelemetryCard(
                    title = "কাবার দূরত্ব",
                    value = "${PrayerTimeCalculator.toBengaliDigits(distanceToKaabaKm.toLong())} কিমি",
                    subtitle = "মক্কা মুকাররমা",
                    icon = Icons.Default.NearMe,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TelemetryCard(
                    title = "বর্তমান হেডিং",
                    value = "${PrayerTimeCalculator.toBengaliDigits(deviceAzimuth.toInt())}°",
                    subtitle = getCardinalDirectionBn(deviceAzimuth),
                    icon = Icons.Default.CompassCalibration,
                    modifier = Modifier.weight(1f)
                )

                TelemetryCard(
                    title = "সেন্সর স্ট্যাটাস",
                    value = compassData.accuracyLabelBn,
                    subtitle = compassData.sensorTypeName,
                    icon = Icons.Default.Vibration,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Figure-8 Calibration Guide
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = RestrainedGold,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "কম্পাস সেন্সর ক্যালিব্রেশন টিপস",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "সঠিক ও নিখুঁত ফলাফলের জন্য মোবাইলটিকে অনুভূমিকভাবে রেখে বাতাসে '8' আকারে ২–৩ বার ঘুরিয়ে সেন্সরটি ক্যালিব্রেট করুন। ধাতব বস্তু ও বৈদ্যুতিক যন্ত্রপাতি থেকে দূরে থাকুন।",
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Manual Angle Simulation Toggle (for emulators or manual inspection)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showManualControls = !showManualControls }
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "ম্যানুয়াল কম্পাস টেস্ট মোড",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = if (showManualControls) "লুকান" else "দেখান",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        if (showManualControls) {
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "হেডিং ম্যানুয়ালি টেস্ট করুন:",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${PrayerTimeCalculator.toBengaliDigits(manualSliderValue.toInt())}°",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Slider(
                            value = manualSliderValue,
                            onValueChange = {
                                manualSliderValue = it
                                onManualAzimuthChange(it)
                            },
                            valueRange = 0f..359f,
                            colors = SliderDefaults.colors(
                                thumbColor = RestrainedGold,
                                activeTrackColor = MaterialTheme.colorScheme.primary
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    manualSliderValue = qiblaBearing.toFloat()
                                    onManualAzimuthChange(manualSliderValue)
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("কিবলায় মিলান (${PrayerTimeCalculator.toBengaliDigits(qiblaBearing.toInt())}°)")
                            }
                            OutlinedButton(
                                onClick = {
                                    manualSliderValue = 0f
                                    onManualAzimuthChange(0f)
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("উত্তরে মিলান (০°)")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardinalLabelsOverlay() {
    Box(modifier = Modifier.fillMaxSize()) {
        // North (Top)
        Text(
            text = "উ (N)",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            ),
            color = Color(0xFFEF5350),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 34.dp)
        )
        // South (Bottom)
        Text(
            text = "দ (S)",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            ),
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
        // East (Right)
        Text(
            text = "পূ (E)",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            ),
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
        )
        // West (Left)
        Text(
            text = "প (W)",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            ),
            color = Color.White.copy(alpha = 0.8f),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 20.dp)
        )
    }
}

@Composable
fun TelemetryCard(
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = RestrainedGold,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.outline,
                maxLines = 1
            )
        }
    }
}

fun getDirectionalGuideText(relativeAngle: Float): String {
    val diff = if (relativeAngle > 180f) 360f - relativeAngle else relativeAngle
    if (diff <= 3.0f) {
        return "কিবলার অভিমুখী হয়েছেন"
    }
    return if (relativeAngle in 1f..180f) {
        "ডানে ${PrayerTimeCalculator.toBengaliDigits(diff.toInt())}° ঘুরুন"
    } else {
        "বায়ে ${PrayerTimeCalculator.toBengaliDigits(diff.toInt())}° ঘুরুন"
    }
}

fun getCardinalDirectionBn(azimuth: Float): String {
    val az = (azimuth + 360f) % 360f
    return when {
        az in 337.5f..360f || az in 0f..22.5f -> "উত্তর (N)"
        az in 22.5f..67.5f -> "উত্তর-পূর্ব (NE)"
        az in 67.5f..112.5f -> "পূর্ব (E)"
        az in 112.5f..157.5f -> "দক্ষিণ-পূর্ব (SE)"
        az in 157.5f..202.5f -> "দক্ষিণ (S)"
        az in 202.5f..247.5f -> "দক্ষিণ-পশ্চিম (SW)"
        az in 247.5f..292.5f -> "পশ্চিম (W)"
        az in 292.5f..337.5f -> "উত্তর-পশ্চিম (NW)"
        else -> "উত্তর (N)"
    }
}

@Composable
fun PrayerScheduleTab(
    prayerTimes: PrayerTimes,
    city: CityLocation,
    isLiveGps: Boolean,
    locationDisplayName: String,
    isHanafiAsr: Boolean,
    onToggleAsrMethod: (Boolean) -> Unit,
    selectedMethod: CalculationMethod,
    onSelectMethod: (CalculationMethod) -> Unit,
    onPlayAudio: (url: String, trackTitle: String) -> Unit
) {
    val context = LocalContext.current
    val (nextPrayerType, remainingMinutes) = remember(prayerTimes) {
        PrayerTimeCalculator.getNextPrayerAndRemainingMinutes(prayerTimes)
    }

    val remainingHours = remainingMinutes / 60
    val remainingMins = remainingMinutes % 60
    val countdownText = if (remainingHours > 0) {
        "${PrayerTimeCalculator.toBengaliDigits(remainingHours)} ঘণ্টা ${PrayerTimeCalculator.toBengaliDigits(remainingMins)} মিনিট বাকি"
    } else {
        "${PrayerTimeCalculator.toBengaliDigits(remainingMins)} মিনিট বাকি"
    }

    val nextPrayerNameBn = when (nextPrayerType) {
        PrayerType.FAJR -> "ফজর (Fajr)"
        PrayerType.SUNRISE -> "সূর্যোদয় (Sunrise)"
        PrayerType.DHUHR -> "জোহর (Dhuhr)"
        PrayerType.ASR -> "আসর (Asr)"
        PrayerType.MAGHRIB -> "মাগরিব (Maghrib)"
        PrayerType.ISHA -> "এশা (Isha)"
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize().testTag("prayer_schedule_tab")
    ) {
        // Location & Date Header Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DeepEmerald),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = locationDisplayName,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        if (isLiveGps) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(RestrainedGold.copy(alpha = 0.25f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "লাইভ জিপিএস",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = RestrainedGold
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "তারিখ: ${prayerTimes.dateFormatted} • ${prayerTimes.hijriDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = RestrainedGold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "পদ্ধতি: ${selectedMethod.nameBn}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }

        // Live Next Prayer Countdown Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Mosque,
                                contentDescription = null,
                                tint = DeepEmerald,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "পরবর্তী সালাত: $nextPrayerNameBn",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = DeepEmerald
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = countdownText,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Schedule Alarms Action Button
                    IconButton(
                        onClick = {
                            val notifManager = PrayerNotificationManager(context)
                            notifManager.scheduleAllDailyPrayers(prayerTimes, locationDisplayName)
                            Toast.makeText(
                                context,
                                "সকল ওয়াক্তের আজান ও অ্যালার্ম সফলভাবে শিডিউল করা হয়েছে।",
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(DeepEmerald)
                    ) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = "সকল আজান অ্যালার্ম শিডিউল করুন",
                            tint = RestrainedGold,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        // Calculation Method Selector
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "গণনা পদ্ধতি (Calculation Convention)",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(CalculationMethod.values()) { method ->
                            FilterChip(
                                selected = selectedMethod == method,
                                onClick = { onSelectMethod(method) },
                                label = {
                                    Text(
                                        text = method.nameEn,
                                        style = MaterialTheme.typography.labelSmall
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

        // Daily Prayers Table with Distinct Audio Preview Playback
        item {
            val mapping = SalahAudioRegistry.getAudioMappingForPrayer(PrayerType.FAJR)
            PrayerTimeRow(
                title = "ফজর (Fajr)",
                subtitle = "সুবহে সাদিক হতে সূর্যোদয়ের পূর্বে • আজানে 'আস-সালাতু খাইরুম মিনান নাওম' পাঠ",
                time = prayerTimes.fajr,
                icon = Icons.Default.Mosque,
                onPlayAdhan = { onPlayAudio(mapping.audioUrl, "${mapping.nameBn} - ${mapping.adhanStyleBn}") }
            )
        }
        item {
            val mapping = SalahAudioRegistry.getAudioMappingForPrayer(PrayerType.SUNRISE)
            PrayerTimeRow(
                title = "সূর্যোদয় (Sunrise)",
                subtitle = "ইশরাকের ওয়াক্ত শুরু • তেলাওয়াত শুনুন",
                time = prayerTimes.sunrise,
                icon = Icons.Default.LocationOn,
                isSun = true,
                onPlayAdhan = { onPlayAudio(mapping.audioUrl, "${mapping.nameBn} - ${mapping.adhanStyleBn}") }
            )
        }
        item {
            val mapping = SalahAudioRegistry.getAudioMappingForPrayer(PrayerType.DHUHR)
            PrayerTimeRow(
                title = "জোহর (Dhuhr)",
                subtitle = "সূর্য মধ্যাহ্ন ঢলে পড়ার পর থেকে • মক্কা হারাম আজান",
                time = prayerTimes.dhuhr,
                icon = Icons.Default.Mosque,
                onPlayAdhan = { onPlayAudio(mapping.audioUrl, "${mapping.nameBn} - ${mapping.adhanStyleBn}") }
            )
        }
        item {
            val mapping = SalahAudioRegistry.getAudioMappingForPrayer(PrayerType.ASR)
            PrayerTimeRow(
                title = "আসর (Asr)",
                subtitle = "${prayerTimes.asrJuristicMethod} • মসজিদে নববীর আজান",
                time = prayerTimes.asr,
                icon = Icons.Default.Mosque,
                onPlayAdhan = { onPlayAudio(mapping.audioUrl, "${mapping.nameBn} - ${mapping.adhanStyleBn}") }
            )
        }
        item {
            val mapping = SalahAudioRegistry.getAudioMappingForPrayer(PrayerType.MAGHRIB)
            PrayerTimeRow(
                title = "মাগরিব (Maghrib)",
                subtitle = "সূর্যাস্ত ও ইফতারের সঠিক সময় • আল-আকসা আজান",
                time = prayerTimes.maghrib,
                icon = Icons.Default.Mosque,
                onPlayAdhan = { onPlayAudio(mapping.audioUrl, "${mapping.nameBn} - ${mapping.adhanStyleBn}") }
            )
        }
        item {
            val mapping = SalahAudioRegistry.getAudioMappingForPrayer(PrayerType.ISHA)
            PrayerTimeRow(
                title = "এশা (Isha)",
                subtitle = "রাতের সালাত ও বিতর ওয়াজিব • শাইখ আল-আফাসী আজান",
                time = prayerTimes.isha,
                icon = Icons.Default.Mosque,
                onPlayAdhan = { onPlayAudio(mapping.audioUrl, "${mapping.nameBn} - ${mapping.adhanStyleBn}") }
            )
        }
    }
}

@Composable
fun PrayerTimeRow(
    title: String,
    subtitle: String,
    time: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSun: Boolean = false,
    onPlayAdhan: (() -> Unit)? = null
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
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
                Column(modifier = Modifier.weight(1f)) {
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = time,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = if (isSun) RestrainedGold else MaterialTheme.colorScheme.primary
                )

                if (onPlayAdhan != null) {
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = onPlayAdhan,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "আজান শুনুন",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
