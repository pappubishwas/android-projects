package com.example.androidtelephony;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

public class SMSActivity extends AppCompatActivity {

    private EditText editTextNumber;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsactivity);


        ActivityCompat.requestPermissions(SMSActivity.this,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS},
                PackageManager.PERMISSION_GRANTED);

        editTextMessage = findViewById(R.id.editText2);
        editTextNumber = findViewById(R.id.editText1);


    }

    public void sendSMS(View view) {
//        String no=mob.getText().toString();
//        String msg=sms.getText().toString();
//
//        //Getting intent and PendingIntent instance
//        Intent intent=new Intent(getApplicationContext(),ThirdActivity.class);
//        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
//
//        //Get the SmsManager instance and call the sendTextMessage method to send message
//        SmsManager sms1= SmsManager.getDefault();
//        sms1.sendTextMessage(no, null, msg, pi,null);


        String message = editTextMessage.getText().toString();
        String number = editTextNumber.getText().toString();

        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(number,null, message, null, null);
        Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                Toast.LENGTH_LONG).show();
    }
}