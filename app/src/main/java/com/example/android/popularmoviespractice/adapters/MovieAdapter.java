package com.example.android.popularmoviespractice.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.android.popularmoviespractice.DetailActivity;
import com.example.android.popularmoviespractice.R;
import com.example.android.popularmoviespractice.tables.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends
        RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movies> mDataset;
    private final Context mContext;
    private ListItemClickListener mOnClickListener;


    public void setMovieData(List<Movies> movies) {
        mDataset = movies;
        notifyDataSetChanged();
    }


    public interface ListItemClickListener {
        void onListItemClick(int mDataset);
    }

    /**
     * Constructor for MovieAdapter that accepts a number of items to display
     and the specification
     * for the ListItemClickListener.
     *
     * @param movies Items to display in list
     */
    public MovieAdapter(Context context, ArrayList<Movies> movies) {
        mContext = context;
        mDataset = movies;
    }


    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at
     the specified
     * position. In this method, we update the contents of the ViewHolder to
     display the correct
     * indices in the list for this particular position, using the "position"
     argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the
    contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final MovieViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // Get the data model based on position

        String mImageBaseUrl = "http://image.tmdb.org/t/p/w185";
        final Movies movie = mDataset.get(position);
        ImageView ingredientsIv = holder.movieListImageView.findViewById
                (R.id.movie_item_list_imageview);
        Picasso.get().load(mImageBaseUrl + movie.getmImage()).resize
                (500,750).into(ingredientsIv);

        holder.movieListImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(mContext, DetailActivity.class);
                intent.putExtra("Movies", mDataset.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * This method simply returns the number of items to display. It is used
     behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our list
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void clear(List<Movies> data) {
        mDataset = data;
        notifyDataSetChanged();
    }


    /**
     * Cache of the children views for a list item.
     */
    class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // each data item is just a string in this case
        public ImageView movieListImageView;


        public MovieViewHolder(View v) {
            super(v);
            movieListImageView = v.findViewById
                    (R.id.movie_item_list_imageview);

            //Call setOnClickListener on the view passed into the constructor
            //(use 'this' as the OnClickListener)
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }
    }

    /**
     *
     * This gets called when each new ViewHolder is created. This happens when
     the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and
     allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained
    within.
     * @param viewType  If your RecyclerView has more than one type of item
    (which ours doesn't) you
     *                  can use this viewType integer to provide a different
    layout. See
     *
     *                  for more details.
     * @return A new MovieViewHolder that holds the View for each list item
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int
            viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_item_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup,
                shouldAttachToParentImmediately);

        MovieViewHolder vh = new MovieViewHolder(view);
        return vh;
    }
}








