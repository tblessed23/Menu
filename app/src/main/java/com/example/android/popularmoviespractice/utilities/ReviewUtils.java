package com.example.android.popularmoviespractice.utilities;

import android.text.TextUtils;
import android.util.Log;


import com.example.android.popularmoviespractice.tables.Reviews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ReviewUtils {
    private static final String LOG_TAG = ReviewUtils.class.getSimpleName();

    /**
     * Query the themoviedb.org dataset and return a list of {@link Reviews} objects.
     */
    public static List<Reviews> fetchMovieData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Movie}s
        List<Reviews> Reviews = extractReviews(jsonResponse);

        // Return the list of {@link Earthquake}s
        return Reviews;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Reviews} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Reviews> extractReviews(String ReviewsJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(ReviewsJSON)) {
            return null;
        }


        // Create an empty ArrayList that we can start adding Reviews to
        List<Reviews> Reviews = new ArrayList<>();

        // Try to parse the JSON Response. If there's a problem with
        // the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error
        // message to the logs.
        try {

            // Build up a list of Movie objects with the corresponding
            //data.

            JSONObject root = new JSONObject(ReviewsJSON);
            JSONArray resultsArray = root.getJSONArray("results");


            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject currentMovie = resultsArray.getJSONObject(i);

                //int movieId = currentMovie.optInt("id");
                String author = currentMovie.getString("author");
                String content = currentMovie.getString("content");


                Reviews newmovie = new Reviews(author, content);
                Reviews.add(newmovie);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above
            //statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log
            // message
            // with the message from the exception.
            Log.e("JsonUtils", "Problem parsing the news JSON results", e);
        }

        // Return the list of news
        return Reviews;
    }



}




