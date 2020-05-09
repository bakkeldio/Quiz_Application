package com.example.quizapplication.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quizapplication.MyAdapter;
import com.example.quizapplication.R;
import com.example.quizapplication.viewModel.RankViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class RankingList extends Fragment {
    private RankViewModel rankViewModel;
    private HashMap<String, String> map;
    ArrayList<String> scores = new ArrayList<>();
    private ListView listView;
    public RankingList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rankViewModel = new ViewModelProvider(getActivity()).get(RankViewModel.class);
        map = rankViewModel.getHashMapMutableLiveData().getValue();
        View view = inflater.inflate(R.layout.fragment_ranking_list, container, false);
        listView = view.findViewById(R.id.listView);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rankViewModel.getHashMapMutableLiveData().observe(getViewLifecycleOwner(), new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> stringIntegerHashMap) {
                map = stringIntegerHashMap;
            }
        });
        Toast.makeText(getContext(), "Size of map"+map.size(), Toast.LENGTH_LONG).show();
        MyAdapter myAdapter = new MyAdapter(map);
        listView.setAdapter(myAdapter);

    }
}
