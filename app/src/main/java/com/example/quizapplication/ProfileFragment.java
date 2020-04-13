package com.example.quizapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    DatabaseReference users;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState);
        getActivity().setTitle("Profile");
        // Write a message to the database

    }
    public void updateUserDetails(View view){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final EditText editUsername = view.findViewById(R.id.usernameText);
        final EditText editEmail = view.findViewById(R.id.emailText);
        final EditText editPhone = view.findViewById(R.id.phoneText);
        Button button = (Button) view.findViewById(R.id.updateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String phoneNumber = editPhone.getText().toString().trim();
                users = FirebaseDatabase.getInstance().getReference("userDetails");
                String id = users.push().getKey();
                AppUser user = new AppUser(id,username, email,phoneNumber);
                assert id != null;
                users.child(id).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("Adding date to real", "Success!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
                Toast.makeText(getContext(), "User Updated!", Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }



}
