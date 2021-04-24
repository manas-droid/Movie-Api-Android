package com.example.movieapp.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.movieapp.data.model.Movie;

@Database(entities = {Movie.class} , version = 1 , exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {
    public abstract MovieDao getMovieDao();
}
