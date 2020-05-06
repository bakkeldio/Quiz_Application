package com.example.quizapplication.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.PasswordAuthentication;

public class ShareViewModel extends ViewModel {
    private MutableLiveData<String> testName;
    private MutableLiveData<Long> time;
    private MutableLiveData<String> points;
    private MutableLiveData<String> passedQuestions;
    private MutableLiveData<Integer> progress;

    public ShareViewModel(){
        testName = new MutableLiveData<>();
        time = new MutableLiveData<>();
        points = new MutableLiveData<>();
        passedQuestions = new MutableLiveData<>();
        progress = new MutableLiveData<>();
    }
    void setTestName(String input) {
        testName.setValue(input);
    }

    LiveData<String> getTestName() {
        return testName;
    }

    void setTime(Long input) {
        time.setValue(input);
    }

    LiveData<Long> getTime() {
        return time;
    }
    void  setPoint(String point){
        points.setValue(point);
    }

    MutableLiveData<String> getPoints() {
        return points;
    }
    void  setPassedQuestions(String string){
        passedQuestions.setValue(string);
    }

    MutableLiveData<String> getPassedQuestions() {
        return passedQuestions;
    }
    void setProgress(Integer integer){
        progress.setValue(integer);

    }

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }
}
