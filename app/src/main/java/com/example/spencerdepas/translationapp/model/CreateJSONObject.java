package com.example.spencerdepas.translationapp.model;

import android.content.Context;
import android.util.Log;

import com.example.spencerdepas.translationapp.pojo.DriversLicenseQuestions;
import com.example.spencerdepas.translationapp.pojo.HygieneContainer;
import com.example.spencerdepas.translationapp.pojo.NailQuestionContainer;
import com.example.spencerdepas.translationapp.pojo.CitizenshipHolder;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by SpencerDepas on 11/30/15.
 */
public class CreateJSONObject {

    private final String LANGUAGE_CHINESE =  "中文";
    private final String LANGUAGE_ENGLISH =  "English";
    private final String CHINESE_FOOD_PROTECTION_JSON = "t_hygiene_last_one";

    private String testToLoad = "";
    private InputStream is;

    private String TAG = "MyCreateJSONObject";
    private Context mContext;
    //private FoodProtectionQuestions foodProtectionQuestions;
    private DriversLicenseQuestions driverQuestions;
    private CitizenshipHolder mCitizenshipHolder;
    private NailQuestionContainer myNailQuestionContainer;
    private HygieneContainer mHygieneContainer;


    public CreateJSONObject(Context mContext){
        Log.d(TAG, "CreateJSONObject");
        this.mContext = mContext;

    }

    public DriversLicenseQuestions loadDMVQuestions(String language) {
        Log.d(TAG, "loadDMVQuestions");

        Log.d(TAG, "language " + language);
        String json = null;

        Gson gson = new Gson();


        try {

            //InputStream is = mContext.getAssets().open("t_hygiene_last_one.json");
            if(language.equals(LANGUAGE_CHINESE)){
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

    public NailQuestionContainer loadNailQuestions(String language) {
        Log.d(TAG, "loadNailQuestions");

        Log.d(TAG, "language " + language);
        String json = null;

        Gson gson = new Gson();


        try {

            //InputStream is = mContext.getAssets().open("t_hygiene_last_one.json");
            if(language.equals(LANGUAGE_CHINESE)){
                is = mContext.getAssets().open("t_nail_chinese.json");
            }else{
                is = mContext.getAssets().open("t_nail_english.json");
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


        myNailQuestionContainer = gson.fromJson(json, NailQuestionContainer.class);

        Log.d(TAG, "reading obj : " + myNailQuestionContainer.getQuestions().size());
        return myNailQuestionContainer;
    }

    public HygieneContainer loadHygieneContainerQuestions(String language) {
        Log.d(TAG, "loadHygieneContainerQuestions");

        Log.d(TAG, "language " + language);
        String json = null;

        Gson gson = new Gson();


        try {

            //InputStream is = mContext.getAssets().open("t_hygiene_last_one.json");
            if(language.equals(LANGUAGE_CHINESE)){
                is = mContext.getAssets().open("t_hygiene_chinese.json");
            }else{
                is = mContext.getAssets().open("t_hygiene_chinese.json");
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


        mHygieneContainer = gson.fromJson(json, HygieneContainer.class);

        Log.d(TAG, "reading obj : " + mHygieneContainer.getQuestions().size());
        return mHygieneContainer;
    }


    public CitizenshipHolder loadCitizenshipQuestions(String language) {
        Log.d(TAG, "loadDMVQuestions");

        Log.d(TAG, "language " + language);
        String json = null;

        Gson gson = new Gson();


        try {
            //temp

            //InputStream is = mContext.getAssets().open("t_hygiene_last_one.json");
            if(language.equals(LANGUAGE_CHINESE)){
                is = mContext.getAssets().open("t_citizenship_hant.json");
            }else{
                is = mContext.getAssets().open("t_citizenship_english.json");

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


        mCitizenshipHolder = gson.fromJson(json, CitizenshipHolder.class);

        Log.d(TAG, "reading obj size : " + mCitizenshipHolder.getCitizenshipTestQuestions().size());
        return mCitizenshipHolder;
    }


}
