package com.example.repository

import com.example.data.model.Hadith
import com.example.data.model.VerificationStatus

object HadithRepository {

    val hadiths: List<Hadith> = listOf(
        Hadith(
            id = "bukhari_1",
            collection = "সহিহ বুখারী (Sahih al-Bukhari)",
            chapterNameBn = "ওহীর সূচনা অধ্যায় (Bad'ul Wahyi)",
            chapterNameEn = "Revelation",
            hadithNumber = 1,
            arabicMatn = "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ، وَإِنَّمَا لِكُلِّ امْرِئٍ مَا نَوَى، فَمَنْ كَانَتْ هِجْرَتُهُ إِلَى دُنْيَا يُصِيبُهَا أَوْ إِلَى امْرَأَةٍ يَنْكِحُهَا فَهِجْرَتُهُ إِلَى مَا هَاجَرَ إِلَيْهِ",
            translationBn = "নিশ্চয়ই সমস্ত কাজের ফলাফল নিয়তের ওপর নির্ভরশীল। প্রত্যেক ব্যক্তি তাই পাবে যা সে নিয়ত করেছে। অতএব যার হিজরত দুনিয়া অর্জনের জন্য অথবা কোনো নারীকে বিবাহ করার উদ্দেশ্যে হয়েছে, তার হিজরত সেই উদ্দেশ্যেই গণ্য হবে যার জন্য সে হিজরত করেছে।",
            translationEn = "The reward of deeds depends upon the intentions and every person will get the reward according to what he has intended. So whoever emigrated for worldly benefits or for a woman to marry, his emigration was for what he emigrated for.",
            narratorBn = "উমর ইবনুল খাত্তাব (রা.)",
            narratorEn = "'Umar bin Al-Khattab (RA)",
            grading = "সহিহ (Sahih - মুত্তাফাক্ব আলাইহ)",
            gradingSource = "ইমাম আল-বুখারী ও ইমাম মুসলিম",
            bookNumber = 1,
            referenceText = "সহিহ বুখারী: হাদিস ১; সহিহ মুসলিম: ১৯০৭",
            verificationStatus = VerificationStatus.VERIFIED
        ),
        Hadith(
            id = "bukhari_8",
            collection = "সহিহ বুখারী (Sahih al-Bukhari)",
            chapterNameBn = "ঈমান অধ্যায় (Kitab al-Iman)",
            chapterNameEn = "Belief",
            hadithNumber = 8,
            arabicMatn = "بُنِيَ الإِسْلاَمُ عَلَى خَمْسٍ: شَهَادَةِ أَنْ لاَ إِلَهَ إِلاَّ اللَّهُ وَأَنَّ مُحَمَّدًا رَسُولُ اللَّهِ، وَإِقَامِ الصَّلاَةِ، وَإِيتَاءِ الزَّكَاةِ، وَالحَجِّ، وَصَوْمِ رَمَضَانَ",
            translationBn = "ইসলামের ভিত্তি পাঁচটি স্তম্ভের ওপর স্থাপিত: এই সাক্ষ্য প্রদান করা যে, আল্লাহ ব্যতীত কোনো সত্য উপাস্য নেই এবং মুহাম্মদ (সা.) আল্লাহর রাসুল; নামাজ কায়েম করা; জাকাত আদায় করা; হজ পালন করা এবং রমজানের রোজা রাখা।",
            translationEn = "Islam is based on five [principles]: To testify that none has the right to be worshipped but Allah and Muhammad is Allah's Apostle, to offer the [compulsory congregational] prayers dutifully and perfectly, to pay Zakat, to perform Hajj, and to observe fast during the month of Ramadan.",
            narratorBn = "আব্দুল্লাহ ইবনে উমর (রা.)",
            narratorEn = "'Abdullah bin 'Umar (RA)",
            grading = "সহিহ (Sahih)",
            gradingSource = "ইমাম আল-বুখারী",
            bookNumber = 2,
            referenceText = "সহিহ বুখারী: হাদিস ৮; সহিহ মুসলিম: ১৬",
            verificationStatus = VerificationStatus.VERIFIED
        ),
        Hadith(
            id = "bukhari_13",
            collection = "সহিহ বুখারী (Sahih al-Bukhari)",
            chapterNameBn = "ঈমান অধ্যায় (Kitab al-Iman)",
            chapterNameEn = "Belief",
            hadithNumber = 13,
            arabicMatn = "لاَ يُؤْمِنُ أَحَدُكُمْ حَتَّى يُحِبَّ لِأَخِيهِ مَا يُحِبُّ لِنَفْسِهِ",
            translationBn = "তোমাদের মধ্যে কেউ ততক্ষণ পর্যন্ত প্রকৃত মুমিন হতে পারবে না, যতক্ষণ না সে তার ভাইয়ের জন্য তাই পছন্দ করে যা সে নিজের জন্য পছন্দ করে।",
            translationEn = "None of you will believe until you love for your brother what you love for yourself.",
            narratorBn = "আনাস ইবনে মালিক (রা.)",
            narratorEn = "Anas bin Malik (RA)",
            grading = "সহিহ (Sahih)",
            gradingSource = "ইমাম আল-বুখারী ও ইমাম মুসলিম",
            bookNumber = 2,
            referenceText = "সহিহ বুখারী: হাদিস ১৩; সহিহ মুসলিম: ৪৫",
            verificationStatus = VerificationStatus.VERIFIED
        ),
        Hadith(
            id = "bukhari_52",
            collection = "সহিহ বুখারী (Sahih al-Bukhari)",
            chapterNameBn = "ঈমান অধ্যায় (Kitab al-Iman)",
            chapterNameEn = "Belief",
            hadithNumber = 52,
            arabicMatn = "الْحَلاَلُ بَيِّنٌ وَالْحَرَامُ بَيِّنٌ، وَبَيْنَهُمَا مُشَبَّهَاتٌ لاَ يَعْلَمُهَا كَثِيرٌ مِنَ النَّاسِ، فَمَنِ اتَّقَى الشُّبُهَاتِ اسْتَبْرَأَ لِدِينِهِ وَعِرْضِهِ...",
            translationBn = "হালাল সুস্পষ্ট এবং হারামও সুস্পষ্ট। আর এই দুয়ের মাঝে রয়েছে বহু সন্দেহজনক বিষয় যা অনেক মানুষই জানে না। অতএব যে ব্যক্তি সন্দেহজনক বিষয় থেকে বেঁচে থাকল, সে তার দ্বীন ও সম্মান রক্ষা করল...",
            translationEn = "Both legal and illegal things are obvious, and in between them are doubtful issues which many people do not know. Thus he who avoids doubtful issues clears himself in regard to his religion and his honor...",
            narratorBn = "নোমান ইবনে বশীর (রা.)",
            narratorEn = "An-Nu'man bin Bashir (RA)",
            grading = "সহিহ (Sahih)",
            gradingSource = "ইমাম আল-বুখারী",
            bookNumber = 2,
            referenceText = "সহিহ বুখারী: হাদিস ৫২; সহিহ মুসলিম: ১৫৯৯",
            verificationStatus = VerificationStatus.VERIFIED
        ),
        Hadith(
            id = "bukhari_1891",
            collection = "সহিহ বুখারী (Sahih al-Bukhari)",
            chapterNameBn = "সাওম / রোজা অধ্যায় (Kitab as-Sawm)",
            chapterNameEn = "Fasting",
            hadithNumber = 1891,
            arabicMatn = "الصِّيَامُ جُنَّةٌ، فَلاَ يَرْفُثْ وَلاَ يَجْهَلْ، وَإِنِ امْرُؤٌ قَاتَلَهُ أَوْ شَاتَمَهُ فَلْيَقُلْ: إِنِّي صَائِمٌ. مَرَّتَيْنِ",
            translationBn = "রোজা হচ্ছে (জাহান্নাম ও পাপ থেকে রক্ষার) ঢালস্বরূপ। সুতরাং রোজাদার যেন অশ্লীল কথা না বলে এবং জাহেলী আচরণ না করে। যদি কেউ তার সাথে ঝগড়া করে বা তাকে গালি দেয়, সে যেন দু'বার বলে: আমি রোজাদার।",
            translationEn = "Fasting is a shield. So, the person observing fasting should avoid obscene language and should not behave foolishly, and if somebody fights with him or abuses him, he should tell him twice, 'I am fasting.'",
            narratorBn = "আবু হুরায়রা (রা.)",
            narratorEn = "Abu Hurairah (RA)",
            grading = "সহিহ (Sahih)",
            gradingSource = "ইমাম আল-বুখারী",
            bookNumber = 30,
            referenceText = "সহিহ বুখারী: হাদিস ১৮৯১; সহিহ মুসলিম: ১১৫১",
            verificationStatus = VerificationStatus.VERIFIED
        ),
        Hadith(
            id = "bukhari_6306",
            collection = "সহিহ বুখারী (Sahih al-Bukhari)",
            chapterNameBn = "দাওয়া অধ্যায় (Kitab ad-Da'awat)",
            chapterNameEn = "Invocations",
            hadithNumber = 6306,
            arabicMatn = "سَيِّدُ الاِسْتِغْفَارِ أَنْ تَقُولَ: اللَّهُمَّ أَنْتَ رَبِّي لاَ إِلَهَ إِلاَّ أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ لَكَ بِذَنْبِي فَاغْفِرْ لِي، فَإِنَّهُ لاَ يَغْفِرُ الذُّنُوبَ إِلاَّ أَنْتَ",
            translationBn = "সাইয়্যিদুল ইস্তিগফার (ক্ষমা প্রার্থনার শ্রেষ্ঠ দু'আ) হলো তুমি বলবে: হে আল্লাহ! আপনি আমার রব, আপনি ব্যতীত সত্য কোনো উপাস্য নেই। আপনি আমাকে সৃষ্টি করেছেন এবং আমি আপনার বান্দা। আমি আমার সাধ্যানুযায়ী আপনার অঙ্গীকার ও প্রতিশ্রুতির ওপর অবিচল আছি। আমি আমার কৃতকর্মের অনিষ্ট হতে আপনার নিকট আশ্রয় প্রার্থনা করছি। আমার প্রতি আপনার নেয়ামত আমি স্বীকার করছি এবং আমার অপরাধও স্বীকার করছি। অতএব আমাকে ক্ষমা করে দিন, নিশ্চয়ই আপনি ব্যতীত গুনাহ ক্ষমা করার আর কেউ নেই। যে ব্যক্তি দিনে দৃঢ় বিশ্বাসের সাথে এ দু'আ পাঠ করবে এবং সন্ধ্যার পূর্বেই মারা যাবে সে জান্নাতীদের অন্তর্ভুক্ত হবে।",
            translationEn = "The most superior way of asking for forgiveness from Allah is to say: 'O Allah, You are my Lord! None has the right to be worshipped but You. You created me and I am Your slave, and I am faithful to my covenant and my promise as much as I can... Whoever says this with conviction in the morning and dies before evening will be of the people of Paradise.",
            narratorBn = "শাদ্দাদ ইবনে আওস (রা.)",
            narratorEn = "Shaddad bin Aws (RA)",
            grading = "সহিহ (Sahih)",
            gradingSource = "ইমাম আল-বুখারী",
            bookNumber = 80,
            referenceText = "সহিহ বুখারী: হাদিস ৬৩০৬",
            verificationStatus = VerificationStatus.VERIFIED
        )
    )

    fun searchHadiths(query: String): List<Hadith> {
        if (query.isBlank()) return hadiths
        val q = query.trim().lowercase()
        return hadiths.filter {
            it.translationBn.lowercase().contains(q) ||
            it.translationEn.lowercase().contains(q) ||
            it.arabicMatn.contains(query) ||
            it.narratorBn.lowercase().contains(q) ||
            it.chapterNameBn.lowercase().contains(q) ||
            it.hadithNumber.toString() == query.trim()
        }
    }
}
