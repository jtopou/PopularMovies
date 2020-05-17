package com.example.android.popularmovies.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.MovieEntry;
import com.example.android.popularmovies.viewmodels.MovieDetailsViewModel;
import com.example.android.popularmovies.viewmodels.MovieDetailsViewModelFactory;

public class MovieActivity extends AppCompatActivity {
    //Declare views variables
    ImageView img;
    TextView txtMovieTitle, txtReleasedDate, txtMovieSynopsis, dbMovieAverage;
    Button trailerButton, reviewsButton;
    ToggleButton favouriteMovieButton;

    Context mContext = this;

    //Declare variables
    static String  movieID, trailersLink;

    //Getter for the movieID variable
    public static String getMovieID() {
        return movieID;
    }

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

            MovieDetailsViewModelFactory factory = new MovieDetailsViewModelFactory(mDb, movieID);
            final MovieDetailsViewModel viewModel = new ViewModelProvider( this,factory).get(MovieDetailsViewModel.class);
            viewModel.getMovie().observe(this, new Observer<MovieEntry>() {
                @Override
                public void onChanged(MovieEntry movieEntry) {
                    viewModel.getMovie().removeObserver(this);
                    populateUI(movieEntry);
                }
            });
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

        //Setup the Reviews button to open the Reviews Activity
      /*  reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readReviews = new Intent(mContext, ReviewsActivity.class);
                readReviews.putExtra("MovieID", movieID);
                startActivity(readReviews);
            }
        });*/

         //Setup the button to update the Database with the favourite movies
     //TODO fix the favourite Button
        favouriteMovieButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                 //   favouriteMovieButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_black_24dp));
                    mDb.movieDao().updateMovie(movieID);
                } else {
                //    favouriteMovieButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_border_black_24dp));
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
     //   reviewsButton = findViewById(R.id.reviews_button);
        trailerButton = findViewById(R.id.trailers_button);
        favouriteMovieButton = findViewById(R.id.favourite_button);
    }

    private void populateUI(MovieEntry movie){
        if (movie == null){
            return;
        }

        Glide.with(this)
                .load(movie.getPosterPath())
                .into(img);

        txtMovieTitle.setText(movie.getTitle());
        txtReleasedDate.setText(movie.getReleaseDate());
        txtMovieSynopsis.setText(movie.getOverview());
        dbMovieAverage.setText(movie.getMovieRating());
        favouriteMovieButton.setChecked(movie.isChecked());

        if (movie.isChecked()) {
            favouriteMovieButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_black_24dp));
        }
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