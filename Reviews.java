package com.example.android.popularmovies;

public class Reviews {
    String mReviewAuthor,mReviewContent;

    public Reviews(String mReviewAuthor, String mReviewContent) {
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
