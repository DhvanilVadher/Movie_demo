package com.example.inshortsmovigdemo.viewmodels;

import android.text.method.MovementMethod;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inshortsmovigdemo.models.MovieModel;
import com.example.inshortsmovigdemo.repositories.MovieRepository;

import java.util.List;

import javax.inject.Inject;

public class MovieListViewModel extends ViewModel {
    //SubClass of Live data (mutable)

    //Live Data is inside Repository


    private MovieRepository movieRepository;

    @Inject
    public MovieListViewModel() {
        Log.v("aaa","viewModel working");
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }
    public LiveData<List<MovieModel>> getPopularMovies(){
        return movieRepository.getPopular();
    }
    public LiveData<List<MovieModel>> getPlayingNow(){
        return movieRepository.getPlayingNow();
    }


    public void searchMovieApi(String query,int pageNumber){
        movieRepository.searchMovieApi(query,pageNumber);
    }

    public void searchNextPage(){
        movieRepository.searchNextPage();
    }

    public void searchPopularMovieApi(int pageNumber){
        movieRepository.searchpopularMovieApi(pageNumber);
    }
    public void searchPlayingNow(int pageNumber){
        movieRepository.searchPlayingNowApi(pageNumber);
    }
    public void searchNextPagePlayingNow(){
        movieRepository.searchPlayingNowNextPage();
    }

    public void searchNextPagePopular(){
        movieRepository.searchPopularNextPage();
    }
}
