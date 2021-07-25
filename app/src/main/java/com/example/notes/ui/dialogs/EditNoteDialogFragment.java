package com.example.notes.ui.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.notes.App;
import com.example.notes.R;
import com.example.notes.data.INotesSource;
import com.example.notes.data.Note;
import com.example.notes.ui.list.ListOfNotesFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditNoteDialogFragment extends DialogFragment {
    private EditText title;
    private TextView date;
    private EditText text;
    private final Calendar dateAndTime = Calendar.getInstance();
    private int index;
    private Note note;
    private INotesSource data;
    private boolean isNew = false;

    @Override
    public void onAttach(@NonNull Context context) {
        App app = (App) requireActivity().getApplication();
        data = app.notesSource;
        if (getArguments() != null) {
            index = getArguments().getInt(ListOfNotesFragment.INDEX_KEY);
            note = data.getNote(index);
        } else {
            index = data.size();
            note = new Note();
            isNew = true;
        }
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_note, container);
        int[] colors = getResources().getIntArray(R.array.colors);
        App app = (App) requireActivity().getApplication();
        data = app.notesSource;
        view.setBackgroundColor(colors[note.color]);

        title = view.findViewById(R.id.title_note);
        title.setText(note.getTitle());
        date = view.findViewById(R.id.date_note);
        date.setText(new SimpleDateFormat("dd.MM.yyyy",
                Locale.getDefault()).format(note.getCreationDate()));
        date.setOnClickListener(view1 ->
                setDate()
        );
        text = view.findViewById(R.id.text_note);
        text.setText(note.getText());

        return view;
    }

    private void setDate() {
        new DatePickerDialog(requireContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setInitialDateTime();
    };

    private void setInitialDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String newDate = format.format(dateAndTime.getTimeInMillis());
        date.setText(newDate);
        note.setCreationDate(dateAndTime.getTime());
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onDestroy() {
        note.setTitle(title.getText().toString());
        try {
            note.setCreationDate(new SimpleDateFormat("dd.MM.yyyy",
                    Locale.getDefault()).parse(date.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        note.setText(text.getText().toString());

        if (isNew) {
            if (!note.getTitle().equals("") || !note.getText().equals(""))
                data.add(note);
        } else {
            data.update(index, note);
        }
        super.onDestroy();
    }
}
