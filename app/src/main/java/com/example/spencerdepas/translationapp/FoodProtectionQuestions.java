package com.example.spencerdepas.translationapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SpencerDepas on 11/30/15.
 */
public class FoodProtectionQuestions {



    @SerializedName("questions")
    @Expose
    private List<Question> questions = new ArrayList<Question>();

    /**
     *
     * @return
     * The questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     *
     * @param questions
     * The questions
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}