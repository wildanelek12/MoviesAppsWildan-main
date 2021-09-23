package com.wildan.moviesappswildan.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.wildan.moviesappswildan.Model.FavoriteMovies;

@Database(entities = {FavoriteMovies.class}, version = 1)
public abstract  class AppDatabase extends RoomDatabase {
    public abstract MoviesDAO moviesDAO();
}
