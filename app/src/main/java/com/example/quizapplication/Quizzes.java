package com.example.quizapplication;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Quizzes extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static RecyclerView recyclerView;
    static View.OnClickListener onClickListener;
    RecyclerView.LayoutManager layoutManager;
    private static ArrayList<DataModel> data;
    private static RecyclerView.Adapter adapter;

    public Quizzes() {

    }

    // TODO: Rename and change types and number of parameters
    public static Quizzes newInstance(String param1, String param2) {
        Quizzes fragment = new Quizzes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
    private static class MyOnClickListener implements View.OnClickListener{
        private final Quizzes context;
        MyOnClickListener(Quizzes context){
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            removeCard(v);
        }
        private void removeCard(View view){
            int selectedItemPosition = recyclerView.getChildAdapterPosition(view);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForAdapterPosition(selectedItemPosition);
            assert viewHolder != null;
            TextView textViewName
                    =  viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < MyData.categories.length; i++) {
                if (selectedName.equals(MyData.categories[i])) {
                    selectedItemId = MyData.id[i];
                }
            }
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quizzes, container, false);
        onClickListener = new MyOnClickListener(this);
        recyclerView = view.findViewById(R.id.my_recycler_view);
                recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i=0; i<MyData.categories.length; i++){
            data.add(new DataModel(MyData.categories[i], MyData.id[i],MyData.drawableArray[i]));
        }
        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView = view.findViewById(R.id.my_recycler_view);
        return view;
    }
}
