package com.example.spencerdepas.translationapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spencerdepas.translationapp.ButtonSelector;
import com.example.spencerdepas.translationapp.pojo.CitizenshipHolder;
import com.example.spencerdepas.translationapp.model.CreateJSONObject;
import com.example.spencerdepas.translationapp.model.GestureListener;
import com.example.spencerdepas.translationapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CitizenshipAcitivty extends AppCompatActivity implements ButtonSelector {

    private final String LANGUAGE_CHINESE =  "中文";
    private final String LANGUAGE_ENGLISH =  "English";
    private final String PREFS_LANGUAGE = "langagePreference";
    private String language = "";
    private final String TAG = "MyCitizenshipAcitivty";
    private Context mcontext;
    private int mIndex = 0;
    private View view;

    GestureDetectorCompat gDetect;

    @Bind(R.id.citizenship_question) TextView mCitizenshipQuestion;
    @Bind(R.id.citizenship_answer) TextView mCitizenshipAnswer;
    @Bind(R.id.citizenship_explanation) TextView mCitizenshipExplanation;
    @Bind(R.id.reveal_button) ImageView mRevealButton;
    @Bind(R.id.previous_question) Button mPreveousButton;
    @Bind(R.id.next_question) Button mNextButton;

    private CitizenshipHolder mCitizenshipHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizenship);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);



        Log.d(TAG, "onCreate");
        mcontext = this.getApplicationContext();

        Intent intent = getIntent();
        language = intent.getStringExtra(PREFS_LANGUAGE); //if it's a string you stored.

        Log.d(TAG, "language : " + language);

        //t_citizenship_english
        CreateJSONObject createJSONObject = new CreateJSONObject(this);
        //loads desired language
        mCitizenshipHolder = createJSONObject.loadCitizenshipQuestions(language);

        Log.d(TAG, "language : " + mCitizenshipHolder.getCitizenshipTestQuestions().get(0).getQuestion());
        view = findViewById(R.id.citizenship_root_view);

        if(language.equals(LANGUAGE_CHINESE)){
            mRevealButton.setAlpha((float).5);
            mRevealButton.setClickable(false);
        }

        loadQuestions();
        makePrevousButtonUnclickable();

        setUpGestures();


    }

    @Override
    public void gestureNextButton(){
        Log.d(TAG, "gestureNextButton :");
        nextQuestion();

    }

    @Override
    public void gesturePreviousButton(){
        Log.d(TAG, "gesturePreviousButton :" );

        preveousQuestion();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gDetect.onTouchEvent(ev);
    }

    public void setUpGestures(){
        Log.d(TAG, "setUpGestures :"  );
        GestureListener mGestureListener = new GestureListener();
        mGestureListener.delegate = CitizenshipAcitivty.this;
        gDetect = new GestureDetectorCompat(this, mGestureListener);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        Log.d(TAG, "onTouchEvent :"  );
        Log.d(TAG, "event :" + event.toString());
        this.gDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void makePrevousButtonUnclickable(){
        Log.d(TAG, "makePrevousButtonUnclickable");
        mPreveousButton.setClickable(false);
        mPreveousButton.setAlpha(.5f);
    }

    public void makePrevousButtonClickable(){
        Log.d(TAG, "makePrevousButtonClickable");
        mPreveousButton.setClickable(true);
        mPreveousButton.setAlpha(1);
    }

    public void loadQuestions(){
        mCitizenshipQuestion.setText(mCitizenshipHolder.getCitizenshipTestQuestions().get(mIndex).getQuestion());
        mCitizenshipAnswer.setText(mCitizenshipHolder.getCitizenshipTestQuestions().get(mIndex).getAnswer().replace("#", "\n"));
        mCitizenshipExplanation.setText(mCitizenshipHolder.getCitizenshipTestQuestions().get(mIndex).getExplanation());
    }



    public void nextQuestion(){
        makePrevousButtonClickable();

        Log.d(TAG, "mIndex : " + mIndex);
        Log.d(TAG, "mCitizenshipHolder.getCitizenshipTestQuestions().size() : "
                + mCitizenshipHolder.getCitizenshipTestQuestions().size());

        hideDetailText();
        if(mIndex  + 1 < mCitizenshipHolder.getCitizenshipTestQuestions().size()){
            mIndex +=1;
        }

        loadQuestions();

        if(mNextButton.getText().toString().equals(getString(R.string.finish))){

            Log.d(TAG, "destroyActivity : " );
            destroyActivity();
        }

        if(mIndex == mCitizenshipHolder.getCitizenshipTestQuestions().size() -1){
            mNextButton.setText(R.string.finish);
            Log.d(TAG, "mQuestionIndex == mWrongAnswersToStudy.size() -1");
        }




    }



    public void preveousQuestion(){
        hideDetailText();
        if(mIndex == 1){
            makePrevousButtonUnclickable();
        }
        if(mIndex > 0){
            mIndex -=1;
        }

        loadQuestions();

        if(mIndex != mCitizenshipHolder.getCitizenshipTestQuestions().size() -1){
            mNextButton.setText(R.string.next_button);
            Log.d(TAG, "mQuestionIndex == mWrongAnswersToStudy.size() -1");
        }

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.reveal_button)
    public void revealMoreDetailedAnswer(View view) {
        Log.d(TAG, "revealMoreDetailedAnswer");
        revealDetailText();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.reveal_cardView)
    public void cardViewOnClickReveal(View view) {


        mRevealButton.setVisibility(View.GONE);
        mCitizenshipExplanation.setVisibility(View.VISIBLE);



    }

    public void revealDetailText(){
        mRevealButton.setVisibility(View.GONE);
        mCitizenshipExplanation.setVisibility(View.VISIBLE);
    }

    public void hideDetailText(){
        mRevealButton.setVisibility(View.VISIBLE);
        mCitizenshipExplanation.setVisibility(View.GONE);
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

        gridView.setAdapter(new ArrayAdapter<>(this, R.layout.custom_list_item, mList));
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

                if (position == 0) {
                    makePrevousButtonUnclickable();
                } else {
                    makePrevousButtonClickable();
                }

                ad.dismiss();

            }
        });



    }

    private void destroyActivity(){
        finish();
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
            destroyActivity();
        }

        return super.onOptionsItemSelected(item);
    }

}
