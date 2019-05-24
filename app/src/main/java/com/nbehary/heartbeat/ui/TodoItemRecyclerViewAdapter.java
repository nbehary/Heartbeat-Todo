package com.nbehary.heartbeat.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nbehary.heartbeat.R;
import com.nbehary.heartbeat.data.TodoItem;


import java.text.DateFormat;
import java.util.List;

public class TodoItemRecyclerViewAdapter extends RecyclerView.Adapter<TodoItemRecyclerViewAdapter.ViewHolder>
{

    private List<TodoItem> values;

    public TodoItemRecyclerViewAdapter(List<TodoItem> items) {
        values = items;
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

        Log.d("9087","onBindViewHolder!");
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

    public void setValues(List<TodoItem> values){
        Log.d("1234", "setValues called");
        this.values = values;
        notifyDataSetChanged();
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
