package com.cinema.client.etc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinema.client.R;
import com.cinema.client.activity.AboutFilmActivity;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.entities.FilmAPI;
import com.rw.loadingdialog.LoadingView;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class SwipeCardAdapter extends RecyclerView.Adapter<SwipeCardItem> {

    @Getter
    @Setter
    private int pos;

    @Getter
    @Setter
    private List<FilmAPI> films;

    @Setter
    @Getter
    LoadingView loadingView;

    @Getter
    @Setter
    private Context context;

    @Getter
    @Setter
    private String cinemaName = null;

    @Getter
    @Setter
    private int cinemaId = -1;

    public SwipeCardAdapter(List<FilmAPI> films, Context context, LoadingView loadingView) {
        this.films = films;
        this.context = context;
        this.loadingView = loadingView;
    }

    public SwipeCardAdapter(List<FilmAPI> films, Context context, String cinemaName, int cinemaId, LoadingView loadingView) {
        this.films = films;
        this.context = context;
        this.cinemaName = cinemaName;
        this.cinemaId = cinemaId;
        this.loadingView = loadingView;
    }

    private final SwipeCardClickListener swipeCardClickListener = new SwipeCardClickListener();

    @NonNull
    @Override
    public SwipeCardItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_card_item, parent, false);
        swipeCardClickListener.setContext(parent.getContext());
        view.setOnClickListener(swipeCardClickListener);
        return new SwipeCardItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeCardItem holder, int position) {
        holder.bind(films.get(position).getTitle(), APIClient.HOST + films.get(position).getPicUrl(), context);
        pos = position;
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public void removeTopItem() {
        films.remove(0);
        notifyDataSetChanged();
    }

    class SwipeCardClickListener implements View.OnClickListener {

        Context context;

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, AboutFilmActivity.class);
            intent.putExtra("filmId", films.get(pos).getId());
            if (cinemaId != -1 && cinemaName != null) {
                intent.putExtra("cinemaId", cinemaId);
                intent.putExtra("cinemaName", cinemaName);
            }
            context.startActivity(intent);
        }

    }

}