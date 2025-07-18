package com.example.lifeease;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Get the total cost from the intent
        float totalCost = getIntent().getFloatExtra("total_cost", 0);

        // Display the total cost in a TextView
        TextView textViewMessage = findViewById(R.id.text_view_message);
        textViewMessage.setText("Your order has been placed successfully!\nTotal Cost: " + totalCost + "/="+"\nCash On Delivery!\n Thank you for your order!");
    }
}
