package com.example.android.popularmovies.database;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM Movie")
    LiveData<List<MovieEntry>> loadAllMovies();

    @Query("SELECT * FROM Movie WHERE movieFlag = 0")
    LiveData<List<MovieEntry>> loadByPopular();

    @Query("SELECT * FROM Movie WHERE movieFlag = 1")
    LiveData<List<MovieEntry>> loadByRating();

    @Query("SELECT * FROM Movie WHERE isChecked = 'true'")
    LiveData<List<MovieEntry>> showFavourites();

    @Query("UPDATE Movie SET isChecked = 'true' WHERE movieID = :idPassed")
    void updateMovie(String idPassed);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntry movieEntry);

    @Query("UPDATE Movie SET isChecked = 'false' WHERE movieID = :idPassed")
    void removeFromFavourites(String idPassed);

    @Query("SELECT * FROM Movie WHERE movieID = :movieID")
    LiveData<MovieEntry> loadMovieById(String movieID);
}
