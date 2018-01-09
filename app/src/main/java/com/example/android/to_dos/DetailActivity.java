package com.example.android.to_dos;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.to_dos.data.ToDoContract;
import com.example.android.to_dos.data.ToDoDbHelper;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitleDetail;
    private TextView mExplanationDetail;
    private TextView mDateDetail;
    private TextView mPlaceDetail;
    private long mToDoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mTitleDetail = findViewById(R.id.tv_title_detail);
        mExplanationDetail = findViewById(R.id.tv_explanation_detail);
        mDateDetail = findViewById(R.id.tv_date_time_detail);
        mPlaceDetail = findViewById(R.id.tv_place_detail);
        Intent starterIntent = getIntent();
        mToDoID = -1;
        if(starterIntent != null)
        {
            setTextViews(starterIntent);
        }

        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab_delete_todo);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                onDeleteButtonClicked();
            }
        });
    }

    private void setTextViews(Intent starterIntent)
    {
        String title = starterIntent.getStringExtra(ToDoContract.ToDoEntry.COLUMN_TITLE);
        String date = starterIntent.getStringExtra(ToDoContract.ToDoEntry.COLUMN_DATE_TIME);
        String place = starterIntent.getStringExtra(ToDoContract.ToDoEntry.COLUMN_PLACE);
        String explanation = starterIntent.getStringExtra(ToDoContract.ToDoEntry.COLUMN_EXPLANATION);
        mToDoID = starterIntent.getLongExtra(ToDoContract.ToDoEntry._ID, -1);
        mTitleDetail.setText(title);
        mExplanationDetail.setText(explanation);
        mDateDetail.setText(date);
        mPlaceDetail.setText(place);
    }

    private void onDeleteButtonClicked()
    {
        boolean deleted = deleteToDoItemFromDB();
        if(deleted) {
            Toast.makeText(this, "The " + mTitleDetail.getText() + " To-Do is deleted from list", Toast.LENGTH_LONG).show();
            finish();
        }
        else
            Toast.makeText(this, "Could not delete. Try again later!", Toast.LENGTH_LONG).show();
    }

    private boolean deleteToDoItemFromDB()
    {
        ToDoDbHelper helper = new ToDoDbHelper(this);
        SQLiteDatabase mDb = helper.getWritableDatabase();
        if(mToDoID == -1)
            return false;
        boolean success = mDb.delete(ToDoContract.ToDoEntry.TABLE_NAME,
                ToDoContract.ToDoEntry._ID + "=" + mToDoID,
                null) > 0;
        mDb.close();
        return success;
    }

}
