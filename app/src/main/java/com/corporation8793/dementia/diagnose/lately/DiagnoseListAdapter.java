package com.corporation8793.dementia.diagnose.lately;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.corporation8793.dementia.R;
import com.corporation8793.dementia.chat.ChatModel;
import com.corporation8793.dementia.util.Application;

import java.util.ArrayList;

import okhttp3.internal.Util;

public class DiagnoseListAdapter extends RecyclerView.Adapter<DiagnoseListAdapter.ViewHolder> {
    private ArrayList<DiagnoseList> mDataset;

    public static final int USER = 1;

    int height = 0;
    public DiagnoseListAdapter(ArrayList<DiagnoseList> dataSet, int height) {
        mDataset = dataSet;
        this.height = height;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.diagnose_list_itemview, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnoseListAdapter.ViewHolder holder, int position) {
        holder.textView.setText(mDataset.get(position).date);

        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams)holder.itemView.getLayoutParams();
        layoutParams.height = height;
        holder.itemView.requestLayout();
    }

    // Replace the contents of a view (invoked by the layout manager)


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.latest_date);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     */

}
