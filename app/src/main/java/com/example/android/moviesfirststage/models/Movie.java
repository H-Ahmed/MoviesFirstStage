package com.example.android.moviesfirststage.models;

public class Movie {

    private String mOriginalTitle;
    private String mPosterPath;
    private String mOverView;
    private String mVoteAverage;
    private String mReleaseDate;

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getOverView() {
        return mOverView;
    }

    public void setOverView(String mOverView) {
        this.mOverView = mOverView;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public Movie() {

    }


}
