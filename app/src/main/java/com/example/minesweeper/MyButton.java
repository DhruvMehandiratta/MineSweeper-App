package com.example.minesweeper;

import android.content.Context;
import android.widget.Button;

/**
 * Created by Dhruv on 05-02-2017.
 */

public class MyButton extends Button {

    String value;
    int x,y;
    public MyButton(Context context,int x,int y){
        super(context);
        this.x = x;
        this.y = y;
        this.value = "";
    }
    public int getXCoordinate(){
        return this.x;
    }
    public int getYCoordinate(){
        return this.y;
    }
    public void setMine(){
        this.value = "*";
    }
    public boolean isMine(){
        if(this.value == "*"){
            return true;
        }
        return false;
    }
    public void setValue(String n){

        this.value = n;
    }
    public String getValue()
    {
        return this.value;
    }

}
