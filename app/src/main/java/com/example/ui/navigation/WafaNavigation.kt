package com.example.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val titleBn: String, val titleEn: String, val icon: ImageVector) {
    object Home : Screen("home", "হোম", "Home", Icons.Default.Home)
    object Salah : Screen("salah", "নামাজ শিক্ষা", "Learn Salah", Icons.Default.Mosque)
    object Quran : Screen("quran", "আল-কুরআন", "Quran", Icons.AutoMirrored.Filled.MenuBook)
    object SurahDetail : Screen("surah_detail/{surahNumber}", "সূরা বিবরণ", "Surah Detail", Icons.Default.MenuBook) {
        fun createRoute(surahNumber: Int) = "surah_detail/$surahNumber"
    }
    object Hadith : Screen("hadith", "সহিহ হাদিস", "Hadith", Icons.Default.MenuBook)
    object Duas : Screen("duas", "দোয়া ও জিকির", "Duas", Icons.Default.PanTool)
    object Tasbih : Screen("tasbih", "ডিজিটাল তাসবীহ", "Tasbih", Icons.Default.RadioButtonChecked)
    object Ramadan : Screen("ramadan", "রমজান ও ক্যালেন্ডার", "Ramadan", Icons.Default.CalendarMonth)
    object Qibla : Screen("qibla", "কিবলা ও নামাজের সময়", "Qibla & Times", Icons.Default.CompassCalibration)
    object Bookmarks : Screen("bookmarks", "সংরক্ষিত", "Bookmarks", Icons.Default.Bookmark)
    object Settings : Screen("settings", "সেটিংস", "Settings", Icons.Default.Settings)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Salah,
    Screen.Quran,
    Screen.Hadith,
    Screen.Tasbih
)
