package com.example.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class ListOfNotesAdapter extends RecyclerView.Adapter<ListOfNotesAdapter.ViewHolder> {

    private List<Note> dataSource = Note.saved;

    private ListItemClickListener listener;
    private RegisterViewListener viewListener;

    public void setOnItemClickListener(ListItemClickListener listener) {
        this.listener = listener;
    }

    public void setRegisterViewListener(RegisterViewListener listener) {
        this.viewListener = listener;
    }

    @NonNull
    @Override
    public ListOfNotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfNotesAdapter.ViewHolder holder, int position) {
        Note currentNote = dataSource.get(position);
        int[] colors = holder.itemView.getResources().getIntArray(R.array.colors);
        holder.itemView.setBackgroundColor(colors[currentNote.color]);
        holder.getTitle().setText(currentNote.getTitle());
        holder.getDate().setText(currentNote.getCreationDate());
        holder.getText().setText(currentNote.getText());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView date;
        private final TextView text;

        public TextView getTitle() {
            return title;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getText() {
            return text;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewListener.registerView(itemView);
            itemView.setOnClickListener(view -> {
                listener.onClick(view, getAdapterPosition());
                System.out.println("" + getAdapterPosition());
            });
            title = itemView.findViewById(R.id.item_title);
            date = itemView.findViewById(R.id.item_date);
            text = itemView.findViewById(R.id.item_text);
        }
    }
}
