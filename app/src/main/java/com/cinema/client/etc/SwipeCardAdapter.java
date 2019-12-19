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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwipeCardAdapter extends RecyclerView.Adapter<SwipeCardItem> {

    private int pos;

    private List<String> items = new ArrayList<String>(Arrays.asList(
            "Lorem #1","Lorem #2","Lorem #3",
            "Lorem #4","Lorem #5","Lorem #6",
            "Lorem #7","Lorem #8","Lorem #9"
    ));


    private final MyOnClickListener mOnClickListener = new MyOnClickListener();

    @NonNull
    @Override
    public SwipeCardItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_card_item, parent, false);
        mOnClickListener.setContext(parent.getContext());
        view.setOnClickListener(mOnClickListener);
        return new SwipeCardItem(view);
        //


//        return new SwipeCardItem(
//                LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_card_item, parent, false));
    }



    @Override
    public void onBindViewHolder(@NonNull SwipeCardItem holder, int position) {
//        holder.bind(items.get(position).intValue());
        holder.bind(items.get(position));
        pos=position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<String> getItems() {
        return items;
    }

    public void removeTopItem() {
        items.remove(0);
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
            context.startActivity(intent);

            Toast.makeText(context, items.get(pos), Toast.LENGTH_SHORT).show();


        }
    }

}