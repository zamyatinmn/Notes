package com.example.notes;

import android.app.Application;

import com.example.notes.data.INotesSource;
import com.example.notes.data.firebase.NoteSourceFirebase;

public class App extends Application {
    public INotesSource notesSource;

    @Override
    public void onCreate() {
        notesSource = new NoteSourceFirebase();
        super.onCreate();
    }
}
