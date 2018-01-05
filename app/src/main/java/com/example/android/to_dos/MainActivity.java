package com.example.android.to_dos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.android.to_dos.data.ToDoContract;
import com.example.android.to_dos.data.ToDoDbHelper;

public class MainActivity extends AppCompatActivity
    implements ToDoAdapter.ListItemClickListener
{
    private SQLiteDatabase mDb;
    private RecyclerView mToDoList;
    private ToDoAdapter mToDoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToDoList = findViewById(R.id.rv_todo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mToDoList.setLayoutManager(layoutManager);
        mToDoList.setHasFixedSize(false);

        ToDoDbHelper helper = new ToDoDbHelper(this);
        mDb = helper.getReadableDatabase();

        mToDoAdapter = new ToDoAdapter(this, getAllToDos());
        mToDoList.setAdapter(mToDoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.main_menu_add_todo) {
            Intent addTodoIntent = new Intent(this, AddToDoActivity.class);
            startActivity(addTodoIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private Cursor getAllToDos() {
        return mDb.query(
                ToDoContract.ToDoEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onListItemClick(int clickedItemId) {
        Cursor cursor = mDb.query(ToDoContract.ToDoEntry.TABLE_NAME, null, null, null, null,null,null);
        if(cursor.getCount() >= clickedItemId)
            cursor.moveToPosition(clickedItemId);
        else
            return;
        Intent detailIntent = new Intent(this, DetailActivity.class);
        String title = cursor.getString(cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_TITLE));
        String date = cursor.getString(cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_DATE_TIME));
        String place = cursor.getString(cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_PLACE));
        String explanation = cursor.getString(cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_EXPLANATION));
        detailIntent.putExtra(ToDoContract.ToDoEntry.COLUMN_TITLE, title);
        detailIntent.putExtra(ToDoContract.ToDoEntry.COLUMN_EXPLANATION, explanation);
        detailIntent.putExtra(ToDoContract.ToDoEntry.COLUMN_DATE_TIME, date);
        detailIntent.putExtra(ToDoContract.ToDoEntry.COLUMN_PLACE, place);
        startActivity(detailIntent);
    }
}
