package com.sun.kh_android_mvvm_databinding.screen.home;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun.kh_android_mvvm_databinding.R;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.databinding.LayoutSlideMoviesBinding;
import com.sun.kh_android_mvvm_databinding.screen.MovieViewModel;
import java.util.List;

public class SlideAdapter extends PagerAdapter implements View.OnClickListener {
    private ObservableList<Movie> mMovies;
    private static int sCurrentPosition;
    private TopTrendingClickListener mListener;

    public SlideAdapter(TopTrendingClickListener listener) {
        mListener = listener;
        mMovies = new ObservableArrayList<>();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutSlideMoviesBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(container.getContext()),
                R.layout.layout_slide_movies, container, true);
        if (binding.getViewModel() == null)
            binding.setViewModel(new MovieViewModel());
        binding.getViewModel().setMovie(mMovies.get(position));
        binding.imageSlide.setOnClickListener(this);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    public void update(List<Movie> movies) {
        mMovies.clear();
        mMovies.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        mListener.onTopTrendingClick(mMovies.get(sCurrentPosition));
    }

    public interface TopTrendingClickListener {
        void onTopTrendingClick(Movie movie);
    }

    public static void setsCurrentPosition(int position) {
        sCurrentPosition = position;
    }
}
