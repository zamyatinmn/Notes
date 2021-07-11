package com.example.notes;

import android.app.Application;

import com.example.notes.data.INotesSource;
import com.example.notes.data.NoteSource;

public class App extends Application {
    public INotesSource notesSource;

    @Override
    public void onCreate() {
        notesSource = new NoteSource();
        super.onCreate();
    }
}
