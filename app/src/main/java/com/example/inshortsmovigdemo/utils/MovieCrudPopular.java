package com.example.inshortsmovigdemo.utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.inshortsmovigdemo.models.MovieWrapperPopular;

import java.util.List;

@Dao
public interface MovieCrudPopular {
    @Insert
    void Insert(MovieWrapperPopular movieWrapper);

    @Update
    void Update(MovieWrapperPopular movieWrapper);

    @Delete
    void Delete(MovieWrapperPopular movieWrapper);

    @Query("Select * FROM Stored_Movies_Popular WHERE movie_id = :Mid")
    List<MovieWrapperPopular> search(int Mid);

    @Query("DELETE FROM Stored_Movies_Popular")
    void DeleteAllMovies();

    @Query("SELECT * FROM Stored_Movies_Popular")
    LiveData<List<MovieWrapperPopular>> getAllMovies();

}
