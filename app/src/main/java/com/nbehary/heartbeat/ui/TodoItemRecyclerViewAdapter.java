package com.nbehary.heartbeat.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.nbehary.heartbeat.R;
import com.nbehary.heartbeat.data.TodoItem;
import com.nbehary.heartbeat.model.TodoViewModel;


import java.text.DateFormat;
import java.util.List;

public class TodoItemRecyclerViewAdapter extends RecyclerView.Adapter<TodoItemRecyclerViewAdapter.ViewHolder>
{

    private List<TodoItem> values;
    private TodoItem recentlyDeleted;
    private MainActivity activity;

    public TodoItemRecyclerViewAdapter(MainActivity ctx,List<TodoItem> items)
    {
        values = items;
        activity = ctx;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = values.get(position);
        //holder.mIdView.setText(values.get(position).getText());
        holder.textView.setText(holder.item.getText());
        holder.timeView.setText(DateFormat.getDateTimeInstance().format(holder.item.getDateTime()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                DialogFragment newFragment = EditDialogFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putLong("ID",holder.item.getId());
                bundle.putBoolean("NEW", false);
                newFragment.setArguments(bundle);
                newFragment.show(ft,"edit_dialog");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (values != null){
            return values.size();
        } else {
            return 0;
        }
    }

    public void deleteItem(int position){
        TodoViewModel viewModel = ViewModelProviders.of((AppCompatActivity) activity).get(TodoViewModel.class);
        recentlyDeleted = values.get(position);
        viewModel.delete(recentlyDeleted);
        showUndoSnackbar();
    }

    private void showUndoSnackbar(){
        View view = activity.findViewById(R.id.activity_main);
        Snackbar snackbar = Snackbar.make(view,R.string.undo_delete_text, Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoItemRecyclerViewAdapter.this.undoDelete();
            }
        });
        snackbar.show();

    }

    private void undoDelete(){
        TodoViewModel viewModel = ViewModelProviders.of((AppCompatActivity) activity).get(TodoViewModel.class);
        viewModel.insert(recentlyDeleted);
    }

    public TodoItem getItem(int position){
        return values.get(position);

    }

    public void setValues(List<TodoItem> values){
        this.values = values;
        notifyDataSetChanged();
    }

    public Context getActivity() {
        return activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView timeView;
        public final TextView textView;

        public TodoItem item;

        public ViewHolder(View view) {
            super(view);
            Log.d("5678","ViewHolder!");
            mView = view;
            timeView = (TextView) view.findViewById(R.id.todo_time_date);
            textView = (TextView) view.findViewById(R.id.todo_text);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textView.getText() + "'";
        }
    }
}
