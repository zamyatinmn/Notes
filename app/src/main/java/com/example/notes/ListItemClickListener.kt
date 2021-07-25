package com.example.notes

import android.view.View

interface ListItemClickListener {
    fun onClick(view: View, position: Int)
}