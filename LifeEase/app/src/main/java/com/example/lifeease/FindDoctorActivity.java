package com.example.lifeease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FindDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);


        Button btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FindDoctorActivity.this,"Wait",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(FindDoctorActivity.this,ProductsDisplay.class);
                startActivity(intent);

            }
        });
        CardView medicine = findViewById(R.id.medicineDoctor);
        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
                intent.putExtra("title","Medicine");
                startActivity(intent);

            }
        });
        CardView dentist = findViewById(R.id.cardDentist);
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
                intent.putExtra("title","Dentist");
                startActivity(intent);

            }
        });

        CardView surgeon = findViewById(R.id.cardSurgeon);
        surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
                intent.putExtra("title","Surgeon");
                startActivity(intent);

            }
        });
        CardView orthopedics = findViewById(R.id.cardOrthopedics);
        orthopedics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FindDoctorActivity.this,DoctorDetailsActivity.class);
                intent.putExtra("title","Orthopedics");
                startActivity(intent);

            }
        });



    }
}