package com.example.lifeease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if a user is already logged in
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            // User is already logged in, redirect to ProductsDisplay activity
            Intent intent = new Intent(MainActivity.this, ProductsDisplay.class);
            startActivity(intent);
            finish(); // Close the MainActivity to prevent returning to it
            return; // Exit onCreate
        }

        // If user is not logged in, proceed with login/register functionality
        EditText etUsername = findViewById(R.id.et_username);
        EditText etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            String usernameOrEmail = etUsername.getText().toString(); // Allow both username or email
            String password = etPassword.getText().toString();

            if (usernameOrEmail.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            } else {
                if (usernameOrEmail.equals("admin") && password.equals("admin")) {
                    // Admin login logic
                    Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                } else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(usernameOrEmail).matches()) {
                        // If the input is an email
                        db.collection("users")
                                .whereEqualTo("email", usernameOrEmail)
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                        String email = task.getResult().getDocuments().get(0).getString("email");

                                        FirebaseAuth auth1 = FirebaseAuth.getInstance();
                                        auth1.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(authTask -> {
                                                    if (authTask.isSuccessful()) {
                                                        Toast.makeText(MainActivity.this, "Login successful! Welcome, " + usernameOrEmail, Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(MainActivity.this, ProductsDisplay.class);
                                                        startActivity(intent);
                                                        finish(); // Close MainActivity
                                                    } else {
                                                        String errorMessage = authTask.getException() != null ? authTask.getException().getMessage() : "Unknown error";
                                                        Toast.makeText(MainActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(MainActivity.this, "Email not found!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // If the input is not an email, assume it's a username
                        db.collection("users")
                                .whereEqualTo("username", usernameOrEmail)
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                        String email = task.getResult().getDocuments().get(0).getString("email");

                                        FirebaseAuth auth1 = FirebaseAuth.getInstance();
                                        auth1.signInWithEmailAndPassword(email, password)
                                                .addOnCompleteListener(authTask -> {
                                                    if (authTask.isSuccessful()) {
                                                        Toast.makeText(MainActivity.this, "Login successful! Welcome, " + usernameOrEmail, Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(MainActivity.this, ProductsDisplay.class);
                                                        startActivity(intent);
                                                        finish(); // Close MainActivity
                                                    } else {
                                                        String errorMessage = authTask.getException() != null ? authTask.getException().getMessage() : "Unknown error";
                                                        Toast.makeText(MainActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(MainActivity.this, "Username not found!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }
                }
            }
        });
    }

}