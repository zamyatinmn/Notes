package com.example.notes.data.firebase

import com.example.notes.data.Note
import com.google.firebase.Timestamp
import java.util.*

object NoteDataMapping {
    fun toNote(id: String?, document: Map<String?, Any?>): Note {
        val title = (document[Fields.TITLE] as String?)?: "null"
        val text = (document[Fields.TEXT] as String?)?: "null"
        val date = (document[Fields.DATE] as Timestamp?)?: Timestamp(0, 0)
        val tempColor = (document[Fields.COLOR] as Long?)?: 1L
        val color = tempColor.toInt()
        val note = Note(title, text)
        note.creationDate = date.toDate()
        note.id = id
        note.color = color
        return note
    }

    fun toDocument(note: Note): Map<String, Any> {
        val document: MutableMap<String, Any> = HashMap()
        document[Fields.TITLE] = note.title
        document[Fields.DATE] = note.creationDate
        document[Fields.TEXT] = note.text
        document[Fields.COLOR] = note.color
        return document
    }

    object Fields {
        const val TITLE = "title"
        const val DATE = "date"
        const val TEXT = "text"
        const val COLOR = "color"
    }
}