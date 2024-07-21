package com.example.cp;// Import necessary packages
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Find buttons by ID
        Button ratingButton = findViewById(R.id.button1);
        Button complainButton = findViewById(R.id.complain);

//         Set click listener for the rating button
        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the RatingActivity
                Intent intent = new Intent(dashboard.this, Rating.class);
                startActivity(intent);
            }
        });

        // Set click listener for the complain button
        complainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the ComplainActivity
                Intent intent = new Intent(dashboard.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
