package com.example.quizapplication.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class RankingList extends Fragment {
    private RankViewModel rankViewModel;
    private HashMap<String, String> map;
    private ArrayList<String> usernames = new ArrayList<>();
    private ListView listView;
    ArrayList<Integer> score = new ArrayList<>();

    public RankingList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rankViewModel = new ViewModelProvider(getActivity()).get(RankViewModel.class);
        map = rankViewModel.getHashMapMutableLiveData().getValue();
        usernames = rankViewModel.getUsernameMutableList().getValue();
        View view = inflater.inflate(R.layout.fragment_ranking_list, container, false);
        listView = view.findViewById(R.id.listView);
        // Inflate the layout for this fragment
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rankViewModel.getHashMapMutableLiveData().observe(getViewLifecycleOwner(), new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> stringIntegerHashMap) {
                map = stringIntegerHashMap;
            }
        });
        rankViewModel.getUsernameMutableList().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                usernames = strings;
            }
        });
        HashMap<String, String> map1 = sortByValue(map);
        MyAdapter myAdapter = new MyAdapter(map1);
        listView.setAdapter(myAdapter);




    }
    private static HashMap<String, String> sortByValue(HashMap<String, String> hm)
    {
        List<Map.Entry<String, String> > list =
                new LinkedList<>(hm.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, String> >() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        HashMap<String, String> temp = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}



