package com.example.hokmgame;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPlayer1;
    private RecyclerView recyclerViewPlayer2;
    private RecyclerView recyclerViewPlayer3;
    private RecyclerView recyclerViewPlayer4;
    private CardAdapter cardAdapterPlayer1;
    private CardAdapter cardAdapterPlayer2;
    private CardAdapter cardAdapterPlayer3;
    private CardAdapter cardAdapterPlayer4;

    private DynamicHorizontalOverlappingDecoration horizontalDecoration;
    private DynamicHorizontalOverlappingDecoration horizontalDecoration2;
    private DynamicVerticalOverlappingDecoration verticalDecoration;
    private DynamicVerticalOverlappingDecoration verticalDecoration2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize RecyclerViews
        recyclerViewPlayer1 = findViewById(R.id.player1_hand_recyclerview);
        recyclerViewPlayer2 = findViewById(R.id.player2_hand_recyclerview);
        recyclerViewPlayer3 = findViewById(R.id.player3_hand_recyclerview);
        recyclerViewPlayer4 = findViewById(R.id.player4_hand_recyclerview);

        // Set LayoutManagers
        recyclerViewPlayer1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPlayer2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewPlayer3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPlayer4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Initialize Card Lists for each player
        List<String> cardListPlayer1 = new ArrayList<>();
        List<String> cardListPlayer2 = new ArrayList<>();
        List<String> cardListPlayer3 = new ArrayList<>();
        List<String> cardListPlayer4 = new ArrayList<>();

        // Add cards to the lists (example)
        cardListPlayer1.add("h8");
        cardListPlayer1.add("s2");
//        for (int j = 0; j < 15; j++) {
            cardListPlayer1.add("ca");
//        }
//        for (int j = 0; j < 15; j++) {
            cardListPlayer3.add("back");
//        }

        cardListPlayer2.add("back_op");
        cardListPlayer2.add("back_op");
        cardListPlayer2.add("back_op");
        cardListPlayer2.add("back_op");
        cardListPlayer2.add("back_op");
        cardListPlayer2.add("back_op");

        cardListPlayer3.add("back");
        cardListPlayer3.add("back");

        cardListPlayer4.add("back_op");
        cardListPlayer4.add("back_op");

        // Initialize CardAdapters for each player
        cardAdapterPlayer1 = new CardAdapter(this, cardListPlayer1);
        cardAdapterPlayer2 = new CardAdapter(this, cardListPlayer2);
        cardAdapterPlayer3 = new CardAdapter(this, cardListPlayer3);
        cardAdapterPlayer4 = new CardAdapter(this, cardListPlayer4);

        // Set Adapters to RecyclerViews
        recyclerViewPlayer1.setAdapter(cardAdapterPlayer1);
        recyclerViewPlayer2.setAdapter(cardAdapterPlayer2);
        recyclerViewPlayer3.setAdapter(cardAdapterPlayer3);
        recyclerViewPlayer4.setAdapter(cardAdapterPlayer4);

        // Initialize Decorations
        horizontalDecoration = new DynamicHorizontalOverlappingDecoration();
        horizontalDecoration2 = new DynamicHorizontalOverlappingDecoration();

        verticalDecoration = new DynamicVerticalOverlappingDecoration();
        verticalDecoration2 = new DynamicVerticalOverlappingDecoration();

        // Add ItemDecorations
        recyclerViewPlayer1.addItemDecoration(horizontalDecoration);
        recyclerViewPlayer2.addItemDecoration(verticalDecoration);
        recyclerViewPlayer3.addItemDecoration(horizontalDecoration2);
        recyclerViewPlayer4.addItemDecoration(verticalDecoration2);

        // Calculate and set overlaps
        calculateAndSetOverlaps();
    }

    private void calculateAndSetOverlaps() {
        calculateHorizontalOverlap(recyclerViewPlayer1, cardAdapterPlayer1, horizontalDecoration);
        horizontalDecoration.setOverlapWidth(200);
        calculateVerticalOverlap(recyclerViewPlayer2, cardAdapterPlayer2, verticalDecoration);
//        calculateHorizontalOverlap(recyclerViewPlayer3, cardAdapterPlayer3, horizontalDecoration2);
        horizontalDecoration2.setOverlapWidth(150);
        calculateVerticalOverlap(recyclerViewPlayer4, cardAdapterPlayer4, verticalDecoration2);
    }

    private void calculateHorizontalOverlap(RecyclerView recyclerView, CardAdapter adapter, DynamicHorizontalOverlappingDecoration horizontalDecoration) {
        int itemCount = adapter.getItemCount();
        if (itemCount > 1) {
            int totalWidth = recyclerView.getWidth();
            int cardWidth = 150;
            int overlapWidth = Math.max(0, Math.abs((totalWidth - cardWidth) / itemCount));
            horizontalDecoration.setOverlapWidth(overlapWidth);
            recyclerView.invalidateItemDecorations();
        }
    }

    private void calculateVerticalOverlap(RecyclerView recyclerView, CardAdapter adapter, DynamicVerticalOverlappingDecoration decoration) {
//        int itemCount = adapter.getItemCount();
//        if (itemCount > 1) {
//            int totalHeight = 100;
//            int cardHeight = 150; // Example height
//            int overlapHeight = (totalHeight - cardHeight) / (itemCount-1 );
            decoration.setOverlapHeight(150);
            recyclerView.invalidateItemDecorations();
//        }
    }
}
