package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AudioPlaybackState(
    val isPlaying: Boolean = false,
    val isLoading: Boolean = false,
    val currentTitle: String = "",
    val currentUrl: String = "",
    val currentPositionMs: Int = 0,
    val totalDurationMs: Int = 0,
    val isRepeatOne: Boolean = false,
    val errorMessage: String? = null
)

class AudioPlayerManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private val handler = Handler(Looper.getMainLooper())

    private val _playbackState = MutableStateFlow(AudioPlaybackState())
    val playbackState: StateFlow<AudioPlaybackState> = _playbackState.asStateFlow()

    private val progressRunnable = object : Runnable {
        override fun run() {
            mediaPlayer?.let { player ->
                if (player.isPlaying) {
                    val pos = player.currentPosition
                    val duration = player.duration
                    _playbackState.value = _playbackState.value.copy(
                        currentPositionMs = pos,
                        totalDurationMs = if (duration > 0) duration else _playbackState.value.totalDurationMs
                    )
                    handler.postDelayed(this, 500)
                }
            }
        }
    }

    fun play(url: String, title: String) {
        if (url.isBlank()) {
            _playbackState.value = _playbackState.value.copy(
                errorMessage = "এই অডিওর উৎস এখনও সংযুক্ত করা হয়নি (Audio not yet available)"
            )
            return
        }

        try {
            stop()
            _playbackState.value = _playbackState.value.copy(
                isLoading = true,
                currentTitle = title,
                currentUrl = url,
                errorMessage = null
            )

            val player = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(url)
                setOnPreparedListener { mp ->
                    _playbackState.value = _playbackState.value.copy(
                        isPlaying = true,
                        isLoading = false,
                        totalDurationMs = mp.duration
                    )
                    mp.start()
                    handler.post(progressRunnable)
                }
                setOnCompletionListener {
                    if (_playbackState.value.isRepeatOne) {
                        it.seekTo(0)
                        it.start()
                    } else {
                        _playbackState.value = _playbackState.value.copy(
                            isPlaying = false,
                            currentPositionMs = 0
                        )
                        handler.removeCallbacks(progressRunnable)
                    }
                }
                setOnErrorListener { _, _, _ ->
                    _playbackState.value = _playbackState.value.copy(
                        isPlaying = false,
                        isLoading = false,
                        errorMessage = "অডিও প্লেব্যাকের সময় ত্রুটি হয়েছে বা ইন্টারনেট সংযোগ নেই"
                    )
                    handler.removeCallbacks(progressRunnable)
                    true
                }
                prepareAsync()
            }
            mediaPlayer = player
        } catch (e: Exception) {
            _playbackState.value = _playbackState.value.copy(
                isPlaying = false,
                isLoading = false,
                errorMessage = "অডিও প্লে করা সম্ভব হয়নি: ${e.localizedMessage}"
            )
        }
    }

    fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                _playbackState.value = _playbackState.value.copy(isPlaying = false)
                handler.removeCallbacks(progressRunnable)
            }
        }
    }

    fun resume() {
        mediaPlayer?.let {
            if (!it.isPlaying) {
                it.start()
                _playbackState.value = _playbackState.value.copy(isPlaying = true)
                handler.post(progressRunnable)
            }
        }
    }

    fun seekTo(positionMs: Int) {
        mediaPlayer?.seekTo(positionMs)
        _playbackState.value = _playbackState.value.copy(currentPositionMs = positionMs)
    }

    fun toggleRepeat() {
        val newRepeat = !_playbackState.value.isRepeatOne
        _playbackState.value = _playbackState.value.copy(isRepeatOne = newRepeat)
    }

    fun stop() {
        handler.removeCallbacks(progressRunnable)
        mediaPlayer?.let {
            try {
                if (it.isPlaying) {
                    it.stop()
                }
                it.reset()
                it.release()
            } catch (ignored: Exception) {}
        }
        mediaPlayer = null
        _playbackState.value = _playbackState.value.copy(
            isPlaying = false,
            isLoading = false,
            currentPositionMs = 0
        )
    }

    fun clearError() {
        _playbackState.value = _playbackState.value.copy(errorMessage = null)
    }

    companion object {
        @Volatile
        private var instance: AudioPlayerManager? = null

        fun getInstance(context: Context): AudioPlayerManager {
            return instance ?: synchronized(this) {
                instance ?: AudioPlayerManager(context.applicationContext).also { instance = it }
            }
        }
    }
}
