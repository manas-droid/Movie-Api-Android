package com.example.movieapp.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.data.MovieRepository;
import com.example.movieapp.data.model.Movie;
import com.example.movieapp.data.network.Resource;

import java.util.List;

public class MovieFragmentViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    public MutableLiveData<Integer> numOfCols;
    private final LiveData<Resource<List<Movie>>> popularMovie;

    public MutableLiveData<Integer> getNumOfCols() {
        return numOfCols;
    }

    public LiveData<Resource<List<Movie>>> getPopularMovie() {
        return popularMovie;
    }

    public MovieFragmentViewModel(Application application) {
        super(application);
        this.movieRepository = new MovieRepository(application);
        this.numOfCols = new MutableLiveData<Integer>();
        this.numOfCols.setValue(2);
        this.popularMovie = movieRepository.getPopularMovies();
    }

    public void setGridView(){
        numOfCols.setValue(2);
    }

    public void setListView(){
        numOfCols.setValue(1);
    }
}
