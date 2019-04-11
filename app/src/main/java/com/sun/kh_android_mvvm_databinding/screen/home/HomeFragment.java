package com.sun.kh_android_mvvm_databinding.screen.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sun.kh_android_mvvm_databinding.R;
import com.sun.kh_android_mvvm_databinding.data.annotation.CategoryRequest;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.repository.MovieRepository;
import com.sun.kh_android_mvvm_databinding.data.source.local.FavoriteReaderDBHelper;
import com.sun.kh_android_mvvm_databinding.data.source.local.LocalDataSource;
import com.sun.kh_android_mvvm_databinding.data.source.remote.RemoteDataSource;
import com.sun.kh_android_mvvm_databinding.databinding.FragmentHomeBinding;
import com.sun.kh_android_mvvm_databinding.screen.detail.MovieDetailActivity;
import com.sun.kh_android_mvvm_databinding.screen.movie.MovieActivity;
import com.sun.kh_android_mvvm_databinding.screen.search.SearchActivity;

import static com.sun.kh_android_mvvm_databinding.screen.movie.MovieActivity.EXTRA_MOVIES_TITLE;
import static com.sun.kh_android_mvvm_databinding.screen.movie.MovieActivity.EXTRA_MOVIES_TYPE;
import static com.sun.kh_android_mvvm_databinding.screen.movie.MovieActivity.EXTRA_TYPE_ID;

public class HomeFragment extends Fragment implements HomeNavigator,
        SlideAdapter.TopTrendingClickListener, ViewPager.OnPageChangeListener,
        CategoryAdapter.CategoryClickListener,
        CategoryAdapter.ViewHolder.MoviesAdapter.MovieItemClickListener {
    public static final String EXTRA_MOVIE = "com.sun.kh_android_mvvm_databinding.screen.home.EXTRA_MOVIE";
    private HomeViewModel mHomeViewModel;
    private FragmentHomeBinding mHomeBinding;
    private SlideAdapter mAdapter;
    private NeedToRefreshListener mListener;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViewModel();
        mHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mHomeBinding.setViewModel(mHomeViewModel);
        mAdapter = new SlideAdapter(this);
        mHomeBinding.pagerImages.setAdapter(mAdapter);
        mHomeBinding.indicator.setupWithViewPager(mHomeBinding.pagerImages, true);
        mHomeBinding.pagerImages.addOnPageChangeListener(this);
        mHomeBinding.recyclerCategories.setAdapter(new CategoryAdapter(this));
        mHomeBinding.recyclerGenres.setAdapter(new CategoryAdapter.ViewHolder.MoviesAdapter(this));
        return mHomeBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mHomeViewModel.updateFavoriteMovie();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHomeViewModel.dispose();
    }

    private void initViewModel() {
        FavoriteReaderDBHelper dbHelper = new FavoriteReaderDBHelper(getContext());
        MovieRepository movieRepository = MovieRepository.getInstance(
                LocalDataSource.getInstance(dbHelper),
                RemoteDataSource.getInstance());
        mHomeViewModel = new HomeViewModel(this, movieRepository);
    }

    @Override
    public void startMoviesActivity(String id, String name, int getBy) {
        Intent intent = MovieActivity.getIntent(getActivity());
        intent.putExtra(EXTRA_MOVIES_TYPE, getBy);
        intent.putExtra(EXTRA_TYPE_ID, id);
        intent.putExtra(EXTRA_MOVIES_TITLE, name);
        startActivity(intent);
    }

    @Override
    public void startMovieDetailActivity(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(getActivity(), movie));
    }

    @Override
    public void startSearchActivity() {
        startActivity(SearchActivity.getIntent(getActivity()));
    }

    @Override
    public void onTopTrendingClick(Movie movie) {
        startMovieDetailActivity(movie);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        SlideAdapter.setsCurrentPosition(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCategoryClick(String type) {
        startMoviesActivity(type, type, CategoryRequest.CATEGORY);
    }

    @Override
    public void onMovieItemClick(Movie movie) {
        startMovieDetailActivity(movie);
    }

    @Override
    public void onFavoriteImageClick(Movie movie) {
        mHomeViewModel.onFavoriteImageClick(movie);
        mListener.refreshFavoriteFragment();
    }

    public void updateFavoriteMovie() {
        mHomeViewModel.updateFavoriteMovie();
    }

    public interface NeedToRefreshListener {
        void refreshFavoriteFragment();
    }

    public void setListener(NeedToRefreshListener listener) {
        mListener = listener;
    }
}
