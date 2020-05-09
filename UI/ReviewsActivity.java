package com.example.android.popularmovies.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.android.popularmovies.NetworkUtils;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Reviews;
import com.example.android.popularmovies.adapters.ReviewsAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsActivity extends AppCompatActivity {
    ReviewsAdapter mAdapter;
    RecyclerView recyclerView;
    String movieID;
    List<Reviews> reviewsList = new ArrayList<>();
    ReviewsAsyncTask asyncTask = new ReviewsAsyncTask();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Intent receivedIntent = getIntent();
        movieID = receivedIntent.getStringExtra("MovieID");

        recyclerView = findViewById(R.id.reviews_recyclerview);

        asyncTask.execute(movieID);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ReviewsAdapter(this,reviewsList);

        recyclerView.setAdapter(mAdapter);

    }

    public class ReviewsAsyncTask extends AsyncTask<String,Void,List<Reviews>> {

        @Override
        protected List<Reviews> doInBackground(String... strings) {
            String linkToFetch = strings[0];
            reviewsList = NetworkUtils.fetchReviewData(linkToFetch);
            return reviewsList;
        }

        @Override
        protected void onPostExecute(List<Reviews> reviews) {
            super.onPostExecute(reviews);
            reviewsList.addAll(reviews);
            mAdapter = new ReviewsAdapter(ReviewsActivity.this,reviewsList);
            recyclerView.setLayoutManager(new LinearLayoutManager(ReviewsActivity.this));
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

    }

}
