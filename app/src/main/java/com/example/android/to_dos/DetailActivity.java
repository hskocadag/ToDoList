package com.example.android.to_dos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.to_dos.data.ToDoContract;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitleDetail;
    private TextView mExplanationDetail;
    private TextView mDateDetail;
    private TextView mPlaceDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mTitleDetail = findViewById(R.id.tv_title_detail);
        mExplanationDetail = findViewById(R.id.tv_explanation_detail);
        mDateDetail = findViewById(R.id.tv_date_time_detail);
        mPlaceDetail = findViewById(R.id.tv_place_detail);
        Intent starterIntent = getIntent();
        if(starterIntent != null)
        {
            String title = starterIntent.getStringExtra(ToDoContract.ToDoEntry.COLUMN_TITLE);
            String date = starterIntent.getStringExtra(ToDoContract.ToDoEntry.COLUMN_DATE_TIME);
            String place = starterIntent.getStringExtra(ToDoContract.ToDoEntry.COLUMN_PLACE);
            String explanation = starterIntent.getStringExtra(ToDoContract.ToDoEntry.COLUMN_EXPLANATION);
            mTitleDetail.setText(title);
            mExplanationDetail.setText(explanation);
            mDateDetail.setText(date);
            mPlaceDetail.setText(place);
        }
    }
}
