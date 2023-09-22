package org.isoron.uhabits

import android.content.Context
import android.media.MediaPlayer

object MediaPlayerManager {
    private var mediaPlayer: MediaPlayer? = null

    fun initialize(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context.applicationContext, R.raw.ding)
        }
    }

    fun playDingSound() {
        mediaPlayer?.apply {
            seekTo(0) // Reset the sound file to the beginning
            start()
        }
    }

    fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}