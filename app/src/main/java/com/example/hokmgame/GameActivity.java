package com.example.hokmgame;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class GameActivity extends AppCompatActivity implements TrumpCaller.OnCardDistributionCompleteListener {

    private RecyclerView recyclerViewPlayer1;
    private RecyclerView recyclerViewPlayer2;
    private RecyclerView recyclerViewPlayer3;
    private RecyclerView recyclerViewPlayer4;
    private CardAdapter cardAdapterPlayer1;
    private CardAdapterOpVertical cardAdapterPlayer2;
    private CardAdapterOpHorizontal cardAdapterPlayer3;
    private CardAdapterOpVertical cardAdapterPlayer4;

    private DynamicHorizontalOverlappingDecoration horizontalDecoration;
    private DynamicHorizontalOverlappingDecoration horizontalDecoration2;
    private DynamicVerticalOverlappingDecoration verticalDecoration;
    private DynamicVerticalOverlappingDecoration verticalDecoration2;
    private ImageView card1, card2, card3, card4;
    public static List<String> deck = new ArrayList<>();
    public List<String> cardListPlayer1 = new ArrayList<>();
    public List<String> cardListPlayer2 = new ArrayList<>();
    public List<String> cardListPlayer3 = new ArrayList<>();
    public List<String> cardListPlayer4 = new ArrayList<>();

    private static final List<String> SUIT_ORDER = Arrays.asList("h", "d", "c", "s");
    private static final List<String> RANK_ORDER = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k", "a");
    public TextView trumpText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        trumpText=findViewById(R.id.trumpText);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);


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


        String[] suits = {"h", "d", "c", "s"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k", "a"};

// Populate the card lists with the standard deck of cards
        for (String suit : suits) {
            for (String rank : ranks) {
                String card = suit+rank;
                deck.add(card);
            }
        }

        Collections.shuffle(deck);
//TODO:make the card distro after trump caller appointment
//        int numCardsPerPlayer = 13;
//        for (int i = 0; i < numCardsPerPlayer; i++) {
//            String card = deck.remove(0);
//            cardListPlayer1.add(card);
//        }
//        for (int i = 0; i < numCardsPerPlayer; i++) {
//            String card = deck.remove(0);
//            cardListPlayer2.add(card);
//        }
//        for (int i = 0; i < numCardsPerPlayer; i++) {
//            String card = deck.remove(0);
//            cardListPlayer3.add(card);
//        }
//        for (int i = 0; i < numCardsPerPlayer; i++) {
//            String card = deck.remove(0);
//            cardListPlayer4.add(card);
//        }

        // Add cards to the lists (example)
//        cardListPlayer1.add("h8");
//        cardListPlayer1.add("s2");
//        cardListPlayer1.add("ca");
//
//        cardListPlayer3.add("back");
//
//        cardListPlayer2.add("back_op");
//        cardListPlayer2.add("back_op");
//        cardListPlayer2.add("back_op");
//        cardListPlayer2.add("back_op");
//        cardListPlayer2.add("back_op");
//        cardListPlayer2.add("back_op");
//
//        cardListPlayer3.add("back");
//        cardListPlayer3.add("back");
//
//        cardListPlayer4.add("back_op");
//        cardListPlayer4.add("back_op");

        // Initialize CardAdapters for each player
        cardAdapterPlayer1 = new CardAdapter(this, cardListPlayer1);
        cardAdapterPlayer2 = new CardAdapterOpVertical(this, cardListPlayer2);
        cardAdapterPlayer3 = new CardAdapterOpHorizontal(this, cardListPlayer3);
        cardAdapterPlayer4 = new CardAdapterOpVertical(this, cardListPlayer4);

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

        if (savedInstanceState == null) {
            addFragment(new TrumpCaller());
        }
    }

    private void calculateAndSetOverlaps() {
        calculateHorizontalOverlap(recyclerViewPlayer1, cardAdapterPlayer1, horizontalDecoration);
        horizontalDecoration.setOverlapWidth(200);
        calculateVerticalOverlap(recyclerViewPlayer2, cardAdapterPlayer2, verticalDecoration);
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

    private void calculateVerticalOverlap(RecyclerView recyclerView, CardAdapterOpVertical adapter, DynamicVerticalOverlappingDecoration decoration) {
            decoration.setOverlapHeight(150);
            recyclerView.invalidateItemDecorations();
    }
    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.trump_caller, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onCardDistributed(String card, int playerIndex) {
        addCard(card, playerIndex);
        // Update the RecyclerView adapter for the corresponding player
        switch (playerIndex) {
            case 0:
                cardAdapterPlayer1.notifyDataSetChanged();
                break;
            case 1:
                cardAdapterPlayer2.notifyDataSetChanged();
                break;
            case 2:
                cardAdapterPlayer3.notifyDataSetChanged();
                break;
            case 3:
                cardAdapterPlayer4.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCardDistributionComplete() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment trumpCallerFragment = fragmentManager.findFragmentById(R.id.trump_caller);
        if (trumpCallerFragment != null) {
            fragmentTransaction.remove(trumpCallerFragment).commit();
        }
    }

    public void addCard(String card, int p) {
        switch (p) {
            case 0:
                cardListPlayer1.add(card);
                cardListPlayer1.sort((card1, card2) -> {
                    // Extract suits and ranks
                    String suit1 = card1.substring(0, 1);
                    String suit2 = card2.substring(0, 1);
                    String rank1 = card1.substring(1);
                    String rank2 = card2.substring(1);

                    // Compare suits
                    int suitComparison = Integer.compare(SUIT_ORDER.indexOf(suit1), SUIT_ORDER.indexOf(suit2));
                    if (suitComparison != 0) {
                        return suitComparison;
                    }

                    // If suits are the same, compare ranks
                    return Integer.compare(RANK_ORDER.indexOf(rank1), RANK_ORDER.indexOf(rank2));
                });
                break;
            case 1:
                cardListPlayer2.add(card);
                break;
            case 2:
                cardListPlayer3.add(card);
                break;
            case 3:
                cardListPlayer4.add(card);
                break;
            default:
                break;
        }
    }
    public void onBidComplete(int tr){
        switch (tr){
            case 1:
                trumpText.setText("Trump:Hearts");
                break;
            case 2:
                trumpText.setText("Trump:Diamonds");
                break;
            case 3:
                trumpText.setText("Trump:Clubs");
                break;
            case 4:
                trumpText.setText("Trump:Spades");
                break;
            default:
                trumpText.setText("Broken");
                break;
        }
    }
}

