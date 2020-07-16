package com.example.android.popularmoviespractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;

import com.example.android.popularmoviespractice.tables.AppDatabase;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

//    //Member variable for the Database
//    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
//
//        //Initialize Database
//        mDb = AppDatabase.getInstance(getApplicationContext());
//
//        /**
//         * onSaveButtonClicked is called when the "save" button is clicked.
//         * It retrieves user input and inserts that new task data into the underlying database.
//         */
//
//        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_id);
//        if (checkBox.isChecked()) {
//            checkBox.setChecked(false);
//        }
//
////        public void onSaveButtonClicked() {
////            String description = mEditText.getText().toString();
////            int priority = getPriorityFromViews();
////            Date date = new Date();
////
////            final TaskEntry task = new TaskEntry(description, priority, date);
////            AppExecutors.getInstance().diskIO().execute(new Runnable() {
////                @Override
////                public void run() {
////                    if (mTaskId == DEFAULT_TASK_ID) {
////                        // insert new task
////                        mDb.taskDao().insertTask(task);
////                    } else {
////                        //update task
////                        task.setId(mTaskId);
////                        mDb.taskDao().updateTask(task);
////                    }
////                    finish();
////                }
////            });
//
//    }
}}