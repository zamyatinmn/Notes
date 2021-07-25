package com.example.notes.data.firebase;

import com.example.notes.data.Note;
import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class NoteDataMapping {
    public static class Fields {
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String TEXT = "text";
        public static final String COLOR = "color";
    }

    public static Note toNote(String id, Map<String, Object> document) {
        String title = (String) document.get(Fields.TITLE);
        String text = (String) document.get(Fields.TEXT);
        Timestamp date = (Timestamp) document.get(Fields.DATE);
        Long color1 = (Long) document.get(Fields.COLOR);
        int color = color1.intValue();
        Note note = new Note(title, text);
        assert date != null;
        note.setCreationDate(date.toDate());
        note.setId(id);
        note.setColor(color);
        return note;
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> document = new HashMap<>();
        document.put(Fields.TITLE, note.getTitle());
        document.put(Fields.DATE, note.getCreationDate());
        document.put(Fields.TEXT, note.getText());
        document.put(Fields.COLOR, note.getColor());
        return document;
    }
}
