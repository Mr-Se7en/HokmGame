package com.example.hokmgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private final Context context;
    private final List<String> cardList;
    private final Map<String, Integer> cardResourceMap;

    public CardAdapter(Context context, List<String> cardList) {
        this.context = context;
        this.cardList = cardList;
        this.cardResourceMap = initializeCardResourceMap();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_vertical, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        String card = cardList.get(position);
        Integer resourceId = cardResourceMap.get(card);
        if (resourceId != null) {
            holder.cardImage.setImageResource(resourceId);
        } else {
            // Handle missing resource ID case (optional)
            holder.cardImage.setImageResource(R.drawable.posht); // Placeholder image
        }
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView cardImage;

        public CardViewHolder(View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.card_image);
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
