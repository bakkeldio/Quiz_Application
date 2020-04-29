package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapplication.model.Question;
import com.example.quizapplication.model.Test;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity  {
    ArrayList<String> q;
    ArrayList<Question> questions;
    DatabaseReference databaseReference;
    CountDownTimer countDownTimer;
    ProgressBar progressBar;
    String option_A;
    String option_B;
    String option_C;
    String option_D;
    String answer;
    String randQuestion;
    List<Button> buttons;
    //
    Button A, B, C, D;
    int currentQuestions;
    int i = 0;
    int point;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Button start = findViewById(R.id.startButton);
        //Getting time
        long time = ((Test) getIntent().getExtras().get("Questions")).getTime();
        final int timeInt = (int) time;

        //Getting and setting Test Name
        name = getIntent().getStringExtra("TestName");
        TextView topic = findViewById(R.id.topicTextView);
        topic.setText(name);
        final EditText setQuestion = findViewById(R.id.setQuestion);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("test").child(name)
                .child("Questions");
        final TextView currentQuestion = findViewById(R.id.currentQuestions);
        final EditText points =
                findViewById(R.id.point);


        progressBar = findViewById(R.id.progressBar2);
        questions = ((Test) getIntent().getExtras().get("Questions")).getQuestions();
        q = new ArrayList<>();
        for (int i = 0; i<questions.size(); i++){
            q.add(questions.get(i).getQuestion());
        }
        selectRandomQuestions();

        //Setting up the progress bar

        disableButtons();
        start.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         for (int i = 0; i<buttons.size(); i++){
                                             buttons.get(i).setEnabled(true);
                                         }
                                         setQuestion.setText(randQuestion);
                                         countDownTimer = new MyCountDownTimer(timeInt * 1000, 1000);
                                         countDownTimer.start();
                                     }
                                 });
        currentQuestions = 1;
        point = Integer.parseInt(points.getText().toString());
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getValue = A.getText().toString();


                if (getValue.equals(answer)){

                    point++;
                    points.setText(String.valueOf(point));
                    i++;
                    currentQuestions++;
                    currentQuestion.setText(String.valueOf(currentQuestions));

                    selectRandomQuestions();
                    setText();
                    setQuestion.setText(randQuestion);
                }
                else {
                    currentQuestions++;
                    currentQuestion.setText(String.valueOf(currentQuestions));
                    i++;
                    selectRandomQuestions();
                    setText();
                    setQuestion.setText(randQuestion);
                }
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getValue = B.getText().toString();


                if (getValue.equals(answer)){
                    point++;
                    points.setText(String.valueOf(point));
                    i++;
                    currentQuestions++;
                    currentQuestion.setText(String.valueOf(currentQuestions));

                    selectRandomQuestions();
                    setText();
                    setQuestion.setText(randQuestion);
                }
                else {
                    currentQuestions++;
                    currentQuestion.setText(String.valueOf(currentQuestions));
                    i++;
                    selectRandomQuestions();
                    setText();
                    setQuestion.setText(randQuestion);
                }
            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getValue = C.getText().toString();


                if (getValue.equals(answer)){
                    point++;
                    points.setText(String.valueOf(point));
                    i++;
                    currentQuestions++;
                    currentQuestion.setText(String.valueOf(currentQuestions));

                    selectRandomQuestions();
                    setText();
                    setQuestion.setText(randQuestion);
                }
                else {
                    currentQuestions++;
                    currentQuestion.setText(String.valueOf(currentQuestions));
                    i++;
                    selectRandomQuestions();
                    setText();
                    setQuestion.setText(randQuestion);
                }
            }
        });
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getValue = D.getText().toString();


                if (getValue.equals(answer)){
                    point++;
                    points.setText(String.valueOf(point));
                    i++;
                    currentQuestions++;
                    currentQuestion.setText(String.valueOf(currentQuestions));

                    selectRandomQuestions();
                    setText();
                    setQuestion.setText(randQuestion);
                }
                else {
                    currentQuestions++;
                    currentQuestion.setText(String.valueOf(currentQuestions));
                    i++;
                    selectRandomQuestions();
                    setText();
                    setQuestion.setText(randQuestion);
                }
            }
        });
        currentQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onQuestionsEnd();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





    }
    public void disableButtons(){
        A = findViewById(R.id.optionA);
        B = findViewById(R.id.optionB);
        C = findViewById(R.id.optionC);
        D = findViewById(R.id.optionD);
        buttons = new ArrayList<>();
        buttons.add(A);
        buttons.add(B);
        buttons.add(C);
        buttons.add(D);
        //Disabling the buttons
        for (Button button: buttons){
            button.setEnabled(false);
        }
        A.setText(option_A);
        B.setText(option_B);
        C.setText(option_C);
        D.setText(option_D);
    }
    public void setText(){
        A.setText(option_A);
        B.setText(option_B);
        C.setText(option_C);
        D.setText(option_D);

    }





    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished/1000);

            progressBar.setProgress(progressBar.getMax()-progress);
        }

        @Override
        public void onFinish() {
            finish();
        }
    }
    public void onQuestionsEnd(){
        if (currentQuestions == q.size()){
            Intent intent = new Intent(this, Result.class);
            intent.putExtra("Score", point);
            intent.putExtra("NameOfTheTest", name);
            startActivity(intent);
        }
    }
    public void selectRandomQuestions(){
        randQuestion = q.get(i);
        final int index = q.indexOf(randQuestion);
        Question question = questions.get(index);

        option_A = question.getA();
        option_B = question.getB();
        option_C =  question.getC();
        option_D =   question.getD();
        answer = question.getAnswer();
    }
}
