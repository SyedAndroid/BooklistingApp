package com.example.syed.booklistapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syed on 29/05/2017.
 */

public class SearchResults extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private String QueryTitle;
    private ArrayAdapter<Book> bookAdapter;
    private TextView emptyTextView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        QueryTitle = MainActivity.query;
        ListView listView = (ListView) findViewById(R.id.list);
        bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        emptyTextView = (TextView) findViewById(R.id.empty_view);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(1, null, this);
        } else {


            emptyTextView.setText("No Internet Connection");
        }


        listView.setAdapter(bookAdapter);

    }


    @Override
    public android.content.Loader<List<Book>> onCreateLoader(int id, Bundle args) {

        return new BookLoader(this, QueryTitle);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Book>> loader, List<Book> data) {
        bookAdapter.clear();
        if (data != null && !data.isEmpty()) {
            bookAdapter.addAll(data);
        } else {
            TextView emptyTextView = (TextView) findViewById(R.id.empty_view);
            // Set empty state text to display "No books found!"
            emptyTextView.setText("No books found");
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Book>> loader) {
        bookAdapter.clear();
    }
}
