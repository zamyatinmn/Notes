package com.example.notes.data;

import com.example.notes.DataChangedListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoteSourceFirebase implements INotesSource {
    static String NOTES_COLLECTION = "notes";
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    CollectionReference reference = firestore.collection(NOTES_COLLECTION);
    private List<Note> data = new ArrayList<>();
    private DataChangedListener listener;

    @Override
    public NoteSourceFirebase init(NoteSourceResponse response) {
        data = new ArrayList<>();
        reference.orderBy(NoteDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (QueryDocumentSnapshot snap : task.getResult()) {
                            Map<String, Object> map = snap.getData();
                            String id = snap.getId();
                            Note note = NoteDataMapping.toNote(id, map);
                            data.add(note);
                        }
                    }
                    response.initialized(NoteSourceFirebase.this);
                });
        return this;
    }

    @Override
    public int size() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public void add(Note note) {
        listener.onDataAdd(data.size());
        data.add(note);
        reference.add(NoteDataMapping.toDocument(note))
                .addOnSuccessListener(documentReference -> note.setId(documentReference.getId()));
    }

    @Override
    public void update(int position, Note note) {
        listener.onDataUpdate(position);
        data.set(position, note);
        reference.document(data.get(position).getId()).set(NoteDataMapping.toDocument(note));
    }

    @Override
    public void setOnChangedListener(DataChangedListener listener) {
        this.listener = listener;
    }

    @Override
    public void remove(int position) {
        listener.onDataDelete(position);
        reference.document(data.get(position).getId()).delete();
        data.remove(position);
    }

    @Override
    public Note getNote(int position) {
        return data.get(position);
    }
}
