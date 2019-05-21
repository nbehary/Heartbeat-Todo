package com.nbehary.percipiatodo.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TodoItem.class},version = 1)
@TypeConverters(com.nbehary.percipiatodo.data.TypeConverters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TodoDao todoDao();
    private static AppDatabase ourInstance;

    public static AppDatabase getDatabase(final Context ctx) {
        if(ourInstance == null){
            ourInstance = Room.databaseBuilder(ctx.getApplicationContext(),
                    AppDatabase.class, "todo_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return ourInstance;
    }

}
