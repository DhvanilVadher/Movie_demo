 package com.example.inshortsmovigdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.inshortsmovigdemo.adapters.MovieViewHolder;
import com.example.inshortsmovigdemo.models.MovieModel;
import com.example.inshortsmovigdemo.models.MovieWrapper;
import com.example.inshortsmovigdemo.utils.Credentials;
import com.example.inshortsmovigdemo.viewmodels.MovieViewModel;

import java.util.List;

 public class Movie_details extends AppCompatActivity {

    private ImageView imageView;
    private TextView title,desciption,release_date;
    private RatingBar ratingBar;
    private MovieViewModel movieViewModel;
    private Button savebutton;
    private MovieModel movieModel;
    private boolean saved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        imageView = findViewById(R.id.imageView_poster);
        title = findViewById(R.id.movie_title);
        desciption = findViewById(R.id.movie_description);
        release_date = findViewById(R.id.release_date);
        ratingBar = findViewById(R.id.rating_bar);
        savebutton = findViewById(R.id.save_movie);

        observerMovie();
        getDataFromIntent();
        configureSave();
    }


    private void observerMovie(){
        movieViewModel.getAllMovies().observe(this, new Observer<List<MovieWrapper>>() {
            @Override
            public void onChanged(List<MovieWrapper> movieWrappers) {
                for(MovieWrapper movieWrapper:movieWrappers){
                    Log.v("Saved",movieWrapper.getTitle());
                }
            }
        });
    }
    private void configureSave(){
        if(movieModel != null){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    List<MovieWrapper> movieWrappers = movieViewModel.searchMovie(movieModel.getMovie_id());
                    if(movieWrappers.size()!=0){
                        saved = true;
                        savebutton.setBackgroundResource(R.drawable.icon_bkmark);
                    }
                }
            };
            Thread t = new Thread(runnable);
            t.start();
        }
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movieModel!=null) {
                       if(!saved){
                            MovieWrapper movieWrapper = new MovieWrapper(movieModel.getMovie_id(),movieModel);
                            try {
                                saved = true;
                                movieViewModel.insert(movieWrapper);
                                savebutton.setBackgroundResource(R.drawable.icon_bkmark);
                            }
                            catch (Exception e){
                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                       }
                       else {
                           MovieWrapper movieWrapper = new MovieWrapper(movieModel.getMovie_id(),movieModel);
                           try{
                               movieViewModel.delete(movieWrapper);
                               savebutton.setBackgroundResource(R.drawable.bkmarkhollo);
                               saved = false;
                           }
                           catch (Exception e){
                               Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                           }
                       }
                }
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void getDataFromIntent() {
        if(getIntent().hasExtra("movie")){
            movieModel = getIntent().getParcelableExtra("movie");
            Glide.with(this).load(Credentials.FIXED_IMAGE_URL+movieModel.getPoster_path())
                 .into(imageView);
            title.setText(movieModel.getTitle());
            desciption.setText("Movie OverView:" + movieModel.getMovie_overview());
            release_date.setText("Release Date:" + movieModel.getRelease_date());
            ratingBar.setRating(movieModel.getVote_average()/2);
        }
     }
 }