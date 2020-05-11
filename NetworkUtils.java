package com.example.android.popularmovies;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.MovieEntry;

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

import javax.xml.transform.Source;

/**
 * Helper methods related to requesting and receiving movie data from MovieDb.
 */
public final class NetworkUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link NetworkUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private NetworkUtils() {
    }

    /**
     * Query the MovieDB and return a list of {@link MovieEntry} objects.
     */
    public static void fetchMovieData(String requestUrl,int flag) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and save them in the Database as MovieEntry objects
        parseJson(jsonResponse,flag);

    }

    private static void parseJson(String jsonResponse,int flag) {
        boolean isChecked = false;

        try {
            JSONObject rootObject = new JSONObject(jsonResponse);
            JSONArray results = rootObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject arrayObject = results.getJSONObject(i);
                String posterPath = arrayObject.getString("poster_path");
                posterPath = "http://image.tmdb.org/t/p/" + "w185" + posterPath;

                String title = arrayObject.getString("title");
              //Get the double voteAverage and convert to String
                double voteAverage = arrayObject.getDouble("vote_average");
                String voteAverageString = Double.toString(voteAverage);

                String releaseDate = arrayObject.getString("release_date");
                String overview = arrayObject.getString("overview");
                //Get the double voteAverage and convert to String
                int movieID = arrayObject.getInt("id");
                String movieIdString = Integer.toString(movieID);

                MovieEntry movieEntry = new MovieEntry(movieIdString,title, overview, posterPath,releaseDate,voteAverageString,flag,isChecked);

                Context context = GlobalApplication.getAppContext();
                AppDatabase mDb = AppDatabase.getInstance(context);
                mDb.movieDao().insertMovie(movieEntry);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    // Query the MovieDB and return a list of {@link Trailer} objects.
    public static String fetchTrailerData(String requestUrl) {
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Trailer}s
        String trailerVideoLink = parseJsonTrailers(jsonResponse);

        // Return the trailer link
        return trailerVideoLink;
    }

    private static String parseJsonTrailers(String jsonResponse) {
        String movieTrailerLink = null;

        try {
            JSONObject rootObject = new JSONObject(jsonResponse);
            JSONArray results = rootObject.getJSONArray("results");


            JSONObject arrayObject = results.getJSONObject(0);
            String trailerKey = arrayObject.getString("key");
            movieTrailerLink = "https://www.youtube.com/watch?v=" + trailerKey;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieTrailerLink;
    }


    // Query the MovieDB and return a list of {@link Review} objects.
    public static List<Reviews> fetchReviewData(String requestUrl) {
        List<Reviews> reviews;

        String reviewsURL = "http://api.themoviedb.org/3/movie/" + requestUrl +"/reviews?api_key=cbac1d3cf3ab1fd31121edfb08623cd1";
        // Create URL object
        URL url = createUrl(reviewsURL);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Reviews}s

        reviews = parseJsonReviews(jsonResponse);

        // Return the list of {@link Review}s
        return reviews;
    }

    private static List<Reviews> parseJsonReviews(String jsonResponse) {
        List<Reviews> reviewsList = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(jsonResponse);
            JSONArray results = rootObject.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject arrayObject = results.getJSONObject(i);

                String reviewAuthor = arrayObject.getString("author");
                String reviewText = arrayObject.getString("content");

                reviewsList.add(new Reviews(reviewAuthor,reviewText));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewsList;

    }



    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
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
            Log.e(LOG_TAG, "Problem retrieving the movie JSON results.", e);
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
}