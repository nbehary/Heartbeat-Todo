package com.nbehary.heartbeat.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.nbehary.heartbeat.model.TodoViewModel;

import java.util.List;

//This doesn't do much.  Good to set it up for further enhancements though.

public class TodoRepository {
    private TodoDao todoDao;
    private LiveData<List<TodoItem>> allTodos;

    public  TodoRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        todoDao = db.todoDao();
        allTodos= todoDao.getAll();
    }

    public LiveData<List<TodoItem>> getAllTodos(){
        return allTodos;
    }

    public void insert(TodoItem item){
        new insertAsyncTask(todoDao).execute(item);
    }

    public void update(TodoItem item){
        new updateAsyncTask(todoDao).execute(item);
    }

    public void delete(TodoItem item) { new deleteAsyncTask(todoDao).execute(item); }

    public LiveData<TodoItem> get(Long id) {return todoDao.findTodoById(id);}

    private static class updateAsyncTask extends AsyncTask<TodoItem,Void,Void>{

        private TodoDao asyncTaskDao;

        updateAsyncTask(TodoDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TodoItem... params){
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<TodoItem,Void,Void>{

        private TodoDao asyncTaskDao;

        insertAsyncTask(TodoDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TodoItem... params){
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<TodoItem,Void,Void>{

        private TodoDao asyncTaskDao;

        deleteAsyncTask(TodoDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TodoItem... params){
            asyncTaskDao.delete(params[0]);
            return null;
        }
    }

}
