package com.example.notes.data;

import com.example.notes.DataChangedListener;

import java.util.ArrayList;
import java.util.List;

public class NoteSourceSupernova implements INotesSource {
    private List<Note> dataSource;
    private DataChangedListener listener;

    public NoteSourceSupernova() {
        dataSource = new ArrayList<>();
    }

    @Override
    public INotesSource init(NoteSourceResponse response) {
        dataSource.add(new Note("Заметка 1", "Заметка разразразраз"));
        dataSource.add(new Note("Заметка 2", "Заметка двадвадвадва"));
        dataSource.add(new Note("Заметка 3", "Заметка тритритритри"));
        dataSource.add(new Note("Заметка 4", "Заметка четыречетыре"));
        dataSource.add(new Note("Заметка 5", "Заметка пятьпятьпять"));
        dataSource.add(new Note("Заметка 6", "Заметка шестьшесть"));
        dataSource.add(new Note("Заметка 7", "Заметка семьсемьсемь"));
        dataSource.add(new Note("Заметка 8", "Заметка восемьвосемь"));
        dataSource.add(new Note("Заметка 9", "Заметка девятьдевять"));

        if (response != null) {
            response.initialized(this);
        }
        return this;
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void add(Note note) {
        listener.onDataAdd(dataSource.size());
        dataSource.add(note);
    }

    @Override
    public void remove(int position) {
        listener.onDataDelete(position);
        dataSource.remove(position);
    }

    @Override
    public Note getNote(int position) {
        return dataSource.get(position);
    }

    @Override
    public void update(int position, Note note) {
        listener.onDataUpdate(position);
        dataSource.set(position, note);
    }

    @Override
    public void setOnChangedListener(DataChangedListener listener) {
        this.listener = listener;
    }
}
