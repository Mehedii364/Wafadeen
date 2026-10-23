# Wafa Deen — Production Android Islamic Learning Application

[![Android Build](https://github.com/wafazone/wafadeen/actions/workflows/android-build.yml/badge.svg)](https://github.com/wafazone/wafadeen/actions/workflows/android-build.yml)

**Wafa Deen** (وفاء دين) is a production-ready, offline-first native Android Islamic learning and lifestyle companion application. It is built strictly with modern Android technologies—Kotlin, Jetpack Compose, Material 3, Room Database, and DataStore.

* **App Name:** Wafa Deen
* **Package ID:** `site.wafazone.wafadeen`
* **Developer Attribution:** Wafa Zone by Mehedi364
* **Default Language:** Bengali (বাংলা) with English and Arabic support
* **Design Identity:** Premium Emerald (`#075E48`), Obsidian (`#171A1A`), Warm Light (`#F7F5EF`), and restrained Gold (`#C5A253`) accents

---

## Key Features

1. **Salah & Purification Education:**
   * Step-by-step interactive guides for Wudu (অজু), Ghusl (গোসল), and Tayammum (তায়াম্মুম).
   * Complete rak'ah breakdown for the 5 daily prayers (Farz, Sunnah Mu'akkadah, Witr Wajib, Nafl).
   * Exact recitations from Takbir to Salam (Takbir Tahrimah, Sana, Fatihah, Ruku Tasbih, Qawmah, Sujud Tasbih, Tashahhud, Durood Ibrahim, Dua Masurah, Salam, and Witr Dua Qunut) with authentic Arabic text, Bengali transliteration, Bengali meaning, and English translation.
   * Hanafi practice as default South Asian standard, with transparent annotations for valid scholarly differences across the four accepted madhhabs.
   * Fiqh guides for Jumu'ah, Janazah prayer, Eid prayers, Musafir Qasr, and Sajdah Sahw.

2. **Al-Quran & Recitation:**
   * Full 114 Surahs directory with Arabic, Bengali, and English names, revelation classification (Makki/Madani), and verse counts.
   * Real-time search by Surah name, meaning, or number.
   * Verse-by-verse reader with King Fahd Uthmani Arabic typography, Bengali pronunciation, Bengali meaning, and English translation.
   * Audio recitation streaming and ayah loop playback with reciter attribution (Sheikh Mishary Rashid Alafasy).
   * One-tap bookmarking to local Room Database.

3. **Sahih Hadith Library:**
   * Structured collections with Sahih al-Bukhari as the priority source.
   * Full Arabic matn, attributed Bengali and English translations, chapter title, narrator attribution, and official reference numbers.
   * Transparent Hadith grading ("সহিহ - ইমাম বুখারী") with verification status badge.
   * Search and bookmarking support.

4. **Duas & Daily Adhkar:**
   * Categorized supplications: Morning & Evening Adhkar, Post-Salah Duas, Sayyid al-Istighfar, Quranic Rabbana Duas, and Daily Life supplications.
   * Clear distinction between Quranic Duas, Prophetic Sunnah, and permissible supplications.

5. **Digital Tasbih Counter:**
   * Selectable Sunnah Dhikrs (SubhanAllah, Alhamdulillah, Allahu Akbar, La ilaha illallah, Astaghfirullah, Salawat, etc.).
   * Interactive large counter with haptic vibration feedback.
   * Configurable targets (33, 99, 100, 1000) with animated progress ring.
   * Decrement button and reset confirmation dialog.
   * Local session saving and history management in Room Database.

6. **Ramadan & Zakat Calculator:**
   * Suhoor and Iftar timings calculated accurately for selected city with authentic Iftar Duas.
   * Taraweeh (8 vs 20 rak'at scholarly consensus note) and Laylat al-Qadr guides.
   * Full Zakat Calculator with Silver Nisab (595g) and Gold Nisab (85g) standards, disclosing 2.5% rate and conditions.

7. **Qibla Compass & Prayer Times:**
   * Astronomical solar calculation using the Islamic Foundation Bangladesh / Karachi 18° twilight method.
   * Hanafi vs Shafi'i Asr calculation toggle.
   * Accurate Qibla bearing calculation using spherical trigonometry referencing the Holy Kaaba (`21.4225° N, 39.8262° E`).
   * Interactive compass dial with device rotation sensor integration and calibration guidance.

8. **Bookmarks & Offline Guarantee:**
   * 100% offline-first architecture powered by Room Database and DataStore.
   * No account, no login, no trackers, and no external server required. All user data stays on device.

---

## Project Structure

```
wafadeen/
├── app/
│   ├── build.gradle.kts           # Application build config & dependencies
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── java/com/example/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── audio/         # AudioPlayerManager (MediaPlayer controller)
│   │   │   │   ├── data/          # Room DB, Entities, DAOs, Preferences
│   │   │   │   ├── repository/    # Quran, Hadith, Salah, Dua, Tasbih repositories
│   │   │   │   ├── ui/
│   │   │   │   │   ├── components/# Reusable M3 widgets (AudioBar, Badges, Cards)
│   │   │   │   │   ├── navigation/# Navigation compose & adaptive rail/bottom bar
│   │   │   │   │   ├── screens/   # Home, Salah, Quran, Hadith, Tasbih, Qibla, etc.
│   │   │   │   │   └── theme/     # Emerald, Obsidian, Gold M3 ColorScheme
│   │   │   └── res/
│   │   │       ├── values/        # strings.xml (Bengali default)
│   │   │       ├── values-en/     # English strings
│   │   │       └── values-ar/     # Arabic strings
│   │   └── test/java/com/example/ # Unit and Robolectric tests
├── .github/workflows/
│   └── android-build.yml          # GitHub Actions workflow for real APK builds
├── docs/
│   ├── SOURCES.md                 # Complete religious dataset manifests
│   ├── VERIFICATION.md            # Verification workflow & matrix
│   └── TESTING.md                 # Automated & manual testing checklist
├── gradlew
├── settings.gradle.kts
└── README.md
```

---

## Local Build Instructions

### Prerequisites
* JDK 17
* Android Studio Ladybug or higher (or Android SDK command line tools)

### Running Automated Tests
```bash
./gradlew testDebugUnitTest
```

### Assembling the Debug APK
```bash
./gradlew assembleDebug
```
The compiled APK will be output to:
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## Automated GitHub Actions CI/CD Pipeline

The `.github/workflows/android-build.yml` pipeline runs on every push and pull request to:
1. Check out source code and setup JDK 17 & Android SDK.
2. Run unit and Robolectric tests.
3. Build the real debug APK via `./gradlew assembleDebug`.
4. Validate APK file integrity (`stat -c%s`, `unzip -t`).
5. Copy validated APK to `.build-outputs/app-debug.apk` and `APK_DOWNLOAD/app-debug.apk`.
6. Verify matching SHA256 checksums.
7. Upload the APK as a downloadable GitHub Actions artifact (`app-debug-apk`).

---

## License & Developer Attribution
Developed with dedication by **Wafa Zone by Mehedi364** (`site.wafazone.wafadeen`).  
Non-commercial religious educational distribution.
