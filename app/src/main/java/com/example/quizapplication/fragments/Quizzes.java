package com.example.quizapplication.fragments;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quizapplication.CustomAdapter;
import com.example.quizapplication.MainActivity;
import com.example.quizapplication.model.DataModel;
import com.example.quizapplication.R;
import com.example.quizapplication.model.Questions;

import java.util.ArrayList;

public class Quizzes extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    View.OnClickListener onClickListener;
    private static ArrayList<DataModel> data;
    private static RecyclerView.Adapter adapter;

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

    }

    @Override
    public void onClick(View v) {
        startTest(v);
    }

        private void startTest(View view){
            int selectedItemPosition = recyclerView.getChildAdapterPosition(view);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForAdapterPosition(selectedItemPosition);
            Intent intent = new Intent(getContext(), Questions.class);
            startActivity(intent);
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
        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        return view;
    }
}
