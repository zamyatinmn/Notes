package com.example.notes.data;

import java.util.ArrayList;
import java.util.List;

public class NoteSource implements INotesSource {
    public static List<Note> dataSource;

    public NoteSource() {
        dataSource = new ArrayList<>();
        dataSource.add(new Note("Заметка 1", "Заметка разразразраз"));
        dataSource.add(new Note("Заметка 2", "Заметка двадвадвадва"));
        dataSource.add(new Note("Заметка 3", "Заметка тритритритри"));
        dataSource.add(new Note("Заметка 4", "Заметка четыречетыре"));
        dataSource.add(new Note("Заметка 5", "Заметка пятьпятьпять"));
        dataSource.add(new Note("Заметка 6", "Заметка шестьшесть"));
        dataSource.add(new Note("Заметка 7", "Заметка семьсемьсемь"));
        dataSource.add(new Note("Заметка 8", "Заметка восемьвосемь"));
        dataSource.add(new Note("Заметка 9", "Заметка девятьдевять"));
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void add(Note note) {
        dataSource.add(note);
    }

    @Override
    public void remove(int position) {
        dataSource.remove(position);
    }

    @Override
    public Note getNote(int position) {
        return dataSource.get(position);
    }
}
