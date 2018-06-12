package com.example.android.moviesfirststage.models;

public class Movies {

    private String mPosterPath;
    private String mId;

    public String getPosterPath() {
        return mPosterPath;
    }


    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public Movies(String posterPath, String id) {
        mId = id;
        mPosterPath = posterPath;

    }
}
