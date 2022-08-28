package com.ekenozlu.dgpaysfinal.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ekenozlu.dgpaysfinal.activities.DetailsActivity;
import com.ekenozlu.dgpaysfinal.activities.SecondActivity;
import com.ekenozlu.dgpaysfinal.activities.ui.movies.HomeFragment;
import com.ekenozlu.dgpaysfinal.databinding.MoviesRecyclerrowBinding;
import com.ekenozlu.dgpaysfinal.models.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.movieVH>{

    private List<MovieModel> movieModelArrayList;

    public MovieAdapter(List<MovieModel> movieModelArrayList) {
        this.movieModelArrayList = movieModelArrayList;
    }

    @NonNull
    @Override
    public movieVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MoviesRecyclerrowBinding moviesRecyclerrowBinding = MoviesRecyclerrowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new movieVH(moviesRecyclerrowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull movieVH holder, int position) {
        Picasso.get().load("https://image.tmdb.org/t/p/original" + movieModelArrayList.get(position).getMovieImageURL()).into(holder.moviesRecyclerrowBinding.movieImage);
        holder.moviesRecyclerrowBinding.movieNameTextView.setText(movieModelArrayList.get(position).getMovieTitle());
        holder.moviesRecyclerrowBinding.movieRatingTextView.setText(movieModelArrayList.get(position).getMovieRating() + "/10");
        holder.moviesRecyclerrowBinding.movieReleaseDateTextView.setText(movieModelArrayList.get(position).getReleaseDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToDetail = new Intent(holder.itemView.getContext(),DetailsActivity.class);
                intentToDetail.putExtra("movieID",movieModelArrayList.get(position).getMovieID());
                holder.itemView.getContext().startActivity(intentToDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieModelArrayList.size();
    }

    public class movieVH extends RecyclerView.ViewHolder{
        MoviesRecyclerrowBinding moviesRecyclerrowBinding;
        public movieVH(MoviesRecyclerrowBinding moviesRecyclerrowBinding) {
            super(moviesRecyclerrowBinding.getRoot());
            this.moviesRecyclerrowBinding = moviesRecyclerrowBinding;
        }
    }
}
