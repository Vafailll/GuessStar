package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotes;
    private NotesDBHelper dbHelper;
    private SQLiteDatabase database;
    private static final ArrayList<Note> notes = new ArrayList<>();
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        recyclerViewNotes = (RecyclerView) findViewById(R.id.recyclerViewNotes);
        dbHelper=new NotesDBHelper(this);
        database=dbHelper.getWritableDatabase();



        getData();
        adapter= new NotesAdapter(notes);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(adapter);

        adapter.setOnNoteClickListener(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {

            }

            @Override
            public void onLongClick(int position) {
                remove(position);

            }
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            remove(viewHolder.getAdapterPosition());
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
    }

    private void remove(int position) {
        int id = notes.get(position).getId();
        //int id=1;
        String where = NotesContract.NotesEntry._ID + " = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};

        database.delete(NotesContract.NotesEntry.TABLE_NAME,where,whereArgs);
        getData();
        adapter.notifyDataSetChanged();
    }
        public void onClickAddNote (View view){
            Intent intent = new Intent(this, AddNoteActivity.class);
            startActivity(intent);
        }
        private void getData(){
            notes.clear();
            String selection= NotesContract.NotesEntry.COLUMN_PRIORITY+" < ?";
            String[]selectionArgs=new String[]{"2"};

            Cursor cursor=database.query(NotesContract.NotesEntry.TABLE_NAME,null,selection,selectionArgs,null,null, NotesContract.NotesEntry.COLUMN_DAYOFWEEK);
            while(cursor.moveToNext()){
                int id=cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry._ID));
                String title=cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE));
                String description=cursor.getString(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION));
                int dayOfWeek=cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_DAYOFWEEK));
                int priority=cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY));
                Note note=new Note(id,title,description,dayOfWeek,priority);
                notes.add(note);
            }
            cursor.close();
        }
    }
