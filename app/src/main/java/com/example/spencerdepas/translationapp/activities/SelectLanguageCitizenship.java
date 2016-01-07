package com.example.spencerdepas.translationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.spencerdepas.translationapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectLanguageCitizenship extends AppCompatActivity {

    private String TAG = "MySelectLanguage";
    private final String LANGUAGE_CHINESE =  "中文";
    private final String LANGUAGE_ENGLISH =  "English";
    private final String PREFS_LANGUAGE = "langagePreference";
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizenship_select_language_chinese);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        view = findViewById(R.id.select_language_root_view);

       Snackbar.make(view, getString(R.string.explanation), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.citizenship_cardview_select_chinese)
    public void selectChinese(View view) {
        Intent myIntent = new Intent(SelectLanguageCitizenship.this, CitizenshipAcitivty.class);
        myIntent.putExtra(PREFS_LANGUAGE, LANGUAGE_CHINESE);
        SelectLanguageCitizenship.this.startActivity(myIntent);

    }


    @SuppressWarnings("unused")
    @OnClick(R.id.citizenship_cardview_select_english)
    public void selectEnglish(View view) {

        Intent myIntent = new Intent(SelectLanguageCitizenship.this, CitizenshipAcitivty.class);
        myIntent.putExtra(PREFS_LANGUAGE, LANGUAGE_ENGLISH);
        SelectLanguageCitizenship.this.startActivity(myIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int BACK_BUTTON = 16908332;
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d(TAG, "id : " + id);
        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            Log.d(TAG, "Changed langague");


        }else if(id == BACK_BUTTON){
            Log.d(TAG, "BackButton");
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
