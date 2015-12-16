package com.example.spencerdepas.translationapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DMVStudyActivity extends AppCompatActivity {


    private String TAG = "MyDMVStudyDriverTestActivity";
    private String language = null;
    @Bind(R.id.driver_test_question) TextView mQuestion;
    @Bind(R.id.option_a) RadioButton mOptionOne;
    @Bind(R.id.option_b) RadioButton mOptionTwo;
    @Bind(R.id.option_c) RadioButton mOptionThree;
    @Bind(R.id.option_d) RadioButton mOptionFour;
    @Bind(R.id.image_view) ImageView mImage;
    @Bind(R.id.myRadioGroup) RadioGroup radioGroup;

    @Bind(R.id.next_question) Button mNextButton;
    @Bind(R.id.displays_wrong_answer) TextView mDisplayCorrectAnswer;

    private int mQuestionIndex = 0;
    private DriversLicenseQuestions driverQuestions;
    private final String PREFS_LANGUAGE = "langagePrference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmv_study);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        language = intent.getStringExtra(PREFS_LANGUAGE); //if it's a string you stored.

        Log.d(TAG, "language : " + language);

        CreateJSONObject createJSONObject = new CreateJSONObject(language, "This should specifie test type", this);
        driverQuestions = createJSONObject.loadDMVQuestions();



        updateQuestion();


    }

    public void loadRadioButtonSelection(){
        Log.d(TAG, "loadRadioButtonSelection");


//        Log.d(TAG, "getSelectedAnswerResourceId() " +
//                driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswerResourceId());
//
//        Log.d(TAG, "getSelectedAnswer()" +
//                driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer());

        if(!driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer()
                .equals("")){

            Log.d(TAG, "radio button has been clicked and will be saved");

            radioGroup.check(driverQuestions.getQuestions().get(mQuestionIndex)
                    .getSelectedAnswerResourceId());

        }
    }
    private String picLocation;
    public void updateQuestion(){
        Log.d(TAG, "updateQuestion");


        if(language.equals("chinese")){
            picLocation = "file:///android_asset/"
                    + driverQuestions.getQuestions().get(mQuestionIndex).getPicUrl().toLowerCase() + "_en"
                    +".png";
        }else{
            picLocation = "file:///android_asset/"
                    + driverQuestions.getQuestions().get(mQuestionIndex).getPicUrl().toLowerCase()
                    +".png";
        }





        Glide.with(this)
                .load(Uri.parse(picLocation))
                .override(300, 300)
                .into(mImage);

        mQuestion.setText(driverQuestions.getQuestions().get(mQuestionIndex).getQuestion());
        mOptionOne.setText(driverQuestions.getQuestions().get(mQuestionIndex).getOptionA());
        mOptionTwo.setText(driverQuestions.getQuestions().get(mQuestionIndex).getOptionB());
        mOptionThree.setText(driverQuestions.getQuestions().get(mQuestionIndex).getOptionC());
        mOptionFour.setText(driverQuestions.getQuestions().get(mQuestionIndex).getOptionD());

        if(!driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer().equals("")){
            Log.d(TAG, "question  answered : ");
            if (driverQuestions.getQuestions().get(mQuestionIndex).isAnsweredCorrectly()) {
                Log.d(TAG, "answer right");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct ");

                radioGroup.setEnabled(false);

            }else {
                Log.d(TAG, "answer wrong");

                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct answer selected above is  : " +
                        driverQuestions.getQuestions().get(mQuestionIndex).getAnswer());
            }
        }else{

            mDisplayCorrectAnswer.setVisibility(View.INVISIBLE);
        }


    }



    @OnClick(R.id.option_a)
    public void radioOptionOne(View view) {
        Log.d(TAG, "radioOptionOne");


        if(mDisplayCorrectAnswer.getText().length() != 0) {
            //this is when they first got the answer wrong and we want to
            //automate it switching to the next questions

            //this is for the answer being correct after seeing the displayed answer
            if (driverQuestions.getQuestions().get(mQuestionIndex).getAnswer().equals("A")) {
                Log.d(TAG, "answer right");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct ");
                nextQuestion(view);
            }else {
                Log.d(TAG, "answer wrong");

                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct answer selected above is  : " +
                        driverQuestions.getQuestions().get(mQuestionIndex).getAnswer());
            }

            Log.d(TAG, "the answer is right this time ");
        }else{
            //to display correct or show the right answer
            if (driverQuestions.getQuestions().get(mQuestionIndex).getAnswer().equals("A")) {
                Log.d(TAG, "answer right");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct ");
            } else {
                Log.d(TAG, "answer wrong");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct answer selected above is  : " +
                        driverQuestions.getQuestions().get(mQuestionIndex).getAnswer());
            }
        }

    }

    @OnClick(R.id.option_b)
    public void radioOptionTwo(View view) {
        Log.d(TAG, "radioOptionTwo");



        if(mDisplayCorrectAnswer.getText().length() != 0) {
            //this is when they first got the answer wrong and we want to
            //automate it switching to the next questions

            //this is for the answer being correct after seeing the displayed answer
            if (driverQuestions.getQuestions().get(mQuestionIndex).getAnswer().equals("B")) {
                Log.d(TAG, "answer right");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct ");
                nextQuestion(view);
            }else {
                Log.d(TAG, "answer wrong");

                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct answer selected above is  : " +
                        driverQuestions.getQuestions().get(mQuestionIndex).getAnswer());
            }

            Log.d(TAG, "the answer is right this time ");
        }else {
            if (driverQuestions.getQuestions().get(mQuestionIndex).getAnswer().equals("B")) {
                Log.d(TAG, "answer right");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct ");

            } else {
                Log.d(TAG, "answer wrong");

                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct answer selected above is  : " +
                        driverQuestions.getQuestions().get(mQuestionIndex).getAnswer());
            }
        }
    }

    @OnClick(R.id.option_c)
    public void radioOptionThree(View view) {
        Log.d(TAG, "radioOptionThree");


        if(mDisplayCorrectAnswer.getText().length() != 0) {
            //this is when they first got the answer wrong and we want to
            //automate it switching to the next questions

            //this is for the answer being correct after seeing the displayed answer
            if (driverQuestions.getQuestions().get(mQuestionIndex).getAnswer().equals("C")) {
                Log.d(TAG, "answer right");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct ");
                nextQuestion(view);
            }else {
                Log.d(TAG, "answer wrong");

                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct answer selected above is  : " +
                        driverQuestions.getQuestions().get(mQuestionIndex).getAnswer());
            }

            Log.d(TAG, "the answer is right this time ");
        }else {
            if (driverQuestions.getQuestions().get(mQuestionIndex).getAnswer().equals("C")) {
                Log.d(TAG, "answer right");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct ");
            } else {
                Log.d(TAG, "answer wrong");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct answer is  : " +
                        driverQuestions.getQuestions().get(mQuestionIndex).getAnswer());
            }
        }
    }

    @OnClick(R.id.option_d)
    public void radioOptionFour(View view) {
        Log.d(TAG, "radioOptionFour");


        if(mDisplayCorrectAnswer.getText().length() != 0) {
            //this is when they first got the answer wrong and we want to
            //automate it switching to the next questions


            //this is for the answer being correct after seeing the displayed answer
            if (driverQuestions.getQuestions().get(mQuestionIndex).getAnswer().equals("D")) {
                Log.d(TAG, "answer right");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct ");
                nextQuestion(view);
            }else {
                Log.d(TAG, "answer wrong");

                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct answer selected above is  : " +
                        driverQuestions.getQuestions().get(mQuestionIndex).getAnswer());
            }

            Log.d(TAG, "the answer is right this time ");
        }else {
            if (driverQuestions.getQuestions().get(mQuestionIndex).getAnswer().equals("D")) {
                Log.d(TAG, "answer right");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct ");
            } else {
                Log.d(TAG, "answer wrong");
                mDisplayCorrectAnswer.setVisibility(View.VISIBLE);
                mDisplayCorrectAnswer.setText("Correct answer selected above is  : " +
                        driverQuestions.getQuestions().get(mQuestionIndex).getAnswer());
            }
        }
    }


    @OnClick(R.id.select_question_dialog)
    public void selectQuestionDialog(View view) {
        Log.d(TAG, "selectQuestionDialog");

        showSelectQuestionDialog();

    }

    private void showSelectQuestionDialog() {
        Log.d(TAG, "showAlertDialog");
        // Prepare grid view
        GridView gridView = new GridView(this);

        int questionSize = driverQuestions.getQuestions().size();
        List<Integer> mList = new ArrayList<Integer>();
        for (int i = 1; i < questionSize + 1 ; i++) {
            mList.add(i);
        }

        gridView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList));
        gridView.setNumColumns(4);


        // Set grid view to alertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(gridView);
        builder.setTitle("Goto");
        builder.setPositiveButton("DISMISS",
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

                mNextButton.setText("Next Question");
                Log.d(TAG, "int pos : " + position);
                if (position == 193) {
                    mNextButton.setText("Finish");
                }

                goToSelectedQuestion(position);
                ad.dismiss();
            }
        });



    }

    private void goToSelectedQuestion(int questionindex){

        //this is only for not study
        radioGroup.setEnabled(true);
        saveAnswer();
        mQuestionIndex = questionindex;
        if(mQuestionIndex == 19){
            mNextButton.setText("Submit");
        }
        updateQuestion();
        unSelectRadioButtons();
        loadRadioButtonSelection();


    }

    @OnClick(R.id.previous_question)
    public void previousQuestion(View view) {
        Log.d(TAG, "previousQuestion");


        radioGroup.setEnabled(true);

        saveAnswer();
        mNextButton.setText("Next Question");
        if(mQuestionIndex != 0){
            mQuestionIndex -= 1;
            updateQuestion();
            unSelectRadioButtons();
            loadRadioButtonSelection();
        }


    }

    @OnClick(R.id.next_question)
    public void nextQuestion(View view) {
        Log.d(TAG, "nextQuestion");

        radioGroup.setEnabled(true);
        saveAnswer();
        if(mQuestionIndex < 193){
            mQuestionIndex += 1;
            updateQuestion();
            unSelectRadioButtons();
            loadRadioButtonSelection();
            if(mQuestionIndex == 193){
                mNextButton.setText("Finish");
            }else {
                mNextButton.setText("Next Question");
            }
        }else{
            mainActivityIntent();
        }




//        Snackbar.make(view, "driver test fab", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();

    }

    private void mainActivityIntent(){
        Intent myIntent = new Intent(DMVStudyActivity.this, MainActivity.class);
        DMVStudyActivity.this.startActivity(myIntent);
    }

    private void unSelectRadioButtons(){
        radioGroup.clearCheck();
    }

    private void saveAnswer() {
        Log.d(TAG, "saveAnswer");

        Log.d(TAG, "radioGroup.getCheckedRadioButtonId() :" +
                radioGroup.getCheckedRadioButtonId() );


        //if button has been pushed
        if(radioGroup.getCheckedRadioButtonId() != -1
                && radioGroup.getCheckedRadioButtonId() != 0){
            Log.d(TAG, "toggle has been switched");
            int checkedRadioButton = radioGroup.getCheckedRadioButtonId();


            driverQuestions.getQuestions().get(mQuestionIndex).setSelectedAnswerResourceId(
                    checkedRadioButton);
            driverQuestions.getQuestions().get(mQuestionIndex).setSelectedAnswer(
                    getResources().getResourceEntryName(checkedRadioButton));


            Log.d(TAG, "getSelectedAnswer : " +
                    driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer());


            isAnswerCorrect();
        }

    }

    private void isAnswerCorrect(){
        Log.d(TAG, "isAnswerCorrect");


        Log.d(TAG, "getSelectedAnswer : " +
                driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer());
        Log.d(TAG, "getAnswer : " +
                driverQuestions.getQuestions().get(mQuestionIndex).getAnswer());
        driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer();

        String lastChracterString = driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer().
                substring(driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer().length() - 1);



        if(lastChracterString
                .equals(driverQuestions.getQuestions().get(mQuestionIndex).getAnswer()
                        .toLowerCase())){
            Log.d(TAG, "isAnswerCorrect answer is correct");

            driverQuestions.getQuestions().get(mQuestionIndex).setAnsweredCorrectly(true);

        }else{
            Log.d(TAG, "isAnswerCorrect answer is incorrect");
            driverQuestions.getQuestions().get(mQuestionIndex).setAnsweredCorrectly(false);
        }
    }




}
