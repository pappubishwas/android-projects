package com.example.studentmanagementsystemusingfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    EditText editTextName,editTextEmail;
    ListView listView;

    ArrayList<Student> stdArrayList;
    ArrayAdapter<Student> adapter;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName= this.<EditText>findViewById(R.id.et1);
        editTextEmail= this.<EditText>findViewById(R.id.et2);
        listView= this.<ListView>findViewById(R.id.list);

        stdArrayList=new ArrayList<>();
        adapter=new ArrayAdapter<Student>(MainActivity.this, android.R.layout.simple_list_item_1,stdArrayList);
        listView.setAdapter(adapter);

        getDataFromFirebase();

    }

    public void saveToFirebase(View view) {

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference stdRef=database.getReference("Student");
        String stdID=stdRef.push().getKey();

        Student std=new Student(editTextName.getText().toString(),editTextEmail.getText().toString());
        stdRef.child(stdID).setValue(std);
        Toast.makeText(MainActivity.this,"Data is Stored",Toast.LENGTH_LONG).show();
        editTextName.setText("");
        editTextEmail.setText("");

//        stdRef.child(stdID).setValue(std)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(MainActivity.this, "Data is Stored", Toast.LENGTH_LONG).show();
//                            editTextName.setText("");
//                            editTextEmail.setText("");
//                            Log.d("Firebase", "Data stored successfully");
//                        } else {
//                            Toast.makeText(MainActivity.this, "Failed to store data", Toast.LENGTH_LONG).show();
//                            Log.e("Firebase", "Error storing data: " + task.getException().getMessage());
//                        }
//                    }
//                });


    }

    public void getDataFromFirebase(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference stdData=database.getReference("Student");
        stdData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                //stdArrayList.clear();
                for (DataSnapshot stdSnap:snapshot.getChildren()){
                    String name=stdSnap.child("name").getValue().toString();
                    String email=stdSnap.child("email").getValue().toString();

                    Student student=new Student(name,email);
                    stdArrayList.add(student);
                    adapter.notifyDataSetChanged();
                }
                Toast.makeText(MainActivity.this,"Data is Updated",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this,"Failed to read the Data from Database",Toast.LENGTH_LONG).show();
            }
        });
    }
}
