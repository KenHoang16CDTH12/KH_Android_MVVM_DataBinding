package com.sun.kh_android_mvvm_databinding.screen.trailer;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun.kh_android_mvvm_databinding.R;
import com.sun.kh_android_mvvm_databinding.data.model.Video;
import com.sun.kh_android_mvvm_databinding.databinding.ItemTrailerBinding;
import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private List<Video> mVideos;
    private OnClickVideoItemListener mListener;

    public TrailerAdapter(OnClickVideoItemListener listener) {
        mVideos = new ArrayList<>();
        mListener = listener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemTrailerBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_trailer, viewGroup, false);
        return new TrailerAdapter.TrailerViewHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int i) {
        trailerViewHolder.bindData(mVideos.get(i));
    }

    @Override
    public int getItemCount() {
        return mVideos != null ? mVideos.size() : 0;
    }

    public void update(List<Video> videos) {
        mVideos.clear();
        mVideos.addAll(videos);
        notifyDataSetChanged();
    }

    public static class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemTrailerBinding mBinding;
        private OnClickVideoItemListener mListener;
        private ItemTrailerViewModel mViewModel;

        public TrailerViewHolder(ItemTrailerBinding binding, OnClickVideoItemListener listener) {
            super(binding.getRoot());
            mBinding = binding;
            mListener = listener;
            mViewModel = new ItemTrailerViewModel();
            mBinding.youtubeThumbnail.setOnClickListener(this);
        }

        public void bindData(Video video) {
            mViewModel.video.set(video);
            mBinding.setItemVideoVM(mViewModel);
        }

        @Override
        public void onClick(View view) {
            mListener.onClickTrailer(mBinding.getItemVideoVM().video.get().getKey());
        }
    }

    public interface OnClickVideoItemListener {
        void onClickTrailer(String videoKey);
    }
}
