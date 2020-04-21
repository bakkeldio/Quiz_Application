package com.example.quizapplication.fragments;

import android.app.Activity;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.example.quizapplication.model.AppUser;
import com.example.quizapplication.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import static androidx.constraintlayout.widget.Constraints.TAG;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private AppUser user;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference users;
    private String id;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();;
    private Uri imageURI;
    static Uri downloadUri;
    public static ImageView avatar;
    private FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    private EditText editUserName;
    private EditText editEmail;
    private EditText editPhone;
    private static int PICK_IMAGE = 100;

    public ProfileFragment() {

    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

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
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + currentUser.getUid());
        Glide.with(this)  // this
                .load(storageReference)
                .into(avatar);
        DatabaseReference email = database.getReference("users").child(currentUser.getUid()).child("email");
        email.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editEmail.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "onCanceled", databaseError.toException());
            }
        });
        DatabaseReference username = database.getReference("users").child(currentUser.getUid()).child("username");
        username.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editUserName.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "onCanceled", databaseError.toException());
                }
        });
        DatabaseReference telephoneNumber = database.getReference("users").child(currentUser.getUid()).child("phoneNumber");
        telephoneNumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editPhone.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "onCanceled", databaseError.toException());
            }
        });


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
                users = database.getReference("users");
                String username = editUserName.getText().toString().trim();
                String email = currentUser.getEmail();
                String phoneNumber = editPhone.getText().toString().trim();
                if (currentUser != null){
                    id = currentUser.getUid();
                }

                user = new AppUser(id,username, email,phoneNumber);

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

                uploadImage();



                Toast.makeText(getContext(), "User updated!", Toast.LENGTH_SHORT).show();
            }

        });

    }

    //Uploading Image
    public void uploadImage() {

        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference().child("images/" + currentUser.getUid());
        UploadTask task = storageRef.putFile(imageURI);

        Task<Uri> uriTask = task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw task.getException();
                }
                return storageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    downloadUri = task.getResult();
                }
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


