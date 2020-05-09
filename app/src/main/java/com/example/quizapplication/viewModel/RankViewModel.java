package com.example.quizapplication.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class RankViewModel extends ViewModel {
    private MutableLiveData<HashMap<String, String>> hashMapMutableLiveData;
    public RankViewModel(){
        hashMapMutableLiveData = new MutableLiveData<>();
    }

    public void setHashMapMutableLiveData(HashMap<String, String> input) {
        hashMapMutableLiveData.setValue(input);
    }

    public MutableLiveData<HashMap<String, String>> getHashMapMutableLiveData() {
        return hashMapMutableLiveData;
    }

}
