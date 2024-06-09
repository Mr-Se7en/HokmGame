package com.example.hokmgame;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DynamicHorizontalOverlappingDecoration extends RecyclerView.ItemDecoration {
    private int overlapWidth;

    public void setOverlapWidth(int overlapWidth) {
        this.overlapWidth = overlapWidth;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int itemPosition = parent.getChildAdapterPosition(view);
        // Apply offset only for items after the first one
        if (itemPosition != 0) {
            outRect.left = -overlapWidth;
        }
    }
}


