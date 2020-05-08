package com.example.quizapplication.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.quizapplication.model.Question;
import com.example.quizapplication.model.Test;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Repo {

    /*
    private static ArrayList<Question> questions = new ArrayList<>();
    private static ArrayList<Test> tests = new ArrayList<>();

    public static MutableLiveData<ArrayList<Question>> getQuestions(){

        loadQuestions();
        MutableLiveData<ArrayList<Question>> questionsLive = new MutableLiveData<>();
        questionsLive.setValue(questions);
        return questionsLive;
    }
    public static MutableLiveData<ArrayList<Test>> getTests(){
        loadQuestions();
        MutableLiveData<ArrayList<Test>> testsLive = new MutableLiveData<>();
        testsLive.setValue(tests);
        return testsLive;
    }

     */




}
