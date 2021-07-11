package com.example.notes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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
    public int color;

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        creationDate = dateFormat.format(currentDate);
        color = new Random().nextInt(10);
    }

    public static List<Note> saved = new ArrayList<>();
    public static int current = 0;

    static {
        saved.add(new Note("Заметка 1", "Заметка разразразраз"));
        saved.add(new Note("Заметка 2", "Заметка двадвадвадва"));
        saved.add(new Note("Заметка 3", "Заметка тритритритри"));
        saved.add(new Note("Заметка 4", "Заметка четыречетыре"));
        saved.add(new Note("Заметка 5", "Заметка пятьпятьпять"));
        saved.add(new Note("Заметка 6", "Заметка шестьшесть"));
        saved.add(new Note("Заметка 7", "Заметка семьсемьсемь"));
        saved.add(new Note("Заметка 8", "Заметка восемьвосемь"));
        saved.add(new Note("Заметка 9", "Заметка девятьдевять"));
    }
}
