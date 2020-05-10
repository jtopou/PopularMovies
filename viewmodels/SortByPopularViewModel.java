package com.example.android.popularmovies.viewmodels;

import android.app.Application;
import android.os.AsyncTask;

import com.example.android.popularmovies.NetworkUtils;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.MovieEntry;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SortByPopularViewModel extends AndroidViewModel {
    String SORT_BY_POPULAR = "http://api.themoviedb.org/3/movie/popular?api_key=cbac1d3cf3ab1fd31121edfb08623cd1";
    LiveData<List<MovieEntry>> movies ;
    SortByPopularViewModel.MovieDbAsyncTask asyncTask = new SortByPopularViewModel.MovieDbAsyncTask();

    AppDatabase database = AppDatabase.getInstance(this.getApplication());

    private int FLAG_POPULAR = 0;

    public SortByPopularViewModel(@NonNull Application application) {
        super(application);
        asyncTask.execute(SORT_BY_POPULAR);
        movies =  database.movieDao().loadByPopular();
    }

    public LiveData<List<MovieEntry>> getMovies() {
        return movies;
    }

    public class MovieDbAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String urlToCall = strings[0];
            NetworkUtils.fetchMovieData(urlToCall,FLAG_POPULAR);
            return null;
        }
    }
}
