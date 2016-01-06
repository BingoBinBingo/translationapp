package com.example.spencerdepas.translationapp.pojo;

/**
 * Created by SpencerDepas on 11/30/15.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Question {

    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("option_count")
    @Expose
    private String optionCount;
    @SerializedName("option_a")
    @Expose
    private String optionA;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("option_e")
    @Expose
    private String optionE;
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
    @SerializedName("choice_question")
    @Expose
    private String choiceQuestion;
    @SerializedName("option_b")
    @Expose
    private String optionB;

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
     * The optionCount
     */
    public String getOptionCount() {
        return optionCount;
    }

    /**
     *
     * @param optionCount
     * The option_count
     */
    public void setOptionCount(String optionCount) {
        this.optionCount = optionCount;
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
     * The optionE
     */
    public String getOptionE() {
        return optionE;
    }

    /**
     *
     * @param optionE
     * The option_e
     */
    public void setOptionE(String optionE) {
        this.optionE = optionE;
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
     * The choiceQuestion
     */
    public String getChoiceQuestion() {
        return choiceQuestion;
    }

    /**
     *
     * @param choiceQuestion
     * The choice_question
     */
    public void setChoiceQuestion(String choiceQuestion) {
        this.choiceQuestion = choiceQuestion;
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

}