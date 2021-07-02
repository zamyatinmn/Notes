package com.example.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListOfNotesFragment extends Fragment {


    private boolean isLandscape;

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        isLandscape = getResources().getConfiguration().orientation
//                == Configuration.ORIENTATION_LANDSCAPE;
//
//        if (isLandscape) {
//            showLand(0);
//        }
//
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (isLandscape) {
            showLand(0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_of_notes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createTextViewList((LinearLayout) view);
    }

    private void createTextViewList(LinearLayout linearLayout) {
        String[] cities = getResources().getStringArray(R.array.cities);
        for (int i = 0; i < cities.length; i++) {
            TextView textView = new TextView(getContext());
            textView.setText(cities[i]);
            final int finalI = i;
            textView.setOnClickListener(v -> {
                if (isLandscape) {
                    showLand(finalI);
                } else {
                    showPortNote(finalI);
                }

            });
            textView.setTextSize(30);
            linearLayout.addView(textView);
        }
    }

    private void showLand(int index) {
        CurrentNoteFragment currentNoteFragment = CurrentNoteFragment.newInstance(index);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.containerNote, currentNoteFragment).commit();
    }

    private void showPortNote(int finalI) {
        Intent intent = new Intent(getActivity(), NotePortActivity.class);
        intent.putExtra(CurrentNoteFragment.KEY_INDEX, finalI);
        startActivity(intent);
    }
}