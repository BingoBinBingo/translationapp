package com.example.spencerdepas.translationapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SpencerDepas on 12/1/15.
 */
public class DriversLicenseQuestions  {

    @SerializedName("driver_test_questions")
    @Expose
    private List<DriverQuestion> questions = new ArrayList<>();

    /**
     *
     * @return
     * The questions
     */
    public List<DriverQuestion> getQuestions() {
        return questions;
    }

    /**
     *
     * @param questions
     * The questions
     */
    public void setQuestions(List<DriverQuestion> questions) {
        this.questions = questions;
    }

}