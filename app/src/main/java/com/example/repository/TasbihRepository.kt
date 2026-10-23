package com.example.repository

import com.example.data.local.TasbihDao
import com.example.data.local.TasbihEntity
import com.example.data.model.TasbihDhikr
import kotlinx.coroutines.flow.Flow

class TasbihRepository(private val tasbihDao: TasbihDao) {

    val availableDhikrs = listOf(
        TasbihDhikr(
            id = "subhanallah",
            arabic = "سُبْحَانَ اللَّهِ",
            transliterationBn = "সুবহানাল্লাহ",
            meaningBn = "আল্লাহ পবিত্র ও ত্রুটিমুক্ত",
            defaultTarget = 33,
            virtueBn = "মিজানের পাল্লায় অত্যন্ত ভারী এবং জান্নাতে একটি করে গাছ রোপিত হয়।"
        ),
        TasbihDhikr(
            id = "alhamdulillah",
            arabic = "الْحَمْدُ لِلَّهِ",
            transliterationBn = "আলহামদুলিল্লাহ",
            meaningBn = "সকল প্রশংসা আল্লাহর",
            defaultTarget = 33,
            virtueBn = "আমলের পাল্লা পরিপূর্ণ করে দেয়।"
        ),
        TasbihDhikr(
            id = "allahuakbar",
            arabic = "اللَّهُ أَكْبَرُ",
            transliterationBn = "আল্লাহু আকবার",
            meaningBn = "আল্লাহ সর্বশ্রেষ্ঠ ও মহান",
            defaultTarget = 34,
            virtueBn = "আসমান ও জমিনের মধ্যবর্তী শূন্যস্থান সওয়াব দিয়ে ভরে দেয়।"
        ),
        TasbihDhikr(
            id = "lailahaillallah",
            arabic = "لاَ إِلَهَ إِلاَّ اللَّهُ",
            transliterationBn = "লা ইলাহা ইল্লাল্লাহ",
            meaningBn = "আল্লাহ ছাড়া কোনো সত্য উপাস্য নেই",
            defaultTarget = 100,
            virtueBn = "সর্বোত্তম জিকির এবং জান্নাতের চাবিকাঠি।"
        ),
        TasbihDhikr(
            id = "astaghfirullah",
            arabic = "أَسْتَغْفِرُ اللَّهَ وَأَتُوبُ إِلَيْهِ",
            transliterationBn = "আস্তাগফিরুল্লাহ ওয়া আতূবু ইলাইহ",
            meaningBn = "আমি আল্লাহর নিকট ক্ষমা চাই এবং তাঁর কাছেই প্রত্যাবর্তন করি",
            defaultTarget = 100,
            virtueBn = "চিন্তামুক্তি, জীবিকায় বরকত এবং গুনাহ মোচনের মাধ্যম।"
        ),
        TasbihDhikr(
            id = "subhanallahi_wabihamdihi",
            arabic = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ ، سُبْحَانَ اللَّهِ الْعَظِيمِ",
            transliterationBn = "সুবহানাল্লাহি ওয়া বিহামদিহী, সুবহানাল্লাহিল 'আযীম",
            meaningBn = "প্রশংসাসহ আল্লাহর পবিত্রতা বর্ণনা করছি, মহান আল্লাহ অতি পবিত্র",
            defaultTarget = 100,
            virtueBn = "মুখে উচ্চারণে সহজ, দয়াময় আল্লাহর কাছে প্রিয় এবং মিজানে ভারী।"
        ),
        TasbihDhikr(
            id = "salawat",
            arabic = "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ وَعَلَى آلِ مُحَمَّدٍ",
            transliterationBn = "আল্লাহুম্মা সাল্লি 'আলা মুহাম্মাদ",
            meaningBn = "হে আল্লাহ! মুহাম্মদ (সা.)-এর ওপর রহমত বর্ষণ করুন",
            defaultTarget = 100,
            virtueBn = "একবার পাঠ করলে আল্লাহ তা'আলা ১০টি রহমত নাযিল করেন ও ১০টি গুনাহ ক্ষমা করেন।"
        ),
        TasbihDhikr(
            id = "lahawla",
            arabic = "لاَ حَوْلَ وَلاَ قُوَّةَ إِلاَّ بِاللَّهِ",
            transliterationBn = "লা হাওলা ওয়ালা কুউওয়াতা ইল্লা বিল্লাহ",
            meaningBn = "আল্লাহর সাহায্য ছাড়া সৎকাজের শক্তি এবং গুনাহ থেকে বাঁচার কোনো উপায় নেই",
            defaultTarget = 33,
            virtueBn = "জান্নাতের গুপ্তধনসমূহের একটি রত্ন।"
        )
    )

    val history: Flow<List<TasbihEntity>> = tasbihDao.getAllHistory()

    suspend fun saveSession(dhikrTitle: String, count: Int, target: Int, formattedDate: String) {
        if (count > 0) {
            tasbihDao.insertSession(
                TasbihEntity(
                    dhikrTitle = dhikrTitle,
                    count = count,
                    target = target,
                    formattedDate = formattedDate
                )
            )
        }
    }

    suspend fun clearHistory() {
        tasbihDao.clearHistory()
    }
}
