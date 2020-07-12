package com.example.android.popularmoviespractice;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviespractice.adapters.MovieAdapter;
import com.example.android.popularmoviespractice.adapters.ReviewAdapter;
import com.example.android.popularmoviespractice.loaders.ReviewsLoader;
import com.example.android.popularmoviespractice.tables.Movies;
import com.example.android.popularmoviespractice.tables.Reviews;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Reviews>> {

    TextView movieIdTextView;
    TextView movieTitleTextView;
    TextView releaseDateTextView;
    TextView userRatingTextView;
    TextView plotSynopsisTextView;
    ImageView moviePosterImageView;
    TextView movieReviewAuthorTextView;
    RecyclerView movieReviewsRecyclerView;
    //RecyclerView movieReviewsAuthorRecyclerView;
    private RecyclerView.LayoutManager layoutManager;


    private Movies movies;
    public List<Reviews> reviews;

  private ReviewAdapter mReviewAdapter;

    String movietitle;
    String releasedate;
    String userrating;
    String synopsis;
    int movieId;
   // String author;
    String content;
  //int reviewsId;
    String mImageBaseUrl = "http://image.tmdb.org/t/p/w185";

    /**
     * URL for data from the themoviedb.org website
     */

    private static final String REVIEWS_REQUEST_URL = "https://api.themoviedb.org/3/movie/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        // Find a reference to the {@link RecyclerView} in the layout
        movieReviewsRecyclerView = (RecyclerView) findViewById(R.id.movie_reviews);
//
//
//
//        // use a grid layout manager
        layoutManager = new GridLayoutManager(this, 1, RecyclerView.HORIZONTAL, false);
        movieReviewsRecyclerView.setLayoutManager(layoutManager);
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
        movieReviewsRecyclerView.setHasFixedSize(true);
//
//        // Create a new adapter that takes an empty list of moviess as input
       mReviewAdapter = new ReviewAdapter(this, new ArrayList<Reviews>(movieId));
        movieReviewsRecyclerView.setAdapter(mReviewAdapter);



        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        movieReviewsRecyclerView.setAdapter(mReviewAdapter);


        moviePosterImageView = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
           closeOnError();
        }

        // Using getParcelableExtra(String key) method
        if (intent.hasExtra(getResources().getString(R.string.intent_key))) {
             movies = intent.getParcelableExtra(getResources().getString(R.string.intent_key));
            Picasso.get().load(mImageBaseUrl + movies.getmImage()).resize(500,750).into(moviePosterImageView);

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
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
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


//        StringBuilder sk =  new StringBuilder(100);
//        sk.append("https://api.themoviedb.org/3/movie/");
//        sk.append(movieId);
//        sk.append("/reviews");

//              movieIdTextView = findViewById(R.id.movie_id);
//        movieIdTextView.setText(Integer.toString(movieId));


//        movieReviewTextViewString = findViewById(R.id.movie_reviews_string);
//        movieIdTextView.setText(sk);


        //if (movieId == reviewsId) {
            //movieReviewsAuthorRecyclerView = findViewById(R.id.movie_reviews_author);
            //movieReviewsAuthorRecyclerView.setVisibility(View.VISIBLE);
        if (reviews != null ) {
            movieReviewsRecyclerView.setVisibility(View.VISIBLE);

        }

        //}

    }


    @Override
    public Loader<List<Reviews>> onCreateLoader (int i, Bundle bundle) {
        // parse breaks apart the URI string that's passed into its parameter
        Uri reviewsBaseUri = Uri.parse(REVIEWS_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = reviewsBaseUri.buildUpon();

        // Append query parameter and its value.
        uriBuilder.appendEncodedPath(Integer.toString(movieId));
        uriBuilder.appendEncodedPath("/reviews");
        uriBuilder.appendQueryParameter("api_key", "543e8145fb4bd3a4d9f616fb389b7356");
        uriBuilder.appendQueryParameter("language", "en-US");

        // Return the completed url
        return new ReviewsLoader(getApplicationContext(), uriBuilder.toString());


    }

    @Override
    public void onLoadFinished (Loader<List<Reviews>> loader, List<Reviews> reviews) {

//        // Set empty state text to display "No movies found."
//        if (reviews == null) {
//            showErrorMessage();
//            mEmptyStateTextView.setText(R.string.no_movies);
//        }

        // Clear the adapter of previous movie data
        mReviewAdapter.clear(new ArrayList<Reviews>());

        // If there is a valid list of {@link Movies}s, then add them to the adapter's
        // data set. This will trigger the RecyclerView to update.
        if (reviews != null && !reviews.isEmpty()) {
            mReviewAdapter.setReviewData(reviews);
        }
    }

    @Override
    public void onLoaderReset (Loader<List<Reviews>> loader) {
        //Clear the existing data
        mReviewAdapter.clear(new ArrayList<Reviews>());

    }
}