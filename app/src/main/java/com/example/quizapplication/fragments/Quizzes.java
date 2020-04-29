package com.example.quizapplication.fragments;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizapplication.CustomAdapter;
import com.example.quizapplication.TestActivity;
import com.example.quizapplication.model.DataModel;
import com.example.quizapplication.R;
import com.example.quizapplication.model.Question;
import com.example.quizapplication.model.Test;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Quizzes extends Fragment implements CustomAdapter.OnItemListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference database;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private static ArrayList<DataModel> data;
    ArrayList<Question> questions;
    ArrayList<String> q;
    private static RecyclerView.Adapter adapter;
    private static ArrayList<Test> tests = new ArrayList<>();

    public Quizzes() {

    }

    // TODO: Rename and change types and number of parameters
    public static Quizzes newInstance(String param1, String param2) {
        Quizzes fragment = new Quizzes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        database = FirebaseDatabase.getInstance().getReference().child("tests");

        getData();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quizzes, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
                recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i=0; i<MyData.categories.length; i++){
            data.add(new DataModel(MyData.categories[i], MyData.id[i],MyData.drawableArray[i]));

        }
        adapter = new CustomAdapter(data, this);
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void getData(){
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tests.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Test test = new Test();
                    test.setName(snapshot.getKey());
                    test.setTime(Long.parseLong(snapshot.child("Time").getValue().toString()));
                    questions = new ArrayList<>();
                    int i = 1;
                    for (DataSnapshot dataSnapshot1: snapshot.child("Questions").getChildren()){
                        questions.add(dataSnapshot1.getValue(Question.class));
                        i++;
                    }
                    test.setQuestions(questions);
                    tests.add(test);


                }
                Toast.makeText(getContext(), "Data successfully added to array",Toast.LENGTH_LONG ).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(getContext(), TestActivity.class);
        intent.putExtra("Questions", tests.get(position));
        intent.putExtra("TestName", tests.get(position).getName());


        startActivity(intent);
    }
}
