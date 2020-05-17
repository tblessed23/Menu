package com.example.android.menu;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class MoviesLoader extends AsyncTaskLoader<List<Movies>> {

    /** Tag for log messages */
    private static final String LOG_TAG = MoviesLoader.class.getName();


    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link MoviesLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public MoviesLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Movies> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news stories.
        List<Movies> news = JsonUtils.fetchEarthquakeData(mUrl);
        return news;
    }}

