package com.example.notes.ui.dialogs

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.notes.App
import com.example.notes.R
import com.example.notes.data.INotesSource
import com.example.notes.data.Note
import com.example.notes.ui.list.ListOfNotesFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EditNoteDialogFragment : DialogFragment() {
    private lateinit var title: EditText
    private lateinit var date: TextView
    private lateinit var text: EditText
    private val dateAndTime = Calendar.getInstance()
    private var index = 0
    private lateinit var note: Note
    private lateinit var data: INotesSource
    private var isNew = false
    override fun onAttach(context: Context) {
        val app = requireActivity().application as App
        data = app.notesSource
        if (arguments != null) {
            index = requireArguments().getInt(ListOfNotesFragment.INDEX_KEY)
            note = data.getNote(index)
        } else {
            index = data.size()
            note = Note()
            isNew = true
        }
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.current_note, container)
        val colors = resources.getIntArray(R.array.colors)
        val app = requireActivity().application as App
        data = app.notesSource
        view.setBackgroundColor(colors[note.color])
        title = view.findViewById(R.id.title_note)
        title.setText(note.title)
        date = view.findViewById(R.id.date_note)
        date.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            .format(note.creationDate)
        date.setOnClickListener { setDate() }
        text = view.findViewById(R.id.text_note)
        text.setText(note.text)
        return view
    }

    private fun setDate() {
        DatePickerDialog(
            requireContext(), d,
            dateAndTime[Calendar.YEAR],
            dateAndTime[Calendar.MONTH],
            dateAndTime[Calendar.DAY_OF_MONTH]
        ).show()
    }

    var d = OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
        dateAndTime[Calendar.YEAR] = year
        dateAndTime[Calendar.MONTH] = monthOfYear
        dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
        setInitialDateTime()
    }

    private fun setInitialDateTime() {
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val newDate = format.format(dateAndTime.timeInMillis)
        date.text = newDate
        note.creationDate = dateAndTime.time
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window!!.setLayout(width, height)
        }
    }

    override fun onDestroy() {
        note.title = title.text.toString()
        try {
            note.creationDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                .parse(date.text.toString())?:Date(0)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        note.text = text.text.toString()
        if (isNew) {
            if (note.title != "" || note.text != ""){
                data.add(note)
            }
        } else {
            data.update(index, note)
        }
        super.onDestroy()
    }
}