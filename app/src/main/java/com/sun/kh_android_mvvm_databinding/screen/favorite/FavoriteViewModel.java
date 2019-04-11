package com.sun.kh_android_mvvm_databinding.screen.favorite;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.repository.MovieRepository;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class FavoriteViewModel {

    private MovieRepository mMovieRepository;
    private CompositeDisposable mCompositeDisposable;

    public final ObservableList<Movie> favoriteMoviesObservable;

    public FavoriteViewModel(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
        mCompositeDisposable = new CompositeDisposable();
        favoriteMoviesObservable = new ObservableArrayList<>();
        loadFavoriteMovies();
    }

    private void loadFavoriteMovies() {
        List<Movie> movies = mMovieRepository.getFavoritesMovies();
        favoriteMoviesObservable.clear();
        favoriteMoviesObservable.addAll(movies);
    }

    public void refreshFavoriteMovies() {
        favoriteMoviesObservable.clear();
        loadFavoriteMovies();
    }

    public boolean deleteFavoriteMovie(int movieId) {
        return mMovieRepository.deleteFromFavorite(movieId);
    }

    public void dispose() {
        mCompositeDisposable.dispose();
    }

    private void handleError(String message) {
    }
}
