package com.example.util

import com.example.data.model.CityLocation
import com.example.data.model.PrayerTimes
import java.util.Calendar
import java.util.Locale
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.tan

object PrayerTimeCalculator {

    val CITIES = listOf(
        CityLocation("ঢাকা", "Dhaka", 23.8103, 90.4125, 6.0, "Bangladesh"),
        CityLocation("চট্টগ্রাম", "Chittagong", 22.3569, 91.7832, 6.0, "Bangladesh"),
        CityLocation("সিলেট", "Sylhet", 24.8949, 91.8687, 6.0, "Bangladesh"),
        CityLocation("রাজশাহী", "Rajshahi", 24.3636, 88.6241, 6.0, "Bangladesh"),
        CityLocation("খুলনা", "Khulna", 22.8456, 89.5403, 6.0, "Bangladesh"),
        CityLocation("বরিশাল", "Barisal", 22.7010, 90.3535, 6.0, "Bangladesh"),
        CityLocation("রংপুর", "Rangpur", 25.7439, 89.2752, 6.0, "Bangladesh"),
        CityLocation("ময়মনসিংহ", "Mymensingh", 24.7471, 90.4203, 6.0, "Bangladesh"),
        CityLocation("মক্কা মুকাররমা", "Mecca", 21.4225, 39.8262, 3.0, "Saudi Arabia"),
        CityLocation("মদিনা মুনাওয়ারা", "Medina", 24.5247, 39.5692, 3.0, "Saudi Arabia"),
        CityLocation("লন্ডন", "London", 51.5074, -0.1278, 0.0, "United Kingdom"),
        CityLocation("নিউইয়র্ক", "New York", 40.7128, -74.0060, -5.0, "United States"),
        CityLocation("কুয়ালালামপুর", "Kuala Lumpur", 3.1390, 101.6869, 8.0, "Malaysia")
    )

    // Kaaba Coordinates in Mecca
    const val KAABA_LAT = 21.4225
    const val KAABA_LNG = 39.8262

    /**
     * Calculates Qibla direction (bearing in degrees clockwise from True North)
     */
    fun calculateQiblaBearing(latitude: Double, longitude: Double): Double {
        val lat1 = Math.toRadians(latitude)
        val lat2 = Math.toRadians(KAABA_LAT)
        val deltaLng = Math.toRadians(KAABA_LNG - longitude)

        val y = sin(deltaLng)
        val x = cos(lat1) * tan(lat2) - sin(lat1) * cos(deltaLng)

        var qibla = Math.toDegrees(atan2(y, x))
        qibla = (qibla + 360.0) % 360.0
        return (qibla * 10.0).roundToInt() / 10.0
    }

    /**
     * Computes solar prayer times using Islamic Foundation Bangladesh / Karachi convention
     * Fajr: 18 degrees, Isha: 18 degrees, Maghrib: sunset + 1-2 min safety
     * Asr: Hanafi (shadow ratio 2) or Shafi'i (shadow ratio 1)
     */
    fun calculatePrayerTimes(
        calendar: Calendar = Calendar.getInstance(),
        location: CityLocation,
        isHanafiAsr: Boolean = true
    ): PrayerTimes {
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

        // Approximate solar declination and equation of time
        val b = 2.0 * Math.PI * (dayOfYear - 81) / 365.0
        val eot = 9.87 * sin(2.0 * b) - 7.53 * cos(b) - 1.5 * sin(b) // in minutes
        val declination = Math.toRadians(23.45 * sin(b))

        val latRad = Math.toRadians(location.latitude)

        // Solar noon (Dhuhr) in local hours
        val noonUtc = 12.0 - (location.longitude / 15.0) - (eot / 60.0)
        val dhuhrLocal = noonUtc + location.timeZoneOffsetHours

        // Sunrise & Sunset angle (sun center at -0.833 degrees for refraction & diameter)
        val zenithSunrise = Math.toRadians(90.833)
        val haSunrise = acosHourAngle(zenithSunrise, latRad, declination)
        val sunriseLocal = dhuhrLocal - (haSunrise / 15.0)
        val sunsetLocal = dhuhrLocal + (haSunrise / 15.0)

        // Fajr (18 degrees below horizon => zenith 108 deg)
        val zenithFajr = Math.toRadians(108.0)
        val haFajr = acosHourAngle(zenithFajr, latRad, declination)
        val fajrLocal = dhuhrLocal - (haFajr / 15.0)

        // Isha (18 degrees below horizon => zenith 108 deg)
        val zenithIsha = Math.toRadians(108.0)
        val haIsha = acosHourAngle(zenithIsha, latRad, declination)
        val ishaLocal = dhuhrLocal + (haIsha / 15.0)

        // Asr: Shadow length factor
        val shadowFactor = if (isHanafiAsr) 2.0 else 1.0
        val angleAsr = atan(1.0 / (shadowFactor + tan(abs(latRad - declination))))
        val zenithAsr = (Math.PI / 2.0) - angleAsr
        val haAsr = acosHourAngle(zenithAsr, latRad, declination)
        val asrLocal = dhuhrLocal + (haAsr / 15.0)

        val maghribLocal = sunsetLocal + (2.0 / 60.0) // 2 minutes safety margin

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dateFormatted = String.format(Locale.US, "%02d/%02d/%d", day, month, year)

        val hijriEstimate = estimateHijriDate(calendar)

        return PrayerTimes(
            fajr = formatHoursToTime(fajrLocal),
            sunrise = formatHoursToTime(sunriseLocal),
            dhuhr = formatHoursToTime(dhuhrLocal),
            asr = formatHoursToTime(asrLocal),
            maghrib = formatHoursToTime(maghribLocal),
            isha = formatHoursToTime(ishaLocal),
            dateFormatted = dateFormatted,
            hijriDate = hijriEstimate,
            calculationMethod = "ইসলামিক ফাউন্ডেশন বাংলাদেশ / করাচি ১৮° পদ্ধতি",
            asrJuristicMethod = if (isHanafiAsr) "হানাফি (দ্বিগুণ ছায়া)" else "শাফেঈ / জমহুর (একগুণ ছায়া)"
        )
    }

    private fun acosHourAngle(zenithRad: Double, latRad: Double, decRad: Double): Double {
        val cosH = (cos(zenithRad) - sin(latRad) * sin(decRad)) / (cos(latRad) * cos(decRad))
        val clamped = cosH.coerceIn(-1.0, 1.0)
        return Math.toDegrees(acos(clamped))
    }

    private fun formatHoursToTime(hoursFraction: Double): String {
        var h = hoursFraction
        while (h < 0) h += 24.0
        while (h >= 24.0) h -= 24.0

        val hours = floor(h).toInt()
        val minutes = ((h - hours) * 60.0).roundToInt()
        val finalHours = if (minutes >= 60) (hours + 1) % 24 else hours
        val finalMinutes = if (minutes >= 60) 0 else minutes

        val isPm = finalHours >= 12
        val display12Hour = when {
            finalHours == 0 -> 12
            finalHours > 12 -> finalHours - 12
            else -> finalHours
        }
        val amPm = if (isPm) "PM" else "AM"
        return String.format(Locale.US, "%02d:%02d %s", display12Hour, finalMinutes, amPm)
    }

    /**
     * Approximate Umm al-Qura / Tabular Hijri calculation with moon sighting note
     */
    fun estimateHijriDate(cal: Calendar): String {
        val jd = julianDay(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
        val l = (jd - 1948440 + 10632).toLong()
        val n = ((l - 1) / 10631).toInt()
        val l2 = l - 10631 * n + 354
        val j = (((10985 - l2) / 5316).toInt()) * ((50 * l2) / 17719).toInt() +
                ((l2 / 5670).toInt()) * ((43 * l2) / 15238).toInt()
        val l3 = l2 - (((30 - j) / 15).toInt()) * ((17719 * j) / 50).toInt() -
                ((j / 16).toInt()) * ((15238 * j) / 43).toInt() + 29
        val m = ((24 * l3) / 709).toInt()
        val d = l3 - ((709 * m) / 24).toInt()
        val y = 30 * n + j - 30

        val hijriMonthsBn = listOf(
            "মুহাররম", "সফর", "রবিউল আউয়াল", "রবিউস সানি", "জমাদিউল আউয়াল", "জমাদিউস সানি",
            "রজব", "শাবান", "রমজান", "শাওয়াল", "জিলকদ", "জিলহজ্জ"
        )
        val monthName = if (m in 1..12) hijriMonthsBn[m - 1] else "হিজরি"
        return "$d $monthName, $y হিজরি (চাঁদ দেখার ওপর নির্ভরশীল)"
    }

    private fun julianDay(year: Int, month: Int, day: Int): Double {
        var y = year
        var m = month
        if (m <= 2) {
            y -= 1
            m += 12
        }
        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        return floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + b - 1524.5
    }
}
