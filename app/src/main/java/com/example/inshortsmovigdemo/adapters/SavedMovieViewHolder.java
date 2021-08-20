package com.example.inshortsmovigdemo.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inshortsmovigdemo.R;

public class SavedMovieViewHolder extends RecyclerView.ViewHolder{
    TextView title,description,rating,releasedate;
    Button deleteButton;
    ImageView poster;

    public SavedMovieViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.movie_title);
        description = itemView.findViewById(R.id.movie_description);
        rating = itemView.findViewById(R.id.movie_rating);
        releasedate = itemView.findViewById(R.id.movie_release);
        poster = itemView.findViewById(R.id.movie_img);
        deleteButton = itemView.findViewById(R.id.delete_button);
    }
}
