package com.example.toolsshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DrillCategoryActivity extends AppCompatActivity {
    private ListView listViewDrills;
    private ArrayList<Drill>drills;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drill_category);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        listViewDrills=(ListView)findViewById(R.id.listViewDrills);
        drills=new ArrayList<>();
        drills.add(new Drill(getString(R.string.drill_interskol_tittle),getString(R.string.drill_interskol_info),R.drawable.interskol));
        drills.add(new Drill(getString(R.string.drill_makita_tittle),getString(R.string.drill_makita_info),R.drawable.makita));
        drills.add(new Drill(getString(R.string.drill_dewolt_tittle),getString(R.string.drill_dewalt_info),R.drawable.dewalt));
        ArrayAdapter<Drill>adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,drills);
        listViewDrills.setAdapter(adapter);
        listViewDrills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Drill drill=drills.get(position);
                Intent intent=new Intent(getApplicationContext(),DrillDetailActivity.class);
                intent.putExtra("tittle",drill.getTittle());
                intent.putExtra("info",drill.getInfo());
                intent.putExtra("resId",drill.getImageResourceId());
                startActivity(intent);
            }
        });
    }
}
