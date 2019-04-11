package com.sun.kh_android_mvvm_databinding.screen.movie;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.view.View;
import com.sun.kh_android_mvvm_databinding.data.annotation.CategoryRequest;
import com.sun.kh_android_mvvm_databinding.data.annotation.GenresKey;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.repository.MovieRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.sun.kh_android_mvvm_databinding.screen.home.HomeViewModel.NOW_PLAYING;
import static com.sun.kh_android_mvvm_databinding.screen.home.HomeViewModel.POPULAR;
import static com.sun.kh_android_mvvm_databinding.screen.home.HomeViewModel.TOP_RATED;
import static com.sun.kh_android_mvvm_databinding.screen.home.HomeViewModel.UPCOMING;

public class MovieViewModel extends BaseObservable {
    public final ObservableList<Movie> moviesObservable;
    public final ObservableBoolean isLoadingSuccess;
    private int mCurrentPage;
    private MovieRepository mMovieRepository;
    private CompositeDisposable mCompositeDisposable;
    private MovieNavigator mNavigator;

    public MovieViewModel(int type, String id, MovieRepository repository) {
        moviesObservable = new ObservableArrayList<>();
        mCompositeDisposable = new CompositeDisposable();
        isLoadingSuccess = new ObservableBoolean(true);
        mCurrentPage = 1;
        mMovieRepository = repository;
        loadMovies(type, id);
    }

    public void updateFavoriteMovies() {
        notifyChange();
    }

    public void setNavigator(MovieNavigator navigator) {
        mNavigator = navigator;
    }

    public void onFavoriteImageClick(Movie movie) {
        int movieId = movie.getId();
        if (mMovieRepository.isFavorite(movieId)) {
            mMovieRepository.deleteFromFavorite(movieId);
            return;
        }
        mMovieRepository.insertToFavorite(movie);
    }

    public void onBackPress(View view) {
        mNavigator.onBackPress();
    }

    public void startSearchActivity(View view) {
        mNavigator.startSearchActivity();
    }

    public void loadMovies(int type, String id) {
        switch (type) {
            case CategoryRequest.GENRE:
                loadMoviesByGenre(id);
                break;
            case CategoryRequest.COMPANY:
                loadMoviesByCompany(id);
                break;
            case CategoryRequest.CAST:
                loadMoviesByCast(id);
                break;
            case CategoryRequest.TRENDING:
                loadMoviesByTrending();
                break;
            case CategoryRequest.CATEGORY:
                loadMoviesByCategory(convertTitleToKey(id));
                break;
            default:
                break;
        }
    }

    private String convertTitleToKey(String string) {
        switch (string) {
            case TOP_RATED:
                return GenresKey.TOP_RATED;
            case NOW_PLAYING:
                return GenresKey.NOW_PLAYING;
            case UPCOMING:
                return GenresKey.UPCOMING;
            case POPULAR:
                return GenresKey.POPULAR;
            default:
                return null;
        }
    }

    private void loadMoviesByTrending() {
        Disposable disposable = mMovieRepository.getMoviesTrendingByDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    moviesObservable.clear();
                    moviesObservable.addAll(movieResponse.getMovies());
                    isLoadingSuccess.set(true);
                }, throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    private void loadMoviesByCast(String id) {
        Disposable disposable = mMovieRepository.getMoviesByCast(id, mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    moviesObservable.addAll(movieResponse.getMovies());
                    isLoadingSuccess.set(true);
                }, throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    private void loadMoviesByCompany(String id) {
        Disposable disposable = mMovieRepository.getMoviesByCompany(id, mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    moviesObservable.addAll(movieResponse.getMovies());
                    isLoadingSuccess.set(true);
                }, throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    private void loadMoviesByCategory(String id) {
        Disposable disposable = mMovieRepository.getMoviesByCategory(id, mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    moviesObservable.addAll(movieResponse.getMovies());
                    isLoadingSuccess.set(true);
                }, throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }


    private void loadMoviesByGenre(String id) {
        Disposable disposable = mMovieRepository.getMoviesByGenre(id, mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    moviesObservable.addAll(movieResponse.getMovies());
                    isLoadingSuccess.set(true);
                }, throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    public void dispose() {
        mCompositeDisposable.dispose();
    }

    public void increaseCurrentPage() {
        mCurrentPage++;
    }

    private void handleError(String message) {
    }
}
