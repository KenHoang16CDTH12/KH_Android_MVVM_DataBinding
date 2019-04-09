package com.sun.kh_android_mvvm_databinding.data.service;

import com.sun.kh_android_mvvm_databinding.data.model.GenreResponse;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.model.MovieResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {
    @GET("genre/movie/list")
    Observable<GenreResponse> getGenres();

    @GET("trending/movie/day")
    Observable<MovieResponse> getMoviesTrendingByDay();

    @GET("discover/movie")
    Observable<MovieResponse> getMoviesByGenre(@Query("with_genres") String grenreId,
            @Query("page") int page);

    @GET("discover/movie")
    Observable<MovieResponse> getMoviesByCast(@Query("with_cast") String castId,
            @Query("page") int page);

    @GET("discover/movie")
    Observable<MovieResponse> getMoviesByCompany(@Query("with_companies") String companyId,
            @Query("page") int page);

    @GET("movie/{type}")
    Observable<MovieResponse> getMoviesCategory(@Path("type") String type, @Query("page") int page);

    @GET("movie/{movie_id}")
    Observable<Movie> getMovieDetail(@Path("movie_id") int id,
            @Query("append_to_response") String value);

    @GET("search/{type}")
    Observable<MovieResponse> searchMovie(@Path("type") String type, @Query("query") String keyword,
            @Query("page") int page);
}
