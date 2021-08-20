package com.example.inshortsmovigdemo.adapters;

import static com.example.inshortsmovigdemo.utils.Credentials.CurrSelected;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.inshortsmovigdemo.Movie_details;
import com.example.inshortsmovigdemo.R;
import com.example.inshortsmovigdemo.models.MovieModel;
import com.example.inshortsmovigdemo.utils.Credentials;
import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;
    private Context context;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
        this.context = (Context) onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item_popular,parent,false);
        return  new MovieViewHolder(view,onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ((MovieViewHolder)holder).title.setText(mMovies.get(position).getTitle());
        ((MovieViewHolder)holder).ratingBar.setRating(mMovies.get(position).getVote_average()/2);

        RequestOptions myOptions = new RequestOptions().override(200,400);
        Glide.with(holder.itemView.getContext())
                .load(Credentials.FIXED_IMAGE_URL+mMovies.get(position).getPoster_path())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .apply(myOptions)
                .into(((MovieViewHolder) holder).imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrSelected = mMovies.get(position);
                Intent intent =  new Intent(context, Movie_details.class);
                intent.putExtra("movie",CurrSelected);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mMovies != null)
            return mMovies.size();
        else
            return 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int position){
        if(mMovies != null){
            if(mMovies.size() > 0){
                CurrSelected = mMovies.get(position);
                return mMovies.get(position);
            }
        }
        return null;
    }
}
