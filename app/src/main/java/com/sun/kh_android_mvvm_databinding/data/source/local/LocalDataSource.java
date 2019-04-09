package com.sun.kh_android_mvvm_databinding.data.source.local;

import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.source.MovieDataSource;
import java.util.List;

public class LocalDataSource implements MovieDataSource.Local  {
    private static LocalDataSource sLocal;
    private FavoriteReaderDBHelper mDBHelper;

    public LocalDataSource(FavoriteReaderDBHelper dbHelper) {
        mDBHelper = dbHelper;
    }

    public static LocalDataSource getInstance(FavoriteReaderDBHelper dbHelper) {
        if (sLocal == null)
            sLocal = new LocalDataSource(dbHelper);
        return sLocal;
    }

    @Override
    public boolean insertToFavorite(Movie movie) {
        return mDBHelper.addToFavorite(movie);
    }

    @Override
    public boolean deleteFromFavorite(int movieId) {
        return mDBHelper.removeFromFavorite(movieId);
    }

    @Override
    public boolean isFavorite(int movieID) {
        return mDBHelper.isFavoriteMovie(movieID);
    }

    @Override
    public List<Movie> getFavoritesMovies() {
        return mDBHelper.getMovies();
    }
}
