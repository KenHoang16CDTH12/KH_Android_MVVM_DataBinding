package com.sun.kh_android_mvvm_databinding.screen.producer;

import android.databinding.ObservableField;
import com.sun.kh_android_mvvm_databinding.data.model.Company;

public class ItemProducerViewModel {
    public final ObservableField<Company> company;

    public ItemProducerViewModel() {
        company = new ObservableField<>();
    }
}
