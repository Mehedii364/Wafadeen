package com.example.repository

import com.example.R
import com.example.data.model.DailyPrayerStructure
import com.example.data.model.SalahCategory
import com.example.data.model.SalahGuideItem

object SalahRepository {

    val dailyPrayersStructure = listOf(
        DailyPrayerStructure(
            nameBn = "ফজর (Fajr)",
            nameEn = "Fajr",
            nameAr = "الفجر",
            totalRakats = 4,
            sunnahBefore = 2, // সুন্নাতে মুয়াক্কাদা
            fard = 2,
            sunnahAfter = 0,
            timeDescriptionBn = "সুবহে সাদিক হতে সূর্যোদয়ের পূর্ব পর্যন্ত",
            timeDescriptionEn = "From true dawn until sunrise",
            specialNotesBn = "ফজরের ২ রাকাত সুন্নাত অত্যন্ত গুরুত্বপূর্ণ (সুন্নাতে মুয়াক্কাদা)। রাসুলুল্লাহ (সা.) বলেছেন, 'ফজরের দুই রাকাত দুনিয়া ও তার মধ্যকার সবকিছুর চেয়ে উত্তম।' (সহিহ মুসলিম ৭২৫)"
        ),
        DailyPrayerStructure(
            nameBn = "জোহর (Dhuhr)",
            nameEn = "Dhuhr",
            nameAr = "الظهر",
            totalRakats = 12,
            sunnahBefore = 4,
            fard = 4,
            sunnahAfter = 2,
            nafl = 2,
            timeDescriptionBn = "সূর্য মধ্যাকাশ থেকে পশ্চিম আকাশে ঢলে পড়লে শুরু হয় এবং কোনো বস্তুর ছায়া তার মূল ছায়া বাদে দ্বিগুণ (হানাফি) বা সমপরিমাণ (জমহুর) হওয়া পর্যন্ত থাকে।",
            timeDescriptionEn = "Begins when the sun passes zenith until an object's shadow equals its length or twice its length (Hanafi).",
            specialNotesBn = "জুমু'আর দিন জোহরের ৪ রাকাত ফরজের পরিবর্তে ২ রাকাত জুমু'আর ফরজ সালাত আদায় করতে হয়।"
        ),
        DailyPrayerStructure(
            nameBn = "আসর (Asr)",
            nameEn = "Asr",
            nameAr = "العصر",
            totalRakats = 8,
            sunnahBefore = 4, // সুন্নাতে গায়রে মুয়াক্কাদা
            fard = 4,
            sunnahAfter = 0,
            timeDescriptionBn = "জোহরের ওয়াক্ত শেষ হওয়া হতে সূর্যাস্তের পূর্ব পর্যন্ত।",
            timeDescriptionEn = "From the end of Dhuhr until just before sunset.",
            specialNotesBn = "আসরের ৪ রাকাত ফরজ নামাজ অত্যন্ত গুরুত্বপূর্ণ। আসরের পূর্বে ৪ রাকাত নফল বা গায়রে মুয়াক্কাদা সুন্নাত আদায় করা মুস্তাহাব।"
        ),
        DailyPrayerStructure(
            nameBn = "মাগরিব (Maghrib)",
            nameEn = "Maghrib",
            nameAr = "المغرب",
            totalRakats = 7,
            sunnahBefore = 0,
            fard = 3,
            sunnahAfter = 2,
            nafl = 2,
            timeDescriptionBn = "সূর্যাস্তের পর হতে পশ্চিম আকাশের লাল আভা বিলীন হওয়া পর্যন্ত।",
            timeDescriptionEn = "From sunset until the red twilight disappears.",
            specialNotesBn = "মাগরিবের ৩ রাকাত ফরজ প্রথমে আদায় করতে হয়, এরপর ২ রাকাত সুন্নাতে মুয়াক্কাদা ও ২ রাকাত নফল (আউয়াবিন)।"
        ),
        DailyPrayerStructure(
            nameBn = "এশা ও বিতর (Isha & Witr)",
            nameEn = "Isha & Witr",
            nameAr = "العشاء والوتر",
            totalRakats = 17,
            sunnahBefore = 4,
            fard = 4,
            sunnahAfter = 2,
            nafl = 4,
            witr = 3,
            timeDescriptionBn = "পশ্চিম আকাশের শুভ্রতা বিলীন হওয়ার পর থেকে সুবহে সাদিকের পূর্ব পর্যন্ত।",
            timeDescriptionEn = "From the disappearance of twilight until true dawn.",
            specialNotesBn = "হানাফি মাযহাবে ৩ রাকাত বিতরের নামাজ ওয়াজিব। ৩য় রাকাতে সূরা ফাতিহা ও কিরাতের পর তাকবির বলে হাত উঠিয়ে দু'আ কুনুত পাঠ করা হয়।"
        )
    )

    val salahGuideItems = listOf(
        // Purification: Wudu
        SalahGuideItem(
            id = "purification_wudu",
            titleBn = "অজু করার সঠিক ও পূর্ণাঙ্গ পদ্ধতি",
            titleEn = "Step-by-Step Method of Wudu (Ablution)",
            titleAr = "صفة الوضوء",
            category = SalahCategory.PURIFICATION,
            arabicText = "أَشْهَدُ أَنْ لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا عَبْدُهُ وَرَسُولُهُ، اللَّهُمَّ اجْعَلْنِي مِنَ التَّوَّابِينَ وَاجْعَلْنِي مِنَ الْمُتَطَهِّرِينَ",
            transliterationBn = "আশহাদু আল-লা-ইলাহা ইল্লাল্লাহু ওয়াহদাহু লা শারীকা লাহু ওয়া আশহাদু আন্না মুহাম্মাদান 'আবদুহু ওয়া রাসুলুহু। আল্লাহুম্মাজ'আলনী মিনাত-তাউয়্যাবীন ওয়াজ'আলনী মিনাল মুতাত্বহহিরীন।",
            meaningBn = "আমি সাক্ষ্য দিচ্ছি আল্লাহ ছাড়া কোনো উপাস্য নেই, তিনি একক, তাঁর কোনো অংশীদার নেই। আর আমি সাক্ষ্য দিচ্ছি মুহাম্মদ (সা.) তাঁর বান্দা ও রাসুল। হে আল্লাহ! আমাকে তওবাকারীদের অন্তর্ভুক্ত করুন এবং পবিত্রতা অর্জনকারীদের অন্তর্ভুক্ত করুন।",
            meaningEn = "I testify that none has the right to be worshipped except Allah alone, without partner; and I testify that Muhammad is His slave and Messenger. O Allah, make me of the repentant and make me of the purified.",
            explanationBn = "অজুর ৪টি ফরজ:\n১. সম্পূর্ণ মুখমণ্ডল ধোয়া (কপালের চুলের গোড়া থেকে থুতনির নিচ এবং এক কানের লতি থেকে অন্য কান পর্যন্ত)।\n২. উভয় হাত কনুইসহ ধোয়া।\n৩. মাথার চারভাগের একভাগ মাসেহ করা (হানাফি)।\n৪. উভয় পা টাখনুসহ ধোয়া।\n\nসুন্নাতসমূহ:\nবিসমিল্লাহ বলে শুরু করা, মেসওয়াক করা, কব্জি পর্যন্ত হাত ৩ বার ধোয়া, কুলি করা ৩ বার, নাকে পানি দেওয়া ৩ বার, কান মাসেহ করা ও উপরোক্ত দো'আটি পাঠ করা।",
            explanationEn = "The 4 obligatory (Fard) acts of Wudu are: washing the whole face, washing both arms up to the elbows, wiping the head, and washing both feet up to the ankles. Sunnah acts include starting with Bismillah, rinsing mouth, sniffing water into the nose, and reciting the dua after Wudu.",
            isObligatory = true,
            hanafiNote = "মাথার চারভাগের একভাগ মাসেহ করা ফরজ। গর্দান মাসেহ করা মুস্তাহাব।",
            otherMadhhabsNote = "শাফেঈ মাযহাবে মাথার সামান্য অংশ ভিজালেই আদায় হয়, মালেকি ও হাম্বলি মাযহাবে পুরো মাথা মাসেহ করা ওয়াজিব।",
            reference = "সূরা মায়িদা: ৬; সহিহ মুসলিম: ২৩৪; তিরমিজি: ৫৫",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/005006.mp3"
        ),

        // Purification: Ghusl
        SalahGuideItem(
            id = "purification_ghusl",
            titleBn = "ফরজ গোসলের নিয়ম",
            titleEn = "Method of Obligatory Bath (Ghusl)",
            titleAr = "صفة الغسل",
            category = SalahCategory.PURIFICATION,
            arabicText = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
            transliterationBn = "বিসমিল্লাহির রাহমানির রাহিম",
            meaningBn = "পরম করুণাময় অসীম দয়ালু আল্লাহর নামে শুরু করছি।",
            meaningEn = "In the name of Allah, the Most Gracious, the Most Merciful.",
            explanationBn = "গোসলের ৩টি ফরজ:\n১. গড়গড়ার সাথে ভালোভাবে কুলি করা (রোজাদার হলে সতর্কতার সাথে)।\n২. নাকের নরম হাড় পর্যন্ত পানি পৌঁছে নাক পরিষ্কার করা।\n৩. শরীরের প্রতিটি অঙ্গের চুলের গোড়ায় পানি পৌঁছানো যাতে একবিন্দু জায়গাও শুকনো না থাকে।\n\nগোসলের সুন্নাত পদ্ধতি:\nপ্রথমে উভয় হাত কব্জি পর্যন্ত ধোয়া, লজ্জাস্থান পরিষ্কার করা, সাধারণ অজুর মতো অজু করা (পা ধোয়া ছাড়া বা শেষে ধোয়া), এরপর পুরো মাথায় ৩ বার পানি ঢালা এবং সারা শরীর ভালোভাবে ধৌত করা।",
            explanationEn = "The 3 fard steps of Ghusl: 1. Thoroughly rinsing the mouth with gargling. 2. Sniffing water into the nose up to the soft bone. 3. Pouring water over the entire body so no spot remains dry.",
            isObligatory = true,
            reference = "সহিহ বুখারী: ২৪৮; সহিহ মুসলিম: ৩১৬",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/004043.mp3"
        ),

        // Step 1: Standing on Prayer Mat (Qiyam & Niyyah)
        SalahGuideItem(
            id = "step_jaynamaz",
            titleBn = "১. জায়নামাজে দাঁড়ানো ও প্রস্তুতি (কিয়াম)",
            titleEn = "1. Standing on the Prayer Mat (Qiyam & Preparation)",
            titleAr = "القيام واستقبال القبلة",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "إِنِّي وَجَّهْتُ وَجْهِيَ لِلَّذِي فَطَرَ السَّمَاوَاتِ وَالأَرْضَ حَنِيفًا وَمَا أَنَا مِنَ الْمُشْرِكِينَ",
            transliterationBn = "ইন্নী ওয়াজ্জাহতু ওয়াজহিয়া লিল্লাযী ফাতারাস-সামাওয়াতি ওয়াল-আরদা হানীফাঁও ওয়ামা আনা মিনাল মুশরিকীন।",
            meaningBn = "নিশ্চয়ই আমি একনিষ্ঠভাবে আমার মুখমণ্ডল সেই মহান সত্তার দিকে ফেরাচ্ছি যিনি আসমানসমূহ ও জমিন সৃষ্টি করেছেন, এবং আমি অংশীবাদীদের (মুশরিকদের) অন্তর্ভুক্ত নই।",
            meaningEn = "Indeed, I have turned my face toward Him who created the heavens and the earth, inclining toward truth, and I am not of the polytheists.",
            explanationBn = "পবিত্র শরীরে, পবিত্র পোশাকে এবং পবিত্র জায়নামাজে কিবলামুখী হয়ে সোজা হয়ে দাঁড়ানো। দুই পায়ের মাঝে ৪ আঙুল পরিমাণ বা স্বাভাবিক ফাঁকা রাখা। দৃষ্টি থাকবে সিজদার স্থানে। মনে মনে যে ওয়াক্তের ও যে ধরণের (ফরজ/সুন্নাত) নামাজ আদায় করছেন তার দৃঢ় সংকল্প বা নিয়ত করা।",
            explanationEn = "Standing straight on a clean prayer mat facing the Qibla with a clean body and clothes. Keeping a natural spacing between feet and focusing the gaze upon the spot of prostration.",
            isObligatory = true,
            hanafiNote = "জায়নামাজে দাঁড়িয়ে মনে মনে সংকল্প করাই নিয়ত। মুখে কোনো নির্দিষ্ট বাক্য উচ্চারণ আবশ্যক নয়, তবে অন্তরে সচেতন থাকা ফরজ।",
            otherMadhhabsNote = "চার মাযহাবের সর্বসম্মত মত অনুযায়ী নামাজের জন্য কিবলামুখী হয়ে কিয়াম (দাঁড়ানো) ফরজ।",
            reference = "সূরা আল-আন'আম: ৭৯; সহিহ বুখারী: ৫০৮",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/006079.mp3",
            imageResId = R.drawable.img_salah_qiyam,
            postureName = "জায়নামাজে কিয়াম / সোজা হয়ে দাঁড়ানো"
        ),

        // Step 2: Takbir Tahrimah
        SalahGuideItem(
            id = "step_takbir",
            titleBn = "২. তাকবীরে তাহরীমা ও হাত বাঁধা",
            titleEn = "2. Takbir Tahrimah & Folding Hands",
            titleAr = "تكبيرة الإحرام ووضع اليدين",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "اللَّهُ أَكْبَرُ",
            transliterationBn = "আল্লাহু আকবার",
            meaningBn = "আল্লাহ সর্বশ্রেষ্ঠ / মহানতম।",
            meaningEn = "Allah is the Greatest.",
            explanationBn = "কিবলামুখী হয়ে উভয় হাত কান পর্যন্ত (পুরুষ) বা কাঁধ পর্যন্ত (মহিলা) উঠিয়ে 'আল্লাহু আকবার' বলা এবং হাত বাঁধা। এর মাধ্যমে নামাজের বাইরের কথা ও কাজকর্ম হারাম হয়ে যায়। পুরুষরা নাভির নিচে ডান হাতের তালু বাম হাতের পিঠের ওপর রেখে কব্জি ধরে হাত বাঁধবে।",
            explanationEn = "Raising hands to earlobes (men) or shoulders (women), declaring Allahu Akbar, and folding hands.",
            isObligatory = true,
            hanafiNote = "পুরুষরা কানের লতি পর্যন্ত হাত উঠিয়ে নাভির নিচে বাঁধবেন; মহিলারা কাঁধ পর্যন্ত উঠিয়ে বুকের ওপর রাখবেন।",
            otherMadhhabsNote = "শাফেঈ ও হাম্বলি মাযহাবে বুকের ওপর বা নাভির ওপরে হাত বাঁধা হয় এবং হাত কাঁধ বরাবর উঠানো হয়।",
            reference = "সহিহ বুখারী: ৭৩৭; সহিহ মুসলিম: ৩৯০",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/017111.mp3",
            imageResId = R.drawable.img_salah_qiyam,
            postureName = "তাকবীরে তাহরীমা ও কিরাতের জন্য হাত বাঁধা"
        ),

        // Step 3: Sana
        SalahGuideItem(
            id = "step_sana",
            titleBn = "৩. ছানা (তাওজীহ / প্রারম্ভিক দু'আ)",
            titleEn = "3. Sana (Opening Supplication)",
            titleAr = "دعاء الاستفتاح (الثناء)",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "سُبْحَانَكَ اللَّهُمَّ وَبِحَمْدِكَ وَتَبَارَكَ اسْمُكَ وَتَعَالَى جَدُّكَ وَلَا إِلَهَ غَيْرُكَ",
            transliterationBn = "সুবহানাকা আল্লাহুম্মা ওয়া বিহামদিকা, ওয়া তাবারাকাসমুকা, ওয়া তা'আলা জাদ্দুকা, ওয়া লা ইলাহা গাইরুক।",
            meaningBn = "হে আল্লাহ! আমি আপনার প্রশংসার সাথে আপনার পবিত্রতা ঘোষণা করছি। আপনার নাম বরকতময়, আপনার মর্যাদা সুউচ্চ এবং আপনি ছাড়া কোনো সত্য উপাস্য নেই।",
            meaningEn = "Glory be to You, O Allah, and praise. Blessed is Your name and exalted is Your majesty, and there is no deity worthy of worship besides You.",
            explanationBn = "তাকবীরে তাহরীমার পর হাত বেঁধে প্রথম রাকাতে সূরা ফাতিহার পূর্বে নিঃশব্দে ছানা পাঠ করা সুন্নাত।",
            explanationEn = "Recited silently right after folding hands in the first rak'ah before Surah Al-Fatihah.",
            isObligatory = false,
            hanafiNote = "হানাফি ও হাম্বলি মাযহাবে এই দু'আটি ছানা হিসেবে সুন্নাত।",
            reference = "সুনানে তিরমিজি: ২৪৩; সুনানে আবু দাউদ: ৭৭৫ (হাসান হাদিস)",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/059023.mp3",
            imageResId = R.drawable.img_salah_qiyam,
            postureName = "কিয়াম অবস্থায় ছানা পাঠ"
        ),

        // Step 4: Ta'awwudh & Tasmiyah
        SalahGuideItem(
            id = "step_taawwudh",
            titleBn = "৪. তা'আউওজ ও তাসমিয়াহ",
            titleEn = "4. Ta'awwudh & Tasmiyah",
            titleAr = "التعوذ والبسملة",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "أَعُوذُ بِاللَّهِ مِنَ الشَّيْطَانِ الرَّجِيمِ • بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
            transliterationBn = "আ'উযু বিল্লাহি মিনাশ-শাইত্বানির রাজীম। বিসমিল্লাহির রাহমানির রাহীম।",
            meaningBn = "আমি বিতাড়িত শয়তান থেকে আল্লাহর আশ্রয় প্রার্থনা করছি। পরম করুণাময় অসীম দয়ালু আল্লাহর নামে শুরু করছি।",
            meaningEn = "I seek refuge in Allah from Satan, the accursed. In the name of Allah, the Most Gracious, the Most Merciful.",
            explanationBn = "ছানা পড়ার পর মনে মনে তা'আউওজ ও তাসমিয়াহ পাঠ করতে হয়। প্রতিটি রাকাতেই কিরাতের পূর্বে বিসমিল্লাহ পাঠ করা সুন্নাত।",
            explanationEn = "Seeking refuge from Satan and invoking Allah's name before reciting the Quran in prayer.",
            isObligatory = false,
            reference = "সূরা আন-নাহল: ৯৮; সহিহ মুসলিম: ৩৯৯",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/016098.mp3",
            imageResId = R.drawable.img_salah_qiyam,
            postureName = "কিয়াম অবস্থায় আশ্রয় প্রার্থনা ও বিসমিল্লাহ"
        ),

        // Step 5: Surah Al-Fatihah & Additional Surah
        SalahGuideItem(
            id = "step_fatihah",
            titleBn = "৫. সূরা আল-ফাতিহা ও অতিরিক্ত কিরাত",
            titleEn = "5. Surah Al-Fatihah & Additional Surah",
            titleAr = "سورة الفاتحة وما تيسر من القرآن",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ﴿١﴾ الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ ﴿٢﴾ الرَّحْمَٰنِ الرَّحِيمِ ﴿٣﴾ مَالِكِ يَوْمِ الدِّينِ ﴿٤﴾ إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ ﴿٥﴾ اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ ﴿٦﴾ صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ ﴿٧﴾",
            transliterationBn = "১. বিসমিল্লাহির রাহমানির রাহীম।\n২. আলহামদু লিল্লাহি রাব্বিল 'আলামীন।\n৩. আর-রাহমানির রাহীম।\n৪. মালিকি ইয়াওমিদ্দীন।\n৫. ইয়্যাকা না'বুদু ওয়া ইয়্যাকা নাসতা'ঈন।\n৬. ইহদিনাস-সিরাতাল মুস্তাক্বীম।\n৭. সিরাতাল্লাযীনা আন'আমতা 'আলাইহিম গাইরিল মাগদূবি 'আলাইহিম ওয়ালাদ্দোয়াল্লীন। (আমীন)",
            meaningBn = "১. পরম করুণাময়, অসীম দয়ালু আল্লাহর নামে।\n২. সমস্ত প্রশংসা আল্লাহর জন্য, যিনি সকল সৃষ্টিজগতের প্রতিপালক।\n৩. পরম করুণাময়, পরম দয়ালু।\n৪. বিচার দিবসের একমাত্র অধিপতি।\n৫. আমরা কেবল আপনারই ইবাদত করি এবং কেবল আপনারই নিকট সাহায্য প্রার্থনা করি।\n৬. আমাদের সরল-সঠিক পথ প্রদর্শন করুন।\n৭. তাদের পথ, যাদের আপনি পুরস্কৃত করেছেন; তাদের নয়, যারা ক্রোধের শিকার বা বিভ্রান্ত।",
            meaningEn = "In the name of Allah, the Entirely Merciful, the Especially Merciful. [All] praise is [due] to Allah, Lord of the worlds. The Entirely Merciful, the Especially Merciful. Sovereign of the Day of Recompense. It is You we worship and You we ask for help. Guide us to the straight path. The path of those upon whom You have bestowed favor, not of those who have evoked [Your] anger or of those who are astray.",
            explanationBn = "নামাজের প্রতি রাকাতে সূরা ফাতিহা পাঠ করা ওয়াজিব/ফরজ। সূরা ফাতিহা শেষ হলে নিঃশব্দে 'আমীন' বলা সুন্নাত। প্রথম দুই রাকাতে সূরা ফাতিহার সাথে কুরআন মাজীদের অন্য কোনো সূরা (যেমন সূরা ইখলাস, ফালাক্ব বা নাস) মিলিয়ে পাঠ করতে হয়।",
            explanationEn = "Surah Al-Fatihah is recited in every rak'ah, followed by saying Ameen and reciting an additional Surah in the first two units.",
            isObligatory = true,
            hanafiNote = "হানাফি মাযহাবে জামাতে ইমামের পেছনে মুক্তাদির সূরা ফাতিহা পাঠ করতে হয় না (ইমামের কিরাতই মুক্তাদির কিরাত)। আমীন মনে মনে আস্তে বলা হয়।",
            otherMadhhabsNote = "শাফেঈ মাযহাবে মুক্তাদির জন্যও সূরা ফাতিহা ফরজ এবং জেহরি নামাজে উচ্চৈঃস্বরে আমীন বলা হয়।",
            reference = "সহিহ বুখারী: ৭৫৬; সহিহ মুসলিম: ৩৯৪",
            audioUrl = "https://server8.mp3quran.net/afs/001.mp3",
            imageResId = R.drawable.img_salah_qiyam,
            postureName = "কিয়াম অবস্থায় পবিত্র কোরআন তেলাওয়াত"
        ),

        // Step 6: Ruku
        SalahGuideItem(
            id = "step_ruku",
            titleBn = "৬. রুকু ও রুকুর তাসবীহ",
            titleEn = "6. Ruku (Bowing) & Tasbih",
            titleAr = "الركوع والتسبيح فيه",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "سُبْحَانَ رَبِّيَ الْعَظِيمِ",
            transliterationBn = "সুবহানা রাব্বিয়াল 'আযীম (কমপক্ষে ৩ বার)",
            meaningBn = "আমার মহান প্রতিপালকের পবিত্রতা ও মহিমা ঘোষণা করছি।",
            meaningEn = "Glory be to my Lord, the Magnificent (recited at least 3 times).",
            explanationBn = "কিরাত শেষ করে 'আল্লাহু আকবার' বলে রুকুতে অবনত হওয়া। পিঠ সোজা ও অনুভূমিক রাখা, মাথা পিঠের সমান্তরালে রাখা (উঁচু বা নিচু না করা)। দুই হাতের আঙুল ফাঁকা রেখে হাঁটু মজবুতভাবে ধরা। রুকুতে স্থির থেকে অন্তত তিনবার 'সুবহানা রাব্বিয়াল আজিম' বলা সুন্নাত।",
            explanationEn = "Bowing with back horizontal, head level with back, hands firmly gripping knees with fingers spread, reciting Subhana Rabbiyal Azeem at least 3 times.",
            isObligatory = true,
            hanafiNote = "পুরুষরা দুই হাতের আঙুল ছড়িয়ে হাঁটু আঁকড়ে ধরবে এবং কনুই সোজা রাখবে। মহিলারা শুধু হাত হাঁটুর ওপর রাখবে এবং সামান্য অবনত হবে।",
            reference = "সহিহ মুসলিম: ৭৭২; সুনানে আবু দাউদ: ৮৬৯",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/056074.mp3",
            imageResId = R.drawable.img_salah_ruku,
            postureName = "রুকূ / বিনম্র চিত্তে অবনত হওয়া"
        ),

        // Step 7: Qawmah (Rising from Ruku)
        SalahGuideItem(
            id = "step_qawmah",
            titleBn = "৭. কওমা (রুকু হতে সোজা হয়ে দাঁড়ানো)",
            titleEn = "7. Qawmah (Rising Upright from Ruku)",
            titleAr = "الرفع من الركوع والاعتدال (القومه)",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "سَمِعَ اللَّهُ لِمَنْ حَمِدَهُ • رَبَّنَا لَكَ الْحَمْدُ حَمْدًا كَثِيرًا طَيِّبًا مُبَارَكًا فِيهِ",
            transliterationBn = "সামি'আল্লাহু লিমান হামিদাহ। রাব্বানা লাকাল হামদ, হামদান কাসীরান ত্বাইয়্যিবান মুবারাকান ফীহ।",
            meaningBn = "আল্লাহ শোনেন যে তাঁর প্রশংসা করে। হে আমাদের রব! আপনার জন্যই সমস্ত প্রশংসা—অগণিত, পবিত্র ও বরকতপূর্ণ প্রশংসা।",
            meaningEn = "Allah hears whoever praises Him. Our Lord, to You belongs all praise, abundant, good and blessed praise.",
            explanationBn = "রুকু থেকে ওঠার সময় 'সামি'আল্লাহু লিমান হামিদাহ' বলে সম্পূর্ণ সোজা হয়ে দাঁড়ানো এবং স্থির হয়ে 'রাব্বানা লাকাল হামদ...' বলা ওয়াজিব (কওমায় স্থিরতা তা'দীলে আরকানের অংশ)।",
            explanationEn = "Rising completely upright from bowing and pausing steadily before going into prostration.",
            isObligatory = true,
            hanafiNote = "একাকী নামাজি তাসমি' ও তাহমিদ উভয়টি বলবে; জামাতে ইমাম 'সামি'আল্লাহু লিমান হামিদাহ' বলবে এবং মুক্তাদি কেবল 'রাব্বানা লাকাল হামদ' বলবে। হাত ঝুলানো অবস্থায় থাকবে।",
            reference = "সহিহ বুখারী: ৭৯৫, ৭৯৯",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/002186.mp3",
            imageResId = R.drawable.img_salah_qiyam,
            postureName = "কওমা / রুকু হতে সোজা হয়ে স্থির দাঁড়ানো"
        ),

        // Step 8: Sujud (First Prostration)
        SalahGuideItem(
            id = "step_sujud",
            titleBn = "৮. প্রথম সেজদা ও সিজদার তাসবীহ",
            titleEn = "8. First Sujud (Prostration) & Tasbih",
            titleAr = "السجدة الأولى والتسبيح",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "سُبْحَانَ رَبِّيَ الأَعْلَى",
            transliterationBn = "সুবহানা রাব্বিয়াল আ'লা (কমপক্ষে ৩ বার)",
            meaningBn = "আমার সর্বোচ্চ মহান প্রতিপালকের পবিত্রতা ঘোষণা করছি।",
            meaningEn = "Glory be to my Lord, the Most High (recited at least 3 times).",
            explanationBn = "'আল্লাহু আকবার' বলে সেজদায় যাওয়া। প্রথমে হাঁটু, তারপর হাত, সবশেষে কপাল ও নাক মাটিতে রাখা। সেজদায় শরীরের ৭টি অঙ্গ মাটিতে স্পর্শ করানো ফরজ: ১-২. দুই পায়ের আঙুল (কিবলামুখী), ৩-৪. দুই হাঁটু, ৫-৬. দুই হাতের তালু, এবং ৭. কপাল ও নাক। সেজদায় অন্তত ৩ বার 'সুবহানা রাব্বিয়াল আ'লা' বলা সুন্নাত।",
            explanationEn = "Prostrating upon 7 limbs: toes, knees, palms, and forehead with nose. Reciting Subhana Rabbiyal A'la at least 3 times.",
            isObligatory = true,
            hanafiNote = "পুরুষরা বাহু বগল থেকে এবং পেট উরু থেকে পৃথক রাখবে। মহিলারা জড়োসড়ো হয়ে অঙ্গগুলো কাছাকাছি রেখে মাটি ঘেঁষে সেজদা করবে।",
            reference = "সহিহ বুখারী: ৮১২; সহিহ মুসলিম: ৪৯১",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/087001.mp3",
            imageResId = R.drawable.img_salah_sujud,
            postureName = "প্রথম সেজদাহ / মাটিতে বিনম্র আত্মসমর্পণ"
        ),

        // Step 9: Jalsah (Sitting between two prostrations)
        SalahGuideItem(
            id = "step_jalsah",
            titleBn = "৯. জলসা (দুই সেজদার মধ্যবর্তী বৈঠক)",
            titleEn = "9. Jalsah (Sitting between Two Prostrations)",
            titleAr = "الجلسة بين السجدتين والدعاء",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "رَبِّ اغْفِرْ لِي وَارْحَمْنِي وَاجْبُرْنِي وَاهْدِنِي وَارْزُقْنِي",
            transliterationBn = "রাব্বিগফির লী, ওয়ারহামনী, ওয়াজবুরনী, ওয়াহদিনী, ওয়ারযুক্বনী।",
            meaningBn = "হে আমার প্রতিপালক! আমাকে ক্ষমা করুন, আমার ওপর দয়া করুন, আমার ক্ষয়ক্ষতি পূরণ করুন, আমাকে সঠিক পথে পরিচালিত করুন এবং আমাকে রিজিক দান করুন।",
            meaningEn = "O my Lord, forgive me, have mercy on me, mend my shortcomings, guide me, and grant me sustenance.",
            explanationBn = "প্রথম সেজদা থেকে 'আল্লাহু আকবার' বলে সোজা হয়ে বসা। বাম পা বিছিয়ে তার ওপর বসা এবং ডান পা খাড়া রাখা (আঙুল কিবলামুখী)। দুই হাত দুই উরুর ওপর রাখা। দুই সেজদার মাঝে সোজা ও স্থির হয়ে বসা ওয়াজিব এবং এই মাসনুন দু'আ পাঠ করা অত্যন্ত বরকতপূর্ণ সুন্নাত।",
            explanationEn = "Sitting upright between the two prostrations on the spread left foot, keeping the right foot vertical, and supplicating.",
            isObligatory = true,
            hanafiNote = "দুই সেজদার মাঝে অন্তত এক তাসবীহ পরিমাণ সময় স্থির হয়ে বসা ওয়াজিব।",
            reference = "সুনানে আবু দাউদ: ৮৫০; সুনানে তিরমিজি: ২৮৪ (সহিহ)",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/023118.mp3",
            imageResId = R.drawable.img_salah_jalsah,
            postureName = "জলসা / দুই সেজদার মাঝে স্থির বৈঠক"
        ),

        // Step 10: Second Sujud
        SalahGuideItem(
            id = "step_sujud2",
            titleBn = "১০. দ্বিতীয় সেজদাহ",
            titleEn = "10. Second Sujud (Prostration)",
            titleAr = "السجدة الثانية",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "سُبْحَانَ رَبِّيَ الأَعْلَى",
            transliterationBn = "সুবহানা রাব্বিয়াল আ'লা (কমপক্ষে ৩ বার)",
            meaningBn = "আমার সর্বোচ্চ মহান প্রতিপালকের পবিত্রতা ঘোষণা করছি।",
            meaningEn = "Glory be to my Lord, the Most High (recited at least 3 times).",
            explanationBn = "জলসা থেকে পুনরায় 'আল্লাহু আকবার' বলে প্রথম সেজদার মতোই দ্বিতীয় সেজদা আদায় করা। স্থির হয়ে অন্তত ৩ বার 'সুবহানা রাব্বিয়াল আ'লা' পাঠ করা। এর মাধ্যমে নামাজের ১ম রাকাত পূর্ণ হয়।",
            explanationEn = "Performing the second prostration just like the first to complete the rak'ah.",
            isObligatory = true,
            reference = "সহিহ বুখারী: ৮২৮; সহিহ মুসলিম: ৪৯১",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/096019.mp3",
            imageResId = R.drawable.img_salah_sujud,
            postureName = "দ্বিতীয় সেজদাহ / রাকাত পূর্ণকরণ"
        ),

        // Step 11: Tashahhud (Attahiyyatu)
        SalahGuideItem(
            id = "step_tashahhud",
            titleBn = "১১. তাশাহহুদ (আত্তাহিয়্যাতু) ও ইশারা",
            titleEn = "11. Tashahhud (Attahiyyatu) & Pointing Finger",
            titleAr = "التشهد والإشارة بالسبابة",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "التَّحِيَّاتُ لِلَّهِ وَالصَّلَوَاتُ وَالطَّيِّبَاتُ، السَّلاَمُ عَلَيْكَ أَيُّهَا النَّبِيُّ وَرَحْمَةُ اللَّهِ وَبَرَكَاتُهُ، السَّلاَمُ عَلَيْنَا وَعَلَى عِبَادِ اللَّهِ الصَّالِحِينَ، أَشْهَدُ أَنْ لاَ إِلَهَ إِلاَّ اللَّهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا عَبْدُهُ وَرَسُولُهُ",
            transliterationBn = "আত্তাহিয়্যাতু লিল্লাহি ওয়াস-সালাওয়াতু ওয়াত-ত্বাইয়্যিবাতু, আস-সালামু 'আলাইকা আইয়্যুহান-নাবিয়্যু ওয়া রাহমাতুল্লাহি ওয়া বারাকাতুহু, আস-সালামু 'আলাইনা ওয়া 'আলা 'ইবাদিল্লাহিস-সালিহীন। আশহাদু আল-লা-ইলাহা ইল্লাল্লাহু ওয়া আশহাদু আন্না মুহাম্মাদান 'আবদুহু ওয়া রাসুলুহু।",
            meaningBn = "সমস্ত মৌখিক, শারীরিক ও আর্থিক ইবাদত আল্লাহর জন্য। হে নবী! আপনার প্রতি শান্তি, আল্লাহর রহমত ও তাঁর বরকত বর্ষিত হোক। আমাদের ওপর এবং আল্লাহর নেক বান্দাদের ওপর শান্তি বর্ষিত হোক। আমি সাক্ষ্য দিচ্ছি যে, আল্লাহ ছাড়া কোনো সত্য উপাস্য নেই এবং আরও সাক্ষ্য দিচ্ছি যে, মুহাম্মদ (সা.) তাঁর বান্দা ও রাসুল।",
            meaningEn = "All greetings, prayers and pure words belong to Allah. Peace be upon you, O Prophet, and the mercy of Allah and His blessings. Peace be upon us and upon the righteous servants of Allah. I testify that there is no deity except Allah, and I testify that Muhammad is His servant and His messenger.",
            explanationBn = "দুই রাকাত পর (প্রথম বৈঠক) এবং শেষ রাকাতে (শেষ বৈঠক) বসে তাশাহহুদ পাঠ করা ওয়াজিব। 'আশহাদু আল-লা ইলাহা' বলার সময় ডান হাতের বৃদ্ধাঙ্গুলি ও মধ্যমা দিয়ে বৃত্ত তৈরি করে শাহাদাত (তর্জনী) আঙুল উঠিয়ে তাওহিদের সাক্ষ্য দেওয়া এবং 'ইল্লাল্লাহ' বলার সময় নামিয়ে রাখা সুন্নাত।",
            explanationEn = "Recited while sitting in the middle and final sitting of the prayer, gently pointing the right index finger at the testimony of oneness.",
            isObligatory = true,
            hanafiNote = "হানাফি মাযহাবে বাম পা বিছিয়ে তার ওপর বসা এবং ডান পা খাড়া রাখা সুন্নাত। মহিলারা উভয় পা ডান দিকে বের করে নিতম্বের ওপর বসবে (তাওয়াররুক)।",
            reference = "সহিহ বুখারী: ৮৩১; সহিহ মুসলিম: ৪০২",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/048029.mp3",
            imageResId = R.drawable.img_salah_jalsah,
            postureName = "বৈঠক (কা'দা) ও তাশাহহুদ পাঠ"
        ),

        // Step 12: Durood Ibrahim
        SalahGuideItem(
            id = "step_durood",
            titleBn = "১২. দরূদে ইব্রাহিম (সালাত আলা আন-নবী)",
            titleEn = "12. Durood Ibrahim (Salawat upon the Prophet)",
            titleAr = "الصلاة الإبراهيمية",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا صَلَّيْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ إِنَّكَ حَمِيدٌ مَجِيدٌ، اللَّهُمَّ بَارِكْ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ كَمَا بَارَكْتَ عَلَى إِبْرَاهِيمَ وَعَلَى آلِ إِبْرَاهِيمَ إِنَّكَ حَمِيدٌ مَجِيدٌ",
            transliterationBn = "আল্লাহুম্মা সাল্লি 'আলা মুহাম্মাদিঁও ওয়া 'আলা আলি মুহাম্মাদ, কামা সাল্লাইতা 'আলা ইবরাহীমা ওয়া 'আলা আলি ইবরাহীম, ইন্নাকা হামীদুম মাজীদ। আল্লাহুম্মা বারিক 'আলা মুহাম্মাদিঁও ওয়া 'আলা আলি মুহাম্মাদ, কামা বারাকতা 'আলা ইবরাহীমা ওয়া 'আলা আলি ইবরাহীম, ইন্নাকা হামীদুম মাজীদ।",
            meaningBn = "হে আল্লাহ! মুহাম্মদ (সা.) এবং তাঁর বংশধরগণের ওপর রহমত বর্ষণ করুন, যেমন আপনি ইব্রাহিম (আ.) এবং তাঁর বংশধরগণের ওপর রহমত বর্ষণ করেছিলেন। নিশ্চয়ই আপনি প্রশংসিত ও মহিমান্বিত। হে আল্লাহ! মুহাম্মদ (সা.) এবং তাঁর বংশধরগণের ওপর বরকত নাযিল করুন, যেমন আপনি ইব্রাহিম (আ.) এবং তাঁর বংশধরগণের ওপর বরকত নাযিল করেছিলেন। নিশ্চয়ই আপনি প্রশংসিত ও মহিমান্বিত।",
            meaningEn = "O Allah, send prayers upon Muhammad and upon the family of Muhammad, as You sent prayers upon Ibrahim and upon the family of Ibrahim; indeed, You are Praiseworthy and Glorious. O Allah, send blessings upon Muhammad and upon the family of Muhammad, as You sent blessings upon Ibrahim and upon the family of Ibrahim; indeed, You are Praiseworthy and Glorious.",
            explanationBn = "শেষ বৈঠকে তাশাহহুদ পাঠ করার পর দরূদে ইব্রাহিম পাঠ করা সুন্নাতে মুয়াক্কাদা (বা অন্যান্য মাযহাবে ওয়াজিব)।",
            explanationEn = "Invoking peace and blessings upon Prophet Muhammad and Prophet Ibrahim in the final sitting.",
            isObligatory = false,
            reference = "সহিহ বুখারী: ৩৩৭০; সহিহ মুসলিম: ৪০৬",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/033056.mp3",
            imageResId = R.drawable.img_salah_jalsah,
            postureName = "শেষ বৈঠকে দরূদে ইব্রাহিম পাঠ"
        ),

        // Step 13: Dua Masurah
        SalahGuideItem(
            id = "step_duamasurah",
            titleBn = "১৩. দু'আয়ে মাছুরা (সালামের পূর্বের দু'আ)",
            titleEn = "13. Dua Masurah (Supplication before Salam)",
            titleAr = "الدعاء المأثور قبل السلام",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "اللَّهُمَّ إِنِّي ظَلَمْتُ نَفْسِي ظُلْمًا كَثِيرًا وَلاَ يَغْفِرُ الذُّنُوبَ إِلاَّ أَنْتَ فَاغْفِرْ লِي مَغْفِرَةً مِنْ عِنْدِكَ وَارْحَمْنِي إِنَّكَ أَنْتَ الْغَفُورُ الرَّحِيمُ",
            transliterationBn = "আল্লাহুম্মা ইন্নী যালামতু নাফসী যুলমান কাসীরাঁও ওয়ালা ইয়াগফিরুয যুনূবা ইল্লা আনতা, ফাগফির লী মাগফিরাতাম মিন 'ইনদিকা ওয়ারহামনী, ইন্নাকা আনতাল গাফুরুর রাহীম।",
            meaningBn = "হে আল্লাহ! আমি নিজের ওপর অনেক জুলুম (অন্যায়) করেছি। আর আপনি ছাড়া আর কেউ গুনাহ ক্ষমা করতে পারে না। অতএব আপনি নিজ অনুগ্রহে আমাকে ক্ষমা করে দিন এবং আমার প্রতি দয়া করুন। নিশ্চয়ই আপনি পরম ক্ষমাশীল ও অসীম দয়ালু।",
            meaningEn = "O Allah, I have wronged myself greatly, and no one forgives sins except You. So grant me forgiveness from You and have mercy on me; indeed, You are the Forgiving, the Merciful.",
            explanationBn = "দরূদ শরীফ পাঠের পর সালাম ফেরানোর পূর্বে এই সুন্নাত দু'আটি পাঠ করতে হয়। এছাড়া অন্যান্য কোরআনিক ও হাদিসসম্মত দু'আও পাঠ করা জায়েজ।",
            explanationEn = "Recited in the final sitting after sending blessings upon the Prophet and before concluding with Salam.",
            isObligatory = false,
            reference = "সহিহ বুখারী: ৮৩৪; সহিহ মুসলিম: ২৭০৫",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/007023.mp3",
            imageResId = R.drawable.img_salah_jalsah,
            postureName = "শেষ বৈঠকে ক্ষমা প্রার্থনার দু'আ"
        ),

        // Step 14: Taslim (Salam)
        SalahGuideItem(
            id = "step_salam",
            titleBn = "১৪. সালাম ফিরিয়ে নামাজ সমাপ্তি",
            titleEn = "14. Taslim (Salam - Concluding Prayer)",
            titleAr = "التسليم لإنهاء الصلاة",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "السَّلاَمُ عَلَيْكُمْ وَرَحْمَةُ اللَّهِ",
            transliterationBn = "আস-সালামু 'আলাইকুম ওয়া রাহমাতুল্লাহ (প্রথমে ডানে, তারপর বামে)",
            meaningBn = "আপনাদের ওপর আল্লাহর শান্তি ও তাঁর রহমত বর্ষিত হোক।",
            meaningEn = "May the peace and mercy of Allah be upon you (turned first to the right, then to the left).",
            explanationBn = "প্রথমে মাথা ডান কাঁধের দিকে ফিরিয়ে 'আস-সালামু আলাইকুম ওয়া রাহমাতুল্লাহ' বলা, এরপর বাম কাঁধের দিকে মাথা ফিরিয়ে পুনরায় 'আস-সালামু আলাইকুম ওয়া রাহমাতুল্লাহ' বলা। এর মাধ্যমে নামাজ সফলভাবে সম্পন্ন হয়। সালাম ফেরানোর সময় ডানে ও বামের ফেরেশতা এবং উপস্থিত মুসল্লিদের নিয়তে সালাম দেওয়া মুস্তাহাব।",
            explanationEn = "Turning the head to the right shoulder while saying the Salam, then turning to the left shoulder to conclude the prayer.",
            isObligatory = true,
            hanafiNote = "হানাফি মাযহাবে উভয় দিকে সালাম ফেরানো ওয়াজিব।",
            reference = "সহিহ মুসলিম: ৫৮২; সুনানে আবু দাউদ: ৯৯৬",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/056091.mp3",
            imageResId = R.drawable.img_salah_salam,
            postureName = "সালাম ফিরানো / ডানে ও বামে মুখ ফেরানো"
        ),

        // Step 15: Dua Qunut (for Witr)
        SalahGuideItem(
            id = "step_qunut",
            titleBn = "১৫. বিতরের নামাজের দু'আ কুনুত",
            titleEn = "15. Dua Qunut for Witr Prayer",
            titleAr = "دعاء القنوت في صلاة الوتر",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "اللَّهُمَّ إِنَّا نَسْتَعِينُكَ وَنَسْتَغْفِرُكَ وَنُؤْمِنُ بِكَ وَنَتَوَكَّلُ عَلَيْكَ وَنُثْنِي عَلَيْكَ الْخَيْرَ وَنَشْكُرُكَ وَلاَ نَكْفُرُكَ وَنَخْلَعُ وَنَتْرُكُ مَنْ يَفْجُرُكَ، اللَّهُمَّ إِيَّاكَ نَعْبُدُ وَلَكَ نُصَلِّي وَنَسْجُدُ وَإِلَيْكَ نَسْعَى وَنَحْفِدُ وَنَرْجُو رَحْمَتَكَ وَنَخْشَى عَذَابَكَ إِنَّ عَذَابَكَ بِالْكُفَّارِ مُلْحِقٌ",
            transliterationBn = "আল্লাহুম্মা ইন্না নাসতা'ঈনুকা ওয়া নাসতাগফিরুকা ওয়া নু'মিনু বিকা ওয়া নাতাওয়াক্কালু 'আলাইকা ওয়া নুছনী 'আলাইকাল খাইর, ওয়া নাশকুরুকা ওয়ালা নাকফুরুকা, ওয়া নাখলা'উ ওয়া নাতরুকু মাইঁ ইয়াফজুরুক। আল্লাহুম্মা ইয়্যাকা না'বুদু ওয়ালাকা নুসল্লী ওয়া নাসজুদু, ওয়া ইলাইকা নাস'আ ওয়া নাহফিদু, ওয়া নারজু রাহমাতাকা ওয়া নাখশা 'আযাবাকা, ইন্না 'আযাবাকা বিল কুফফারি মুলহিক্ব।",
            meaningBn = "হে আল্লাহ! আমরা আপনারই সাহায্য চাই, আপনারই নিকট ক্ষমা প্রার্থনা করি, আপনার প্রতি বিশ্বাস স্থাপন করি, আপনার ওপর ভরসা রাখি। আপনার উত্তম প্রশংসা করি, আপনার কৃতজ্ঞতা প্রকাশ করি এবং অকৃতজ্ঞ হই না। যারা আপনার নাফরমানি করে, আমরা তাদের সাথে সম্পর্ক ছিন্ন করি। হে আল্লাহ! আমরা কেবল আপনারই ইবাদত করি, আপনার জন্যই নামাজ পড়ি ও সেজদা করি, আপনার সন্তুষ্টির দিকেই ধাবিত হই। আমরা আপনার রহমতের আশা করি এবং আপনার শাস্তিকে ভয় করি; নিশ্চয়ই আপনার কঠোর শাস্তি অবিশ্বাসীদের ওপর কার্যকর হবে।",
            meaningEn = "O Allah, we seek Your help and Your forgiveness, and we believe in You and rely upon You, and we praise You in the best manner, and we thank You and are not ungrateful...",
            explanationBn = "বিতরের নামাজের ৩য় রাকাতে সূরা ফাতিহার পর অন্য সূরা মিলিয়ে হাত কান পর্যন্ত উঠিয়ে পুনরায় 'আল্লাহু আকবার' বলে হাত বেঁধে এ দু'আ পাঠ করা হানাফি মাযহাবে ওয়াজিব।",
            explanationEn = "Recited in the third rak'ah of Witr prayer before bowing.",
            isObligatory = true,
            hanafiNote = "হানাফি মাযহাবে রুকুর পূর্বে তাকবির বলে হাত বেঁধে দু'আ কুনুত পাঠ করা হয়।",
            otherMadhhabsNote = "শাফেঈ মাযহাবে রুকু থেকে ওঠার পর হাত তুলে কুনুত পাঠ করা হয় এবং রমজানের শেষার্ধে বিতরে ও প্রতিদিন ফজরে কুনুত পাঠ করা হয়।",
            reference = "মুসান্নাফ ইবনে আবি শায়বাহ: ৬৯৬৫; বায়হাকী: ৩/৪০",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/003008.mp3",
            imageResId = R.drawable.img_salah_qiyam,
            postureName = "বিতরে কুনুত পাঠের কিয়াম"
        ),

        // Step 16: Post-Prayer Dhikr & Ayat al-Kursi
        SalahGuideItem(
            id = "step_post_prayer",
            titleBn = "১৬. নামাজের পরবর্তী মাসনুন জিকির ও দু'আ",
            titleEn = "16. Post-Prayer Masnoon Dhikr & Ayat al-Kursi",
            titleAr = "الأذكار بعد الصلاة المكتوبة",
            category = SalahCategory.PRAYER_STEPS,
            arabicText = "أَسْتَغْفِرُ اللَّهَ (٣ مَرَّاتٍ) • اللَّهُمَّ أَنْتَ السَّلاَمُ وَمِنْكَ السَّلاَمُ، تَبَارَكْتَ يَا ذَا الْجَلاَلِ وَالإِكْرَامِ",
            transliterationBn = "আস্তাগফিরুল্লাহ (৩ বার)। আল্লাহুম্মা আনতাস-সালামু ওয়া মিনকাস-সালাম, তাবারাকতা ইয়া যাল জালালি ওয়াল ইকরাম।",
            meaningBn = "আমি আল্লাহর নিকট ক্ষমা প্রার্থনা করছি (৩ বার)। হে আল্লাহ! আপনিই শান্তিময় এবং আপনার নিকট থেকেই শান্তি আসে। আপনি বরকতময়, হে মর্যাদা ও সম্মানের অধিকারী!",
            meaningEn = "I ask Allah for forgiveness (3 times). O Allah, You are Peace and from You comes peace. Blessed are You, O Owner of majesty and honor.",
            explanationBn = "ফরজ নামাজের সালাম ফেরানোর পর ৩ বার আস্তাগফিরুল্লাহ ও উক্ত দু'আ পাঠ করা সুন্নাত। এরপর সুবহানাল্লাহ (৩৩ বার), আলহামদুলিল্লাহ (৩৩ বার), আল্লাহু আকবার (৩৪ বার) এবং আয়াতুল কুরসি পাঠ করা অত্যন্ত ফজিলতপূর্ণ। রাসুলুল্লাহ (সা.) বলেছেন: 'যে ব্যক্তি প্রত্যেক ফরজ নামাজের পর আয়াতুল কুরসি পাঠ করবে, তার জান্নাতে প্রবেশের পথে মৃত্যু ছাড়া আর কোনো বাধা থাকে না।' (সুনানে নাসায়ী)",
            explanationEn = "Masnoon dhikr after every prescribed prayer including Istighfar, Tasbih Fatimi, and Ayat al-Kursi.",
            isObligatory = false,
            reference = "সহিহ মুসলিম: ৫৯১, ৫৯৭; সুনানে নাসায়ী: ৯৯২৮ (সহিহ)",
            audioUrl = "https://everyayah.com/data/Alafasy_128kbps/002255.mp3",
            imageResId = R.drawable.img_salah_jalsah,
            postureName = "নামাজ শেষে জিকির ও মোনাজাতের বৈঠক"
        )
    )
}
