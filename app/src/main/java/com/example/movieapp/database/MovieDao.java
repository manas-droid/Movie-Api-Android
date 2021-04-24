package com.example.movieapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieapp.data.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("Select * FROM movies")
    LiveData<List<Movie>> loadPopularMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovies(List<Movie> movieList);
}
