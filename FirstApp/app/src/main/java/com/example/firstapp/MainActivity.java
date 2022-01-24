package com.example.firstapp;


import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer md;
    AudioManager audioManager;
    SeekBar actualSeekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.demo);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();*/
        audioManager= (AudioManager) getSystemService(AUDIO_SERVICE);
        md=MediaPlayer.create(this,R.raw.excuses);
        SeekBar volumeBar =(SeekBar) findViewById(R.id.volumeSeekBar);
         actualSeekBar =(SeekBar) findViewById(R.id.actualSeekBar);
        ImageView front = findViewById(R.id.front);
        ImageView back = findViewById(R.id.back);
        Button play = (Button)findViewById(R.id.play);
        Button pause = (Button)findViewById(R.id.pause);

        //animation
        front.animate().alpha(0).setDuration(2000);
        back.animate().alpha(1).setDuration(2000);
        play.animate().alpha(1).setDuration(3000);
        pause.animate().alpha(1).setDuration(3000);

        int streamMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volumeBar.setMax(streamMaxVolume);
        volumeBar.setProgress(currentVolume);
        actualSeekBar.setMax(md.getDuration());

        // Volume control bar
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("info",String.valueOf(i));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        actualSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               // md.seekTo(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                md.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                md.seekTo(seekBar.getProgress());
                md.start();
            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                actualSeekBar.setProgress(md.getCurrentPosition());
            }
        },0,1000);


    }
    public void playOnClick(View view)
    {
        Log.i("info",String.valueOf(view.getId()) );
        if(md.isPlaying())
        {
            md.pause();
        }
        else
        {
            md.start();
        }

       /* {
            case R.id.button:
                 md = MediaPlayer.create(this, R.raw.excuses);
                md.start();
                break;
            case R.id.button2:
                 md = MediaPlayer.create(this, R.raw.sample);
                md.start();
                break;
            case R.id.button3:
                md = MediaPlayer.create(this, R.raw.excuses);
                md.start();
                break;
            case R.id.button4:
                md = MediaPlayer.create(this, R.raw.demo);
                md.start();
                break;
        }*/

    }




}