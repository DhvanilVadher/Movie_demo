package com.example.inshortsmovigdemo.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.inshortsmovigdemo.models.MovieWrapperPopular;
import com.example.inshortsmovigdemo.utils.MovieCrudPopular;

import java.util.List;

public class MovieRepoPopular {
    private MovieCrudPopular movieCrudPopular;
    private LiveData<List<MovieWrapperPopular>> allMoviesPopular;
    public MovieRepoPopular(Application application){
        MovieDatabasePopular database =
                MovieDatabasePopular.getInstance(application);
        movieCrudPopular = database.movieCrudPopular();
        allMoviesPopular = movieCrudPopular.getAllMovies();
    }
    public void insert(MovieWrapperPopular movieWrapper){
        new MovieRepoPopular.InsertMovieAsyncTask(movieCrudPopular).execute(movieWrapper);
    }
    public void update(MovieWrapperPopular movieWrapper) {
        new MovieRepoPopular.UpdateMovieAsyncTask(movieCrudPopular).execute(movieWrapper);
    }
    public void delete(MovieWrapperPopular movieWrapper) {
        new MovieRepoPopular.DeleteMovieAsyncTask(movieCrudPopular).execute(movieWrapper);
    }
    public void deleteAllNotes() {
        new MovieRepoPopular.DeleteAllMoviesAsyncTask(movieCrudPopular).execute();
    }
    public LiveData<List<MovieWrapperPopular>> getAllMovieWrapperPopulars() {
        return allMoviesPopular;
    }

    public List<MovieWrapperPopular> searchMovieWrapperPopular(int id){
        return movieCrudPopular.search(id);
    }

    private static class InsertMovieAsyncTask extends AsyncTask<MovieWrapperPopular,Void,Void>{

        private MovieCrudPopular movieCrud;
        private InsertMovieAsyncTask(MovieCrudPopular movieCrud){
            this.movieCrud = movieCrud;
        }
        @Override
        protected Void doInBackground(MovieWrapperPopular... movieWrappers) {
            movieCrud.Insert(movieWrappers[0]);
            return null;
        }
    }

    private static class UpdateMovieAsyncTask extends AsyncTask<MovieWrapperPopular,Void,Void>{

        private MovieCrudPopular movieCrud;
        private UpdateMovieAsyncTask(MovieCrudPopular movieCrud){
            this.movieCrud = movieCrud;
        }
        @Override
        protected Void doInBackground(MovieWrapperPopular... movieWrappers) {
            movieCrud.Update(movieWrappers[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<MovieWrapperPopular,Void,Void>{

        private MovieCrudPopular movieCrud;
        private DeleteMovieAsyncTask(MovieCrudPopular movieCrud){
            this.movieCrud = movieCrud;
        }
        @Override
        protected Void doInBackground(MovieWrapperPopular... movieWrappers) {
            movieCrud.Delete(movieWrappers[0]);
            return null;
        }
    }

    private static class DeleteAllMoviesAsyncTask extends AsyncTask<Void,Void,Void>{

        private MovieCrudPopular movieCrud;
        private DeleteAllMoviesAsyncTask(MovieCrudPopular movieCrud){
            this.movieCrud = movieCrud;
        }
        @Override
        protected Void doInBackground(Void ... voids) {
            movieCrud.DeleteAllMovies();
            return null;
        }
    }

}
