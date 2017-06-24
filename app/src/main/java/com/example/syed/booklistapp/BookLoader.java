package com.example.syed.booklistapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.syed.booklistapp.MainActivity.urlString;
import static com.example.syed.booklistapp.QueryUtil.makeHttpRequest;

/**
 * Created by syed on 28/05/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    public String searchTerm;

    public BookLoader(Context context, String search) {
        super(context);
        searchTerm = search;
    }

    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        URL url = QueryUtil.createUrl(urlString + searchTerm);
        String JSONResponse = "";
        try {
            JSONResponse = makeHttpRequest(url);
            Log.e(JSONResponse, "hello");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (ArrayList<Book>) QueryUtil.extractFeatureFromJson(JSONResponse);
    }
}
