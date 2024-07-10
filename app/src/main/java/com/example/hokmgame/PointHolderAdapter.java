package com.example.hokmgame;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class PointHolderAdapter extends RecyclerView.Adapter<PointHolderAdapter.CardViewHolder> {

    private final List<PointItem> cardItemList;
    private boolean vertical=true;

    public PointHolderAdapter(List<PointItem> cardItemList,boolean v) {
        this.cardItemList = cardItemList;
        vertical=v;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(vertical)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_item_vertical, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_item_horizontal, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        PointItem cardItem = cardItemList.get(position);
        holder.editTextNumberSigned.setText(String.valueOf(cardItem.getCardNumber()));
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView editTextNumberSigned;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextNumberSigned = itemView.findViewById(R.id.cardNumber);
        }
    }
}
