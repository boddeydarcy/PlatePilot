package uk.edu.le.co2124.frontend_app.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import uk.edu.le.co2124.frontend_app.R;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the saved user name
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String fullName = prefs.getString("loggedInUser", "User");

        // Set profile initials
        TextView profileInitials = findViewById(R.id.profileInitials);
        profileInitials.setText(getInitials(fullName));

        profileInitials.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Set up New Order button
        Button newOrderButton = findViewById(R.id.newOrderButton);
        newOrderButton.setOnClickListener(v -> {
            Toast.makeText(this, "Starting new order...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, NewOrderActivity.class));
        });

        Button viewOrdersButton = findViewById(R.id.viewOrdersButton);
        viewOrdersButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewOrdersActivity.class)));
    }

    private String getInitials(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) return "";
        String[] parts = fullName.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                sb.append(Character.toUpperCase(part.charAt(0)));
            }
        }
        return sb.length() > 2 ? sb.substring(0, 2) : sb.toString();
    }
}
