package com.example.spencerdepas.translationapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SpencerDepas on 1/6/16.
 */
public class HygieneContainer {

    @SerializedName("hygine_test_questions")
    @Expose
    private List<HygineTestQuestion> hygineTestQuestions = new ArrayList<>();

    /**
     *
     * @return
     * The hygineTestQuestions
     */
    public List<HygineTestQuestion> getQuestions() {
        return hygineTestQuestions;
    }

    /**
     *
     * @param hygineTestQuestions
     * The hygine_test_questions
     */
    public void setHygineTestQuestions(List<HygineTestQuestion> hygineTestQuestions) {
        this.hygineTestQuestions = hygineTestQuestions;
    }

}
