package com.sun.kh_android_mvvm_databinding.data.source.remote;

import com.sun.kh_android_mvvm_databinding.data.model.GenreResponse;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.model.MovieResponse;
import com.sun.kh_android_mvvm_databinding.data.service.ApiRequest;
import com.sun.kh_android_mvvm_databinding.data.service.MovieDBClient;
import com.sun.kh_android_mvvm_databinding.data.source.MovieDataSource;
import io.reactivex.Observable;

public class RemoteDataSource implements MovieDataSource.Remote {
    private static RemoteDataSource sRemote;
    private ApiRequest mRequest;

    private RemoteDataSource(MovieDBClient client) {
        mRequest = client.initRetrofitRequest();
    }

    public static RemoteDataSource getInstance() {
        if (sRemote == null) sRemote = new RemoteDataSource(MovieDBClient.getInstance());
        return sRemote;
    }

    @Override
    public Observable<MovieResponse> getMoviesByCategory(String category, int page) {
        return mRequest.getMoviesCategory(category, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByGenre(String genresId, int page) {
        return mRequest.getMoviesByGenre(genresId, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByCast(String castId, int page) {
        return mRequest.getMoviesByCast(castId, page);
    }

    @Override
    public Observable<MovieResponse> getMoviesByCompany(String company, int page) {
        return mRequest.getMoviesByCompany(company, page);
    }

    @Override
    public Observable<Movie> getMovieDetail(int movieId, String value) {
        return mRequest.getMovieDetail(movieId, value);
    }

    @Override
    public Observable<GenreResponse> getGenres() {
        return mRequest.getGenres();
    }

    @Override
    public Observable<MovieResponse> searchMovie(String input, int page) {
        return mRequest.searchMovie("movie", input, page);
    }
}
