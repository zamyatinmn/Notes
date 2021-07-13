package com.example.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.ListItemClickListener;
import com.example.notes.R;
import com.example.notes.RegisterViewListener;
import com.example.notes.data.INotesSource;
import com.example.notes.data.Note;

public class ListOfNotesAdapter extends RecyclerView.Adapter<ViewHolder> {

    private INotesSource dataSource;

    private ListItemClickListener listener;
    private RegisterViewListener viewListener;
    private int position = 0;

    public void setDataSource(INotesSource dataSource){
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setOnItemClickListener(ListItemClickListener listener) {
        this.listener = listener;
    }

    public void setRegisterViewListener(RegisterViewListener listener) {
        this.viewListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note currentNote = dataSource.getNote(position);
        int[] colors = holder.itemView.getResources().getIntArray(R.array.colors);
        holder.getLinear().setBackgroundColor(colors[currentNote.color]);
        holder.getTitle().setText(currentNote.getTitle());
        holder.getDate().setText(currentNote.getCreationDate());
        holder.getText().setText(currentNote.getText());
        holder.itemView.setOnLongClickListener(view -> {
            setPosition(position);
            viewListener.registerView(holder.itemView);
            return false;
        });
        holder.itemView.setOnClickListener(view -> listener.onClick(view, position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
