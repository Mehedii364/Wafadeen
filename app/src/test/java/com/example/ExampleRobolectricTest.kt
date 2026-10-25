package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.CityLocation
import com.example.repository.HadithRepository
import com.example.repository.QuranRepository
import androidx.room.Room
import com.example.data.local.BookmarkEntity
import com.example.data.local.WafaDeenDatabase
import com.example.data.model.CalculationMethod
import com.example.data.model.HighLatitudeRule
import com.example.data.model.NisabType
import com.example.data.model.PrayerType
import com.example.data.model.ZakatInput
import com.example.service.SalahAudioRegistry
import com.example.util.PrayerTimeCalculator
import com.example.util.ZakatCalculator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.text.SimpleDateFormat
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
  fun `verify ayah search finds matching verses by bengali translation and arabic`() {
    val resultsBengali = QuranRepository.searchAyahs("আল্লাহ")
    assertTrue(resultsBengali.isNotEmpty())
    assertTrue(resultsBengali.any { it.second.translationBn.contains("আল্লাহ") })

    val resultsArabic = QuranRepository.searchAyahs("الرَّحْمَٰنِ")
    assertTrue(resultsArabic.isNotEmpty())

    val resultsSalat = QuranRepository.searchAyahs("সালাত")
    assertTrue(resultsSalat.isNotEmpty())
  }

  @Test
  fun `verify get all available ayahs returns verses across multiple surahs`() {
    val allAyahs = QuranRepository.getAllAvailableAyahs()
    assertTrue(allAyahs.size >= 50)
    assertTrue(allAyahs.any { it.first.number == 1 })
    assertTrue(allAyahs.any { it.first.number == 112 })
    assertTrue(allAyahs.any { it.first.number == 2 })
    assertTrue(allAyahs.any { it.first.number == 67 })
  }

  @Test
  fun `verify hadith repository contains exactly 200 authentic Bukhari hadiths`() {
    val hadiths = HadithRepository.hadiths
    assertEquals(200, hadiths.size)

    val uniqueIds = hadiths.map { it.id }.distinct()
    assertEquals("All 200 hadiths must have unique IDs", 200, uniqueIds.size)

    hadiths.forEach { hadith ->
      assertTrue("ID must not be blank for hadith #${hadith.hadithNumber}", hadith.id.isNotBlank())
      assertTrue("Hadith number must be positive for ${hadith.id}", hadith.hadithNumber > 0)
      assertTrue("Arabic Matn must not be blank for ${hadith.id}", hadith.arabicMatn.isNotBlank())
      assertTrue("Bangla translation must not be blank for ${hadith.id}", hadith.banglaTranslation.isNotBlank())
      assertTrue("Bangla transliteration must not be blank for ${hadith.id}", hadith.banglaTransliteration.isNotBlank())
      assertTrue("Topic must not be blank for ${hadith.id}", hadith.topic.isNotBlank())
      assertTrue("Book name Bn must not be blank for ${hadith.id}", hadith.bookNameBn.isNotBlank())
      assertTrue("Narrator Bn must not be blank for ${hadith.id}", hadith.narratorBn.isNotBlank())
      assertTrue("Source URL must point to sunnah.com/bukhari for ${hadith.id}", hadith.sourceUrl.contains("sunnah.com/bukhari"))
      assertEquals("Verification status must be VERIFIED for ${hadith.id}", com.example.data.model.VerificationStatus.VERIFIED, hadith.verificationStatus)
    }
  }

  @Test
  fun `verify hadith repository topics and daily hadith selection`() {
    val topics = HadithRepository.allTopics
    assertTrue(topics.contains("সকল"))
    assertTrue(topics.size >= 15)
    assertTrue(topics.contains("ঈমান ও আকিদা"))
    assertTrue(topics.contains("সালাত ও নামাজ"))
    assertTrue(topics.contains("রোজা ও রমজান"))
    assertTrue(topics.contains("যাকাত ও সদকা"))

    val imanHadiths = HadithRepository.getHadithsByTopic("ঈমান ও আকিদা")
    assertTrue("Should have multiple Iman hadiths", imanHadiths.size >= 5)
    assertTrue(imanHadiths.all { it.topic == "ঈমান ও আকিদা" })

    val dailyHadith = HadithRepository.getDailyHadith()
    assertNotNull(dailyHadith)
    assertTrue(dailyHadith.hadithNumber > 0)
    assertTrue(dailyHadith.arabicMatn.isNotBlank())
  }

  @Test
  fun `verify hadith repository search finds hadith by keyword, arabic, number and topic`() {
    val resultByKeyword = HadithRepository.searchHadiths("নিয়ত")
    assertTrue(resultByKeyword.isNotEmpty())
    assertEquals(1, resultByKeyword.first().hadithNumber)

    val resultByNumber = HadithRepository.searchHadiths("8")
    assertTrue(resultByNumber.isNotEmpty())
    assertTrue(resultByNumber.any { it.hadithNumber == 8 })

    val resultByArabic = HadithRepository.searchHadiths("النِّيَّاتِ")
    assertTrue(resultByArabic.isNotEmpty())

    val resultByTopicFilter = HadithRepository.searchHadiths("", selectedTopic = "সালাত ও নামাজ")
    assertTrue(resultByTopicFilter.isNotEmpty())
    assertTrue(resultByTopicFilter.all { it.topic == "সালাত ও নামাজ" })
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

  @Test
  fun `verify distance to kaaba calculation`() {
    // Dhaka to Kaaba distance is approx 5,160 km
    val distDhaka = PrayerTimeCalculator.calculateDistanceToKaaba(23.8103, 90.4125)
    assertTrue("Distance from Dhaka should be ~5,160 km", distDhaka in 4900.0..5400.0)

    // Distance from Kaaba to Kaaba should be ~0 km
    val distKaaba = PrayerTimeCalculator.calculateDistanceToKaaba(21.4225, 39.8262)
    assertTrue("Distance from Kaaba to itself should be close to 0", distKaaba < 5.0)
  }

  @Test
  fun `verify prayer times for arbitrary live coordinates`() {
    val cal = Calendar.getInstance().apply {
      set(Calendar.YEAR, 2026)
      set(Calendar.MONTH, Calendar.OCTOBER)
      set(Calendar.DAY_OF_MONTH, 23)
    }

    // Chittagong coordinates (Lat 22.3569, Lng 91.7832, UTC+6)
    val times = PrayerTimeCalculator.calculatePrayerTimesForCoordinates(
      calendar = cal,
      latitude = 22.3569,
      longitude = 91.7832,
      timeZoneOffsetHours = 6.0,
      isHanafiAsr = true
    )

    assertNotNull(times.fajr)
    assertNotNull(times.dhuhr)
    assertNotNull(times.asr)
    assertNotNull(times.maghrib)
    assertNotNull(times.isha)
  }

  @Test
  fun `verify bengali digits conversion`() {
    val bnDigits = PrayerTimeCalculator.toBengaliDigits("2026-10-23 12:45")
    assertEquals("২০২৬-১০-২৩ ১২:৪৫", bnDigits)
    assertEquals("৫,১৮২", PrayerTimeCalculator.toBengaliDigits("5,182"))
  }

  @Test
  fun `verify all five daily prayers and sunrise have distinct audio mappings in SalahAudioRegistry`() {
    assertTrue(SalahAudioRegistry.verifyDistinctAudioMappings())

    val mappings = SalahAudioRegistry.audioMappings
    assertEquals(6, mappings.size)

    val audioIds = mappings.values.map { it.audioId }.toSet()
    val audioUrls = mappings.values.map { it.audioUrl }.toSet()
    val channels = mappings.values.map { it.channelId }.toSet()

    assertEquals(6, audioIds.size)
    assertEquals(6, audioUrls.size)
    assertEquals(6, channels.size)

    assertEquals("audio_adhan_fajr_alafasy", mappings[PrayerType.FAJR]?.audioId)
    assertEquals("audio_adhan_makkah_haram", mappings[PrayerType.DHUHR]?.audioId)
    assertEquals("audio_adhan_madinah_nabawi", mappings[PrayerType.ASR]?.audioId)
    assertEquals("audio_adhan_alaqsa_basit", mappings[PrayerType.MAGHRIB]?.audioId)
    assertEquals("audio_adhan_kuwait_alafasy", mappings[PrayerType.ISHA]?.audioId)
  }

  @Test
  fun `verify Fajr audio mapping has As-Salatu Khayrum Minan-Nawm special phrase flag`() {
    val fajr = SalahAudioRegistry.getAudioMappingForPrayer(PrayerType.FAJR)
    assertTrue(fajr.hasSpecialFajrPhrase)
    assertTrue(fajr.adhanStyleBn.contains("আস-সালাতু খাইরুম মিনান নাওম"))

    assertFalse(SalahAudioRegistry.getAudioMappingForPrayer(PrayerType.DHUHR).hasSpecialFajrPhrase)
    assertFalse(SalahAudioRegistry.getAudioMappingForPrayer(PrayerType.ASR).hasSpecialFajrPhrase)
    assertFalse(SalahAudioRegistry.getAudioMappingForPrayer(PrayerType.MAGHRIB).hasSpecialFajrPhrase)
    assertFalse(SalahAudioRegistry.getAudioMappingForPrayer(PrayerType.ISHA).hasSpecialFajrPhrase)
  }

  @Test
  fun `verify all calculation conventions produce valid prayer times`() {
    val dhaka = CityLocation("ঢাকা", "Dhaka", 23.8103, 90.4125, 6.0, "Bangladesh")
    val cal = Calendar.getInstance().apply {
      set(Calendar.YEAR, 2026)
      set(Calendar.MONTH, Calendar.OCTOBER)
      set(Calendar.DAY_OF_MONTH, 24)
    }

    val methods = listOf(
      CalculationMethod.KARACHI_IFB,
      CalculationMethod.MWL,
      CalculationMethod.ISNA,
      CalculationMethod.UMM_AL_QURA,
      CalculationMethod.EGYPTIAN
    )

    for (method in methods) {
      val times = PrayerTimeCalculator.calculatePrayerTimes(
        calendar = cal,
        location = dhaka,
        isHanafiAsr = true,
        calculationMethod = method
      )
      assertNotNull("Fajr should not be null for $method", times.fajr)
      assertNotNull("Dhuhr should not be null for $method", times.dhuhr)
      assertNotNull("Asr should not be null for $method", times.asr)
      assertNotNull("Maghrib should not be null for $method", times.maghrib)
      assertNotNull("Isha should not be null for $method", times.isha)
      assertEquals(method.nameBn, times.calculationMethod)
    }
  }

  @Test
  fun `verify Hanafi Asr is later than Shafii Asr for identical coordinates`() {
    val dhaka = CityLocation("ঢাকা", "Dhaka", 23.8103, 90.4125, 6.0, "Bangladesh")
    val cal = Calendar.getInstance().apply {
      set(Calendar.YEAR, 2026)
      set(Calendar.MONTH, Calendar.OCTOBER)
      set(Calendar.DAY_OF_MONTH, 24)
    }

    val timesHanafi = PrayerTimeCalculator.calculatePrayerTimes(cal, dhaka, isHanafiAsr = true)
    val timesShafii = PrayerTimeCalculator.calculatePrayerTimes(cal, dhaka, isHanafiAsr = false)

    val format = SimpleDateFormat("hh:mm a", java.util.Locale.US)
    val hanafiTime = format.parse(timesHanafi.asr)
    val shafiiTime = format.parse(timesShafii.asr)

    assertNotNull(hanafiTime)
    assertNotNull(shafiiTime)
    assertTrue(
      "Hanafi Asr (${timesHanafi.asr}) should be strictly after Shafi'i Asr (${timesShafii.asr})",
      hanafiTime!!.after(shafiiTime)
    )
  }

  @Test
  fun `verify high-latitude coordinates calculate safely without NaN or crash`() {
    val cal = Calendar.getInstance().apply {
      set(Calendar.YEAR, 2026)
      set(Calendar.MONTH, Calendar.JUNE)
      set(Calendar.DAY_OF_MONTH, 21)
    }

    // Oslo, Norway (59.91° N)
    val osloTimes = PrayerTimeCalculator.calculatePrayerTimesForCoordinates(
      calendar = cal,
      latitude = 59.9139,
      longitude = 10.7522,
      timeZoneOffsetHours = 2.0,
      highLatitudeRule = HighLatitudeRule.ANGLE_BASED
    )
    assertNotNull(osloTimes.fajr)
    assertNotNull(osloTimes.isha)
    assertFalse(osloTimes.fajr.contains("NaN"))
    assertFalse(osloTimes.isha.contains("NaN"))

    // Tromso, Norway (69.65° N)
    val tromsoTimes = PrayerTimeCalculator.calculatePrayerTimesForCoordinates(
      calendar = cal,
      latitude = 69.6492,
      longitude = 18.9553,
      timeZoneOffsetHours = 2.0,
      highLatitudeRule = HighLatitudeRule.ONE_SEVENTH
    )
    assertNotNull(tromsoTimes.fajr)
    assertNotNull(tromsoTimes.isha)
    assertFalse(tromsoTimes.fajr.contains("NaN"))
  }

  @Test
  fun `verify next prayer determination and remaining minutes`() {
    val dhaka = CityLocation("ঢাকা", "Dhaka", 23.8103, 90.4125, 6.0, "Bangladesh")
    val cal = Calendar.getInstance().apply {
      set(Calendar.YEAR, 2026)
      set(Calendar.MONTH, Calendar.OCTOBER)
      set(Calendar.DAY_OF_MONTH, 24)
    }
    val times = PrayerTimeCalculator.calculatePrayerTimes(cal, dhaka)

    // Mock time: 02:00 AM -> Next prayer must be Fajr
    val earlyCal = Calendar.getInstance().apply {
      set(Calendar.HOUR_OF_DAY, 2)
      set(Calendar.MINUTE, 0)
    }
    val (nextPrayer, remaining) = PrayerTimeCalculator.getNextPrayerAndRemainingMinutes(times, earlyCal)
    assertEquals(PrayerType.FAJR, nextPrayer)
    assertTrue("Remaining minutes should be positive", remaining > 0)
  }

  @Test
  fun `verify Zakat calculator calculations below and above silver nisab threshold`() {
    val resultBelow = ZakatCalculator.calculate(
      cash = 50000.0,
      goldVal = 0.0,
      silverVal = 0.0,
      merchandise = 0.0,
      receivables = 0.0,
      debts = 0.0,
      silverGramPrice = 120.0,
      nisabType = NisabType.SILVER
    )
    assertEquals(71400.0, resultBelow.nisabThreshold, 0.01)
    assertEquals(50000.0, resultBelow.netWealth, 0.01)
    assertFalse(resultBelow.isZakatObligatory)
    assertEquals(0.0, resultBelow.zakatPayable, 0.01)

    val resultAbove = ZakatCalculator.calculate(
      cash = 100000.0,
      goldVal = 0.0,
      silverVal = 0.0,
      merchandise = 0.0,
      receivables = 0.0,
      debts = 0.0,
      silverGramPrice = 120.0,
      nisabType = NisabType.SILVER
    )
    assertTrue(resultAbove.isZakatObligatory)
    assertEquals(2500.0, resultAbove.zakatPayable, 0.01)
  }

  @Test
  fun `verify Zakat calculator debts deduction and gold nisab threshold`() {
    val resultGold = ZakatCalculator.calculate(
      cash = 1000000.0,
      goldVal = 0.0,
      silverVal = 0.0,
      merchandise = 0.0,
      receivables = 0.0,
      debts = 0.0,
      goldGramPrice = 11500.0,
      nisabType = NisabType.GOLD
    )
    assertEquals(977500.0, resultGold.nisabThreshold, 0.01)
    assertTrue(resultGold.isZakatObligatory)
    assertEquals(25000.0, resultGold.zakatPayable, 0.01)

    val resultWithDebts = ZakatCalculator.calculate(
      cash = 100000.0,
      goldVal = 0.0,
      silverVal = 0.0,
      merchandise = 0.0,
      receivables = 0.0,
      debts = 40000.0,
      silverGramPrice = 120.0,
      nisabType = NisabType.SILVER
    )
    assertEquals(60000.0, resultWithDebts.netWealth, 0.01)
    assertFalse(resultWithDebts.isZakatObligatory)
    assertEquals(0.0, resultWithDebts.zakatPayable, 0.01)
  }

  @Test
  fun `verify Room database bookmark insertion retrieval and deletion`() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val inMemoryDb = Room.inMemoryDatabaseBuilder(context, WafaDeenDatabase::class.java)
      .allowMainThreadQueries()
      .build()
    val dao = inMemoryDb.bookmarkDao()

    val testBookmark = BookmarkEntity(
      id = "quran_1_1",
      contentType = "QURAN",
      contentId = "1:1",
      title = "সূরা আল-ফাতিহা (১:১)",
      arabicText = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
      translation = "শুরু করছি আল্লাহর নামে যিনি পরম করুণাময়, অতি দয়ালু।"
    )

    dao.insertBookmark(testBookmark)

    val bookmarks = dao.getAllBookmarks().first()
    assertEquals(1, bookmarks.size)
    assertEquals("quran_1_1", bookmarks.first().id)
    assertEquals("সূরা আল-ফাতিহা (১:১)", bookmarks.first().title)

    val isBookmarked = dao.isBookmarked("quran_1_1").first()
    assertTrue(isBookmarked)

    dao.deleteBookmarkById("quran_1_1")
    val afterDelete = dao.getAllBookmarks().first()
    assertTrue(afterDelete.isEmpty())

    inMemoryDb.close()
  }
}
