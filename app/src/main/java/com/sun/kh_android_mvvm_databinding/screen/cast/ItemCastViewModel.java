package com.sun.kh_android_mvvm_databinding.screen.cast;

import android.databinding.ObservableField;
import com.sun.kh_android_mvvm_databinding.data.model.Cast;

public class ItemCastViewModel {
    public final ObservableField<Cast> cast;

    public ItemCastViewModel() {
        cast = new ObservableField<>();
    }
}
