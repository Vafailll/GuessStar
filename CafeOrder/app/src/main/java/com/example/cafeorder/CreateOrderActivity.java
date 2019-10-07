package com.example.cafeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CreateOrderActivity extends AppCompatActivity {
    private TextView textViewHello;
    private TextView textViewAdditions;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;
    private Spinner spinnerTea;
    private Spinner spinnerCoffe;

    private String drink;
    private String name;
    private String password;

    private StringBuilder builderAdditions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        drink="";
        textViewHello=(TextView)findViewById(R.id.textViewHello);
        textViewAdditions=(TextView)findViewById(R.id.textViewEditions);
        String additions=String.format("Что добавить в ваш %s?",drink);
        textViewAdditions.setText(additions);
        checkBoxLemon=(CheckBox)findViewById(R.id.checkboxLemon);
        checkBoxMilk=(CheckBox)findViewById(R.id.checkboxMilk);
        checkBoxSugar=(CheckBox)findViewById(R.id.checkboxSugar);
        spinnerCoffe=(Spinner)findViewById(R.id.spinnerCoffe);
        spinnerTea=(Spinner)findViewById(R.id.spinnerTea);
        Intent intent=getIntent();
        if(intent.hasExtra("name")&&intent.hasExtra("password")) {
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        }else{
            name=getString(R.string.default_name);
            password=getString(R.string.default_password);
        }
        String hello=String.format("Привет, %s! Что будете заказывать",name);
        textViewHello.setText(hello);
        builderAdditions=new StringBuilder();
    }

    public void onClickChangeDrink(View view) {
        RadioButton button=(RadioButton)view;
        int id=button.getId();
        if(id==R.id.radioButtonTea){
            drink="Чай";
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffe.setVisibility(View.INVISIBLE);
            checkBoxLemon.setVisibility(View.VISIBLE);
        }else if(id==R.id.radioButtonCoffee){
            drink="Кофе";
            spinnerTea.setVisibility(View.INVISIBLE);
            spinnerCoffe.setVisibility(View.VISIBLE);
            checkBoxLemon.setVisibility(View.INVISIBLE);
        }
        String additions=String.format("Что добавить в ваш %s?",drink);
        textViewAdditions.setText(additions);

    }

    public void onClickSendOrder(View view) {
    builderAdditions.setLength(0);
    if(checkBoxMilk.isChecked()){
            builderAdditions.append("Молоко").append(" ");
    }
    if(checkBoxSugar.isChecked()){
            builderAdditions.append("Сахар").append(" ");
    }
    if(checkBoxLemon.isChecked()&&drink.equals("Чай")){
            builderAdditions.append("Лимон").append(" ");
    }
    String optionOfDrink="";
    if(drink.equals("Чай")){
        optionOfDrink=spinnerTea.getSelectedItem().toString();
    }else{
        optionOfDrink=spinnerCoffe.getSelectedItem().toString();
    }
    String order=String.format(getString(R.string.Order),name,password,drink,optionOfDrink);
    String additions;
    if(builderAdditions.length()>0){
        additions="\n"+getString(R.string.needed_additions)+builderAdditions.toString();
    }else{
        additions="";
    }
    String fullOrder=order+additions;
    Intent intent=new Intent(this,OrderDetailActivity.class);
    intent.putExtra("order",fullOrder);
    startActivity(intent);
    }
}
