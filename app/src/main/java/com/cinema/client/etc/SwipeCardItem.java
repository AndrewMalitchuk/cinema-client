package com.cinema.client.etc;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cinema.client.R;

import lombok.Getter;
import lombok.Setter;

public class SwipeCardItem extends RecyclerView.ViewHolder {

    private TextView posterNameSwipeCardTextView;

    private ImageView posterSwipeCardImageView;

    @Setter
    @Getter
    private Context context;

    public SwipeCardItem(View itemView) {
        super(itemView);
        posterNameSwipeCardTextView = itemView.findViewById(R.id.posterNameSwipeCardTextView);
        posterSwipeCardImageView = itemView.findViewById(R.id.posterSwipeCardImageView);
    }

    public void bind(String title, String url, Context context) {
        posterNameSwipeCardTextView.setText(title);
        Glide.with(context).load(url).into(posterSwipeCardImageView);
    }

}
