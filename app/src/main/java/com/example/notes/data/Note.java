package com.example.notes.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Note {
    private String id;
    private String title;
    private String creationDate;
    private String text;
    public int color;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        creationDate = dateFormat.format(currentDate);
        color = new Random().nextInt(10);
    }

    public String getTitle() {
        return title;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getText() {
        return text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
