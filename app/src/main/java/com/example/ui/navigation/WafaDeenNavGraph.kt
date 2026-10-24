package com.example.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.audio.AudioPlayerManager
import com.example.data.local.BookmarkDao
import com.example.data.local.PreferencesManager
import com.example.data.local.TasbihDao
import com.example.data.model.CityLocation
import com.example.repository.QuranRepository
import com.example.ui.components.AudioPlayerBar
import com.example.ui.components.WafaTopAppBar
import com.example.ui.screens.bookmarks.BookmarksScreen
import com.example.ui.screens.duas.DuasScreen
import com.example.ui.screens.hadith.HadithScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.qibla.QiblaPrayerScreen
import com.example.ui.screens.quran.QuranScreen
import com.example.ui.screens.quran.SurahDetailScreen
import com.example.ui.screens.ramadan.RamadanScreen
import com.example.ui.screens.salah.LearnSalahScreen
import com.example.ui.screens.settings.SettingsScreen
import com.example.ui.screens.tasbih.TasbihScreen
import com.example.ui.theme.DeepEmerald
import com.example.ui.theme.RestrainedGold
import com.example.util.PrayerTimeCalculator

@Composable
fun WafaDeenApp(
    bookmarkDao: BookmarkDao,
    tasbihDao: TasbihDao,
    preferencesManager: PreferencesManager,
    audioPlayerManager: AudioPlayerManager
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val playbackState by audioPlayerManager.playbackState.collectAsState()
    val selectedCityName by preferencesManager.cityFlow.collectAsState(initial = "Dhaka")
    val asrMethodName by preferencesManager.asrMethodFlow.collectAsState(initial = "Hanafi")
    val isHanafi = asrMethodName == "Hanafi"

    val activeCity = remember(selectedCityName) {
        PrayerTimeCalculator.CITIES.firstOrNull { it.nameEn.equals(selectedCityName, ignoreCase = true) }
            ?: PrayerTimeCalculator.CITIES[0]
    }

    val prayerTimes = remember(activeCity, isHanafi) {
        PrayerTimeCalculator.calculatePrayerTimes(
            location = activeCity,
            isHanafiAsr = isHanafi
        )
    }

    val title = when {
        currentRoute == Screen.Home.route -> "Wafa Deen"
        currentRoute == Screen.Salah.route -> "নামাজ শিক্ষা"
        currentRoute == Screen.Quran.route -> "আল-কুরআন"
        currentRoute?.startsWith("surah_detail") == true -> "পবিত্র কুরআন তেলাওয়াত"
        currentRoute == Screen.Hadith.route -> "সহিহ হাদিস"
        currentRoute == Screen.Duas.route -> "দোয়া ও জিকির"
        currentRoute == Screen.Tasbih.route -> "ডিজিটাল তাসবীহ"
        currentRoute == Screen.Ramadan.route -> "রমজান ও ক্যালেন্ডার"
        currentRoute == Screen.Qibla.route -> "কিবলা ও নামাজের ওয়াক্ত"
        currentRoute == Screen.Bookmarks.route -> "সংরক্ষিত আমল"
        currentRoute == Screen.Settings.route -> "সেটিংস ও যাচাইকরণ"
        else -> "Wafa Deen"
    }

    val isTopLevel = currentRoute in listOf(
        Screen.Home.route,
        Screen.Salah.route,
        Screen.Quran.route,
        Screen.Hadith.route,
        Screen.Tasbih.route
    )

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isWideScreen = maxWidth > 600.dp

        Row(modifier = Modifier.fillMaxSize()) {
            if (isWideScreen) {
                NavigationRail(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.primary,
                    header = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mosque,
                                contentDescription = "Wafa Deen",
                                tint = RestrainedGold,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Wafa Deen",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    modifier = Modifier.fillMaxHeight()
                ) {
                    bottomNavItems.forEach { screen ->
                        NavigationRailItem(
                            selected = currentRoute == screen.route,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo(Screen.Home.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = { Icon(imageVector = screen.icon, contentDescription = screen.titleBn) },
                            label = { Text(screen.titleBn) },
                            colors = NavigationRailItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                indicatorColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    NavigationRailItem(
                        selected = currentRoute == Screen.Settings.route,
                        onClick = {
                            if (currentRoute != Screen.Settings.route) {
                                navController.navigate(Screen.Settings.route)
                            }
                        },
                        icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "সেটিংস") },
                        label = { Text("সেটিংস") }
                    )
                }
            }

            Scaffold(
                topBar = {
                    WafaTopAppBar(
                        title = title,
                        onNavigateBack = if (!isTopLevel) {
                            { navController.navigateUp() }
                        } else null,
                        onLanguageClick = {
                            navController.navigate(Screen.Settings.route)
                        }
                    )
                },
                bottomBar = {
                    Column(modifier = Modifier.navigationBarsPadding()) {
                        AudioPlayerBar(
                            state = playbackState,
                            onPlayPause = {
                                if (playbackState.isPlaying) audioPlayerManager.pause()
                                else audioPlayerManager.resume()
                            },
                            onSeek = { audioPlayerManager.seekTo(it) },
                            onToggleRepeat = { audioPlayerManager.toggleRepeat() },
                            onStop = { audioPlayerManager.stop() },
                            onDismissError = { audioPlayerManager.clearError() }
                        )

                        if (!isWideScreen) {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.border(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                                )
                            ) {
                                bottomNavItems.forEach { screen ->
                                    NavigationBarItem(
                                        selected = currentRoute == screen.route,
                                        onClick = {
                                            if (currentRoute != screen.route) {
                                                navController.navigate(screen.route) {
                                                    popUpTo(Screen.Home.route) { saveState = true }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = screen.icon,
                                                contentDescription = screen.titleBn
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = screen.titleBn,
                                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                                fontWeight = if (currentRoute == screen.route) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                                            indicatorColor = MaterialTheme.colorScheme.primary,
                                            selectedTextColor = MaterialTheme.colorScheme.primary
                                        ),
                                        modifier = Modifier.testTag("nav_${screen.route}")
                                    )
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            prayerTimes = prayerTimes,
                            selectedCity = activeCity.nameBn,
                            onNavigate = { route -> navController.navigate(route) }
                        )
                    }

                    composable(Screen.Salah.route) {
                        LearnSalahScreen(
                            onPlayAudio = { url, trackTitle ->
                                audioPlayerManager.play(url, trackTitle)
                            }
                        )
                    }

                    composable(Screen.Quran.route) {
                        QuranScreen(
                            bookmarkDao = bookmarkDao,
                            onSurahClick = { surahNumber ->
                                navController.navigate(Screen.SurahDetail.createRoute(surahNumber))
                            },
                            onPlaySurahAudio = { url, trackTitle ->
                                audioPlayerManager.play(url, trackTitle)
                            }
                        )
                    }

                    composable(
                        route = Screen.SurahDetail.route,
                        arguments = listOf(navArgument("surahNumber") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val surahNumber = backStackEntry.arguments?.getInt("surahNumber") ?: 1
                        SurahDetailScreen(
                            surahNumber = surahNumber,
                            bookmarkDao = bookmarkDao,
                            onPlayAudio = { url, trackTitle ->
                                audioPlayerManager.play(url, trackTitle)
                            }
                        )
                    }

                    composable(Screen.Hadith.route) {
                        HadithScreen(bookmarkDao = bookmarkDao)
                    }

                    composable(Screen.Duas.route) {
                        DuasScreen(bookmarkDao = bookmarkDao)
                    }

                    composable(Screen.Tasbih.route) {
                        TasbihScreen(tasbihDao = tasbihDao)
                    }

                    composable(Screen.Ramadan.route) {
                        RamadanScreen(
                            prayerTimes = prayerTimes,
                            selectedCity = activeCity.nameBn
                        )
                    }

                    composable(Screen.Qibla.route) {
                        QiblaPrayerScreen(
                            currentPrayerTimes = prayerTimes,
                            selectedCity = activeCity.nameEn,
                            onCitySelected = { city ->
                                scope.launch {
                                    preferencesManager.setCity(city.nameEn)
                                }
                            },
                            isHanafiAsr = isHanafi,
                            onToggleAsrMethod = { isHanafiSelected ->
                                scope.launch {
                                    preferencesManager.setAsrMethod(if (isHanafiSelected) "Hanafi" else "Standard")
                                }
                            }
                        )
                    }

                    composable(Screen.Bookmarks.route) {
                        BookmarksScreen(bookmarkDao = bookmarkDao)
                    }

                    composable(Screen.Settings.route) {
                        SettingsScreen(preferencesManager = preferencesManager)
                    }
                }
            }
        }
    }
}
