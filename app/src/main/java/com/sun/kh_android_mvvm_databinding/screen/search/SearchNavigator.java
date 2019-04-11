package com.sun.kh_android_mvvm_databinding.screen.search;

import com.sun.kh_android_mvvm_databinding.data.model.Movie;

public interface SearchNavigator {
    void startMovieDetailActivity(Movie movie);
    void onBackPress();
}
