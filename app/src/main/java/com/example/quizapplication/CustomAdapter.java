package com.example.quizapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapplication.model.DataModel;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private ArrayList<DataModel> dataSet;
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameView;
        ImageView imageView;

        public MyViewHolder(View itemView){
            super(itemView);
            this.nameView = itemView.findViewById(R.id.textViewName);
            this.imageView = itemView.findViewById(R.id.image_view);
        }
    }
    public CustomAdapter(ArrayList<DataModel> data){
        this.dataSet = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);
        view.setOnClickListener(MainActivity.onClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView name =  holder.nameView;
        ImageView imageView = holder.imageView;
        name.setText(dataSet.get(position).getName());
        imageView.setImageResource(dataSet.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
