package com.example.notes.data

import com.example.notes.data.firebase.NoteSourceResponse

interface INotesSource {
    fun init(response: NoteSourceResponse): INotesSource
    fun getNote(position: Int): Note
    fun size(): Int
    fun add(note: Note)
    fun remove(position: Int)
    fun update(position: Int, note: Note)
    fun setOnChangedListener(listener: DataChangedListener)
}