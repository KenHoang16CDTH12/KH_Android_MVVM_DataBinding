package com.sun.kh_android_mvvm_databinding.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieResponse implements Parcelable {
    @SerializedName("results")
    private List<Movie> mMovies;
    @SerializedName("total_pages")
    private int mTotalPage;
    @SerializedName("total_results")
    private int mTotalResult;

    protected MovieResponse(Parcel in) {
        mMovies = in.createTypedArrayList(Movie.CREATOR);
        mTotalPage = in.readInt();
        mTotalResult = in.readInt();
    }

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    public List<Movie> getMovies() {
        return mMovies;
    }

    public int getTotalPage() {
        return mTotalPage;
    }

    public int getTotalResult() {
        return mTotalResult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mMovies);
        parcel.writeInt(mTotalPage);
        parcel.writeInt(mTotalResult);
    }
}
