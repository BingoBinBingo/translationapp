package com.example.spencerdepas.translationapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SpencerDepas on 1/6/16.
 */
public class NailQuestions {

    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("Pic_Url")
    @Expose
    private String PicUrl;
    @SerializedName("option_a")
    @Expose
    private String optionA;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("option_d")
    @Expose
    private String optionD;
    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("option_c")
    @Expose
    private String optionC;
    @SerializedName("option_b")
    @Expose
    private String optionB;

    private String selectedAnswer = "";
    private boolean answeredCorrectly;
    private int selectedAnswerResourceId;


    public String getSelectedAnswer(){
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer){
        this.selectedAnswer = selectedAnswer;
    }



    public void setSelectedAnswerResourceId(int resourceId){
        selectedAnswerResourceId = resourceId;
    }

    public int getSelectedAnswerResourceId(){
        return selectedAnswerResourceId;
    }


    /**
     *
     * @return
     * The answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     *
     * @param answer
     * The answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     *
     * @return
     * The PicUrl
     */
    public String getPicUrl() {
        return PicUrl;
    }

    /**
     *
     * @param PicUrl
     * The Pic_Url
     */
    public void setPicUrl(String PicUrl) {
        this.PicUrl = PicUrl;
    }

    /**
     *
     * @return
     * The optionA
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     *
     * @param optionA
     * The option_a
     */
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     *
     * @param question
     * The question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     *
     * @return
     * The optionD
     */
    public String getOptionD() {
        return optionD;
    }

    /**
     *
     * @param optionD
     * The option_d
     */
    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    /**
     *
     * @return
     * The questionId
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     *
     * @param questionId
     * The question_id
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    /**
     *
     * @return
     * The optionC
     */
    public String getOptionC() {
        return optionC;
    }

    /**
     *
     * @param optionC
     * The option_c
     */
    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    /**
     *
     * @return
     * The optionB
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     *
     * @param optionB
     * The option_b
     */
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }


    public boolean isAnsweredCorrectly(){
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(boolean answeredCorrectly){
        this.answeredCorrectly = answeredCorrectly;
    }

}