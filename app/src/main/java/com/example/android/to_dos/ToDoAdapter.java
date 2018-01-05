package com.example.android.to_dos;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.to_dos.data.ToDoContract;

/**
 * Created by Semper_Sinem on 4.01.2018.
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>{

    final private ListItemClickListener mlistItemClickListener;
    final private Cursor mCursor;

    public ToDoAdapter(ListItemClickListener listItemClickListener, Cursor cursor)
    {
        mCursor = cursor;
        mlistItemClickListener = listItemClickListener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemId);
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mTitleTextView;
        public final TextView mDateTimeTextView;
        public final TextView mPlaceTextView;

        public ToDoViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title_list_item);
            mDateTimeTextView = (TextView) itemView.findViewById(R.id.tv_date_list_item);
            mPlaceTextView = (TextView) itemView.findViewById(R.id.tv_place_list_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mlistItemClickListener.onListItemClick(adapterPosition);
        }
    }

    public ToDoViewHolder onCreateViewHolder (ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.todo_list_item, viewGroup, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String title = mCursor.getString(mCursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_TITLE));
        String date = mCursor.getString(mCursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_DATE_TIME));
        String place = mCursor.getString(mCursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_PLACE));
        long id = mCursor.getLong(mCursor.getColumnIndex(ToDoContract.ToDoEntry._ID));
        holder.mTitleTextView.setText(title);
        holder.mDateTimeTextView.setText(date);
        holder.mPlaceTextView.setText(place);
        // TODO (7) Set the tag of the itemview in the holder to the id
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

}
