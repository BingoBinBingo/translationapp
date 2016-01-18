package com.example.spencerdepas.translationapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.spencerdepas.translationapp.ButtonSelector;
import com.example.spencerdepas.translationapp.R;
import com.example.spencerdepas.translationapp.model.CreateJSONObject;
import com.example.spencerdepas.translationapp.model.GestureListener;
import com.example.spencerdepas.translationapp.pojo.HygieneContainer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

public class HealthTestSimulation extends AppCompatActivity implements ButtonSelector {


    private boolean testCompletedStudymode = false;
    private final String NULL_STRING =  "<null>";
    private final String LANGUAGE_CHINESE =  "Chinese";
    private final String LANGUAGE_ENGLISH =  "English";
    private ArrayList<Integer> mWrongAnswersToStudy = new ArrayList<Integer>();
    private String TAG = "MyHealthTestSimulation";
    private final String PREFS_LANGUAGE = "langagePrference";
    private String language = null;
    private  String picLocation;
    private Context mcontext;
    private int mQuestionIndex = 0;
    private View view;
    Integer[] mQuestionIndexArray;

    private GestureDetectorCompat gDetect;

    @Bind(R.id.driver_test_question)
    TextView mQuestion;
    @Bind(R.id.option_a) RadioButton mOptionOne;
    @Bind(R.id.option_b) RadioButton mOptionTwo;
    @Bind(R.id.option_c) RadioButton mOptionThree;
    @Bind(R.id.option_d) RadioButton mOptionFour;
    @Bind(R.id.option_e) RadioButton mOptionFive;
    @Bind(R.id.image_view) ImageView mImage;
    @Bind(R.id.myRadioGroup) RadioGroup radioGroup;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.previous_question)
    Button mPreveousButton;
    @Bind(R.id.next_question) Button mNextButton;
    @Bind(R.id.displays_wrong_answer) TextView mDisplayCorrectAnswer;

    private HygieneContainer loadHygieneContainerQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_test_simulation);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "onCreate");
        mcontext = this.getApplicationContext();
        view = findViewById(R.id.hygiene_root_view_simulation);

        language = Locale.getDefault().getDisplayLanguage(); //if it's a string you stored.

        Log.d(TAG, "language : " + language);

        CreateJSONObject createJSONObject = new CreateJSONObject( this);
        loadHygieneContainerQuestions = createJSONObject.loadHygieneContainerQuestions(language);

        Log.d(TAG, "loadHygieneContainerQuestions size : " + loadHygieneContainerQuestions.getQuestions().size());


        mQuestionIndexArray = generateRandomQuestionIndex();


        updateQuestion();

        makePrevousButtonUnclickable();

        setUpGestures();

    }



    public Integer[] generateRandomQuestionIndex() {
        Log.d(TAG, "generateRandomQuestionIndex");

        //generates 4 random numbers for the image questions
        //generates 16 random numbers for only worded questions
        //total questions are 194
        //first 36 are image questions

        Set<Integer> set = new HashSet<Integer>();

        int minimum = 0;
        for(int i = 0 ; set.size() < 50; i ++){

            set.add(minimum + (int) (Math.random() * 160));

        }


        Integer[] mQuestionIndex = set.toArray(new Integer[0]);

        return mQuestionIndex;


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gDetect.onTouchEvent(ev);
    }

    public void setUpGestures(){
        Log.d(TAG, "setUpGestures :"  );
        GestureListener mGestureListener = new GestureListener();
        mGestureListener.delegate = HealthTestSimulation.this;
        gDetect = new GestureDetectorCompat(this, mGestureListener);


    }

    public void formatAnswers(){
        Log.d(TAG, "formatAnswers");


        if(testCompletedStudymode){


            //this adds True and False or removes two null questions
            Log.d(TAG, "formatAnswers mOption one" +
                    loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionA());
            Log.d(TAG, "formatAnswers mOption 2" +
                    loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionB());
            Log.d(TAG, "formatAnswers mOption 3" +
                    loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionC());
            Log.d(TAG, "formatAnswers mOption 4" +
                    loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionD());
            Log.d(TAG, "formatAnswers mOption 5" +
                    loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionE());

            Log.d(TAG, "selected answer" +
                    loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getSelectedAnswer());

            if(  loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionA()
                    .equals(NULL_STRING)){
                //adds truth or false
                Log.d(TAG, "formatAnswers True or false only two posible answers");
                loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex))
                        .setOptionA(getResources().getString(R.string.answer_true));
                loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex))
                        .setOptionB(getResources().getString(R.string.answer_false));
                mOptionThree.setVisibility(View.INVISIBLE);
                mOptionFour.setVisibility(View.INVISIBLE);
                mOptionFive.setVisibility(View.INVISIBLE);
            }else{

                if( loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionC()
                        .equals(NULL_STRING)){
                    Log.d(TAG, "formatAnswers not True or false still only two posible answers");
                    mOptionThree.setVisibility(View.INVISIBLE);
                    mOptionFour.setVisibility(View.INVISIBLE);
                    mOptionFive.setVisibility(View.INVISIBLE);



                }else  if(  loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionD()
                        .equals(NULL_STRING)){
                    mOptionFour.setVisibility(View.INVISIBLE);
                    mOptionFive.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "formatAnswers three posible answers");

                    Log.d(TAG, "formatAnswers mOptionFive get text " + mOptionFive.getText());


                }else if(  loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionE()
                        .equals(NULL_STRING)){
                    Log.d(TAG, "formatAnswers four posible answers");
                    mOptionFive.setVisibility(View.INVISIBLE);

                }else{
                    Log.d(TAG, "formatAnswers five posible answers");
                }

            }


        }else{


            //this adds True and False or removes two null questions
            Log.d(TAG, "formatAnswers mOption one" +
                    loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionA());
            Log.d(TAG, "formatAnswers mOption 2" +
                    loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionB());
            Log.d(TAG, "formatAnswers mOption 3" +
                    loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionC());
            Log.d(TAG, "formatAnswers mOption 4" +
                    loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionD());
            Log.d(TAG, "formatAnswers mOption 5" +
                    loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionE());

            if(  loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionA()
                    .equals(NULL_STRING)){
                //adds truth or false
                Log.d(TAG, "formatAnswers True or false only two posible answers");
                loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex])
                        .setOptionA(getResources().getString(R.string.answer_true));
                loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex])
                        .setOptionB(getResources().getString(R.string.answer_false));
                mOptionThree.setVisibility(View.INVISIBLE);
                mOptionFour.setVisibility(View.INVISIBLE);
                mOptionFive.setVisibility(View.INVISIBLE);
            }else{

                Log.d(TAG, "formatAnswers loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionC() :"+
                        loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionC());
                if( loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionC()
                        .equals(NULL_STRING)){
                    Log.d(TAG, "formatAnswers not True or false still only two posible answers");
                    mOptionThree.setVisibility(View.INVISIBLE);
                    mOptionFour.setVisibility(View.INVISIBLE);
                    mOptionFive.setVisibility(View.INVISIBLE);



                }else  if(  loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionD()
                        .equals(NULL_STRING)){
                    mOptionFour.setVisibility(View.INVISIBLE);
                    mOptionFive.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "formatAnswers three posible answers");

                    Log.d(TAG, "formatAnswers mOptionFive get text " + mOptionFive.getText());


                }else if(  loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionE()
                        .equals(NULL_STRING)){
                    Log.d(TAG, "formatAnswers four posible answers");
                    mOptionFive.setVisibility(View.INVISIBLE);

                }else{
                    Log.d(TAG, "formatAnswers five posible answers");
                }

            }
        }

    }

    public void setUpWrongAnswersAfterTestComplete(){
        Log.d(TAG, "updateQuestion");

        mOptionFive.setVisibility(View.VISIBLE);
        mOptionOne.setVisibility(View.VISIBLE);
        mOptionTwo.setVisibility(View.VISIBLE);
        mOptionThree.setVisibility(View.VISIBLE);
        mOptionFour.setVisibility(View.VISIBLE);
        formatAnswers();


        mQuestion.setText(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getQuestion());
        mOptionOne.setText(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionA());
        mOptionTwo.setText(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionB());
        mOptionThree.setText(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionC());
        mOptionFour.setText(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionD());

        if(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionE().equals(NULL_STRING)){
            mOptionFive.setVisibility(View.INVISIBLE);
        }else {
            mOptionFive.setVisibility(View.VISIBLE);
            mOptionFive.setText(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionE());
        }


        if(!loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getSelectedAnswer().equals("")){
            Log.d(TAG, "question  answered : ");
            if (loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).isAnsweredCorrectly()) {
                Log.d(TAG, "answer right");
//                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
//                mDisplayCorrectAnswer.setText( getResources().getString(R.string.correct));

                radioGroup.setEnabled(false);

            }else {
                Log.d(TAG, "updateQuestion answer wrong");
                //condition for id you press prevous button and the answer has not been answered
                if(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).isAnsweredCorrectly()){
//                    mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
//                    mDisplayCorrectAnswer.setText(getResources().getString(R.string.correct_answer_above) +
//                            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer());
                }



            }
        }else{

            mDisplayCorrectAnswer.setVisibility(View.INVISIBLE);
        }




    }

    public void updateQuestion(){
        Log.d(TAG, "updateQuestion");


        formatAnswers();


        mQuestion.setText(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getQuestion());
        mOptionOne.setText(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionA());
        mOptionTwo.setText(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionB());
        mOptionThree.setText(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionC());
        mOptionFour.setText(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionD());

        if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionE().equals(NULL_STRING)){
            mOptionFive.setVisibility(View.INVISIBLE);
        }else {
            mOptionFive.setVisibility(View.VISIBLE);
            mOptionFive.setText(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionE());
        }


        if(!loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer().equals("")){
            Log.d(TAG, "question  answered : ");
            if (loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).isAnsweredCorrectly()) {
                Log.d(TAG, "answer right");
//                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
//                mDisplayCorrectAnswer.setText( getResources().getString(R.string.correct));

                radioGroup.setEnabled(false);

            }else {
                Log.d(TAG, "updateQuestion answer wrong");
                //condition for id you press prevous button and the answer has not been answered
                if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).isAnsweredCorrectly()){
//                    mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
//                    mDisplayCorrectAnswer.setText(getResources().getString(R.string.correct_answer_above) +
//                            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer());
                }



            }
        }else{

            mDisplayCorrectAnswer.setVisibility(View.INVISIBLE);
        }




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

    public void loadRadioButtonSelection(){
        Log.d(TAG, "loadRadioButtonSelection");


        //this loads which radio button was selected if you go back to an answered question
        if(!loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer()
                .equals("")){

            Log.d(TAG, "radio button has been clicked and will be saved");

            radioGroup.check(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex])
                    .getSelectedAnswerResourceId());

        }
    }

    @Override
    public void gestureNextButton(){
        Log.d(TAG, "gestureNextButton :" );

        Log.d(TAG, "mindex size :" + loadHygieneContainerQuestions.getQuestions().size());
        Log.d(TAG, "mQuestionIndex :" + mQuestionIndexArray[mQuestionIndex]);

        if(mQuestionIndex == loadHygieneContainerQuestions.getQuestions().size() -1){
            Log.d(TAG, "mQuestionIndex == driverQuestions.getQuestions().size()");
            // do nothing
        }else {
            nextQuestion(view);
        }


    }

    @Override
    public void gesturePreviousButton(){
        Log.d(TAG, "gesturePreviousButton :");

        previousQuestion(view);
    }


    public void displayIfAnswerIsRightOrWrongForRegularQuestions(String rightAnswerIndex ){
        Log.d(TAG, "displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions ");

        Log.d(TAG, "displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions ");

        Log.d(TAG, "displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions " +
                loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer()
                        .toLowerCase());

        Log.d(TAG, "displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions " +
                loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer());

        if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer()
                .toLowerCase().equals(rightAnswerIndex)){

            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnsweredCorrectly(true);
            Log.d(TAG, "displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions answer right");
            mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
            mDisplayCorrectAnswer.setText(getResources().getString(R.string.correct));
            nextQuestion(view);
        }else{
            Log.d(TAG, "displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions radioOptionTwo answer wrong");

            mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
            mDisplayCorrectAnswer.setText(getResources().getString(R.string.correct_answer_above) +
                    loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer());
        }
    }



    public void displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(String rightAnswerIndex){
        Log.d(TAG, "displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions ");




        if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer()
                .toLowerCase().equals(rightAnswerIndex)){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(true);
            Log.d(TAG, "answer right");
            mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
            mDisplayCorrectAnswer.setText(getResources().getString(R.string.correct));
            nextQuestion(view);
        }else{
            Log.d(TAG, "radioOptionTwo answer wrong");

            mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
            mDisplayCorrectAnswer.setText(getResources().getString(R.string.correct_answer_above) +
                    loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer());
        }
    }

    @OnClick(R.id.option_a)
    public void radioOptionOne(View view) {
        //indexus are diferent because apis are shit
        Log.d(TAG, "radioOptionOne");
        Log.d(TAG, "testCompletedStudymode" + testCompletedStudymode);

        if(testCompletedStudymode){
            if(mOptionOne.getText().toString().equals(getResources().getString(R.string.answer_true))){
                //if the question is a true or false question
                String rightAnswerIndex = "0";
                displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);

            }else {
                //notTrueOrFalseQuestion(lastChracterString);
                String rightAnswerIndex = "1";
                displayIfAnswerIsRightOrWrongForRegularQuestions(rightAnswerIndex);
            }
        }else{

        }



    }

    @OnClick(R.id.option_b)
    public void radioOptionTwo(View view) {
        Log.d(TAG, "radioOptionTwo");

        Log.d(TAG, "radioOptionTwo mOptionTwo.getText().toString().equals(getResources().getString(R.string.answer_false) : " +
                mOptionTwo.getText().toString().equals(getResources().getString(R.string.answer_false)));

        Log.d(TAG, "radioOptionTwo mDisplayCorrectAnswer.getText().length() != 0 " +
                (mDisplayCorrectAnswer.getText().length() != 0));



        if(testCompletedStudymode){
            if(mOptionTwo.getText().toString().equals(getResources().getString(R.string.answer_false))){
                //if the question is a true or false question
                Log.d(TAG, "radioOptionTwo is tru or false question ");
                String rightAnswerIndex = "1";

                displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);


            }else {
                String rightAnswerIndex = "2";
                displayIfAnswerIsRightOrWrongForRegularQuestions(rightAnswerIndex);
            }
        }else{

        }

    }

    @OnClick(R.id.option_c)
    public void radioOptionThree(View view) {
        Log.d(TAG, "radioOptionThree");


        if(testCompletedStudymode){
            String rightAnswerIndex = "3";
            if(mOptionOne.getText().toString().equals(getResources().getString(R.string.answer_true))){
                //if the question is a true or false question

                displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);

            }else{
                //notTrueOrFalseQuestion(lastChracterString);
                displayIfAnswerIsRightOrWrongForRegularQuestions(rightAnswerIndex);
            }
        }else{

        }


    }

    @OnClick(R.id.option_d)
    public void radioOptionFour(View view) {
        Log.d(TAG, "radioOptionFour");


        if(testCompletedStudymode){
            String rightAnswerIndex = "4";
            if(mOptionOne.getText().toString().equals(getResources().getString(R.string.answer_true))){
                //if the question is a true or false question

                displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);

            }else{
                //notTrueOrFalseQuestion(lastChracterString);
                displayIfAnswerIsRightOrWrongForRegularQuestions(rightAnswerIndex);
            }
        }else{

        }


    }

    @OnClick(R.id.option_e)
    public void radioOptionFive(View view) {
        Log.d(TAG, "radioOptionFour");


        if(testCompletedStudymode){
            String rightAnswerIndex = "5";
            if(mOptionOne.getText().toString().equals(getResources().getString(R.string.answer_true))){
                //if the question is a true or false question

                displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);

            }else{
                //notTrueOrFalseQuestion(lastChracterString);
                displayIfAnswerIsRightOrWrongForRegularQuestions(rightAnswerIndex);
            }
        }else{


        }



    }

    @OnClick(R.id.next_question)
    public void nextQuestion(View view) {
        Log.d(TAG, "nextQuestion");

        //mWrongAnswers
        mOptionThree.setVisibility(View.VISIBLE);
        mOptionFour.setVisibility(View.VISIBLE);
        mOptionFive.setVisibility(View.VISIBLE);

        makePrevousButtonClickable();

        radioGroup.setEnabled(true);
        saveAnswer();


        Log.d(TAG, "myNailQuestionContainer.getQuestions().size()" + loadHygieneContainerQuestions.getQuestions().size());
        Log.d(TAG, "mQuestionIndex " + mQuestionIndexArray[mQuestionIndex]);

        if(mWrongAnswersToStudy.size() > 0){
            //this is for when we have completed the test once and
            //want to go over the wrong answer
            mQuestionIndex += 1;
            studyWrongAnswersNextQuestion();

        }else if(mQuestionIndex < mQuestionIndexArray.length -1){
            //on the first time through we go here
            regularNextQuestion();

        }else{


            Log.d(TAG, "We are on the 50th question " );
            formatAnswers();

            haveAllQuestionsBeenAnswered();

        }




    }

    public void studyWrongAnswersNextQuestion(){
        Log.d(TAG, "mWrongAnswersToStudy.size() : " + mWrongAnswersToStudy.size());




        if(mQuestionIndex < mWrongAnswersToStudy.size() ){
            Log.d(TAG, "mQuestionIndex < mWrongAnswersToStudy.size() &&\n");

            //to see if we are at the end of reviewing questions
            if(mQuestionIndex == mWrongAnswersToStudy.size() -1){
                mNextButton.setText(R.string.finish);
                Log.d(TAG, "mQuestionIndex == mWrongAnswersToStudy.size() -1");
            }

            setUpWrongAnswersAfterTestComplete();
            loadCorrectAnswerRadioButton();
        } else{
            //this is if we are at the end of reviewing questions


            destroyActivity();


        }
    }



    public void regularNextQuestion(){
        mQuestionIndex += 1;
        unSelectRadioButtons();
        updateQuestion();

        loadRadioButtonSelection();
        if(mQuestionIndex == mQuestionIndexArray.length -1 ){
            mNextButton.setText(getResources().getString(R.string.finish_studying));
        }else {
            mNextButton.setText(getResources().getString(R.string.next_button));
        }
    }

    public void loadCorrectAnswerRadioButton(){
        Log.d(TAG, "loadCorrectAnswerRadioButton");

        Log.d(TAG, "showCorrectAnswersForStudy");

        Log.d(TAG, "loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex))\n" +
                "                .isTrueOrFalseAllreadySet()" + loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex))
                .isTrueOrFalseAllreadySet());


        //this finds out which index is used from poor api
        if(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex))
                .isTrueOrFalseAllreadySet()){
            Log.d(TAG, "true or false question where true or false was pree writen and the index is 1 and 2 ");
            //this has a diferent index
            //if the question is a true or false question
            //this is if the questions uses 1 or 2 toindex the correct answer and it is a true or false question
            //this is a quest

            if(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getAnswer().toLowerCase()
                    .equals("1")){
                Log.d(TAG, "corrct answer is a ");
                mOptionOne.toggle();
            }else{
                mOptionTwo.toggle();
            }




        }else if(mOptionOne.getText().toString().equals(getResources().getString(R.string.answer_true))){
            //if the question is a true or false question
            //this is if the questions uses 0 or 1 toindex the correct answer and it is a true or false question
//            String rightAnswerIndex = "0";
//            displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);
            Log.d(TAG, "true or false question where true or false was post writen and the index is 0 and 1 ");
            if(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getAnswer().toLowerCase()
                    .equals("0")){
                Log.d(TAG, "corrct answer is a ");
                mOptionOne.toggle();
            }else{
                mOptionTwo.toggle();
            }




        }else {
            Log.d(TAG, "3 ossible answers and up. index starts at 1 finishes at 5  ");
            //notTrueOrFalseQuestion(lastChracterString);
            if(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getAnswer().toLowerCase()
                    .equals("1")){
                Log.d(TAG, "corrct answer is a ");
                mOptionOne.toggle();
            }else if(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getAnswer().toLowerCase()
                    .equals("2")){
                Log.d(TAG, "corrct answer is b ");
                mOptionTwo.toggle();
            }else if(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getAnswer().toLowerCase()
                    .equals("3")){
                Log.d(TAG, "corrct answer is c ");
                mOptionThree.toggle();
            }else if(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getAnswer().toLowerCase()
                    .equals("4")){
                Log.d(TAG, "corrct answer is d ");
                mOptionFour.toggle();
            }else if(loadHygieneContainerQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getAnswer().toLowerCase()
                    .equals("5")){
                Log.d(TAG, "corrct answer is d ");
                mOptionFive.toggle();
            }
        }





    }

    public void haveAllQuestionsBeenAnswered(){
        Log.d(TAG, "haveAllQuestionsBeenAnswered" );

        for (int i = 0; i < mQuestionIndexArray.length; i++) {


            if (loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[i])
                    .getSelectedAnswer().equals("")) {
                //questions not answered on test is not complete

                Log.d(TAG, "loop i is  :" + i + "    " +loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[i])
                        .getSelectedAnswer().equals(""));
                Snackbar.make(view, getResources().getString(R.string.anser_all_questions),
                        Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;

            } else {
                Log.d(TAG, "in the else loop i is  :" + i + "    " +loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[i])
                        .getSelectedAnswer().equals(""));




                if (!loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[i])
                        .isAnsweredCorrectly()
                        && i != 0) {
                    //to save wrong answers for studying
                    Log.d(TAG, "!driverQuestions.getQuestions().get(mQuestionIndexArray[i]:" );
                    Log.d(TAG, "i " + i );
                    mWrongAnswersToStudy.add(mQuestionIndexArray[i]);
                }


                if (i == 49) {
                    // all questions  answered
                    Log.d(TAG, "all questions  answered " );
                    Log.d(TAG, "i == 49" );
                    //all questions completed
                    //50 questions for simulation test
                    finishedTestDialog(mWrongAnswersToStudy);
                    mQuestionIndex = -1;
                    //this is so radio buttons display the right answer
                    testCompletedStudymode = true;
                    fab.setVisibility(view.INVISIBLE);
                }

            }

        }
    }

    private void finishedTestDialog(ArrayList<Integer> mWrongAnswers){
        //must answer 14 right to pass
        Log.d(TAG, "finishedTestDialog " );


        Log.d(TAG, "mWrongAnswers.size() : " + mWrongAnswers.size());

        if(mWrongAnswers.size() > 15){

            final MaterialDialog mMaterialDialog = new MaterialDialog(HealthTestSimulation.this)
                    .setTitle(getResources().getString(R.string.health_test_complete_dialog))

                    .setMessage(getResources().getString(R.string.health_test_complete_dialog_message_part_one_failed));

            mMaterialDialog
                    .setPositiveButton(getResources().getString(R.string.failed_test_dialog_conferm),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d(TAG, "failed test confirm button");
                                    mMaterialDialog.dismiss();
                                    destroyActivity();

                                }
                            })
                    .setNegativeButton(
                            getResources().getString(R.string.failed_test_dialog_study_wrong_answer),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Log.d(TAG, "failed test negative button");
                                    mMaterialDialog.dismiss();
                                    mQuestionIndex = 0;
                                    setUpWrongAnswersAfterTestComplete();

                                }
                            });
            mMaterialDialog.show();
        }else {




            final MaterialDialog mMaterialDialog = new MaterialDialog(HealthTestSimulation.this)
                    .setTitle(getResources().getString(R.string.you_passed))
                    .setMessage(getResources().getString(R.string.health_test_complete_dialog_message_part_one_passed)
                             );

            mMaterialDialog
                    .setPositiveButton(
                            getResources().getString(R.string.passed_test_dialog_conferm), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                mMaterialDialog.dismiss();
                                    Log.d(TAG, "passed test confirm button");
                                finish();
                                }
                            })
                    .setNegativeButton(
                            getResources().getString(R.string.passed_test_dialog_dismiss), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                mMaterialDialog.dismiss();
                                Log.d(TAG, "passed test negative button");
                                mQuestionIndex = 0;
                                setUpWrongAnswersAfterTestComplete();

                                }
                            });
            mMaterialDialog.show();
        }


    }



    @SuppressWarnings("unused")
    @OnClick(R.id.fab)
    public void selectQuestionDialog(View view) {
        Log.d(TAG, "selectQuestionDialog");

        showSelectQuestionDialog();

    }

    private void showSelectQuestionDialog() {
        Log.d(TAG, "showAlertDialog");
        // Prepare grid view



        GridView gridView = new GridView(this);

        int questionSize = mQuestionIndexArray.length;
        List<Integer> mList = new ArrayList<Integer>();
        for (int i = 1; i < questionSize + 1 ; i++) {
            mList.add(i);
        }

        gridView.setAdapter(new ArrayAdapter<Integer>(this, R.layout.custom_list_item, mList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);


                boolean hasBeenAnswered = hasBeenAnswered(mQuestionIndexArray[position]);
                Log.d(TAG, "hasBeenAnswered : " + hasBeenAnswered);
                int color = 0x00FFFFFF; // Transparent
                if (hasBeenAnswered) {

                    if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[position]).isAnsweredCorrectly()){
                        //answer is correct
                        view.setBackgroundColor(getResources().getColor(R.color.colorForQuestionGrid));
                    }else{
                        //answer is incorrect
                        view.setBackgroundColor(getResources().getColor(R.color.colorForQuestionGrid));
                    }


                }else{

                    view.setBackgroundColor(color);
                }



                return view;
            }
        });
        gridView.setNumColumns(4);


        // Set grid view to alertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(gridView);
        builder.setTitle(getResources().getString(R.string.select_question));
        builder.setPositiveButton(getResources().getString(R.string.dialog_dismiss),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, getResources().getString(R.string.dialog_dismiss));
                        dialog.dismiss();
                    }
                });



        final AlertDialog ad = builder.show();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // do something here
                Log.d(TAG, "onclick");

                mOptionThree.setVisibility(View.VISIBLE);
                mOptionFour.setVisibility(View.VISIBLE);
                mOptionFive.setVisibility(View.VISIBLE);

                if (position == 0) {
                    makePrevousButtonUnclickable();
                } else {
                    makePrevousButtonClickable();
                }

                mNextButton.setText(getResources().getString(R.string.next_button));
                Log.d(TAG, "int pos : " + position);

                if (position == mQuestionIndexArray.length - 1) {
                    mNextButton.setText(getResources().getString(R.string.finish_studying));
                }

                goToSelectedQuestion(position);
                ad.dismiss();
            }
        });



    }


    private boolean hasBeenAnswered(int position){
        Log.d(TAG, "hasBeenAnswered");



        Log.d(TAG, "hasBeenAnswered getSelectedAnswer : " +
                loadHygieneContainerQuestions.getQuestions().get(position).getSelectedAnswer());
        Log.d(TAG, "hasBeenAnswered getAnswer : " +
                loadHygieneContainerQuestions.getQuestions().get(position).getAnswer());
        loadHygieneContainerQuestions.getQuestions().get(position).getSelectedAnswer();




        if(!loadHygieneContainerQuestions.getQuestions().get(position).getSelectedAnswer().equals("")){

            Log.d(TAG, "hasBeenAnswered has  been answered");
            return true;


        }
        Log.d(TAG, "hasBeenAnswered has  been answered");
        Log.d(TAG, "hasBeenAnswered question has not been answered");
        return false;




    }

    private void goToSelectedQuestion(int questionindex){


        //this is only for not study
        radioGroup.setEnabled(true);
        saveAnswer();
        mQuestionIndex = questionindex;
        unSelectRadioButtons();
        updateQuestion();

        loadRadioButtonSelection();


    }

    @OnClick(R.id.previous_question)
    public void previousQuestion(View view) {
        Log.d(TAG, "previousQuestion");

        if(mQuestionIndex == 1){
            makePrevousButtonUnclickable();
        }

        mOptionThree.setVisibility(View.VISIBLE);
        mOptionFour.setVisibility(View.VISIBLE);
        mOptionFive.setVisibility(View.VISIBLE);

        radioGroup.setEnabled(true);
        saveAnswer();
        mNextButton.setText(getResources().getString(R.string.next_button));

        if(mWrongAnswersToStudy.size() > 0){
            //this is for when we have completed the test once and
            //want to go over the wrong answer
            mQuestionIndex -= 1;
            studyWrongAnswersNextQuestion();

        }else if(mQuestionIndex != 0){
            mQuestionIndex -= 1;
            unSelectRadioButtons();
            updateQuestion();
            loadRadioButtonSelection();
        }


    }

    private void unSelectRadioButtons(){
        radioGroup.clearCheck();
    }

    private void destroyActivity(){

        finish();
    }

    private void saveAnswer() {
        Log.d(TAG, "saveAnswer");

        Log.d(TAG, "radioGroup.getCheckedRadioButtonId() :" +
                radioGroup.getCheckedRadioButtonId() );


        //if button has been pushed
        if(radioGroup.getCheckedRadioButtonId() != -1
                && radioGroup.getCheckedRadioButtonId() != 0) {
            Log.d(TAG, "toggle has been switched");
            int checkedRadioButton = radioGroup.getCheckedRadioButtonId();


            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setSelectedAnswerResourceId(
                    checkedRadioButton);
            Log.d(TAG, "setSelectedAnswer" + getResources().getResourceEntryName(checkedRadioButton));
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setSelectedAnswer(
                    getResources().getResourceEntryName(checkedRadioButton));


            Log.d(TAG, "getSelectedAnswer : " +
                    loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer());

            isAnswerCorrect();

        }

    }



    private void isAnswerCorrect(){
        Log.d(TAG, "isAnswerCorrect");



        Log.d(TAG, "getSelectedAnswer() : " + loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer());


        String lastChracterString = loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer().
                substring(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer().length() - 1);

        Log.d(TAG, "question answer : " +
                loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer()
                        .toLowerCase());

        if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionA()
                .equals(getResources().getString(R.string.answer_true))){

            ifTrueOrFalseQuestion(lastChracterString);
        }else{
            if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionC()
                    .equals(NULL_STRING)){
                twoAnswerQuestions(lastChracterString);
            }else{
                notTrueOrFalseQuestion(lastChracterString);
            }

        }

        Log.d(TAG, " isAnswerCorrect ???" +
                loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).isAnsweredCorrectly());

    }

    public void twoAnswerQuestions(String lastChracterString){
        Log.d(TAG, "twoAnswerQuestions : ");
        if(lastChracterString.equals("a") && loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer()
                .toLowerCase().equals("1")){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(true);
            Log.d(TAG, "poo answer right! : " );
        }else if(lastChracterString.equals("b") && loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer()
                .toLowerCase().equals("2")){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(true);
            Log.d(TAG, "poo answer right! : " );
        }else{
            Log.d(TAG, "poo answer wrong! : " );
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(false);
        }
    }

    public void notTrueOrFalseQuestion(String lastChracterString){
        Log.d(TAG, "notTrueOrFalseQuestion : ");

        Log.d(TAG, "notTrueOrFalseQuestion lastChracterString : " + lastChracterString);

        Log.d(TAG, "notTrueOrFalseQuestion answer : " + loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer()
                .toLowerCase());

        //changeCorrectRecordedAnswerToABCD();

        if(lastChracterString
                .equals(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer()
                        .toLowerCase())){
            Log.d(TAG, "inotTrueOrFalseQuestion sAnswerCorrect answer is correct");

            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(true);

        } else {
            Log.d(TAG, "notTrueOrFalseQuestion isAnswerCorrect answer is incorrect");
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(false);
        }

        Log.d(TAG, " isAnswerCorrect ???");
    }



    public void ifTrueOrFalseQuestion(String lastChracterString){
        Log.d(TAG, "ifTrueOrFalseQuestion");

        if(lastChracterString.equals("a") && loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer()
                .toLowerCase().equals("0")){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(true);
            Log.d(TAG, "poo answer right! : " );
        }else if(lastChracterString.equals("b") && loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer()
                .toLowerCase().equals("1")){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(true);
            Log.d(TAG, "poo answer right! : " );
        }else{
            Log.d(TAG, "poo answer wrong! : " );
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int BACK_BUTTON = 16908332;
        Log.d(TAG, "item : " + item);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d(TAG, "id : " + id);
        //noinspection SimplifiableIfStatement

        if(id == BACK_BUTTON){
            Log.d(TAG, "BackButton");
            destroyActivity();
        }

        return super.onOptionsItemSelected(item);
    }

}
