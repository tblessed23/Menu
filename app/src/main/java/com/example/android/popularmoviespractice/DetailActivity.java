package com.example.android.popularmoviespractice;

import android.content.Intent;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviespractice.adapters.ReviewAdapter;
import com.example.android.popularmoviespractice.adapters.TrailerAdapter;
import com.example.android.popularmoviespractice.loaders.ReviewsLoader;
import com.example.android.popularmoviespractice.loaders.TrailersLoader;
import com.example.android.popularmoviespractice.tables.AppDatabase;
import com.example.android.popularmoviespractice.tables.AppExecutors;
import com.example.android.popularmoviespractice.tables.Favorites;
import com.example.android.popularmoviespractice.tables.Movies;
import com.example.android.popularmoviespractice.tables.Reviews;
import com.example.android.popularmoviespractice.tables.Trailers;
import com.example.android.popularmoviespractice.utilities.NetworkHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();


    TextView movieTitleTextView;
    TextView releaseDateTextView;
    TextView userRatingTextView;
    TextView plotSynopsisTextView;
    ImageView moviePosterImageView;
    Button mButton;

    private Movies movies;

    String movietitle;
    String releasedate;
    String userrating;
    String synopsis;
    int movieId;
    String mImageBaseUrl = "http://image.tmdb.org/t/p/w185";

    /**
     * Recyclerview for Reviews
     */

    RecyclerView movieReviewsRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ReviewAdapter mReviewAdapter;



    /**
     * RecyclerView for Trailers
     */
    RecyclerView trailersRecyclerView;
    private TrailerAdapter mTrailerAdapter;

    //Member variable for the Database
    private AppDatabase mDb;

    private static final int REVIEWS_LOADER_ID = 1;
    private static final int TRAILERS_LOADER_ID = 2;

    /**
     * URL for data from the themoviedb.org website
     */

    private static final String MOVIES_REQUEST_URL = "https://api.themoviedb.org/3/movie";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Initialize Database
        mDb = AppDatabase.getInstance(getApplicationContext());
        mButton = findViewById(R.id.buttonSave);



        LiveData<Favorites> currentId = mDb.favoritesDao().loadTaskById(movieId);
        currentId.observe(this, new Observer<Favorites>() {
            @Override
            public void onChanged(Favorites favorites) {
                if (favorites !=null && favorites.getId()==movieId){
                    mButton.setClickable(false);
                }else {
                    mButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            saveButton();
                        }
                    });
                }
            }
        });

/**
 * Begin Recyclerview for Reviews
 */
        //  // Find a reference to the {@link RecyclerView} in the layout
        movieReviewsRecyclerView = (RecyclerView) findViewById(R.id.movie_reviews);

        // use a grid layout manager
        layoutManager = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);
        movieReviewsRecyclerView.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        movieReviewsRecyclerView.setHasFixedSize(true);

        // Create a new adapter that takes an empty list of moviess as input
        mReviewAdapter = new ReviewAdapter(this, new ArrayList<Reviews>());
        movieReviewsRecyclerView.setAdapter(mReviewAdapter);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        movieReviewsRecyclerView.setAdapter(mReviewAdapter);

/**
 * End Recyclerview  for Reviews
 */

/**
 * Begin Recyclerview for Trailers
 */
        //  // Find a reference to the {@link RecyclerView} in the layout
        trailersRecyclerView = (RecyclerView) findViewById(R.id.movie_trailers);

        // use a grid layout manager
        LinearLayoutManager layoutTrailerManager = new LinearLayoutManager(this);
        trailersRecyclerView.setLayoutManager(layoutTrailerManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        trailersRecyclerView.setHasFixedSize(true);

        // Create a new adapter that takes an empty list of moviess as input
        mTrailerAdapter = new TrailerAdapter(new ArrayList<Trailers>(), this);
        trailersRecyclerView.setAdapter(mTrailerAdapter);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        trailersRecyclerView.setAdapter(mTrailerAdapter);

/**
 * End Recyclerview  for Trailers
 */

        moviePosterImageView = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        // Using getParcelableExtra(String key) method
        if (intent.hasExtra(getResources().getString(R.string.intent_key))) {
            movies = intent.getParcelableExtra(getResources().getString(R.string.intent_key));
            Picasso.get().load(mImageBaseUrl + movies.getmImage()).resize(500, 750).into(moviePosterImageView);

            movietitle = movies.getmTitle();
            releasedate = movies.getmReleaseDate();
            userrating = movies.getmUserRating();
            synopsis = movies.getmSynopsis();
            movieId = movies.getId();
        }

        if (movies == null) {
            // Movie data unavailable
            closeOnError();
            return;
        }

        populateUI(movies);

        setTitle(movies.getmTitle());

        if (NetworkHelper.networkIsAvailable(this)) {

            LoaderManager loaderReviewManager = getSupportLoaderManager();
            loaderReviewManager.initLoader(REVIEWS_LOADER_ID, null, reviewLoader);

            LoaderManager loaderTrailerManager = getSupportLoaderManager();
            loaderTrailerManager.initLoader(TRAILERS_LOADER_ID, null, trailerLoader);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LiveData<Favorites> currentId = mDb.favoritesDao().loadTaskById(movieId);
        currentId.observe(this, new Observer<Favorites>() {
            @Override
            public void onChanged(Favorites favorites) {
                if (favorites !=null && favorites.getId()==movieId){
                    mButton.setEnabled(false);
                }else {
                    mButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            saveButton();
                        }
                    });
                }
            }
        });
    }



    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */

    public void saveButton() {

        String titleFavorities = movietitle;
        int id = movieId;

        final Favorites favorites = new Favorites(id, titleFavorities);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoritesDao().insertFavorites(favorites);
                finish();
            }
        });

    }

    //populate the user interface
    private void populateUI(Movies movies) {

        //Set the Text of the Movie Object Variables
        movieTitleTextView = findViewById(R.id.movietitle_tv);
        movieTitleTextView.setText(movietitle);

        releaseDateTextView = findViewById(R.id.release_date_tv);
        releaseDateTextView.setText(releasedate);


        userRatingTextView = findViewById(R.id.user_rating_tv);
        userRatingTextView.setText(userrating);

        plotSynopsisTextView = findViewById(R.id.plot_synopsis_tv);
        plotSynopsisTextView.setText(TextUtils.join(",", Collections.singleton(synopsis)));

    }



    private LoaderManager.LoaderCallbacks<List<Reviews>> reviewLoader
            = new LoaderManager.LoaderCallbacks<List<Reviews>>() {
        @Override
        public Loader<List<Reviews>> onCreateLoader(int i, Bundle bundle) {

            // parse breaks apart the URI string that's passed into its parameter
            Uri baseUri = Uri.parse(MOVIES_REQUEST_URL);

            // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
            Uri.Builder uriBuilder = baseUri.buildUpon();

            // Append query parameter and its value.
            uriBuilder.appendPath(String.valueOf(movieId));
            uriBuilder.appendEncodedPath(getResources().getString(R.string.reviews));
            uriBuilder.appendQueryParameter(getResources().getString(R.string.api_key), "543e8145fb4bd3a4d9f616fb389b7356");
            uriBuilder.appendQueryParameter(getResources().getString(R.string.language), "en-US");

            // Return the completed url
            return new ReviewsLoader(getApplicationContext(), uriBuilder.toString());
        }

        @Override
        public void onLoadFinished(Loader < List < Reviews >> loader, List < Reviews > reviews) {


            // Clear the adapter of previous movie data
            mReviewAdapter.clear(new ArrayList<Reviews>());

            // If there is a valid list of {@link Movies}s, then add them to the adapter's
            // data set. This will trigger the RecyclerView to update.
            if (reviews != null && !reviews.isEmpty()) {
                mReviewAdapter.setReviewData(reviews);
            }
        }



        @Override
        public void onLoaderReset(Loader<List<Reviews>> loader) {
            //Clear the existing data
            mReviewAdapter.clear(new ArrayList<Reviews>());
        }


    };

    private LoaderManager.LoaderCallbacks<List<Trailers>> trailerLoader
            = new LoaderManager.LoaderCallbacks<List<Trailers>>() {
        @Override
        public Loader<List<Trailers>> onCreateLoader(int i, Bundle bundle) {

            // parse breaks apart the URI string that's passed into its parameter
            Uri baseUri = Uri.parse(MOVIES_REQUEST_URL);

            // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
            Uri.Builder uriBuilder = baseUri.buildUpon();

            // Append query parameter and its value.
            uriBuilder.appendPath(String.valueOf(movieId));
            uriBuilder.appendEncodedPath(getResources().getString(R.string.videos));
            uriBuilder.appendQueryParameter(getResources().getString(R.string.api_key), "543e8145fb4bd3a4d9f616fb389b7356");
            uriBuilder.appendQueryParameter(getResources().getString(R.string.language), "en-US");

            // Return the completed url
            return new TrailersLoader(getApplicationContext(), uriBuilder.toString());
        }

        @Override
        public void onLoadFinished(Loader < List < Trailers >> loader, List < Trailers > trailers) {


            // Clear the adapter of previous movie data
            mTrailerAdapter.clear(new ArrayList<Trailers>());

            // If there is a valid list of {@link Movies}s, then add them to the adapter's
            // data set. This will trigger the RecyclerView to update.
            if (trailers != null && !trailers.isEmpty()) {
                mTrailerAdapter.setReviewData(trailers);
            }
        }



        @Override
        public void onLoaderReset(Loader<List<Trailers>> loader) {
            //Clear the existing data
            mTrailerAdapter.clear(new ArrayList<Trailers>());
        }
    };
}