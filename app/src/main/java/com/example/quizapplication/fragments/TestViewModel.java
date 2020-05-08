package com.example.quizapplication.fragments;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.quizapplication.model.Question;
import com.example.quizapplication.model.Test;
import com.example.quizapplication.repository.Repo;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

public class TestViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    DatabaseReference databaseReference;
    private MutableLiveData<ArrayList<Test>> tests;
    private MutableLiveData<ArrayList<Question>> questions;
    private MutableLiveData<ArrayList<HashMap<String, ArrayList<Question>>>> hashMapMutableLiveData;
    public TestViewModel(){
        tests = new MutableLiveData<>();
        questions = new MutableLiveData<>();
        hashMapMutableLiveData = new MutableLiveData<>();
    }

    void setTests(ArrayList<Test> test) {
        tests.setValue(test);
    }

    MutableLiveData<ArrayList<Test>> getTest(){
        return tests;
    }
    void setHashMapMutableLiveData(ArrayList<HashMap<String, ArrayList<Question>>> hashMap){
        hashMapMutableLiveData.setValue(hashMap);
    }

    public MutableLiveData<ArrayList<HashMap<String, ArrayList<Question>>>> getHashMapMutableLiveData() {
        return hashMapMutableLiveData;
    }

    public void setQuestions(ArrayList<Question> questions1) {
        questions.setValue(questions1);
    }

    MutableLiveData<ArrayList<Question>> getQuestions(){
        return questions;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
