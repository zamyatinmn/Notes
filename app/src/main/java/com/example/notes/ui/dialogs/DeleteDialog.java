package com.example.notes.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.notes.App;
import com.example.notes.R;

public class DeleteDialog extends DialogFragment {
    private static final String INDEX_KEY = "index";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.are_you_sure)
                .setView(view)
                .setMessage(R.string.irrevocably_warning)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dismiss())
                .setPositiveButton(R.string.delete, (dialogInterface, i) -> {
                    App app = (App) requireActivity().getApplication();
                    app.notesSource.remove(getArguments().getInt(INDEX_KEY));
                });
        return builder.create();
    }
}
