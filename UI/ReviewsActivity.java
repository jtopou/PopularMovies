package com.example.android.popularmovies.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.database.ReviewsEntry;
import com.example.android.popularmovies.adapters.ReviewsAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsActivity extends AppCompatActivity {

    //TODO Delete ReviewsActivity

    ReviewsAdapter mAdapter;
    RecyclerView recyclerView;
    String movieID;
    List<ReviewsEntry> reviewsList = new ArrayList<>();
/*
    ReviewsAsyncTask asyncTask = new ReviewsAsyncTask();
*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Intent receivedIntent = getIntent();
        movieID = receivedIntent.getStringExtra("MovieID");

        recyclerView = findViewById(R.id.reviews_recyclerview);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ReviewsAdapter(this,reviewsList);

        recyclerView.setAdapter(mAdapter);
    }



}
