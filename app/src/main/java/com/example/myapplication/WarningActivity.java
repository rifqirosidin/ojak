package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WarningActivity extends AppCompatActivity {

    private Context context;
    private Activity activity;
    private TextView tvAlarmPlay, tvOff;
    boolean play = true;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);
        getSupportActionBar().hide();

        tvAlarmPlay = findViewById(R.id.tv_alarm_play);
        context = getApplicationContext();
        activity = WarningActivity.this;


        tvAlarmPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play){
                    mediaPlayer = MediaPlayer.create(context, R.raw.alarm_tone);
                    mediaPlayer.start();
                    play = false;
                    tvAlarmPlay.setText("OFF");
                } else {
                    mediaPlayer.stop();
                    play = true;
                    tvAlarmPlay.setText("ON");
                }
            }
        });

    }

    public void back(View view) {
        Intent intent = new Intent(WarningActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}
