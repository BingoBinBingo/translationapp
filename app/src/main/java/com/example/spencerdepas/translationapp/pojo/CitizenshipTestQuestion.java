package com.example.spencerdepas.translationapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SpencerDepas on 12/18/15.
 */
public class CitizenshipTestQuestion {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("question_id")
    @Expose
    private String questionId;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("explanation")
    @Expose
    private String explanation;

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
     * The explanation
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     *
     * @param explanation
     * The explanation
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

}