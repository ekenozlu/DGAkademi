package com.ekenozlu.dgpaysfinal.models;

import com.google.gson.annotations.SerializedName;

public class MovieModel {

    private int movieID;
    private String movieImageURL;
    private String movieTitle;
    private double movieRating;
    private String releaseDate;

    public MovieModel(){}

    public MovieModel(int movieID,String movieImageURL, String movieTitle, long movieRating,String releaseDate) {
        this.movieID = movieID;
        this.movieImageURL = movieImageURL;
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.releaseDate = releaseDate;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieImageURL() {
        return movieImageURL;
    }

    public void setMovieImageURL(String movieImageURL) {
        this.movieImageURL = movieImageURL;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.movieRating = movieRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
