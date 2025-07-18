package com.example.lifeease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = new DatabaseHelper(this);

        EditText etUsername = findViewById(R.id.et_register_username);
        EditText etEmail = findViewById(R.id.et_register_email);
        EditText etPhone = findViewById(R.id.et_register_mobile);
        EditText etPassword = findViewById(R.id.et_register_password);
        EditText etConfirmPassword = findViewById(R.id.et_register_confirm_password);

        Button btnLogin = findViewById(R.id.btn_sign_up_login);
        Button btnRegister = findViewById(R.id.btn_sign_up_register);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            // Input validation
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(RegisterActivity.this, "Invalid Email Address!", Toast.LENGTH_SHORT).show();
                return; // Stop further processing
            }

            if (password.length() < 8 || !password.matches(".*[!@#$%^&*].*")) {
                Toast.makeText(RegisterActivity.this, "Password must be at least 8 characters long and include a special character.", Toast.LENGTH_SHORT).show();
                return; // Stop further processing
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                return; // Stop further processing
            }

            if (username.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
                return; // Stop further processing
            }

            // Proceed to register user in Firebase Authentication first
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        // User successfully created in Firebase Authentication

                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        // Create a user object for Firestore
                        Map<String, Object> user = new HashMap<>();
                        user.put("username", username);
                        user.put("email", email);
                        user.put("phone", phone);
                        user.put("password", password);

                        // Save additional user data to Firestore
                        db.collection("users")
                                .document(auth.getCurrentUser().getUid())  // Use Firebase User ID as document ID
                                .set(user)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(RegisterActivity.this, "Registration Done!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(RegisterActivity.this, "Registration Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });

                    })
                    .addOnFailureListener(e -> {
                        // Failed to register in Firebase Authentication
                        Toast.makeText(RegisterActivity.this, "Registration Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        btnLogin.setOnClickListener(v -> {
            Toast.makeText(RegisterActivity.this, "Login button clicked!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
