package com.sun.kh_android_mvvm_databinding.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Credit implements Parcelable {
    @SerializedName("cast")
    private List<Cast> mCasts;

    protected Credit(Parcel in) {
        mCasts = in.createTypedArrayList(Cast.CREATOR);
    }

    public static final Creator<Credit> CREATOR = new Creator<Credit>() {
        @Override
        public Credit createFromParcel(Parcel in) {
            return new Credit(in);
        }

        @Override
        public Credit[] newArray(int size) {
            return new Credit[size];
        }
    };

    public List<Cast> getCasts() {
        return mCasts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mCasts);
    }
}
