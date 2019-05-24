package com.nbehary.heartbeat.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo")
    LiveData<List<TodoItem>> getAll();

    @Insert(onConflict = REPLACE)
    void insert(TodoItem item);

    @Update(onConflict = REPLACE)
    void update(TodoItem item);

    @Query("DELETE FROM todo")
    void deleteAll();

    @Delete
    void delete(TodoItem item);

    @Query("SELECT * FROM todo WHERE id= :id")
    LiveData<TodoItem> findTodoById(Long id);

}
