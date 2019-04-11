package com.sun.kh_android_mvvm_databinding.screen.detail;

import android.os.Bundle;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.sun.kh_android_mvvm_databinding.BuildConfig;

public class VideoFragment extends YouTubePlayerSupportFragment
        implements YouTubePlayer.OnInitializedListener {
    private YouTubePlayer mYouTubePlayer;
    private String mVideoKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(BuildConfig.YOUTUBE_API_KEY, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mYouTubePlayer != null) mYouTubePlayer.release();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
            boolean restored) {
        mYouTubePlayer = player;
        mYouTubePlayer.loadVideo(mVideoKey);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
            YouTubeInitializationResult result) {
        mYouTubePlayer = null;
    }

    public void setVideoKey(String videoKey) {
        mVideoKey = videoKey;
        if (mYouTubePlayer != null) {
            mYouTubePlayer.cueVideo(videoKey);
        }
    }

    public void playVideo() {
        if (mYouTubePlayer != null) {
            mYouTubePlayer.play();
        }
    }

    public boolean isPlaying() {
        return mYouTubePlayer.isPlaying();
    }

    public String getVideoId() {
        return mVideoKey;
    }

    public void pause() {
        if (mYouTubePlayer != null) mYouTubePlayer.pause();
    }
}
