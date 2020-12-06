package com.example.androidfinalproject.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidfinalproject.model.Comment;
import com.example.androidfinalproject.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    ArrayList<Comment> comments;
    LayoutInflater inflater;
    public CommentAdapter(Context context, ArrayList<Comment> comments){
        this.comments = comments;
        this.inflater = LayoutInflater.from(context);
    }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.comment_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            String comment = comments.get(i).getText();
            String time = comments.get(i).getTime();
            viewHolder.view.setText("- " + comment + "\n");
            viewHolder.view.append(time);
    }

        @Override
        public int getItemCount() {
            return comments.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView view;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                view = itemView.findViewById(R.id.info_comment);
            }
        }
}
