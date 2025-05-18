package com.example.randomnumberguess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText userNoET;
    Button guessNoBtn;

    int appNumber,userNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //app random number 1 -10
        Random rand=new Random();
        appNumber= rand.nextInt(10)+1;

        userNoET=findViewById(R.id.userET);
        guessNoBtn=findViewById(R.id.submitBtn);

        guessNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userStr=userNoET.getText().toString();
                userNumber=Integer.parseInt(userStr);

                if (userNumber<appNumber){
                    Toast.makeText(MainActivity.this,"Enter larger number.",Toast.LENGTH_SHORT).show();
                    userNoET.setText("");
                }
                else if (userNumber>appNumber) {
                    Toast.makeText(MainActivity.this,"Enter smaller number.",Toast.LENGTH_SHORT).show();
                    userNoET.setText("");
                }else
                {
                    Toast.makeText(MainActivity.this,"Congrates !Your number is correct.",Toast.LENGTH_SHORT).show();
                    userNoET.setText("");
                    Random rand=new Random();
                    appNumber= rand.nextInt(10)+1;
                }
            }
        });
    }
}