package com.example.hokmgame;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {

    private static MediaPlayer mediaPlayer;

    public static void playSound(Context context, int soundResId) {
        // Release any previously playing MediaPlayer instance
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Create and start a new MediaPlayer instance
        mediaPlayer = MediaPlayer.create(context, soundResId);
        mediaPlayer.start();

        // Release the MediaPlayer instance once playback is complete
        mediaPlayer.setOnCompletionListener(mp -> {
            mediaPlayer.release();
            mediaPlayer = null;
        });
    }
}
