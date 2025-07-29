package org.isoron.uhabits

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer

object MediaPlayerManager {
    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private var originalVolume: Int = -1

    fun initialize(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context.applicationContext, R.raw.reward)
            audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        }
    }

    fun playDingSound() {
        // 暂时将媒体音量设置为最大
        audioManager?.let { am ->
            originalVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC)
            val maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val optimalVolume = (maxVolume * 0.5).toInt()
            am.setStreamVolume(AudioManager.STREAM_MUSIC, optimalVolume, 0)
        }

        mediaPlayer?.apply {
            // 播放完成后恢复原始音量
            setOnCompletionListener {
                audioManager?.let { am ->
                    if (originalVolume != -1) {
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, originalVolume, 0)
                    }
                }
                // 移除监听器以避免意外行为
                setOnCompletionListener(null)
            }
            seekTo(0)
            start()
        }
    }

    fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}