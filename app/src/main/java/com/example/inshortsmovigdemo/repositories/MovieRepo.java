package com.example.inshortsmovigdemo.repositories;

import android.app.Application;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inshortsmovigdemo.models.MovieModel;
import com.example.inshortsmovigdemo.models.MovieWrapper;
import com.example.inshortsmovigdemo.utils.MovieCrud;

import java.util.List;

public class MovieRepo {
    private MovieCrud movieCrud;
    private LiveData<List<MovieWrapper>> allMovies;
    public MovieRepo(Application application){
        MovieDatabase database =
                MovieDatabase.getInstance(application);
        movieCrud = database.movieCrud();
        allMovies = movieCrud.getAllMovies();
    }
    public void insert(MovieWrapper movieWrapper){
        new InsertMovieAsyncTask(movieCrud).execute(movieWrapper);
    }
    public void update(MovieWrapper movieWrapper) {
        new UpdateMovieAsyncTask(movieCrud).execute(movieWrapper);
    }
    public void delete(MovieWrapper movieWrapper) {
        new DeleteMovieAsyncTask(movieCrud).execute(movieWrapper);
    }
    public void deleteAllNotes() {
        new DeleteAllMoviesAsyncTask(movieCrud).execute();
    }
    public LiveData<List<MovieWrapper>> getAllMovieWrappers() {
        return allMovies;
    }

    public List<MovieWrapper> searchMovieWrapper(int id){
        return movieCrud.search(id);
    }

    static class InsertMovieAsyncTask extends AsyncTask<MovieWrapper,Void,Void>{

        private MovieCrud movieCrud;
        private InsertMovieAsyncTask(MovieCrud movieCrud){
            this.movieCrud = movieCrud;
        }
        @Override
        protected Void doInBackground(MovieWrapper... movieWrappers) {
            movieCrud.Insert(movieWrappers[0]);
            return null;
        }
    }

    private static class UpdateMovieAsyncTask extends AsyncTask<MovieWrapper,Void,Void>{

        private MovieCrud movieCrud;
        private UpdateMovieAsyncTask(MovieCrud movieCrud){
            this.movieCrud = movieCrud;
        }
        @Override
        protected Void doInBackground(MovieWrapper... movieWrappers) {
            movieCrud.Update(movieWrappers[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<MovieWrapper,Void,Void>{

        private MovieCrud movieCrud;
        private DeleteMovieAsyncTask(MovieCrud movieCrud){
            this.movieCrud = movieCrud;
        }
        @Override
        protected Void doInBackground(MovieWrapper... movieWrappers) {
            movieCrud.Delete(movieWrappers[0]);
            return null;
        }
    }

    private static class DeleteAllMoviesAsyncTask extends AsyncTask<Void,Void,Void>{

        private MovieCrud movieCrud;
        private DeleteAllMoviesAsyncTask(MovieCrud movieCrud){
            this.movieCrud = movieCrud;
        }
        @Override
        protected Void doInBackground(Void ... voids) {
            movieCrud.DeleteAllMovies();
            return null;
        }
    }

}
