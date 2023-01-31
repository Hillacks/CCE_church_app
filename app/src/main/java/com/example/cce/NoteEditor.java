package com.example.cce;

import static com.example.cce.Notes.arrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditor extends AppCompatActivity {
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        EditText editText = findViewById(R.id.editText);
        Intent intent = getIntent();

        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            editText.setText(Notes.notes.get(noteId));
        }else {
            Notes.notes.add("");
            noteId = Notes.notes.size() - 1;
            arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @java.lang.Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @java.lang.Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Notes.notes.set(noteId, String.valueOf(s));
                arrayAdapter.notifyDataSetChanged();
                //creating object of shared preferences to store data in the phone
                SharedPreferences sharedPreferences = getApplication().getSharedPreferences("com.example.cce", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet(Notes.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @java.lang.Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.savenote, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_action:
                // save the note
                startActivity(new Intent(NoteEditor.this, Notes.class));
                break;
            case R.id.delete_action:
                //delete the note
                new AlertDialog.Builder(NoteEditor.this)
                        .setIcon(R.drawable.ic_baseline_crop_din_24)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", (dialog, position1) -> {
                            SharedPreferences sharedPreferences1 = getApplication().getSharedPreferences("com.example.cce", Context.MODE_PRIVATE);
                            HashSet<String> set1 = new HashSet(Notes.notes);
                            sharedPreferences1.edit().remove("text").commit();
                        }).setNegativeButton("No", null).show();
                break;
        }
        return true;
    }
}