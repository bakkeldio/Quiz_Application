package com.example.quizapplication.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Test implements Serializable {
    private String Name;
    private ArrayList<Question> Questions;
    private HashMap<String, ArrayList<Question>> hashMap;
    private Long time;

    public Test() {
    }
    public Test(String name){
        this.Name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<Question> getQuestions() {
        return Questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        Questions = questions;
    }

    public HashMap<String, ArrayList<Question>> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, ArrayList<Question>> hashMap) {
        this.hashMap = hashMap;
    }
}