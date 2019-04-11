package com.sun.kh_android_mvvm_databinding.screen.trailer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun.kh_android_mvvm_databinding.databinding.FragmentTrailerBinding;
import com.sun.kh_android_mvvm_databinding.screen.detail.MovieDetailViewModel;

public class TrailerFragment extends Fragment implements TrailerAdapter.OnClickVideoItemListener {
    private FragmentTrailerBinding mBinding;
    private MovieDetailViewModel mViewModel;
    private OnTrailerSelectedListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnTrailerSelectedListener) context;
        } catch (ClassCastException e) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = FragmentTrailerBinding.inflate(inflater, container, false);
        mBinding.setMovieVM(mViewModel);
        mBinding.recyclerTrailer.setAdapter(new TrailerAdapter(this));
        return mBinding.getRoot();
    }

    public void setViewModel(MovieDetailViewModel viewModel) {
        mViewModel = viewModel;
    }

    public static TrailerFragment newInstance() {
        return new TrailerFragment();
    }

    @Override
    public void onClickTrailer(String videoKey) {
        mListener.onTrailerSelected(videoKey);
    }

    public interface OnTrailerSelectedListener {
        void onTrailerSelected(String videoKey);
    }
}

