package com.example.android.moviesfirststage.utilities;

import com.example.android.moviesfirststage.models.Movie;
import com.example.android.moviesfirststage.models.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {
    private static final String RESULTS = "results";
    private static final String MOVIE_ID = "id";
    private static final String POSTER_PATH = "poster_path";

    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String RELEASE_DATE = "release_date";

    public static Movies[] parseMoviesDataFromJson(String jsonData) throws JSONException {
        JSONObject moviesData = new JSONObject(jsonData);
        JSONArray moviesArray = moviesData.getJSONArray(RESULTS);
        Movies[] parseMoviesData = new Movies[moviesArray.length()];
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieData = moviesArray.getJSONObject(i);
            String movieId = movieData.getString(MOVIE_ID);
            String moviePosterPath = movieData.getString(POSTER_PATH);
            parseMoviesData[i] = new Movies(moviePosterPath, movieId);
        }
        return parseMoviesData;
    }

    public static Movie parseMovieDataFromJson(String jsonData) throws JSONException {
        Movie movie = new Movie();
        JSONObject movieData = new JSONObject(jsonData);

        String originalTitle = movieData.getString(ORIGINAL_TITLE);
        movie.setOriginalTitle(originalTitle);

        String posterImagePath = movieData.getString(POSTER_PATH);
        movie.setPosterPath(posterImagePath);

        String overview = movieData.getString(OVERVIEW);
        movie.setOverView(overview);

        String voteAverage = movieData.getString(VOTE_AVERAGE);
        movie.setVoteAverage(voteAverage);

        String releaseDate = movieData.getString(RELEASE_DATE);
        movie.setReleaseDate(releaseDate);

        return movie;
    }

}
