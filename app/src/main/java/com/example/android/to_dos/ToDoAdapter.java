package com.example.android.to_dos;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.android.to_dos.data.ToDoContract;

/**
 * Created by Semper_Sinem on 4.01.2018.
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>{

    final private ListItemClickListener mlistItemClickListener;
    final private ListItemCheckBoxClickListener mListItemCheckBoxClickListener;
    private Cursor mCursor;

    public ToDoAdapter(ListItemCheckBoxClickListener listItemCheckBoxClickListener, ListItemClickListener listItemClickListener, Cursor cursor)
    {
        mCursor = cursor;
        mlistItemClickListener = listItemClickListener;
        mListItemCheckBoxClickListener = listItemCheckBoxClickListener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemId);
    }

    public interface ListItemCheckBoxClickListener {
        void onCheckBoxItemClick(long clickedItemId, boolean isChecked);
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final CheckBox mTitleCheckBox;
        public final TextView mDateTimeTextView;
        public final TextView mPlaceTextView;

        public ToDoViewHolder(View itemView) {
            super(itemView);
            mTitleCheckBox = (CheckBox) itemView.findViewById(R.id.cb_title);
            mDateTimeTextView = (TextView) itemView.findViewById(R.id.tv_date_list_item);
            mPlaceTextView = (TextView) itemView.findViewById(R.id.tv_place_list_item);
            mTitleCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                  @Override
                  public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                      Object tag = buttonView.getTag();
                      if(tag == null || !(tag instanceof Long))
                          Log.v("ToDoAdapter", "tag is invalid");
                      else {
                          long id = (long) tag;
                          mListItemCheckBoxClickListener.onCheckBoxItemClick(id, isChecked);
                      }
                  }
              }
            );

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mlistItemClickListener.onListItemClick(adapterPosition);
        }
    }

    public void updateCursor(Cursor cursor)
    {
        mCursor = cursor;
        notifyDataSetChanged();
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
        boolean done = mCursor.getInt(mCursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_STATUS)) == 1;
        long id = mCursor.getLong(mCursor.getColumnIndex(ToDoContract.ToDoEntry._ID));
        holder.mTitleCheckBox.setTag(id);
        holder.mTitleCheckBox.setChecked(done);
        holder.mTitleCheckBox.setText(title);
        holder.mDateTimeTextView.setText(date);
        holder.mPlaceTextView.setText(place);
        //holder.mTitleCheckBox
        // TODO (7) Set the tag of the itemview in the holder to the in
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

}
