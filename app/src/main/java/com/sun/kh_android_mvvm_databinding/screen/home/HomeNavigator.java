package com.sun.kh_android_mvvm_databinding.screen.home;

import com.sun.kh_android_mvvm_databinding.data.model.Movie;

public interface HomeNavigator {
    void startMoviesActivity(String id, String name, int getBy);

    void startMovieDetailActivity(Movie movie);

    void startSearchActivity();
}
