package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MusicPlayerActivity extends AppCompatActivity {
    Button playbtn, btnnext, btnprev, btnfastforward, btnfastrewind;
    TextView txtsn,starttxt, stoptxt;
    SeekBar seekcrash;
    ImageView imageView;

    String sing;
    public static final String EXTRA_NAME = "song_name";
    static MediaPlayer mediaPlayer;
    int position; ArrayList<File> mySongs;
    Thread updateseekbar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //initialize the objects
        imageView = findViewById(R.id.imageview);
        playbtn = findViewById(R.id.playbtn);
        btnnext = findViewById(R.id.btnnext);
        btnprev = findViewById(R.id.btnprev);
        btnfastforward = findViewById(R.id.btnfastforward);
        btnfastrewind = findViewById(R.id.btnfastrewind);
        txtsn = findViewById(R.id.txtsn);
        starttxt = findViewById(R.id.starttxt);
        stoptxt = findViewById(R.id.stoptxt);
        seekcrash = findViewById(R.id.seekcrash);

        //when a user enters and a song is running release the player
        if (mediaPlayer != null)

        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        //use intent to get song name and song from the previous activity
        Intent x = getIntent(); Bundle bundle = x.getExtras();

        //retrieve information from intent
        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        //retrieve song names
        String songName = x.getStringExtra("songname");
        //get postion
        position = bundle.getInt("position", 0);
        //put song name in the textview
        txtsn.setSelected(true);

        Uri uri = Uri.parse(mySongs.get(position).toString());
        sing = mySongs.get(position).getName();
        //put name to the text view
        txtsn.setText(sing);

        //play music from the uri
        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        updateseekbar = new Thread()
        {
            @Override
            public void run() {
                int totalLength = mediaPlayer.getDuration();
                int currentposition = 0;

                //update every 500ms
                while (currentposition < totalLength){
                    try {
                        sleep(500);
                        currentposition = mediaPlayer.getCurrentPosition();
                        //move seekbar to the position
                        seekcrash.setProgress(currentposition);
                    }
                    catch (InterruptedException | IllegalStateException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        //maximum position of the seekbar
        seekcrash.setMax(mediaPlayer.getDuration());
        updateseekbar.start();
        seekcrash.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
        seekcrash.getThumb().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);

        //when user manually change the seek bar, song should update
        seekcrash.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int x, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //seek to position the user selected
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        String endTime = setTime(mediaPlayer.getDuration());
        //set string to stop
        stoptxt.setText(endTime);

        //current time
        final Handler handler = new Handler();
        //1000ms is 1 second
        final int delay = 1000;
        //updates current time on every second and shows to txt start.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = setTime(mediaPlayer.getCurrentPosition());
                starttxt.setText(currentTime);
                handler.postDelayed(this, delay);

            }

        }, delay);



        //when the user hits play, music should play
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                {
                    playbtn.setBackgroundResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                }
                else {
                    playbtn.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });
        //next listener
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                btnnext.performClick();
            }
        });

        //onclick listener for previous and next
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                //increment position of songs
                position = ((position +1)%mySongs.size());
                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                //add song name to the song name text view
                sing = mySongs.get(position).getName();
                txtsn.setText(sing);
                mediaPlayer.start();
                //change background icon of the play button
                playbtn.setBackgroundResource(R.drawable.ic_pause);
                //add animation of the imageview
                startAnimation(imageView);

            }
        });

        //creating previous button
        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                //decrement position, if less than 0, decrease total size of mysongs
                position = ((position-1)<0)?(mySongs.size()-1):(position-1);
                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), u);
                //set name to the text view
                sing = mySongs.get(position).getName();
                txtsn.setText(sing);
                //start mediaplayer
                mediaPlayer.start();
                //change background icon of the play button
                playbtn.setBackgroundResource(R.drawable.ic_pause);
                //start animation
                startAnimation(imageView);
                String endTime = setTime(mediaPlayer.getDuration());
                stoptxt.setText(endTime);

            }
        });

        //forward and rewind

        btnfastforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying())
                {
                    //fast forward to move towards 10 seconds
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+10000);
                }
            }
        });

        btnfastrewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-1000);
                }
            }
        });


    }
    //image animation
    public void startAnimation(View view)
    {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 180f);
        objectAnimator.setDuration(500);
        AnimatorSet animatorSet;
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator);
        animatorSet.start();

    }

    public String setTime(int duration)
    {
        String time = "";
        int min = duration/1000/60;
        //variable for seconds
        int sec = duration/1000%60;
        time+= min+":";

        if (sec<10)
        {
            time+="0";
        }
        time+=sec;

        return time;

    }
}