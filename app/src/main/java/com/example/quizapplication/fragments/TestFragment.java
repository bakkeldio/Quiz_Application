package com.example.quizapplication.fragments;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.quizapplication.R;
import com.example.quizapplication.model.Question;
import com.example.quizapplication.model.Test;

import java.util.ArrayList;
import java.util.List;

public class TestFragment extends Fragment {
    private ArrayList<String> q;
    private ArrayList<Question> questions;
    private CountDownTimer countDownTimer;
    public static ProgressBar progressBar;
    private EditText setQuestion; private TextView currentQuestion;
    private String option_A, option_B, option_C, option_D, answer,randQuestion;
    private List<Button> buttons;
    private int progress;
    private Button A, B, C, D, restart;
    private int currentQuestions;
    private Button startButton;
    private int i = 0;
    private int point;
    int position;
    private boolean startPressed = false;
    private int timeInt;
    private EditText points;
    private TestViewModel mViewModel;
    private TextView questionsNum;
    private boolean restartB = false;
    private Fragment resultFragment;
    private FragmentManager fragmentManager;
    private long time;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment, container, false);
        startButton = view.findViewById(R.id.startButton);
        questionsNum = view.findViewById(R.id.quiestionsNum);
        timeInt = (int) time;
        fragmentManager = getActivity().getSupportFragmentManager();
        resultFragment = new Result();
        A = view.findViewById(R.id.optionA);
        B = view.findViewById(R.id.optionB);
        C = view.findViewById(R.id.optionC);
        D = view.findViewById(R.id.optionD);
        TextView topic = view.findViewById(R.id.topicTextView);
        topic.setText(getArguments().getString("TestName"));
        setQuestion = view.findViewById(R.id.setQuestion);
        points = view.findViewById(R.id.point);
        currentQuestion = view.findViewById(R.id.currentQuestions);
        progressBar = view.findViewById(R.id.progressBar2);
        restart = view.findViewById(R.id.restart);


        return view ;
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        selectRandomQuestions();


        disableButtons();
        startButton.setEnabled(true);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    for (int i = 0; i < buttons.size(); i++) {
                        buttons.get(i).setEnabled(true);
                    }
                    startPressed = true;
                    setQuestion.setText(randQuestion);
                    countDownTimer = new MyCountDownTimer((timeInt-progress) * 1000, 1000);
                    countDownTimer.start();
                    startButton.setEnabled(false);


            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (restartB){
                    i = 0;
                    selectRandomQuestions();
                    for (int i =0;i<buttons.size(); i++){
                        buttons.get(i).setEnabled(true);
                    }
                    setQuestion.setText(randQuestion);
                    point = 0;
                    currentQuestion.setText(String.valueOf(currentQuestions));
                    questionsNum.setText(String.valueOf(q.size()));
                    points.setText(String.valueOf(point));
                    progressBar.setProgress(0);
                    countDownTimer = new MyCountDownTimer(timeInt*1000, 1000);
                    countDownTimer.start();
                }
                else {
                    restart.setEnabled(false);
                }
            }
        });
        buttonsClicked();
    }
    private void buttonsClicked(){
        questionsNum.setText(String.valueOf(q.size()));
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



    public class MyCountDownTimer extends CountDownTimer {

        MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            progress = (int) (millisUntilFinished/1000);

            progressBar.setProgress(progressBar.getMax()-progress);
        }

        @Override
        public void onFinish() {

            fragmentManager.beginTransaction().replace(R.id.content_frame, resultFragment).addToBackStack(null).commit();
        }
    }
    private void onQuestionsEnd(){

        if (currentQuestions == q.size()){
            fragmentManager.beginTransaction().replace(R.id.content_frame, resultFragment).addToBackStack(null).commit();
            /*
            Intent intent = new Intent(getContext(), Result.class);
            intent.putExtra("Score", point);
            intent.putExtra("NameOfTheTest", name);
            startActivity(intent);

             */
        }


    }
    private void selectRandomQuestions(){
        randQuestion = q.get(i);
        final int index = q.indexOf(randQuestion);
        Question question = questions.get(index);

        option_A = question.getA();
        option_B = question.getB();
        option_C =  question.getC();
        option_D =   question.getD();
        answer = question.getAnswer();
    }

    private void disableButtons(){

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
    private void setText(){
        A.setText(option_A);
        B.setText(option_B);
        C.setText(option_C);
        D.setText(option_D);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.onCleared();
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Questions", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CurrentQuestion", setQuestion.getText().toString());
        editor.putInt("ProgressValue", progressBar.getProgress());
        editor.putInt("Points",point);
        editor.putString("CurrentNumOfQ", currentQuestion.getText().toString());
        editor.apply();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("NumOf", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putString("NumOfQ", questionsNum.getText().toString());
        editor.apply();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (progressBar.getProgress() == progressBar.getMax()) {
            getChildFragmentManager().beginTransaction().replace(R.id.content_frame, resultFragment).addToBackStack(null).commit();
        } else {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Questions", Context.MODE_PRIVATE);
            point = sharedPreferences.getInt("Points", 0);
            points.setText(String.valueOf(point));
            i = Integer.parseInt(sharedPreferences.getString("CurrentNumOfQ",""))-1;
            startButton.setText("Resume");
            selectRandomQuestions();
            progressBar.setProgress(sharedPreferences.getInt("ProgressValue", 0));
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (startPressed) {
                        progressBar.setProgress(progressBar.getProgress());
                    } else {
                        for (int i = 0; i < buttons.size(); i++) {
                            buttons.get(i).setEnabled(true);
                        }
                        startPressed = true;
                        setQuestion.setText(randQuestion);
                        countDownTimer = new MyCountDownTimer((timeInt - progressBar.getProgress()) * 1000, 1000);
                        countDownTimer.start();
                        startButton.setText("Stop");

                    }
                }
            });

            buttonsClicked();
            String currentNum = sharedPreferences.getString("CurrentNumOfQ", "");
            currentQuestion.setText(currentNum);
        }
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        time = this.getArguments().getLong("Time");

        mViewModel = new ViewModelProvider(getActivity()).get(TestViewModel.class);
        mViewModel.init();
        if (getArguments() != null) {
            position = this.getArguments().getInt("Test", 0);
        }
        questions = mViewModel.getTest().getValue().get(position).getQuestions();
        mViewModel.getTest().observe(this, new Observer<ArrayList<Test>>() {
            @Override
            public void onChanged(ArrayList<Test> tests) {
                questions = tests.get(position).getQuestions();
            }
        });
        q = new ArrayList<>();
        q.clear();
        for (int i = 0; i<questions.size(); i++){
            q.add(questions.get(i).getQuestion());
        }


    }


}
