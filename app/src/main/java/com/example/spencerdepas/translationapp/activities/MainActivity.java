package com.example.spencerdepas.translationapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.crashlytics.android.Crashlytics;
import com.example.spencerdepas.translationapp.R;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private final String LANGUAGE_CHINESE =  "中文";
    private final String LANGUAGE_ENGLISH =  "English";
    private final String PREFS_LANGUAGE = "langagePreference";
    private String language = LANGUAGE_CHINESE;
    private String TAG = "MyMainActivity";

    @Bind(R.id.nail_cardview) CardView mTestCardView;
    @Bind(R.id.dmv_cardview) CardView mDMVCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        language = getLanguage(this);
        Log.d(TAG, "language : " + Locale.getDefault().getDisplayLanguage());


        //mStudyDMVCardView.setCardBackgroundColor(R.color.colorPrimary);
        //mTestCardView.setCardBackgroundColor(R.color.colorAccent);

    }

    public String getLanguage(Context context) {
        SharedPreferences settings;
        String text;
        settings = context.getSharedPreferences(PREFS_LANGUAGE, Context.MODE_PRIVATE); //1
        text = settings.getString(PREFS_LANGUAGE, "english"); //2
        return text;
    }


    @OnClick(R.id.nail_cardview)
    public void nailTest(View view) {
        Intent myIntent = new Intent(MainActivity.this, NailTestActivity.class);
        myIntent.putExtra(PREFS_LANGUAGE, language); //Optional parameters
        MainActivity.this.startActivity(myIntent);

    }



    @OnClick(R.id.dmv_cardview)
    public void dMVStudyIntent(View view) {
//        Snackbar.make(view, "food_protection_study_button", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();

        Intent myIntent = new Intent(MainActivity.this, DMVSelect.class);
        myIntent.putExtra(PREFS_LANGUAGE, language); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }





    @OnClick(R.id.citizenship_cardview)
    public void citizenshipIntent(View view) {
//        Snackbar.make(view, "food_protection_study_button", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();

        Intent myIntent = new Intent(MainActivity.this, CitizenshipAcitivty.class);
        myIntent.putExtra(PREFS_LANGUAGE, language); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }



//
//    @OnClick(R.id.health_cardview)
//    public void healthTestIntent(View view) {
//
//
//        Intent myIntent = new Intent(MainActivity.this, HygieneActivity.class);
//        myIntent.putExtra(PREFS_LANGUAGE, language); //Optional parameters
//        MainActivity.this.startActivity(myIntent);
//    }


    @OnClick(R.id.fab)
    public void submit(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d(TAG, "Changed langague");


            emailIntent();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    public void emailIntent(){

        try{
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto",getString(R.string.email_address), null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
            startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }catch (Exception e){
            Snackbar.make(mDMVCardView, "OPPS", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }



    }

}
