package com.example.syed.booklistapp;

import android.media.Image;

/**
 * Created by syed on 27/05/2017.
 */

public class Book {

    private String title;
    private String author;
    private String desc;
    private String thumbnailURL;

    public Book(String title, String author, String desc, String thumbnailURL) {
        this.title = title;
        this.author = author;
        this.desc = desc;
        this.thumbnailURL = thumbnailURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
