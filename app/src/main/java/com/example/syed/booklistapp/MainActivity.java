package com.example.syed.booklistapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.syed.booklistapp.QueryUtil.makeHttpRequest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String urlString = "https://www.googleapis.com/books/v1/volumes?q=";
    public BookAdapter mAdapter;
    ListView BookListView;
    TextView mEmptyStateTextView;
    EditText keyword;
    public static String query;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchButton = (Button) findViewById(R.id.button);
        searchButton.setOnClickListener(this);
        keyword = (EditText) findViewById(R.id.search);

    }


    @Override
    public void onClick(View v) {
        query = keyword.getText().toString();

        // Start BookListActivity in order to show the query result
        Intent intent = new Intent(MainActivity.this, SearchResults.class);
        startActivity(intent);
    }
}


