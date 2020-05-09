package com.example.android.popularmovies.database;


import java.util.List;

import androidx.lifecycle.LiveData;
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

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieEntry movieEntry);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieEntry movieEntry);

    @Delete
    void deleteMovie(MovieEntry movieEntry);

    /*@Query("SELECT * FROM Movie WHERE m = :id")
    LiveData<MovieEntry> loadTaskById(int id);*/
}
