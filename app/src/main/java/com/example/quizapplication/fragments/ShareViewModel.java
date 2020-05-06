package com.example.quizapplication.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {
    MutableLiveData<String> testName = new MutableLiveData<>();
    MutableLiveData<Long> time = new MutableLiveData<>();

    public void setTestName(String input) {
        testName.setValue(input);
    }

    public LiveData<String> getTestName() {
        return testName;
    }

    public void setTime(Long input) {
        time.setValue(input);
    }

    public LiveData<Long> getTime() {
        return time;
    }
}
