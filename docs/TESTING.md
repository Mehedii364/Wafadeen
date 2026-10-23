# Wafa Deen — Testing Strategy & Device Checklist

## 1. Automated Testing Strategy
Automated unit and Robolectric tests are implemented in `app/src/test/java/com/example/`:
* **`ExampleRobolectricTest.kt`:**
  * Context & string localization verification (`R.string.app_name` == "Wafa Deen").
  * 114 Surahs Directory integrity (validating numbering, Arabic names, and revelation types).
  * Surah Al-Fatihah verse validation (7 verses, authentic Uthmani text).
  * Hadith search engine tests (verifying search by keywords like "নিয়ত" and by Hadith number "8").
  * Qibla bearing calculation test (verifying ~277° bearing from Dhaka coordinates to Mecca).
  * Prayer time calculation test (verifying accurate Fajr, Sunrise, Dhuhr, Asr, Maghrib, and Isha generation).

Run tests locally via command line:
```bash
gradle :app:testDebugUnitTest
```

---

## 2. Physical Device Manual Testing Checklist

| Test Item | Verification Method | Expected Result |
| :--- | :--- | :--- |
| **Splash & App Launcher Icon** | Launch on phone home screen | Custom adaptive icon (gold crescent & open book on emerald `#043D31`) renders cleanly |
| **Offline Operation** | Put device into Airplane Mode | All Salah guides, Quran directory, Hadiths, Duas, Tasbih, and calculations function without network |
| **Digital Tasbih Vibration** | Tap counter button | Device vibrator provides subtle tactile haptic feedback per tap |
| **Audio Playback** | Play Surah Al-Fatihah or Ayah recitation | Audio streams via MediaPlayer, seek slider updates, repeat ayah loops cleanly |
| **Qibla Compass** | Rotate device in 360 degrees | Compass dial needle rotates smoothly toward Kaaba azimuth (~277° from Dhaka) |
| **Language Switching** | Switch between Bengali, English, and Arabic | UI labels, numbers, and navigation update instantly |
| **Theme Switching** | Toggle Dark Mode / Light Mode | Emerald & Obsidian dark theme or Warm Light theme applies with high contrast |
| **Database Persistence** | Add bookmarks and Tasbih sessions, kill & restart app | Saved bookmarks and history remain persisted in Room Database |
