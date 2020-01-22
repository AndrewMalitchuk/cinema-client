package com.cinema.client.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.bumptech.glide.Glide;
import com.cinema.client.R;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.entities.FilmAPI;

import java.util.ArrayList;

public class DemoInfiniteAdapter extends LoopingPagerAdapter<FilmAPI> {

    private static final int VIEW_TYPE_NORMAL = 100;
//    private  ArrayList<FilmAPI> itemList;


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

//        convertView.findViewById(R.id.image).setBackgroundColor(context.getResources().getColor(getBackgroundColor(listPosition)));

        ImageView imageView=convertView.findViewById(R.id.curentFilmPosterItemPagerImageView);

        Glide.with(this.context).load(APIClient.HOST+itemList.get(listPosition).getPicUrl()).into(imageView);
//        Log.e("LOAD",itemList.get(listPosition).toString());

//        switch (listPosition){
//            case 0:
//                Glide.with(this.context).load(APIClient.HOST+itemList.get(listPosition).getPicUrl()).into(imageView);
//                break;
//            case 1:
//                break;
//            case 2:
//                break;
//
//        }


    }


//    private int getBackgroundColor(int number) {
//        switch (number) {
//            case 0:
//                return android.R.color.holo_red_light;
//            case 1:
//                return android.R.color.holo_orange_light;
//            case 2:
//                return android.R.color.holo_green_light;
//            case 3:
//                return android.R.color.holo_blue_light;
//            case 4:
//                return android.R.color.holo_purple;
//            case 5:
//                return android.R.color.black;
//            default:
//                return android.R.color.black;
//        }
//    }
}

