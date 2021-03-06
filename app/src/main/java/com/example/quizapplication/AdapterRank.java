package com.example.quizapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizapplication.model.UserRank;

import java.util.ArrayList;

public class AdapterRank extends RecyclerView.Adapter<AdapterRank.MViewHolder> {
    private OnClickListener onClickListener;
    private ArrayList<UserRank> ranks;
    public AdapterRank(ArrayList<UserRank> data, OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.ranks = data;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_rank, parent, false);
        MViewHolder mViewHolder = new MViewHolder(view, onClickListener);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
        TextView name = holder.testName;
        ImageView image = holder.testImage;
        name.setText(ranks.get(position).getTestName());
        image.setImageResource(ranks.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return ranks.size();
    }

    public static class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView testName;
        ImageView testImage;
        OnClickListener onClickListener;
        public MViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);
            this.testName = itemView.findViewById(R.id.testName);
            this.testImage = itemView.findViewById(R.id.testImage);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(getAdapterPosition());
        }
    }
    public interface OnClickListener{
        void onItemClick(int position);
    }
}
