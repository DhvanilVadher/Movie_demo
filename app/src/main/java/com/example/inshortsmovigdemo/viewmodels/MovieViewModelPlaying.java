package com.example.inshortsmovigdemo.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.inshortsmovigdemo.models.MovieWrapperPlaying;
import com.example.inshortsmovigdemo.repositories.MovieRepoPlaying;

import java.util.List;

public class MovieViewModelPlaying extends AndroidViewModel {
    private MovieRepoPlaying repository;
    private LiveData<List<MovieWrapperPlaying>> allMoviesPlaying;

    public MovieViewModelPlaying(@NonNull Application application) {
        super(application);
        repository = new MovieRepoPlaying(application);
        allMoviesPlaying = repository.getAllMovieWrapperPlayings();
    }

    public List<MovieWrapperPlaying> searchMovie(int id){
        return repository.searchMovieWrapperPlaying(id);
    }
    public void insert(MovieWrapperPlaying movieWrapperPlaying){
        repository.insert(movieWrapperPlaying);
    }

    public void update(MovieWrapperPlaying movieWrapperPlaying){
        repository.update(movieWrapperPlaying);
    }

    public void delete(MovieWrapperPlaying movieWrapperPlaying){
        repository.delete(movieWrapperPlaying);
    }

    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }
    public LiveData<List<MovieWrapperPlaying>> getAllMovies(){
        return allMoviesPlaying;
    }

}
