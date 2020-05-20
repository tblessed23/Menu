package com.example.android.popularmoviespractice;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return new Movies[0];
        }
    };

    private String mTitle;
    private String mReleaseDate;
    private String mUserRating;
    private String mSynopsis;
    private String mImage;



    /**
     * No args constructor for use in serialization
     * @param movies
     */
    public Movies(String movies) {
    }

    //Regular Constructor
    public Movies (String title, String releasedate, String userrating, String synopsis){
        this.mTitle = title;
        this.mReleaseDate = releasedate;
        this.mUserRating = userrating;
        this.mSynopsis = synopsis;
    }



    public Movies(String title, String releasedate, String userrating, String synopsis, String image) {
        this.mTitle = title;
        this.mReleaseDate = releasedate;
        this.mUserRating = userrating;
        this.mSynopsis = synopsis;
        this.mImage = image;

    }

    public String getmTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setReleasedate(String releasedate) {
        this.mReleaseDate = releasedate;
    }

    public String getmUserRating() {
        return mUserRating;
    }

    public void setUserrating(String userrating) {
        this.mUserRating = userrating;
    }

    public String getmSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        this.mSynopsis = synopsis;
    }

    public String getmImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }



    //Parceling constructor
    public Movies (Parcel in) {
        this.mTitle = in.readString();
        this.mReleaseDate = in.readString();
        this.mUserRating = in.readString();
        this.mSynopsis = in.readString();
        this.mImage = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mUserRating);
        dest.writeString(this.mSynopsis);
       dest.writeString(this.mImage);

    }

//    @Override
//    public String toString() {
//       return "Movies{" +
//                "title='" + mTitle + '\'' +
//                ", releasedate='" + mReleaseDate + '\'' +
//                ", userrating='" + mUserRating + '\'' +
//               ", synopsis='" + mSynopsis +'\'' +
//                '}';
//    }
}
