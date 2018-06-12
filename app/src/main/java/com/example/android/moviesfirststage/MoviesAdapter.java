package com.example.android.moviesfirststage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.example.android.moviesfirststage.models.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends ArrayAdapter<Movies> {

    public MoviesAdapter(Context context, List<Movies> movies) {
        super(context, 0, movies);
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final Movies movies = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.grid_view_item, viewGroup, false);
        }


        String posterPath = movies.getPosterPath();
        ImageView imageView = view.findViewById(R.id.iv_movie_poster);
        Picasso.get().load("http://image.tmdb.org/t/p/w185//" + posterPath).into(imageView);


        return view;
    }
}
