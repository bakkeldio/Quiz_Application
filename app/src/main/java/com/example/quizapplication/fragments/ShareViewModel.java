package com.example.quizapplication.fragments;

import android.support.v4.app.INotificationSideChannel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.PasswordAuthentication;
import java.util.HashMap;

public class ShareViewModel extends ViewModel {
    private MutableLiveData<String> testName;
    private MutableLiveData<HashMap<String, Long>> time;
    private MutableLiveData<HashMap<String, String>> points;
    private MutableLiveData<HashMap<String, String>> passedQuestions;
    private MutableLiveData<HashMap<String, Integer>> progress;
    private MutableLiveData<HashMap<String, String>> currentQuestion;
    //hashmaps
    private HashMap<String,String> passedQ = new HashMap<>();
    private HashMap<String,String> point = new HashMap<>();
    private HashMap<String, Long> t = new HashMap<>();
    private HashMap<String, Integer> pros = new HashMap<>();
    private HashMap<String, String> setQ = new HashMap<>();

    public ShareViewModel(){
        testName = new MutableLiveData<>();
        time = new MutableLiveData<>();
        points = new MutableLiveData<>();
        passedQuestions = new MutableLiveData<>();
        progress = new MutableLiveData<>();
        currentQuestion = new MutableLiveData<>();
    }
    void setTestName(String input) {
        testName.setValue(input);
    }

    LiveData<String> getTestName() {
        return testName;
    }

   void setTime(String key, Long tm){
        t.put(key, tm);
        time.setValue(t);
   }
   void setPoints(String key, String value){
        point.put(key, value);
        points.setValue(point);
   }
   void setPassedQuestions(String key, String input){
        passedQ.put(key, input);
        passedQuestions.setValue(passedQ);
   }
   void setProgress(String key, Integer input){
        pros.put(key, input);
        progress.setValue(pros);
   }
   void setCurrentQuestion(String key, String input){
        setQ.put(key, input);
        currentQuestion.setValue(setQ);
    }

    MutableLiveData<HashMap<String, Integer>> getProgress() {
        return progress;
    }

    MutableLiveData<HashMap<String, Long>> getTime() {
        return time;
    }

    MutableLiveData<HashMap<String, String>> getPassedQuestions() {
        return passedQuestions;
    }

    MutableLiveData<HashMap<String, String>> getPoints() {
        return points;
    }

    MutableLiveData<HashMap<String, String>> getCurrentQuestion() {
        return currentQuestion;
    }
}
