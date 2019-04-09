package com.sun.kh_android_mvvm_databinding.screen.home;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.view.View;
import android.widget.AdapterView;
import com.sun.kh_android_mvvm_databinding.data.annotation.CategoryRequest;
import com.sun.kh_android_mvvm_databinding.data.annotation.GenresKey;
import com.sun.kh_android_mvvm_databinding.data.model.Genre;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.repository.MovieRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseObservable {
    public final ObservableList<Movie> popularMoviesObservable;
    public final ObservableList<Movie> nowPlayingMoviesObservable;
    public final ObservableList<Movie> upComingMoviesObservable;
    public final ObservableList<Movie> topRateMoviesObservable;
    public final ObservableList<Movie> topTrendingMoviesObservable;
    public final ObservableList<Genre> genresObservable;
    public final ObservableList<Movie> genreMoviesObservable;
    public final ObservableList<ObservableList<Movie>> listCategoryMovies;
    public final ObservableList<String> categoryStringObservable;
    public final ObservableField<Genre> genreField;
    public final ObservableBoolean isLoadingSuccess;
    private static final int DEFAULT_PAGE = 1;
    private static final int FIRST_PAGE = 1;
    private static final int FIRST_INDEX = 0;
    private static final int LAST_INDEX = 5;
    public static final String TOP_RATED = "TOP RATE";
    public static final String NOW_PLAYING = "NOW PLAYING";
    public static final String POPULAR = "POPULAR";
    public static final String UPCOMING = "UPCOMING";
    private static final String TRENDING = "TRENDING";
    private MovieRepository mMovieRepository;
    private CompositeDisposable mCompositeDisposable;
    private HomeNavigator mNavigator;

    public HomeViewModel(HomeNavigator navigator, MovieRepository movieRepository) {
        mNavigator = navigator;
        mMovieRepository = movieRepository;
        genreField = new ObservableField<>();
        listCategoryMovies = new ObservableArrayList<>();
        categoryStringObservable = new ObservableArrayList<>();
        popularMoviesObservable = new ObservableArrayList<>();
        nowPlayingMoviesObservable = new ObservableArrayList<>();
        upComingMoviesObservable = new ObservableArrayList<>();
        topRateMoviesObservable = new ObservableArrayList<>();
        topTrendingMoviesObservable = new ObservableArrayList<>();
        genreMoviesObservable = new ObservableArrayList<>();
        genresObservable = new ObservableArrayList<>();
        isLoadingSuccess = new ObservableBoolean();
        mCompositeDisposable = new CompositeDisposable();
        initData();
    }

    private void initData() {
        loadTopTrendingMovies();
        loadPopularMovies();
        loadNowPlayingMovies();
        loadUpComingMovies();
        loadTopRateMovies();
        loadGenre();
    }

    public void onItemSpinnerSelected(AdapterView<?> parent, View view, int position, long id) {
        genreField.set(genresObservable.get(position));
        loadGenreMovies();
    }

    public void onGenreClick(Genre genre) {
        mNavigator.startMoviesActivity(String.valueOf(genre.getId()), genre.getName(),
                CategoryRequest.GENRE);
    }

    public void onSearchClick() {
        mNavigator.startSearchActivity();
    }

    public void onFloatingButtonClick() {
        mNavigator.startMoviesActivity(null, TRENDING, CategoryRequest.TRENDING);
    }

    public void onFavoriteImageClick(Movie movie) {
        int movieId = movie.getId();
        if (mMovieRepository.isFavorite(movieId)) {
            mMovieRepository.deleteFromFavorite(movieId);
            return;
        }
        mMovieRepository.insertToFavorite(movie);
    }

    @Bindable
    public ObservableList<ObservableList<Movie>> getListCategoryMovies() {
        return listCategoryMovies;
    }

    public void updateFavoriteMovie() {
        notifyPropertyChanged(BR.listCategoryMovies);
    }

    private void loadTopTrendingMovies() {
        Disposable disposable = mMovieRepository.getMoviesTrendingByDay()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onSuccess
                        movieResponse -> {
                            topTrendingMoviesObservable.addAll(
                                    movieResponse.getMovies().subList(FIRST_INDEX, LAST_INDEX));
                            listCategoryMovies.add(topTrendingMoviesObservable);
                            categoryStringObservable.add(TRENDING);
                        },
                        // onError
                        throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    private void loadPopularMovies() {
        Disposable disposable = mMovieRepository.getMoviesByCategory(GenresKey.POPULAR, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onSuccess
                        movieResponse -> {
                            popularMoviesObservable.addAll(
                                    movieResponse.getMovies().subList(FIRST_INDEX, LAST_INDEX));
                            listCategoryMovies.add(popularMoviesObservable);
                            categoryStringObservable.add(POPULAR);
                        },
                        // onError
                        throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    private void loadNowPlayingMovies() {
        Disposable disposable =
                mMovieRepository.getMoviesByCategory(GenresKey.NOW_PLAYING, FIRST_PAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // onSuccess
                                movieResponse -> {
                                    nowPlayingMoviesObservable.addAll(movieResponse.getMovies()
                                            .subList(FIRST_INDEX, LAST_INDEX));
                                    listCategoryMovies.add(nowPlayingMoviesObservable);
                                    categoryStringObservable.add(NOW_PLAYING);
                                },
                                // onError
                                throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    private void loadUpComingMovies() {
        Disposable disposable = mMovieRepository.getMoviesByCategory(GenresKey.UPCOMING, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onSuccess
                        movieResponse -> {
                            upComingMoviesObservable.addAll(
                                    movieResponse.getMovies().subList(FIRST_INDEX, LAST_INDEX));
                            listCategoryMovies.add(upComingMoviesObservable);
                            categoryStringObservable.add(UPCOMING);
                        }, throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    private void loadTopRateMovies() {
        Disposable disposable =
                mMovieRepository.getMoviesByCategory(GenresKey.TOP_RATED, FIRST_PAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // onSuccess
                                movieResponse -> {
                                    topRateMoviesObservable.addAll(movieResponse.getMovies()
                                            .subList(FIRST_INDEX, LAST_INDEX));
                                    listCategoryMovies.add(topRateMoviesObservable);
                                    categoryStringObservable.add(TOP_RATED);
                                },
                                // onError
                                throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    private void loadGenre() {
        Disposable disposable = mMovieRepository.getGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onSuccess
                        genreResponse -> {
                            genresObservable.addAll(genreResponse.getGenres());
                            loadGenreMovies();
                        },
                        // onError
                        throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    private void handleError(String message) {

    }

    private void loadGenreMovies() {
        Disposable disposable =
                mMovieRepository.getMoviesByGenre(String.valueOf(genreField.get().getId()),
                        DEFAULT_PAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                // onSuccess
                                movieResponse -> {
                                    genreMoviesObservable.clear();
                                    genreMoviesObservable.addAll(movieResponse.getMovies()
                                            .subList(FIRST_INDEX, LAST_INDEX));
                                },
                                // onError
                                throwable -> handleError(throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    public void dispose() {
        mCompositeDisposable.dispose();
    }
}
