package com.example.quizapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapplication.MainActivity;
import com.example.quizapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Result extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private Button tryButton, upload;

    private String name;
    private DatabaseReference databaseReference;
    private boolean upLoadPressed = false;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final Map<String, String> map = new HashMap<>();
    private final List<String> list = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String testName;
    private String score;
    TextView points;
    boolean tryB = false;

    public Result() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            score = getArguments().getString("Points");
            testName = getArguments().getString("TestName");
        }
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
        databaseReference.child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_result, container, false);
        points = view.findViewById(R.id.score);
        tryButton = view.findViewById(R.id.tryButton);
        upload = view.findViewById(R.id.upload);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        points.setText(score);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadPressed = true;
                list.addAll(Arrays.asList(MyData.categories));
                for (String name: list){
                    map.put(name, "");
                }
                databaseReference.child("scores").setValue(map);
                databaseReference.child("scores").child(testName).setValue(String.valueOf(score));
            }
        });

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        tryButton.setOnClickListener(new View.OnClickListener() {

            TestFragment testFragment = new TestFragment();
            Quizzes quizzes = new Quizzes();
            @Override
            public void onClick(View v) {
                if (tryB){
                    fragmentManager.beginTransaction().replace(R.id.content_frame, testFragment).addToBackStack(null).commit();
                }
                else {
                    tryButton.setText("Try");
                    tryB = true;
                    fragmentManager.beginTransaction().replace(R.id.content_frame, quizzes).addToBackStack(null).commit();
                }

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TestFragment", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("tryButton", tryB);
        editor.apply();
    }

    @Override
    public void onStart() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TestFragment", Context.MODE_PRIVATE);
        super.onStart();
        tryB = sharedPreferences.getBoolean("tryButton", false);

    }
}
