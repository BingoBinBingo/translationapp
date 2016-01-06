package com.example.spencerdepas.translationapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SpencerDepas on 1/6/16.
 */
public class NailQuestionContainer {

    @SerializedName("questions")
    @Expose
    private List<NailQuestions> mNailquestions = new ArrayList<NailQuestions>();

    /**
     *
     * @return
     * The questions
     */
    public List<NailQuestions> getQuestions() {
        return mNailquestions;
    }

    /**
     *
     * @param mNailquestions
     * The questions
     */
    public void setQuestions(List<NailQuestions> mNailquestions) {
        this.mNailquestions = mNailquestions;
    }

}