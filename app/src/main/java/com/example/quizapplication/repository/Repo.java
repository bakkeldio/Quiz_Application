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

    private static ArrayList<Question> questions = new ArrayList<>();
    private static ArrayList<Test> tests = new ArrayList<>();

    public MutableLiveData<ArrayList<Question>> getQuestions(){

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
    private static void loadQuestions(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("tests");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tests.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Test test = new Test();
                    test.setName(snapshot.getKey());
                    test.setTime(Long.parseLong(snapshot.child("Time").getValue().toString()));
                    for (DataSnapshot dataSnapshot1: snapshot.child("Questions").getChildren()){
                        questions.add(dataSnapshot1.getValue(Question.class));
                    }
                    test.setQuestions(questions);
                    tests.add(test);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
