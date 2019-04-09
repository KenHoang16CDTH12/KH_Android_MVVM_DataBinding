package com.sun.kh_android_mvvm_databinding.data.repository;

import com.sun.kh_android_mvvm_databinding.data.model.GenreResponse;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.model.MovieResponse;
import com.sun.kh_android_mvvm_databinding.data.source.MovieDataSource;
import com.sun.kh_android_mvvm_databinding.data.source.local.LocalDataSource;
import com.sun.kh_android_mvvm_databinding.data.source.remote.RemoteDataSource;
import io.reactivex.Observable;
import java.util.List;

public class MovieRepository implements MovieDataSource.Local, MovieDataSource.Remote {
    private static MovieRepository sRepository;
    private LocalDataSource mLocal;
    private RemoteDataSource mRemote;

    public MovieRepository(LocalDataSource local, RemoteDataSource remote) {
        mLocal = local;
        mRemote = remote;
    }

    public static MovieRepository getInstance(LocalDataSource local, RemoteDataSource remote) {
        if (sRepository == null) sRepository = new MovieRepository(local, remote);
        return sRepository;
    }

    @Override
    public boolean insertToFavorite(Movie movie) {
        return mLocal.insertToFavorite(movie);
    }

    @Override
    public boolean deleteFromFavorite(int movieId) {
        return mLocal.deleteFromFavorite(movieId);
    }

    @Override
    public boolean isFavorite(int movieID) {
        return mLocal.isFavorite(movieID);
    }

    @Override
    public List<Movie> getFavoritesMovies() {
        return mLocal.getFavoritesMovies();
    }

    @Override
    public Observable<MovieResponse> getMoviesByCategory(String category, int page) {
        return mRemote.getMoviesByCategory(category, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByGenre(String genresId, int page) {
        return mRemote.getMoviesByGenre(genresId, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByCast(String castId, int page) {
        return mRemote.getMoviesByCast(castId, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByCompany(String company, int page) {
        return mRemote.getMoviesByCompany(company, page);
    }

    @Override
    public Observable<Movie> getMovieDetail(int movieId, String value) {
        return mRemote.getMovieDetail(movieId, value);
    }

    @Override
    public Observable<GenreResponse> getGenres() {
        return mRemote.getGenres();
    }

    @Override
    public Observable<MovieResponse> searchMovie(String input, int page) {
        return mRemote.searchMovie(input, page);
    }
}
