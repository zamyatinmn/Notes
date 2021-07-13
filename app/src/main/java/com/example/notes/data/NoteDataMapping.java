package com.example.notes.data;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {
    public static class Fields {
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String TEXT = "text";
    }

    public static Note toNote(String id, Map<String, Object> document) {
        String title = (String) document.get(Fields.TITLE);
        String text = (String) document.get(Fields.TEXT);
        String date = (String) document.get(Fields.DATE);
        Note note = new Note(title, text);
        note.setCreationDate(date);
        note.setId(id);
        return note;
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> document = new HashMap<>();
        document.put(Fields.TITLE, note.getTitle());
        document.put(Fields.DATE, note.getCreationDate());
        document.put(Fields.TEXT, note.getText());
        return document;
    }
}
