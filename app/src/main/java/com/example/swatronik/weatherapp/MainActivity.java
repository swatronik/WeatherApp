package com.example.swatronik.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        Realm.init(this);
        final Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmGeo> storeGeo = realm.where(RealmGeo.class).findAll();

        RealmGeo coordinates = new RealmGeo();


        if (storeGeo.size() == 0) {
            Geocoder gd = new Geocoder(this, Locale.getDefault());
            List<Address> adress;

            try {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }

                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);



                adress = gd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                coordinates = new RealmGeo();
                coordinates.setGeo(adress.get(0).getLocality().toString());
                realm.beginTransaction();
                realm.copyToRealm(coordinates);
                realm.commitTransaction();
                realm.close();
            }catch (Exception e){
                    textView.setText("Error, permission to use the phone's location is not working");
                }
             }
        else {
            for (RealmGeo rg: storeGeo) {
                coordinates=rg;
        }}

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        final String strDate = sdf.format(c.getTime());


        final RealmResults<RealmText> storeText = realm.where(RealmText.class).findAll();

        if(storeText.size()==0){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyResultServer service = retrofit.create(MyResultServer.class);
        service.request(coordinates.getGeo(),"metric","6f60a2e2eba0ac6d5868f11ba9b8c10b").enqueue(new Callback<MyResult>() {
            @Override
            public void onResponse(Call<MyResult> call, Response<MyResult> response) {
                TextView tv = (TextView)findViewById(R.id.text);
                String nowTemp = response.body().getMain().getTemp().toString();
                tv.setText(nowTemp+"°C");
                RealmText rt = new RealmText();
                rt.setTime(strDate);
                rt.setTemp(nowTemp);
                realm.beginTransaction();
                realm.copyToRealm(rt);
                realm.commitTransaction();
                realm.close();
            }

            @Override
            public void onFailure(Call<MyResult> call, Throwable t) {
                TextView tv = (TextView)findViewById(R.id.text);
                tv.setText(t.getMessage());
            }
        });}
        else {
            RealmText realmText=new RealmText();
        for (RealmText rt: storeText) {
            realmText=rt;
        }
                if (strDate!=realmText.getTime()){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://api.openweathermap.org")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    MyResultServer service = retrofit.create(MyResultServer.class);
                    service.request(coordinates.getGeo(),"metric","6f60a2e2eba0ac6d5868f11ba9b8c10b").enqueue(new Callback<MyResult>() {
                        @Override
                        public void onResponse(Call<MyResult> call, Response<MyResult> response) {
                            TextView tv = (TextView)findViewById(R.id.text);
                            String nowTemp = response.body().getMain().getTemp().toString();
                            tv.setText(nowTemp+"°C");
                            RealmText rt = new RealmText();
                            rt.setTime(strDate);
                            rt.setTemp(nowTemp);
                            realm.beginTransaction();
                            realm.copyToRealm(rt);
                            realm.commitTransaction();
                            realm.close();
                        }

                        @Override
                        public void onFailure(Call<MyResult> call, Throwable t) {
                            TextView tv = (TextView)findViewById(R.id.text);
                            tv.setText(t.getMessage());
                        }
                    });
                }else {
                    TextView tv = (TextView)findViewById(R.id.text);
                    tv.setText(realmText.getTemp()+"°C");
                }}
        TextView city = (TextView)findViewById(R.id.textView);
        city.setText(coordinates.getGeo());
    }

}
