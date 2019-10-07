package com.example.startwatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Homework2 extends AppCompatActivity {
    private TextView textViewCreate;
    private TextView textViewStart;
    private TextView textViewResume;
    private TextView textViewRestart;
    private int counterOnCreate;
    private int counterOnStart;
    private int counterOnResume;
    private int counterOnRestart;
    private Button buttonClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework2);
        buttonClose=(Button)findViewById(R.id.button);
        textViewCreate=(TextView)findViewById(R.id.textViewCreate);
        textViewStart=(TextView)findViewById(R.id.textViewStart);
        textViewResume=(TextView)findViewById(R.id.textViewResume);
        textViewRestart=(TextView)findViewById(R.id.textViewRestart);
        if(savedInstanceState!=null) {
            counterOnCreate=savedInstanceState.getInt("counterCreate");
            counterOnResume=savedInstanceState.getInt("counterResume");
            counterOnStart=savedInstanceState.getInt("counterStart");
            counterOnRestart=savedInstanceState.getInt("counterRestart");
        }else {
            Intent intent = getIntent();
            counterOnCreate = intent.getIntExtra("counterCreate", 0);
            counterOnResume = intent.getIntExtra("counterResume", 0);
            counterOnStart = intent.getIntExtra("counterStart", 0);
            counterOnRestart = intent.getIntExtra("counterRestart", 0);
        }
        counterOnCreate++;
        String s="onCreate calls(: "+counterOnCreate;
        String a="onResume calls(): "+counterOnResume;
        String b="onStart cells(): "+counterOnStart;
        String c="onRestart calls(): "+counterOnRestart;
        textViewRestart.setText(c);
        textViewResume.setText(a);
        textViewStart.setText(b);
        textViewCreate.setText(s);

    }
    @Override
    protected void onResume() {
        super.onResume();
        counterOnResume++;
        String s="onResume calls(): "+counterOnResume;
        textViewResume.setText(s);
    }

    protected void onStart(){
        super.onStart();
        counterOnStart++;
        String s="OnStart calls(: "+counterOnStart;
        textViewStart.setText(s);
    }
    protected void onRestart(){
        super.onRestart();
        counterOnRestart++;
        String s="onRestart call(): "+counterOnRestart;
        textViewRestart.setText(s);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counterCreate",counterOnCreate);
        outState.putInt("counterResume",counterOnResume);
        outState.putInt("counterStart",counterOnStart);
        outState.putInt("counterRestart",counterOnRestart);

    }

    public void onClick(View view) {
        Intent intent=new Intent(this,Homework1.class);
        intent.putExtra("counterCreate",counterOnCreate);
        intent.putExtra("counterResume",counterOnResume);
        intent.putExtra("counterStart",counterOnStart);
        intent.putExtra("counterRestart",counterOnRestart);

        startActivity(intent);
    }
}
