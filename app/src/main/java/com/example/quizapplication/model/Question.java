package com.example.quizapplication.model;

import java.io.Serializable;

public class Question implements Serializable {

    private String id;
    private String question;
    private String A;
    private String B;
    private String C;
    private String D;
    private String answer;

    public Question() {

    }

    public Question(String id, String question, String A, String B, String C, String D, String answer) {
        this.id = id;
        this.question = question;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getA() {
        return A;
    }

    public String getB() {
        return B;
    }

    public String getC() {
        return C;
    }

    public String getD() {
        return D;
    }

    public String getAnswer() {
        return answer;
    }

}