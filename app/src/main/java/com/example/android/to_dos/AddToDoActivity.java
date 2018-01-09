package com.example.android.to_dos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.to_dos.data.ToDoContract;
import com.example.android.to_dos.data.ToDoDbHelper;

public class AddToDoActivity extends AppCompatActivity {

    private EditText mTitle;
    private EditText mExplanation;
    private EditText mDateTime;
    private EditText mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        mTitle = (EditText) findViewById(R.id.et_add_title);
        mExplanation = (EditText) findViewById(R.id.et_add_explanation);
        mDateTime = (EditText) findViewById(R.id.et_add_date_time);
        mPlace = (EditText) findViewById(R.id.et_add_place);
        final Button addToDo = (Button) findViewById(R.id.bt_add_todo);
        addToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mTitle.getText().toString();
                String explanation = mExplanation.getText().toString();
                String dateTime = mDateTime.getText().toString();
                String place = mPlace.getText().toString();
                addToDoToDatabase(title, explanation, dateTime, place);
            }
        });
    }

    private void handleClick(String title, String explanation, String dateTime, String place) {
        if (!title.isEmpty()) {
            addToDoToDatabase(title, explanation, dateTime, place);
        }
        else {
            Toast.makeText(this, "Title can not be empty", Toast.LENGTH_LONG).show();
        }
    }

    private void addToDoToDatabase(String title, String explanation, String dateTime, String place)
    {
        ToDoDbHelper dbHelper = new ToDoDbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ToDoContract.ToDoEntry.COLUMN_TITLE, title);
        cv.put(ToDoContract.ToDoEntry.COLUMN_DATE_TIME, dateTime);
        cv.put(ToDoContract.ToDoEntry.COLUMN_EXPLANATION, explanation);
        cv.put(ToDoContract.ToDoEntry.COLUMN_PLACE, place);
        database.insert(ToDoContract.ToDoEntry.TABLE_NAME, null, cv);
        database.close();
        finish();
    }

}
