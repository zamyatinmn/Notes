package com.example.notes.data.firebase

import com.example.notes.data.INotesSource

interface NoteSourceResponse {
    fun initialized(notesSource: INotesSource)
}