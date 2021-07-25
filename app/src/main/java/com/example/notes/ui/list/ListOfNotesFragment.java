package com.example.notes.ui.list;

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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.notes.App;
import com.example.notes.R;
import com.example.notes.data.DataChangedListener;
import com.example.notes.data.INotesSource;
import com.example.notes.data.Note;
import com.example.notes.data.firebase.NoteSourceFirebase;
import com.example.notes.ui.dialogs.DeleteDialog;
import com.example.notes.ui.dialogs.EditNoteDialogFragment;

public class ListOfNotesFragment extends Fragment {

    private static final int MENU_CHANGE_COLOR = 321;
    private static final int MENU_DELETE = 123;
    public static final String INDEX_KEY = "index";
    private static final String TAG_EDIT_NOTE = "TAG_EDIT_NOTE";
    private static final String TAG_DELETE = "TAG_DELETE";
    private ListOfNotesAdapter adapter;
    private INotesSource noteSource;


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
        SwipeRefreshLayout refresh = view.findViewById(R.id.refresh);

        noteSource = new NoteSourceFirebase()
                .init(cardsData -> {
                    adapter.notifyDataSetChanged();
                });

        refresh.setOnRefreshListener(() -> noteSource.init(notesSource -> {
            adapter.notifyDataSetChanged();
            refresh.setRefreshing(false);
        }));

        ((App) requireActivity().getApplication()).notesSource = noteSource;
        adapter.setDataSource(noteSource);

        noteSource.setOnChangedListener(new DataChangedListener() {
            @Override
            public void onDataAdd(int position) {
                adapter.notifyItemInserted(position);
            }

            @Override
            public void onDataUpdate(int position) {
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onDataDelete(int position) {
                adapter.notifyItemRemoved(position);
            }
        });
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        setHasOptionsMenu(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListOfNotesAdapter();
        adapter.setRegisterViewListener(this::registerForContextMenu);
        adapter.setOnItemClickListener((view, position) ->
        {
            DialogFragment fragment = new EditNoteDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(INDEX_KEY, position);
            fragment.setArguments(bundle);
            fragment.show(getParentFragmentManager(), TAG_EDIT_NOTE);
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v
            , ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, MENU_CHANGE_COLOR, 0, R.string.change_color);
        menu.add(0, MENU_DELETE, 1, R.string.delete);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case MENU_CHANGE_COLOR:
                Note note = noteSource.getNote(adapter.getPosition());
                note.newColor();
                noteSource.update(adapter.getPosition(), note);
                break;
            case MENU_DELETE:
                DialogFragment dialog = new DeleteDialog();
                Bundle bundle = new Bundle();
                bundle.putInt(INDEX_KEY, adapter.getPosition());
                dialog.setArguments(bundle);
                dialog.show(getParentFragmentManager(), TAG_DELETE);
                break;
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
                DialogFragment fragment = new EditNoteDialogFragment();
                fragment.show(getParentFragmentManager(), TAG_EDIT_NOTE);
                break;
            case R.id.menu_search:
                Toast.makeText(requireContext(), R.string.search, Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.menu_about:
                Toast.makeText(requireContext(), R.string.about, Toast.LENGTH_SHORT)
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}