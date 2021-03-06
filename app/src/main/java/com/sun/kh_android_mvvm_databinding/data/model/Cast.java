package com.sun.kh_android_mvvm_databinding.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Cast implements Parcelable {
    @SerializedName("cast_id")
    private int mCastId;
    @SerializedName("id")
    private int mId;
    @SerializedName("character")
    private String mCharacter;
    @SerializedName("gender")
    private String mGender;
    @SerializedName("profile_path")
    private String mProfilePath;
    @SerializedName("name")
    private String mName;

    protected Cast(Parcel in) {
        mCastId = in.readInt();
        mId = in.readInt();
        mCharacter = in.readString();
        mGender = in.readString();
        mProfilePath = in.readString();
        mName = in.readString();
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };

    public int getCastId() {
        return mCastId;
    }

    public int getId() {
        return mId;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public String getGender() {
        return mGender;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public String getName() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mCastId);
        parcel.writeInt(mId);
        parcel.writeString(mCharacter);
        parcel.writeString(mGender);
        parcel.writeString(mProfilePath);
        parcel.writeString(mName);
    }
}
