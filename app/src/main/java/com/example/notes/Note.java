package com.example.notes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note {
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

    private String title;
    private String creationDate;
    private String text;

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        creationDate = dateFormat.format(currentDate);
    }
}
