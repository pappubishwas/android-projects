package com.example.androidtelephony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendEmailActivity extends AppCompatActivity {

    EditText editTextSubject,editTextContent,editTextEmailAddress;
    Button emailButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        emailButton=findViewById(R.id.emailbtn);
        editTextEmailAddress=findViewById(R.id.emailAddress);
        editTextSubject=findViewById((R.id.editSubject));
        editTextContent=findViewById(R.id.editContent);

        emailButton.setOnClickListener(v -> {
            String subject,content,emailadd;
            subject=editTextSubject.getText().toString();
            content=editTextContent.getText().toString();
            emailadd=editTextEmailAddress.getText().toString();

            if(subject.equals("") || content.equals("") || emailadd.equals("")){
                Toast.makeText(SendEmailActivity.this,"All fields are required.",Toast.LENGTH_SHORT).show();
            }else{
                toSendMail(subject,content,emailadd);
            }
        });
    }
    public void toSendMail(String subject,String content,String toEmail){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{toEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,content);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Chose Email Client"));
    }
}