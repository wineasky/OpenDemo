package com.example.open.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.open.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class FeedViewHolder  extends RecyclerView.ViewHolder{

    public CircleImageView avatarView;

    public ImageView imageView;

    public TextView nameText, timeText, weatherText, authorText,
            categoryText, favoriteText;

    public ImageButton shareBtn;

    public FeedViewHolder(View itemView) {
        super(itemView);

        avatarView = (CircleImageView) itemView.findViewById(R.id.avatar_view);
        imageView = (ImageView) itemView.findViewById(R.id.image_view);
        nameText = (TextView) itemView.findViewById(R.id.name_text);
        timeText = (TextView) itemView.findViewById(R.id.time_label);
        weatherText = (TextView) itemView.findViewById(R.id.weather_label);
        authorText = (TextView) itemView.findViewById(R.id.author);
        categoryText = (TextView) itemView.findViewById(R.id.category);
        favoriteText = (TextView) itemView.findViewById(R.id.favorite_count);
        shareBtn = (ImageButton) itemView.findViewById(R.id.btn_share);
    }
}
