package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.CityLocation
import com.example.repository.HadithRepository
import com.example.repository.QuranRepository
import com.example.util.PrayerTimeCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Calendar

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `verify app name resource matches Wafa Deen`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Wafa Deen", appName)
  }

  @Test
  fun `verify quran repository has 114 surahs`() {
    val surahs = QuranRepository.allSurahs
    assertEquals(114, surahs.size)
    assertEquals(1, surahs.first().number)
    assertEquals(114, surahs.last().number)
    assertEquals("الفاتحة", surahs.first().nameAr)
    assertEquals("الناس", surahs.last().nameAr)
  }

  @Test
  fun `verify surah al fatihah has 7 ayahs`() {
    val ayahs = QuranRepository.getAyahsForSurah(1)
    assertEquals(7, ayahs.size)
    assertEquals(1, ayahs.first().ayahNumber)
    assertEquals(7, ayahs.last().ayahNumber)
    assertTrue(ayahs.first().arabicText.contains("بِسْمِ اللَّهِ"))
  }

  @Test
  fun `verify hadith repository search finds hadith by keyword and number`() {
    val resultByKeyword = HadithRepository.searchHadiths("নিয়ত")
    assertTrue(resultByKeyword.isNotEmpty())
    assertEquals(1, resultByKeyword.first().hadithNumber)

    val resultByNumber = HadithRepository.searchHadiths("8")
    assertTrue(resultByNumber.isNotEmpty())
    assertTrue(resultByNumber.any { it.hadithNumber == 8 })
  }

  @Test
  fun `verify qibla bearing calculation for dhaka`() {
    // Dhaka coordinates: Lat 23.8103, Lon 90.4125
    // Bearing to Mecca (21.4225, 39.8262) is approximately 277° (West-North-West)
    val bearing = PrayerTimeCalculator.calculateQiblaBearing(23.8103, 90.4125)
    assertTrue("Bearing should be approx 277 degrees", bearing in 270.0..285.0)
  }

  @Test
  fun `verify prayer times calculation generates valid times`() {
    val dhaka = CityLocation("ঢাকা", "Dhaka", 23.8103, 90.4125, 6.0, "Bangladesh")
    val cal = Calendar.getInstance().apply {
      set(Calendar.YEAR, 2026)
      set(Calendar.MONTH, Calendar.OCTOBER)
      set(Calendar.DAY_OF_MONTH, 23)
    }

    val times = PrayerTimeCalculator.calculatePrayerTimes(cal, dhaka, isHanafiAsr = true)
    assertNotNull(times.fajr)
    assertNotNull(times.sunrise)
    assertNotNull(times.dhuhr)
    assertNotNull(times.asr)
    assertNotNull(times.maghrib)
    assertNotNull(times.isha)

    assertTrue(times.fajr.contains("AM"))
    assertTrue(times.dhuhr.contains("PM") || times.dhuhr.contains("AM"))
    assertTrue(times.maghrib.contains("PM"))
  }
}
