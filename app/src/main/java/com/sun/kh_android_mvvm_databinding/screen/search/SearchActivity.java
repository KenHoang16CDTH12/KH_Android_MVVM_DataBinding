package com.sun.kh_android_mvvm_databinding.screen.search;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.sun.kh_android_mvvm_databinding.R;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.repository.MovieRepository;
import com.sun.kh_android_mvvm_databinding.data.source.local.FavoriteReaderDBHelper;
import com.sun.kh_android_mvvm_databinding.data.source.local.LocalDataSource;
import com.sun.kh_android_mvvm_databinding.data.source.remote.RemoteDataSource;
import com.sun.kh_android_mvvm_databinding.databinding.ActivitySearchBinding;
import com.sun.kh_android_mvvm_databinding.screen.detail.MovieDetailActivity;
import com.sun.kh_android_mvvm_databinding.screen.home.CategoryAdapter;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity
        implements CategoryAdapter.ViewHolder.MoviesAdapter.MovieItemClickListener,
        SearchNavigator {
    private ActivitySearchBinding mBinding;
    private SearchViewModel mViewModel;
    private String mInput = "";
    private String mType = "movie";
    private int currentItem, totalItem, scrollOutItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        initViewModel();
        mBinding.setSearchVM(mViewModel);
        mBinding.recyclerSearch.setAdapter(new CategoryAdapter.ViewHolder.MoviesAdapter(this));
        setupSearchView();
        setupScrollListener(mBinding.recyclerSearch);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.updateFavoriteMovie();
    }

    @Override
    public void onMovieItemClick(Movie movie) {
        startMovieDetailActivity(movie);
    }

    @Override
    public void onFavoriteImageClick(Movie movie) {
        mViewModel.onFavoriteImageClick(movie);
    }

    @Override
    public void startMovieDetailActivity(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(this, movie));
    }

    @Override
    public void onBackPress() {
        onBackPressed();
    }

    private void initViewModel() {
        FavoriteReaderDBHelper dbHelper = new FavoriteReaderDBHelper(this);
        MovieRepository movieRepository =
                MovieRepository.getInstance(LocalDataSource.getInstance(dbHelper),
                        RemoteDataSource.getInstance());
        mViewModel = new SearchViewModel(movieRepository);
        mViewModel.setNavigator(this);
    }

    private void setupSearchView() {
        final EditText editSearch = mBinding.editSearch;
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mInput = charSequence.toString().trim();
                mViewModel.searchMovie(mType, mInput);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void setupScrollListener(RecyclerView genresRecycler) {
        genresRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager =
                        (LinearLayoutManager) recyclerView.getLayoutManager();
                currentItem = Objects.requireNonNull(layoutManager).getChildCount();
                totalItem = layoutManager.getItemCount();
                scrollOutItem = layoutManager.findFirstVisibleItemPosition();
                if (currentItem + scrollOutItem == totalItem) {
                    mViewModel.increaseCurrentPage();
                    mViewModel.searchMovie(mType, mInput);
                }
            }
        });
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }
}
