package com.example.android.popularmovies.viewmodels;

import android.app.Application;
import android.os.AsyncTask;

import com.example.android.popularmovies.NetworkUtils;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.MovieEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SortByRatingViewModel extends AndroidViewModel {
    String SORT_BY_TOP_RATING = "http://api.themoviedb.org/3/movie/top_rated?api_key=";
    LiveData<List<MovieEntry>> movies;
    MovieDbAsyncTask asyncTask = new MovieDbAsyncTask();

    AppDatabase database = AppDatabase.getInstance(this.getApplication());

    private int FLAG_RATING = 1;

    public SortByRatingViewModel(@NonNull Application application) {
        super(application);
        asyncTask.execute(SORT_BY_TOP_RATING);

    }

    public LiveData<List<MovieEntry>> getMovies() {
        return movies;
    }

    public class MovieDbAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String sortByRating = strings[0];
            NetworkUtils.fetchMovieData(sortByRating,FLAG_RATING);
            return null;
        }

    }
}
