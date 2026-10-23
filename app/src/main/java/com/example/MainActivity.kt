package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.audio.AudioPlayerManager
import com.example.data.local.PreferencesManager
import com.example.data.local.WafaDeenDatabase
import com.example.ui.navigation.WafaDeenApp
import com.example.ui.theme.WafaDeenTheme

class MainActivity : ComponentActivity() {

    private lateinit var audioPlayerManager: AudioPlayerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = WafaDeenDatabase.getDatabase(this)
        val bookmarkDao = database.bookmarkDao()
        val tasbihDao = database.tasbihDao()
        val preferencesManager = PreferencesManager(this)
        audioPlayerManager = AudioPlayerManager.getInstance(this)

        setContent {
            val themeMode by preferencesManager.themeFlow.collectAsState(initial = "system")
            val isDarkTheme = when (themeMode) {
                "dark" -> true
                "light" -> false
                else -> isSystemInDarkTheme()
            }

            WafaDeenTheme(darkTheme = isDarkTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WafaDeenApp(
                        bookmarkDao = bookmarkDao,
                        tasbihDao = tasbihDao,
                        preferencesManager = preferencesManager,
                        audioPlayerManager = audioPlayerManager
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioPlayerManager.stop()
    }
}
