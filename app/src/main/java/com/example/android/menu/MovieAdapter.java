package com.example.android.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movies> mDataset;
    List<Movies> mNewsData;

    private String mImageBaseUrl = "http://image.tmdb.org/t/p/w185";
    private final Context mContext;
    private ListItemClickListener mOnClickListener;


    public interface ListItemClickListener {

        public void onListItemClick (int clickedItemIndex);
    }

    public void setOnItemClickListener(ListItemClickListener listener) {
        mOnClickListener = listener;
    }

    /**
     * Constructor for MovieAdapter that accepts a number of items to display and the specification
     * for the ListItemClickListener.
     *
     * @param movies Items to display in list
     */
    public MovieAdapter(Context context, ArrayList<Movies> movies) {
        mContext = context;
        mDataset = movies;
        /// mOnClickListener = listener;
    }

    public void addAll(List<Movies> newsData) {
        mNewsData = newsData;
    }

    /**
     *
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *
     *                  for more details.
     * @return A new NumberViewHolder that holds the View for each list item
     */
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_item_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        MovieViewHolder vh = new MovieViewHolder(view);
        return vh;
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the correct
     * indices in the list for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // Get the data model based on position
        Movies movie = mDataset.get(position);

        Movies movies = (Movies) JsonUtils.extractMovies(String.valueOf(movie));

        // Set item views based on your views and data model
//        ImageView imageView = holder.movieListTextView;
//        imageView.setImageResource(movie.getmImage());

//        ImageView imageView = holder.movieListTextView;
//       imageView.setImageResource(Integer.parseInt(movies.getmImage()));

        TextView textView = holder.movieListTextView;
        textView.setText(movies.getmReleaseDate());



//        String movieResponse;
//        try {
//            movieResponse = MovieNetworkUtilities.getResponseFromHTTP(movieUrl);
//            mMovie = MovieJSONUtilities.parseJSONMovie(movieResponse);

        //Picasso.with(mContext).load(mImageBaseUrl + movie.getmImage()).into(imageView);


    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
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
        public TextView movieListTextView;


        public MovieViewHolder(View v) {
            super(v);
            movieListTextView = v.findViewById
                    (R.id.movie_item_list_textview);

            //Call setOnClickListener on the view passed into the constructor
            //(use 'this' as the OnClickListener)
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }



    }
}



