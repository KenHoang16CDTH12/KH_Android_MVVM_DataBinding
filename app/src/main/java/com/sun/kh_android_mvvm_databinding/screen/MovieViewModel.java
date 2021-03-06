package com.sun.kh_android_mvvm_databinding.screen;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.repository.MovieRepository;
import com.sun.kh_android_mvvm_databinding.data.source.local.FavoriteReaderDBHelper;
import com.sun.kh_android_mvvm_databinding.data.source.local.LocalDataSource;
import com.sun.kh_android_mvvm_databinding.data.source.remote.RemoteDataSource;

public class MovieViewModel extends BaseObservable {
    public final ObservableBoolean isFavoriteMovie;
    private Movie mMovie;
    private Context mContext;
    private MovieRepository mMovieRepository;

    public MovieViewModel() {
        mMovie = new Movie();
        isFavoriteMovie = new ObservableBoolean();
    }

    public MovieViewModel(Context context) {
        mContext = context;
        mMovie = new Movie();
        isFavoriteMovie = new ObservableBoolean();
    }

    public int getId() {
        return mMovie.getId();
    }

    public String getBackdropPath() {
        return mMovie.getBackdropPath();
    }

    public String getPosterPath() {
        return mMovie.getPosterPath();
    }

    public String getTitle() {
        return mMovie.getTitle();
    }

    public String getReleaseDate() {
        return mMovie.getReleaseDate();
    }

    public String getStatus() {
        return mMovie.getStatus();
    }

    public double getVoteAverage() {
        return mMovie.getVoteAverage();
    }

    public String getOverview() {
        return mMovie.getOverview();
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        isFavoriteMovie.set(checkFavorite());
        notifyChange();
    }

    public Movie getMovie() {
        return mMovie;
    }

    public boolean checkFavorite() {
        FavoriteReaderDBHelper dbHelper = new FavoriteReaderDBHelper(mContext);
        mMovieRepository = MovieRepository.getInstance(
                LocalDataSource.getInstance(dbHelper),
                RemoteDataSource.getInstance()
        );
        isFavoriteMovie.set(mMovieRepository.isFavorite(mMovie.getId()));
        return isFavoriteMovie.get();
    }
}
