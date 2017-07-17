package com.example.minesweeper;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        Intent i = getIntent();
        int score = i.getIntExtra("Score",0);
        TextView scoreText = (TextView) findViewById(R.id.score_text);
        scoreText.setText(score+"");
        MediaPlayer player = MediaPlayer.create(this, R.raw.sound);
        player.start();

    }
}
