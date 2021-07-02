package com.example.notes;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class CurrentNoteFragment extends Fragment {

    private final String KEY_TITLE = "key_title";
    private final String KEY_DATE = "key_date";
    private final String KEY_TEXT = "key_text";

    private EditText title;
    private TextView date;
    private EditText text;
    private Calendar dateAndTime = Calendar.getInstance();
    public static final String KEY_INDEX = "index";
    private int index;


    public static CurrentNoteFragment newInstance(int index) {
        CurrentNoteFragment fragment = new CurrentNoteFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            index = args.getInt(KEY_INDEX);
//            title.setText(args.getString(KEY_TITLE));
//            date.setText(args.getString(KEY_DATE));
//            text.setText(args.getString(KEY_TEXT));
        }
    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note, container, false);
        view.setBackgroundColor(R.color.teal_700);

        Note note = new Note("Hello world", "azazazazaazzazzzaazzazazaazazza");
        title = view.findViewById(R.id.title_note);
        title.setText(note.getTitle());
        date = view.findViewById(R.id.date_note);
        date.setText(note.getCreationDate());

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
        date.setText(DateUtils.formatDateTime(requireContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TITLE, title.getText().toString());
        outState.putString(KEY_DATE, date.getText().toString());
        outState.putString(KEY_TEXT, text.getText().toString());
    }
}