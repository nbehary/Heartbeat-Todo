package com.nbehary.heartbeat.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;

@Database(entities = {TodoItem.class},version = 1)
@TypeConverters(com.nbehary.heartbeat.data.TypeConverters.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TodoDao todoDao();
    private static AppDatabase ourInstance;

    public static AppDatabase getDatabase(final Context ctx) {
        if(ourInstance == null){
            ourInstance = Room.databaseBuilder(ctx.getApplicationContext(),
                    AppDatabase.class, "todo_database")
                    .fallbackToDestructiveMigration()
//                    .addCallback(sRoomDatabaseCallback)
                    .build();
        }
        return ourInstance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(ourInstance).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TodoDao todoDao;

        PopulateDbAsync(AppDatabase db) {
            todoDao = db.todoDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            todoDao.deleteAll();
            TodoItem item = new TodoItem("Task 1",new Date(119,1,1,7,55),false);
            todoDao.insert(item);
            item = new TodoItem("Task 2",new Date(112,2,12,5,23),false);
            todoDao.insert(item);
            return null;
        }
    }

}
