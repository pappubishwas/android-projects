package com.example.lifeease;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorDetailsActivity extends AppCompatActivity {

    private String[][] doctor_details1=
            {
                    {"Doctor Name: Shishir Basak","Osmani Medical","5yrs","Call for Serial:01963258954","600"},
                    {"Doctor Name: Ismail Patwary ","Hospital: Osmani","Exp:6yrs ","Call for Serial:01779878071","600"},
                    {"Doctor Name: Hezbullah jeebon ","Hospital:Parkview","Exp: 7yrs","Call for Serial:01589633256","600"},
                    {"Doctor Name: Jahangir Alam","Hospital: Queens","Exp: 1yrs","Call for Serial:01589635478","600"},
                    {"Doctor Name: Nahida Zafrin","Hospital: Queens","Exp: 1yrs","Call for Serial:01589635478","600"}

            };
    private String[][] doctor_details2=
            {
                    {"Doctor Name: Mushfiqul Hasan ","Hospital:SCDCH","Exp: 4yrs ","Call for Serial:01741556622","600"},
                    {"Doctor Name: Tasneem Faruqui ","Hospital:SCDC","Exp: 4yrs","Call for Serial:01747807097","600"},
                    {"Doctor Name: Tasneem Faruqui ","Hospital:SCDC","Exp: 4yrs","Call for Serial:01747807097","600"},
                    {"Doctor Name: Tasneem Faruqui ","Hospital:SCDC","Exp: 4yrs","Call for Serial:01747807097","600"},
                    {"Doctor Name: Tasneem Faruqui ","Hospital:SCDC","Exp: 4yrs","Call for Serial:01747807097","600"}

            };
    private String[][] doctor_details3=
            {
                    {"Doctor Name:Monoranjan Sarkar ","Hospital:Jalalabad Ragib Rabeya","Exp:5yrs ","Call for Serial:01325064701","600"},
                    {"Doctor Name:Monoranjan Sarkar ","Hospital:Jalalabad Ragib Rabeya","Exp:5yrs ","Call for Serial:01325064701","600"},
                    {"Doctor Name:Monoranjan Sarkar ","Hospital:Jalalabad Ragib Rabeya","Exp:5yrs ","Call for Serial:01325064701","600"},
                    {"Doctor Name:Monoranjan Sarkar ","Hospital:Jalalabad Ragib Rabeya","Exp:5yrs ","Call for Serial:01325064701","600"},
                    {"Doctor Name:Parveen Akter","Hospital:Jalalabad Ragib Rabeya","Exp:5yrs ","Call for Serial:01325064701","600"}

            };
    private String[][] doctor_details4=
            {
                    {"Doctor Name:Ishtiaque Ul Fattah ","Hospital:Parkview","Exp:6yrs ","Call for Serial:01318210276","600"},
                    {"Doctor Name:Ishtiaque Ul Fattah ","Hospital:Parkview","Exp:6yrs ","Call for Serial:01318210276","600"},
                    {"Doctor Name:Ishtiaque Ul Fattah ","Hospital:Parkview","Exp:6yrs ","Call for Serial:01318210276","600"},
                    {"Doctor Name:Ishtiaque Ul Fattah ","Hospital:Parkview","Exp:6yrs ","Call for Serial:01318210276","600"},
                    {"Doctor Name:Ishtiaque Ul Fattah ","Hospital:Parkview","Exp:6yrs ","Call for Serial:01318210276","600"}

            };





    TextView tv;
    Button btnBack;
    String[][] doctor_details={};
    HashMap<String,String> item;
    ListView lst;
    ArrayList list;
    SimpleAdapter sa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        tv = findViewById(R.id.doctorDetailsTextView);
        btnBack =findViewById(R.id.btnBackDoctorDetails);


        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);



        if(title.compareTo("Medicine") == 0)
            doctor_details =doctor_details1;
        else
            if(title.compareTo("Dentist")==0)
                doctor_details =doctor_details2;
            else
             if(title.compareTo("Surgeon")==0)
            doctor_details =doctor_details3;
             else
                 doctor_details =doctor_details4;


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DoctorDetailsActivity.this,"Wait",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DoctorDetailsActivity.this,FindDoctorActivity.class);
                startActivity(intent);
            }
        });

        list = new ArrayList();
        for (int i= 0; i<doctor_details.length;i++)
        {
            item = new HashMap<String, String>();
            item.put("Line1", doctor_details[i][0]);
            item.put("Line2", doctor_details[i][1]);
            item.put("Line3", doctor_details[i][2]);
            item.put("Line4", doctor_details[i][3]);
            item.put("Line5", "cons Fees: " + doctor_details[i][4] + "/=");
            list.add( item );


            sa = new SimpleAdapter(this, list,
                    R.layout.multi_lines,
                    new String[]{"line1", "line2", "line3", "line4", "line5"},
                    new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}
            );
           lst = findViewById(R.id.doctorDetailsListView);
            lst.setAdapter(sa);


        }


    }
}