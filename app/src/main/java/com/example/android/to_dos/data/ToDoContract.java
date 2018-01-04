package com.example.android.to_dos.data;

import android.provider.BaseColumns;

/**
 * Created by Semper_Sinem on 4.01.2018.
 */

public class ToDoContract {
    public static final class ToDoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_DATE_TIME = "datetime";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_EXPLANATION = "explanation";
        public static final String COLUMN_PLACE = "place";
    }
}
