package com.example.spencerdepas.translationapp.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import com.example.spencerdepas.translationapp.pojo.DriversLicenseQuestions;
import com.example.spencerdepas.translationapp.model.GestureListener;
import com.example.spencerdepas.translationapp.R;
import com.example.spencerdepas.translationapp.model.CreateJSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

public class DMVSimulationActivity extends AppCompatActivity implements ButtonSelector {

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
    @Bind(R.id.previous_question) Button mPreveousButton;

    //message text view
    private TextView messageView;
    //message array
    private String[] messages;
    //total messages
    private int numMessages = 10;
    //current message -start at zero
    private int currMessage = 0;

    GestureDetectorCompat gDetect;

    //@Bind(R.id.displays_wrong_answer) TextView mDisplayWrongAnswer;

    private int mQuestionIndex = 0;
    private DriversLicenseQuestions driverQuestions;
    Integer[] mQuestionIndexArray;
    private final String PREFS_LANGUAGE = "langagePrference";
    private Context mcontext;
    private View view;
    private View testView;
    private final String LANGUAGE_CHINESE =  "中文";
    private final String LANGUAGE_ENGLISH =  "English";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmv_simulation_test);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view = findViewById(R.id.simulation_test_root_view);
        testView = findViewById(R.id.control_panal);
        Log.d(TAG, "onCreate");
        mcontext = this.getApplicationContext();

        language = Locale.getDefault().getDisplayLanguage(); //if it's a string you stored.

        Log.d(TAG, "language : " + language);

        CreateJSONObject createJSONObject = new CreateJSONObject(this);
        driverQuestions = createJSONObject.loadDMVQuestions(language);




        mQuestionIndexArray = selectRandomQuestions();
        updateQuestion();

        setUpGestures();

        makePrevousButtonUnclickable();

        Log.d(TAG, "mQuestionIndex :" + mQuestionIndex);


        Log.d(TAG, "driverQuestions size:" +
                driverQuestions.getQuestions().size());


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gDetect.onTouchEvent(ev);
    }

    public void setUpGestures(){
        Log.d(TAG, "setUpGestures :"  );
        GestureListener mGestureListener = new GestureListener();
        mGestureListener.delegate = DMVSimulationActivity.this;
        gDetect = new GestureDetectorCompat(this, mGestureListener);


    }

    @Override
    public void gestureNextButton(){
        Log.d(TAG, "gestureNextButton :");


        nextQuestion(view);



    }

    @Override
    public void gesturePreviousButton(){
        Log.d(TAG, "gesturePreviousButton :" );

        previousQuestion(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public Integer[] selectRandomQuestions() {
        Log.d(TAG, "generateRandomQuestionIndex");

        //generates 4 random numbers for the image questions
        //generates 16 random numbers for only worded questions
        //total questions are 194
        //first 36 are image questions

        Set<Integer> set = new HashSet<Integer>();
        if(language.equals(LANGUAGE_CHINESE)){


            //pics are the first 8 questions in JSON
            int minimum = 0;
            for(int i = 0 ; set.size() != 4; i ++){
                set.add(minimum + (int) (Math.random() * 8));

            }


            //the rest need to be random questions without pictures
            minimum = 8;
            int randInt = 0;
            for(int i = 0 ; set.size() != 20; i ++){
                randInt = minimum + (int) (Math.random() * 121);
                set.add(randInt);

            }




        }else{

            //this is for ENGLISH
            //ENGLISH AND CHINESSE HAVE DIFERENT QUESTIONS


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



        }


        Integer[] mQuestionIndex = set.toArray(new Integer[0]);
        return mQuestionIndex;

    }



    @OnClick(R.id.select_question_dialog_fab)
    public void myFabOnClick(View view) {
        Log.d(TAG, "myFabOnClick");



        showSelectQuestionDialog();

    }

        @OnClick(R.id.image_view)
    public void myImageOnClick(View view) {
        Log.d(TAG, "myImageOnClick");




        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton( getResources().getString(R.string.dialog_dismiss),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.image_full_screen, null);
            dialog.setView(dialogLayout);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.show();

        ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
            Log.d(TAG, "picLocation" + picLocation);
        Glide.with(mcontext)
                .load(Uri.parse(picLocation))
                .into(image);





    }

    public void loadRadioButtonSelection(){
        Log.d(TAG, "loadRadioButtonSelection");


//        Log.d(TAG, "getSelectedAnswerResourceId() " +
//                driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswerResourceId());
//
//        Log.d(TAG, "getSelectedAnswer()" +
//                driverQuestions.getQuestions().get(mQuestionIndex).getSelectedAnswer());

        Log.d(TAG, "driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer()\n" +
                "                .equals(\"\") : " + driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer()
                .equals(""));

        if(!driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer()
                .equals("")){

            Log.d(TAG, "radio button has been clicked and will be saved");

            radioGroup.check(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex])
                    .getSelectedAnswerResourceId());

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

    public void updateQuestion() {
        Log.d(TAG, "updateQuestion");




        //Makes sure question has picture.

        if(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getPicUrl().toLowerCase()
                .equals("")){
            mImage.setVisibility(View.INVISIBLE);
            picLocation = "";


        }else{
            mImage.setVisibility(View.VISIBLE);
            if(language.equals(LANGUAGE_CHINESE)){
                picLocation = "file:///android_asset/"
                        + driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getPicUrl().toLowerCase() + "_chinese"
                        + ".png";
            }else{
                picLocation = "file:///android_asset/"
                        + driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getPicUrl().toLowerCase()
                        +".png";
            }
        }





        Glide.with(this)
                .load(Uri.parse(picLocation))
                .crossFade()
                .override(400, 400)
                .into(mImage);

        mQuestion.setText(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getQuestion());
        mOptionOne.setText(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionA());
        mOptionTwo.setText(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionB());
        mOptionThree.setText(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionC());
        mOptionFour.setText(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getOptionD());

    }

    private void studyWrongQuestions(){
        mNextButton.setText(getResources().getString(R.string.next_button));
        //mQuestionIndex = mWrongAnswersToStudy.size();



        mQuestionIndex = 0;

        String picLocation = "";




        //Makes sure question has picture.

        if(driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getPicUrl().toLowerCase()
                .equals("")){
            mImage.setVisibility(View.INVISIBLE);
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
                .override(400, 400)
                .into(mImage);


        int  anserLength = driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getSelectedAnswer().length();
//        mDisplayWrongAnswer.setVisibility(View.VISIBLE);
//        mDisplayWrongAnswer.setText("Correct answer selected above. \n You selected answer : " +
//                driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getSelectedAnswer()
//                        .substring(anserLength - 1, anserLength).toUpperCase());

        mQuestion.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getQuestion());
        mOptionOne.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionA());
        mOptionTwo.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionB());
        mOptionThree.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionC());
        mOptionFour.setText(driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getOptionD());


    }





    @OnClick(R.id.previous_question)
    public void previousQuestion(View view) {
        Log.d(TAG, "previousQuestion");

        if(mQuestionIndex == 1){
            makePrevousButtonUnclickable();
        }

        saveAnswer();
        mNextButton.setText(getResources().getString(R.string.next_button));

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

        makePrevousButtonClickable();

        if(mWrongAnswersToStudy.size() > 0){
            Log.d(TAG, "mWrongAnswersToStudy.size() > 0");

            //this is to see if we are in review mode
            //this is reviewing questions after you have taken the test.
            Log.d(TAG, "mWrongAnswersToStudy.size() : " + mWrongAnswersToStudy.size());
            Log.d(TAG, "mQuestionIndex : " + mQuestionIndex);



            mQuestionIndex += 1;
            if(mQuestionIndex < mWrongAnswersToStudy.size() ){
                Log.d(TAG, "mQuestionIndex < mWrongAnswersToStudy.size() &&\n");

                //to see if we are at the end of reviewing questions
                if(mQuestionIndex == mWrongAnswersToStudy.size() -1){
                    mNextButton.setText(R.string.finish);
                    Log.d(TAG, "mQuestionIndex == mWrongAnswersToStudy.size() -1");
                }

                loadStudyQuestions();
                showCorrectAnswersForStudy();

            } else{
                //this is if we are at the end of reviewing questions


                destroyActivity();


            }


        }else {
            Log.d(TAG, "mWrongAnswersToStudy.size() > 0   " +
                    "else");
            saveAnswer();

            //test is 20 questions long
            if (mQuestionIndex < 19) {

                if (mQuestionIndex == 18) {
                    mNextButton.setText(R.string.submit_test);
                }

                mQuestionIndex += 1;
                unSelectRadioButtons();
                updateQuestion();

                loadRadioButtonSelection();

            } else {
                //this changes the button to the submit button to end the test and show results


                Log.d(TAG, "20th question");
                //goes through all questions to see if they are answered and record how many are right



                    for (int i = 0; i < mQuestionIndexArray.length; i++) {


                        if (driverQuestions.getQuestions().get(mQuestionIndexArray[i])
                                .getSelectedAnswer().equals("")) {
                            //questions not answered on test is not complete

                            Log.d(TAG, "loop i is  :" + i + "    " +driverQuestions.getQuestions().get(mQuestionIndexArray[i])
                                    .getSelectedAnswer().equals(""));
                            Snackbar.make(testView, getResources().getString(R.string.anser_all_questions),
                                    Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            break;

                        } else {
                            Log.d(TAG, "in the else loop i is  :" + i + "    " +driverQuestions.getQuestions().get(mQuestionIndexArray[i])
                                    .getSelectedAnswer().equals(""));
                            // all questions  answered




                            if (!driverQuestions.getQuestions().get(mQuestionIndexArray[i])
                                    .isAnsweredCorrectly()
                                    && i != 0) {
                                //to save wrong answers for studying
                                Log.d(TAG, "!driverQuestions.getQuestions().get(mQuestionIndexArray[i]:" );
                                Log.d(TAG, "i " + i );
                                mWrongAnswersToStudy.add(mQuestionIndexArray[i]);
                            }


                            if (i == 19) {
                                Log.d(TAG, "i == 19" );
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


            mImage.setVisibility(View.INVISIBLE);
            picLocation = "";
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

            mImage.setVisibility(View.INVISIBLE);
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
                .override(400, 400)
                .into(mImage);

        int  anserLength = driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getSelectedAnswer().length();

//        mDisplayWrongAnswer.setText( getResources().getString(R.string.corrrect_answer_is_selected_above) +
//                driverQuestions.getQuestions().get(mWrongAnswersToStudy.get(mQuestionIndex)).getSelectedAnswer()
//                        .substring(anserLength - 1, anserLength).toUpperCase());

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

       // gridView.setAdapter(new ArrayAdapter<>(this, R.layout.custom_list_item, mList));
        gridView.setAdapter(new ArrayAdapter<Integer>(this, R.layout.custom_list_item, mList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);


                boolean hasBeenAnswered = hasBeenAnswered(position);

                int color = 0x00FFFFFF; // Transparent
                if (hasBeenAnswered) {
                    view.setBackgroundColor(getResources().getColor(R.color.colorForQuestionGrid));
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


                saveAnswer();
                unSelectRadioButtons();


                loadRadioButtonSelection();

                if (position == 0) {
                    makePrevousButtonUnclickable();
                } else {
                    makePrevousButtonClickable();
                }
                mNextButton.setText(getResources().getString(R.string.next_button));
                Log.d(TAG, "int pos : " + position);
                goToSelectedQuestion(position);
                ad.dismiss();
            }
        });



    }



    private void goToSelectedQuestion(int questionindex){
        Log.d(TAG, "goToSelectedQuestion");
        //this is only for not study
        saveAnswer();
        mQuestionIndex = questionindex;
        if(mQuestionIndex == 19){
            mNextButton.setText(getResources().getString(R.string.submit_test));
        }
        unSelectRadioButtons();
        updateQuestion();
        loadRadioButtonSelection();


    }

    private void finishedTestDialog(ArrayList<Integer> mWrongAnswers){
        //must answer 14 right to pass




        if(mWrongAnswers.size() > 6){
            final MaterialDialog mMaterialDialog = new MaterialDialog(DMVSimulationActivity.this)
                    .setTitle(getResources().getString(R.string.failed_test_dialog_one))

                    .setMessage(getResources().getString(R.string.failed_test_dialog_two) +
                            (20 - mWrongAnswers.size()) + getResources().getString(R.string.failed_test_dialog_three));

            mMaterialDialog
                    .setPositiveButton(getResources().getString(R.string.failed_test_dialog_conferm),
                            new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();
                            destroyActivity();

                        }
                    })
                    .setNegativeButton(
                            getResources().getString(R.string.failed_test_dialog_study_wrong_answer),
                            new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mMaterialDialog.dismiss();

                            studyWrongQuestions();
                            showCorrectAnswersForStudy();
                        }
                    });
            mMaterialDialog.show();
        }else {




            final MaterialDialog mMaterialDialog = new MaterialDialog(DMVSimulationActivity.this)
                    .setTitle(getResources().getString(R.string.you_passed))
                    .setMessage(getResources().getString(R.string.passed_test_dialog_two)
                           + " "   + (mWrongAnswers.size() - 20) + " " +  getResources().getString(R.string.passed_test_dialog_three));

            mMaterialDialog
                    .setPositiveButton(
                            getResources().getString(R.string.passed_test_dialog_conferm), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();

                        }
                    })
                    .setNegativeButton(
                            getResources().getString(R.string.passed_test_dialog_dismiss), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();

                        }
                    });
            mMaterialDialog.show();
        }


    }

    private void destroyActivity(){
        finish();
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


            setIfAnswerCorrect();
        }

    }

    private void setIfAnswerCorrect(){
        Log.d(TAG, "setIfAnswerCorrect");


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
            Log.d(TAG, "setIfAnswerCorrect answer is correct");

            driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(true);

        }else{
            Log.d(TAG, "setIfAnswerCorrect answer is incorrect");
            driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).setAnsweredCorrectly(false);

        }





    }


    private boolean hasBeenAnswered(int position){
        Log.d(TAG, "hasBeenAnswered");



        Log.d(TAG, "hasBeenAnswered getSelectedAnswer : " +
                driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer());
        Log.d(TAG, "hasBeenAnswered position : " +  position  );
//        Log.d(TAG, "hasBeenAnswered getAnswer : " +
//                driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getAnswer());
//        driverQuestions.getQuestions().get(mQuestionIndexArray[mQuestionIndex]).getSelectedAnswer();




        if(!driverQuestions.getQuestions().get(mQuestionIndexArray[position]).getSelectedAnswer().equals("")
                && !driverQuestions.getQuestions().get(mQuestionIndexArray[position]).getSelectedAnswer().equals("sound")){

            Log.d(TAG, "hasBeenAnswered has  been answered");
            Log.d(TAG, "hasBeenAnswered == " +
                    driverQuestions.getQuestions().get(mQuestionIndexArray[position]).getSelectedAnswer().equals(""));





            return true;


        }

        Log.d(TAG, "hasBeenAnswered question has not been answered");
        return false;




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
