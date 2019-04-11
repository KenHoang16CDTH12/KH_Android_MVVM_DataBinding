package com.sun.kh_android_mvvm_databinding.screen.movie;

import com.sun.kh_android_mvvm_databinding.data.model.Movie;

public interface MovieNavigator {
    void startMovieDetailActivity(Movie movie);
    void startSearchActivity();
    void onBackPress();
}
