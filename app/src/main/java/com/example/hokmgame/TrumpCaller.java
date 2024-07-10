package com.example.hokmgame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

public class TrumpCaller extends Fragment {

    public interface OnCardDistributionCompleteListener {
        void onCardDistributionComplete();
        void onCardDistributed(String card, int playerIndex);
        void onBidComplete(int tr);
        void onTrumpApp(int tr);
    }
    public class playercounter{
        //H D C S
        //j q k a
        public int[] cards={0,0,0,0,0,0,0,0};
        public void counter(String card){
            String suit = card.substring(0, 1);
            String rank = card.substring(1);
            int val;
            if(suit.equals("h")){
                cards[0]++;
                if(rank.equals("j")){
                    val=11;
                }
                else if(rank.equals("q")){
                    val=12;
                }
                else if(rank.equals("k")){
                    val=13;
                }
                else if(rank.equals("a")){
                    val=14;
                }
                else {
                    val=Integer.parseInt(rank);
                }
                cards[4]+=val;
            }
            if(suit.equals("d")){
                cards[1]++;
                if(rank.equals("j")){
                    val=11;
                }
                else if(rank.equals("q")){
                    val=12;
                }
                else if(rank.equals("k")){
                    val=13;
                }
                else if(rank.equals("a")){
                    val=14;
                }
                else {
                    val=Integer.parseInt(rank);
                }
                cards[5]+=val;
            }
            if(suit.equals("c")){
                cards[2]++;
                if(rank.equals("j")){
                    val=11;
                }
                else if(rank.equals("q")){
                    val=12;
                }
                else if(rank.equals("k")){
                    val=13;
                }
                else if(rank.equals("a")){
                    val=14;
                }
                else {
                    val=Integer.parseInt(rank);
                }
                cards[6]+=val;
            }
            if(suit.equals("s")){
                cards[3]++;
                if(rank.equals("j")){
                    val=11;
                }
                else if(rank.equals("q")){
                    val=12;
                }
                else if(rank.equals("k")){
                    val=13;
                }
                else if(rank.equals("a")){
                    val=14;
                }
                else {
                    val=Integer.parseInt(rank);
                }
                cards[7]+=val;
            }

        }
    }
    private final playercounter playerc2=new playercounter();
    private final playercounter playerc3=new playercounter();
    private final playercounter playerc4=new playercounter();
    private ImageView centerDeck;
    private ConstraintLayout rootLayout;
    private List<String> deck;
    public static int trump;
    private OnCardDistributionCompleteListener listener;
    private Handler handler;
    public static String selectedSuit;

    TrumpCaller(int t){
        trump=t;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCardDistributionCompleteListener) {
            listener = (OnCardDistributionCompleteListener) context;
        } else {
            throw new ClassCastException(context + " must implement TrumpCaller.OnCardDistributionCompleteListener");
        }
        handler = new Handler(Looper.getMainLooper());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trump_caller, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        centerDeck = view.findViewById(R.id.centerDeck);
        rootLayout = view.findViewById(R.id.rootLayout);

        ImageView player1 = view.findViewById(R.id.player1);
        ImageView player2 = view.findViewById(R.id.player2);
        ImageView player3 = view.findViewById(R.id.player3);
        ImageView player4 = view.findViewById(R.id.player4);

        String[] suits = {"h", "d", "c", "s"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "j", "q", "k", "a"};

        deck = new ArrayList<>();
        for (String suit : suits) {
            for (String rank : ranks) {
                String card = suit + rank;
                deck.add(card);
            }
        }

        Collections.shuffle(deck);
        // Start animations as soon as the view is created
        if(trump<=0)
            centerDeck.setOnClickListener(v -> animateCardDistribution(player1, player2, player3, player4));
        else{
            listener.onTrumpApp(trump);
            handler.postDelayed(() -> startDistributionFromTrumpPlayer(new ImageView[]{player1, player2, player3, player4}), 3000);
        }

    }

    private void bidder(playercounter playerc2,playercounter playerc3,playercounter playerc4,int index){
        int max,maxIndex=0;
        switch (index){
            case 1:
                max=playerc2.cards[0];
                for(int i=1;i<4;i++){
                    if(max==playerc2.cards[i]){
                       if(playerc2.cards[maxIndex+4]<playerc2.cards[i+4]){
                           max=playerc2.cards[i];
                           maxIndex=i;
                       }
                    }
                }
                break;
            case 2:
                max=playerc3.cards[0];
                for(int i=1;i<4;i++){
                    if(max==playerc3.cards[i]){
                        if(playerc3.cards[maxIndex+4]<playerc3.cards[i+4]){
                            max=playerc3.cards[i];
                            maxIndex=i;
                        }
                    }
                }
                break;
            case 3:
                max=playerc4.cards[0];
                for(int i=1;i<4;i++){
                    if(max==playerc4.cards[i]){
                        if(playerc4.cards[maxIndex+4]<playerc4.cards[i+4]){
                            max=playerc4.cards[i];
                            maxIndex=i;
                        }
                    }
                }
                break;
        }
        switch (maxIndex) {
            case 0:
                selectedSuit = "h";
                break;
            case 1:
                selectedSuit = "d";
                break;
            case 2:
                selectedSuit = "c";
                break;
            case 3:
                selectedSuit = "s";
                break;
            default:
                break;
        }
    }

    private void animateCardDistribution(ImageView player1, ImageView player2, ImageView player3, ImageView player4) {
        centerDeck.setClickable(false);
        distributeCardsSequentially(new ImageView[]{player1, player2, player3, player4}, 0);
    }

    private void distributeCardsSequentially(ImageView[] players, int playerIndex) {
        if (deck.isEmpty()) return;

        String card = deck.remove(0);
        ImageView currentPlayer = players[playerIndex];

        moveCardToPositionWithDelay(currentPlayer, 0, card, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cardAssign(currentPlayer, card);
                if (card.charAt(1) != 'a') {
                    distributeCardsSequentially(players, (playerIndex + 1) % players.length);
                } else {
                    trump = (playerIndex+1)%4;
                    if(trump==0) trump=4;
                    listener.onTrumpApp(trump);
                    // Add a delay before starting card distribution
                    handler.postDelayed(() -> startDistributionFromTrumpPlayer(players), 3000); // 3 seconds delay
                }
            }
        });
    }

    private void cardAssign(ImageView player, String card) {
        Integer resourceId = MainActivity.cardResourceMap.get(card);
        if (resourceId != null) {
            player.setImageResource(resourceId);
        } else {
            // Handle missing resource ID case (optional)
            player.setImageResource(R.drawable.posht); // Placeholder image
        }
    }

    private void moveCardToPositionWithDelay(View targetView, long delay, String card, AnimatorListenerAdapter listener) {
        // Create a temporary card ImageView
        ImageView tempCard = new ImageView(getContext());
        tempCard.setImageDrawable(centerDeck.getDrawable());

        // Set initial position of the temporary card to match the center deck
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(centerDeck.getWidth(), centerDeck.getHeight());
        tempCard.setX(centerDeck.getX());
        tempCard.setY(centerDeck.getY());
        rootLayout.addView(tempCard, params);

        // Calculate the end position
        float endX = targetView.getX();
        float endY = targetView.getY();

        // Animate the temporary card
        ObjectAnimator moveX = ObjectAnimator.ofFloat(tempCard, "x", endX);
        ObjectAnimator moveY = ObjectAnimator.ofFloat(tempCard, "y", endY);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(moveX, moveY);
        animatorSet.setDuration(150); // Animation duration in milliseconds
        animatorSet.setStartDelay(delay);
        animatorSet.start();

        // Add listener to remove the temporary card and start the next animation
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rootLayout.removeView(tempCard);
                listener.onAnimationEnd(animation);
            }
        });
    }

    private void startDistributionFromTrumpPlayer(ImageView[] players) {
        // Remove the ImageView objects as needed (e.g., player1, player2, etc.)
        for (ImageView player : players) {
            player.setImageDrawable(null);
        }
        // Start distributing cards to each player starting from the trump player
        distributeCards(players, trump);
    }

    private void distributeCards(ImageView[] players, int trump) {
        distributeCardsForRound(players, trump, 5);

        if(trump ==1)
            handler.postDelayed(() -> showTrumpSuitDialog(players,trump), 3000);
        else{
            bidder(playerc2,playerc3,playerc4,trump);
//            distributeRemainingCards(players, trump);
            handler.postDelayed(() -> distributeRemainingCards(players, trump), 3000);
        }
    }

    private void showTrumpSuitDialog(ImageView[] players, int trump) {
        // Create an AlertDialog to prompt the user to bid the trump suit
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Trump Suit")
                .setItems(new CharSequence[]{"Hearts", "Diamonds", "Clubs", "Spades"}, (dialog, which) -> {

                    switch (which) {
                        case 1:
                            selectedSuit = "d";
                            break;
                        case 2:
                            selectedSuit = "c";
                            break;
                        case 3:
                            selectedSuit = "s";
                            break;
                        default:
                            selectedSuit = "h";
                            break;
                    }
                    // Do something with the selected suit
                    distributeRemainingCards(players, trump);
                });
        builder.create().show();
    }

    private void distributeRemainingCards(ImageView[] players, int trump) {
        switch (selectedSuit){
            case "h":
                listener.onBidComplete(1);
                break;
            case "d":
                listener.onBidComplete(2);
                break;
            case "c":
                listener.onBidComplete(3);
                break;
            case "s":
                listener.onBidComplete(4);
                break;
            default:
                listener.onBidComplete(-1);
                break;
        }
        distributeCardsForRound(players, trump, 4);
        distributeCardsForRound(players, trump, 4);

        if (listener != null) {
            handler.postDelayed(() -> listener.onCardDistributionComplete(), 3000);
        }
    }

    private void distributeCardsForRound(ImageView[] players, int trump, int cardsPerPlayer) {
        Handler handler = new Handler(Looper.getMainLooper());
        long delayIncrement = 130; // Delay increment for each card (e.g., 500 milliseconds)
        long currentDelay = 0;

        for (int i = 0; i < 4; i++) {
            int index = (trump + i-1) % 4;
            ImageView currentPlayer = players[index];
            for (int j = 0; j < cardsPerPlayer; j++) {
                String card = GameActivity.deck.remove(0);
                switch (index){
                    case 1:
                        playerc2.counter(card);
                        break;
                    case 2:
                        playerc3.counter(card);
                        break;
                    case 3:
                        playerc4.counter(card);
                        break;
                }
                handler.postDelayed(() -> moveCardToPositionWithDelay(currentPlayer, 0, card, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (listener != null) {
                            listener.onCardDistributed(card, index);
                        }
                    }
                }), currentDelay);
                currentDelay += delayIncrement; // Increment the delay for the next card
            }
        }
    }
}


