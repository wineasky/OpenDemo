package com.example.open.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class FeedListView  extends FrameLayout{
    FeedListAdapter listAdapter;
    public FeedListView(Context context) {
        this(context,null);
    }

    public FeedListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FeedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        listAdapter = new FeedListAdapter(context);

        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(listAdapter);
        recyclerView.addItemDecoration(new SplitItemDecoration(context));
        addView(recyclerView,new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }
}
