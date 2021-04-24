package com.example.movieapp.data.remote;

import com.example.movieapp.data.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieApiService {
    @GET("movie/popular")
    Call<MovieResponse> loadPopularMovies();
}
