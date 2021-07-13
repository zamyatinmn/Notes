package com.example.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.App;
import com.example.notes.data.INotesSource;
import com.example.notes.data.Note;
import com.example.notes.R;
import com.example.notes.data.NoteSourceFirebase;
import com.example.notes.data.NoteSourceResponse;

public class ListOfNotesFragment extends Fragment {

    private static final int MENU_DELETE = 123;
    private ListOfNotesAdapter adapter;
    private INotesSource noteSource;
    private GridLayoutManager layoutManager;
    private RecyclerView recyclerView;

    public static ListOfNotesFragment newInstance() {
        return new ListOfNotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_notes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        initRecyclerView(recyclerView);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(animator);

        noteSource = new NoteSourceFirebase()
                .init(cardsData -> adapter.notifyDataSetChanged());

        ((App) requireActivity().getApplication()).notesSource = noteSource;
        adapter.setDataSource(noteSource);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        this.recyclerView = recyclerView;
        setHasOptionsMenu(true);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
//        App app = (App) requireActivity().getApplication();
//        noteSource = app.notesSource;
        adapter = new ListOfNotesAdapter();
        boolean isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        adapter.setRegisterViewListener(this::registerForContextMenu);
        adapter.setOnItemClickListener((view, position) ->
        {
            if (isLandscape) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container2, CurrentNoteFragment.newInstance(position))
                        .commit();
            } else {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, CurrentNoteFragment.newInstance(position))
                        .commit();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v
            , ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, MENU_DELETE, 0, "delete");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == MENU_DELETE) {
            noteSource.remove(adapter.getPosition());
//            Если использовать этот метод, позиции оставшихся вьюх не пересчитываются
//            попадаю на OutOfBound
//            adapter.notifyItemRemoved(adapter.getPosition());
            adapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                noteSource.add(new Note("Новая заметка", "Текст новой заметки"));
                adapter.notifyItemInserted(noteSource.size());
                recyclerView.smoothScrollToPosition(noteSource.size() - 1);
                break;
            case R.id.menu_search:
                Toast.makeText(requireContext(), "search", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.menu_about:
                Toast.makeText(requireContext(), "about", Toast.LENGTH_SHORT)
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}