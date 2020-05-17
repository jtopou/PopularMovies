package com.example.android.popularmovies.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface ReviewsDao {

    @Query("SELECT * FROM reviews")
    LiveData<List<ReviewsEntry>> loadAllReviews();

}
