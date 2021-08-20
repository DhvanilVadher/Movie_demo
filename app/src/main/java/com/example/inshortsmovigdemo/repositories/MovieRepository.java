package com.example.inshortsmovigdemo.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inshortsmovigdemo.models.MovieModel;
import com.example.inshortsmovigdemo.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    // Acting as repositories
    private static MovieRepository instance;
    private MovieApiClient movieApiClient;
    private String mQuery;
    private int mPageNumber;
    private int mPageNumberPopular;
    private int mPageNumberPlayingNow;

    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository()
    {
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }
    public LiveData<List<MovieModel>> getPopular(){
        return movieApiClient.getPopularMovies();
    }
    public LiveData<List<MovieModel>> getPlayingNow() { return movieApiClient.getPlayingNow();}

    public void searchMovieApi(String query,int pageNumber){
        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesApi(query,pageNumber);
    }

    public void searchpopularMovieApi(int pageNumber){
        mPageNumberPopular = pageNumber;
        movieApiClient.searchPopularMoviesApi(pageNumber);
    }

    public void searchPlayingNowApi(int PageNumber){
        mPageNumberPlayingNow = PageNumber;
        movieApiClient.searchPlayingNowApi(mPageNumberPlayingNow);
    }

    public void searchPlayingNowNextPage(){
        mPageNumberPlayingNow++;
        movieApiClient.searchPlayingNowApi(mPageNumberPlayingNow);
    }

    public void searchPopularNextPage(){
        mPageNumberPopular++;
        Log.v("ssss", String.valueOf(mPageNumberPopular));
        movieApiClient.searchPopularMoviesApi(mPageNumberPopular);
    }

    public void searchNextPage() {
        mPageNumber++;
        movieApiClient.searchMoviesApi(mQuery,mPageNumber);
    }
}
