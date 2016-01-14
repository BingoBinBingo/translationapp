package com.example.spencerdepas.translationapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.spencerdepas.translationapp.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HealthSelect extends AppCompatActivity {

    private final String LANGUAGE_CHINESE =  "中文";
    private final String LANGUAGE_ENGLISH =  "English";
    private String language = LANGUAGE_CHINESE;
    private final String PREFS_LANGUAGE = "langagePreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_select);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.health_test_study)
    public void dMVStudyIntent(View view) {
//        Snackbar.make(view, "food_protection_study_button", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();

        Intent myIntent = new Intent(HealthSelect.this, HygieneActivity.class);
        myIntent.putExtra(PREFS_LANGUAGE, language); //Optional parameters
        HealthSelect.this.startActivity(myIntent);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.health_test_simulation)
    public void dMCSimulationTestIntent(View view) {
//        Snackbar.make(view, "food_protection_study_button", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();

        Intent myIntent = new Intent(HealthSelect.this, HealthTestSimulation.class);
        myIntent.putExtra(PREFS_LANGUAGE, language); //Optional parameters
        HealthSelect.this.startActivity(myIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int BACK_BUTTON = 16908332;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == BACK_BUTTON){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
