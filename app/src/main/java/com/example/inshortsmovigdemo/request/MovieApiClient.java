package com.example.inshortsmovigdemo.request;

import android.graphics.Movie;
import android.util.Log;
import android.view.Display;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inshortsmovigdemo.AppExecutors;
import com.example.inshortsmovigdemo.models.MovieModel;
import com.example.inshortsmovigdemo.response.MovieSearchResponse;
import com.example.inshortsmovigdemo.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {
    
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;
    private RetriveMoviesRunnable retriveMoviesRunnable;

    //popular
    private MutableLiveData<List<MovieModel>> mMoviesPopular;
    private RetriveMoviesRunnablePopular retriveMoviesRunnablePopular;

    //PlayingNow
    private MutableLiveData<List<MovieModel>> mMoviesPlayingNow;
    private RetriveMoviesRunnablePlayingNow retriveMoviesRunnablePlayingNow;

     private MovieApiClient(){
        mMovies = new MutableLiveData<>();
        mMoviesPopular = new MutableLiveData<>();
        mMoviesPlayingNow = new MutableLiveData<>();
    }


    public static MovieApiClient getInstance() {
        if(instance == null){
            instance = new MovieApiClient();
        }
        return  instance;
    }

    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }
    public LiveData<List<MovieModel>> getPopularMovies() {
        return mMoviesPopular;
    }
    public LiveData<List<MovieModel>> getPlayingNow(){ return mMoviesPlayingNow;}

    public void searchMoviesApi(String query,int pageNumber){
        if(retriveMoviesRunnable != null){
            retriveMoviesRunnable = null;
        }
        retriveMoviesRunnable = new RetriveMoviesRunnable(query,pageNumber);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retriveMoviesRunnable);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        },10000, TimeUnit.MILLISECONDS);
    }

    public void searchPopularMoviesApi(int pageNumber){
        if(retriveMoviesRunnablePopular != null){
            retriveMoviesRunnablePopular = null;
        }
        retriveMoviesRunnablePopular = new RetriveMoviesRunnablePopular(pageNumber);
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retriveMoviesRunnablePopular);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // Cancelling the retrofit call
                myHandler.cancel(true);
            }
        },10000, TimeUnit.MILLISECONDS);
    }

    public void searchPlayingNowApi(int pageNumber){
         if(retriveMoviesRunnablePlayingNow != null){
             retriveMoviesRunnablePlayingNow = null;
         }
         retriveMoviesRunnablePlayingNow = new RetriveMoviesRunnablePlayingNow(pageNumber);
         final Future myHandler = AppExecutors.getInstance().networkIO().submit(retriveMoviesRunnablePlayingNow);
         AppExecutors.getInstance().networkIO().schedule(new Runnable() {
             @Override
             public void run() {
                 myHandler.cancel(true);
             }
         },10000,TimeUnit.MILLISECONDS);
    }

    class RetriveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetriveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response response = getMovies(query,pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
                        mMovies.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                }
                else{
                    String error = response.errorBody().string();
                    Log.v("Tag","Error" + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber){
            return ServiceMain.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest(){
            Log.v("Tag","Cancelling Search Request");
            cancelRequest = true;
        }
    }

    class RetriveMoviesRunnablePopular implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        public RetriveMoviesRunnablePopular( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response response = getPopularMovies(pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
                        mMoviesPopular.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = mMoviesPopular.getValue();
                        currentMovies.addAll(list);
                        mMoviesPopular.postValue(currentMovies);
                    }
                }
                else{
                    String error = response.errorBody().string();
                    Log.v("Tag","Error" + error);
                    mMoviesPopular.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Call<MovieSearchResponse> getPopularMovies(int pageNumber){
            return ServiceMain.getMovieApi().getPouplar(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("Tag","Cancelling Search Request");
            cancelRequest = true;
        }
    }

    class RetriveMoviesRunnablePlayingNow implements Runnable{

        private int pageNumber;
        boolean cancelRequest;

        public RetriveMoviesRunnablePlayingNow( int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try{
                Response response = getPlayingNow(pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
                        mMoviesPlayingNow.postValue(list);
                    }
                    else{
                        List<MovieModel> currentMovies = mMoviesPlayingNow.getValue();
                        currentMovies.addAll(list);
                        mMoviesPlayingNow.postValue(currentMovies);
                    }
                }
                else{
                    String error = response.errorBody().string();
                    Log.v("Tag","Error" + error);
                    mMoviesPlayingNow.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private Call<MovieSearchResponse> getPlayingNow(int pageNumber){
            return ServiceMain.getMovieApi().getNowPlaying(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        private void cancelRequest(){
            Log.v("Tag","Cancelling Search Request");
            cancelRequest = true;
        }
    }
}