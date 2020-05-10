package com.example.quizapplication.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class RankViewModel extends ViewModel {
    private MutableLiveData<HashMap<String, String>> hashMapMutableLiveData;
    private MutableLiveData<ArrayList<String>> usernameMutableList;
    public RankViewModel(){
        hashMapMutableLiveData = new MutableLiveData<>();
        usernameMutableList = new MutableLiveData<>();
    }

    public void setHashMapMutableLiveData(HashMap<String, String> input) {
        hashMapMutableLiveData.setValue(input);
    }
    public void setUsernameMutableList(ArrayList<String> strings){
        usernameMutableList.setValue(strings);
    }

    public MutableLiveData<ArrayList<String>> getUsernameMutableList() {
        return usernameMutableList;
    }

    public MutableLiveData<HashMap<String, String>> getHashMapMutableLiveData() {
        return hashMapMutableLiveData;
    }

}
