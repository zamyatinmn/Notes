package com.example.notes.ui;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView title;
    private final TextView date;
    private final TextView text;
    private final LinearLayout linear;


    public TextView getTitle() {
        return title;
    }

    public TextView getDate() {
        return date;
    }

    public TextView getText() {
        return text;
    }

    public LinearLayout getLinear() {
        return linear;
    }

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.item_title);
        date = itemView.findViewById(R.id.item_date);
        text = itemView.findViewById(R.id.item_text);
        linear = itemView.findViewById(R.id.linear);
    }
}