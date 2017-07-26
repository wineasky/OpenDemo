package com.example.open.home;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.open.OnTabSelectListener;
import com.example.open.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.example.open.utils.Utils.dp2px;
import static com.example.open.utils.Utils.getTextView;

//上方标签栏：关注、最新、精选
public class HomeNavigationBar extends LinearLayout implements View.OnClickListener{
    //文字与选中的TAG
    private static final String TAG_TEXT = "";
    private static final String TAG_STRIP = "";

    private int current = -1;

    private OnTabSelectListener listener;
    public HomeNavigationBar(Context context) {
       this(context,null);
    }

    public HomeNavigationBar(Context context, AttributeSet attrs) {
       this(context, attrs,0);
    }

    public HomeNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context ctx) {
        //水平布局
        setOrientation(HORIZONTAL);
        String[] titles = getResources().getStringArray(R.array.home_tabs);
        LayoutParams lp = new LayoutParams(dp2px(ctx,35+24), MATCH_PARENT);

        for (int i = 0;i < titles.length; i++ ){
            RelativeLayout rl = getItemView(ctx,titles[i],i);
            addView(rl);
        }
       setIndexAt(1);
    }

    public void setIndexAt(int i) {
        if (current != -1){
            setStatus(current,false);
        }
        setStatus(i,true);
        current = i;
    }

    //TODO  Visibility
    private void setStatus(int idx, boolean show) {
        findViewWithTag(String.format(TAG_TEXT,idx)).setSelected(show);
        findViewWithTag(String.format(TAG_STRIP,idx)).setVisibility(show ? VISIBLE : GONE);
    }


    private RelativeLayout getItemView(Context ctx, String text, int i) {
        RelativeLayout layout = new RelativeLayout(ctx);
        layout.setTag(i);
        layout.setOnClickListener(this);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        TextView v = getTextView(ctx, text, Color.TRANSPARENT, 14.f);
        v.setTag(String.format(TAG_TEXT, i));
        v.setTextColor(getResources().getColorStateList(R.color.home_tab_text_color));
        layout.addView(v, lp);

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(dp2px(ctx, 35),
                dp2px(ctx, 2));
        lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        View strip = new View(ctx);
        strip.setTag(String.format(TAG_STRIP, i));
        strip.setBackgroundColor(0xFFFF8665);
        strip.setVisibility(GONE);
        layout.addView(strip, lp1);

        return layout;
    }


    private RelativeLayout getItemView1(Context ctx, String title, int i) {
        //整块区域
        RelativeLayout layout = new RelativeLayout(ctx);
        layout.setTag(i);
        layout.setOnClickListener(this);

        //标题
        RelativeLayout.LayoutParams  rlp = new RelativeLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        TextView tv =getTextView(ctx,title, Color.TRANSPARENT,14.f);
        tv.setTag(String.format(TAG_TEXT,i));
        tv.setTextColor(R.color.home_tab_text_color);
        layout.addView(tv,rlp);

        //标题所在的小区域
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(dp2px(ctx,35),dp2px(ctx,2));
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        View strip = new View(ctx);
        strip.setTag(String.format(TAG_STRIP));
        strip.setBackgroundColor(Color.WHITE);
        strip.setVisibility(GONE);
        layout.addView(strip,lp);

        return layout;
    }


    public void setOnTabSelectListener(OnTabSelectListener listener){
        this.listener = listener;
    }
     @Override
    public void onClick(View v) {
        if(null == v.getTag())
            return;
         int idx = (Integer)v.getTag();
         setIndexAt(idx);
        if (null != listener)
            listener.onTabSelected(idx);
    }
}
