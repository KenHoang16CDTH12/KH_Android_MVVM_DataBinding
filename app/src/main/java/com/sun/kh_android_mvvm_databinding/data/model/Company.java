package com.sun.kh_android_mvvm_databinding.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Company implements Parcelable {
    @SerializedName("id")
    private int mId;
    @SerializedName("logo_path")
    private String mLogoPath;
    @SerializedName("name")
    private String mName;
    @SerializedName("origin_country")
    private String mOriginCountry;

    public Company(int id) {
        mId = id;
    }

    protected Company(Parcel in) {
        mId = in.readInt();
        mLogoPath = in.readString();
        mName = in.readString();
        mOriginCountry = in.readString();
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    public int getId() {
        return mId;
    }

    public String getLogoPath() {
        return mLogoPath;
    }

    public String getName() {
        return mName;
    }

    public String getOriginCountry() {
        return mOriginCountry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mLogoPath);
        parcel.writeString(mName);
        parcel.writeString(mOriginCountry);
    }
}
