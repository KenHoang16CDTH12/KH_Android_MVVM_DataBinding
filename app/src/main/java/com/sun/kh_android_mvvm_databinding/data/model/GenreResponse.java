package com.sun.kh_android_mvvm_databinding.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GenreResponse implements Parcelable {
    @SerializedName("genres")
    private List<Genre> mGenres;

    protected GenreResponse(Parcel in) {
        mGenres = in.createTypedArrayList(Genre.CREATOR);
    }

    public static final Creator<GenreResponse> CREATOR = new Creator<GenreResponse>() {
        @Override
        public GenreResponse createFromParcel(Parcel in) {
            return new GenreResponse(in);
        }

        @Override
        public GenreResponse[] newArray(int size) {
            return new GenreResponse[size];
        }
    };

    public List<Genre> getGenres() {
        return mGenres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mGenres);
    }
}
