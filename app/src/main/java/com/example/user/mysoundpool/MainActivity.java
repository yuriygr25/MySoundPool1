package com.example.user.mysoundpool;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.Builder;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SoundPool sSoundPool;
    private int mSoundId = 1;
    private int mStreamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        AudioAttributes attributes = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sSoundPool = new Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }


        sSoundPool.load(this, R.raw.cat, 1);

        Button playButton = (Button) findViewById(R.id.buttonPlay);
        Button pauseButton = (Button) findViewById(R.id.buttonPause);
        Button resumeButton = (Button) findViewById(R.id.buttonResume);
        playButton.setOnClickListener(onPlayButtonClickListener);
        pauseButton.setOnClickListener(onPauseButtonClickListener);
        resumeButton.setOnClickListener(onResumeButtonClickListener);

    }

    Button.OnClickListener onPlayButtonClickListener
            = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float leftVolume = curVolume / maxVolume;
            float rightVolume = curVolume / maxVolume;
            int priority = 1;
            int no_loop = 0;
            float normal_playback_rate = 1f;
            mStreamId = sSoundPool.play(mSoundId, leftVolume, rightVolume, priority, no_loop,
                    normal_playback_rate);

            Toast.makeText(getApplicationContext(),
                    "soundPool.play()",
                    Toast.LENGTH_LONG).show();
        }
    };

    Button.OnClickListener onPauseButtonClickListener
            = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            sSoundPool.pause(mStreamId);
            Toast.makeText(getApplicationContext(),
                    "soundPool.pause()",
                    Toast.LENGTH_LONG).show();
        }
    };
    Button.OnClickListener onResumeButtonClickListener
            = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            sSoundPool.resume(mStreamId);
            Toast.makeText(getApplicationContext(),
                    "soundPool.resume()",
                    Toast.LENGTH_LONG).show();
        }
    };

}
