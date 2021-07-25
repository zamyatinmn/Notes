package com.example.notes.ui.list

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView
    val date: TextView
    val text: TextView
    val linear: LinearLayout

    init {
        title = itemView.findViewById(R.id.item_title)
        date = itemView.findViewById(R.id.item_date)
        text = itemView.findViewById(R.id.item_text)
        linear = itemView.findViewById(R.id.linear)
    }
}