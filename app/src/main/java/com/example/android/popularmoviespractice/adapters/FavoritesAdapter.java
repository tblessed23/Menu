package com.example.android.popularmoviespractice.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.popularmoviespractice.DetailActivity;
import com.example.android.popularmoviespractice.R;
import com.example.android.popularmoviespractice.fragments.FavoritesFragment;
import com.example.android.popularmoviespractice.tables.AppDatabase;
import com.example.android.popularmoviespractice.tables.AppExecutors;
import com.example.android.popularmoviespractice.tables.Favorites;
import com.example.android.popularmoviespractice.tables.Reviews;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private AppDatabase mDb;

    // Member variable to handle item clicks
  // final private ItemClickListener mItemClickListener;

    // Class variables for the List that holds task data and the Context
    private List<Favorites> mTaskEntries;
    private Context mContext;


    /**
     * Constructor for the TaskAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param Reviews the ItemClickListener
     */
    public FavoritesAdapter(Context context, ArrayList<Favorites> Reviews) {
        mContext = context;
        mTaskEntries = Reviews;
        //mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.favorite_layout, parent, false);

        mDb = AppDatabase.getInstance(mContext);
        return new FavoritesViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        // Determine the values of the wanted data
        Favorites taskEntry = mTaskEntries.get(position);
        String titleFavorities = taskEntry.getTitleFavorites();
        int id = taskEntry.getId();


        //Set values
        holder.taskDescriptionView.setText(titleFavorities);
        holder.updatedAtView.setText(String.valueOf(id));


    }



    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

   /**Used for uodating/deleting database information**/
    public List<Favorites> getTasks() {
        return mTaskEntries;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Favorites> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }

//    public interface ItemClickListener {
//        void onItemClickListener(int itemId);
//    }

    // Inner class for creating ViewHolders
    class FavoritesViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView taskDescriptionView;
        TextView updatedAtView;


        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public FavoritesViewHolder(View itemView) {
            super(itemView);

            taskDescriptionView = itemView.findViewById(R.id.taskDescription);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
           // itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
//            final int elementId = mTaskEntries.get(getAdapterPosition()).getId();
//            mItemClickListener.onItemClickListener(elementId);
//
//        }


    }
}
