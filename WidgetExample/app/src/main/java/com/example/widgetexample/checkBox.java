package com.example.widgetexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class checkBox extends AppCompatActivity {

    CheckBox pizzaCheckBox, coffeeCheckBox,burgerCheckBox;
    TextView displayText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);

        pizzaCheckBox=findViewById(R.id.cb1);
        coffeeCheckBox=findViewById(R.id.cb2);
        burgerCheckBox=findViewById(R.id.cb3);
        displayText=findViewById(R.id.tv);
    }


    public void order(View view) {
        int totalamount=0;

        StringBuilder result=new StringBuilder();
        result.append("Selected Items: ");
        if(pizzaCheckBox.isChecked()){
            result.append("\nPizza 100Rs");
            totalamount+=100;
        }
        if(coffeeCheckBox.isChecked()){
            result.append("\n Burger 50Rs");
            totalamount+=50;
        }
        if(burgerCheckBox.isChecked()){
            result.append("\nBurger 120Rs");
            totalamount+=120;
        }
        result.append("\nTotal: "+totalamount+"Rs");

        //Toast.makeText(getApplicationContext(),result.toString(),Toast.LENGTH_SHORT).show();
        displayText.setText(result);
    }
}