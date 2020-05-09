package com.example.android.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.Reviews;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>{
    Context mContext;
    List<Reviews> mData;

    public ReviewsAdapter(Context mContext, List<Reviews> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater myInflater = LayoutInflater.from(mContext);
        view = myInflater.inflate(R.layout.review_list_item,parent,false);

        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
         holder.mReviewAuthor.setText(mData.get(position).getmReviewAuthor());
         holder.mReviewContent.setText(mData.get(position).getmReviewContent());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ReviewsViewHolder extends RecyclerView.ViewHolder{
        TextView mReviewAuthor;
        TextView mReviewContent;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);

            mReviewAuthor = itemView.findViewById(R.id.review_author);
            mReviewContent = itemView.findViewById(R.id.review_text);
        }
    }

}





