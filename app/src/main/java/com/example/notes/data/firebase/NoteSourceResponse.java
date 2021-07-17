package com.example.notes.data.firebase;

import com.example.notes.data.INotesSource;

public interface NoteSourceResponse {
    void initialized(INotesSource notesSource);
}
