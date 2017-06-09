package com.example.farazahmed.paginationrecyclerview.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.farazahmed.paginationrecyclerview.Model.Movie;
import com.example.farazahmed.paginationrecyclerview.R;


import java.util.List;

/**
 * Created by FarazAhmed on 4/20/2017.
 */

public class MoviesAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Movie> movies;


    public MoviesAdaptor(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
        setHasStableIds(true);
    }


    public class Viewholder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;

        ImageView imageview;

        public Viewholder(View v) {
            super(v);

            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
            imageview = (ImageView) v.findViewById(R.id.movie_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item, parent, false);

        return new Viewholder(rootview);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Viewholder viewholder = (Viewholder) holder;

        Movie movie = movies.get(position);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).into(viewholder.imageview);
        viewholder.movieTitle.setText(movie.getTitle());
        viewholder.data.setText(movie.getReleaseDate());
        viewholder.movieDescription.setText(movie.getOverview());
        viewholder.rating.setText(movie.getVoteCount().toString());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
