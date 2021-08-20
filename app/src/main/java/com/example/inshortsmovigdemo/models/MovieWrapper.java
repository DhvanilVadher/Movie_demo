package com.example.inshortsmovigdemo.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Stored_Movies")
public class MovieWrapper {
    @PrimaryKey
    private int movie_id;
    private String title;
    private String poster_path;
    private String release_date;
    private int id;
    private float vote_average;
    private String overview;
    private int runtime;

    public MovieWrapper(int movie_id, MovieModel movieModel) {
        this.movie_id = movie_id;
        this.title = movieModel.getTitle();
        this.poster_path = movieModel.getPoster_path();
        release_date = movieModel.getRelease_date();
        id = movieModel.getMovie_id();
        vote_average = movieModel.getVote_average();
        overview = movieModel.getMovie_overview();
        runtime = movieModel.getRuntime();
    }
    public MovieModel getMovieModel(){
        return new MovieModel(title,poster_path,release_date,id,vote_average,overview,runtime);
    }
    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public MovieWrapper(){

    }
}
