package com.example.service

import com.example.data.model.PrayerType
import com.example.data.model.SalahAudioMapping

object SalahAudioRegistry {

    val audioMappings: Map<PrayerType, SalahAudioMapping> = mapOf(
        PrayerType.FAJR to SalahAudioMapping(
            prayerType = PrayerType.FAJR,
            nameBn = "ফজর (Fajr)",
            nameEn = "Fajr",
            nameAr = "الفجر",
            channelId = "channel_prayer_fajr",
            audioId = "audio_adhan_fajr_alafasy",
            audioUrl = "https://media.blubrry.com/muslim_central_audio/podcasts.qurancentral.com/adhan/adhan-fajr-alafasy.mp3",
            adhanStyleBn = "ফজর স্পেশাল আজান (আস-সালাতু খাইরুম মিনান নাওম সহ)",
            adhanStyleEn = "Fajr Adhan with As-Salatu Khayrum Minan-Nawm (Prayer is better than sleep)",
            hasSpecialFajrPhrase = true
        ),
        PrayerType.SUNRISE to SalahAudioMapping(
            prayerType = PrayerType.SUNRISE,
            nameBn = "সূর্যোদয় (Sunrise)",
            nameEn = "Sunrise",
            nameAr = "الشروق",
            channelId = "channel_prayer_sunrise",
            audioId = "audio_sunrise_reminder_shams",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/091001.mp3",
            adhanStyleBn = "সূর্যোদয় ও ইশরাক স্মরণিকা (সূরা আশ-শামস)",
            adhanStyleEn = "Sunrise & Ishraq Reminder (Surah Ash-Shams)",
            hasSpecialFajrPhrase = false
        ),
        PrayerType.DHUHR to SalahAudioMapping(
            prayerType = PrayerType.DHUHR,
            nameBn = "জোহর (Dhuhr)",
            nameEn = "Dhuhr",
            nameAr = "الظهر",
            channelId = "channel_prayer_dhuhr",
            audioId = "audio_adhan_makkah_haram",
            audioUrl = "https://media.blubrry.com/muslim_central_audio/podcasts.qurancentral.com/adhan/adhan-makkah.mp3",
            adhanStyleBn = "মক্কা মুকাররমা পবিত্র কাবা চত্বরের আজান",
            adhanStyleEn = "Makkah Al-Mukarramah Haram Adhan",
            hasSpecialFajrPhrase = false
        ),
        PrayerType.ASR to SalahAudioMapping(
            prayerType = PrayerType.ASR,
            nameBn = "আসর (Asr)",
            nameEn = "Asr",
            nameAr = "العصر",
            channelId = "channel_prayer_asr",
            audioId = "audio_adhan_madinah_nabawi",
            audioUrl = "https://media.blubrry.com/muslim_central_audio/podcasts.qurancentral.com/adhan/adhan-madinah.mp3",
            adhanStyleBn = "মদিনা মুনাওয়ারা মসজিদে নববীর ঐতিহ্যবাহী আজান",
            adhanStyleEn = "Madinah Al-Munawwarah Masjid An-Nabawi Adhan",
            hasSpecialFajrPhrase = false
        ),
        PrayerType.MAGHRIB to SalahAudioMapping(
            prayerType = PrayerType.MAGHRIB,
            nameBn = "মাগরিব (Maghrib)",
            nameEn = "Maghrib",
            nameAr = "المغرب",
            channelId = "channel_prayer_maghrib",
            audioId = "audio_adhan_alaqsa_basit",
            audioUrl = "https://media.blubrry.com/muslim_central_audio/podcasts.qurancentral.com/adhan/adhan-abdul-basit.mp3",
            adhanStyleBn = "বাইতুল মুকাদ্দাস আল-আকসা ও মিসরীয় সুরে আজান",
            adhanStyleEn = "Al-Aqsa Sanctuary & Egyptian Adhan (Qari Abdul Basit)",
            hasSpecialFajrPhrase = false
        ),
        PrayerType.ISHA to SalahAudioMapping(
            prayerType = PrayerType.ISHA,
            nameBn = "এশা (Isha)",
            nameEn = "Isha",
            nameAr = "العشاء",
            channelId = "channel_prayer_isha",
            audioId = "audio_adhan_kuwait_alafasy",
            audioUrl = "https://media.blubrry.com/muslim_central_audio/podcasts.qurancentral.com/adhan/adhan-alafasy.mp3",
            adhanStyleBn = "সুললিত এশার আজান (শাইখ মিশারি রশিদ আল-আফাসী)",
            adhanStyleEn = "Resonant Isha Adhan (Sheikh Mishary Rashid Alafasy)",
            hasSpecialFajrPhrase = false
        )
    )

    fun getAudioMappingForPrayer(prayerType: PrayerType): SalahAudioMapping {
        return audioMappings[prayerType] ?: audioMappings.getValue(PrayerType.DHUHR)
    }

    /**
     * Confirms that all five obligatory Salah and sunrise have distinct audio identifiers
     */
    fun verifyDistinctAudioMappings(): Boolean {
        val allAudioIds = audioMappings.values.map { it.audioId }.toSet()
        val allUrls = audioMappings.values.map { it.audioUrl }.toSet()
        val allChannels = audioMappings.values.map { it.channelId }.toSet()
        return allAudioIds.size == audioMappings.size &&
                allUrls.size == audioMappings.size &&
                allChannels.size == audioMappings.size
    }
}
