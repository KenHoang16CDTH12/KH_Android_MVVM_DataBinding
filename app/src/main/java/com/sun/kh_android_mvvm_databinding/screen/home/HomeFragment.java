package com.sun.kh_android_mvvm_databinding.screen.home;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sun.kh_android_mvvm_databinding.R;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;

public class HomeFragment extends Fragment implements HomeNavigator {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void startMoviesActivity(String id, String name, int getBy) {

    }

    @Override
    public void startMovieDetailActivity(Movie movie) {

    }

    @Override
    public void startSearchActivity() {

    }
}
