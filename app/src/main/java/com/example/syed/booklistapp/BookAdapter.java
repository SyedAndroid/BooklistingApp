package com.example.syed.booklistapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by syed on 27/05/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.title);

        title.setText(currentBook.getTitle());
        TextView author = (TextView) listItemView.findViewById(R.id.author);

        author.setText(currentBook.getAuthor());

        TextView desc = (TextView) listItemView.findViewById(R.id.desc);

        desc.setText(currentBook.getDesc());
        ImageView image = (ImageView) listItemView.findViewById(R.id.thumbnail);

        Picasso.with(this.getContext())
                .load(currentBook.getThumbnailURL())
                .into(image);

        return listItemView;
    }
}
