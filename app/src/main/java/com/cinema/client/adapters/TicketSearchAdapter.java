package com.cinema.client.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cinema.client.R;
import com.cinema.client.activity.TicketActivity;
import com.cinema.client.entities.TicketItemSearch;

import java.util.List;

public class TicketSearchAdapter extends RecyclerView.Adapter<TicketSearchAdapter.ViewHolder> {

    List<TicketItemSearch> myTicketsList;
    Context context;

    public TicketSearchAdapter(List<TicketItemSearch>myTicketsList)
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
        TicketItemSearch myTickets = myTicketsList.get(position);

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

        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(context,"CNTX: The position is:"+position,Toast.LENGTH_SHORT).show();

                PopupMenu popupMenu=new PopupMenu(context,holder.cv);
                popupMenu.getMenuInflater().inflate(R.menu.ticket_context_menu1,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.delete:
                                Toast.makeText(context, "Delete #"+position, Toast.LENGTH_SHORT).show();
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
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener,
//        MenuItem.OnMenuItemClickListener
//    {
//        ImageView filmImage;
//        TextView filmNameText;
//        TextView filmDateTimeText;
//        TextView filmPlaceText;
//        TextView filmCinemaText;
//        CardView cv;
//
//        public ViewHolder(View itemView)
//        {
//
//            super(itemView);
//
//            filmImage= (ImageView)itemView.findViewById(R.id.filmImage);;
//            filmNameText= (TextView)itemView.findViewById(R.id.cinemaName);
//            filmDateTimeText= (TextView)itemView.findViewById(R.id.cinemaAddress);
//            filmPlaceText= (TextView)itemView.findViewById(R.id.filmPlace);
//            filmCinemaText= (TextView)itemView.findViewById(R.id.filmCinema);
//
//            cv = (CardView)itemView.findViewById(R.id.cv);
//
//            //
//            itemView.setOnClickListener(this);
//            itemView.setOnCreateContextMenuListener(this);
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            return true;
//        }
//
//        @Override
//        public void onClick(View view) {
//
//        }
//
//        @Override
//        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//            MenuItem myActionItem = contextMenu.add("Some menu item");
//            myActionItem.setOnMenuItemClickListener(this);
//        }
//    }
}