package com.example.inshortsmovigdemo.utils;

import com.example.inshortsmovigdemo.models.MovieModel;
import com.example.inshortsmovigdemo.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MovieApi {

    @GET("search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") String page
    );

    @GET("movie/{id}")
    Call<MovieModel> getMovieById(
            @Path("id")String id,
            @Query("api_key") String key
    );

    //popular
    @GET("movie/popular")
    Call<MovieSearchResponse> getPouplar(
            @Query("api_key") String key,
            @Query("page") int page
    );

    //nowPlaying
    @GET("movie/now_playing")
    Call<MovieSearchResponse> getNowPlaying(
            @Query("api_key") String key,
            @Query("page") int page
    );
}
