package com.example.notes.data

import com.example.notes.data.firebase.NoteSourceResponse
import java.util.*

class NoteSourceSupernova : INotesSource {
    private val dataSource: MutableList<Note>
    private var listener: DataChangedListener? = null
    override fun init(response: NoteSourceResponse): INotesSource {
        dataSource.add(Note("Заметка 1", "Заметка разразразраз"))
        dataSource.add(Note("Заметка 2", "Заметка двадвадвадва"))
        dataSource.add(Note("Заметка 3", "Заметка тритритритри"))
        dataSource.add(Note("Заметка 4", "Заметка четыречетыре"))
        dataSource.add(Note("Заметка 5", "Заметка пятьпятьпять"))
        dataSource.add(Note("Заметка 6", "Заметка шестьшесть"))
        dataSource.add(Note("Заметка 7", "Заметка семьсемьсемь"))
        dataSource.add(Note("Заметка 8", "Заметка восемьвосемь"))
        dataSource.add(Note("Заметка 9", "Заметка девятьдевять"))
        response.initialized(this)
        return this
    }

    override fun size(): Int {
        return dataSource.size
    }

    override fun add(note: Note) {
        listener?.onDataAdd(dataSource.size)
        dataSource.add(note)
    }

    override fun remove(position: Int) {
        listener?.onDataDelete(position)
        dataSource.removeAt(position)
    }

    override fun getNote(position: Int): Note {
        return dataSource[position]
    }

    override fun update(position: Int, note: Note) {
        listener?.onDataUpdate(position)
        dataSource[position] = note
    }

    override fun setOnChangedListener(listener: DataChangedListener) {
        this.listener = listener
    }

    init {
        dataSource = ArrayList()
    }
}