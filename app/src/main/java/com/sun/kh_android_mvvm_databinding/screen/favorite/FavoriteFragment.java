package com.sun.kh_android_mvvm_databinding.screen.favorite;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import com.sun.kh_android_mvvm_databinding.R;
import com.sun.kh_android_mvvm_databinding.data.model.Movie;
import com.sun.kh_android_mvvm_databinding.data.repository.MovieRepository;
import com.sun.kh_android_mvvm_databinding.data.source.local.FavoriteReaderDBHelper;
import com.sun.kh_android_mvvm_databinding.data.source.local.LocalDataSource;
import com.sun.kh_android_mvvm_databinding.data.source.remote.RemoteDataSource;
import com.sun.kh_android_mvvm_databinding.databinding.FragmentFavoriteBinding;
import com.sun.kh_android_mvvm_databinding.screen.detail.MovieDetailActivity;
import com.sun.kh_android_mvvm_databinding.screen.home.CategoryAdapter;

public class FavoriteFragment extends Fragment implements CategoryAdapter.ViewHolder.MoviesAdapter.MovieItemClickListener,
        FavoriteNavigator {
    private FavoriteViewModel mViewModel;
    private FragmentFavoriteBinding mBinding;
    private RefreshHomeData mListener;
    private int mSelectedMovieId;

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    public void setListener(RefreshHomeData listener) {
        mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViewModel();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite,
                container, false);
        mBinding.setFavoriteVM(mViewModel);
        mBinding.recyclerFavorities.setAdapter(new CategoryAdapter.ViewHolder.MoviesAdapter(this));
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.refreshFavoriteMovies();
    }

    public void refreshMovies() {
        if (mViewModel != null)
            mViewModel.refreshFavoriteMovies();
    }

    private void initViewModel() {
        FavoriteReaderDBHelper dbHelper = new FavoriteReaderDBHelper(getContext());
        MovieRepository movieRepository = MovieRepository.getInstance(
                LocalDataSource.getInstance(dbHelper),
                RemoteDataSource.getInstance());
        mViewModel = new FavoriteViewModel(movieRepository);
    }

    @Override
    public void onMovieItemClick(Movie movie) {
        startMovieDetailActivity(movie);
    }

    @Override
    public void onFavoriteImageClick(Movie movie) {
        mSelectedMovieId = movie.getId();
        openDialog();
    }

    @Override
    public void startMovieDetailActivity(Movie movie) {
        startActivity(MovieDetailActivity.getIntent(getActivity(), movie));
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Are you sure??");
        Button buttonCancel = dialog.findViewById(R.id.button_cancel);
        Button buttonOk = dialog.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.deleteFavoriteMovie(mSelectedMovieId);
                mViewModel.refreshFavoriteMovies();
                mListener.onRefreshHomeFragment();
                dialog.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public interface RefreshHomeData {
        void onRefreshHomeFragment();
    }
}
