package com.example.inshortsmovigdemo.response;

import androidx.annotation.NonNull;

import com.example.inshortsmovigdemo.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResponse {
    @SerializedName("results")
    @Expose()

    private MovieModel movie;

    public MovieModel getMovie(){
        return movie;
    }

    @NonNull
    @Override
    public String toString() {
        return "MovieResponse{" + "movie = " + movie + "}";
    }
}
