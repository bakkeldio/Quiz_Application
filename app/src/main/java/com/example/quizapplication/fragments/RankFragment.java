package com.example.quizapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.example.quizapplication.AdapterRank;
import com.example.quizapplication.R;
import com.example.quizapplication.model.MyData;
import com.example.quizapplication.model.UserRank;
import com.example.quizapplication.viewModel.RankViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class RankFragment extends Fragment implements AdapterRank.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReference;
    private ArrayList<UserRank> data;
    RankViewModel rankViewModel;
    private RecyclerView.Adapter adapter;
    private HashMap<String, String> map = new HashMap<>();
    private ArrayList<Integer> scores1 = new ArrayList<>();
    private ArrayList<String> usernames = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public RankFragment() {
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rankViewModel = new ViewModelProvider(getActivity()).get(RankViewModel.class);
        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        recyclerView = view.findViewById(R.id.rankRecylerView);
        recyclerView.setHasFixedSize(true);
        Toast.makeText(getContext(), "Rank view is creating ", Toast.LENGTH_LONG).show();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<>();
        data = new ArrayList<>();
        for (int i = 0; i< MyData.categories.length; i++){
            data.add(new UserRank(MyData.categories[i],MyData.drawableArray[i]));

        }
        adapter = new AdapterRank(data, this);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String username = dataSnapshot1.child("username").getValue().toString();
                    String score = dataSnapshot1.child("scores").child("Animal").getValue().toString();
                    if (score.equals("")) {
                        map.put(username, "0");
                    } else {
                        map.put(username, score);
                    }
                    usernames.add(username);
                    rankViewModel.setHashMapMutableLiveData(map);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onItemClick(int position) {
        RankingList rankingList = new RankingList();
        final String testName = MyData.categories[position];



        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, rankingList).addToBackStack(null).commit();
    }
}
