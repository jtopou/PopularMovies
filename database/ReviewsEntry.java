package com.example.android.popularmovies.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reviews")
public class ReviewsEntry {
    @PrimaryKey @NonNull
    String mReviewAuthor;
    String mReviewContent;

    public ReviewsEntry(String mReviewAuthor, String mReviewContent) {
        this.mReviewAuthor = mReviewAuthor;
        this.mReviewContent = mReviewContent;
    }

    public String getmReviewAuthor() {
        return mReviewAuthor;
    }

    public void setmReviewAuthor(String mReviewAuthor) {
        this.mReviewAuthor = mReviewAuthor;
    }

    public String getmReviewContent() {
        return mReviewContent;
    }

    public void setmReviewContent(String mReviewContent) {
        this.mReviewContent = mReviewContent;
    }
}
