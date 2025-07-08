package uk.edu.le.co2124.frontend_app.ui.activities;

import static androidx.fragment.app.FragmentManagerKt.commit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import uk.edu.le.co2124.frontend_app.R;
import uk.edu.le.co2124.frontend_app.ui.fragments.MainsFragment;

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

        // Find and set profile initials
        profileInitials = findViewById(R.id.profileInitials);
        String fullName = "Darcy Boddey"; // Replace with real user session data
        profileInitials.setText(getInitials(fullName));

        profileInitials.setOnClickListener(v -> {
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show();
            // TODO: Navigate to profile screen
        });

        // Set up New Order button (launches NewOrderActivity)
        newOrderButton = findViewById(R.id.newOrderButton);
        newOrderButton.setOnClickListener(v -> {
            Toast.makeText(this, "Starting new order...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, NewOrderActivity.class);
            startActivity(intent);
        });
    }

    // Extract initials from full name
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
