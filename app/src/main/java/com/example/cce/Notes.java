package com.example.cce;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class Notes extends AppCompatActivity {
    static ArrayList<String> notes = new ArrayList<String>();
    static ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ListView listView = findViewById(R.id.listview);
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("com.example.cce", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
        if (set == null) {
            notes.add("");
        } else {
            notes = new ArrayList(set);
        }

        arrayAdapter = new ArrayAdapter(getApplication(), android.R.layout.simple_expandable_list_item_1, notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(Notes.this, NoteEditor.class);
            intent.putExtra("noteId", position);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {

            final int itemToDelete = position;

            new AlertDialog.Builder(Notes.this)
                    .setIcon(R.drawable.ic_baseline_crop_din_24)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this note?")
                    .setPositiveButton("Yes", (dialog, position1) -> {
                        notes.remove(itemToDelete);
                        arrayAdapter.notifyDataSetChanged();
                        SharedPreferences sharedPreferences1 = getApplication().getSharedPreferences("com.example.cce", Context.MODE_PRIVATE);
                        HashSet<String> set1 = new HashSet(Notes.notes);
                        sharedPreferences1.edit().putStringSet("notes", set1).apply();
                    }).setNegativeButton("No", null).show();
            return true;
        });
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notes.this, NoteEditor.class);
                startActivity(intent);
            }
        });

    }
}