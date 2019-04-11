package com.sun.kh_android_mvvm_databinding.screen.movie;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.sun.kh_android_mvvm_databinding.R;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.repository.MovieRepository;
import com.sun.kh_android_mvvm_databinding.data.source.local.FavoriteReaderDBHelper;
import com.sun.kh_android_mvvm_databinding.data.source.local.LocalDataSource;
import com.sun.kh_android_mvvm_databinding.data.source.remote.RemoteDataSource;
import com.sun.kh_android_mvvm_databinding.databinding.ActivityMovieBinding;
import com.sun.kh_android_mvvm_databinding.screen.detail.MovieDetailActivity;
import com.sun.kh_android_mvvm_databinding.screen.home.CategoryAdapter;
import com.sun.kh_android_mvvm_databinding.screen.search.SearchActivity;

public class MovieActivity extends AppCompatActivity implements CategoryAdapter.ViewHolder.MoviesAdapter.MovieItemClickListener,
        MovieNavigator  {
    public static final String EXTRA_MOVIES_TYPE = "com.sun.kh_android_mvvm_databinding.screen.movie.MovieActivity.EXTRA_MOVIES_TYPE";
    public static final String EXTRA_TYPE_ID = "com.sun.kh_android_mvvm_databinding.screen.movie.MovieActivity.EXTRA_TYPE_ID";
    public static final String EXTRA_MOVIES_TITLE = "com.sun.kh_android_mvvm_databinding.screen.movie.MovieActivity.EXTRA_MOVIES_TITLE";
    private MovieViewModel mViewModel;
    private ActivityMovieBinding mBinding;
    private String id;
    private int currentItem, totalItem, scrollOutItem, type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        mBinding.setMoviesVM(mViewModel);
        mBinding.recyclerMovies.setAdapter(new CategoryAdapter.ViewHolder.MoviesAdapter(this));
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_MOVIES_TITLE));
        Glide.with(this)
                .load(getResources().getDrawable(R.drawable.movies_background))
                .into(mBinding.imageBackgroundAppBar);
        setUpLoadMore(mBinding.recyclerMovies);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.updateFavoriteMovies();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.dispose();
    }


    @Override
    public void startMovieDetailActivity(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(this, movie));
    }

    @Override
    public void startSearchActivity() {
        startActivity(SearchActivity.getIntent(this));
    }

    @Override
    public void onBackPress() {
        onBackPressed();
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MovieActivity.class);
    }

    @Override
    public void onMovieItemClick(Movie movie) {
        startMovieDetailActivity(movie);
    }

    @Override
    public void onFavoriteImageClick(Movie movie) {
        mViewModel.onFavoriteImageClick(movie);
    }

    private void setUpLoadMore(RecyclerView genresRecycler) {
        genresRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                currentItem = layoutManager.getChildCount();
                totalItem = layoutManager.getItemCount();
                scrollOutItem = layoutManager.findFirstVisibleItemPosition();
                if (currentItem + scrollOutItem == totalItem) {
                    mViewModel.isLoadingSuccess.set(false);
                    mViewModel.increaseCurrentPage();
                    mViewModel.loadMovies(type, id);
                }
            }
        });
    }

    private void initViewModel() {
        FavoriteReaderDBHelper dbHelper = new FavoriteReaderDBHelper(this);
        type = getIntent().getIntExtra(EXTRA_MOVIES_TYPE, 0);
        id = getIntent().getStringExtra(EXTRA_TYPE_ID);
        MovieRepository movieRepository = MovieRepository.getInstance(
                LocalDataSource.getInstance(dbHelper),
                RemoteDataSource.getInstance());
        mViewModel = new MovieViewModel(type, id, movieRepository);
        mViewModel.setNavigator(this);
    }
}
