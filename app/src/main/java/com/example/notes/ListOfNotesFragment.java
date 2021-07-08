package com.example.notes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListOfNotesFragment extends Fragment {

    private static final int MENU_DELETE = 123;


    private boolean isLandscape;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (isLandscape) {
            showLand(0);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_of_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createTextViewList((LinearLayout) view);
    }

    private void createTextViewList(LinearLayout linearLayout) {
        for (int i = 0; i < Note.saved.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setText(Note.saved.get(i).getTitle());
            final int finalI = i;
            textView.setOnClickListener(v -> {
                if (isLandscape) {
                    showLand(finalI);
                } else {
                    showPortNote(finalI);
                }
            });
            textView.setTextSize(30);
            registerForContextMenu(textView);
            linearLayout.addView(textView);
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v
            ,ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, MENU_DELETE, 0, "delete");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == MENU_DELETE){
            Toast.makeText(requireContext(), "note deleted", Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    private void showLand(int index) {
        CurrentNoteFragment currentNoteFragment = CurrentNoteFragment.newInstance(index);
        requireActivity().getSupportFragmentManager().beginTransaction().add(
                R.id.containerNote, currentNoteFragment).commit();
    }

    private void showPortNote(int finalI) {
//        Intent intent = new Intent(getActivity(), NotePortActivity.class);
//        intent.putExtra(CurrentNoteFragment.KEY_INDEX, finalI);
//        startActivity(intent);
        Note.current = finalI;
        getParentFragmentManager().beginTransaction().replace(R.id.list_of_notes
                , new CurrentNoteFragment()).commit();
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//
//        inflater.inflate(R.menu.menu_main, menu);
//        MenuItem search = menu.findItem(R.id.menu_search);
//        SearchView searchText = (SearchView) search.getActionView();
//
//        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(requireContext(), query, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(requireContext(), newText, Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//        super.onCreateOptionsMenu(menu, inflater);
//    }
}