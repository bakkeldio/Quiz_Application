package com.example.quizapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.DefaultTaskExecutor;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


import static android.view.View.IMPORTANT_FOR_ACCESSIBILITY_AUTO;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private AppUser user;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference users;
    private DatabaseReference email;
    private DatabaseReference username;
    private DatabaseReference telephoneNumber;
    private String id;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();;
    private Uri imageURI;
    private ImageView avatar;
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
        email = database.getReference("users").child(currentUser.getUid()).child("email");
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
        username = database.getReference("users").child(currentUser.getUid()).child("username");
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
        telephoneNumber = database.getReference("users").child(currentUser.getUid()).child("phoneNumber");
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

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images/" + currentUser.getUid());
        storageRef.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getContext() ,"Image Uploaded!", Toast.LENGTH_LONG).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_LONG).show();
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


