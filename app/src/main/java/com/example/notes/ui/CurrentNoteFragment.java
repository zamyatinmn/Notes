package com.example.notes.ui;

import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.App;
import com.example.notes.R;
import com.example.notes.data.INotesSource;
import com.example.notes.data.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class CurrentNoteFragment extends Fragment {

    private EditText title;
    private TextView date;
    private EditText text;
    private final Calendar dateAndTime = Calendar.getInstance();
    public static final String KEY_INDEX = "index";
    private int index;
    private Note note;
    private INotesSource data;


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
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_note, container, false);

        int[] colors = getResources().getIntArray(R.array.colors);
        App app = (App) requireActivity().getApplication();
        data = app.notesSource;
        note = data.getNote(index);
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

    @Override
    public void onPause() {
        super.onPause();
        note.setTitle(title.getText().toString());
        try {
            note.setCreationDate(new SimpleDateFormat("dd.MM.yyyy",
                    Locale.getDefault()).parse(date.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        note.setText(text.getText().toString());
        data.update(index, note);
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.note_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_change_color) {
            note.setColor(new Random().nextInt(10));
            boolean isLandscape = getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE;
            if (isLandscape) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container2
                        , newInstance(index)).commit();
            } else {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container
                        , newInstance(index)).commit();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}