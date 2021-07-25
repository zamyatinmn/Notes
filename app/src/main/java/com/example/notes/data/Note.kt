package com.example.notes.data;

import java.util.Date;
import java.util.Random;

public class Note {
    private String id;
    private String title;
    private Date creationDate;
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
        creationDate = new Date();
        newColor();
    }

    public Note() {
        this.title = "";
        this.text = "";
        creationDate = new Date();
        newColor();
    }

    public String getTitle() {
        return title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getText() {
        return text;
    }

    public int getColor() {
        return color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void newColor() {
        color = new Random().nextInt(10);
    }
}
