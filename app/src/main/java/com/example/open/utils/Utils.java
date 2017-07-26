package com.example.open.utils;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;

public class Utils {

    public static int dp2px(Context ctx, int d) {
        return (int) (ctx.getResources().getDisplayMetrics().density * d + .5f);
    }
    public static TextView getTextView(Context ctx, String s, int color, float size) {
        TextView v = new TextView(ctx);
        v.setTextColor(color);
        v.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        v.setText(s);

        return v;
    }
}
