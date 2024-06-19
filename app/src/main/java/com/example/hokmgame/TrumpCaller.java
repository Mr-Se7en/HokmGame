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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.os.Handler;
import android.os.Looper;

public class TrumpCaller extends Fragment {
    public interface OnCardDistributionCompleteListener {
        void onCardDistributionComplete();
    }

    private ImageView centerDeck;
    private ConstraintLayout rootLayout;
    private List<String> deck;

    public static int trump;

    private OnCardDistributionCompleteListener listener;
    private Handler handler;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCardDistributionCompleteListener) {
            listener = (OnCardDistributionCompleteListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement TrumpCaller.OnCardDistributionCompleteListener");
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
        centerDeck.setOnClickListener(v -> animateCardDistribution(player1, player2, player3, player4));
    }

    private void animateCardDistribution(ImageView player1, ImageView player2, ImageView player3, ImageView player4) {
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
                }
                else{
                    trump = playerIndex + 1;
                    if (listener != null) {
                        handler.postDelayed(() -> listener.onCardDistributionComplete(), 1000);
                    }
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
}
