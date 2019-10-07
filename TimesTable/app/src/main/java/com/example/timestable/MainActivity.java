package com.example.timestable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listViewNumbers;
    private SeekBar seekBar;
    private ArrayList<String>numbers;
    private int max=20;
    private int min=1;
    private int count=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewNumbers=(ListView)findViewById(R.id.listViewNumbers);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        numbers=new ArrayList<>();
        final ArrayAdapter<String>arrayAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,numbers);
        listViewNumbers.setAdapter(arrayAdapter);
        seekBar.setMax(max);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numbers.clear();
                if(progress<min) {
                    seekBar.setProgress(min);
                }
                for (int i = min; i <count ; i++) {
                numbers.add(seekBar.getProgress()+"*"+i+"="+seekBar.getProgress()*i);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setProgress(10);
    }
}
