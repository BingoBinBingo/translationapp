package com.example.spencerdepas.translationapp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by SpencerDepas on 11/30/15.
 */
public class CreateJSONObject {


    private final String CHINESE_FOOD_PROTECTION_JSON = "t_hygiene_last_one";
    private String language = "";
    private String testToLoad = "";
    private InputStream is;

    private String TAG = "MyCreateJSONObject";
    private Context mContext;
    //private FoodProtectionQuestions foodProtectionQuestions;
    private DriversLicenseQuestions driverQuestions;

    public CreateJSONObject(String language, String testToLoad, Context mContext){
        Log.d(TAG, "CreateJSONObject");
        this.mContext = mContext;
        this.language = language;
        this.testToLoad = testToLoad;
    }

    public DriversLicenseQuestions loadDMVQuestions() {
        Log.d(TAG, "loadDMVQuestions");

        Log.d(TAG, "language " + language);
        String json = null;

        Gson gson = new Gson();


        try {

            //InputStream is = mContext.getAssets().open("t_hygiene_last_one.json");
            if(language.equals("chinese")){
                is = mContext.getAssets().open("driver_test_questions_hant.json");
            }else{
                is = mContext.getAssets().open("driver_test_questions_english.json");
                Log.d(TAG, "language " + "loading english");
            }


            int size = is.available();
            Log.d(TAG, "size " + size);

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");




        } catch (IOException ex) {
            ex.printStackTrace();

        }


        driverQuestions = gson.fromJson(json, DriversLicenseQuestions.class);

        Log.d(TAG, "reading obj : " + driverQuestions.getQuestions().size());
        return driverQuestions;
    }





}
