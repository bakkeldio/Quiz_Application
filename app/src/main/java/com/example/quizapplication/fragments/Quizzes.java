package com.example.quizapplication.fragments;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.quizapplication.CustomAdapter;

import com.example.quizapplication.model.DataModel;
import com.example.quizapplication.R;

import com.example.quizapplication.model.Test;

import java.util.ArrayList;

public class Quizzes extends Fragment implements CustomAdapter.OnItemListener {


    // TODO: Rename and change types of parameters

    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private static ArrayList<DataModel> data;
    private static RecyclerView.Adapter adapter;
    private ArrayList<Test> tests;
    private ShareViewModel shareViewModel;

    public Quizzes() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shareViewModel = new ViewModelProvider(getActivity()).get(ShareViewModel.class);
        TestViewModel viewModel = new ViewModelProvider(getActivity()).get(TestViewModel.class);
        viewModel.init();
        tests = viewModel.getTest().getValue();
        viewModel.getTest().observe(getViewLifecycleOwner(), new Observer<ArrayList<Test>>() {
            @Override
            public void onChanged(ArrayList<Test> newTests) {
                tests = newTests;
            }
        });
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


    @Override
    public void onItemClick(int position) {
        shareViewModel.setTestName(tests.get(position).getName());
        shareViewModel.setTime(tests.get(position).getTime());

        Fragment testFragment = new TestFragment();
        Fragment result = new Result();
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (getArguments() !=null) {
            if (getArguments().getInt("ProgressValue", 0) == tests.get(position).getTime()) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, result).addToBackStack(null).commit();
                bundle.putString("Points", Integer.toString(getArguments().getInt("Points", 0)));
                bundle.putString("TestName", tests.get(position).getName());
                result.setArguments(bundle);
                Toast.makeText(getContext(), "Time for the test: " + tests.get(position).getTime(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            shareViewModel.setTestName(tests.get(position).getName());
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Position", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("position", position);
            editor.apply();
            fragmentManager.beginTransaction().replace(R.id.content_frame, testFragment).addToBackStack(null).commit();
        }


    }

}
