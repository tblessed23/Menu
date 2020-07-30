package com.example.android.popularmoviespractice.tables;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "favorites")
public class Favorites {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titleFavorites;


    @Ignore
    public Favorites(String titleFavorites) {
        this.titleFavorites = titleFavorites;
    }

    public Favorites(int id, String titleFavorites) {
        this.id = id;
        this.titleFavorites = titleFavorites;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleFavorites() {
        return titleFavorites;
    }

    public void setTitleFavorites(String title) {
        this.titleFavorites = titleFavorites;
    }
}