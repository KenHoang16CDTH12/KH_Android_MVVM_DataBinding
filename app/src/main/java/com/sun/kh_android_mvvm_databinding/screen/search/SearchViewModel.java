package com.sun.kh_android_mvvm_databinding.screen.search;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.view.View;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.model.MovieResponse;
import com.sun.kh_android_mvvm_databinding.data.repository.MovieRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends BaseObservable {
    private MovieRepository mRepository;
    private SearchNavigator mNavigator;
    private int mCurrentPage;
    private CompositeDisposable mCompositeDisposable;
    public final ObservableList<Movie> moviesObservable;
    public final ObservableInt totalResults;
    public final ObservableBoolean isLoadingSuccess;

    public SearchViewModel(MovieRepository repository) {
        mRepository = repository;
        moviesObservable = new ObservableArrayList<>();
        isLoadingSuccess = new ObservableBoolean(true);
        mCompositeDisposable = new CompositeDisposable();
        totalResults = new ObservableInt();
        mCurrentPage = 1;
    }

    public void updateFavoriteMovie() {
        notifyChange();
    }

    public void setNavigator(SearchNavigator navigator) {
        mNavigator = navigator;
    }

    public void increaseCurrentPage() {
        mCurrentPage++;
    }

    public void searchMovie(String type, String input) {
        if (input.length() == 0) {
            clearData();
            return;
        }
        isLoadingSuccess.set(false);
        Disposable disposable = mRepository.searchMovie(input, mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponse -> {
                    if (!isLoadingSuccess.get())
                        getData(movieResponse);
                }, throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    public void clearData() {
        moviesObservable.clear();
        mCurrentPage = 1;
        totalResults.set(0);
        isLoadingSuccess.set(true);
    }

    public void onFavoriteImageClick(Movie movie) {
        int movieId = movie.getId();
        if (mRepository.isFavorite(movieId)) {
            mRepository.deleteFromFavorite(movieId);
            return;
        }
        mRepository.insertToFavorite(movie);
    }

    public void onBackPress(View view) {
        mNavigator.onBackPress();
    }

    private void getData(MovieResponse movieResponse) {
        totalResults.set(movieResponse.getTotalResult());
        if (mCurrentPage > 1) {
            moviesObservable.addAll(movieResponse.getMovies());
            isLoadingSuccess.set(true);
            return;
        }
        moviesObservable.clear();
        moviesObservable.addAll(movieResponse.getMovies());
        isLoadingSuccess.set(true);
    }

    private void handleError(String msg) {
        clearData();
    }
}
