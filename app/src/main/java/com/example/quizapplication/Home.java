package com.example.quizapplication;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Intent intent = new Intent(Home.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
