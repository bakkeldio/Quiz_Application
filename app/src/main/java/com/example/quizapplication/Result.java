package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quizapplication.fragments.MyData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseUser currentUser;
    String testName;
    Button backButton;

    int score;
    final Map<String, String> map = new HashMap<>();
    final List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        score = getIntent().getIntExtra("Score", 0);
        testName = getIntent().getStringExtra("NameOfTheTest");
        TextView sc = findViewById(R.id.score);
        sc.setText(String.valueOf(score));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
        Button button = findViewById(R.id.upload);

    }
    public void onUpload(View view){
        list.addAll(Arrays.asList(MyData.categories));
        for (String name: list){
            map.put(name, "");
        }
        databaseReference.child("scores").setValue(map);
        databaseReference.child("scores").child(testName).setValue(String.valueOf(score));
        String name = databaseReference.child("username").getKey();

        ResultsCount.userResult.put(name,score);

    }

}
