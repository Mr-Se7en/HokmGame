package com.example.hokmgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.OnApplyWindowInsetsListener;

public class MainActivity extends AppCompatActivity {
    private Button settingsBtn;
    private Button shareBtn; // Declare the share button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assuming EdgeToEdge.enable(this) is a custom method you've defined elsewhere
        EdgeToEdge.enable(this);

        settingsBtn = findViewById(R.id.settingsButton);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

        shareBtn = findViewById(R.id.shareButton); // Initialize the share button
        shareBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareGameLink(); // Call the method to share the game link
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void shareGameLink() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this game!");
            String shareMessage = "Let me recommend you this game:\n\n";
            shareMessage += "https://play.google.com/store/apps/details?id=";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
