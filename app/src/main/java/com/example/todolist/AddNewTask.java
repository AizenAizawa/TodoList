package com.example.todolist;

import static android.graphics.Color.GRAY;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";

    private EditText mEditText;
    private Button mSaveButton;
    private DatabaseHelper myDB;
    private boolean isUpdate = false;
    private int taskId = -1;

    public static AddNewTask newInstance() {
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText = view.findViewById(R.id.editText);
        mSaveButton = view.findViewById(R.id.addButton);
        myDB = new DatabaseHelper(getActivity());

        Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            taskId = bundle.getInt("id", -1);
            String task = bundle.getString("task", "");

            if (!task.isEmpty()) {
                mEditText.setText(task);
                mSaveButton.setEnabled(true);
            }
        }

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSaveButton.setEnabled(!s.toString().trim().isEmpty());
                mSaveButton.setBackgroundColor(s.toString().trim().isEmpty() ? GRAY : getResources().getColor(R.color.green));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString().trim();

                if (isUpdate) {
                    myDB.updateTask(taskId, text);
                } else {
                    ToDoModel newItem = new ToDoModel(0, text, 0);
                    myDB.insertTask(newItem);
                }

                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListener) {
            ((OnDialogCloseListener) activity).onDialogClose(dialog);
        }
    }
}
