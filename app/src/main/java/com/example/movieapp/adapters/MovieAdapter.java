package com.example.movieapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Commons.Constants;
import com.example.movieapp.R;
import com.example.movieapp.data.model.Movie;

import java.util.List;

public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.movie = movieList.get(position);
        Glide.with(context)
                .load(Constants.COVER_PHOTO_BASE_URL+holder.movie.getPoster_path())
                .into(holder.imageViewCover);
    }

    public void setData(List<Movie> movies){
        this.movieList = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(movieList != null){
            return movieList.size();
        }
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View view;
        public final ImageView imageViewCover;
        public Movie movie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            imageViewCover = itemView.findViewById(R.id.imageViewCover);
        }
    }
}
