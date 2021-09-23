package com.wildan.moviesappswildan.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.wildan.moviesappswildan.Model.FavoriteMovies;

import java.util.List;

@Dao
public interface MoviesDAO {
    @Query("SELECT * FROM favoriteMovies")
    List<FavoriteMovies> getAll();
    @Insert
    void insertAll(FavoriteMovies favoriteMovies);
    @Query("SELECT EXISTS(SELECT * FROM favoriteMovies WHERE id = :userId)")
    boolean isRecordExistsUserId(int userId);
    @Query("DELETE FROM favoriteMovies WHERE id =:id ")
    void hapus(int id);
}
