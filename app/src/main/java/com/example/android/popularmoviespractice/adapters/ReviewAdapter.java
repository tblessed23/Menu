package com.example.android.popularmoviespractice.adapters;


import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.android.popularmoviespractice.R;
import com.example.android.popularmoviespractice.tables.Reviews;


import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends
        RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Reviews> mDataset;
    private final Context mContext;
    private Reviews reviews;


    public void setReviewData(List<Reviews> Reviews) {
        mDataset = Reviews;
    }



    /**
     * Constructor for ReviewAdapter that accepts a number of items to display
     and the specification
     * for the ListItemClickListener.
     *
     * @param Reviews Items to display in list
     */
    public ReviewAdapter(Context context, ArrayList<Reviews> Reviews) {
        mContext = context;
        mDataset = Reviews;
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
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // Get the data model based on position

        Reviews review = mDataset.get(position);
        TextView textView = holder.author;
        textView.setText(review.getmAuthor());
        TextView textViewAgain = holder.content;
        textViewAgain.setText(review.getmContent());
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

    public void clear(List<Reviews> data) {
        mDataset = data;
        notifyDataSetChanged();
    }


    /**
     * Cache of the children views for a list item.
     */
    class ReviewViewHolder extends RecyclerView.ViewHolder
    {

        // each data item is just a string in this case
        public TextView content;
        public TextView author;

        public ReviewViewHolder(View v) {
            super(v);


            author = (TextView) v.findViewById(R.id.movie_reviews_author);
            content= (TextView) v.findViewById(R.id.movie_reviews_content);
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
     * @return A new ReviewViewHolder that holds the View for each list item
     */
    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int
            viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.review_item_list, viewGroup,
                false);

        ReviewViewHolder vh = new ReviewViewHolder(view);
        return vh;
    }
}








