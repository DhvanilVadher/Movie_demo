package com.example.inshortsmovigdemo.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.inshortsmovigdemo.models.MovieWrapperPopular;
import com.example.inshortsmovigdemo.repositories.MovieRepoPopular;

import java.util.List;

public class MovieViewModelPopular  extends AndroidViewModel {

    private MovieRepoPopular repository;
    private LiveData<List<MovieWrapperPopular>> allMoviesPopular;

    public MovieViewModelPopular(@NonNull Application application) {
        super(application);
        repository = new MovieRepoPopular(application);
        allMoviesPopular = repository.getAllMovieWrapperPopulars();
    }

    public List<MovieWrapperPopular> searchMovie(int id){
        return repository.searchMovieWrapperPopular(id);
    }
    public void insert(MovieWrapperPopular movieWrapperPopular){
        repository.insert(movieWrapperPopular);
    }

    public void update(MovieWrapperPopular movieWrapperPopular){
        repository.update(movieWrapperPopular);
    }

    public void delete(MovieWrapperPopular movieWrapperPopular){
        repository.delete(movieWrapperPopular);
    }

    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }
    public LiveData<List<MovieWrapperPopular>> getAllMovies(){
        return allMoviesPopular;
    }

}
