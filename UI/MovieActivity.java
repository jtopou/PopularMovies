package com.example.android.popularmovies.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.NetworkUtils;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.MovieEntry;
import com.example.android.popularmovies.viewmodels.FavouriteMoviesViewModel;
import com.example.android.popularmovies.viewmodels.SortByPopularViewModel;
import com.example.android.popularmovies.viewmodels.SortByRatingViewModel;

import java.util.List;

public class MovieActivity extends AppCompatActivity {
    //Declare views variables
    ImageView img;
    TextView txtMovieTitle, txtReleasedDate, txtMovieSynopsis, dbMovieAverage;
    Button trailerButton, reviewsButton;
    ToggleButton favouriteMovieButton;

    Context mContext = this;

    //Declare variables
    String moviePoster, movieTitle, releasedDate, movieRating, movieID, trailersLink,movieSynopsis;
    boolean isChecked;
    int movieFlag;

    //Declare the Room database
    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //Initialize the Views
        initViews();
        //Initialise the Database
        mDb = AppDatabase.getInstance(getApplicationContext());

        //Get the values from the incoming Intent
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            movieID = receivedIntent.getExtras().getString("MovieID");
            movieTitle = receivedIntent.getExtras().getString("MovieTitle");
            movieSynopsis = receivedIntent.getExtras().getString("MovieSynopsis");
            moviePoster = receivedIntent.getExtras().getString("MoviePoster");
            releasedDate = receivedIntent.getExtras().getString("ReleasedDate");
            movieRating = receivedIntent.getExtras().getString("MovieRating");
            movieFlag = receivedIntent.getExtras().getInt("MovieFlag");
            isChecked = receivedIntent.getExtras().getBoolean("IsChecked");
        }

        if (isChecked) {
            favouriteMovieButton.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
        }

        getTrailerLink(movieID);

        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trailersLink != null) {
                    Uri webpage = Uri.parse(trailersLink);
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(webIntent);
                } else {
                    Toast.makeText(mContext, "Sorry, there isn't an available trailer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setValuesToViews();

        //Setup the Reviews button to open the Reviews Activity
        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readReviews = new Intent(mContext, ReviewsActivity.class);
                readReviews.putExtra("MovieID", movieID);
                startActivity(readReviews);
            }
        });

         //Setup the button to update the Database with the favourite movies
     //TODO fix the favourite Button
        favouriteMovieButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   mDb.movieDao().updateMovie(movieID);
                } else {
                   mDb.movieDao().removeFromFavourites(movieID);
                }
            }
        });
    }

    private void initViews() {
        img = findViewById(R.id.img_movie_poster);
        txtMovieTitle = findViewById(R.id.txt_movie_title);
        txtReleasedDate = findViewById(R.id.txt_release_date);
        txtMovieSynopsis = findViewById(R.id.txt_plot_synopsis);
        dbMovieAverage = findViewById(R.id.txt_average_rating);
        reviewsButton = findViewById(R.id.reviews_button);
        trailerButton = findViewById(R.id.trailers_button);
        favouriteMovieButton = findViewById(R.id.favourite_button);
    }

    private void setValuesToViews(){
        Glide.with(this)
                .load(moviePoster)
                .into(img);

        txtMovieTitle.setText(movieTitle);
        txtReleasedDate.setText(releasedDate);
        txtMovieSynopsis.setText(movieSynopsis);
        dbMovieAverage.setText(movieRating);
        favouriteMovieButton.setChecked(isChecked);
    }

    private String getTrailerLink(String movieID) {
        String trailerJson = "http://api.themoviedb.org/3/movie/" + movieID + "/videos?api_key=cbac1d3cf3ab1fd31121edfb08623cd1";
        TrailerAsyncTask trailerAsyncTask = new TrailerAsyncTask() {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                trailersLink = s;
            }
        };

        trailerAsyncTask.execute(trailerJson);

        return trailersLink;
    }

    public class TrailerAsyncTask extends AsyncTask<String, Void, String> {
        String trailerLink;

        @Override
        protected String doInBackground(String... strings) {
            String trailerJson = strings[0];
            trailerLink = NetworkUtils.fetchTrailerData(trailerJson);
            return trailerLink;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }
}