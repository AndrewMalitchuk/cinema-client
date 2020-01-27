package com.cinema.client.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cinema.client.R;
import com.cinema.client.activity.AboutCinemaActivity;
import com.cinema.client.activity.Main3Activity;
import com.cinema.client.activity.TicketActivity;
import com.cinema.client.entities.CinemaItemSearch;
import com.cinema.client.entities.TicketItemSearch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liangfeizc.avatarview.AvatarView;
import com.pd.chocobar.ChocoBar;

import java.util.ArrayList;
import java.util.List;

public class FavouriteCinemasAdapter extends RecyclerView.Adapter<FavouriteCinemasAdapter.ViewHolder> {


    public static final String FAVOURITE_CINEMAS_PREF="favourite_cinema_pref";
    private SharedPreferences sharedpreferences;

    List<CinemaItemSearch> favCinemasList;
    Context context;

    public FavouriteCinemasAdapter(List<CinemaItemSearch>favCinemasList)
    {
        this.favCinemasList = favCinemasList;

    }

    @Override
    public FavouriteCinemasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_cinema_item,parent,false);
        FavouriteCinemasAdapter.ViewHolder viewHolder = new FavouriteCinemasAdapter.ViewHolder(view);
        context = parent.getContext();
        sharedpreferences = context.getSharedPreferences(FAVOURITE_CINEMAS_PREF, Context.MODE_PRIVATE);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavouriteCinemasAdapter.ViewHolder holder, final int position) {
        CinemaItemSearch favCinema=favCinemasList.get(position);
        holder.favCinemaNameTextView.setText(favCinema.getCinemaName());
        Glide.with(context).load(favCinema.getCinemaImg()).into(holder.favCinemaAvatarView);


        holder.favCinemaLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AboutCinemaActivity.class);
                intent.putExtra("cinemaId", favCinema.getCinemaId());
                context.startActivity(intent);

            }
        });

        holder.favCinemaLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(context,"CNTX: The position is:"+position,Toast.LENGTH_SHORT).show();

                PopupMenu popupMenu=new PopupMenu(context,holder.favCinemaLinearLayout);
                popupMenu.getMenuInflater().inflate(R.menu.ticket_context_menu1,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.delete:
                                Toast.makeText(context, "Delete #"+position, Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                String json=sharedpreferences.getString("fav_json",null);

                                Gson gson=new GsonBuilder().create();

                                List<Integer> list;
                                if(json==null){
                                    list=new ArrayList<>();
                                }else {
                                    list = gson.fromJson(json, new TypeToken<List<Integer>>() {
                                    }.getType());
                                }

                                list.remove(new Integer(favCinemasList.get(position).getCinemaId()));

                                editor.remove("fav_json");
                                editor.putString("fav_json",gson.toJson(list));
                                editor.commit();

                                // Если не заработает
                                context.startActivity(new Intent(context,Main3Activity.class));
//                                notifyDataSetChanged();

                                //



                                return true;
                        }
                        return true;
                    }

                });


                popupMenu.show();
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return favCinemasList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout favCinemaLinearLayout;
        AvatarView favCinemaAvatarView;
        TextView favCinemaNameTextView;


        public ViewHolder(View itemView)
        {

            super(itemView);
            favCinemaLinearLayout=itemView.findViewById(R.id.favCinemaLinearLayout);
            favCinemaAvatarView=itemView.findViewById(R.id.favCinemaAvatarView);
            favCinemaNameTextView=itemView.findViewById(R.id.favCinemaNameTextView);


        }

    }

}
