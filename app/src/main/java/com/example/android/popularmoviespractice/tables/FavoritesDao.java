package com.example.android.popularmoviespractice.tables;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    List<Favorites> loadAllFavorites();

    @Insert
    void insertFavorites(Favorites favoriteEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorites(Favorites favoriteEntry);

    @Delete
    void deleteFavorites(Favorites favoriteEntry);
}
