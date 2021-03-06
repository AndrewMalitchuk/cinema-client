package com.cinema.client.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cinema.client.R;
import com.cinema.client.activity.AboutCinemaActivity;
import com.cinema.client.entities.CinemaItemSearch;

import java.util.List;

public class CinemaSearchAdapter extends RecyclerView.Adapter<CinemaSearchAdapter.ViewHolder> {

    List<CinemaItemSearch> cinemaItemSearchList;

    Context context;

    private boolean isForSearch;

    private Intent intent;

    private Activity activity;

    public CinemaSearchAdapter(List<CinemaItemSearch> cinemaItemSearchList) {
        this.cinemaItemSearchList = cinemaItemSearchList;
    }

    public CinemaSearchAdapter(List<CinemaItemSearch> cinemaItemSearchList, boolean isForSearch, Intent intent, Activity activity) {
        this.cinemaItemSearchList = cinemaItemSearchList;
        this.isForSearch = isForSearch;
        this.intent = intent;
        this.activity = activity;
    }

    @Override
    public CinemaSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cinemas_item, parent, false);
        CinemaSearchAdapter.ViewHolder viewHolder = new CinemaSearchAdapter.ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CinemaSearchAdapter.ViewHolder holder, final int position) {
        CinemaItemSearch cinemaItemSearch = cinemaItemSearchList.get(position);
        Glide.with(context).load(cinemaItemSearch.getCinemaImg()).into(holder.cinemaImage);
        holder.cinemaName.setText(cinemaItemSearch.getCinemaName());
        holder.cinemaAddress.setText(cinemaItemSearch.getCinemaAddress());
        holder.cv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isForSearch) {
                    intent.putExtra("cinemaName", cinemaItemSearch.getCinemaName());
                    intent.putExtra("cinemaId", cinemaItemSearch.getCinemaId());
                    activity.setResult(Activity.RESULT_OK, intent);
                    activity.finish();
                } else {

                    Intent intent = new Intent(context, AboutCinemaActivity.class);
                    intent.putExtra("cinemaId", cinemaItemSearch.getCinemaId());
                    context.startActivity(intent);
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return cinemaItemSearchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cinemaImage;
        TextView cinemaName;
        TextView cinemaAddress;
        CardView cv;

        public ViewHolder(View itemView) {
            super(itemView);
            cinemaImage = (ImageView) itemView.findViewById(R.id.cinemaImage);
            cinemaName = (TextView) itemView.findViewById(R.id.cinemaName);
            cinemaAddress = (TextView) itemView.findViewById(R.id.cinemaAddress);
            cv = (CardView) itemView.findViewById(R.id.cv);
        }

    }

}