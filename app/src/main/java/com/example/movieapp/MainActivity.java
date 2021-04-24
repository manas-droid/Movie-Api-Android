package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.movieapp.ui.MovieFragment;
import com.example.movieapp.ui.MovieFragmentViewModel;

public class MainActivity extends AppCompatActivity {
    Menu menu;
    MovieFragmentViewModel movieFragmentViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieFragmentViewModel = new ViewModelProvider(this ,
                ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())
        ).get(MovieFragmentViewModel.class);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("MoviewDB App");
        MovieFragment fragment = new MovieFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gridMenuItem:
                movieFragmentViewModel.setGridView();
                menu.findItem(R.id.listMenuItem).setVisible(true);
                menu.findItem(R.id.gridMenuItem).setVisible(false);
                return true;
            case R.id.listMenuItem:
                movieFragmentViewModel.setListView();
                menu.findItem(R.id.listMenuItem).setVisible(false);
                menu.findItem(R.id.gridMenuItem).setVisible(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}