package com.example.inshortsmovigdemo.utils;

import android.graphics.Movie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inshortsmovigdemo.models.MovieWrapper;

import java.util.List;

@Dao
public interface MovieCrud {
    @Insert
    void Insert(MovieWrapper movieWrapper);

    @Update
    void Update(MovieWrapper movieWrapper);

    @Delete
    void Delete(MovieWrapper movieWrapper);

    @Query("Select * FROM Stored_Movies WHERE movie_id = :Mid")

    List<MovieWrapper> search(int Mid);

    @Query("DELETE FROM Stored_Movies")
    void DeleteAllMovies();

    @Query("SELECT * FROM Stored_Movies")
    LiveData<List<MovieWrapper>> getAllMovies();

}
