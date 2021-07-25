package com.example.notes.data

import java.util.*

class Note {
    var id: String? = null
    var title: String
    var creationDate: Date
    var text: String
    var color = 0

    constructor(title: String, text: String) {
        this.title = title
        this.text = text
        creationDate = Date()
        newColor()
    }

    constructor() {
        title = ""
        text = ""
        creationDate = Date()
        newColor()
    }

    fun newColor() {
        color = Random().nextInt(10)
    }
}