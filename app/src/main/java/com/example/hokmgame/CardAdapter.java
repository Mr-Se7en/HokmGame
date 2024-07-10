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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private final List<String> cardList;
    private final OnItemClickListener listener;

    public CardAdapter(List<String> cardList, OnItemClickListener listener) {
        this.cardList = cardList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_vertical, parent, false);
        return new CardViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        String card = cardList.get(position);
        Integer resourceId = MainActivity.cardResourceMap.get(card);
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

        public CardViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.card_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
