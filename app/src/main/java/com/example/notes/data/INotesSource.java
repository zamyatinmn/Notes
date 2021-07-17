package com.example.notes.data;

import com.example.notes.data.Note;

public interface INotesSource {
    int size();

    void add(Note note);

    void remove(int position);

    Note getNote(int position);
}
