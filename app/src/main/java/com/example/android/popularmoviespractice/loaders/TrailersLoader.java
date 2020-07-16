package com.example.android.popularmoviespractice.loaders;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.popularmoviespractice.tables.Reviews;
import com.example.android.popularmoviespractice.tables.Trailers;
import com.example.android.popularmoviespractice.utilities.JsonUtils;
import com.example.android.popularmoviespractice.utilities.ReviewUtils;
import com.example.android.popularmoviespractice.utilities.TrailersUtils;

import java.util.List;

public class TrailersLoader extends AsyncTaskLoader<List<Trailers>>  {
    /** Tag for log messages */
    private static final String LOG_TAG = TrailersLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link TrailersLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */

    public TrailersLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }




    @Override
    public List<Trailers> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of movies.
        List<Trailers> trailers = TrailersUtils.fetchTrailerData(mUrl);
        return trailers;
    }
}

