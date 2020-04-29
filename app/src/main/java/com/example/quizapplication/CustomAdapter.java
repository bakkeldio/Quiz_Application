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
    private OnItemListener onItemListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameView;
        ImageView imageView;
        OnItemListener onItemListener;
        public MyViewHolder(View itemView, OnItemListener onItemListener){
            super(itemView);
            this.nameView = itemView.findViewById(R.id.maxScore);
            this.imageView = itemView.findViewById(R.id.image_view);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }
    public interface OnItemListener{
        void onItemClick(int position);
    }
    public CustomAdapter(ArrayList<DataModel> data, OnItemListener onItemListener){
        this.dataSet = data;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, onItemListener);

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
