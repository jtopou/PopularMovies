package com.example.android.popularmovies.viewmodels;

import android.app.Application;

import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.ReviewsEntry;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ReviewsViewModel extends AndroidViewModel {
   // String reviewsURL = "http://api.themoviedb.org/3/movie/" + requestUrl +"/reviews?api_key=cbac1d3cf3ab1fd31121edfb08623cd1";
    LiveData<List<ReviewsEntry>> reviews;
    AppDatabase database = AppDatabase.getInstance(this.getApplication());


    public ReviewsViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<List<ReviewsEntry>> getReviews() {
        return reviews;
    }
}
