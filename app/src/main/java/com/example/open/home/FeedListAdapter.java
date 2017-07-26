package com.example.open.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.open.R;

public class FeedListAdapter  extends RecyclerView.Adapter<FeedViewHolder>{
    private Context mContext;
    private LayoutInflater mInflater;

    public  FeedListAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_feeds,parent,false);
        return new FeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        holder.avatarView.setImageResource(R.drawable.guobiting);
        holder.nameText.setText("Jack");
        holder.timeText.setText("2017/07/19");
        holder.weatherText.setText("多云，北京");
        holder.imageView.setImageResource(R.drawable.seaside);
        holder.authorText.setText("摄影 | 混江龙");
        holder.categoryText.setText("游记");
        holder.favoriteText.setText("345");
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
