package com.sun.kh_android_mvvm_databinding.data.source;

import com.sun.kh_android_mvvm_databinding.data.model.GenreResponse;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.model.MovieResponse;
import io.reactivex.Observable;
import java.util.List;

public interface MovieDataSource {
    interface Local {
        boolean insertToFavorite(Movie movie);

        boolean deleteFromFavorite(int movieId);

        boolean isFavorite(int movieID);

        List<Movie> getFavoritesMovies();
    }

    interface Remote {
        Observable<MovieResponse> getMoviesByCategory(String category, int page);

        Observable<MovieResponse> getMoviesByGenre(String genresId, int page);

        Observable<MovieResponse> getMoviesByCast(String castId, int page);

        Observable<MovieResponse> getMoviesByCompany(String company, int page);

        Observable<MovieResponse> getMoviesTrendingByDay();

        Observable<Movie> getMovieDetail(int movieId, String value);

        Observable<GenreResponse> getGenres();

        Observable<MovieResponse> searchMovie(String input, int page);
    }
}
