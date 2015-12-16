package com.example.spencerdepas.translationapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

public class DMVSimulationTest extends AppCompatActivity {

    private ArrayList<Integer> mWrongAnswersToStudy = new ArrayList<Integer>();
    private String TAG = "MyDMVPracticeTest";
    private String language = null;
    private  String picLocation;

    @Bind(R.id.driver_test_question) TextView mQuestion;
    @Bind(R.id.option_a) RadioButton mOptionOne;
    @Bind(R.id.option_b) RadioButton mOptionTwo;
    @Bind(R.id.option_c) RadioButton mOptionThree;
    @Bind(R.id.option_d) RadioButton mOptionFour;
    @Bind(R.id.image_view) ImageView mImage;
    @Bind(R.id.myRadioGroup)  RadioGroup radioGroup;

    @Bind(R.id.next_question) Button mNextButton;

    @Bind(R.id.select_question_dialog) Button mSelectQuestionDialog;

    @Bind(R.id.displays_wrong_answer) TextView mDisplayWrongAnswer;

    private int mQuestionIndex = 0;
    private DriversLicenseQuestions driverQuestions;
    Integer[] mQuestionIndexArray;
    private final String PREFS_LANGUAGE = "langagePrference";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmvpractice_test);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate");


        Intent intent = getIntent();
        language = intent.getStringExtra(PREFS_LANGUAGE); //if it's a string you stored.

        Log.d(TAG, "language : " + language);

        CreateJSONObject createJSONObject = new CreateJSONObject(language, "This should specifie test type", this);
        driverQuestions = createJSONObject.loadDMVQuestions();



        mQuestionIndexArray = selectRandomQuestions();
        updateQuestion();

        Log.d(TAG, "mQuestionIndex :" +mQuestionIndex );


        Log.d(TAG, "driverQuestions.getQuestions().get(mQuestionIndex).getPicUrl().toLowerCase() :" +
                driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getPicUrl().toLowerCase());


    }


    public Integer[] selectRandomQuestions(){
        Log.d(TAG, "selectRandomQuestions");

        //generates 4 random numbers for the image questions
        //generates 16 random numbers for only worded questions
        //total questions are 194
        //first 36 are image questions


        Set<Integer> set = new HashSet<Integer>();
        int minimum = 0;
        for(int i = 0 ; set.size() != 4; i ++){
            set.add(minimum + (int) (Math.random() * 36));

        }



        minimum = 36;
        int randInt = 0;
        for(int i = 0 ; set.size() != 20; i ++){
            randInt = minimum + (int) (Math.random() * 158);
            set.add(randInt);

        }


        Integer[] mQuestionIndex = set.toArray(new Integer[0]);

        return mQuestionIndex;

    }

    public void loadRadioButtonSelection(){
        Log.d(TAG, "loadRadioButtonSelection");


//        Log.d(TAG, "getSelectedAnswerResourceId() " +
//                driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswerResourceId());
//
//        Log.d(TAG, "getSelectedAnswer()" +
//                driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer());

        if(!driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer()
                .equals("")){

            Log.d(TAG, "radio button has been clicked and will be saved");

            radioGroup.check(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex])
                    .getSelectedAnswerResourceId());

        }
    }

    public void updateQuestion(){
        Log.d(TAG, "updateQuestion");




        //Makes sure question has picture.

        if(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getPicUrl().toLowerCase()
                .equals("")){
            mImage.setVisibility(View.GONE);
            picLocation = "";


        }else{
            mImage.setVisibility(View.VISIBLE);
            if(language.equals("chinese")){
                picLocation = "file:///android_asset/"
                        + driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getPicUrl().toLowerCase() + "_en"
                        +".png";
            }else{
                picLocation = "file:///android_asset/"
                        + driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getPicUrl().toLowerCase()
                        +".png";
            }
        }





        Glide.with(this)
                .load(Uri.parse(picLocation))
                .crossFade()
                .override(300, 300)
                .into(mImage);

        mQuestion.setText(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getQuestion());
        mOptionOne.setText(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionA());
        mOptionTwo.setText(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionB());
        mOptionThree.setText(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionC());
        mOptionFour.setText(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionD());

    }

    private void studyWrongQuestions(){
        mNextButton.setText("Next Question");
        //mQuestionIndex = mWrongAnswersToStudy.size();



        mQuestionIndex = 0;

        String picLocation = "";




        //Makes sure question has picture.

        if(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getPicUrl().toLowerCase()
                .equals("")){
            mImage.setVisibility(View.GONE);
            picLocation = "";


        }else{
            mImage.setVisibility(View.VISIBLE);
            if(language.equals("chinese")){
                picLocation = "file:///android_asset/"
                        + driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getPicUrl().toLowerCase() + "_en"
                        +".png";
            }else{
                picLocation = "file:///android_asset/"
                        + driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getPicUrl().toLowerCase()
                        +".png";
            }
        }

        Glide.with(this)
                .load(Uri.parse(picLocation))
                .crossFade()
                .override(300, 300)
                .into(mImage);


        int  anserLength = driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getSelectedAnswer().length();
        mDisplayWrongAnswer.setVisibility(View.VISIBLE);
        mDisplayWrongAnswer.setText("Correct answer selected above. \n You selected answer : " +
                driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getSelectedAnswer()
                        .substring(anserLength - 1, anserLength).toUpperCase());

        mQuestion.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getQuestion());
        mOptionOne.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionA());
        mOptionTwo.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionB());
        mOptionThree.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionC());
        mOptionFour.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionD());


    }



    @OnClick(R.id.select_question_dialog)
    public void selectQuestionDialog(View view) {
        Log.d(TAG, "previousQuestion");

        showSelectQuestionDialog();

    }

    @OnClick(R.id.previous_question)
    public void previousQuestion(View view) {
        Log.d(TAG, "previousQuestion");

        saveAnswer();
        mNextButton.setText("Next Question");

        if(mWrongAnswersToStudy.size() > 0) {


            if(mQuestionIndex != 0 ){
                mQuestionIndex -= 1;

                loadStudyQuestions();
                showCorrectAnswersForStudy();

            }

        }else{
            if (mQuestionIndex != 0) {
                mQuestionIndex -= 1;
                updateQuestion();
                unSelectRadioButtons();
                loadRadioButtonSelection();
            }
        }
    }

    @OnClick(R.id.next_question)
    public void nextQuestion(View view) {
        Log.d(TAG, "nextQuestion");

        if(mWrongAnswersToStudy.size() > 0){
            //this is to see if we are in review mode
            //this is reviewing questions after you have taken the test.

            mQuestionIndex += 1;
            if(mQuestionIndex < mWrongAnswersToStudy.size() ){


                //to see if we are at the end of reviewing questions
                if(mQuestionIndex == mWrongAnswersToStudy.size() -1){
                    mNextButton.setText("Finish");
                }

                loadStudyQuestions();
                showCorrectAnswersForStudy();

            }else{
                //this is if we are at the end of revieing questions
                mainActivityIntent();
            }


        }else {
            saveAnswer();

            //test is 20 questions long
            if (mQuestionIndex < 19) {

                if (mQuestionIndex == 18) {
                    mNextButton.setText("Submit");
                }

                mQuestionIndex += 1;
                updateQuestion();
                unSelectRadioButtons();
                loadRadioButtonSelection();

            } else {
                //this changes the button to the submit button to end the test and show results


                Log.d(TAG, "20th question");
                //goes through all questions to see if they are answered and record how many are right
                for (int i = 0; i < mQuestionIndexArray.length; i++) {


                    if (driverQuestions.getQuestions().get(mQuestionIndexArray[i])
                            .getSelectedAnswer().equals("")) {
                        //questions not answered on test is not complete

                        Snackbar.make(view, "Please answer all questions", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;

                    } else {
                        // all questions  answered
                        Log.d(TAG, "is answered correct :" + driverQuestions.getQuestions().get(mQuestionIndexArray[i])
                                .isAnsweredCorrectly());


                        if (!driverQuestions.getQuestions().get(mQuestionIndexArray[i])
                                .isAnsweredCorrectly()) {
                            //to save wrong answers for studying
                            mWrongAnswersToStudy.add(mQuestionIndexArray[i]);
                        }


                        if (i == 19) {
                            //all questions completed
                            finishedTestDialog(mWrongAnswersToStudy);
                        }

                    }

                }
            }


        }

    }

    private void showCorrectAnswersForStudy(){
        Log.d(TAG, "showCorrectAnswersForStudy");

        driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getAnswer();
        Log.d(TAG, "showCorrectAnswersForStudy");



        if(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getPicUrl().toLowerCase()
                .equals("")){


            mImage.setVisibility(View.GONE);
            picLocation = "";
            Log.d(TAG, "no pic url: " +
                    driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getPicUrl().toLowerCase());
        }


        if(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getAnswer().toLowerCase()
                .equals("a")){
            Log.d(TAG, "corrct answer is a ");
            mOptionOne.toggle();
        }else if(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getAnswer().toLowerCase()
                .equals("b")){
            Log.d(TAG, "corrct answer is b ");
            mOptionTwo.toggle();
        }else if(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getAnswer().toLowerCase()
                .equals("c")){
            Log.d(TAG, "corrct answer is c ");
            mOptionThree.toggle();
        }else if(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getAnswer().toLowerCase()
                .equals("d")){
            Log.d(TAG, "corrct answer is d ");
            mOptionFour.toggle();
        }







    }


    private void loadStudyQuestions(){
        Log.d(TAG, "loadStudyQuestions");




        //Makes sure question has picture.

        if(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getPicUrl().toLowerCase()
                .equals("")){
            Log.d(TAG, "no pic url: " +
                    driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getPicUrl().toLowerCase());

            mImage.setVisibility(View.GONE);
            picLocation = "";


        }else{
            Log.d(TAG, "pic url: " +
                    driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getPicUrl().toLowerCase());
            mImage.setVisibility(View.VISIBLE);
            if(language.equals("chinese")){
                picLocation = "file:///android_asset/"
                        + driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getPicUrl().toLowerCase() + "_en"
                        +".png";
            }else{
                picLocation = "file:///android_asset/"
                        + driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getPicUrl().toLowerCase()
                        +".png";
            }
        }




        Glide.with(this)
                .load(Uri.parse(picLocation))
                .crossFade()
                .override(300, 300)
                .into(mImage);

        int  anserLength = driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getSelectedAnswer().length();

        mDisplayWrongAnswer.setText("Correct answer selected above. \n You selected answer : " +
                driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getSelectedAnswer()
                        .substring(anserLength - 1, anserLength).toUpperCase());

        mQuestion.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getQuestion());
        mOptionOne.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionA());
        mOptionTwo.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionB());
        mOptionThree.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionC());
        mOptionFour.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionD());


    }


    private void showSelectQuestionDialog() {
        Log.d(TAG, "showAlertDialog");
        // Prepare grid view
        GridView gridView = new GridView(this);

        List<Integer> mList = new ArrayList<Integer>();
        for (int i = 1; i < 21; i++) {
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
                mQuestionIndex = position;
                goToSelectedQuestion(position);
                ad.dismiss();
            }
        });



    }

    private void goToSelectedQuestion(int questionindex){

        //this is only for not study
        saveAnswer();
        mQuestionIndex = questionindex;
        if(mQuestionIndex == 19){
            mNextButton.setText("Submit");
        }
        updateQuestion();
        unSelectRadioButtons();
        loadRadioButtonSelection();


    }

    private void finishedTestDialog(ArrayList<Integer> mWrongAnswers){
        //must answer 14 right to pass


        if(mWrongAnswers.size() > 6){
            final MaterialDialog mMaterialDialog = new MaterialDialog(DMVSimulationTest.this)
                    .setTitle("I'm sorry")

                    .setMessage("You need to score 14 or more to pass. You scored " +
                            (20 - mWrongAnswers.size()) + " out of 20");

            mMaterialDialog
                    .setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                            mainActivityIntent();

                        }
                    })
                    .setNegativeButton("STUDY Wrong Answers", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSelectQuestionDialog.setVisibility(View.INVISIBLE);
                            mMaterialDialog.dismiss();

                            studyWrongQuestions();
                            showCorrectAnswersForStudy();
                        }
                    });
            mMaterialDialog.show();
        }else {


            final MaterialDialog mMaterialDialog = new MaterialDialog(DMVSimulationTest.this)
                    .setTitle("Congratulations you passed")
                    .setMessage("You got " + (mWrongAnswers.size() - 20) + " out of 20");

            mMaterialDialog
                    .setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();

                        }
                    })
                    .setNegativeButton("STUDY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();

                        }
                    });
            mMaterialDialog.show();
        }


    }

    private void mainActivityIntent(){
        Intent myIntent = new Intent(DMVSimulationTest.this, MainActivity.class);
        DMVSimulationTest.this.startActivity(myIntent);
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


            driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setSelectedAnswerResourceId(
                    checkedRadioButton);
            driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setSelectedAnswer(
                    getResources().getResourceEntryName(checkedRadioButton));


            Log.d(TAG, "getSelectedAnswer : " +
                    driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer());


            isAnswerCorrect();
        }

    }

    private void isAnswerCorrect(){
        Log.d(TAG, "isAnswerCorrect");


        Log.d(TAG, "getSelectedAnswer : " +
                driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer());
        Log.d(TAG, "getAnswer : " +
                driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer());
        driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer();

        String lastChracterString = driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer().
                substring(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer().length() - 1);



        if(lastChracterString
                .equals(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer()
                        .toLowerCase())){
            Log.d(TAG, "isAnswerCorrect answer is correct");

            driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(true);

        }else{
            Log.d(TAG, "isAnswerCorrect answer is incorrect");
            driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(false);

        }
    }



}
