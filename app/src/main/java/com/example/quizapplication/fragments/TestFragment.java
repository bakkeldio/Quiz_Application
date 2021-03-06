package com.example.quizapplication.fragments;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
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
import com.example.quizapplication.viewModel.ShareViewModel;
import com.example.quizapplication.viewModel.TestViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestFragment extends Fragment {
    private ArrayList<String> q;
    private ArrayList<Question> questions;
    private CountDownTimer countDownTimer;
    private ProgressBar progressBar;
    private EditText setQuestion; private TextView currentQuestion;
    private String option_A, option_B, option_C, option_D, answer,randQuestion;
    private List<Button> buttons;
    private int progress = 0;
    private Button A, B, C, D;
    private int currentQuestions;
    private Button startButton;
    private int i = 0;
    private int point;
    private int position;
    private boolean startPressed = false;
    private int timeInt;
    private EditText points;
    private TestViewModel mViewModel;
    private TextView questionsNum;
    private Fragment resultFragment;
    private FragmentManager fragmentManager;
    private long time;
    private ShareViewModel shareViewModel;
    private TextView topic;
    private Bundle bundle;
    private ArrayList<Test> tests;
    private String name;
    private HashMap<String, ArrayList<Question>> hashMap;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.test_fragment, container, false);
        startButton = view.findViewById(R.id.startButton);
        questionsNum = view.findViewById(R.id.quiestionsNum);
        mViewModel = new ViewModelProvider(getActivity()).get(TestViewModel.class);
        shareViewModel = new ViewModelProvider(getActivity()).get(ShareViewModel.class);
        if (getArguments() != null){
            position = getArguments().getInt("position");
        }
        tests = mViewModel.getTest().getValue();

        name = tests.get(position).getName();
        questions = tests.get(position).getQuestions();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Position", Context.MODE_PRIVATE);
        time = tests.get(sharedPreferences.getInt("position",0)).getTime();


        fragmentManager = getActivity().getSupportFragmentManager();
        resultFragment = new Result();
        A = view.findViewById(R.id.optionA);
        B = view.findViewById(R.id.optionB);
        C = view.findViewById(R.id.optionC);
        D = view.findViewById(R.id.optionD);
        topic = view.findViewById(R.id.topicTextView);
        topic.setText(shareViewModel.getTestName().getValue());
        timeInt = (int) time;

        setQuestion = view.findViewById(R.id.setQuestion);
        points = view.findViewById(R.id.point);
        currentQuestion = view.findViewById(R.id.currentQuestions);
        progressBar = view.findViewById(R.id.progressBar2);

        shareViewModel.getPoints().observe(getViewLifecycleOwner(), new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> stringStringHashMap) {
                points.setText(stringStringHashMap.get(name));
            }
        });
        shareViewModel.getPassedQuestions().observe(getViewLifecycleOwner(), new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> stringStringHashMap) {
                currentQuestion.setText(stringStringHashMap.get(name));
            }
        });
        if (questionsNum != null) {
            questionsNum.setText(String.valueOf(questions.size()));
        }

        bundle = new Bundle();


        return view ;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.getTest().observe(getViewLifecycleOwner(), new Observer<ArrayList<Test>>() {
            @Override
            public void onChanged(ArrayList<Test> tests1) {
                questions = tests1.get(position).getQuestions();
            }
        });

        q = new ArrayList<>();
        q.clear();
        for (int i = 0; i<questions.size(); i++){
            q.add(questions.get(i).getQuestion());
        }
        setQuestion.setText(q.get(0));

        shareViewModel.getTestName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                topic.setText(s);
            }
        });
        if (shareViewModel.getProgress() != null) {
            shareViewModel.getProgress().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Integer>>() {
                @Override
                public void onChanged(HashMap<String, Integer> stringIntegerHashMap) {
                    try {
                        progressBar.setProgress(stringIntegerHashMap.get(name));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        shareViewModel.getCurrentQuestion().observe(getViewLifecycleOwner(), new Observer<HashMap<String, String>>() {
            @Override
            public void onChanged(HashMap<String, String> stringStringHashMap) {
                setQuestion.setText(stringStringHashMap.get(name));
            }
        });


        if (progressBar.getProgress() == progressBar.getMax()) {
            getChildFragmentManager().beginTransaction().replace(R.id.content_frame, resultFragment).addToBackStack(null).commit();
        }


        selectQuestions();

        disableButtons();
        startButton.setEnabled(true);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    for (int i = 0; i < buttons.size(); i++) {
                        buttons.get(i).setEnabled(true);
                    }
                    startPressed = true;
                    if (setQuestion.getText() != null){

                        i = q.indexOf(setQuestion.getText().toString());
                    }else {
                        setQuestion.setText(randQuestion);
                    }
                    countDownTimer = new MyCountDownTimer((timeInt -progressBar.getProgress()) * 1000, 1000);
                    countDownTimer.start();
                    startButton.setEnabled(false);


            }
        });

        buttonsClicked();
    }
    private void buttonsClicked(){
        questionsNum.setText(String.valueOf(q.size()));

        if (Integer.parseInt(currentQuestion.getText().toString())>1 || Integer.parseInt(points.getText().toString())>0){
            currentQuestions = Integer.parseInt(currentQuestion.getText().toString());
            point = Integer.parseInt(points.getText().toString());
        }
        else {
            currentQuestions = 1;
            point = Integer.parseInt(points.getText().toString());
        }
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

                    selectQuestions();
                    setText();
                    setQuestion.setText(randQuestion);
                }
                else {
                    currentQuestions++;
                    currentQuestion.setText(String.valueOf(currentQuestions));
                    i++;
                    selectQuestions();
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

                    selectQuestions();
                    setText();
                    setQuestion.setText(randQuestion);
                }
                else {
                    currentQuestions++;
                    currentQuestion.setText(String.valueOf(currentQuestions));
                    i++;
                    selectQuestions();
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

                    selectQuestions();
                    setText();
                    setQuestion.setText(randQuestion);
                }
                else {
                    currentQuestions++;
                    currentQuestion.setText(String.valueOf(currentQuestions));
                    i++;
                    selectQuestions();
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

                    selectQuestions();
                    setText();
                    setQuestion.setText(randQuestion);
                }
                else {
                    currentQuestions++;
                    currentQuestion.setText(String.valueOf(currentQuestions));
                    i++;
                    selectQuestions();
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
            Bundle onFinishBundle = new Bundle();
            Quizzes quizzes = new Quizzes();

            onFinishBundle.putString("Points", String.valueOf(point));
            onFinishBundle.putString("TestName", name);
            onFinishBundle.putString("ProgressValue", String.valueOf(progressBar.getProgress()));
            resultFragment.setArguments(onFinishBundle);
            quizzes.setArguments(onFinishBundle);
            fragmentManager.beginTransaction().replace(R.id.content_frame, resultFragment).addToBackStack(null).commit();
        }
    }
    private void onQuestionsEnd(){

        if (currentQuestions == q.size()){
            Bundle questionEndBundle = new Bundle();
            Quizzes quizzes = new Quizzes();
            questionEndBundle.putString("Points", String.valueOf(point));
            questionEndBundle.putString("TestName",name);

            resultFragment.setArguments(questionEndBundle);
            quizzes.setArguments(questionEndBundle);
            fragmentManager.beginTransaction().replace(R.id.content_frame, resultFragment).addToBackStack(null).commit();

        }


    }


    private void selectQuestions(){
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
    public void onDestroyView() {
        super.onDestroyView();

        TestFragment testFragment = new TestFragment();
        bundle.putInt("ProgressValue", progressBar.getProgress());
        testFragment.setArguments(bundle);
        if (progressBar.getProgress() == progressBar.getMax()){
            shareViewModel.setPoints(name, "0");
            shareViewModel.setPassedQuestions(name, "1");
            shareViewModel.setCurrentQuestion(name, q.get(0));
            shareViewModel.setProgress(name, 0);
        }
        else {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            shareViewModel.setProgress(name, progressBar.getProgress());
            shareViewModel.setPassedQuestions(name, currentQuestion.getText().toString());
            shareViewModel.setPoints(name, points.getText().toString());

            shareViewModel.setCurrentQuestion(name, setQuestion.getText().toString());
        }
    }



}
