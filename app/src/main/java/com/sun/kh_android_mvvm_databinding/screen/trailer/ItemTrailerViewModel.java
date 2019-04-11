package com.sun.kh_android_mvvm_databinding.screen.trailer;

import android.databinding.ObservableField;
import com.sun.kh_android_mvvm_databinding.data.model.Video;

public class ItemTrailerViewModel {
    public final ObservableField<Video> video;

    public ItemTrailerViewModel(){
        video = new ObservableField<>();
    }
}
