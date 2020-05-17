package com.example.android.popularmovies.viewmodels;


import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.MovieEntry;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MovieDetailsViewModel extends ViewModel {
    LiveData<MovieEntry> movie;

    public MovieDetailsViewModel(AppDatabase database, String movieId) {
        movie = database.movieDao().loadMovieById(movieId);
    }

    public LiveData<MovieEntry> getMovie() {
        return movie;
    }
}
