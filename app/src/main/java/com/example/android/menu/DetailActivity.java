package com.example.android.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

//public static final String EXTRA_POSITION = "extra_position";
    //private static final int DEFAULT_POSITION = -1;

    TextView movieTitleTextView;
    TextView releaseDateTextView;
    TextView userRatingTextView;
    TextView plotSynopsisTextView;
    ImageView moviePosterImageView;


    String movietitle;
    String releasedate;
    String userrating;
    String synopsis;
    String movieposter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        moviePosterImageView = findViewById(R.id.image_iv);


        //if (intent == null) {
        //   closeOnError();
        //}

        //int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        //if (position == DEFAULT_POSITION) {

        // EXTRA_POSITION not found in intent
        //  closeOnError();
        //return;
        //}


        // Using getParcelableExtra(String key) method
        Bundle data = getIntent().getExtras();
        Movies movies = (Movies) data.getParcelable("Movies");

        //Collect all property values from 'Movies'
        movietitle = movies.getmTitle();
        releasedate = movies.getmReleaseDate();
        userrating = movies.getmUserRating();
        synopsis = movies.getmSynopsis();
        movieposter = movies.getmImage();


        if (movies == null) {
            // Movie data unavailable
            closeOnError();
            return;
        }

        populateUI(movies);

        Picasso.with(this)
                .load(movieposter)
                .into(moviePosterImageView);

        setTitle(movies.getmTitle());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //populate the user interface
    private void populateUI(Movies movies) {


        //moviePosterImageView.setImageResource(movieposter);

        //Set the Text of the Sandwich Object Variables
        movieTitleTextView = findViewById(R.id.movietitle_tv);
        movieTitleTextView.setText(movietitle);
        //TextView alsoknownaslabel = findViewById(R.id.movie_title_label);

        releaseDateTextView = findViewById(R.id.release_date_tv);
        releaseDateTextView.setText(releasedate);
        //TextView originlabel = findViewById(R.id.release_date_label);

        userRatingTextView = findViewById(R.id.user_rating_tv);
        userRatingTextView.setText(userrating);

        plotSynopsisTextView = findViewById(R.id.plot_synopsis_tv);
        plotSynopsisTextView.setText(synopsis);
    }
}