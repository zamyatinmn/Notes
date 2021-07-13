package com.example.notes.data;

public interface INotesSource {
    INotesSource init(NoteSourceResponse response);

    Note getNote(int position);

    int size();

    void add(Note note);

    void remove(int position);

    void update(int position, Note note);
}
