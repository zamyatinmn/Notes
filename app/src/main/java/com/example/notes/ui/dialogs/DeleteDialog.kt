package com.example.notes.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.notes.App
import com.example.notes.R

class DeleteDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog, null)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.are_you_sure)
            .setView(view)
            .setMessage(R.string.irrevocably_warning)
            .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface?, i: Int -> dismiss() }
            .setPositiveButton(R.string.delete) { dialogInterface: DialogInterface?, i: Int ->
                val app = requireActivity().application as App
                app.notesSource.remove(requireArguments().getInt(INDEX_KEY))
            }
        return builder.create()
    }

    companion object {
        private const val INDEX_KEY = "index"
    }
}