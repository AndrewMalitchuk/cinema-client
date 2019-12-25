package com.cinema.client.etc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cinema.client.R;
import com.cinema.client.activity.TicketActivity;

import java.util.List;

public class MyTicketsAdapter extends RecyclerView.Adapter<MyTicketsAdapter.ViewHolder> {

    List<MyTickets> myTicketsList;
    Context context;

    public MyTicketsAdapter(List<MyTickets>myTicketsList)
    {
        this.myTicketsList = myTicketsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_films_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MyTickets myTickets = myTicketsList.get(position);

        holder.filmNameText.setText(myTickets.getFilmName());
        holder.filmDateTimeText.setText(myTickets.getFilmDateTime());
        holder.filmPlaceText.setText(myTickets.getFilmPlace());
        holder.filmCinemaText.setText(myTickets.getFilmCinema());

        holder.filmImage.setImageResource(myTickets.getFilmImg());



        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"The position is:"+position,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, TicketActivity.class);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return myTicketsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView filmImage;
        TextView filmNameText;
        TextView filmDateTimeText;
        TextView filmPlaceText;
        TextView filmCinemaText;
        CardView cv;

        public ViewHolder(View itemView)
        {

            super(itemView);

            filmImage= (ImageView)itemView.findViewById(R.id.filmImage);;
            filmNameText= (TextView)itemView.findViewById(R.id.cinemaName);
            filmDateTimeText= (TextView)itemView.findViewById(R.id.cinemaAddress);
            filmPlaceText= (TextView)itemView.findViewById(R.id.filmPlace);
            filmCinemaText= (TextView)itemView.findViewById(R.id.filmCinema);

            cv = (CardView)itemView.findViewById(R.id.cv);
        }

    }
}