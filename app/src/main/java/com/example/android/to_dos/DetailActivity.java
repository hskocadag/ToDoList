package com.example.android.to_dos;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private SQLiteDatabase mDb;

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
            String title = starterIntent.getStringExtra(ToDoContract.ToDoEntry.COLUMN_TITLE);
            String date = starterIntent.getStringExtra(ToDoContract.ToDoEntry.COLUMN_DATE_TIME);
            String place = starterIntent.getStringExtra(ToDoContract.ToDoEntry.COLUMN_PLACE);
            String explanation = starterIntent.getStringExtra(ToDoContract.ToDoEntry.COLUMN_EXPLANATION);
            mToDoID = starterIntent.getLongExtra(ToDoContract.ToDoEntry._ID, -1);
            mTitleDetail.setText(title);
            mExplanationDetail.setText(explanation);
            mDateDetail.setText(date);
            mPlaceDetail.setText(place);
            ToDoDbHelper helper = new ToDoDbHelper(this);
            mDb = helper.getWritableDatabase();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.detail_menu_delete_todo)
        {
            boolean deleted = deleteToDoItemFromDB();
            if(deleted) {
                Toast.makeText(this, "The " + mTitleDetail.getText() + " To-Do is deleted from list", Toast.LENGTH_LONG).show();
                finish();
            }
            else
                Toast.makeText(this, "Could not delete. Try again later!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean deleteToDoItemFromDB()
    {
        if(mToDoID == -1)
            return false;
        return mDb.delete(ToDoContract.ToDoEntry.TABLE_NAME,
                ToDoContract.ToDoEntry._ID + "=" + mToDoID,
                null) > 0;
    }

}
