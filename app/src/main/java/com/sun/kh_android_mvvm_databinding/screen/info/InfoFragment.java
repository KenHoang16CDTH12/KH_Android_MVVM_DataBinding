package com.sun.kh_android_mvvm_databinding.screen.info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun.kh_android_mvvm_databinding.databinding.FragmentInfoBinding;
import com.sun.kh_android_mvvm_databinding.screen.detail.MovieDetailViewModel;

public class InfoFragment extends Fragment {
    private FragmentInfoBinding mBinding;
    private MovieDetailViewModel mViewModel;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentInfoBinding.inflate(inflater, container, false);
        mBinding.setMovieVM(mViewModel);
        return mBinding.getRoot();
    }

    public void setViewModel(MovieDetailViewModel viewModel) {
        mViewModel = viewModel;
    }
}
