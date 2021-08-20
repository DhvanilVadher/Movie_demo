package com.example.inshortsmovigdemo.viewmodels;

import android.app.Application;
import android.graphics.Movie;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.inshortsmovigdemo.models.MovieWrapper;
import com.example.inshortsmovigdemo.repositories.MovieRepo;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepo repository;
    private LiveData<List<MovieWrapper>> allMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepo(application);
        allMovies = repository.getAllMovieWrappers();
    }


    public List<MovieWrapper> searchMovie(int id){
        return repository.searchMovieWrapper(id);
    }
    public void insert(MovieWrapper movieWrapper){
        repository.insert(movieWrapper);
    }

    public void update(MovieWrapper movieWrapper){
        repository.update(movieWrapper);
    }

    public void delete(MovieWrapper movieWrapper){
        repository.delete(movieWrapper);
    }

    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }
    public LiveData<List<MovieWrapper>> getAllMovies(){
        return allMovies;
    }
}
