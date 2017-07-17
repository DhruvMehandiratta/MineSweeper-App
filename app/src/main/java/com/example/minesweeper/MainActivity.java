package com.example.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    MyButton buttons[][];
    LinearLayout topbar;
    LinearLayout rows[];
    LinearLayout mainLayout;
    int n = 8;
    int noOfMines = 13;

    TextView textView;
    TextView minesLeft;
    Button smily;
    int score = 0;
    int minesRemained = noOfMines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout)findViewById(R.id.activity_main);

        Intent i = getIntent();
        String a = i.getStringExtra(IntentConstants.PLAYER_NAME);
        Toast.makeText(this,a,Toast.LENGTH_SHORT).show();
        setUpBoard();
          }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.large) {
            if (n != 12) {
                n = 12;
                noOfMines = 25;
                setUpBoard();
            } else {
                resetBoard();
            }
        }
        if(id == R.id.small){
            if(n != 5){
                n = 5;
                noOfMines = 5;
                setUpBoard();
            }
            else{
                resetBoard();
            }
        }
        if(id == R.id.normal){
            if(n != 8){
                n = 8;
                noOfMines = 13;
                setUpBoard();
            }else{
                resetBoard();
            }
        }
        return true;
    }

    private void resetBoard() {
        for(int i = 0 ; i < n ; i++){
            for(int j = 0 ; j < n ; j++){
                buttons[i][j].setText("");
                buttons[i][j].setValue("");
                buttons[i][j].setEnabled(true);
                buttons[i][j].setBackgroundResource(android.R.drawable.btn_default);
            }
        }
        setMines();
    }

    private void setUpBoard(){
        mainLayout.removeAllViews();
        topbar = new LinearLayout(this);
        topbar.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,150,0);
        params.setMargins(0,0,0,0);
        topbar.setLayoutParams(params);
        mainLayout.addView(topbar);

        textView = new TextView(this);
        textView.setText("Score");
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
//        textView.setGravity(Gravity.CENTER_HORIZONTAL);
//        textView.setGravity(Gravity.CENTER_VERTICAL);
        params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
        textView.setLayoutParams(params);

        smily = new Button(this);
        smily.setText("New Game");
        smily.setId(R.id.NewGame);
        params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
        params.setMargins(0,0,0,0);
        smily.setLayoutParams(params);
        smily.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textView.setText("score");
                minesLeft.setText("minesLeft");
                resetBoard();
            }
    });
        minesLeft = new TextView(this);
        minesLeft.setText("MinesLeft");
        minesLeft.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
        minesLeft.setLayoutParams(params);

        topbar.addView(textView);
        topbar.addView(smily);
        topbar.addView(minesLeft);

        buttons = new MyButton[n][n];
        rows = new LinearLayout[n];
        for(int i = 0 ; i < n ; i++){
            rows[i] = new LinearLayout(this);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            rows[i].setLayoutParams(params);
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rows[i]);
        }

        for(int i = 0 ; i < n ; i++){
            for(int j = 0 ; j < n ; j++){
                buttons[i][j] = new MyButton(this,i,j);
                buttons[i][j].setText("");
                params = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.MATCH_PARENT,1);
                params.setMargins(0,0,0,0);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setOnLongClickListener(this);
                rows[i].addView(buttons[i][j]);
            }
        }
       setMines();
    }
        private void setMines(){
         Random r = new Random();
            for(int i = 0 ; i < noOfMines ; ){
               int j = r.nextInt(n);
                int k = r.nextInt(n);
                if(!buttons[j][k].isMine()){
                      buttons[j][k].setMine();
                    setUpSurroundings(j,k);
                    i++;
                }
            }
            score = 0;
            minesRemained = noOfMines;
        }

    private void setUpSurroundings(int j,int k){
        for(int x = j - 1 ; x <= j + 1 ; x ++){
            for(int y = k - 1 ; y <= k + 1 ; y ++) {
                if (x >= 0 && x < n && y >= 0 && y < n) {
               // if(buttons[x][y].getText().toString() == "*"){
                        if (buttons[x][y].getValue() == "*") {
                        continue;
                    }
                   //else if (buttons[x][y].getText().toString() == ""){
                    //buttons[x][y].setText(1 + "");
                     else if (buttons[x][y].getValue() == "") {
                        buttons[x][y].setValue(1 + "");
                    }
                    else {
                    //int value = Integer.parseInt(buttons[x][y].getText().toString());
                    //buttons[x][y].setText((value + 1) + "");
                        int value = Integer.parseInt(buttons[x][y].getValue());
                        buttons[x][y].setValue((value + 1) + "");
                    }
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        MyButton b = (MyButton) v;
        if(b.getValue() == "*"){
            b.setBackgroundColor(Color.RED);
            for(int i = 0 ; i < n ; i++){
                for(int j = 0 ; j < n ; j++){
                    buttons[i][j].setText(buttons[i][j].getValue());
                    buttons[i][j].setEnabled(false);
                }
            }
            Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
            textView.setText(score+"");

            SharedPreferences sp1 = getSharedPreferences(getString(R.string.high_score),MODE_PRIVATE);
            int highScore = sp1.getInt(getString(R.string.high_score),0);
            if(score > highScore){
                SharedPreferences.Editor editor1 = sp1.edit();
                editor1.putInt(getString(R.string.high_score),score);
                editor1.commit();
            }
            return;
        }
        if(b.getValue().equals("")){
           int x = b.getXCoordinate();
            int y = b.getYCoordinate();
            buttons[x][y].setEnabled(false);
            for(int i = x-1 ; i <= x+1 ; i++){
                for(int j = y-1 ; j <= y+1 ; j++){
                    if(i >= 0 && i < n && j >=0 && j < n && !(i== x && j == y)  && buttons[i][j].isEnabled() &&buttons[i][j].getValue() != "*"){
                       buttons[i][j].setEnabled(false);
                        onClick(buttons[i][j]);
                        //score++;
                        textView.setText(score+"");
                        b.setBackgroundColor(Color.YELLOW);
                    }
                }
            }
            if(gameWon()){
                Intent i = new Intent(this, WinActivity.class);
                i.putExtra("Score",score);
                startActivity(i);
            }
        }
        else if(b.getText().equals("M")){
            Toast.makeText(this,"Cant click on mines",Toast.LENGTH_SHORT).show();
        }
        else{
            b.setText(b.getValue());
            score++;
            textView.setText(score+"");
            b.setEnabled(false);
            b.setBackgroundColor(Color.GREEN);
            if(gameWon()){
                Intent i = new Intent(this, WinActivity.class);
                i.putExtra("Score",score);
                startActivity(i);
                //setContentView(R.layout.activity_win);
                //Toast.makeText(this,"You won!!",Toast.LENGTH_SHORT).show();
            }
        }
            }

    @Override
    public boolean onLongClick(View v) {
        MyButton b = (MyButton)v;
        if(b.getText() == "M" && minesRemained < noOfMines){
            //b.setValue("");
            b.setText("");
                minesRemained++;
                minesLeft.setText(minesRemained + "");
            }
        else{
            if(minesRemained > 0) {
                b.setText("M");
                //b.setValue("M");
                minesRemained--;
                minesLeft.setText(minesRemained + "");
                if(gameWon()){
                    Intent i = new Intent(this, WinActivity.class);
                    i.putExtra("Score",score);
                    startActivity(i);
                }
            }
        }
        return true;
    }

    public Boolean gameWon(){
        for(int i = 0 ; i < n ; i++){
            for(int j = 0 ; j < n ; j++){
                if(buttons[i][j].isEnabled() && buttons[i][j].getText()!= "M"){
                    return false;
                }
            }
        }
        return true;
    }
}