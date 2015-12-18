package com.example.spencerdepas.translationapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CitizenshipAcitivty extends AppCompatActivity {

    private final String LANGUAGE_CHINESE =  "chinese";
    private final String LANGUAGE_ENGLISH =  "english";
    private final String PREFS_LANGUAGE = "langagePrference";
    private String language = LANGUAGE_CHINESE;
    private final String TAG = "MyCitizenshipAcitivty";
    private Context mcontext;
    private int mIndex = 0;


    @Bind(R.id.citizenship_question) TextView mCitizenshipQuestion;
    @Bind(R.id.citizenship_answer) TextView mCitizenshipAnswer;
    @Bind(R.id.citizenship_explanation) TextView mCitizenshipExplanation;


    private CitizenshipHolder mCitizenshipHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizenship);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        Log.d(TAG, "onCreate");
        mcontext = this.getApplicationContext();

        Intent intent = getIntent();
        language = intent.getStringExtra(PREFS_LANGUAGE); //if it's a string you stored.

        Log.d(TAG, "language : " + language);

        //t_citizenship_english
        CreateJSONObject createJSONObject = new CreateJSONObject(language, "This should specifie test type", this);
        mCitizenshipHolder = createJSONObject.loadCitizenshipQuestions();

        Log.d(TAG, "language : " + mCitizenshipHolder.getCitizenshipTestQuestions().get(0).getQuestion());



        loadQuestions();


    }

    public void loadQuestions(){
        mCitizenshipQuestion.setText(mCitizenshipHolder.getCitizenshipTestQuestions().get(mIndex).getQuestion());
        mCitizenshipAnswer.setText(mCitizenshipHolder.getCitizenshipTestQuestions().get(mIndex).getAnswer());
        mCitizenshipExplanation.setText(mCitizenshipHolder.getCitizenshipTestQuestions().get(mIndex).getExplanation());
    }

    public void nextQuestion(){

        Log.d(TAG, "mIndex : " + mIndex);
        Log.d(TAG, "mCitizenshipHolder.getCitizenshipTestQuestions().size() : "
                + mCitizenshipHolder.getCitizenshipTestQuestions().size());

        if(mIndex  + 1 < mCitizenshipHolder.getCitizenshipTestQuestions().size()){
            mIndex +=1;
        }

        mCitizenshipQuestion.setText(mCitizenshipHolder.getCitizenshipTestQuestions().get(mIndex).getQuestion());
        mCitizenshipAnswer.setText(mCitizenshipHolder.getCitizenshipTestQuestions().get(mIndex).getAnswer());
        mCitizenshipExplanation.setText(mCitizenshipHolder.getCitizenshipTestQuestions().get(mIndex).getExplanation());
    }

    public void preveousQuestion(){
        if(mIndex > 0){
            mIndex -=1;
        }

        mCitizenshipQuestion.setText(mCitizenshipHolder.getCitizenshipTestQuestions().get(mIndex).getQuestion());
        mCitizenshipAnswer.setText(mCitizenshipHolder.getCitizenshipTestQuestions().get(mIndex).getAnswer());
        mCitizenshipExplanation.setText(mCitizenshipHolder.getCitizenshipTestQuestions().get(mIndex).getExplanation());
    }


    @SuppressWarnings("unused")
    @OnClick(R.id.next_question)
    public void nextQuestion(View view) {
        nextQuestion();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.previous_question)
    public void preveousQuestion(View view) {
        preveousQuestion();
    }


    @SuppressWarnings("unused")
    @OnClick(R.id.fab)
    public void submit(View view) {
        showSelectQuestionDialog();
    }



    private void showSelectQuestionDialog() {
        Log.d(TAG, "showAlertDialog");
        // Prepare grid view
        GridView gridView = new GridView(this);

        List<Integer> mList = new ArrayList<Integer>();
        for (int i = 1; i <= mCitizenshipHolder.getCitizenshipTestQuestions().size(); i++) {
            mList.add(i);
        }

        gridView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList));
        gridView.setNumColumns(4);


        // Set grid view to alertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(gridView);
        builder.setTitle(getResources().getString(R.string.select_question));
        builder.setPositiveButton(getResources().getString(R.string.dialog_dismiss),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "DISMISS");

                        dialog.dismiss();
                    }
                });

        final AlertDialog ad = builder.show();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // do something here
                Log.d(TAG, "onclick");
                Log.d(TAG, "int pos : " + position);

                //minus one as when next question is pressed
                //it adds one
                mIndex = position -1;
                nextQuestion();
                ad.dismiss();

            }
        });



    }

}
