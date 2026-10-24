package com.example.repository

import com.example.data.model.Ayah
import com.example.data.model.RevelationType
import com.example.data.model.Surah

object QuranRepository {

    // Complete Directory of 114 Surahs with metadata
    val allSurahs: List<Surah> = listOf(
        Surah(1, "الفاتحة", "Al-Fatihah", "আল-ফাতিহা", "সূচনা", 7, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/001.mp3"),
        Surah(2, "البقرة", "Al-Baqarah", "আল-বাক্বারাহ", "গাভী", 286, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/002.mp3"),
        Surah(3, "آل عمران", "Ali 'Imran", "আলে ইমরান", "ইমরানের পরিবার", 200, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/003.mp3"),
        Surah(4, "النساء", "An-Nisa", "আন-নিসা", "নারী", 176, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/004.mp3"),
        Surah(5, "المائدة", "Al-Ma'idah", "আল-মায়িদাহ", "খাদ্য পরিবেশিত টেবিল", 120, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/005.mp3"),
        Surah(6, "الأنعام", "Al-An'am", "আল-আনআম", "গৃহপালিত পশু", 165, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/006.mp3"),
        Surah(7, "الأعراف", "Al-A'raf", "আল-আরাফ", "উঁচু স্থানসমূহ", 206, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/007.mp3"),
        Surah(8, "الأنفال", "Al-Anfal", "আল-আনফাল", "যুদ্ধলব্ধ সম্পদ", 75, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/008.mp3"),
        Surah(9, "التوبة", "At-Tawbah", "আত-তাওবাহ", "অনুশোচনা", 129, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/009.mp3"),
        Surah(10, "يونس", "Yunus", "ইউনুস", "নবী ইউনুস (আ.)", 109, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/010.mp3"),
        Surah(11, "هود", "Hud", "হুদ", "নবী হুদ (আ.)", 123, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/011.mp3"),
        Surah(12, "يوسف", "Yusuf", "ইউসুফ", "নবী ইউসুফ (আ.)", 111, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/012.mp3"),
        Surah(13, "الرعد", "Ar-Ra'd", "আর-রাদ", "বজ্রনাদ", 43, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/013.mp3"),
        Surah(14, "إبراهيم", "Ibrahim", "ইব্রাহিম", "নবী ইব্রাহিম (আ.)", 52, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/014.mp3"),
        Surah(15, "الحجر", "Al-Hijr", "আল-হিজর", "পাথুরে পাহাড়", 99, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/015.mp3"),
        Surah(16, "النحل", "An-Nahl", "আন-নাহল", "মৌমাছি", 128, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/016.mp3"),
        Surah(17, "الإسراء", "Al-Isra", "আল-ইসরা", "নৈশভ্রমণ", 111, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/017.mp3"),
        Surah(18, "الكهف", "Al-Kahf", "আল-কাহফ", "গুহা", 110, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/018.mp3"),
        Surah(19, "مريم", "Maryam", "মারইয়াম", "মরিয়ম (আ.)", 98, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/019.mp3"),
        Surah(20, "طه", "Ta-Ha", "ত্বা-হা", "ত্বা-হা", 135, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/020.mp3"),
        Surah(21, "الأنبياء", "Al-Anbiya", "আল-আম্বিয়া", "নবীগণ", 112, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/021.mp3"),
        Surah(22, "الحج", "Al-Hajj", "আল-হজ্জ", "হজ", 78, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/022.mp3"),
        Surah(23, "المؤمنون", "Al-Mu'minun", "আল-মুমিনুন", "মুমিনগণ", 118, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/023.mp3"),
        Surah(24, "النور", "An-Nur", "আন-নূর", "জ্যোতি", 64, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/024.mp3"),
        Surah(25, "الفرقان", "Al-Furqan", "আল-ফুরকান", "সত্য ও মিথ্যার পার্থক্যকারী", 77, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/025.mp3"),
        Surah(26, "الشعراء", "Ash-Shu'ara", "আশ-শুয়ারা", "কবিগণ", 227, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/026.mp3"),
        Surah(27, "النمل", "An-Naml", "আন-নামল", "পিঁপড়া", 93, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/027.mp3"),
        Surah(28, "القصص", "Al-Qasas", "আল-ক্বাসাস", "ইতিহাস / কাহিনী", 88, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/028.mp3"),
        Surah(29, "العنكبوت", "Al-'Ankabut", "আল-আনকাবুত", "মাকড়সা", 69, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/029.mp3"),
        Surah(30, "الروم", "Ar-Rum", "আর-রূম", "রোমান জাতি", 60, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/030.mp3"),
        Surah(31, "لقمان", "Luqman", "লুকমান", "জ্ঞানী লুকমান", 34, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/031.mp3"),
        Surah(32, "السجدة", "As-Sajdah", "আস-সাজদাহ", "সিজদা", 30, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/032.mp3"),
        Surah(33, "الأحزاب", "Al-Ahzab", "আল-আহযাব", "জোটবদ্ধ দল", 73, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/033.mp3"),
        Surah(34, "سبأ", "Saba", "সাবা", "সাবা জাতি", 54, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/034.mp3"),
        Surah(35, "فاطر", "Fatir", "ফাতির", "আদি স্রষ্টা", 45, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/035.mp3"),
        Surah(36, "يس", "Ya-Sin", "ইয়াসীন", "ইয়াসীন", 83, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/036.mp3"),
        Surah(37, "الصافات", "As-Saffat", "আস-সাফফাত", "সারিবদ্ধ ফেরেশতাগণ", 182, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/037.mp3"),
        Surah(38, "ص", "Sad", "সোয়াদ", "সোয়াদ", 88, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/038.mp3"),
        Surah(39, "الزمر", "Az-Zumar", "আজ-জুমার", "দলবদ্ধ জনতা", 75, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/039.mp3"),
        Surah(40, "غافر", "Ghafir", "গাফির", "ক্ষমাকারী", 85, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/040.mp3"),
        Surah(41, "فصلت", "Fussilat", "ফুসসিলাত", "সুস্পষ্ট ব্যাখ্যা", 54, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/041.mp3"),
        Surah(42, "الشورى", "Ash-Shura", "আশ-শুরা", "পরামর্শ", 53, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/042.mp3"),
        Surah(43, "الزخرف", "Az-Zukhruf", "আজ-জু Ruf", "স্বর্ণালঙ্কার", 89, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/043.mp3"),
        Surah(44, "الدخان", "Ad-Dukhan", "আদ-দুখান", "ধোঁয়া", 59, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/044.mp3"),
        Surah(45, "الجاثية", "Al-Jathiyah", "আল-জাসিয়া", "নতজানু", 37, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/045.mp3"),
        Surah(46, "الأحقاف", "Al-Ahqaf", "আল-আহক্বাফ", "বালুকাময় পাহাড়", 35, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/046.mp3"),
        Surah(47, "محمد", "Muhammad", "মুহাম্মদ", "নবী মুহাম্মদ (সা.)", 38, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/047.mp3"),
        Surah(48, "الفتح", "Al-Fath", "আল-ফাতহ", "বিজয়", 29, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/048.mp3"),
        Surah(49, "الحجرات", "Al-Hujurat", "আল-হুজুরাত", "বাসগৃহসমূহ", 18, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/049.mp3"),
        Surah(50, "ق", "Qaf", "ক্বাফ", "ক্বাফ", 45, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/050.mp3"),
        Surah(51, "الذاريات", "Adh-Dhariyat", "আজ-যারিয়াত", "বিক্ষিপ্তকারী বাতাস", 60, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/051.mp3"),
        Surah(52, "الطور", "At-Tur", "আত-তূর", "তূর পাহাড়", 49, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/052.mp3"),
        Surah(53, "النجم", "An-Najm", "আন-নাজম", "তারা", 62, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/053.mp3"),
        Surah(54, "القمر", "Al-Qamar", "আল-ক্বামার", "চাঁদ", 55, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/054.mp3"),
        Surah(55, "الرحمن", "Ar-Rahman", "আর-রাহমান", "পরম দয়ালু", 78, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/055.mp3"),
        Surah(56, "الواقعة", "Al-Waqi'ah", "আল-ওয়াক্বিয়া", "নিশ্চিত ঘটনা", 96, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/056.mp3"),
        Surah(57, "الحديد", "Al-Hadid", "আল-হাদীদ", "লোহা", 29, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/057.mp3"),
        Surah(58, "المجادلة", "Al-Mujadila", "আল-মুজাদালাহ", "অনুযোগকারিণী", 22, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/058.mp3"),
        Surah(59, "الحشر", "Al-Hashr", "আল-হাশর", "সমাবেশ", 24, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/059.mp3"),
        Surah(60, "الممتحنة", "Al-Mumtahanah", "আল-মুমতাহিনাহ", "পরীক্ষিতা নারী", 13, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/060.mp3"),
        Surah(61, "الصف", "As-Saff", "আস-সাফ", "সারিবদ্ধ দল", 14, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/061.mp3"),
        Surah(62, "الجمعة", "Al-Jumu'ah", "আল-জুমুআহ", "সম্মেলন / শুক্রবার", 11, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/062.mp3"),
        Surah(63, "المنافقون", "Al-Munafiqun", "আল-মুনাফিকুন", "কপট বিশ্বাসীগণ", 11, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/063.mp3"),
        Surah(64, "التغابن", "At-Taghabun", "আত-তাগাবুন", "ক্ষতি ও লাভ", 18, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/064.mp3"),
        Surah(65, "الطلاق", "At-Talaq", "আত-তালাক", "বিবাহবিচ্ছেদ", 12, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/065.mp3"),
        Surah(66, "التحريم", "At-Tahrim", "আত-তাহরীম", "নিষিদ্ধকরণ", 12, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/066.mp3"),
        Surah(67, "الملك", "Al-Mulk", "আল-মুলক", "সার্বভৌম কর্তৃত্ব", 30, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/067.mp3"),
        Surah(68, "القلم", "Al-Qalam", "আল-ক্বলম", "কলম", 52, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/068.mp3"),
        Surah(69, "الحاقة", "Al-Haqqah", "আল-হাক্বক্বাহ", "নিশ্চিত সত্য", 52, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/069.mp3"),
        Surah(70, "المعارج", "Al-Ma'arij", "আল-মাআরিজ", "উন্নয়নের সোপান", 44, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/070.mp3"),
        Surah(71, "نوح", "Nuh", "নূহ", "নবী নূহ (আ.)", 28, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/071.mp3"),
        Surah(72, "الجن", "Al-Jinn", "আল-জ্বিন", "জ্বিন জাতি", 28, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/072.mp3"),
        Surah(73, "المزمل", "Al-Muzzammil", "আল-মুযযাম্মিল", "বস্ত্রাবৃত", 20, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/073.mp3"),
        Surah(74, "المدثر", "Al-Muddaththir", "আল-মুদ্দাসসির", "চাদরাবৃত", 56, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/074.mp3"),
        Surah(75, "القيامة", "Al-Qiyamah", "আল-ক্বিয়ামাহ", "পুনরুত্থান দিবস", 40, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/075.mp3"),
        Surah(76, "الإنسان", "Al-Insan", "আল-ইনসান", "মানবজাতি", 31, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/076.mp3"),
        Surah(77, "المرسلات", "Al-Mursalat", "আল-মুরসালাত", "প্রেরিত বাতাস", 50, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/077.mp3"),
        Surah(78, "النبأ", "An-Naba", "আন-নাবা", "মহাসংবাদ", 40, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/078.mp3"),
        Surah(79, "النازعات", "An-Nazi'at", "আন-নাযিআত", "উৎপাটনকারী ফেরেশতা", 46, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/079.mp3"),
        Surah(80, "عبس", "'Abasa", "আবাসা", "ভ্রূকুটি করল", 42, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/080.mp3"),
        Surah(81, "التكوير", "At-Takwir", "আত-তাকভীর", "সূর্য আচ্ছাদন", 29, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/081.mp3"),
        Surah(82, "الانفطار", "Al-Infitar", "আল-ইনফিতার", "বিদীর্ণ হওয়া", 19, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/082.mp3"),
        Surah(83, "المطففين", "Al-Mutaffifin", "আল-মুতাফফিফীন", "পরিমাপে কম প্রদানকারী", 36, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/083.mp3"),
        Surah(84, "الانشقاق", "Al-Inshiqaq", "আল-ইনশিক্বাক্ব", "খন্ড-বিখন্ড হওয়া", 25, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/084.mp3"),
        Surah(85, "البروج", "Al-Buruj", "আল-বুরূজ", "নক্ষত্রপুঞ্জ", 22, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/085.mp3"),
        Surah(86, "الطارق", "At-Tariq", "আত-ত্বারিক্ব", "নৈশ আগমনকারী", 17, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/086.mp3"),
        Surah(87, "الأعلى", "Al-A'la", "আল-আলা", "সর্বোচ্চ মহান", 19, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/087.mp3"),
        Surah(88, "الغاشية", "Al-Ghashiyah", "আল-গাশিয়াহ", "সর্বগ্রাসী সংকট", 26, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/088.mp3"),
        Surah(89, "الفجر", "Al-Fajr", "আল-ফজর", "ভোরবেলা", 30, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/089.mp3"),
        Surah(90, "البلد", "Al-Balad", "আল-বালাদ", "নগরী (মক্কা)", 20, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/090.mp3"),
        Surah(91, "الشمس", "Ash-Shams", "আশ-শামস", "সূর্য", 15, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/091.mp3"),
        Surah(92, "الليل", "Al-Layl", "আল-লাইল", "রাত্রি", 21, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/092.mp3"),
        Surah(93, "الضحى", "Ad-Duha", "আদ-দুহা", "পূর্বাহ্ণ", 11, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/093.mp3"),
        Surah(94, "الشرح", "Ash-Sharh", "আল-ইনশিরাহ", "বক্ষ প্রশস্তকরণ", 8, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/094.mp3"),
        Surah(95, "التين", "At-Tin", "আত-তীন", "ডুমুর ফল", 8, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/095.mp3"),
        Surah(96, "العلق", "Al-'Alaq", "আল-আলাক্ব", "রক্তপিণ্ড", 19, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/096.mp3"),
        Surah(97, "القدر", "Al-Qadr", "আল-ক্বদর", "মহিমান্বিত রাত", 5, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/097.mp3"),
        Surah(98, "البينة", "Al-Bayyinah", "আল-বাইয়্যিনাহ", "সুস্পষ্ট প্রমাণ", 8, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/098.mp3"),
        Surah(99, "الزلزلة", "Az-Zalzalah", "আল-যিলযাল", "ভূমিকম্প", 8, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/099.mp3"),
        Surah(100, "العاديات", "Al-'Adiyat", "আল-আদিয়াত", "অভিযানকারী অশ্ব", 11, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/100.mp3"),
        Surah(101, "القارعة", "Al-Qari'ah", "আল-ক্বারিয়াহ", "মহা প্রলয়", 11, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/101.mp3"),
        Surah(102, "التكاثر", "At-Takathur", "আত-তাকাসুর", "প্রাচুর্যের প্রতিযোগিতা", 8, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/102.mp3"),
        Surah(103, "العصر", "Al-'Asr", "আল-আসর", "মহাকাল", 3, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/103.mp3"),
        Surah(104, "الهمزة", "Al-Humazah", "আল-হুমাযাহ", "পরনিন্দাকারী", 9, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/104.mp3"),
        Surah(105, "الفيل", "Al-Fil", "আল-ফীল", "হাতি", 5, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/105.mp3"),
        Surah(106, "قريش", "Quraysh", "কুরাইশ", "কুরাইশ বংশ", 4, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/106.mp3"),
        Surah(107, "الماعون", "Al-Ma'un", "আল-মাউন", "নিত্যপ্রয়োজনীয় সাহায্য", 7, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/107.mp3"),
        Surah(108, "الكوثر", "Al-Kawthar", "আল-কাওসার", "প্রচুর কল্যাণ", 3, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/108.mp3"),
        Surah(109, "الكافرون", "Al-Kafirun", "আল-কাফিরুন", "অবিশ্বাসীগণ", 6, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/109.mp3"),
        Surah(110, "النصر", "An-Nasr", "আন-নাসর", "সাহায্য ও বিজয়", 3, RevelationType.MEDINAN, "https://server8.mp3quran.net/afs/110.mp3"),
        Surah(111, "المسد", "Al-Masad", "আল-মাসাদ", "খেজুরগাছের পাকানো দড়ি", 5, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/111.mp3"),
        Surah(112, "الإخلاص", "Al-Ikhlas", "আল-ইখলাস", "একনিষ্ঠতা / তাওহীদ", 4, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/112.mp3"),
        Surah(113, "الفلق", "Al-Falaq", "আল-ফালাক্ব", "ঊষা / প্রভাত", 5, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/113.mp3"),
        Surah(114, "الناس", "An-Nas", "আন-নাস", "মানবমণ্ডলী", 6, RevelationType.MECCAN, "https://server8.mp3quran.net/afs/114.mp3")
    )

    // Detailed verified verses for reading with Arabic Uthmani, Bengali, English & audio
    val sampleAyahs: Map<Int, List<Ayah>> = mapOf(
        1 to listOf(
            Ayah("1_1", 1, 1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "বিসমিল্লাহির রাহমানির রাহিম", "পরম করুণাময় অসীম দয়ালু আল্লাহর নামে শুরু করছি।", "In the name of Allah, the Entirely Merciful, the Especially Merciful.", "https://everyayah.com/data/Alafasy_128kbps/001001.mp3"),
            Ayah("1_2", 1, 2, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "আলহামদু লিল্লাহি রাব্বিল 'আলামীন", "সকল প্রশংসা আল্লাহ তা'আলার, যিনি সমস্ত সৃষ্টিজগতের পালনকর্তা।", "[All] praise is [due] to Allah, Lord of the worlds.", "https://everyayah.com/data/Alafasy_128kbps/001002.mp3"),
            Ayah("1_3", 1, 3, "الرَّحْمَٰنِ الرَّحِيمِ", "আর-রাহমানির রাহীম", "যিনি পরম করুণাময় ও অসীম দয়ালু।", "The Entirely Merciful, the Especially Merciful,", "https://everyayah.com/data/Alafasy_128kbps/001003.mp3"),
            Ayah("1_4", 1, 4, "مَالِكِ يَوْمِ الدِّينِ", "মালিকি ইয়াওমিদ্দীন", "যিনি বিচার দিবসের মালিক।", "Sovereign of the Day of Recompense.", "https://everyayah.com/data/Alafasy_128kbps/001004.mp3"),
            Ayah("1_5", 1, 5, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "ইয়্যাকা না'বুদু ওয়া ইয়্যাকা নাসতা'ঈন", "আমরা কেবল আপনারই ইবাদত করি এবং কেবল আপনারই সাহায্য চাই।", "It is You we worship and You we ask for help.", "https://everyayah.com/data/Alafasy_128kbps/001005.mp3"),
            Ayah("1_6", 1, 6, "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ", "ইহদিনাস-সিরাতাল মুস্তাক্বীম", "আমাদের সরল ও সঠিক পথ প্রদর্শন করুন।", "Guide us to the straight path -", "https://everyayah.com/data/Alafasy_128kbps/001006.mp3"),
            Ayah("1_7", 1, 7, "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ", "সিরাতাল্লাযীনা আন'আমতা 'আলাইহিম গাইরিল মাগদূবি 'আলাইহিম ওয়ালাদ্দোয়াল্লীন", "তাদের পথ, যাদের আপনি অনুগ্রহ দান করেছেন; তাদের পথ নয় যারা ক্রোধের শিকার বা পথভ্রষ্ট।", "The path of those upon whom You have bestowed favor, not of those who have evoked [Your] anger or of those who are astray.", "https://everyayah.com/data/Alafasy_128kbps/001007.mp3")
        ),
        112 to listOf(
            Ayah("112_1", 112, 1, "قُلْ هُوَ اللَّهُ أَحَدٌ", "কুল হুওয়াল্লাহু আহাদ", "বলুন, তিনিই আল্লাহ, একক ও অদ্বিতীয়।", "Say, He is Allah, [who is] One,", "https://everyayah.com/data/Alafasy_128kbps/112001.mp3"),
            Ayah("112_2", 112, 2, "اللَّهُ الصَّمَدُ", "আল্লাহুস সামাদ", "আল্লাহ অমুখাপেক্ষী (সকলের ভরসা)।", "Allah, the Eternal Refuge.", "https://everyayah.com/data/Alafasy_128kbps/112002.mp3"),
            Ayah("112_3", 112, 3, "لَمْ يَلِدْ وَلَمْ يُولَدْ", "লাম ইয়ালিদ ওয়া লাম ইউলাদ", "তিনি কাউকে জন্ম দেননি এবং তিনিও কারো থেকে জন্ম নেননি।", "He neither begets nor is born,", "https://everyayah.com/data/Alafasy_128kbps/112003.mp3"),
            Ayah("112_4", 112, 4, "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ", "ওয়া লাম ইয়াকুল লাহু কুফুওয়ান আহাদ", "এবং তাঁর সমকক্ষ আর কেউ নেই।", "Nor is there to Him any equivalent.", "https://everyayah.com/data/Alafasy_128kbps/112004.mp3")
        ),
        113 to listOf(
            Ayah("113_1", 113, 1, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ", "কুল আ'উযু বিরাব্বিল ফালাক্ব", "বলুন, আমি আশ্রয় প্রার্থনা করছি উষার রবের নিকট,", "Say, I seek refuge in the Lord of daybreak", "https://everyayah.com/data/Alafasy_128kbps/113001.mp3"),
            Ayah("113_2", 113, 2, "مِن شَرِّ مَا خَلَقَ", "মিন শাররি মা খালাক্ব", "তিনি যা সৃষ্টি করেছেন তার অনিষ্ট হতে,", "From the evil of that which He created", "https://everyayah.com/data/Alafasy_128kbps/113002.mp3"),
            Ayah("113_3", 113, 3, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ", "ওয়া মিন শাররি গাসিক্বিন ইযা ওয়াক্বাব", "এবং অন্ধকার রাত্রির অনিষ্ট হতে যখন তা গভীর হয়,", "And from the evil of darkness when it settles", "https://everyayah.com/data/Alafasy_128kbps/113003.mp3"),
            Ayah("113_4", 113, 4, "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ", "ওয়া মিন শাররিন নাফফাসাতি ফিল 'উক্বাফ", "এবং গ্রন্থিতে ফুঁকদানকারিণীদের অনিষ্ট হতে,", "And from the evil of the blowers in knots", "https://everyayah.com/data/Alafasy_128kbps/113004.mp3"),
            Ayah("113_5", 113, 5, "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ", "ওয়া মিন শাররি হাসিদিন ইযা হাসাদ", "এবং হিংসুকের অনিষ্ট হতে যখন সে হিংসা করে।", "And from the evil of an envier when he envies.", "https://everyayah.com/data/Alafasy_128kbps/113005.mp3")
        ),
        114 to listOf(
            Ayah("114_1", 114, 1, "قُلْ أَعُوذُ بِرَبِّ النَّاسِ", "কুল আ'উযু বিরাব্বিন নাস", "বলুন, আমি আশ্রয় চাই মানুষের রবের কাছে,", "Say, I seek refuge in the Lord of mankind,", "https://everyayah.com/data/Alafasy_128kbps/114001.mp3"),
            Ayah("114_2", 114, 2, "مَلِكِ النَّاسِ", "মালিকিন নাস", "মানুষের অধিপতির কাছে,", "The Sovereign of mankind.", "https://everyayah.com/data/Alafasy_128kbps/114002.mp3"),
            Ayah("114_3", 114, 3, "إِلَٰهِ النَّاسِ", "ইলাহিন নাস", "মানুষের প্রকৃত উপাস্যের কাছে,", "The God of mankind,", "https://everyayah.com/data/Alafasy_128kbps/114003.mp3"),
            Ayah("114_4", 114, 4, "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ", "মিন শাররিল ওয়াসওয়াসিল খান্নাস", "সেই কুমন্ত্রণাদাতার অনিষ্ট থেকে যে বার বার ফিরে আসে,", "From the evil of the retreating whisperer -", "https://everyayah.com/data/Alafasy_128kbps/114004.mp3"),
            Ayah("114_5", 114, 5, "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ", "আল্লাযী ইউওয়াসউইসু ফী সুদূরিন নাস", "যে মানুষের অন্তরে কুমন্ত্রণা দেয়,", "Who whispers [evil] into the breasts of mankind -", "https://everyayah.com/data/Alafasy_128kbps/114005.mp3"),
            Ayah("114_6", 114, 6, "مِنَ الْجِنَّةِ وَالنَّاسِ", "মিনাল জিন্নাতি ওয়ান নাস", "জ্বিনের মধ্য হতে এবং মানুষের মধ্য হতে।", "From among the jinn and mankind.", "https://everyayah.com/data/Alafasy_128kbps/114006.mp3")
        ),
        109 to listOf(
            Ayah("109_1", 109, 1, "قُلْ يَا أَيُّهَا الْكَافِرُونَ", "কুল ইয়া আইয়্যুহাল কাফিরূন", "বলুন, হে অবিশ্বাসীগণ (কাফেররা)!", "Say, O disbelievers,", "https://everyayah.com/data/Alafasy_128kbps/109001.mp3"),
            Ayah("109_2", 109, 2, "لَا أَعْبُدُ مَا تَعْبُدُونَ", "লা আ'বুদু মা তা'বুদূন", "আমি তাদের ইবাদত করি না যাদের তোমরা ইবাদত কর,", "I do not worship what you worship.", "https://everyayah.com/data/Alafasy_128kbps/109002.mp3"),
            Ayah("109_3", 109, 3, "وَلَا أَنتُمْ عَابِدُونَ مَا أَعْبُدُ", "ওয়ালা আনতুম 'আবিদূনা মা আ'বুদ", "এবং তোমরাও তাঁর ইবাদতকারী নও যাঁর ইবাদত আমি করি,", "Nor are you worshippers of what I worship.", "https://everyayah.com/data/Alafasy_128kbps/109003.mp3"),
            Ayah("109_4", 109, 4, "وَلَا أَنَا عَابِدٌ مَّا عَبَدتُّمْ", "ওয়ালা আনা 'আবিদুম মা 'আবাত্তুম", "আর আমি কখনও তাদের ইবাদতকারী হব না যাদের তোমরা ইবাদত করে আসছ,", "Nor will I be a worshipper of what you worship.", "https://everyayah.com/data/Alafasy_128kbps/109004.mp3"),
            Ayah("109_5", 109, 5, "وَلَا أَنتُمْ عَابِدُونَ مَا أَعْبُدُ", "ওয়ালা আনতুম 'আবিদূনা মা আ'বুদ", "এবং তোমরাও তাঁর ইবাদতকারী নও যাঁর ইবাদত আমি করি।", "Nor will you be worshippers of what I worship.", "https://everyayah.com/data/Alafasy_128kbps/109005.mp3"),
            Ayah("109_6", 109, 6, "لَكُمْ دِينُكُمْ وَلِيَ دِينِ", "লাকুম দীনুকুম ওয়ালিয়া দীন", "তোমাদের জন্য তোমাদের দ্বীন (ধর্ম) এবং আমার জন্য আমার দ্বীন।", "For you is your religion, and for me is my religion.", "https://everyayah.com/data/Alafasy_128kbps/109006.mp3")
        ),
        110 to listOf(
            Ayah("110_1", 110, 1, "إِذَا جَاءَ نَصْرُ اللَّهِ وَالْفَتْحُ", "ইযা জা-আ নাসরুল্লাহি ওয়াল ফাতহ", "যখন আল্লাহর সাহায্য ও বিজয় আসবে,", "When the victory of Allah has come and the conquest,", "https://everyayah.com/data/Alafasy_128kbps/110001.mp3"),
            Ayah("110_2", 110, 2, "وَرَأَيْتَ النَّاسَ يَدْخُلُونَ فِي دِينِ اللَّهِ أَفْوَاجًا", "ওয়া রাআইতান-নাসা ইয়াদখুলূনা ফী দীনিল্লাহি আফওয়াজা", "এবং আপনি মানুষকে দলে দলে আল্লাহর দ্বীনে প্রবেশ করতে দেখবেন,", "And you see the people entering into the religion of Allah in multitudes,", "https://everyayah.com/data/Alafasy_128kbps/110002.mp3"),
            Ayah("110_3", 110, 3, "فَسَبِّحْ بِحَمْدِ رَبِّكَ وَاسْتَغْفِرْهُ ۚ إِنَّهُ كَانَ تَوَّابًا", "ফাসাব্বিহ বিহামদি রাব্বিকা ওয়াসতাগফিরহু, ইন্নাহু কানা তাউয়্যাবা", "তখন আপনি আপনার রবের প্রশংসাসহ পবিত্রতা বর্ণনা করুন এবং তাঁর নিকট ক্ষমা প্রার্থনা করুন। নিশ্চয়ই তিনি পরম তওবা কবুলকারী।", "Then exalt [Him] with praise of your Lord and ask forgiveness of Him. Indeed, He is ever Accepting of repentance.", "https://everyayah.com/data/Alafasy_128kbps/110003.mp3")
        ),
        111 to listOf(
            Ayah("111_1", 111, 1, "تَبَّتْ يَدَا أَبِي لَهَبٍ وَتَبَّ", "তাব্বাত ইয়াদা আবী লাহাবিঁও ওয়াতাব্ব", "আবু লাহাবের দুই হাত ধ্বংস হোক এবং সে নিজেও ধ্বংস হোক।", "May the hands of Abu Lahab be ruined, and ruined is he.", "https://everyayah.com/data/Alafasy_128kbps/111001.mp3"),
            Ayah("111_2", 111, 2, "مَا أَغْنَىٰ عَنْهُ مَالُهُ وَمَا كَسَبَ", "মা আগনা 'আনহু মালুহু ওয়ামা কাসাব", "তার ধন-সম্পদ এবং যা সে অর্জন করেছে তা তার কোনো কাজে আসল না।", "His wealth will not avail him or that which he gained.", "https://everyayah.com/data/Alafasy_128kbps/111002.mp3"),
            Ayah("111_3", 111, 3, "سَيَصْلَىٰ نَارًا ذَاتَ لَهَبٍ", "সাইয়াসলা নারান যাতা লাহ্যাব", "অচিরেই সে লেলিহান আগুনে প্রবেশ করবে,", "He will [enter to] burn in a Fire of [blazing] flame", "https://everyayah.com/data/Alafasy_128kbps/111003.mp3"),
            Ayah("111_4", 111, 4, "وَامْرَأَتُهُ حَمَّالَةَ الْحَطَبِ", "وامরাআতুহু হাম্মা লাতাল হাত্বাব", "এবং তার স্ত্রীও—যে কাঠ বহনকারিণী,", "And his wife [as well] - the carrier of firewood.", "https://everyayah.com/data/Alafasy_128kbps/111004.mp3"),
            Ayah("111_5", 111, 5, "فِي جِيدِهَا حَبْلٌ مِّن مَّسَدٍ", "ফী জীদিহা হাবলুম মিম মাসাদ", "তার গলায় থাকবে খেজুরগাছের শক্ত পাকানো দড়ি।", "Around her neck is a rope of [twisted] fiber.", "https://everyayah.com/data/Alafasy_128kbps/111005.mp3")
        ),
        105 to listOf(
            Ayah("105_1", 105, 1, "أَلَمْ تَرَ كَيْفَ فَعَلَ رَبُّكَ بِأَصْحَابِ الْفِيلِ", "আলাম তারা কাইফা ফা'আলা রাব্বুকা বিআসহাবিল ফীল", "আপনি কি দেখেননি আপনার রব হাতিওয়ালাদের সাথে কীরূপ আচরণ করেছিলেন?", "Have you not considered how your Lord dealt with the companions of the elephant?", "https://everyayah.com/data/Alafasy_128kbps/105001.mp3"),
            Ayah("105_2", 105, 2, "أَلَمْ يَجْعَلْ كَيْدَهُمْ فِي تَضْلِيلٍ", "আলাম ইয়াজ'আল কাইদাহুম ফী তাদ্বলীল", "তিনি কি তাদের ষড়যন্ত্র নস্যাৎ করে দেননি?", "Did He not make their plan into misguidance?", "https://everyayah.com/data/Alafasy_128kbps/105002.mp3"),
            Ayah("105_3", 105, 3, "وَأَرْسَلَ عَلَيْهِمْ طَيْرًا أَبَابِيلَ", "ওয়া আরসালা 'আলাইহিম তাইরান আবাবীল", "এবং তিনি তাদের ওপর ঝাঁকে ঝাঁকে আবাবিল পাখি প্রেরণ করেছিলেন,", "And He sent against them birds in flocks,", "https://everyayah.com/data/Alafasy_128kbps/105003.mp3"),
            Ayah("105_4", 105, 4, "تَرْمِيهِم بِحِجَارَةٍ مِّن سِجِّيلٍ", "তারমীহিম বিহিজারাতিম মিন সিজ্জীল", "যা তাদের ওপর নিক্ষেপ করেছিল পোড়ামাটির পাথর;", "Striking them with stones of hard clay,", "https://everyayah.com/data/Alafasy_128kbps/105004.mp3"),
            Ayah("105_5", 105, 5, "فَجَعَلَهُمْ كَعَصْفٍ مَّأْكُولٍ", "ফাজা'আলাহুম কা'আসফিম মা'কূল", "অতঃপর তিনি তাদের চর্বিত ঘাসপাতার মতো বানিয়ে দিলেন।", "And He made them like eaten straw.", "https://everyayah.com/data/Alafasy_128kbps/105005.mp3")
        ),
        106 to listOf(
            Ayah("106_1", 106, 1, "لِإِيلَافِ قُرَيْشٍ", "লিইলাফি কুরাইশ", "কুরাইশদের আসক্তির কারণে,", "For the accustomed security of the Quraysh -", "https://everyayah.com/data/Alafasy_128kbps/106001.mp3"),
            Ayah("106_2", 106, 2, "إِيلَافِهِمْ رِحْلَةَ الشِّتَاءِ وَالصَّيْفِ", "ইলাফিহিম রিহলাতাশ-শিতা-য়ি ওয়াস-সাইফ", "শীত ও গ্রীষ্মকালীন সফরের সাথে তাদের অভ্যস্ততার কারণে,", "Their accustomed security [in] the caravan of winter and summer -", "https://everyayah.com/data/Alafasy_128kbps/106002.mp3"),
            Ayah("106_3", 106, 3, "فَلْيَعْبُدُوا رَبَّ هَٰذَا الْبَيْتِ", "ফালইয়া'বুদূ রাব্বা হাযাল বাইত", "অতএব তারা যেন এই ঘরের (কাবাঘরের) রবের ইবাদত করে,", "Let them worship the Lord of this House,", "https://everyayah.com/data/Alafasy_128kbps/106003.mp3"),
            Ayah("106_4", 106, 4, "الَّذِي أَطْعَمَهُم مِّن جُوعٍ وَآمَنَهُم مِّنْ خَوْفٍ", "আল্লাযী আত'আমাহুম মিন জু'ইঁও ওয়া আমানাহুম মিন খাউফ", "যিনি তাদের ক্ষুধায় খাদ্য দান করেছেন এবং ভয় থেকে তাদের নিরাপত্তা দিয়েছেন।", "Who has fed them, [saving them] from hunger and made them safe, [saving them] from fear.", "https://everyayah.com/data/Alafasy_128kbps/106004.mp3")
        ),
        107 to listOf(
            Ayah("107_1", 107, 1, "أَرَأَيْتَ الَّذِي يُكَذِّبُ بِالدِّينِ", "আরাআইতাল্লাযী ইউকাযযিবু বিদ্দীন", "আপনি কি দেখেছেন তাকে যে শেষ কর্মফল দিবসকে অস্বীকার করে?", "Have you seen the one who denies the Recompense?", "https://everyayah.com/data/Alafasy_128kbps/107001.mp3"),
            Ayah("107_2", 107, 2, "فَذَٰلِكَ الَّذِي يَدُعُّ الْيَتِيمَ", "ফাযালিকাল্লাযী ইয়াদু'উল ইয়াতীম", "সে তো সেই ব্যক্তি যে এতিমকে রূঢ়ভাবে তাড়িয়ে দেয়,", "For that is the one who drives away the orphan", "https://everyayah.com/data/Alafasy_128kbps/107002.mp3"),
            Ayah("107_3", 107, 3, "وَلَا يَحُضُّ عَلَىٰ طَعَامِ الْمِسْكِينِ", "ওয়ালা ইয়াহুদ্দু 'আলা তা'আমিল মিসকীন", "এবং মিসকিনদের খাদ্যদানে উৎসাহ দেয় না।", "And does not encourage the feeding of the poor.", "https://everyayah.com/data/Alafasy_128kbps/107003.mp3"),
            Ayah("107_4", 107, 4, "فَوَيْلٌ لِّلْمُصَلِّينَ", "ফাওয়াইলুল লিল মুসল্লীন", "অতএব ধ্বংস সেই নামাজিদের জন্য,", "So woe to those who pray", "https://everyayah.com/data/Alafasy_128kbps/107004.mp3"),
            Ayah("107_5", 107, 5, "الَّذِينَ هُمْ عَن صَلَاتِهِمْ سَاهُونَ", "আল্লাযীনা হুম 'আন সালাতিহিম সাহূন", "যারা তাদের নামাজের ব্যাপারে উদাসীন ও অমনোযোগী,", "[But] who are heedless of their prayer -", "https://everyayah.com/data/Alafasy_128kbps/107005.mp3"),
            Ayah("107_6", 107, 6, "الَّذِينَ هُمْ يُرَاءُونَ", "আল্লাযীনা হুম ইউরা-ঊন", "যারা লোকদেখানোর জন্য কাজ করে,", "Those who make a show [of their deeds]", "https://everyayah.com/data/Alafasy_128kbps/107006.mp3"),
            Ayah("107_7", 107, 7, "وَيَمْنَعُونَ الْمَاعُونَ", "ওয়া ইয়ামনা'ঊনাল মা'ঊন", "এবং নিত্যপ্রয়োজনীয় সামান্য সাহায্যদানেও বাধা দেয়।", "And withhold [simple] assistance.", "https://everyayah.com/data/Alafasy_128kbps/107007.mp3")
        ),
        94 to listOf(
            Ayah("94_1", 94, 1, "أَلَمْ نَشْرَحْ لَكَ صَدْرَكَ", "আলাম নাশরাহ লাকা সাদরাক", "আমি কি আপনার বক্ষকে আপনার জন্য প্রশস্ত করে দিইনি?", "Did We not expand for you, [O Muhammad], your breast?", "https://everyayah.com/data/Alafasy_128kbps/094001.mp3"),
            Ayah("94_2", 94, 2, "وَوَضَعْنَا عَنكَ وِزْرَكَ", "ওয়া ওয়াদ্বা'না 'আনকা বিযরাক", "এবং আমি আপনার ওপর থেকে নামিয়ে দিয়েছি আপনার সেই ভারী বোঝা,", "And We removed from you your burden", "https://everyayah.com/data/Alafasy_128kbps/094002.mp3"),
            Ayah("94_3", 94, 3, "الَّذِي أَنقَضَ ظَهْرَكَ", "আল্লাযী আনক্বাদা যাহরাক", "যা আপনার পিঠকে ভেঙে দিচ্ছিল,", "Which had weighed upon your back", "https://everyayah.com/data/Alafasy_128kbps/094003.mp3"),
            Ayah("94_4", 94, 4, "وَرَفَعْنَا لَكَ ذِكْرَكَ", "ওয়া রাফা'না লাকা যিকরাক", "আর আমি আপনার স্মরণ ও মর্যাদাকে সমুচ্চ করেছি।", "And raised high for you your repute.", "https://everyayah.com/data/Alafasy_128kbps/094004.mp3"),
            Ayah("94_5", 94, 5, "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا", "ফাইন্না মা'আল 'উসরি ইউসরা", "নিশ্চয়ই কষ্টের সাথেই রয়েছে স্বস্তি,", "For indeed, with hardship [will be] ease.", "https://everyayah.com/data/Alafasy_128kbps/094005.mp3"),
            Ayah("94_6", 94, 6, "إِنَّ مَعَ الْعُسْرِ يُسْرًا", "ইন্না মা'আল 'উসরি ইউসরা", "নিশ্চয়ই কষ্টের সাথেই রয়েছে স্বস্তি!", "Indeed, with hardship [will be] ease.", "https://everyayah.com/data/Alafasy_128kbps/094006.mp3"),
            Ayah("94_7", 94, 7, "فَإِذَا فَرَغْتَ فَانصَبْ", "ফা ইযা ফারাগতা ফানসাব", "অতএব যখনই আপনি অবসর পান, ইবাদতে মশগুল হোন,", "So when you have finished [your duties], labor hard [in worship].", "https://everyayah.com/data/Alafasy_128kbps/094007.mp3"),
            Ayah("94_8", 94, 8, "وَإِلَىٰ رَبِّكَ فَارْغَب", "ওয়া ইলা রাব্বিকা ফারগাব", "এবং কেবল আপনার রবের প্রতি মনোনিবেশ করুন।", "And to your Lord direct [your] longing.", "https://everyayah.com/data/Alafasy_128kbps/094008.mp3")
        ),
        95 to listOf(
            Ayah("95_1", 95, 1, "وَالتِّينِ وَالزَّيْتُونِ", "ওয়াত-তীনি ওয়ায-যাইতূন", "ডুমুর (তীন) এবং জলপাইয়ের (যয়তূন) শপথ,", "By the fig and the olive", "https://everyayah.com/data/Alafasy_128kbps/095001.mp3"),
            Ayah("95_2", 95, 2, "وَطُورِ سِينِينَ", "ওয়া তূরি সীনীন", "এবং সিনাই পর্বতের শপথ,", "And [by] Mount Sinai", "https://everyayah.com/data/Alafasy_128kbps/095002.mp3"),
            Ayah("95_3", 95, 3, "وَهَٰذَا الْبَلَدِ الْأَمِينِ", "ওয়া হাযাল বালাদিল আমীন", "এবং এই নিরাপদ নগরীর (মক্কা মুকাররমার) শপথ,", "And [by] this secure city [Makkah],", "https://everyayah.com/data/Alafasy_128kbps/095003.mp3"),
            Ayah("95_4", 95, 4, "لَقَدْ خَلَقْنَا الْإِنسَانَ فِي أَحْسَنِ تَقْوِيمٍ", "লাক্বাদ খালাক্বনাল ইনসানা ফী আহসানি তাক্ববীম", "নিশ্চয়ই আমি মানুষকে সৃষ্টি করেছি সর্বোত্তম ও সুন্দরতম গঠনে,", "We have certainly created man in the best of stature;", "https://everyayah.com/data/Alafasy_128kbps/095004.mp3"),
            Ayah("95_5", 95, 5, "ثُمَّ رَدَدْنَاهُ أَسْفَلَ سَافِلِينَ", "ছুম্মা রাদাদনাহু আসফালা সাফিলীন", "অতঃপর আমি তাকে নামিয়ে দিয়েছি হীনতমদের নিম্নতম স্তরে;", "Then We return him to the lowest of the low,", "https://everyayah.com/data/Alafasy_128kbps/095005.mp3"),
            Ayah("95_6", 95, 6, "إِلَّا الَّذِينَ آمَنُوا وَعَمِلُوا الصَّالِحَاتِ فَلَهُمْ أَجْرٌ غَيْرُ مَمْنُونٍ", "ইল্লাল্লাযীনা আমানু ওয়া 'আমিলুস সালিহাতি ফালাহুম আজরুন গাইরু মামনূন", "কিন্তু তারা ব্যতীত যারা ঈমান এনেছে এবং সৎকাজ করেছে—তাদের জন্য রয়েছে নিরবচ্ছিন্ন ও অফুরন্ত প্রতিদান।", "Except for those who believe and do righteous deeds, for they will have a reward uninterrupted.", "https://everyayah.com/data/Alafasy_128kbps/095006.mp3"),
            Ayah("95_7", 95, 7, "فَمَا يُكَذِّبُكَ بَعْدُ بِالدِّينِ", "ফামা ইউকাযযিবুকা বা'দু বিদ্দীন", "অতএব এরপর কিসে আপনাকে বিচার দিবস সম্পর্কে অবিশ্বাসী করে?", "So what yet causes you to deny the Recompense?", "https://everyayah.com/data/Alafasy_128kbps/095007.mp3"),
            Ayah("95_8", 95, 8, "أَلَيْسَ اللَّهُ بِأَحْكَمِ الْحَاكِمِينَ", "আলাইসাল্লাহু বিআহকামিল হাকিমীন", "আল্লাহ কি বিচারকদের মধ্যে সর্বশ্রেষ্ঠ বিচারক নন?", "Is not Allah the most just of judges?", "https://everyayah.com/data/Alafasy_128kbps/095008.mp3")
        ),
        2 to listOf(
            Ayah("2_1", 2, 1, "الم", "আলিফ-লাম-মীম", "আলিফ-লাম-মীম।", "Alif, Lam, Meem.", "https://everyayah.com/data/Alafasy_128kbps/002001.mp3"),
            Ayah("2_2", 2, 2, "ذَٰلِكَ الْكِتَابُ لَا رَيْبَ ۛ فِيهِ ۛ هُدًى لِّلْمُتَّقِينَ", "যালিকাল কিতাবু লা রাইবা ফীহ, হুদাল লিল মুত্তাক্বীন", "এ সেই কিতাব, যাতে কোনো সন্দেহ নেই; এটি মুত্তাকীদের (আল্লাহভীরুদের) জন্য পথনির্দেশক।", "This is the Book about which there is no doubt, a guidance for those conscious of Allah -", "https://everyayah.com/data/Alafasy_128kbps/002002.mp3"),
            Ayah("2_3", 2, 3, "الَّذِينَ يُؤْمِنُونَ بِالْغَيْبِ وَيُقِيمُونَ الصَّلَاةَ وَمِمَّا رَزَقْنَاهُمْ يُنفِقُونَ", "আল্লাযীনা ইউ'মিনূনা বিল গাইবি ওয়া ইয়ুক্বীমূনাস-সালাতা ওয়া মিম্মা রাযাক্বনাহুম ইউনফিক্বূন", "যারা অদৃশ্যের প্রতি বিশ্বাস স্থাপন করে, সালাত কায়েম করে এবং আমি তাদের যা জীবিকা দিয়েছি তা থেকে ব্যয় করে,", "Who believe in the unseen, establish prayer, and spend out of what We have provided for them,", "https://everyayah.com/data/Alafasy_128kbps/002003.mp3"),
            Ayah("2_4", 2, 4, "وَالَّذِينَ يُؤْمِنُونَ بِمَا أُنزِلَ إِلَيْكَ وَمَا أُنزِلَ مِن قَبْلِكَ وَبِالْآخِرَةِ هُمْ يُوقِنُونَ", "ওয়াল্লাযীনা ইউ'মিনূনা বিমা উনযিলা ইলাইকা ওয়ামা উনযিলা মিন ক্বাবলিকা ওয়াবিল আখিরাতি হুম ইউক্বিনূন", "এবং যারা বিশ্বাস করে যা আপনার প্রতি অবতীর্ণ করা হয়েছে এবং যা আপনার পূর্বে অবতীর্ণ করা হয়েছে, আর আখেরাতের প্রতি তারা দৃঢ় বিশ্বাস পোষণ করে।", "And who believe in what has been revealed to you, [O Muhammad], and what was revealed before you, and of the Hereafter they are certain [in faith].", "https://everyayah.com/data/Alafasy_128kbps/002004.mp3"),
            Ayah("2_5", 2, 5, "أُولَٰئِكَ عَلَىٰ هُدًى مِّن رَّبِّهِمْ ۖ وَأُولَٰئِكَ هُمُ الْمُفْلِحُونَ", "উলা-ইকা 'আলা হুদাম মির রাব্বিহিম ওয়া উলা-ইকা হুমুল মুফলিহূন", "তারাই তাদের প্রতিপালকের সঠিক নির্দেশনার ওপর প্রতিষ্ঠিত এবং তারাই সফলকাম।", "Those are upon [right] guidance from their Lord, and it is those who are the successful.", "https://everyayah.com/data/Alafasy_128kbps/002005.mp3"),
            Ayah(
                "2_255", 2, 255,
                "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ ۗ مَن ذَا الَّذِي يَشْفَعُ عِندَهُ إِلَّا بِإِذْنِهِ ۚ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ ۖ وَلَا يُحِيطُونَ بِشَيْءٍ مِّنْ عِلْمِهِ إِلَّا بِمَا شَاءَ ۚ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ ۖ وَلَا يَئُودُهُ حِفْظُهُمَا ۚ وَهُوَ الْعَلِيُّ الْعَظِيمُ",
                "আল্লাহু লা ইলাহা ইল্লা হুওয়াল হাইয়্যুল ক্বাইয়্যুম, লা তা'খুযুহু সিনাতুঁও ওয়ালা নাওম, লাহু মা ফিস-সামাওয়াতি ওয়ামা ফিল আরদ্ব, মান যাল্লাযী ইয়্যাশফা'উ 'ইনদাহু ইল্লা বিইযনিহ, ইয়া'লামু মা বাইনা আইদীহিম ওয়ামা খালফাহুম, ওয়ালা ইউহীতূনা বিশাইইম মিন 'ইলমিহী ইল্লা বিমা শা-আ, ওয়াসি'আ কুরসিইয়্যুহুস-সামাওয়াতি ওয়াল আরদ্ব, ওয়ালা ইয়াউদুহু হিফজুহুমা, ওয়াহুওয়াল 'আলিয়্যুল 'আযীম।",
                "আল্লাহ, তিনি ব্যতীত কোনো সত্য উপাস্য নেই। তিনি চিরঞ্জীব, সর্বসত্তার ধারক। তন্দ্রা ও নিদ্রা তাঁকে স্পর্শ করে না। আকাশমণ্ডলী ও পৃথিবীতে যা কিছু আছে সবই তাঁর। কে আছে যে তাঁর অনুমতি ব্যতীত তাঁর নিকট সুপারিশ করবে? তাদের সম্মুখে ও পেছনে যা কিছু আছে সবই তিনি জানেন। তাঁর জ্ঞানের কোনো কিছুই কেউ আয়ত্ত করতে পারে না—তিনি যতটুকু ইচ্ছে করেন তা ব্যতীত। তাঁর কুরসী সমস্ত আকাশমণ্ডলী ও পৃথিবীকে পরিবেষ্টন করে আছে, এবং এ দুয়ের সংরক্ষণ তাঁর জন্য কোনো ক্লান্তিকর কাজ নয়। তিনি সুউচ্চ, মহামহিম।",
                "Allah - there is no deity except Him, the Ever-Living, the Sustainer of [all] existence. Neither drowsiness overtakes Him nor sleep. To Him belongs whatever is in the heavens and whatever is on the earth...",
                "https://everyayah.com/data/Alafasy_128kbps/002255.mp3"
            ),
            Ayah(
                "2_285", 2, 285,
                "آمَنَ الرَّسُولُ بِمَا أُنزِلَ إِلَيْهِ مِن رَّبِّهِ وَالْمُؤْمِنُونَ ۚ كُلٌّ آمَنَ بِاللَّهِ وَمَلَائِكَتِهِ وَكُتُبِهِ وَرُسُلِهِ لَا نُفَرِّقُ بَيْنَ أَحَدٍ مِّن رُّسُلِهِ ۚ وَقَالُوا سَمِعْنَا وَأَطَعْنَا ۖ غُفْرَانَكَ رَبَّنَا وَإِلَيْكَ الْمَصِيرُ",
                "আমানার-রাসূলু বিমা উনযিলা ইলাইহি মির রাব্বিহী ওয়াল মু'মিনূন, কুল্লুন আমানা বিল্লাহি ওয়া মালাইকাতিহী ওয়া কুতুবিহী ওয়া রুসুলিহ, লা নুফাররিক্বু বাইনা আহাদিম মির রুসুলিহ, ওয়া ক্বালূ সামি'না ওয়া আতা'না গুফরানাকা রাব্বানা ওয়া ইলাইকাল মাসীর",
                "রাসূল তাঁর প্রতিপালকের পক্ষ থেকে যা তাঁর নিকট অবতীর্ণ করা হয়েছে তার ওপর ঈমান এনেছেন এবং মুমিনগণও। তারা প্রত্যেকেই আল্লাহ, তাঁর ফেরেশতাগণ, তাঁর কিতাবসমূহ এবং তাঁর রাসূলগণের ওপর ঈমান এনেছে। তারা বলে: আমরা তাঁর রাসূলগণের কারো মধ্যে কোনো পার্থক্য করি না। এবং তারা বলে: আমরা শুনলাম ও মান্য করলাম; হে আমাদের প্রতিপালক! আমরা আপনার ক্ষমা প্রার্থনা করি এবং আপনারই দিকে প্রত্যাবর্তনস্থল।",
                "The Messenger has believed in what was revealed to him from his Lord, and [so have] the believers...",
                "https://everyayah.com/data/Alafasy_128kbps/002285.mp3"
            ),
            Ayah(
                "2_286", 2, 286,
                "لَا يُكَلِّفُ اللَّهُ نَفْسًا إِلَّا وُسْعَهَا ۚ لَهَا مَا كَسَبَتْ وَعَلَيْهَا مَا اكْتَسَبَتْ ۗ رَبَّنَا لَا تُؤَاخِذْنَا إِن نَّسِينَا أَوْ أَخْطَأْنَا ۚ رَبَّنَا وَلَا تَحْمِلْ عَلَيْنَا إِصْرًا كَمَا حَمَلْتَهُ عَلَى الَّذِينَ مِن قَبْلِنَا ۚ رَبَّنَا وَلَا تُحَمِّلْنَا مَا لَا طَاقَةَ لَنَا بِهِ ۖ وَاعْفُ عَنَّا وَاغْفِرْ لَنَا وَارْحَمْنَا ۚ أَنتَ مَوْلَانَا فَانصُرْنَا عَلَى الْقَوْمِ الْكَافِرِينَ",
                "লা ইউকাল্লিফুল্লাহু নাফসান ইল্লা উস'আহা, লাহা মা কাসাবাত ওয়া 'আলাইহা মাকতাসাবাত, রাব্বানা লা তুআখিযনা ইন নাসীনা আও আখতানা...",
                "আল্লাহ কাউকে তার সাধ্যাতীত কোনো কাজের ভার দেন না। সে যা সৎকাজ করে তা তার জন্যই এবং যা অসৎকাজ করে তা তারই ওপর বর্তাবে। হে আমাদের প্রতিপালক! যদি আমরা ভুলে যাই কিংবা ভুল করি তবে আমাদের পাকড়াও করবেন না। হে আমাদের প্রতিপালক! আমাদের ওপর এমন ভারী বোঝা অর্পণ করবেন না যেরূপ আমাদের পূর্ববর্তীদের ওপর অর্পণ করেছিলেন...",
                "Allah does not charge a soul except [with that within] its capacity. It will have [the consequence of] what [good] it has gained...",
                "https://everyayah.com/data/Alafasy_128kbps/002286.mp3"
            )
        ),
        108 to listOf(
            Ayah("108_1", 108, 1, "إِنَّا أَعْطَيْنَاكَ الْكَوْثَرَ", "ইন্না আ'তাইনা-কাল কাওসার", "নিশ্চয়ই আমি আপনাকে কাওসার (অফুরন্ত কল্যাণ ও হাউজে কাওসার) দান করেছি।", "Indeed, We have granted you, [O Muhammad], al-Kawthar.", "https://everyayah.com/data/Alafasy_128kbps/108001.mp3"),
            Ayah("108_2", 108, 2, "فَصَلِّ لِرَبِّكَ وَانْحَرْ", "ফাসাল্লি লিরাব্বিকা ওয়ানহার", "অতএব আপনার রবের উদ্দেশ্যেই সালাত আদায় করুন এবং কোরবানি করুন।", "So pray to your Lord and sacrifice [to Him alone].", "https://everyayah.com/data/Alafasy_128kbps/108002.mp3"),
            Ayah("108_3", 108, 3, "إِنَّ شَانِئَكَ هُوَ الْأَبْتَرُ", "ইন্না শানি'আকা হুওয়াল আবতার", "নিশ্চয়ই আপনার প্রতি শত্রুতা পোষণকারী ব্যক্তিই নির্বংশ ও মূলহীন।", "Indeed, your enemy is the one cut off.", "https://everyayah.com/data/Alafasy_128kbps/108003.mp3")
        ),
        103 to listOf(
            Ayah("103_1", 103, 1, "وَالْعَصْرِ", "ওয়াল 'আসর", "সময়ের শপথ,", "By time,", "https://everyayah.com/data/Alafasy_128kbps/103001.mp3"),
            Ayah("103_2", 103, 2, "إِنَّ الْإِنسَانَ لَفِي خُسْرٍ", "ইন্নাল ইনসানা লাফী খুসর", "নিশ্চয়ই সমস্ত মানুষ চরম ক্ষতির মধ্যে নিমজ্জিত,", "Indeed, mankind is in loss,", "https://everyayah.com/data/Alafasy_128kbps/103002.mp3"),
            Ayah("103_3", 103, 3, "إِلَّا الَّذِينَ آمَنُوا وَعَمِلُوا الصَّالِحَاتِ وَتَوَاصَوْا بِالْحَقِّ وَتَوَاصَوْا بِالصَّبْرِ", "ইল্লাল্লাযীনা আমানু ওয়া 'আমিলুস সালিহাতি ওয়া তাওয়াসাও বিল হাক্কি ওয়া তাওয়াসাও বিস-সাবর", "তারা ব্যতীত যারা ঈমান এনেছে, সৎকাজ করেছে এবং পরস্পরকে সত্যের উপদেশ দিয়েছে ও ধৈর্যের উপদেশ দিয়েছে।", "Except for those who have believed and done righteous deeds and advised each other to truth and advised each other to patience.", "https://everyayah.com/data/Alafasy_128kbps/103003.mp3")
        ),
        97 to listOf(
            Ayah("97_1", 97, 1, "إِنَّا أَنزَلْنَاهُ فِي لَيْلَةِ الْقَدْرِ", "ইন্না আনযালনাহু ফী লাইলাতিল ক্বাদর", "নিশ্চয়ই আমি একে (কুরআন মাজীদকে) নাযিল করেছি মহিমান্বিত কদরের রাতে।", "Indeed, We sent the Qur'an down during the Night of Decree.", "https://everyayah.com/data/Alafasy_128kbps/097001.mp3"),
            Ayah("97_2", 97, 2, "وَمَا أَدْرَاكَ مَا لَيْلَةُ الْقَدْرِ", "ওয়ামা আদরাকা মা লাইলাতুল ক্বাদর", "আর আপনি কি জানেন মহিমান্বিত কদরের রাত কী?", "And what can make you know what is the Night of Decree?", "https://everyayah.com/data/Alafasy_128kbps/097002.mp3"),
            Ayah("97_3", 97, 3, "لَيْلَةُ الْقَدْرِ خَيْرٌ مِّنْ أَلْفِ شَهْرٍ", "লাইলাতুল ক্বাদরি খাইরুম মিন আলফি শাহর", "কদরের রাত এক হাজার মাসের চেয়েও অধিক শ্রেষ্ঠ ও কল্যাণময়।", "The Night of Decree is better than a thousand months.", "https://everyayah.com/data/Alafasy_128kbps/097003.mp3"),
            Ayah("97_4", 97, 4, "تَنَزَّلُ الْمَلَائِكَةُ وَالرُّوحُ فِيهَا بِإِذْنِ رَبِّهِم مِّن كُلِّ أَمْرٍ", "তানাযযালুল মালাইকাতু ওয়ার-রূহু ফীহা বিইযনি রাব্বিহিম মিন কুল্লি আমর", "সে রাতে ফেরেশতাগণ ও রূহ (জিবরাঈল আ.) তাদের রবের অনুমতিক্রমে প্রত্যেক কল্যাণময় আদেশ নিয়ে অবতরণ করেন।", "The angels and the Spirit descend therein by permission of their Lord for every matter.", "https://everyayah.com/data/Alafasy_128kbps/097004.mp3"),
            Ayah("97_5", 97, 5, "سَلَامٌ هِيَ حَتَّىٰ مَطْلَعِ الْفَجْرِ", "সালামুন হিয়া হাত্তা মাতলা'ইল ফাজর", "শান্তিময় ও নিরাপদ এই মহিমান্বিত রাত, যা সুবহে সাদিক (ফজর) উদয় পর্যন্ত অব্যাহত থাকে।", "Peace it is until the emergence of dawn.", "https://everyayah.com/data/Alafasy_128kbps/097005.mp3")
        ),
        99 to listOf(
            Ayah("99_1", 99, 1, "إِذَا زُلْزِلَتِ الْأَرْضُ زِلْزَالَهَا", "ইযা যুলযিলাতিল আরদু যিলযালাহা", "যখন পৃথিবী তার ভয়াবহ কম্পনে প্রকম্পিত হবে,", "When the earth is shaken with its [final] earthquake", "https://everyayah.com/data/Alafasy_128kbps/099001.mp3"),
            Ayah("99_2", 99, 2, "وَأَخْرَجَتِ الْأَرْضُ أَثْقَالَهَا", "ওয়া আখরাজাতিল আরদু আসক্বালাহা", "এবং পৃথিবী তার ভেতরের ভারী বোঝা বের করে দেবে,", "And the earth discharges its burdens", "https://everyayah.com/data/Alafasy_128kbps/099002.mp3"),
            Ayah("99_3", 99, 3, "وَقَالَ الْإِنسَانُ مَا لَهَا", "ওয়া ক্বালাল ইনসানু মা লাহা", "আর মানুষ বলবে, এর কী হলো?", "And man says, \"What is [wrong] with it?\" -", "https://everyayah.com/data/Alafasy_128kbps/099003.mp3"),
            Ayah("99_7", 99, 7, "فَمَن يَعْمَلْ مِثْقَالَ ذَرَّةٍ خَيْرًا يَرَهُ", "ফামাইঁ ইয়া'মাল মিসক্বালা যাররাতিন খাইরাইঁ ইয়ারাহ", "অতএব কেউ অণু পরিমাণও সৎকর্ম করলে সে তা দেখতে পাবে,", "So whoever does an atom's weight of good will see it,", "https://everyayah.com/data/Alafasy_128kbps/099007.mp3"),
            Ayah("99_8", 99, 8, "وَمَن يَعْمَلْ مِثْقَالَ ذَرَّةٍ شَرًّا يَرَهُ", "ওয়ামাইঁ ইয়া'মাল মিসক্বালা যাররাতিন শাররাইঁ ইয়ারাহ", "আর কেউ অণু পরিমাণও মন্দকাজ করলে সে তাও দেখতে পাবে।", "And whoever does an atom's weight of evil will see it.", "https://everyayah.com/data/Alafasy_128kbps/099008.mp3")
        ),
        101 to listOf(
            Ayah("101_1", 101, 1, "الْقَارِعَةُ", "আল-ক্বারি'আহ", "মহা প্রলয়কারী আঘাত,", "The Striking Calamity -", "https://everyayah.com/data/Alafasy_128kbps/101001.mp3"),
            Ayah("101_2", 101, 2, "مَا الْقَارِعَةُ", "মাল ক্বারি'আহ", "মহা প্রলয়কারী আঘাত কী?", "What is the Striking Calamity?", "https://everyayah.com/data/Alafasy_128kbps/101002.mp3"),
            Ayah("101_6", 101, 6, "فَأَمَّا مَن ثَقُلَتْ مَوَازِينُهُ", "ফা আম্মা মান সাকুলাত মাওয়াযীনুহু", "অতঃপর যার নেক আমলের পাল্লা ভারী হবে,", "Then as for one whose scales are heavy [with good deeds],", "https://everyayah.com/data/Alafasy_128kbps/101006.mp3"),
            Ayah("101_7", 101, 7, "فَهُوَ فِي عِيشَةٍ رَّاضِيَةٍ", "ফাহুওয়া ফী 'ঈশাতির রাদিয়াহ", "সে তো থাকবে সন্তোষজনক পরম সুখময় জীবনে (জান্নাতে)।", "He will be in a pleasant life.", "https://everyayah.com/data/Alafasy_128kbps/101007.mp3")
        ),
        102 to listOf(
            Ayah("102_1", 102, 1, "أَلْهَاكُمُ التَّكَاثُرُ", "আলহাকুমুত-তাকাসুর", "পরস্পরের ধন ও সন্তান বৃদ্ধির প্রতিযোগিতা তোমাদের গাফেল করে রেখেছে,", "Competition in [worldly] increase diverts you", "https://everyayah.com/data/Alafasy_128kbps/102001.mp3"),
            Ayah("102_2", 102, 2, "حَتَّىٰ زُرْتُمُ الْمَقَابِرَ", "হাত্তা যুরতুমুল মাক্বাবির", "যতক্ষণ না তোমরা কবরস্থানে উপনীত হও।", "Until you visit the graveyards.", "https://everyayah.com/data/Alafasy_128kbps/102002.mp3"),
            Ayah("102_8", 102, 8, "ثُمَّ لَتُسْأَلُنَّ يَوْمَئِذٍ عَنِ النَّعِيمِ", "সুম্মা লাতুসআলুন্না ইয়াওমাইযিন 'আনিন-না'ঈম", "অতঃপর অবশ্যই সেদিন তোমাদেরকে প্রদত্ত যাবতীয় নিয়ামত সম্পর্কে প্রশ্ন করা হবে।", "Then you will surely be asked that Day about pleasure.", "https://everyayah.com/data/Alafasy_128kbps/102008.mp3")
        ),
        104 to listOf(
            Ayah("104_1", 104, 1, "وَيْلٌ لِّكُلِّ هُمَزَةٍ لُّمَزَةٍ", "ওয়াইলুল লিকুল্লি হুমাযাতিল লুমাযাহ", "ধ্বংস প্রত্যেকের জন্য, যে সামনে ও পেছনে মানুষের দুর্নাম ও পরনিন্দা করে,", "Woe to every scorner and mocker", "https://everyayah.com/data/Alafasy_128kbps/104001.mp3"),
            Ayah("104_2", 104, 2, "الَّذِي جَمَعَ مَالًا وَعَدَّدَهُ", "আল্লাযী জামা'আ মালাঁও ওয়া 'আদ্দাদাহ", "যে অর্থসম্পদ জমা করে এবং তা বারবার গণনা করে;", "Who collects wealth and [continuously] counts it.", "https://everyayah.com/data/Alafasy_128kbps/104002.mp3")
        ),
        67 to listOf(
            Ayah("67_1", 67, 1, "تَبَارَكَ الَّذِي بِيَدِهِ الْمُلْكُ وَهُوَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ", "তাবারাকাল্লাযী বিয়াদিহিল মুলকু ওয়াহুওয়া 'আলা কুল্লি শাইয়িন ক্বাদীর", "পরম বরকতময় ও মহিমান্বিত তিনি, যাঁর হাতে সমস্ত রাজত্ব ও সার্বভৌম কর্তৃত্ব; এবং তিনি সর্ববিষয়ে সর্বশক্তিমান।", "Blessed is He in whose hand is dominion, and He is over all things competent -", "https://everyayah.com/data/Alafasy_128kbps/067001.mp3"),
            Ayah("67_2", 67, 2, "الَّذِي خَلَقَ الْمَوْتَ وَالْحَيَاةَ لِيَبْلُوَكُمْ أَيُّكُمْ أَحْسَنُ عَمَلًا ۚ وَهُوَ الْعَزِيزُ الْغَفُورُ", "আল্লাযী খালাক্বাল মাওতা ওয়াল হায়াতা লিয়াবলুওয়াকুম আইয়্যুকুম আহসানু 'আমালা, ওয়াহুওয়াল 'আযীযুল গাফূর", "যিনি সৃষ্টি করেছেন মৃত্যু ও জীবন, তোমাদের পরীক্ষা করার জন্য যে কে তোমাদের মধ্যে কর্মে উত্তম; এবং তিনি পরাক্রমশালী, পরম ক্ষমাশীল।", "[He] who created death and life to test you [as to] which of you is best in deed - and He is the Exalted in Might, the Forgiving -", "https://everyayah.com/data/Alafasy_128kbps/067002.mp3"),
            Ayah("67_3", 67, 3, "الَّذِي خَلَقَ سَبْعَ سَمَاوَاتٍ طِبَاقًا ۖ مَّا تَرَىٰ فِي خَلْقِ الرَّحْمَٰنِ مِن تَفَاوُتٍ ۖ فَارْجِعِ الْبَصَرَ هَلْ تَرَىٰ مِن فُطُورٍ", "আল্লাযী খালাক্বা সাব'আ সামাওয়াতিম তিবা ক্বা, মা তারা ফী খালক্বির-রাহমানি মিন তাফাউত, ফারজি'ইল বাসারা হাল তারা মিন ফুতূর", "যিনি সাত আকাশ সৃষ্টি করেছেন স্তরে স্তরে। পরম দয়াময় আল্লাহর সৃষ্টিতে আপনি কোনো খুঁত দেখতে পাবেন না; দৃষ্টি ফিরিয়ে দেখুন, কোনো ফাটল দেখতে পান কি?", "[And] who created seven heavens in layers. You do not see in the creation of the Most Merciful any inconsistency...", "https://everyayah.com/data/Alafasy_128kbps/067003.mp3"),
            Ayah("67_4", 67, 4, "ثُمَّ ارْجِعِ الْبَصَرَ كَرَّتَيْنِ يَنقَلِبْ إِلَيْكَ الْبَصَرُ خَاسِئًا وَهُوَ حَسِيرٌ", "সুম্মার জি'ইল বাসারা কাররাতাইনি ইয়ানক্বালিব ইলাইকাল বাসারু খাসিআঁও ওয়াহুওয়া হাসীর", "অতঃপর আপনি বারবার দৃষ্টি নিক্ষেপ করুন, আপনার দৃষ্টি ব্যর্থ ও ক্লান্ত হয়ে আপনার দিকে ফিরে আসবে।", "Then return [your] vision twice again. [Your] vision will return to you humbled while it is fatigued.", "https://everyayah.com/data/Alafasy_128kbps/067004.mp3"),
            Ayah("67_5", 67, 5, "وَلَقَدْ زَيَّنَّا السَّمَاءَ الدُّنْيَا بِمَصَابِيحَ وَجَعَلْنَاهَا رُجُومًا لِّلشَّيَاطِينِ ۖ وَأَعْتَدْنَا لَهُمْ عَذَابَ السَّعِيرِ", "ওয়া লাক্বাদ যাইয়্যান্নাস-সামাআদ দুনইয়া বিমাসাবীহা ওয়া জা'আলনাহা রুজূমাল লিশ-শায়াতীন...", "এবং নিশ্চয়ই আমি নিকটবর্তী আকাশকে প্রদীপমালা (তারকারাজি) দ্বারা সুশোভিত করেছি এবং সেগুলোকে শয়তানদের বিতাড়নকারী ক্ষেপণাস্ত্র স্বরূপ বানিয়েছি...", "And We have certainly beautified the nearest heaven with stars and have made [from] them what is thrown at the devils...", "https://everyayah.com/data/Alafasy_128kbps/067005.mp3")
        ),
        36 to listOf(
            Ayah("36_1", 36, 1, "يس", "ইয়াসীন", "ইয়াসীন।", "Ya, Seen.", "https://everyayah.com/data/Alafasy_128kbps/036001.mp3"),
            Ayah("36_2", 36, 2, "وَالْقُرْآنِ الْحَكِيمِ", "ওয়াল ক্বুরআনিল হাকীম", "প্রজ্ঞাপূর্ণ কুরআনের শপথ,", "By the wise Qur'an.", "https://everyayah.com/data/Alafasy_128kbps/036002.mp3"),
            Ayah("36_3", 36, 3, "إِنَّكَ لَمِنَ الْمُرْسَلِينَ", "ইন্নাকা লামিনাল মুরসালীন", "নিশ্চয়ই আপনি রাসূলগণের একজন,", "Indeed you, [O Muhammad], are from among the messengers,", "https://everyayah.com/data/Alafasy_128kbps/036003.mp3"),
            Ayah("36_4", 36, 4, "عَلَىٰ صِرَاطٍ مُّسْتَقِيمٍ", "আলা সিরাতিম মুস্তাক্বীম", "সরল ও সঠিক পথের ওপর প্রতিষ্ঠিত।", "On a straight path.", "https://everyayah.com/data/Alafasy_128kbps/036004.mp3"),
            Ayah("36_5", 36, 5, "تَنزِيلَ الْعَزِيزِ الرَّحِيمِ", "তানযীলাল 'আযীযির রাহীম", "এ কুরআন পরাক্রমশালী, পরম দয়ালু আল্লাহর পক্ষ থেকে অবতীর্ণ,", "[This is] a revelation of the Exalted in Might, the Merciful,", "https://everyayah.com/data/Alafasy_128kbps/036005.mp3"),
            Ayah("36_6", 36, 6, "لِتُنذِرَ قَوْمًا مَّا أُنذِرَ آبَاؤُهُمْ فَهُمْ غَافِلُونَ", "লিতুনযিরা ক্বাওমাম মা উনযিরা আবাউহুম ফাহুম গাফিলূন", "যাতে আপনি এমন এক জাতিকে সতর্ক করতে পারেন, যাদের পূর্বপুরুষদের সতর্ক করা হয়নি, ফলে তারা অসচেতন ছিল।", "That you may warn a people whose forefathers were not warned, so they are unaware.", "https://everyayah.com/data/Alafasy_128kbps/036006.mp3")
        ),
        55 to listOf(
            Ayah("55_1", 55, 1, "الرَّحْمَٰنُ", "আর-রাহমান", "পরম দয়াময় আল্লাহ,", "The Most Merciful", "https://everyayah.com/data/Alafasy_128kbps/055001.mp3"),
            Ayah("55_2", 55, 2, "عَلَّمَ الْقُرْآنَ", "'আল্লামাল ক্বুরআন", "তিনিই শিক্ষা দিয়েছেন পবিত্র কুরআন,", "Taught the Qur'an,", "https://everyayah.com/data/Alafasy_128kbps/055002.mp3"),
            Ayah("55_3", 55, 3, "خَلَقَ الْإِنسَانَ", "খালাক্বাল ইনসান", "তিনি সৃষ্টি করেছেন মানুষকে,", "Created man,", "https://everyayah.com/data/Alafasy_128kbps/055003.mp3"),
            Ayah("55_4", 55, 4, "عَلَّمَهُ الْبَيَانَ", "'আল্লামাহুল বায়ান", "তাকে শিখিয়েছেন ভাব প্রকাশ ও বর্ণনা করার ক্ষমতা।", "[And] taught him eloquence.", "https://everyayah.com/data/Alafasy_128kbps/055004.mp3"),
            Ayah("55_13", 55, 13, "فَبِأَيِّ آلَاءِ رَبِّكُمَا تُكَذِّبَانِ", "ফাবিআইয়্যি আলা-ই রাব্বিকুমা তুকায়্যিবান", "অতএব (হে মানুষ ও জিন জাতি!) তোমরা তোমাদের রবের কোন কোন নিয়ামতকে অস্বীকার করবে?", "So which of the favors of your Lord would you deny?", "https://everyayah.com/data/Alafasy_128kbps/055013.mp3")
        )
    )

    fun getAyahsForSurah(surahNumber: Int): List<Ayah> {
        return sampleAyahs[surahNumber] ?: emptyList()
    }

    /**
     * Search across all available Ayahs by query (in Bengali translation, pronunciation, Arabic text, or English)
     */
    fun searchAyahs(query: String): List<Pair<Surah, Ayah>> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return emptyList()

        val results = mutableListOf<Pair<Surah, Ayah>>()
        for (surah in allSurahs) {
            val ayahs = getAyahsForSurah(surah.number)
            for (ayah in ayahs) {
                if (ayah.translationBn.lowercase().contains(q) ||
                    ayah.transliterationBn.lowercase().contains(q) ||
                    ayah.arabicText.contains(query.trim()) ||
                    ayah.translationEn.lowercase().contains(q) ||
                    surah.nameBn.lowercase().contains(q) ||
                    surah.nameEn.lowercase().contains(q)
                ) {
                    results.add(surah to ayah)
                }
            }
        }
        return results
    }

    /**
     * Returns all available Ayahs paired with their Surah metadata
     */
    fun getAllAvailableAyahs(): List<Pair<Surah, Ayah>> {
        val list = mutableListOf<Pair<Surah, Ayah>>()
        for (surah in allSurahs) {
            val ayahs = getAyahsForSurah(surah.number)
            for (ayah in ayahs) {
                list.add(surah to ayah)
            }
        }
        return list
    }
}
