package ru.mirea.sukhovav.myplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import ru.mirea.sukhovav.myplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ImageView imagePlayPause;
    private TextView textCorrentTime, textTotalDuration;
    private SeekBar playerSeekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    private ActivityMainBinding binding;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imagePlayPause = binding.imagePlayPause;
        textCorrentTime = binding.textCorrentTime;
        textTotalDuration = binding.textTotalDuration;
        playerSeekBar = binding.playerSeekBar;
        mediaPlayer = new MediaPlayer();

        playerSeekBar.setMax(100);



        imagePlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    Log.d(MainActivity.class.getSimpleName(),"onClick button1");
                    handler.removeCallbacks(runable);
                    mediaPlayer.pause();
                    imagePlayPause.setImageResource(R.drawable.ic_play);
                } else {
                    Log.d(MainActivity.class.getSimpleName(),"onClick button2");
                    mediaPlayer.start();
                    imagePlayPause.setImageResource(R.drawable.is_pause);
                    updateSeekBar();
                }
            }
        });

        prepareMediaPlayer();

        playerSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                SeekBar seekBar = (SeekBar) view;
                int PlayPosition = (mediaPlayer.getDuration() / 100 ) * seekBar.getProgress();
                mediaPlayer.seekTo(PlayPosition);
                textCorrentTime.setText(milliSecondToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

    }

    private void prepareMediaPlayer() {
        try {
            mediaPlayer.setDataSource("https://mp3uks.ru/mp3/files/mona-basta-ty-tak-mne-neobhodim-mp3.mp3");
            mediaPlayer.prepare();
            textTotalDuration.setText(milliSecondToTimer(mediaPlayer.getDuration()));
        } catch (Exception exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable runable = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            textCorrentTime.setText(milliSecondToTimer(currentDuration));
        }
    };

    private void updateSeekBar(){
        if(mediaPlayer.isPlaying()){
            playerSeekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration()) * 100));
            handler.postDelayed(runable, 1000);
        }
    }

    private String milliSecondToTimer(long millSecond){
        String TimerString = "";
        String SecondString;

        int hours = (int)(millSecond / (1000 * 60 * 60));
        int minuts = (int)(millSecond % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int)(millSecond % (1000 * 60 * 60)) / (1000 * 60) / 1000;

        if (hours > 0) {
            TimerString = hours + ":";
        }

        if (seconds < 10){
            SecondString = "0" + seconds;
        } else {
            SecondString = "" + seconds;
        }

        TimerString = TimerString + minuts + ":" + seconds;
        return TimerString;
    }



}