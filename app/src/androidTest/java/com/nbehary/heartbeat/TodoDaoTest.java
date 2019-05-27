package com.nbehary.heartbeat;

import android.content.Context;
import android.util.Log;

import com.nbehary.heartbeat.data.AppDatabase;
import com.nbehary.heartbeat.data.TodoDao;
import com.nbehary.heartbeat.data.TodoItem;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;



@RunWith(AndroidJUnit4.class)
public class TodoDaoTest {
    private TodoDao todoDao;
    private AppDatabase db;
    private Context context;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    @Before
    public void createDb(){
        context = ApplicationProvider.getApplicationContext();
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
        long id = todoDao.insert(todoItem);
        todoItem.setId(id);
        TodoItem result = todoDao.findTodoByIdPlain(id);
        assertThat(result.getText(), equalTo("Exist"));
        assertThat(result.getDateTime(),equalTo(new Date(1977,3,8,7,3)));
        assertThat(result.isDone(),equalTo(false));
        todoItem.setText("Continue");
        todoDao.update(todoItem);
        result = todoDao.findTodoByIdPlain(id);
        assertThat(result.getText(), equalTo("Continue"));
        assertThat(result.getDateTime(),equalTo(new Date(1977,3,8,7,3)));
        assertThat(result.isDone(),equalTo(false));
    }


}
