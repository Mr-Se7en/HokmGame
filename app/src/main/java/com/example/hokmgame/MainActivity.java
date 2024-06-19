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

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button settingsBtn;
    private Button shareBtn; // Declare the share button
    private Button playBtn;

    public static HashMap<String,Integer> cardResourceMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cardResourceMap=initializeCardResourceMap();

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
        playBtn=findViewById(R.id.playButton);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivity(i);
            }
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
    private HashMap<String, Integer> initializeCardResourceMap() {
        HashMap<String, Integer> map = new HashMap<>();
        // Hearts
        map.put("ha", R.drawable.ha); // Ace of Hearts
        map.put("h2", R.drawable.h2); // Two of Hearts
        map.put("h3", R.drawable.h3); // Three of Hearts
        map.put("h4", R.drawable.h4); // Four of Hearts
        map.put("h5", R.drawable.h5); // Five of Hearts
        map.put("h6", R.drawable.h6); // Six of Hearts
        map.put("h7", R.drawable.h7); // Seven of Hearts
        map.put("h8", R.drawable.h8); // Eight of Hearts
        map.put("h9", R.drawable.h9); // Nine of Hearts
        map.put("h10", R.drawable.h10); // Ten of Hearts
        map.put("hj", R.drawable.hj); // Jack of Hearts
        map.put("hq", R.drawable.hq); // Queen of Hearts
        map.put("hk", R.drawable.hk); // King of Hearts

        // Diamonds
        map.put("da", R.drawable.da); // Ace of Diamonds
        map.put("d2", R.drawable.d2); // Two of Diamonds
        map.put("d3", R.drawable.d3); // Three of Diamonds
        map.put("d4", R.drawable.d4); // Four of Diamonds
        map.put("d5", R.drawable.d5); // Five of Diamonds
        map.put("d6", R.drawable.d6); // Six of Diamonds
        map.put("d7", R.drawable.d7); // Seven of Diamonds
        map.put("d8", R.drawable.d8); // Eight of Diamonds
        map.put("d9", R.drawable.d9); // Nine of Diamonds
        map.put("d10", R.drawable.d10); // Ten of Diamonds
        map.put("dj", R.drawable.dj); // Jack of Diamonds
        map.put("dq", R.drawable.dq); // Queen of Diamonds
        map.put("dk", R.drawable.dk); // King of Diamonds

        // Clubs
        map.put("ca", R.drawable.ca); // Ace of Clubs
        map.put("c2", R.drawable.c2); // Two of Clubs
        map.put("c3", R.drawable.c3); // Three of Clubs
        map.put("c4", R.drawable.c4); // Four of Clubs
        map.put("c5", R.drawable.c5); // Five of Clubs
        map.put("c6", R.drawable.c6); // Six of Clubs
        map.put("c7", R.drawable.c7); // Seven of Clubs
        map.put("c8", R.drawable.c8); // Eight of Clubs
        map.put("c9", R.drawable.c9); // Nine of Clubs
        map.put("c10", R.drawable.c10); // Ten of Clubs
        map.put("cj", R.drawable.cj); // Jack of Clubs
        map.put("cq", R.drawable.cq); // Queen of Clubs
        map.put("ck", R.drawable.ck); // King of Clubs

        // Spades
        map.put("sa", R.drawable.sa); // Ace of Spades
        map.put("s2", R.drawable.s2); // Two of Spades
        map.put("s3", R.drawable.s3); // Three of Spades
        map.put("s4", R.drawable.s4); // Four of Spades
        map.put("s5", R.drawable.s5); // Five of Spades
        map.put("s6", R.drawable.s6); // Six of Spades
        map.put("s7", R.drawable.s7); // Seven of Spades
        map.put("s8", R.drawable.s8); // Eight of Spades
        map.put("s9", R.drawable.s9); // Nine of Spades
        map.put("s10", R.drawable.s10); // Ten of Spades
        map.put("sj", R.drawable.sj); // Jack of Spades
        map.put("sq", R.drawable.sq); // Queen of Spades
        map.put("sk", R.drawable.sk); // King of Spades

        //back
        map.put("back", R.drawable.posht);
        map.put("back_op", R.drawable.posht_r);

        return map;
    }
}
