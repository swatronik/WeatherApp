package com.example.swatronik.weatherapp;

import io.realm.RealmObject;

/**
 * Created by Swatronik on 22.06.2018.
 */

public class RealmText extends RealmObject {

    private String temp;
    private String time;

    public void setTemp(String text){
        this.temp = text;
    }

    public String getTemp(){
        return this.temp;
    }

    public void setTime(String text){
        this.time = text;
    }

    public String getTime(){
        return this.time;
    }
}
