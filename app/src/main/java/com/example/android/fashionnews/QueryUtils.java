package com.example.android.fashionnews;

/**
 * Created by Maria on 22/5/2018.
 */

import android.text.TextUtils;
import android.util.Log;

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

/**
 * Helper methods related to requesting and receiving news from the Guardian.
 */
public final class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /** Constant for Read Time Out */
    private static final int READ_TIME_OUT = 10000;

    /* Constant for Connection Time Out */
    private static final int CONNECT_TIME_OUT = 15000;

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the Guardian dataset and return a list of {@link Fashion} objects.
     */
    public static List<Fashion> fetchFashionData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Fashion)
        List<Fashion> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Fashion)
        return news;
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
            urlConnection.setReadTimeout(READ_TIME_OUT /* milliseconds */);
            urlConnection.setConnectTimeout(CONNECT_TIME_OUT /* milliseconds */);
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
            Log.e(LOG_TAG, "Problem retrieving the Fashion objects JSON results.", e);
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
     * Return a list of {@link Fashion} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Fashion> extractFeatureFromJson(String fashionJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(fashionJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding news to
        List<Fashion> news = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(fashionJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of articles.
            JSONObject response = baseJsonResponse.getJSONObject("response");

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of articles.
            JSONArray results = response.getJSONArray("results");

            // For each article in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < results.length(); i++) {

                // Get a single article at position i within the list of articles
                JSONObject currentNews = results.getJSONObject(i);

                // Extract the value for the key called "id"
                String id = currentNews.optString("webTitle");

                // Extract the value for the key called "webPublicationDate"
                String time = currentNews.optString("webPublicationDate");

                // Extract the value for the key called "webUrl"
                String url = currentNews.optString("webUrl");

                // Extract the value for the key called sectionName
                String sectionName = currentNews.optString("sectionName");

                // Initialize the author String variable
                String author="";
                // Get the tags array
                JSONArray tags = currentNews.getJSONArray("tags");
                // For each tag, find the webTitle, which is the author name, and store it in
                //the author variable
                for(int j=0;j<tags.length();j++) {
                    JSONObject currentTag = tags.getJSONObject(j);
                    if (currentTag.has("webTitle")) {
                        author = currentTag.getString("webTitle");
                    }
                }

                // Create a new {@link Fashion} object with the title, date,
                // and url from the JSON response.
                Fashion fashion = new Fashion(id, time, url, sectionName, author);

                // Add the new {@link Fashion} to the list of articles.
                news.add(fashion);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Guardian JSON results", e);
        }
        // Return the list of news
        return news;
    }

}