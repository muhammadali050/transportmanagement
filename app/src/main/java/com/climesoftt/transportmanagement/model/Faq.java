package com.climesoftt.transportmanagement.model;

/**
 * Created by AtoZ on 3/23/2018.
 */

public class Faq {
    private String id = "";
    private String question = "";
    private String answer = "";

    public Faq()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
