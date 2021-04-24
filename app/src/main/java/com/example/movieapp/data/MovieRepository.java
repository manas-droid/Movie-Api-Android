package com.example.movieapp.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.movieapp.Commons.Constants;
import com.example.movieapp.MyApp;
import com.example.movieapp.data.model.Movie;
import com.example.movieapp.data.model.MovieResponse;
import com.example.movieapp.data.network.NetworkBoundResource;
import com.example.movieapp.data.network.Resource;
import com.example.movieapp.data.remote.MovieApiService;
import com.example.movieapp.data.remote.RequestInterceptor;
import com.example.movieapp.database.MovieDao;
import com.example.movieapp.database.MovieRoomDatabase;

import java.util.List;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
   private final MovieApiService movieApiService;
   private final MovieDao movieDao;

   public MovieRepository(Context context){
       MovieRoomDatabase movieRoomDatabase = Room
               .databaseBuilder(context.getApplicationContext(), MovieRoomDatabase.class, "db_movies")
               .fallbackToDestructiveMigration()
               .build();

       movieDao = movieRoomDatabase.getMovieDao();

       OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
       okBuilder.addInterceptor(new RequestInterceptor());
       OkHttpClient client = okBuilder.build();
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl(Constants.API_BASE_URL)
               .client(client)
               .addConverterFactory(GsonConverterFactory.create())
               .build();

       movieApiService = retrofit.create(MovieApiService.class);
   }

   public LiveData<Resource<List<Movie>>> getPopularMovies(){
       // first parameter = room
       // second parameter = MovieResponse
       return new NetworkBoundResource<List<Movie>, MovieResponse>() {
           @Override
           protected void saveCallResult(MovieResponse item) {
               movieDao.saveMovies(item.getResults());
           }

           @Override
           protected boolean shouldFetch(List<Movie> data) {
               return true;
           }

           @Override
           protected LiveData<List<Movie>> loadFromDb() {
               return movieDao.loadPopularMovies();
           }

           @Override
           protected Call<MovieResponse> createCall() {
               return movieApiService.loadPopularMovies();
           }
       }.getAsLiveData();


   }



}
