package uk.edu.le.co2124.frontend_app.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import uk.edu.le.co2124.frontend_app.R;

public class MainActivity extends AppCompatActivity {

    private Button newOrderButton;
    private TextView profileInitials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find and set the profile initials TextView
        profileInitials = findViewById(R.id.profileInitials);

        // Example full name (replace with real user data later)
        String fullName = "Darcy Boddey"; // Replace with session/user data
        String initials = getInitials(fullName);
        profileInitials.setText(initials);

        // Profile click action
        profileInitials.setOnClickListener(v -> {
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to profile screen
        });

        // Set up the "New Order" button
        newOrderButton = findViewById(R.id.newOrderButton);
        newOrderButton.setOnClickListener(v -> {
            Toast.makeText(this, "Starting new order...", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to new order screen
        });

        newOrderButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewOrderActivity.class);
            startActivity(intent);
        });

    }

    // Helper to extract initials from a full name
    private String getInitials(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) return "";
        String[] parts = fullName.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                sb.append(part.charAt(0));
            }
        }
        return sb.length() > 2 ? sb.substring(0, 2).toUpperCase() : sb.toString().toUpperCase();
    }
}
