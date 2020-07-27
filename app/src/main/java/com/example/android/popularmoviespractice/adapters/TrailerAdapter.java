package com.example.android.popularmoviespractice.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.android.popularmoviespractice.R;
import com.example.android.popularmoviespractice.tables.Trailers;
import com.example.android.popularmoviespractice.utilities.ColorUtils;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private static final String TAG = TrailerAdapter.class.getSimpleName();



    private ListItemClickListener mOnClickListener;

    //Holds the total number of ViewHolders that are created
      private static int viewHolderCount;
    StringBuilder sk;
    private List<Trailers> mNumberItems;
    private Context mContext;
    String trailerSite;
    String movieType;
    String movieKey;

    public interface ListItemClickListener {

        public void onListItemClick (int mNumberItems);
    }



    public void setReviewData(List<Trailers> trailers) {
        mNumberItems = trailers;
    }


    /**
     * Constructor for GreenAdapter that accepts a number of items to display and the specification
     * for the ListItemClickListener.
     *
     * @param numberOfItems Number of items to display in list
     */
    public TrailerAdapter(ArrayList<Trailers> numberOfItems, Context context) {

        mNumberItems = numberOfItems;
        mContext = context;


        //When a new TrailerAdapter is created, set the viewHolderCount to 0
        viewHolderCount = 1;


    }

    /**
     *
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new NumberViewHolder that holds the View for each list item
     */
    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailer_item_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);

        //Set the text of viewHolderIndex
         viewHolder.viewHolderIndex.setText("Trailer: " + viewHolderCount);

        // ColorUtils.getViewHolderBackgroundColorFromInstance and pass in a Context and the viewHolderCount
        int backgroundColorForViewHolder = ColorUtils.getViewHolderBackgroundColorFromInstance(context, viewHolderCount);


        // Set the background color of viewHolder.itemView with the color from above
         viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        // Increment viewHolderCount and log its value
        viewHolderCount = viewHolderCount + 1;

        // Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
        //         + viewHolderCount);


        return viewHolder;
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
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        Trailers trailer = mNumberItems.get(position);

        trailerSite = trailer.getmSite();
        movieType = trailer.getmType();
        movieKey = trailer.getmKey();

        if (trailerSite.equals("YouTube") && movieType.equals("Trailer")){
            sk = new StringBuilder();
            sk.append("https://youtu.be/");
            sk.append(movieKey);

        }


        holder.viewHolderIndex.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(String.valueOf(sk)));
                mContext.startActivity(i);

            }
        });

    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        return mNumberItems.size();
    }

    public void clear(List<Trailers> data) {
        mNumberItems = data;
        notifyDataSetChanged();
    }


    /**
     * Cache of the children views for a list item.
     */
    class TrailerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // Will display the position in the list, ie 0 through getItemCount() - 1
        TextView listItemNumberView;

        // Used to display the ViewHolder index
        TextView viewHolderIndex;



        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         * @param itemView The View that you inflated in
         *                 {@link TrailerAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public TrailerViewHolder(View itemView) {
            super(itemView);

            //listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
            itemView.setOnClickListener(this);
        }

        /**
         * A method we wrote for convenience. This method will take an integer as input and
         * use that integer to display the appropriate text within a list item.
         * @param //listIndex Position of the item in the list
         */
//        //void bind(int listIndex) {
//            listItemNumberView.setText(String.valueOf(listIndex));
//        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
