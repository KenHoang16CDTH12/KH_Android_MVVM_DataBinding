package com.sun.kh_android_mvvm_databinding.screen.producer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun.kh_android_mvvm_databinding.data.model.Company;
import com.sun.kh_android_mvvm_databinding.databinding.FragmentProducerBinding;
import com.sun.kh_android_mvvm_databinding.screen.detail.MovieDetailViewModel;

public class ProducerFragment extends Fragment implements ProducerAdapter.OnClickProducerListener {
    private FragmentProducerBinding mBinding;
    private MovieDetailViewModel mViewModel;
    private OnProducerSelectedListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnProducerSelectedListener) context;
        } catch (ClassCastException e) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentProducerBinding.inflate(inflater, container, false);
        mBinding.setMovieVM(mViewModel);
        mBinding.recyclerProducer.setAdapter(new ProducerAdapter(this));
        return mBinding.getRoot();
    }

    @Override
    public void onClickProducer(Company company) {
        mListener.onProducerSelected(company);
    }

    public static ProducerFragment newInstance() {
        return new ProducerFragment();
    }

    public void setViewModel(MovieDetailViewModel viewModel) {
        mViewModel = viewModel;
    }

    public interface OnProducerSelectedListener{
        void onProducerSelected(Company company);
    }
}
