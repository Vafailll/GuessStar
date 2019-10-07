package com.example.startwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int seconds=0;
    private boolean isRunning=false;
    private TextView editText;
    private boolean wasRunning=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(TextView)findViewById(R.id.textViewTime);
        if(savedInstanceState!=null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning=savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds",seconds);
        outState.putBoolean("isRunning",isRunning);
        outState.putBoolean("wasRunning",wasRunning);

    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning=isRunning;
        isRunning=false;

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        isRunning=wasRunning;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning=isRunning;
        isRunning=false;
    }


    public void onClickStartTimer(View view) {
        isRunning=true;
    }

    public void onClickPauseTimer(View view) {
        isRunning=false;
    }

    public void onClickResetTimer(View view) {
        isRunning=false;
        seconds=0;
    }
    private  void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                editText.setText(time);

                if (isRunning) {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }
}
