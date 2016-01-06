package com.example.spencerdepas.translationapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.spencerdepas.translationapp.R;
import com.example.spencerdepas.translationapp.model.CreateJSONObject;
import com.example.spencerdepas.translationapp.pojo.HygieneContainer;
import com.example.spencerdepas.translationapp.pojo.NailQuestionContainer;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class HygieneActivity extends AppCompatActivity {

    private ArrayList<Integer> mWrongAnswersToStudy = new ArrayList<Integer>();
    private String TAG = "MyHygieneActivity";
    private final String PREFS_LANGUAGE = "langagePrference";
    private String language = null;
    private  String picLocation;
    private Context mcontext;

    private HygieneContainer loadHygieneContainerQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "onCreate");
        mcontext = this.getApplicationContext();

        Intent intent = getIntent();
        language = intent.getStringExtra(PREFS_LANGUAGE); //if it's a string you stored.

        Log.d(TAG, "language : " + language);

        CreateJSONObject createJSONObject = new CreateJSONObject(language, "This should specifie test type", this);
        loadHygieneContainerQuestions = createJSONObject.loadHygieneContainerQuestions();

        Log.d(TAG, "loadHygieneContainerQuestions size : " + loadHygieneContainerQuestions.getHygineTestQuestions().size());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
