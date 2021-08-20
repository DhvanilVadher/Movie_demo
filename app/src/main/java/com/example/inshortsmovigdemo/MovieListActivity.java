package com.example.inshortsmovigdemo;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.inshortsmovigdemo.adapters.MovieRecyclerView;
import com.example.inshortsmovigdemo.adapters.OnMovieListener;
import com.example.inshortsmovigdemo.depedency.AuthViewModel;
import com.example.inshortsmovigdemo.depedency.ViewModelProviderFactory;
import com.example.inshortsmovigdemo.models.MovieModel;
import com.example.inshortsmovigdemo.models.MovieWrapperPlaying;
import com.example.inshortsmovigdemo.models.MovieWrapperPopular;
import com.example.inshortsmovigdemo.viewmodels.MovieListViewModel;
import com.example.inshortsmovigdemo.viewmodels.MovieViewModelPlaying;
import com.example.inshortsmovigdemo.viewmodels.MovieViewModelPopular;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    private RecyclerView recyclerView,recyclerViewSearch,recyclerViewPlayingNow;
    private MovieRecyclerView movieRecyclerAdapter,movieRecyclerAdapterPopular,movieRecyclerAdapterPlayingNow;
    private TextView playingNow,popularMovie;
    private Button SavedMoviesBtn;

    private static  final String TAG = "ListActivity";


    //ViewModel

    MovieListViewModel movieListViewModel;

    MovieViewModelPopular movieViewModelPopular;
    MovieViewModelPlaying movieViewModelPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewSearch = findViewById(R.id.recyclerViewSearch);
        recyclerViewPlayingNow = findViewById(R.id.recyclerViewPlayingnow);
        playingNow = findViewById(R.id.txt_playing_now);
        popularMovie = findViewById(R.id.txt_pop_movie);
        SavedMoviesBtn = findViewById(R.id.SavedMovieButton);
        SavedMoviesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SavedMovies.class);
                startActivity(intent);
            }
        });

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        movieViewModelPopular = new ViewModelProvider(this).get(MovieViewModelPopular.class);
        movieViewModelPlaying = new ViewModelProvider(this).get(MovieViewModelPlaying.class);


        if(isOnline()) {
            ConfigureRecyclerView();
            ConfigureRecyclerViewPopular();
            ConfigureRecyclerViewPlayingNow();
            configureSearchView();
            movieListViewModel.searchPopularMovieApi(1);
            movieListViewModel.searchPlayingNow(1);
            ObserveChange();
            ObservePopularChange();
            ObservePlayingNowChange();
        }
        else{
            ConfigurePopularOffline();
            OfflineObserver();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setVisibility(View.VISIBLE);
        recyclerViewSearch.setVisibility(View.GONE);
        recyclerViewPlayingNow.setVisibility(View.VISIBLE);
        playingNow.setVisibility(View.VISIBLE);
        popularMovie.setVisibility(View.VISIBLE);
    }


    private void OfflineObserver() {
        movieViewModelPopular.getAllMovies().observe(this, new Observer<List<MovieWrapperPopular>>() {
            @Override
            public void onChanged(List<MovieWrapperPopular> movieWrapperPopulars) {
                List<MovieModel> mm = new ArrayList<>();
                for(MovieWrapperPopular i: movieWrapperPopulars){
                    mm.add(i.getMovieModel());
                }
                movieRecyclerAdapterPopular.setmMovies(mm);
            }
        });
        movieViewModelPlaying.getAllMovies().observe(this, new Observer<List<MovieWrapperPlaying>>() {
            @Override
            public void onChanged(List<MovieWrapperPlaying> movieWrapperPlayings) {
                List<MovieModel> mm = new ArrayList<>();
                for(MovieWrapperPlaying i: movieWrapperPlayings){
                    mm.add(i.getMovieModel());
                }
                movieRecyclerAdapterPlayingNow.setmMovies(mm);
            }
        });
    }

    private void ConfigurePopularOffline() {
        movieRecyclerAdapterPopular = new MovieRecyclerView(this);
        movieRecyclerAdapter = new MovieRecyclerView(this);
        movieRecyclerAdapterPlayingNow = new MovieRecyclerView(this);

        recyclerView.setAdapter(movieRecyclerAdapterPopular);
        recyclerViewSearch.setAdapter(movieRecyclerAdapter);
        recyclerViewPlayingNow.setAdapter(movieRecyclerAdapterPlayingNow);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewPlayingNow.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }

    private void ConfigureRecyclerViewPlayingNow() {
        movieRecyclerAdapterPlayingNow = new MovieRecyclerView(this);
        recyclerViewPlayingNow.setAdapter(movieRecyclerAdapterPlayingNow);
        recyclerViewPlayingNow.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        recyclerViewPlayingNow.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView1, int newState) {
                super.onScrollStateChanged(recyclerView1, newState);
                if(!recyclerView1.canScrollHorizontally(1)){
                    {
                        movieListViewModel.searchNextPagePlayingNow();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void configureSearchView(){
        SearchView searchView = findViewById(R.id.search_view);
        SearchView.OnQueryTextListener onQuery = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovieApi(query,1);
                recyclerView.setVisibility(View.GONE);
                recyclerViewSearch.setVisibility(View.VISIBLE);
                recyclerViewPlayingNow.setVisibility(View.GONE);
                playingNow.setVisibility(View.GONE);
                popularMovie.setVisibility(View.GONE);
                   return true;
            };

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() <= 3) {
                    return false;
                }
                else
                {
                    searchMovieApi(newText,1);
                    recyclerView.setVisibility(View.GONE);
                    recyclerViewSearch.setVisibility(View.VISIBLE);
                    recyclerViewPlayingNow.setVisibility(View.GONE);
                    playingNow.setVisibility(View.GONE);
                    popularMovie.setVisibility(View.GONE);
                    return true;
                }
            };
        };

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                recyclerViewSearch.setVisibility(View.VISIBLE);
                recyclerViewPlayingNow.setVisibility(View.GONE);
                playingNow.setVisibility(View.GONE);
                popularMovie.setVisibility(View.GONE);
            }
        });
        searchView.setOnQueryTextListener(onQuery);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerViewSearch.setVisibility(View.GONE);
                recyclerViewPlayingNow.setVisibility(View.VISIBLE);
                playingNow.setVisibility(View.VISIBLE);
                popularMovie.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }

    private void ConfigureRecyclerView(){
        movieRecyclerAdapter = new MovieRecyclerView(this);
        recyclerViewSearch.setAdapter(movieRecyclerAdapter);
        recyclerViewSearch.setLayoutManager(new GridLayoutManager(this,3));
        recyclerViewSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView1, int newState) {
                super.onScrollStateChanged(recyclerView1, newState);
                if(!recyclerView1.canScrollHorizontally(1)){
                    {
                            movieListViewModel.searchNextPage();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void ConfigureRecyclerViewPopular(){
        movieRecyclerAdapterPopular = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerAdapterPopular);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollHorizontally(1)){
                    {
                        movieListViewModel.searchNextPagePopular();
                    }
                }
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void searchMovieApi(String query,int pageNumber){
        movieListViewModel.searchMovieApi(query,pageNumber);
    }

    //Observers for observing change
    private void ObserveChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                movieRecyclerAdapter.setmMovies(movieModels);
            }
        });
    }

    //Observers for observing change
    private void ObservePopularChange(){
        movieListViewModel.getPopularMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                synchronized(this) {
                    movieViewModelPopular.deleteAllNotes();
                    for (MovieModel movieModel : movieModels) {
                        movieViewModelPopular.insert(new MovieWrapperPopular(movieModel.getMovie_id(), movieModel));
                    }
                }
                movieRecyclerAdapterPopular.setmMovies(movieModels);
            }
        });
    }

    private void ObservePlayingNowChange(){
        movieListViewModel.getPlayingNow().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {

                synchronized(this) {
                    movieViewModelPlaying.deleteAllNotes();
                    for (MovieModel movieModel : movieModels) {
                        movieViewModelPlaying.insert(new MovieWrapperPlaying(movieModel.getMovie_id(), movieModel));
                    }
                }
                movieRecyclerAdapterPlayingNow.setmMovies(movieModels);
            }
        });
    }

    @Override
    public void onMovieClick(int position) {}

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}