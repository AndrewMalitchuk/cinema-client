package com.cinema.client.etc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cinema.client.R;
import com.cinema.client.activity.AboutFilmActivity;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.entities.FilmAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class SwipeCardAdapter extends RecyclerView.Adapter<SwipeCardItem> {

    private int pos;

    @Getter
    @Setter
    private List<FilmAPI> films;


    @Getter
    @Setter
    private Context context;

//    private List<String> items = new ArrayList<String>(Arrays.asList(
//            "Lorem #1","Lorem #2","Lorem #3",
//            "Lorem #4","Lorem #5","Lorem #6",
//            "Lorem #7","Lorem #8","Lorem #9"
//    ));


    private String cinemaName=null;
    private int cinemaId=-1;


    public SwipeCardAdapter(List<FilmAPI> films, Context context){
        this.films=films;
        this.context=context;
    }

    public SwipeCardAdapter(List<FilmAPI> films, Context context,String cinemaName,int cinemaId){
        this.films=films;
        this.context=context;
        this.cinemaName=cinemaName;
        this.cinemaId=cinemaId;
    }


    private final MyOnClickListener mOnClickListener = new MyOnClickListener();

    @NonNull
    @Override
    public SwipeCardItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_card_item, parent, false);
        mOnClickListener.setContext(parent.getContext());
        view.setOnClickListener(mOnClickListener);
        return new SwipeCardItem(view);
        //`

    }



    @Override
    public void onBindViewHolder(@NonNull SwipeCardItem holder, int position) {

//        holder.bind(items.get(position));
        holder.bind(films.get(position).getTitle(), APIClient.HOST+films.get(position).getPicUrl(),context);
        pos=position;
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public List<FilmAPI> getItems() {
        return films;
    }

    public void removeTopItem() {
        films.remove(0);
        notifyDataSetChanged();
    }


    class MyOnClickListener implements View.OnClickListener {

        Context context;

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View view) {


            Intent intent=new Intent(context, AboutFilmActivity.class);
            intent.putExtra("filmId",films.get(pos).getId());
            //

            if (cinemaId != -1 && cinemaName != null) {
                intent.putExtra("cinemaId",cinemaId);
                intent.putExtra("cinemaName",cinemaName);
            }

            //


            context.startActivity(intent);

            Toast.makeText(context, films.get(pos).getTitle(), Toast.LENGTH_SHORT).show();


        }
    }

}