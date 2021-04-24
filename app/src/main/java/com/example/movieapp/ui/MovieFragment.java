package com.example.movieapp.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieapp.R;
import com.example.movieapp.adapters.MovieAdapter;
import com.example.movieapp.data.model.Movie;
import com.example.movieapp.data.network.Resource;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {
    private MovieFragmentViewModel viewModel;
    private List<Movie> movieList;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private Integer mColumnCount;
    public MovieFragment() {
    }

    public static MovieFragment newInstance(int columnCount) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        viewModel = new ViewModelProvider(getActivity(),
                ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication()))
                .get(MovieFragmentViewModel.class);
        mColumnCount = viewModel.numOfCols.getValue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_movie, container, false);

         if(view instanceof RecyclerView){
             Context context = view.getContext();
             recyclerView = (RecyclerView) view;

             if(mColumnCount <=1){
                 recyclerView.setLayoutManager(new LinearLayoutManager(context));
             }else{
                 recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
             }

             adapter = new MovieAdapter(movieList, getActivity());
             recyclerView.setAdapter(adapter);
             recyclerView.setItemAnimator(new DefaultItemAnimator());
             loadMovies();
             observeColumnChanges();
         }
         return view;
    }

    private  void observeColumnChanges(){
        viewModel.numOfCols.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mColumnCount = integer;
                if(mColumnCount <=1){
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mColumnCount));
                }
                adapter = new MovieAdapter( movieList , getActivity());
                recyclerView.setAdapter(adapter);
            }
        });

    }


    private void loadMovies(){
        viewModel.getPopularMovie().observe(getActivity(), new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> listResource) {
                movieList = listResource.data;
                adapter.setData(movieList);
            }
        });
    }
}