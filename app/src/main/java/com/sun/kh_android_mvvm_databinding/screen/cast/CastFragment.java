package com.sun.kh_android_mvvm_databinding.screen.cast;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun.kh_android_mvvm_databinding.data.model.Cast;
import com.sun.kh_android_mvvm_databinding.databinding.FragmentCastBinding;
import com.sun.kh_android_mvvm_databinding.screen.detail.MovieDetailViewModel;

public class CastFragment extends Fragment implements ActorAdapter.OnClickActorListener {
    private MovieDetailViewModel mViewModel;
    private FragmentCastBinding mBinding;
    private OnActorSelectedListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnActorSelectedListener) context;
        } catch (ClassCastException e) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mBinding = FragmentCastBinding.inflate(inflater, container, false);
        mBinding.setMovieVM(mViewModel);
        mBinding.recyclerActor.setAdapter(new ActorAdapter(this));
        return mBinding.getRoot();
    }

    public static CastFragment newInstance() {
        return new CastFragment();
    }

    public void setViewModel(MovieDetailViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public void onClickActor(Cast cast) {
        mListener.onActorSelected(cast);
    }

    public interface OnActorSelectedListener {
        void onActorSelected(Cast cast);
    }
}
