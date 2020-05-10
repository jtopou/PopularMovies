package com.example.android.popularmovies.UI;


import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapters.MainViewAdapter;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.MovieEntry;
import com.example.android.popularmovies.viewmodels.FavouriteMoviesViewModel;
import com.example.android.popularmovies.viewmodels.SortByPopularViewModel;
import com.example.android.popularmovies.viewmodels.SortByRatingViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppDatabase mDb;
    List<MovieEntry> movieList = new ArrayList<>();
    RecyclerView recyclerView;
    MainViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(getApplicationContext());

        //Setup the RecyclerView with the Adapter and the LayoutManager
        recyclerView = findViewById(R.id.recyclerview_id);

        recyclerViewAdapter = new MainViewAdapter(this, movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(recyclerViewAdapter);

        SortByPopularViewModel popularViewModel = new ViewModelProvider(this).get(SortByPopularViewModel.class);
        popularViewModel.getMovies().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(List<MovieEntry> movieEntryList) {
                recyclerViewAdapter.setMovies(movieEntryList);
            }
        });
    }

    public void sortByRating() {
        SortByRatingViewModel ratingViewModel = new ViewModelProvider(this).get(SortByRatingViewModel.class);
        ratingViewModel.getMovies().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(List<MovieEntry> movieEntryList) {
                recyclerViewAdapter.setMovies(movieEntryList);
            }
        });

    }

    public void sortByPopular() {
        SortByPopularViewModel popularViewModel = new ViewModelProvider(this).get(SortByPopularViewModel.class);
        popularViewModel.getMovies().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(List<MovieEntry> movieEntryList) {
                recyclerViewAdapter.setMovies(movieEntryList);
            }
        });
    }

    private void showFavouriteMovies() {
        FavouriteMoviesViewModel model = new ViewModelProvider(this).get(FavouriteMoviesViewModel.class);
        model.getMovies().observe(this, new Observer<List<MovieEntry>>() {
            @Override
            public void onChanged(List<MovieEntry> movieEntries) {
                recyclerViewAdapter.setMovies(movieEntries);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasSelected = item.getItemId();

        if (itemThatWasSelected == R.id.sort_by_popular) {
            Toast.makeText(this, "Sort by Popular", Toast.LENGTH_SHORT).show();
            sortByPopular();
            return true;
        }

        if ((itemThatWasSelected == R.id.sort_by_rating)) {
            Toast.makeText(this, "Sort By Rating", Toast.LENGTH_SHORT).show();
            sortByRating();
            return true;
        }

        if (itemThatWasSelected == R.id.favourite_movies) {
            Toast.makeText(this, "Favourite Movies", Toast.LENGTH_SHORT).show();
             showFavouriteMovies();
        }

        return super.onOptionsItemSelected(item);
    }

}





