package com.example.open.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.open.R;
//todo

public class SplitItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int margin;

    private int topMargin;

    public SplitItemDecoration(Context ctx) {
        this(ctx, 0);
    }

    public SplitItemDecoration(Context ctx, int margin) {
        this.margin = margin;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ctx.getResources().getColor(R.color.background));
    }

    public void setTopMargin(int margin) {
        topMargin = margin;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + margin;
        int right = parent.getWidth() - parent.getPaddingRight() - margin;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + 1;

            c.drawRect(left, top, right, bottom, paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (topMargin == 0) return;

        int idx = parent.getChildAdapterPosition(view);
        if (idx == RecyclerView.NO_POSITION) return;

        outRect.set(0, idx == 0 ? 0 : topMargin, 0, 0);
    }
}
