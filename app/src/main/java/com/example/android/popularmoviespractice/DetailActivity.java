package com.example.android.popularmoviespractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView movieTitleTextView;
    TextView releaseDateTextView;
    TextView userRatingTextView;
    TextView plotSynopsisTextView;
    ImageView moviePosterImageView;

    private Movies movies;
    String movietitle;
    String releasedate;
    String userrating;
    String synopsis;
    String movieposter;
    String mImageBaseUrl = "http://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        moviePosterImageView = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
           closeOnError();
        }

        // Using getParcelableExtra(String key) method
        if (intent.hasExtra("Movies")) {

            //Bundle data = getIntent().getExtras();
             //movies = (Movies) data.getParcelable(Intent.EXTRA_INTENT);
             movies = intent.getParcelableExtra("Movies");
            Picasso.get().load(mImageBaseUrl + movies.getmImage()).into(moviePosterImageView);
            movietitle = movies.getmTitle();
            releasedate = movies.getmReleaseDate();
            userrating = movies.getmUserRating();
           synopsis = movies.getmSynopsis();
        }

        if (movies == null) {
            // Movie data unavailable
            closeOnError();
            return;
        }

        populateUI(movies);
     //   Movies movies = intent.getParcelableExtra("Movies");

//        if (movies != null) {
//            //Collect all property values from 'Movies'
//            movietitle = movies.getmTitle();
//            releasedate = movies.getmReleaseDate();
//            userrating = movies.getmUserRating();
//            synopsis = movies.getmSynopsis();
//            movieposter = movies.getmImage();
//        }

//        if (movies == null) {
//            // Movie data unavailable
//            closeOnError();
//            return;
//        }

//        populateUI(movies);

//       Picasso.get()
//               .load(movieposter)
//               .into(moviePosterImageView);

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