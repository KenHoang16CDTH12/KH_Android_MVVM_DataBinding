package com.sun.kh_android_mvvm_databinding.screen.detail;

public interface MovieDetailNavigator {
    void startSearchActivity();
    void onBackPress();
    void startMoviesActivity(int type, String name, String id);
}
