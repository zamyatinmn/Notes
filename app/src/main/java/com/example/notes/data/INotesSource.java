package com.example.notes.data;

import com.example.notes.DataChangedListener;

public interface INotesSource {
    INotesSource init(NoteSourceResponse response);

    Note getNote(int position);

    int size();

    void add(Note note);

    void remove(int position);

    void update(int position, Note note);

    void setOnChangedListener(DataChangedListener listener);
}
