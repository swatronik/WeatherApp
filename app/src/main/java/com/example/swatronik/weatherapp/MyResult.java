package com.example.swatronik.weatherapp;

/**
 * Created by Swatronik on 23.06.2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyResult {


        @SerializedName("main")
        @Expose
        private Main main;

        public Main getMain() {
            return main;
        }

}