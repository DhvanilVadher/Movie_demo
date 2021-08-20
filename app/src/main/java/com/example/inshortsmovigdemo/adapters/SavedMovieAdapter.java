package com.example.inshortsmovigdemo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inshortsmovigdemo.R;
import com.example.inshortsmovigdemo.models.MovieModel;
import com.example.inshortsmovigdemo.models.MovieWrapper;
import com.example.inshortsmovigdemo.utils.Credentials;
import com.example.inshortsmovigdemo.viewmodels.MovieViewModel;

import java.util.List;

public class SavedMovieAdapter extends RecyclerView.Adapter<SavedMovieViewHolder> {


    private List<MovieModel> mMovies;

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    private Context context;
    private MovieViewModel movieViewModel ;
    public SavedMovieAdapter(Context context,MovieViewModel movieViewModel) {
        this.context = context;
        this.movieViewModel = movieViewModel;
    }

    @NonNull
    @Override
    public SavedMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_movie_model,parent,false);
       return new SavedMovieViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SavedMovieViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(mMovies.get(position).getTitle());
        holder.releasedate.setText(mMovies.get(position).getRelease_date());
        holder.rating.setText("Rating : " + mMovies.get(position).getVote_average());
        holder.description.setText(mMovies.get(position).getMovie_overview());
        Glide.with(holder.itemView.getContext())
                .load(Credentials.FIXED_IMAGE_URL+mMovies.get(position).getPoster_path())
                .into((holder).poster );
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieViewModel.delete(new MovieWrapper(mMovies.get(position).getMovie_id(),mMovies.get(position)));
                mMovies.remove(mMovies.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mMovies != null)
        return mMovies.size();
        return 0;
    }
}
