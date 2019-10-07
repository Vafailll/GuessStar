package com.example.toolsshop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrillDetailActivity extends AppCompatActivity {
    private TextView textViewTittle;
    private TextView textViewInfo;
    private ImageView imageViewDrill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drill_detail);
        textViewInfo=(TextView)findViewById(R.id.textViewInfo);
        textViewTittle=(TextView)findViewById(R.id.textViewTittle);
        imageViewDrill=(ImageView)findViewById(R.id.imageViewDrill);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        Intent intent=getIntent();
        if(intent.hasExtra("tittle")&&intent.hasExtra("info")&&intent.hasExtra("resId")){
            String tittle=intent.getStringExtra("tittle");
            String info=intent.getStringExtra("info");
            int resId=intent.getIntExtra("resId",0);
            textViewTittle.setText(tittle);
            textViewInfo.setText(info);
            imageViewDrill.setImageResource(resId);
        }else{
            Intent backToCategoru=new Intent(this,DrillCategoryActivity.class);
            startActivity(backToCategoru);
        }
    }
}
