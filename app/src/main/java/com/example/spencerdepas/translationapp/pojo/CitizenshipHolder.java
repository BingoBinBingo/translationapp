package com.example.spencerdepas.translationapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SpencerDepas on 12/18/15.
 */
public class CitizenshipHolder {

    @SerializedName("citizenship_test_questions")
    @Expose
    private List<CitizenshipTestQuestion> citizenshipTestQuestions = new ArrayList<CitizenshipTestQuestion>();

    /**
     *
     * @return
     * The citizenshipTestQuestions
     */
    public List<CitizenshipTestQuestion> getCitizenshipTestQuestions() {
        return citizenshipTestQuestions;
    }

    /**
     *
     * @param citizenshipTestQuestions
     * The citizenship_test_questions
     */
    public void setCitizenshipTestQuestions(List<CitizenshipTestQuestion> citizenshipTestQuestions) {
        this.citizenshipTestQuestions = citizenshipTestQuestions;
    }

}