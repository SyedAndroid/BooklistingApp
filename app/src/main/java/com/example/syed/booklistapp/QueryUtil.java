package com.example.syed.booklistapp;

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
 * Created by syed on 27/05/2017.
 */

public final class QueryUtil {

    private static final String LOG_TAG = QueryUtil.class.getSimpleName();
////An empty private constructor makes sure that the class is not going to be initialised.
    private QueryUtil() {
    }


    public static List<Book> extractFeatureFromJson(String bookJSON) {
        // If the JSON string is empty or null, then return early.

        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding Books
        List<Book> books = new ArrayList<>();


        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features
            JSONArray booksArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < booksArray.length(); i++) {

                JSONObject currentBook = booksArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");


                // Extract the value for the key called "mag"
                String title = volumeInfo.getString("title");
                String authorString="";
                // Extract the value for the key called "place"
                if (volumeInfo.has("authors")) {
                    JSONArray author = volumeInfo.getJSONArray("authors");

                StringBuilder auth = new StringBuilder();
                for (int j = 0; j < author.length(); j++) {
                    if (j > 0) {
                        auth.append(", ");
                    }
                    auth.append(author.getString(j));
                    }
                    authorString= auth.toString();
                }else {
                    authorString="No Author available";
                }
                String desc;
                // Extract the value for the key called "time"
                if (volumeInfo.has("description")) {
                    desc = volumeInfo.getString("description");
                } else {
                    desc = "No descp Available";
                }

                JSONObject thumbnail = volumeInfo.getJSONObject("imageLinks");
                String thumbnailURL = thumbnail.getString("smallThumbnail");

                // Create a new  object with the magnitude, location, time,
                // and url from the JSON response.
                Book book = new Book(title, authorString, desc, thumbnailURL);

                // Add the new  to the list of books.
                books.add(book);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the books JSON results", e);
        }

        // Return the list of books
        return books;
    }

    public static String makeHttpRequest(URL url) throws IOException {
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
            Log.e(LOG_TAG, "Problem retrieving the books JSON results.", e);
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

    public static String readFromStream(InputStream inputStream) throws IOException {
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


    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
}
