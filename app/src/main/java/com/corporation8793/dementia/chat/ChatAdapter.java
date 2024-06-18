package com.corporation8793.dementia.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.corporation8793.dementia.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ArrayList<ChatModel> mDataset;

    public static final int USER = 1;
    public static final int DATE = 2;
    public ChatAdapter(ArrayList<ChatModel> dataSet) {
        mDataset = dataSet;
    }


    @Override
    public int getItemViewType(int position) {
        if (mDataset.get(position).type == USER){
            return 1;
        }else if(mDataset.get(position).type == DATE){
            return 2;
        }else{
            return 0;
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.chat_message_itemview, viewGroup, false);

        if (viewType == USER){
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.user_message_itemview, viewGroup, false);
        }

        if (viewType == DATE){
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.date_message_itemview, viewGroup, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        holder.textView.setText(mDataset.get(position).message);
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

            textView = (TextView) view.findViewById(R.id.message);
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
