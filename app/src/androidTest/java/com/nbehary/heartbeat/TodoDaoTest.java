package com.nbehary.heartbeat;

import android.content.Context;

import com.nbehary.heartbeat.data.AppDatabase;
import com.nbehary.heartbeat.data.TodoDao;
import com.nbehary.heartbeat.data.TodoItem;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class TodoDaoTest {
    private TodoDao todoDao;
    private AppDatabase db;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context,AppDatabase.class).build();
        todoDao = db.todoDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeAndReadTodoItem() throws Exception {
        TodoItem todoItem = new TodoItem("Exist",new Date(1977,3,8,7,3),false);
        todoDao.insert(todoItem);
        List<TodoItem> all = todoDao.getAll();
        TodoItem result = all.get(0);
        assertThat(result.getText(), equalTo("Exist"));
        assertThat(result.getDateTime(),equalTo(new Date(1977,3,8,7,3)));
        assertThat(result.isDone(),equalTo(false));
    }
}
