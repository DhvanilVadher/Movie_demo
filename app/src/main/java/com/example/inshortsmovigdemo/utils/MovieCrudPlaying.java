package com.example.inshortsmovigdemo.utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inshortsmovigdemo.models.MovieWrapperPlaying;

import java.util.List;

@Dao
public interface MovieCrudPlaying  {
    @Insert
    void Insert(MovieWrapperPlaying  movieWrapper);

    @Update
    void Update(MovieWrapperPlaying  movieWrapper);

    @Delete
    void Delete(MovieWrapperPlaying movieWrapper);

    @Query("Select * FROM Stored_Movies_Playing WHERE movie_id = :Mid")
    List<MovieWrapperPlaying> search(int Mid);

    @Query("DELETE FROM Stored_Movies_Playing ")
    void DeleteAllMovies();

    @Query("SELECT * FROM Stored_Movies_Playing ")
    LiveData<List<MovieWrapperPlaying >> getAllMovies();

}