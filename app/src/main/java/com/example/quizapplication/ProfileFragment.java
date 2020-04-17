package com.example.quizapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private AppUser user;
    private FirebaseDatabase database;
    private DatabaseReference users;
    private String id;
    private Uri imageURI;
    private ImageView avatar;
    private StorageReference storageRef;


    private EditText editUserName;
    private EditText editEmail;
    private EditText editPhone;
    private static int PICK_IMAGE = 100;

    public ProfileFragment() {
        // Required empty public constructor
    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

         storageRef =FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE){
            assert data != null;
            imageURI = data.getData();
            avatar.setImageURI(imageURI);

        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState);
        getActivity().setTitle("Profile");
        // Write a message to the database
        editUserName = view.findViewById(R.id.usernameText);
        editEmail = view.findViewById(R.id.emailText);
        editPhone = view.findViewById(R.id.phoneText);
        Button changeImage = view.findViewById(R.id.changeImage);
        avatar = view.findViewById(R.id.photoImageView);
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        Button update = (Button) view.findViewById(R.id.updateButton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                users = database.getReference("users");
                String username = editUserName.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String phoneNumber = editPhone.getText().toString().trim();
                //users.child(id).setValue(user);
                users.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        id = s;
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                user = new AppUser(id,username, email,phoneNumber);
                //assert id != null;

                database = FirebaseDatabase.getInstance();
                users = database.getReference();
                users.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child(id).setValue(user);


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("User", databaseError.getMessage());
                    }
                });


                Toast.makeText(getContext(), "User updated!", Toast.LENGTH_SHORT).show();
            }

        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
}


