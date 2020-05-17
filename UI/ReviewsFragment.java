package com.example.android.popularmovies.UI;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmovies.Utils.GlobalApplication;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Utils.NetworkUtils;
import com.example.android.popularmovies.database.ReviewsEntry;
import com.example.android.popularmovies.adapters.ReviewsAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsFragment extends Fragment {
    String movieID;
    List<ReviewsEntry> reviewsList = new ArrayList<>();
    ReviewsAsyncTask asyncTask = new ReviewsAsyncTask();

    RecyclerView recyclerView;
    ReviewsAdapter reviewsAdapter;
    List<ReviewsEntry> mReviews = new ArrayList<>();
    Context mContext = GlobalApplication.getAppContext();
    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieID=MovieActivity.getMovieID();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.reviews_fragment, container, false);
        recyclerView = v.findViewById(R.id.reviews_recyclerview);

        asyncTask.execute(movieID);

        reviewsAdapter = new ReviewsAdapter(mContext, mReviews);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(reviewsAdapter);

        return v;
    }

    public class ReviewsAsyncTask extends AsyncTask<String,Void,List<ReviewsEntry>> {

        @Override
        protected List<ReviewsEntry> doInBackground(String... strings) {
            String linkToFetch = strings[0];
            reviewsList = NetworkUtils.fetchReviewData(linkToFetch);
            return reviewsList;
        }

        @Override
        protected void onPostExecute(List<ReviewsEntry> reviews) {
            super.onPostExecute(reviews);
            reviewsList.addAll(reviews);
            reviewsAdapter = new ReviewsAdapter(mContext,reviewsList);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(reviewsAdapter);
            reviewsAdapter.notifyDataSetChanged();
        }

    }
}
