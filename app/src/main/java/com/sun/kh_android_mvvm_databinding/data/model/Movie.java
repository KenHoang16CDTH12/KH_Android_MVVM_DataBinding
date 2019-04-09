package com.sun.kh_android_mvvm_databinding.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Movie implements Parcelable {
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("genres")
    private List<Genre> mGenres;
    @SerializedName("videos")
    private VideoResponse mVideoResponse;
    @SerializedName("production_companies")
    private List<Company> mCompanies;
    @SerializedName("credits")
    private Credit mCredits;
    @SerializedName("id")
    private int mId;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("vote_average")
    private float mVoteAverage;
    @SerializedName("vote_count")
    private int mVoteCount;

    public Movie() {
    }

    public Movie(int id) {
        mId = id;
    }

    protected Movie(Parcel in) {
        mBackdropPath = in.readString();
        mGenres = in.createTypedArrayList(Genre.CREATOR);
        mCompanies = in.createTypedArrayList(Company.CREATOR);
        mCredits = in.readParcelable(Credit.class.getClassLoader());
        mId = in.readInt();
        mOverview = in.readString();
        mPosterPath = in.readString();
        mReleaseDate = in.readString();
        mTitle = in.readString();
        mStatus = in.readString();
        mVoteAverage = in.readFloat();
        mVoteCount = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public VideoResponse getVideoResponse() {
        return mVideoResponse;
    }

    public List<Company> getCompanies() {
        return mCompanies;
    }

    public Credit getCredits() {
        return mCredits;
    }

    public int getId() {
        return mId;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getStatus() {
        return mStatus;
    }

    public float getVoteAverage() {
        return mVoteAverage;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public float calculateVoteRating() {
        return (mVoteAverage / 2);
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setVoteAverage(float voteAverage) {
        mVoteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mBackdropPath);
        parcel.writeTypedList(mGenres);
        parcel.writeTypedList(mCompanies);
        parcel.writeParcelable(mCredits, i);
        parcel.writeInt(mId);
        parcel.writeString(mOverview);
        parcel.writeString(mPosterPath);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mTitle);
        parcel.writeString(mStatus);
        parcel.writeFloat(mVoteAverage);
        parcel.writeInt(mVoteCount);
    }
}
