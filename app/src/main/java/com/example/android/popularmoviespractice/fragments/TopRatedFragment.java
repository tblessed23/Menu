package com.example.android.popularmoviespractice.fragments;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmoviespractice.utilities.NetworkHelper;
import com.example.android.popularmoviespractice.R;
import com.example.android.popularmoviespractice.adapters.MovieAdapter;
import com.example.android.popularmoviespractice.loaders.MoviesLoader;
import com.example.android.popularmoviespractice.tables.Movies;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 //* //Use the {@link TopRatedFragment#} factory method to
 * create an instance of this fragment.
 */
public class TopRatedFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Movies>> {

    private RecyclerView recyclerView;
    private MovieAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;
    private static final int MOVIESARTICLE_LOADER_ID = 1;

    /**
     * URL for data data from the themoviedb.org website
     */

    private static final String MOVIES_REQUEST_URL = "https://api.themoviedb.org/3/movie/top_rated";

    public TopRatedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_category, container, false);

        setHasOptionsMenu(true);

        //  // Find a reference to the {@link RecyclerView} in the layout
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);

        // use a grid layout manager
        layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // Create a new adapter that takes an empty list of moviess as input
        mAdapter = new MovieAdapter(getActivity(), new ArrayList<Movies>());
        recyclerView.setAdapter(mAdapter);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }
    /**
     * This method will make the error message visible and hide the movies
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        /* First, hide the currently visible data */
        recyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mEmptyStateTextView.setVisibility(View.VISIBLE);
    }

    //This method is only used in Fragments
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(NetworkHelper.networkIsAvailable(getActivity())){

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(MOVIESARTICLE_LOADER_ID, null, this);
        } else {
            View loadingIndicator = getView().findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }


    @Override
    public Loader<List<Movies>> onCreateLoader (int i, Bundle bundle){

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(MOVIES_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value.
        uriBuilder.appendQueryParameter((getResources().getString(R.string.api_key)), "543e8145fb4bd3a4d9f616fb389b7356");
        uriBuilder.appendQueryParameter((getResources().getString(R.string.language)), "en-US");



        // Return the completed url
        return new MoviesLoader(getContext(), uriBuilder.toString());
    }


    @Override
    public void onLoadFinished (Loader < List < Movies >> loader, List < Movies > movies){
        //Hide Progress Bar (loading indicator) to gone

        View loadingIndicator = getView().findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No movies found."
        if (movies == null) {
            showErrorMessage();
            mEmptyStateTextView.setText(R.string.no_movies);
        }

        // Clear the adapter of previous movie data
        mAdapter.clear(new ArrayList<Movies>());

        // If there is a valid list of {@link Movies}s, then add them to the adapter's
        // data set. This will trigger the RecyclerView to update.
        if (movies != null && !movies.isEmpty()) {
            mAdapter.setMovieData(movies);
        }
    }


    @Override
    public void onLoaderReset (Loader <List< Movies >> loader) {
        //Clear the existing data
        mAdapter.clear(new ArrayList<Movies>());
    }
}









