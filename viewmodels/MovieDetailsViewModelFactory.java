package com.example.android.popularmovies.viewmodels;

import com.example.android.popularmovies.database.AppDatabase;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MovieDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDb;
    private final String mMovieId;

    public MovieDetailsViewModelFactory(AppDatabase mDb, String mMovieId) {
        this.mDb = mDb;
        this.mMovieId = mMovieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MovieDetailsViewModel(mDb, mMovieId);
    }
}
