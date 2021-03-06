package com.example.android.to_dos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.android.to_dos.data.ToDoContract;
import com.example.android.to_dos.data.ToDoDbHelper;

public class MainActivity extends AppCompatActivity
    implements ToDoAdapter.ListItemClickListener, ToDoAdapter.ListItemCheckBoxClickListener
{
    private SQLiteDatabase mReadableDb;
    private SQLiteDatabase mWritableDb;
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
        mReadableDb = helper.getReadableDatabase();
        mWritableDb = helper.getWritableDatabase();

        mToDoAdapter = new ToDoAdapter(this,this, getAllToDos());
        mToDoList.setAdapter(mToDoAdapter);

        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab_insert_todo_main);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(MainActivity.this, AddToDoActivity.class);
                startActivity(addTaskIntent);
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.main_menu_add_todo) {
            Intent addTodoIntent = new Intent(this, AddToDoActivity.class);
            startActivity(addTodoIntent);
        }
        return super.onOptionsItemSelected(item);
    }*/

    private Cursor getAllToDos() {
        return mReadableDb.query(
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
    protected void onResume() {
        super.onResume();
        mToDoAdapter.updateCursor(getAllToDos());
    }

    @Override
    public void onListItemClick(int clickedItemId) {
        Cursor cursor = mReadableDb.query(ToDoContract.ToDoEntry.TABLE_NAME, null, null, null, null,null,null);
        if(cursor.getCount() >= clickedItemId)
            cursor.moveToPosition(clickedItemId);
        else
            return;
        Intent detailIntent = new Intent(this, DetailActivity.class);
        String title = cursor.getString(cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_TITLE));
        String date = cursor.getString(cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_DATE_TIME));
        String place = cursor.getString(cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_PLACE));
        String explanation = cursor.getString(cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_EXPLANATION));
        long id = cursor.getLong(cursor.getColumnIndex(ToDoContract.ToDoEntry._ID));
        detailIntent.putExtra(ToDoContract.ToDoEntry.COLUMN_TITLE, title);
        detailIntent.putExtra(ToDoContract.ToDoEntry.COLUMN_EXPLANATION, explanation);
        detailIntent.putExtra(ToDoContract.ToDoEntry.COLUMN_DATE_TIME, date);
        detailIntent.putExtra(ToDoContract.ToDoEntry.COLUMN_PLACE, place);
        detailIntent.putExtra(ToDoContract.ToDoEntry._ID, id);
        startActivity(detailIntent);
    }

    @Override
    public void onCheckBoxItemClick(long clickedItemId, boolean isChecked) {
        String id = Long.toString(clickedItemId);
        ContentValues cv = new ContentValues();
        int status;
        if(isChecked)
            status = 1;
        else
            status = 0;
        cv.put(ToDoContract.ToDoEntry.COLUMN_STATUS, Integer.toString(status));
        int res = mWritableDb.update(ToDoContract.ToDoEntry.TABLE_NAME, cv, ToDoContract.ToDoEntry._ID + " = ?", new String[]{id});
        /*if(res > 0)
            Toast.makeText(this, "updated " + clickedItemId, Toast.LENGTH_LONG).show();*/
    }
}
