package com.example.inshortsmovigdemo.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.inshortsmovigdemo.models.MovieWrapperPlaying ;
import com.example.inshortsmovigdemo.utils.MovieCrudPlaying ;

import java.util.List;

public class MovieRepoPlaying {
    private MovieCrudPlaying  movieCrudPlaying ;
    private LiveData<List<MovieWrapperPlaying >> allMoviesPlaying ;
    public MovieRepoPlaying (Application application){
        MovieDatabasePlaying  database =
                MovieDatabasePlaying .getInstance(application);
        movieCrudPlaying  = database.movieCrudPlaying();
        allMoviesPlaying  = movieCrudPlaying .getAllMovies();
    }
    public void insert(MovieWrapperPlaying  movieWrapper){
        new MovieRepoPlaying .InsertMovieAsyncTask(movieCrudPlaying ).execute(movieWrapper);
    }
    public void update(MovieWrapperPlaying  movieWrapper) {
        new MovieRepoPlaying .UpdateMovieAsyncTask(movieCrudPlaying ).execute(movieWrapper);
    }
    public void delete(MovieWrapperPlaying  movieWrapper) {
        new MovieRepoPlaying .DeleteMovieAsyncTask(movieCrudPlaying ).execute(movieWrapper);
    }
    public void deleteAllNotes() {
        new MovieRepoPlaying .DeleteAllMoviesAsyncTask(movieCrudPlaying ).execute();
    }
    public LiveData<List<MovieWrapperPlaying >> getAllMovieWrapperPlayings() {
        return allMoviesPlaying ;
    }

    public List<MovieWrapperPlaying > searchMovieWrapperPlaying (int id){
        return movieCrudPlaying .search(id);
    }

    private static class InsertMovieAsyncTask extends AsyncTask<MovieWrapperPlaying ,Void,Void> {

        private MovieCrudPlaying  movieCrud;
        private InsertMovieAsyncTask(MovieCrudPlaying  movieCrud){
            this.movieCrud = movieCrud;
        }
        @Override
        protected Void doInBackground(MovieWrapperPlaying ... movieWrappers) {
            movieCrud.Insert(movieWrappers[0]);
            return null;
        }
    }

    private static class UpdateMovieAsyncTask extends AsyncTask<MovieWrapperPlaying ,Void,Void>{

        private MovieCrudPlaying  movieCrud;
        private UpdateMovieAsyncTask(MovieCrudPlaying  movieCrud){
            this.movieCrud = movieCrud;
        }
        @Override
        protected Void doInBackground(MovieWrapperPlaying ... movieWrappers) {
            movieCrud.Update(movieWrappers[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<MovieWrapperPlaying ,Void,Void>{

        private MovieCrudPlaying  movieCrud;
        private DeleteMovieAsyncTask(MovieCrudPlaying  movieCrud){
            this.movieCrud = movieCrud;
        }
        @Override
        protected Void doInBackground(MovieWrapperPlaying ... movieWrappers) {
            movieCrud.Delete(movieWrappers[0]);
            return null;
        }
    }

    private static class DeleteAllMoviesAsyncTask extends AsyncTask<Void,Void,Void>{

        private MovieCrudPlaying  movieCrud;
        private DeleteAllMoviesAsyncTask(MovieCrudPlaying  movieCrud){
            this.movieCrud = movieCrud;
        }
        @Override
        protected Void doInBackground(Void ... voids) {
            movieCrud.DeleteAllMovies();
            return null;
        }
    }

}
