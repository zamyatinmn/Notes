package com.example.notes.data

interface DataChangedListener {
    fun onDataAdd(position: Int)
    fun onDataUpdate(position: Int)
    fun onDataDelete(position: Int)
}