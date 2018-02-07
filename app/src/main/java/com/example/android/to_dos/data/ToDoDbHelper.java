package com.example.android.to_dos.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Semper_Sinem on 4.01.2018.
 */

public class ToDoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 2;

    public ToDoDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_QUERY = "CREATE TABLE " + ToDoContract.ToDoEntry.TABLE_NAME + " ( " +
                ToDoContract.ToDoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ToDoContract.ToDoEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ToDoContract.ToDoEntry.COLUMN_EXPLANATION + " TEXT, " +
                ToDoContract.ToDoEntry.COLUMN_DATE_TIME + " TEXT NOT NULL, " +
                ToDoContract.ToDoEntry.COLUMN_PLACE + " TEXT, " +
                ToDoContract.ToDoEntry.COLUMN_STATUS + " INTEGER );" ;
        sqLiteDatabase.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String DROP_QUERY = "DROP TABLE IF EXISTS " + ToDoContract.ToDoEntry.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(DROP_QUERY);
        onCreate(sqLiteDatabase);
    }
}
