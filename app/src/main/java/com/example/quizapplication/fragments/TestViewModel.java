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

public class TestViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    DatabaseReference databaseReference;
    private MutableLiveData<ArrayList<Test>> tests;
    void init(){
        if (tests!= null){

            return;
        }
        tests = Repo.getTests();

    }

    MutableLiveData<ArrayList<Test>> getTest(){
        return tests;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
