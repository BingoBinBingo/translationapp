package com.example.spencerdepas.translationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.example.spencerdepas.translationapp.activities.MainActivity;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;

/**
 * Created by SpencerDepas on 1/1/16.
 */
public class SplashActivity  extends AppCompatActivity {

    private final String LANGUAGE_CHINESE =  "中文";
    private final String LANGUAGE_ENGLISH =  "English";

    private String TAG = "MySplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        Log.d(TAG, "language : " + Locale.getDefault().getDisplayLanguage());



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startMainActivity();
            }
        }, 0001);



    }

    public void startMainActivity(){
        if(Locale.getDefault().getDisplayLanguage().equals(LANGUAGE_CHINESE)){
            Intent intent = new Intent(this, MainActivityChinese.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}