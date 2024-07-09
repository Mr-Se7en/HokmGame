package com.example.hokmgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapterOpHorizontal extends RecyclerView.Adapter<CardAdapterOpHorizontal.CardViewHolder> {

    private final Context context;
    private final List<String> cardList;

    public CardAdapterOpHorizontal(Context context, List<String> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_vertical, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.cardImage.setImageResource(R.drawable.posht);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public void removeCard(String card) {
        int position = cardList.indexOf(card);
        if (position >= 0) {
            cardList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cardList.size());
        }
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView cardImage;

        public CardViewHolder(View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.card_image);
        }
    }
}

