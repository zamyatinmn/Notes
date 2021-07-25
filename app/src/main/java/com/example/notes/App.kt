package com.example.notes

import android.app.Application
import com.example.notes.data.INotesSource
import com.example.notes.data.firebase.NoteSourceFirebase

class App : Application() {
    lateinit var notesSource: INotesSource
    var isAuthorized = false

    override fun onCreate() {
        notesSource = NoteSourceFirebase()
        super.onCreate()
    }
}