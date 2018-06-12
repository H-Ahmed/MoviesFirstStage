package com.example.android.moviesfirststage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviesfirststage.models.Movies;
import com.example.android.moviesfirststage.utilities.JsonUtils;
import com.example.android.moviesfirststage.utilities.NetworkUtils;

import java.net.URL;
import java.util.Arrays;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.containsKey("orderByKey")){
            orderBy = savedInstanceState.getString("orderByKey");
        }else {
            orderBy = getResources().getString(R.string.sort_by_popular);
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        API_KEY_VALUE = getResources().getString(R.string.api_key);

        loadMovieData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("orderByKey", orderBy);
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
            Toast.makeText(MainActivity.this, orderBy, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (menuItemId == R.id.sort_by_top_rated) {
            orderBy = getResources().getString(R.string.sort_by_top_rated);
            loadMovieData();
            Toast.makeText(MainActivity.this, orderBy, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMoviesData() {
        mMoviesGridView.setVisibility(View.VISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }

    public void showErrorMessage() {
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
                showMoviesData();
                mMoviesData = moviesData;
                mAdapter = new MoviesAdapter(MainActivity.this, Arrays.asList(mMoviesData));
                mMoviesGridView.setAdapter(mAdapter);
                mMoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                        intent.putExtra(Intent.EXTRA_TEXT, moviesData[i].getId());
                        startActivity(intent);
                    }
                });
            } else {
                showErrorMessage();
            }
        }
    }

}
