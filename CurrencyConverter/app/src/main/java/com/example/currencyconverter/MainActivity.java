package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editView);
        textView=findViewById(R.id.displayView);
    }

    public void convertNow(View view) {
        String taka=editText.getText().toString();
        Double takaDouble=Double.parseDouble(taka);
        Double dollarDouble=takaDouble*0.012;
        String dollar=String.valueOf(dollarDouble);
        textView.setText(dollar);
    }
}