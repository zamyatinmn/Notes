package com.example.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.ListOfNotesAdapter;
import com.example.notes.R;
import com.example.notes.RegisterViewListener;

public class ListOfNotesFragment extends Fragment {

    private static final int MENU_DELETE = 123;

    public static ListOfNotesFragment newInstance() {
        return new ListOfNotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_of_notes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        initRecyclerView(recyclerView);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        ListOfNotesAdapter adapter = new ListOfNotesAdapter();
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
            Toast.makeText(requireContext(), "note deleted", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }
}