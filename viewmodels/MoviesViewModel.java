package com.example.android.popularmovies.viewmodels;

import android.app.Application;
import android.content.Context;

import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.MovieEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MoviesViewModel extends AndroidViewModel {
    LiveData<List<MovieEntry>> movies;


    public MoviesViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        movies = database.movieDao().loadAllMovies();
    }


    public LiveData<List<MovieEntry>> getMovies() {
        return movies;
    }
}
