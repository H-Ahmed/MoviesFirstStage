package com.example.android.moviesfirststage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.android.moviesfirststage.models.Movies;
import com.squareup.picasso.Picasso;


public class MoviesAdapter extends BaseAdapter {

    private Movies[] mMoviesData;
    private Context mContext;

    private LayoutInflater inflater;

    public MoviesAdapter(Context context) {
        mContext = context;
    }


    public void setMoviesData(Movies[] moviesData) {
        mMoviesData = moviesData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mMoviesData != null) {
            return mMoviesData.length;
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return mMoviesData[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View gridView = view;
        if (view == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.grid_view_item, null);
        }

        String posterPath = mMoviesData[i].getPosterPath();
        ImageView imageView = gridView.findViewById(R.id.iv_movie_poster);
        Picasso.get().load("http://image.tmdb.org/t/p/w185//" + posterPath).into(imageView);


        gridView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, mMoviesData[i].getId());
                mContext.startActivity(intent);
            }
        });


        return gridView;
    }
}
