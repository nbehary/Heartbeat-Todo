package com.nbehary.heartbeat.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nbehary.heartbeat.data.TodoItem;
import com.nbehary.heartbeat.data.TodoRepository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository repository;
    private LiveData<List<TodoItem>> allTodos;

    public TodoViewModel(Application application){
        super(application);
        repository = new TodoRepository(application);
        allTodos = repository.getAllTodos();
    }

    public LiveData<List<TodoItem>> getAllTodos() {return allTodos;};

    public void insert(TodoItem item) {repository.insert(item);}

    public void update(TodoItem item) {repository.update(item);}

    public void delete(TodoItem item) {repository.delete(item);}

    public LiveData<TodoItem> get(Long id){
        return repository.get(id);
    }

}
