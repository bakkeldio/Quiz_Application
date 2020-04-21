package com.example.quizapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quizapplication.R;
import com.example.quizapplication.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;


public class Logout extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Logout() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       Button yes =  view.findViewById(R.id.yesButton);
       Button no = view.findViewById(R.id.noButton);
       yes.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               FirebaseAuth.getInstance().signOut();
               startActivity(new Intent(getContext(), SignUpActivity.class));
           }
       });
       no.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FragmentTransaction manager = getParentFragmentManager().beginTransaction();
               Quizzes quizzes = new Quizzes();
               manager.replace(R.id.content_frame, quizzes);
               manager.commit();
           }
       });
    }

    // TODO: Rename and change types and number of parameters
    public static Logout newInstance(int title) {
        Logout fragment = new Logout();
        Bundle args = new Bundle();
        args.putInt("title", title);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }
}
