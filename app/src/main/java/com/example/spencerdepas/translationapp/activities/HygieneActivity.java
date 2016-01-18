package com.example.spencerdepas.translationapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.example.spencerdepas.translationapp.ButtonSelector;
import com.example.spencerdepas.translationapp.R;
import com.example.spencerdepas.translationapp.model.CreateJSONObject;
import com.example.spencerdepas.translationapp.model.GestureListener;
import com.example.spencerdepas.translationapp.pojo.HygieneContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HygieneActivity extends AppCompatActivity implements ButtonSelector{


    private final String NULL_STRING =  "<null>";
    private final String LANGUAGE_CHINESE =  "Chinese";
    private final String LANGUAGE_ENGLISH =  "English";
    private ArrayList<Integer> mWrongAnswersToStudy = new ArrayList<Integer>();
    private String TAG = "MyHygieneActivity";
    private final String PREFS_LANGUAGE = "langagePrference";
    private String language = null;
    private  String picLocation;
    private Context mcontext;
    private int mQuestionIndex = 0;
    private View view;

    private GestureDetectorCompat gDetect;

    @Bind(R.id.driver_test_question)  TextView mQuestion;
    @Bind(R.id.option_a) RadioButton mOptionOne;
    @Bind(R.id.option_b) RadioButton mOptionTwo;
    @Bind(R.id.option_c) RadioButton mOptionThree;
    @Bind(R.id.option_d) RadioButton mOptionFour;
    @Bind(R.id.option_e) RadioButton mOptionFive;
    @Bind(R.id.image_view) ImageView mImage;
    @Bind(R.id.myRadioGroup) RadioGroup radioGroup;

    @Bind(R.id.previous_question) Button mPreveousButton;
    @Bind(R.id.next_question) Button mNextButton;
    @Bind(R.id.displays_wrong_answer) TextView mDisplayCorrectAnswer;

    private HygieneContainer loadHygieneContainerQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hygiene);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "onCreate");
        mcontext = this.getApplicationContext();
        view = findViewById(R.id.hygiene_root_view);

        language = Locale.getDefault().getDisplayLanguage(); //if it's a string you stored.

        Log.d(TAG, "language : " + language);

        CreateJSONObject createJSONObject = new CreateJSONObject( this);
        loadHygieneContainerQuestions = createJSONObject.loadHygieneContainerQuestions(language);

        Log.d(TAG, "loadHygieneContainerQuestions size : " + loadHygieneContainerQuestions.getQuestions().size());



        updateQuestion();

        makePrevousButtonUnclickable();

        setUpGestures();

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
        mGestureListener.delegate = HygieneActivity.this;
        gDetect = new GestureDetectorCompat(this, mGestureListener);


    }

    public void formatAnswers(){
        Log.d(TAG, "formatAnswers");
        //this adds True and False or removes two null questions

        Log.d(TAG, "formatAnswers");
        if(  loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionA()
                .equals(NULL_STRING)){
            //adds truth or false
            Log.d(TAG, "formatAnswers == null");
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex)
                    .setOptionA(getResources().getString(R.string.answer_true));
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex)
                    .setOptionB(getResources().getString(R.string.answer_false));
            mOptionThree.setVisibility(View.INVISIBLE);
            mOptionFour.setVisibility(View.INVISIBLE);
        }else{


            if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionA()
                    .equals(getResources().getString(R.string.answer_true))){
                //if it is allready set to true or false we need to add a check
                //becuase the index in the api is diferent
                Log.d(TAG, "answer already set to true false");
                loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex)
                        .setTrueOrFalseAllreadySet(true);
            }




            if(  loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionC()
                    .equals(NULL_STRING)){

                mOptionThree.setVisibility(View.INVISIBLE);


                if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionD()
                        .equals(NULL_STRING)){
                    mOptionFour.setVisibility(View.INVISIBLE);
                }




            }else  if(  loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionD()
                    .equals(NULL_STRING)){
                mOptionFour.setVisibility(View.INVISIBLE);

                if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionE()
                        .equals(NULL_STRING)){
                    mOptionFive.setVisibility(View.INVISIBLE);
                }

            }else if(  loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionE()
                    .equals(NULL_STRING)){
                mOptionFive.setVisibility(View.INVISIBLE);

            }

        }

    }

    public void updateQuestion(){
        Log.d(TAG, "updateQuestion");


        formatAnswers();


        mQuestion.setText(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getQuestion());
        mOptionOne.setText(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionA());
        mOptionTwo.setText(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionB());
        mOptionThree.setText(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionC());
        mOptionFour.setText(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionD());

        if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionE().equals(NULL_STRING)){
            mOptionFive.setVisibility(View.INVISIBLE);
        }else {
            mOptionFive.setVisibility(View.VISIBLE);
            mOptionFive.setText(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionE());
        }


        if(!loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer().equals("")){
            Log.d(TAG, "question  answered : ");
            if (loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).isAnsweredCorrectly()) {
                Log.d(TAG, "answer right");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText( getResources().getString(R.string.correct));

                radioGroup.setEnabled(false);

            }else {
                Log.d(TAG, "updateQuestion answer wrong");
                //condition for id you press prevous button and the answer has not been answered
                if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).isAnsweredCorrectly()){
                    mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                    mDisplayCorrectAnswer.setText(getResources().getString(R.string.correct_answer_above) +
                            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer());
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
        if(!loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer()
                .equals("")){

            Log.d(TAG, "radio button has been clicked and will be saved");

            radioGroup.check(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex)
                    .getSelectedAnswerResourceId());

        }
    }

    @Override
    public void gestureNextButton(){
        Log.d(TAG, "gestureNextButton :" );

        Log.d(TAG, "mindex size :" + loadHygieneContainerQuestions.getQuestions().size());
        Log.d(TAG, "mQuestionIndex :" + mQuestionIndex);

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

    @OnClick(R.id.option_a)
    public void radioOptionOne(View view) {
        //indexus are diferent because apis are shit
        Log.d(TAG, "radioOptionOne");




        if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).isTrueOrFalseAllreadySet()){
            //this has a diferent index

            String rightAnswerIndex = "1";
            displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);

        }else if(mOptionOne.getText().toString().equals(getResources().getString(R.string.answer_true))){
            //if the question is a true or false question
            String rightAnswerIndex = "0";
            displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);

        }else {
            //notTrueOrFalseQuestion(lastChracterString);
            String rightAnswerIndex = "1";
            displayIfAnswerIsRightOrWrongForRegularQuestions(rightAnswerIndex);
        }

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
                    loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer());
        }
    }



    public void displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(String rightAnswerIndex){
        Log.d(TAG, "displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions ");
        if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer()
                .toLowerCase().equals(rightAnswerIndex)){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnsweredCorrectly(true);
            Log.d(TAG, "answer right");
            mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
            mDisplayCorrectAnswer.setText(getResources().getString(R.string.correct));
            nextQuestion(view);
        }else{
            Log.d(TAG, "radioOptionTwo answer wrong");

            mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
            mDisplayCorrectAnswer.setText( getResources().getString(R.string.correct_answer_above) +
                    loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer());
        }
    }

    @OnClick(R.id.option_b)
    public void radioOptionTwo(View view) {
        Log.d(TAG, "radioOptionTwo");

        Log.d(TAG, "radioOptionTwo mOptionTwo.getText().toString().equals(getResources().getString(R.string.answer_false) : " +
                mOptionTwo.getText().toString().equals(getResources().getString(R.string.answer_false)));

        Log.d(TAG, "radioOptionTwo mDisplayCorrectAnswer.getText().length() != 0 " +
                (mDisplayCorrectAnswer.getText().length() != 0));


        if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).isTrueOrFalseAllreadySet()){
            //this has a diferent index

            String rightAnswerIndex = "2";
            displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);

        }else if(mOptionTwo.getText().toString().equals(getResources().getString(R.string.answer_false))){
            //if the question is a true or false question
            Log.d(TAG, "radioOptionTwo is tru or false question ");
            String rightAnswerIndex = "1";

            displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);


        }else {
            String rightAnswerIndex = "2";
            displayIfAnswerIsRightOrWrongForRegularQuestions(rightAnswerIndex);
        }

    }

    @OnClick(R.id.option_c)
    public void radioOptionThree(View view) {
        Log.d(TAG, "radioOptionThree");



        String rightAnswerIndex = "3";
        if(mOptionOne.getText().toString().equals(getResources().getString(R.string.answer_true))){
            //if the question is a true or false question

            displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);

        }else{
            //notTrueOrFalseQuestion(lastChracterString);
            displayIfAnswerIsRightOrWrongForRegularQuestions(rightAnswerIndex);
        }
    }

    @OnClick(R.id.option_d)
    public void radioOptionFour(View view) {
        Log.d(TAG, "radioOptionFour");

        String rightAnswerIndex = "4";
        if(mOptionOne.getText().toString().equals(getResources().getString(R.string.answer_true))){
            //if the question is a true or false question

            displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);

        }else{
            //notTrueOrFalseQuestion(lastChracterString);
            displayIfAnswerIsRightOrWrongForRegularQuestions(rightAnswerIndex);
        }
    }

    @OnClick(R.id.option_e)
    public void radioOptionFive(View view) {
        Log.d(TAG, "radioOptionFour");

        String rightAnswerIndex = "5";
        if(mOptionOne.getText().toString().equals(getResources().getString(R.string.answer_true))){
            //if the question is a true or false question

            displayIfAnswerIsRightOrWrongForTrueOrFalseQuestions(rightAnswerIndex);

        }else{
            //notTrueOrFalseQuestion(lastChracterString);
            displayIfAnswerIsRightOrWrongForRegularQuestions(rightAnswerIndex);
        }
    }

    @OnClick(R.id.next_question)
    public void nextQuestion(View view) {
        Log.d(TAG, "nextQuestion");

        mOptionThree.setVisibility(View.VISIBLE);
        mOptionFour.setVisibility(View.VISIBLE);
        mOptionFive.setVisibility(View.VISIBLE);

        makePrevousButtonClickable();

        radioGroup.setEnabled(true);
        saveAnswer();


        Log.d(TAG, "myNailQuestionContainer.getQuestions().size()" + loadHygieneContainerQuestions.getQuestions().size());
        Log.d(TAG, "mQuestionIndex " + mQuestionIndex);
        if(mQuestionIndex < loadHygieneContainerQuestions.getQuestions().size() -1){
            mQuestionIndex += 1;
            unSelectRadioButtons();
            updateQuestion();

            loadRadioButtonSelection();
            if(mQuestionIndex == loadHygieneContainerQuestions.getQuestions().size() -1 ){
                mNextButton.setText(getResources().getString(R.string.finish_studying));
            }else {
                mNextButton.setText(getResources().getString(R.string.next_button));
            }
        }else{
            destroyActivity();
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

        int questionSize = loadHygieneContainerQuestions.getQuestions().size();
        List<Integer> mList = new ArrayList<Integer>();
        for (int i = 1; i < questionSize + 1 ; i++) {
            mList.add(i);
        }

        gridView.setAdapter(new ArrayAdapter<Integer>(this, R.layout.custom_list_item, mList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);


                boolean hasBeenAnswered = hasBeenAnswered(position);
                Log.d(TAG, "hasBeenAnswered : " + hasBeenAnswered);
                int color = 0x00FFFFFF; // Transparent
                if (hasBeenAnswered) {

                    if(loadHygieneContainerQuestions.getQuestions().get(position).isAnsweredCorrectly()){
                        //answer is correct
                        view.setBackgroundColor(getResources().getColor(R.color.colorForQuestionGrid));
                    }else{
                        //answer is incorrect
                        view.setBackgroundColor(getResources().getColor(R.color.red));
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
                if (position == loadHygieneContainerQuestions.getQuestions().size() - 1) {
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


        if(loadHygieneContainerQuestions.getQuestions().get(position).getSelectedAnswer().equals("sound")){
            Log.d(TAG, " i r say sound : "  );
            return false;
        }


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
        if(mQuestionIndex != 0){
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


            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setSelectedAnswerResourceId(
                    checkedRadioButton);
            Log.d(TAG, "setSelectedAnswer" + getResources().getResourceEntryName(checkedRadioButton));
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setSelectedAnswer(
                    getResources().getResourceEntryName(checkedRadioButton));


            Log.d(TAG, "getSelectedAnswer : " +
                    loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer());

            isAnswerCorrect();

        }

    }



    private void isAnswerCorrect(){
        Log.d(TAG, "isAnswerCorrect");



        Log.d(TAG, "getSelectedAnswer() : " + loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer());


        String lastChracterString = loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer().
                substring(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer().length() - 1);

        Log.d(TAG, "question answer : " +
                loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer()
                        .toLowerCase());

        if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionA()
                .equals(getResources().getString(R.string.answer_true))){

            ifTrueOrFalseQuestion(lastChracterString);
        }else{
            if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getOptionC()
                    .equals(NULL_STRING)){
                twoAnswerQuestions(lastChracterString);
            }else{
                notTrueOrFalseQuestion(lastChracterString);
            }

        }

        Log.d(TAG, " isAnswerCorrect ???" +
                loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).isAnsweredCorrectly());

    }

    public void twoAnswerQuestions(String lastChracterString){
        Log.d(TAG, "twoAnswerQuestions : ");
        if(lastChracterString.equals("a") && loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer()
                .toLowerCase().equals("1")){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnsweredCorrectly(true);
            Log.d(TAG, "poo answer right! : " );
        }else if(lastChracterString.equals("b") && loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer()
                .toLowerCase().equals("2")){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnsweredCorrectly(true);
            Log.d(TAG, "poo answer right! : " );
        }else{
            Log.d(TAG, "poo answer wrong! : " );
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnsweredCorrectly(false);
        }
    }

    public void notTrueOrFalseQuestion(String lastChracterString){
        Log.d(TAG, "notTrueOrFalseQuestion : ");

        Log.d(TAG, "notTrueOrFalseQuestion lastChracterString : " + lastChracterString);

        Log.d(TAG, "notTrueOrFalseQuestion answer : " + loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer()
                .toLowerCase());

        //changeCorrectRecordedAnswerToABCD();

        if(lastChracterString
                .equals(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer()
                        .toLowerCase())){
            Log.d(TAG, "inotTrueOrFalseQuestion sAnswerCorrect answer is correct");

            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnsweredCorrectly(true);

        } else {
            Log.d(TAG, "notTrueOrFalseQuestion isAnswerCorrect answer is incorrect");
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnsweredCorrectly(false);
        }

        Log.d(TAG, " isAnswerCorrect ???");
    }

    public void changeCorrectRecordedAnswerToABCD(){

        if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer().equals("1")){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnswer("a");
        }else if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer().equals("2")){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnswer("b");
        }else if(loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer().equals("3")){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnswer("c");
        } else {
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnswer("d");
        }

    }

    public void ifTrueOrFalseQuestion(String lastChracterString){
        Log.d(TAG, "ifTrueOrFalseQuestion");

        if(lastChracterString.equals("a") && loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer()
                .toLowerCase().equals("0")){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnsweredCorrectly(true);
            Log.d(TAG, "poo answer right! : " );
        }else if(lastChracterString.equals("b") && loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).getAnswer()
                .toLowerCase().equals("1")){
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnsweredCorrectly(true);
            Log.d(TAG, "poo answer right! : " );
        }else{
            Log.d(TAG, "poo answer wrong! : " );
            loadHygieneContainerQuestions.getQuestions().get(mQuestionIndex).setAnsweredCorrectly(false);
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
