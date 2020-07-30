package com.example.android.popularmoviespractice.tables;

import android.os.Parcel;
import android.os.Parcelable;

public class Trailers implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Trailers createFromParcel(Parcel in) {
            return new Trailers(in);
        }

        public Trailers[] newArray(int size) {
            return new Trailers[0];
        }
    };


    private String mKey;
    private String mSite;
    private String mType;


    /**
     * No args constructor for use in serialization
     *
     * @param trailers
     */
    public Trailers(String trailers) {
    }

    //Regular Constructor

    public Trailers(String key, String site, String type) {
        this.mKey = key;
        this.mSite = site;
        this.mType = type;
    }

    public String getmKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public String getmSite() {
        return mSite;
    }

    public void setSite(String site) {
        this.mSite = site;
    }

    public String getmType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    //Parceling constructor
    public Trailers(Parcel in) {
        this.mKey = in.readString();
        this.mSite = in.readString();
        this.mType = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mKey);
        dest.writeString(this.mSite);
        dest.writeString(this.mType);
    }
}
