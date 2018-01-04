package com.example.android.to_dos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Semper_Sinem on 4.01.2018.
 */

public class ToDoAdapter {

    final private ToDoAdapterClickHandler mClickHandler;

    public interface ToDoAdapterClickHandler {
        void onClick(String data);
    }

    public ToDoAdapter(ToDoAdapterClickHandler toDoAdapterClickHandler)
    {
        mClickHandler = toDoAdapterClickHandler;
    }

    public class ToDoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mTitleTextView;

        public ToDoAdapterViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(Integer.toString(adapterPosition));
        }
    }

    public ToDoAdapterViewHolder onCreateViewHolder (ViewGroup viewGroup, int viewType)
    {
        Context context = viewGroup.getContext();
        return new ToDoAdapterViewHolder(null);
    }

}
