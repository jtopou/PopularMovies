package com.example.android.popularmovies.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Movie")
public class MovieEntry {
    @PrimaryKey @NonNull
    private String movieID;
    private String title,overview, posterPath,releaseDate,movieRating;
    private boolean isChecked;
    private int movieFlag;

    //The MovieEntry Constructor
    public MovieEntry(@NonNull String movieID, String title, String overview, String posterPath, String releaseDate, String movieRating,int movieFlag,boolean isChecked) {
        this.movieID = movieID;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.movieRating = movieRating;
        this.movieFlag = movieFlag;
        this.isChecked = isChecked;
    }

    //Setters and Getters
    public String getMovieID() {
        return movieID;
    }
    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPosterPath() {
        return posterPath;
    }
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public boolean isChecked() {
        return isChecked;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    public int getMovieFlag() {
        return movieFlag;
    }
    public void setMovieFlag(int movieFlag) {
        this.movieFlag = movieFlag;
    }
    public String getMovieRating() {
        return movieRating;
    }
    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }
}
