package com.example.notes.data.firebase

import com.example.notes.data.DataChangedListener
import com.example.notes.data.INotesSource
import com.example.notes.data.Note
import com.example.notes.data.firebase.NoteSourceFirebase
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import java.util.*

class NoteSourceFirebase : INotesSource {
    private val firestore = FirebaseFirestore.getInstance()
    private val reference = firestore.collection(NOTES_COLLECTION)

    private var data: MutableList<Note> = ArrayList()
    private var listener: DataChangedListener? = null
    override fun init(response: NoteSourceResponse): NoteSourceFirebase {
        data = ArrayList()
        reference.orderBy(NoteDataMapping.Fields.DATE, Query.Direction.DESCENDING).get()
            .addOnCompleteListener {
                it.result?.let { result ->
                    if (it.isSuccessful){
                        for (snap in result) {
                            val map = snap.data
                            val id = snap.id
                            val note = NoteDataMapping.toNote(id, map)
                            data.add(note)
                        }
                    }
                }
                response.initialized(this@NoteSourceFirebase)
            }
        return this
    }

    override fun size(): Int {
        return data.size
    }

    override fun add(note: Note) {
        listener?.onDataAdd(data.size)
        data.add(note)
        reference.add(NoteDataMapping.toDocument(note))
            .addOnSuccessListener {
                note.id = it.id
            }
    }

    override fun update(position: Int, note: Note) {
        listener?.onDataUpdate(position)
        data[position] = note
        data[position].id?.let { reference.document(it).set(NoteDataMapping.toDocument(note)) }
    }

    override fun setOnChangedListener(listener: DataChangedListener) {
        this.listener = listener
    }

    override fun remove(position: Int) {
        listener?.onDataDelete(position)
        data[position].id?.let { reference.document(it).delete() }
        data.removeAt(position)
    }

    override fun getNote(position: Int): Note {
        return data[position]
    }

    companion object {
        var NOTES_COLLECTION = "notes"
    }
}