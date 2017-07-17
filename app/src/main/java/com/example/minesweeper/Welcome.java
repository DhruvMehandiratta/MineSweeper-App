package com.example.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends AppCompatActivity implements View.OnClickListener {

    Button b;
    TextView highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        b = (Button)findViewById(R.id.button1);
        b.setOnClickListener(this);

        SharedPreferences sp = getSharedPreferences("SPKey",MODE_PRIVATE);
        Boolean firstLogin = sp.getBoolean("first_login",true);
        if(firstLogin){
            Toast.makeText(this,"first time visit !!",Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("first_login",false);
            editor.commit();
        }

        //Error
//        highScore = (TextView) findViewById(R.id.high_score);
//        SharedPreferences sp1 = getSharedPreferences(getString(R.string.high_score),MODE_PRIVATE);
//        highScore.setText(sp1.getInt(getString(R.string.high_score)+"",MODE_PRIVATE));
    }

    @Override
    public void onClick(View v) {
        b = (Button)findViewById(R.id.button1);
        Intent i = new Intent();
        i.setClass(this,MainActivity.class);
        i.putExtra(IntentConstants.PLAYER_NAME,"Abc");
        startActivity(i);
    }
}
