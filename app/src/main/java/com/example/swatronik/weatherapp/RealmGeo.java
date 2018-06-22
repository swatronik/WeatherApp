package com.example.swatronik.weatherapp;

import io.realm.RealmObject;

/**
 * Created by Swatronik on 22.06.2018.
 */
public class RealmGeo extends RealmObject {

    private String geo;

    public void setGeo(String text){
        this.geo = text;
    }

    public String getGeo(){
        return this.geo;
    }

}
