package com.nbehary.heartbeat.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo ORDER BY date_time DESC")
    LiveData<List<TodoItem>> getAll();

    @Insert(onConflict = REPLACE)
    long insert(TodoItem item);

    @Update(onConflict = REPLACE)
    void update(TodoItem item);

    @Query("DELETE FROM todo")
    void deleteAll();

    @Delete
    void delete(TodoItem item);

    @Query("SELECT * FROM todo WHERE id= :id")
    LiveData<TodoItem> findTodoById(Long id);

    //TODO: This is only for testing and tests should use LiveData, probably
    @Query("SELECT * FROM todo WHERE id= :id")
    TodoItem findTodoByIdPlain(Long id);



}
