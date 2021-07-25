package com.example.notes.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.ListItemClickListener
import com.example.notes.R
import com.example.notes.RegisterViewListener
import com.example.notes.data.INotesSource
import java.text.SimpleDateFormat
import java.util.*

class ListOfNotesAdapter : RecyclerView.Adapter<ViewHolder>() {
    private lateinit var dataSource: INotesSource
    private lateinit var listener: ListItemClickListener
    private lateinit var viewListener: RegisterViewListener
    var position = 0
    fun setDataSource(dataSource: INotesSource) {
        this.dataSource = dataSource
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: ListItemClickListener) {
        this.listener = listener
    }

    fun setRegisterViewListener(listener: RegisterViewListener) {
        viewListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote = dataSource.getNote(position)
        val colors = holder.itemView.resources.getIntArray(R.array.colors)
        holder.linear.setBackgroundColor(colors[currentNote.color])
        holder.title.text = currentNote.title
        holder.date.text = SimpleDateFormat("dd.MM.yyyy"
            , Locale.getDefault())
            .format(currentNote.creationDate)
        holder.text.text = currentNote.text
        holder.itemView.setOnLongClickListener {
            this.position = holder.adapterPosition
            viewListener.registerView(holder.itemView)
            false
        }
        holder.itemView.setOnClickListener {
            listener.onClick(it, holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size()
    }
}