package com.example.swatronik.weatherapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tech on 17.05.2018.
 */

public interface MyResultServer {
    @GET("data/2.5/weather")
    Call<MyResult> request(@Query("q") String q,@Query("units") String units,@Query("APPID") String APPID);
}
