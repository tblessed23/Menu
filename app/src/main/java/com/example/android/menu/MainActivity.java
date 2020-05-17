package com.example.android.menu;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movies>> {

    private RecyclerView recyclerView;
    private MovieAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    private static final int MOVIESARTICLE_LOADER_ID = 1;

    /**
     * URL for earthquake data from the USGS dataset
     */
   // private static final String MOVIES_REQUEST_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String MOVIES_REQUEST_URL = "https://api.themoviedb.org/3/discover/movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //  // Find a reference to the {@link RecyclerView} in the layout
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);







        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new MovieAdapter(this, new ArrayList<Movies>());
        recyclerView.setAdapter(mAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);


        // use a grid layout manager
        layoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
//        if (JsonUtils.fetchEarthquakeData( MOVIES_REQUEST_URL).isEmpty()) {
//            recyclerView.setVisibility(View.GONE);
//            mEmptyStateTextView.setVisibility(View.VISIBLE);
//        }
//        else {
//            recyclerView.setVisibility(View.VISIBLE);
//            mEmptyStateTextView.setVisibility(View.GONE);
//        }

        mAdapter.setOnItemClickListener(new MovieAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int clickedItemIndex) {

                Class destinationActivity = DetailActivity.class;
                Intent startChildActivityIntent = new Intent(MainActivity.this, destinationActivity);

                startChildActivityIntent.putExtra("Movies", mAdapter.getItemViewType(clickedItemIndex));
                startActivity(startChildActivityIntent);

            }


        });


        // Get a reference to the ConnectivityManager to check state of network connectivity

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        // because this activity implements the LoaderCallbacks interface).
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MOVIESARTICLE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public Loader<List<Movies>> onCreateLoader(int i, Bundle bundle) {

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(MOVIES_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value.
        uriBuilder.appendQueryParameter("api_key", "543e8145fb4bd3a4d9f616fb389b7356");
        uriBuilder.appendQueryParameter("language", "en-US");
        uriBuilder.appendQueryParameter("sort_by", "popularity.desc");


        // Return the completed uri `https://content.guardianapis.com/search?section=food&from-date=2019-01-01&order-by=newest&show-tags=contributor&api-key=test'
        return new MoviesLoader(this, uriBuilder.toString());
    }


    @Override
    public void onLoadFinished(Loader < List < Movies >> loader, List < Movies > news) {
        //Hide Progress Bar (loading indicator) to gone

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No movies found."
        mEmptyStateTextView.setText(R.string.no_movies);

        // Clear the adapter of previous news data
        mAdapter.clear(new ArrayList<Movies>());

        // If there is a valid list of {@link News}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {
        //Clear the existing data
        mAdapter.clear(new ArrayList<Movies>());

    }

}
