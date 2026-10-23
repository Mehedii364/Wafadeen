package com.example.repository

import com.example.data.model.Dua
import com.example.data.model.DuaCategory
import com.example.data.model.DuaType

object DuaRepository {

    val duas: List<Dua> = listOf(
        // Morning & Evening Adhkar
        Dua(
            id = "dua_sayyidul_istighfar",
            titleBn = "সাইয়্যিদুল ইস্তিগফার (শ্রেষ্ঠ ক্ষমা প্রার্থনা)",
            titleEn = "Sayyid al-Istighfar (Chief of Prayers for Forgiveness)",
            category = DuaCategory.ISTIGHFAR,
            type = DuaType.PROPHETIC_SUNNAH,
            arabicText = "اللَّهُمَّ أَنْتَ رَبِّي لاَ إِلَهَ إِلاَّ أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ لَكَ بِذَنْبِي فَاغْفِرْ لِي، فَإِنَّهُ لاَ يَغْفِرُ الذُّنُوبَ إِلاَّ أَنْتَ",
            transliterationBn = "আল্লাহুম্মা আনতা রাব্বী লা ইলাহা ইল্লা আনতা, খালাক্বতানী ওয়া আনা 'আবদুকা, ওয়া আনা 'আলা 'আহদিকা ওয়া ওয়া'দিকা মাসতাত্বা'তু, আ'উযু বিকা মিন শাররি মা সানা'তু, আবূউ লাকা বিনি'মাতিকা 'আলাইয়্যা, ওয়া আবূউ লাকা বিযাম্বী ফাগফির লী, ফাইন্নাহু লা ইয়াগফিরুয যুনূবা ইল্লা আনতা।",
            meaningBn = "হে আল্লাহ! আপনি আমার প্রতিপালক, আপনি ব্যতীত সত্য কোনো উপাস্য নেই। আপনি আমাকে সৃষ্টি করেছেন এবং আমি আপনার বান্দা। আমি আমার সাধ্যমতো আপনার অঙ্গীকার ও প্রতিশ্রুতির ওপর কায়েম আছি। আমি আমার কৃতকর্মের অনিষ্ট থেকে আপনার নিকট আশ্রয় চাই। আমার ওপর আপনার নেয়ামত স্বীকার করছি এবং আমার গুনাহও স্বীকার করছি। অতএব আমাকে ক্ষমা করুন, নিশ্চয়ই আপনি ব্যতীত গুনাহ ক্ষমা করার আর কেউ নেই।",
            meaningEn = "O Allah, You are my Lord, none has the right to be worshipped except You. You created me and I am Your servant, and I abide by Your covenant and promise as best I can. I seek refuge in You from the evil of what I have done. I acknowledge Your favor upon me, and I acknowledge my sin, so forgive me; none forgives sins except You.",
            contextBn = "রাসুলুল্লাহ (সা.) বলেছেন: যে ব্যক্তি সকাল বেলা দৃঢ় বিশ্বাসের সাথে এই দু'আ পাঠ করবে এবং সন্ধ্যার আগে মারা যাবে, সে জান্নাতে যাবে। একইভাবে সন্ধ্যায় পাঠ করে সকালের পূর্বে মারা গেলেও সে জান্নাতে প্রবেশ করবে।",
            reference = "সহিহ বুখারী: ৬৩০৬; তিরমিজি: ৩৩৯৩",
            repetitionCount = 1
        ),
        Dua(
            id = "dua_morning_protection",
            titleBn = "সকল অনিষ্ট থেকে নিরাপদ থাকার দু'আ (সকাল-সন্ধ্যা)",
            titleEn = "Supplication for Protection from Harm",
            category = DuaCategory.MORNING_EVENING,
            type = DuaType.PROPHETIC_SUNNAH,
            arabicText = "بِسْمِ اللَّهِ الَّذِي لاَ يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الأَرْضِ وَلاَ فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ",
            transliterationBn = "বিসমিল্লাহিল্লাযী লা ইয়াদুররু মা'আসমিহী শাইউন ফিল আরদ্বি ওয়ালা ফিস-সামায়ি ওয়াহুওয়াস-সামী'উল 'আলীম।",
            meaningBn = "আল্লাহর নামে শুরু করছি, যাঁর নামের বরকতে আসমান ও জমিনের কোনো কিছুই কোনো ক্ষতিসাধন করতে পারে না। আর তিনি সর্বশ্রোতা ও সর্বজ্ঞ।",
            meaningEn = "In the name of Allah, with whose name nothing can cause harm in the earth nor in the heavens, and He is the All-Hearing, the All-Knowing.",
            contextBn = "সকাল ও সন্ধ্যায় ৩ বার পাঠ করলে কোনো বিপদ-আপদ বা বিষাক্ত প্রাণীর ক্ষতি তাকে স্পর্শ করতে পারবে না।",
            reference = "সুনানে আবু দাউদ: ৫০৮৮; সুনানে তিরমিজি: ৩৩৮৮ (সহিহ)",
            repetitionCount = 3
        ),

        // After Salah Adhkar
        Dua(
            id = "dua_after_salah_istighfar",
            titleBn = "সালাম ফেরানোর পর ইস্তিগফার ও শান্তিকামনা",
            titleEn = "Adhkar Immediately After Finishing Salah",
            category = DuaCategory.AFTER_SALAH,
            type = DuaType.PROPHETIC_SUNNAH,
            arabicText = "أَسْتَغْفِرُ اللَّهَ، أَسْتَغْفِرُ اللَّهَ، أَسْتَغْفِرُ اللَّهَ، اللَّهُمَّ أَنْتَ السَّلاَمُ وَمِنْكَ السَّلاَمُ تَبَارَكْتَ يَا ذَا الْجَلاَلِ وَالإِكْرَامِ",
            transliterationBn = "আস্তাগফিরুল্লাহ, আস্তাগফিরুল্লাহ, আস্তাগফিরুল্লাহ। আল্লাহুম্মা আনতাস-সালামু ওয়া মিনকাস-সালাম, তাবারাকতা ইয়া যাল জালালি ওয়াল ইকরাম।",
            meaningBn = "আমি আল্লাহর নিকট ক্ষমা প্রার্থনা করছি (৩ বার)। হে আল্লাহ! আপনিই শান্তির উৎস এবং আপনার থেকেই শান্তি আসে। হে মহিমাময় ও মর্যাদাবান! আপনি বরকতময়।",
            meaningEn = "I seek Allah's forgiveness (3 times). O Allah, You are Peace and from You comes peace. Blessed are You, O Possessor of majesty and honor.",
            contextBn = "রাসুলুল্লাহ (সা.) ফরজ সালাতের সালাম ফেরানোর পর উচ্চৈঃস্বরে একবার আল্লাহু আকবার এবং তিনবার আস্তাগফিরুল্লাহ বলে এ দু'আ পাঠ করতেন।",
            reference = "সহিহ মুসলিম: ৫৯১; তিরমিজি: ৩০০",
            repetitionCount = 1
        ),
        Dua(
            id = "dua_ayatul_kursi",
            titleBn = "আয়াতুল কুরসী (কুরআনের শ্রেষ্ঠ আয়াত)",
            titleEn = "Ayat al-Kursi (The Greatest Verse)",
            category = DuaCategory.AFTER_SALAH,
            type = DuaType.QURANIC,
            arabicText = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ ۗ مَن ذَا الَّذِي يَشْفَعُ عِندَهُ إِلَّا بِإِذْنِهِ ۚ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ ۖ وَلَا يُحِيطُونَ بِشَيْءٍ مِّنْ عِلْمِهِ إِلَّا بِمَا شَاءَ ۚ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ ۖ وَلَا يَئُودُهُ حِفْظُهُمَا ۚ وَهُوَ الْعَلِيُّ الْعَظِيمُ",
            transliterationBn = "আল্লাহু লা ইলাহা ইল্লা হুওয়াল হাইয়্যুল ক্বাইয়্যুম, লা তা'খুযুহু সিনাতুঁও ওয়ালা নাওম, লাহু মা ফিস-সামাওয়াতি ওয়ামা ফিল আরদ্ব...",
            meaningBn = "আল্লাহ, তিনি ব্যতীত সত্য কোনো উপাস্য নেই। তিনি চিরঞ্জীব, সর্বসত্তার ধারক। তন্দ্রা ও নিদ্রা তাঁকে স্পর্শ করে না। আকাশমণ্ডলী ও পৃথিবীতে যা কিছু আছে সবই তাঁর...",
            meaningEn = "Allah - there is no deity except Him, the Ever-Living, the Sustainer of all existence. Neither drowsiness overtakes Him nor sleep...",
            contextBn = "রাসুলুল্লাহ (সা.) বলেছেন: যে ব্যক্তি প্রতি ফরজ সালাতের পর আয়াতুল কুরসী পাঠ করবে, তার জান্নাতে প্রবেশের পথে মৃত্যু ছাড়া আর কোনো বাধা থাকে না।",
            reference = "সূরা আল-বাক্বারাহ: ২৫৫; সুনানে আন-নাসায়ী (আস-সুনানুল কুবরা: ৯৮৪৮; সহিহ আল-জামে: ৬৪৬৪)",
            repetitionCount = 1
        ),

        // Quranic Rabbana Duas
        Dua(
            id = "dua_rabbana_dunya_hasana",
            titleBn = "দুনিয়া ও আখিরাতে কল্যাণের কুরআনি দু'আ",
            titleEn = "Supplication for Good in This Life and Hereafter",
            category = DuaCategory.QURANIC_RABBANA,
            type = DuaType.QURANIC,
            arabicText = "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ",
            transliterationBn = "রাব্বানা আতিনা ফিদ-দুনয়া হাসানাতাঁও ওয়াফিল আখিরাতি হাসানাতাঁও ওয়াক্বিনা 'আযাবান-নার।",
            meaningBn = "হে আমাদের রব! আমাদের দুনিয়াতেও কল্যাণ দান করুন এবং আখিরাতেও কল্যাণ দান করুন এবং আমাদের জাহান্নামের আযাব হতে রক্ষা করুন।",
            meaningEn = "Our Lord, give us in this world that which is good and in the Hereafter that which is good and protect us from the punishment of the Fire.",
            contextBn = "এটি রাসুলুল্লাহ (সা.)-এর সর্বাধিক পঠিত ও প্রিয় দু'আ ছিল। তাওয়াফের সময় রুকনে ইয়ামানি ও হাজরে আসওয়াদের মাঝেও এ দু'আ পাঠের সুন্নাত রয়েছে।",
            reference = "সূরা আল-বাক্বারাহ: ২০১; সহিহ বুখারী: ৪৫২২; সহিহ মুসলিম: ২৬৯০",
            repetitionCount = 1
        ),
        Dua(
            id = "dua_rabbana_hablana",
            titleBn = "উত্তম পরিবার ও চোখের শীতলতাদায়ী সন্তানের দু'আ",
            titleEn = "Supplication for Righteous Family and Offspring",
            category = DuaCategory.QURANIC_RABBANA,
            type = DuaType.QURANIC,
            arabicText = "رَبَّنَا هَبْ لَنَا مِنْ أَزْوَاجِنَا وَذُرِّيَّاتِنَا قُرَّةَ أَعْيُنٍ وَاجْعَلْنَا لِلْمُتَّقِينَ إِمَامًا",
            transliterationBn = "রাব্বানা হাব লানা মিন আযওয়াজিনা ওয়া যুররিইয়্যাতিনা ক্বুররাতা আ'ইউনিঁও ওয়াজ'আলনা লিল মুত্তাক্বীনা ইমামা।",
            meaningBn = "হে আমাদের রব! আমাদের জন্য এমন স্ত্রী ও সন্তানাদি দান করুন যারা আমাদের চোখ জুড়িয়ে দেবে এবং আমাদের মুত্তাকিদের ইমাম ও পথপ্রদর্শক বানিয়ে দিন।",
            meaningEn = "Our Lord, grant us from among our wives and offspring comfort to our eyes and make us a leader for the righteous.",
            contextBn = "পবিত্র কুরআনে আল্লাহ তা'আলা রহমান-এর প্রিয় বান্দাদের বৈশিষ্ট্য হিসেবে এই দু'আটির কথা উল্লেখ করেছেন।",
            reference = "সূরা আল-ফুরকান: ৭৪",
            repetitionCount = 1
        ),

        // Daily Life Duas
        Dua(
            id = "dua_leaving_home",
            titleBn = "ঘর থেকে বের হওয়ার সময়ের দু'আ",
            titleEn = "Supplication When Leaving Home",
            category = DuaCategory.DAILY_LIFE,
            type = DuaType.PROPHETIC_SUNNAH,
            arabicText = "بِسْمِ اللَّهِ تَوَكَّلْتُ عَلَى اللَّهِ، وَلاَ حَوْلَ وَلاَ قُوَّةَ إِلاَّ بِاللَّهِ",
            transliterationBn = "বিসমিল্লাহি তাওয়াক্কালতু 'আলাল্লাহ, ওয়ালা হাওলা ওয়ালা কুউওয়াতা ইল্লা বিল্লাহ।",
            meaningBn = "আল্লাহর নামে বের হচ্ছি, আল্লাহর ওপরই ভরসা করলাম। আল্লাহর সাহায্য ছাড়া পাপ থেকে বাঁচার এবং সৎকাজ করার কোনো শক্তি কারও নেই।",
            meaningEn = "In the name of Allah, I trust in Allah, there is no might and no power but in Allah.",
            contextBn = "ঘর থেকে বের হওয়ার সময় এ দু'আ পাঠ করলে ফেরেশতা বলেন: 'তুমি সুরক্ষা লাভ করেছ এবং শয়তান তোমার থেকে দূরে সরে গেল।'",
            reference = "সুনানে আবু দাউদ: ৫০৯৫; সুনানে তিরমিজি: ৩৪২৬ (সহিহ)",
            repetitionCount = 1
        ),
        Dua(
            id = "dua_before_sleep",
            titleBn = "ঘুমানোর পূর্বে পাঠ করার দু'আ",
            titleEn = "Supplication Before Sleeping",
            category = DuaCategory.DAILY_LIFE,
            type = DuaType.PROPHETIC_SUNNAH,
            arabicText = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
            transliterationBn = "বিসমিকাল্লাহুম্মা আমূতু ওয়া আহ্ইয়া।",
            meaningBn = "হে আল্লাহ! আপনারই নামে আমি মৃত্যুবরণ করছি (ঘুমাচ্ছি) এবং আপনারই নামে জীবিত (জাগ্রত) হবো।",
            meaningEn = "In Your name, O Allah, I die and I live.",
            contextBn = "ডান কাত হয়ে ডান হাত গালের নিচে রেখে শোয়ার পর এ দু'আ পাঠ করা সুন্নাত।",
            reference = "সহিহ বুখারী: ৬৩১২; সহিহ মুসলিম: ২৭১১",
            repetitionCount = 1
        )
    )

    fun getDuasByCategory(category: DuaCategory): List<Dua> {
        return duas.filter { it.category == category }
    }
}
