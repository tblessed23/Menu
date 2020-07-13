package com.example.android.popularmoviespractice.tables;

import android.os.Parcel;
import android.os.Parcelable;




public class Reviews implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        public Reviews[] newArray(int size) {
            return new Reviews[0];
        }
    };


    //private int movieId;
    private String mAuthor;
    private String mContent;



    /**
     * No args constructor for use in serialization
     *
     * @param reviews
     */
    public Reviews(String reviews) {
    }

    //Regular Constructor

    public Reviews(int id, String author, String content) {

        this.mAuthor = author;
        this.mContent = content;
    }


    public Reviews(String author, String content) {
        this.mAuthor = author;
        //this.movieId = id;
        this.mContent = content;
    }

   // public int getId() {
     //   return movieId;
    //}

    //public void setId(int id) {
       // this.movieId = id;
    //}

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String author) {
        this.mAuthor = author;
    }

    public String getmContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }



    //Parceling constructor
    public Reviews(Parcel in) {
        //this.movieId = in.readInt();
        this.mAuthor = in.readString();
        this.mContent = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeInt(this.movieId);
        dest.writeString(this.mAuthor);
        dest.writeString(this.mContent);
    }
}

