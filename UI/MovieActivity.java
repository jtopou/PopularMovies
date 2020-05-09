package com.example.android.popularmovies.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class MovieActivity extends AppCompatActivity {
    //Declare views variables
    ImageView img;
    TextView txtMovieTitle, txtReleasedDate, txtMovieSynopsis, dbMovieAverage;
    Button trailerButton, reviewsButton;
    ToggleButton favouriteMovieButton;

    Context mContext = this;

    //Declare variables
    String moviePoster, movieTitle, releasedDate, movieRating, movieID, trailersLink,movieSynopsis;
    int movieIDInt;

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

            moviePoster = receivedIntent.getExtras().getString("MoviePoster");
            movieTitle = receivedIntent.getExtras().getString("MovieTitle");
            releasedDate = receivedIntent.getExtras().getString("ReleasedDate");
            movieRating = receivedIntent.getExtras().getString("MovieRating");
            movieIDInt = receivedIntent.getExtras().getInt("MovieID");

            movieID = Integer.toString(movieIDInt);

            getTrailerLink(movieID);

            movieSynopsis = receivedIntent.getExtras().getString("MovieSynopsis");

            Glide.with(this)
                    .load(moviePoster)
                    .into(img);

            txtMovieTitle.setText(movieTitle);
            txtReleasedDate.setText(releasedDate);
            txtMovieSynopsis.setText(movieSynopsis);
            dbMovieAverage.setText(movieRating);

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

        }
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

       /* favouriteMovieButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            MovieEntry movieEntry = new MovieEntry(movieID,movieTitle,movieSynopsis,moviePoster,releasedDate,movieRating);
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDb.movieDao().insertMovie(movieEntry);
                } else {
                    mDb.movieDao().deleteMovie(movieEntry);
                }
            }
        });*/
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