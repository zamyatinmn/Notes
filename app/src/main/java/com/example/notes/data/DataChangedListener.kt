package com.example.notes.data;

public interface DataChangedListener {
    void onDataAdd(int position);
    void onDataUpdate(int position);
    void onDataDelete(int position);
}
