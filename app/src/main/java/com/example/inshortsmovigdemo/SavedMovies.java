package com.example.inshortsmovigdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.inshortsmovigdemo.adapters.SavedMovieAdapter;
import com.example.inshortsmovigdemo.models.MovieModel;
import com.example.inshortsmovigdemo.models.MovieWrapper;
import com.example.inshortsmovigdemo.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class SavedMovies extends AppCompatActivity {

    private RecyclerView recyclerViewSavedMovies;
    private MovieViewModel movieViewModel;
    private SavedMovieAdapter savedMovieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_movies);
        recyclerViewSavedMovies = findViewById(R.id.recyclerviewSaved);
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        savedMovieAdapter = new SavedMovieAdapter(this,movieViewModel);
        recyclerViewSavedMovies.setAdapter(savedMovieAdapter);
        recyclerViewSavedMovies.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        observeChanges();
        movieViewModel.getAllMovies();
    }


    void observeChanges(){
        movieViewModel.getAllMovies().observe(this, new Observer<List<MovieWrapper>>() {
            @Override
            public void onChanged(List<MovieWrapper> movieWrappers) {
                    List<MovieModel> mm = new ArrayList<>();
                    for(MovieWrapper i: movieWrappers){
                        mm.add(i.getMovieModel());
                    }
                    savedMovieAdapter.setmMovies(mm);
                    recyclerViewSavedMovies.setAdapter(savedMovieAdapter);
                }
        });
    }
}