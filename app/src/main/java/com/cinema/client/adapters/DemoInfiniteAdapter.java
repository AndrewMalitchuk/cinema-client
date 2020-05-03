package com.cinema.client.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.bumptech.glide.Glide;
import com.cinema.client.R;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.entities.FilmAPI;

import java.util.ArrayList;

public class DemoInfiniteAdapter extends LoopingPagerAdapter<FilmAPI> {

    private static final int VIEW_TYPE_NORMAL = 100;

    public DemoInfiniteAdapter(Context context, ArrayList<FilmAPI> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }

    @Override
    protected int getItemViewType(int listPosition) {
        return VIEW_TYPE_NORMAL;
    }

    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.item_pager, container, false);
    }

    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
        ImageView imageView = convertView.findViewById(R.id.curentFilmPosterItemPagerImageView);
        Glide.with(this.context).load(APIClient.HOST + itemList.get(listPosition).getPicUrl()).into(imageView);
    }

}

