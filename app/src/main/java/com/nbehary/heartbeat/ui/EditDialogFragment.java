package com.nbehary.heartbeat.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nbehary.heartbeat.R;
import com.nbehary.heartbeat.data.TodoItem;
import com.nbehary.heartbeat.model.TodoViewModel;

import java.util.Date;;

public class EditDialogFragment extends DialogFragment {

    TodoItem itemToEdit;
    boolean isNew = false;

    static EditDialogFragment newInstance(){
        EditDialogFragment f = new EditDialogFragment();

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        final TextView titleText = view.findViewById(R.id.editTodoTitle);
        final EditText editText = view.findViewById(R.id.editTodoText);
        final TodoViewModel viewModel = ViewModelProviders.of(getActivity()).get(TodoViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null){
            Long id = bundle.getLong("ID");
            if (bundle.getBoolean("NEW")){
                itemToEdit = new TodoItem("", new Date(),false);
                editText.setText(itemToEdit.getText());
                titleText.setText("New TODO Item");
                isNew = true;
            } else {
                viewModel.get(id).observe(this, new Observer<TodoItem>() {
                    @Override
                    public void onChanged(TodoItem todoItem) {
                        itemToEdit = todoItem;
                        editText.setText(itemToEdit.getText());
                        titleText.setText("Edit TODO Item");

                    }
                });
            }

        }

        Button button = view.findViewById(R.id.editDialogButton);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                itemToEdit.setText(editText.getText().toString());
                if(isNew){
                    viewModel.insert(itemToEdit);
                }else{
                    viewModel.update(itemToEdit);
                }
                dismiss();
            }
        });
        return view;
    }

}
