package com.cinema.client.etc;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cinema.client.R;

public class SwipeCardItem extends RecyclerView.ViewHolder {
    TextView textView;

    public SwipeCardItem(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text);
    }

    public void bind(String str) {
        textView.setText(str);
    }
}
