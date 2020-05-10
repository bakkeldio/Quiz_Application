package com.example.quizapplication.fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.quizapplication.CustomAdapter;

import com.example.quizapplication.CustomDialog;
import com.example.quizapplication.LoginActivity;
import com.example.quizapplication.MainActivity;
import com.example.quizapplication.model.DataModel;
import com.example.quizapplication.R;

import com.example.quizapplication.model.Question;
import com.example.quizapplication.model.Test;
import com.example.quizapplication.viewModel.ShareViewModel;
import com.example.quizapplication.viewModel.TestViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Quizzes extends Fragment implements CustomAdapter.OnItemListener {


    // TODO: Rename and change types of parameters

    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TestViewModel viewModel;
    private static ArrayList<DataModel> data;
    private static RecyclerView.Adapter adapter;
    private HashMap<String, ArrayList<Question>> questionHashMap = new HashMap<>();
    private ArrayList<Test> tests =new ArrayList<>();
    private ShareViewModel shareViewModel;
    private ArrayList<Question> questions = new ArrayList<>();

    private DatabaseReference reference;
    private ArrayList<HashMap<String, ArrayList<Question>>> hashMaps = new ArrayList<>();
    public Quizzes() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference().child("tests");

    }
    private void loadQuestions(){

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (tests != null){
                    tests.clear();
                }
                int i = 0;
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {

                    Test test = new Test();
                    test.setName(snapshot.getKey());
                    test.setTime(Long.parseLong(snapshot.child("Time").getValue().toString()));
                    for (DataSnapshot dataSnapshot1 : snapshot.child("Questions").getChildren()) {
                        questions.add(dataSnapshot1.getValue(Question.class));
                    }
                    ArrayList<Question> questions1 = new ArrayList<>(questions.subList(i,questions.size()));
                    //test.setQuestions(questions);
                    test.setQuestions(questions1);
                    tests.add(test);
                    viewModel.setTests(tests);
                    i = i + questions.size();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getTest().observe(getViewLifecycleOwner(), new Observer<ArrayList<Test>>() {
            @Override
            public void onChanged(ArrayList<Test> tests1) {
                tests = tests1;
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
        shareViewModel =  new ViewModelProvider(getActivity()).get(ShareViewModel.class);
        viewModel = new ViewModelProvider(getActivity()).get(TestViewModel.class);
        loadQuestions();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quizzes, container, false);
        recyclerView = view.findViewById(R.id.my_recycler_view);
                recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i = 0; i< MyData.categories.length; i++){
            data.add(new DataModel(MyData.categories[i], MyData.id[i],MyData.drawableArray[i]));

        }

        adapter = new CustomAdapter(data, this);
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onItemClick(int position) {
        if  (position>=tests.size()){
            CustomDialog cdd=new CustomDialog(getActivity());
            cdd.show();
        } else {
            shareViewModel.setTestName(viewModel.getTest().getValue().get(position).getName());
            shareViewModel.setTime(tests.get(position).getName(), viewModel.getTest().getValue().get(position).getTime());

            Fragment testFragment = new TestFragment();
            Fragment result = new Result();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            if (getArguments() != null) {
                if (getArguments().getInt("ProgressValue", 0) == tests.get(position).getTime()) {
                    Bundle bundle = new Bundle();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, result).addToBackStack(null).commit();
                    bundle.putString("Points", Integer.toString(getArguments().getInt("Points", 0)));
                    bundle.putString("TestName", tests.get(position).getName());
                    result.setArguments(bundle);
                    Toast.makeText(getContext(), "Time for the test: " + tests.get(position).getTime(), Toast.LENGTH_LONG).show();
                }
            } else {

                shareViewModel.setTestName(tests.get(position).getName());
                Bundle bundle1 = new Bundle();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Position", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("position", position);
                bundle1.putInt("position", position);
                testFragment.setArguments(bundle1);
                editor.apply();
                fragmentManager.beginTransaction().replace(R.id.content_frame, testFragment).addToBackStack(null).commit();
            }


        }
    }

}
