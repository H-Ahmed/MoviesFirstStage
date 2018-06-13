package com.example.android.moviesfirststage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.android.moviesfirststage.models.Movies;
import com.example.android.moviesfirststage.utilities.JsonUtils;
import com.example.android.moviesfirststage.utilities.NetworkUtils;
import java.net.URL;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pb_movies)
    ProgressBar mMoviesProgressBar;
    @BindView(R.id.gv_movies)
    GridView mMoviesGridView;
    @BindView(R.id.tv_error_message)
    TextView mErrorMessageTextView;

    private String orderBy;
    private static String API_KEY_VALUE;

    private Movies[] mMoviesData;
    private MoviesAdapter mAdapter;

    private static final String MOVIES_DATA_KEY = "moviesData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIES_DATA_KEY)) {
            mMoviesData = (Movies[]) savedInstanceState.getParcelableArray(MOVIES_DATA_KEY);
        }

        orderBy = getResources().getString(R.string.sort_by_popular);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        API_KEY_VALUE = getResources().getString(R.string.api_key);

        mAdapter = new MoviesAdapter(MainActivity.this);
        if (mMoviesData != null) {
            showMoviesData();
            mAdapter.setMoviesData(mMoviesData);
        } else {
            showErrorMessage();
            loadMovieData();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(MOVIES_DATA_KEY, mMoviesData);
    }


    private void loadMovieData() {
        showMoviesData();
        new FetchMoviesData().execute(orderBy);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.sort_by_popular) {
            orderBy = getResources().getString(R.string.sort_by_popular);
            loadMovieData();
            return true;
        }
        if (menuItemId == R.id.sort_by_top_rated) {
            orderBy = getResources().getString(R.string.sort_by_top_rated);
            loadMovieData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMoviesData() {
        mMoviesGridView.setVisibility(View.VISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mMoviesGridView.setAdapter(mAdapter);
    }

    private void showErrorMessage() {
        mMoviesGridView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    public class FetchMoviesData extends AsyncTask<String, Void, Movies[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mMoviesProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movies[] doInBackground(String... strings) {
            String orderValue = strings[0];
            URL moviesRequestURL = NetworkUtils.buildUrl(API_KEY_VALUE, orderValue);
            try {
                String jsonMoviesData = NetworkUtils.getResponseFromHttpUrl(moviesRequestURL);
                return JsonUtils.parseMoviesDataFromJson(jsonMoviesData);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final Movies[] moviesData) {
            mMoviesProgressBar.setVisibility(View.INVISIBLE);
            if (moviesData != null) {
                mMoviesData = moviesData;
                showMoviesData();
                mAdapter.setMoviesData(moviesData);
            } else {
                showErrorMessage();
            }
        }
    }

}
