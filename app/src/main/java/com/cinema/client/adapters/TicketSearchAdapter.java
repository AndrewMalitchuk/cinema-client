package com.cinema.client.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cinema.client.R;
import com.cinema.client.activity.NewNewCardActivity;
import com.cinema.client.activity.SearchCinemaActivity;
import com.cinema.client.activity.TicketActivity;
import com.cinema.client.entities.TicketItemSearch;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.TicketAPI;
import com.cinema.client.requests.entities.TokenAPI;
import com.droidbyme.dialoglib.DroidDialog;
import com.pd.chocobar.ChocoBar;
import com.skyhope.materialtagview.TagView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.gujun.android.taggroup.TagGroup;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketSearchAdapter extends RecyclerView.Adapter<TicketSearchAdapter.ViewHolder> {

    List<TicketItemSearch> myTicketsList;
    Context context;

    private APIInterface apiInterface;

    public static final String ACCOUNT_PREF = "accountPref";
    private SharedPreferences sharedpreferences;


    int positionOfDeleted;

    public TicketSearchAdapter(List<TicketItemSearch> myTicketsList) {
        this.myTicketsList = myTicketsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_search_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TicketItemSearch myTickets = myTicketsList.get(position);

        holder.filmNameText.setText(myTickets.getFilmName());
        //
        String date=myTickets.getFilmDateTime().split("T")[0];
        String time=myTickets.getFilmDateTime().split("T")[0].split("/+")[0];
        holder.filmDateTimeText.setText(date+" "+time);

        //


//        holder.filmDateTimeText.setText(myTickets.getFilmDateTime());
//        holder.filmPlaceText.setText(myTickets.getFilmPlace());
        holder.filmCinemaText.setText(myTickets.getFilmCinema());

        switch (myTickets.getStatus()) {
            case 1:
                // Returned

                holder.status.setTags("Returned");

                break;
            case 2:
                // Active

                holder.status.setTags("Active");

                break;
            case 3:
                // Canceled

                holder.status.setTags("Canceled");

                break;
        }

//        holder.filmImage.setImageResource(myTickets.getFilmImg());
        Glide.with(context).load(myTickets.getFilmUrl()).into(holder.filmImage);


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "The position is:" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, TicketActivity.class);
                intent.putExtra("ticketCode", myTicketsList.get(position).getTicketCode());
                context.startActivity(intent);

            }
        });

        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Toast.makeText(context, "CNTX: The position is:" + position, Toast.LENGTH_SHORT).show();

                PopupMenu popupMenu = new PopupMenu(context, holder.cv);
                popupMenu.getMenuInflater().inflate(R.menu.ticket_context_menu2, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.cancel:
                                Toast.makeText(context, "Delete #" + position, Toast.LENGTH_SHORT).show();

                                positionOfDeleted = position;


                                DroidDialog dialog = new DroidDialog.Builder(context)
                                        .icon(R.drawable.ic_video_label_black_24dp)
                                        .title("Confirm your action")
                                        .content("Are you really want to delete this ticket?")
                                        .cancelable(true, false)
                                        .positiveButton("Yes, I'm sure", new DroidDialog.onPositiveListener() {
                                            @Override
                                            public void onPositive(Dialog droidDialog) {
                                                Toast.makeText(context, "Yes, I'm sure", Toast.LENGTH_SHORT).show();
//                                                droidDialog.cancel();
                                                //

                                                sharedpreferences = context.getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
                                                if (sharedpreferences != null) {
                                                    String login = sharedpreferences.getString("login", null);
                                                    String password = sharedpreferences.getString("password", null);

                                                    RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"),
                                                            password);

                                                    RequestBody login_ = RequestBody.create(MediaType.parse("text/plain"),
                                                            login);

                                                    apiInterface = APIClient.getClient().create(APIInterface.class);
                                                    Observable<TokenAPI> tokenRx = apiInterface.refreshTokenRx(login_, password_);

                                                    tokenRx.subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .map(result -> result)
                                                            .subscribe(TicketSearchAdapter.this::onToken);


                                                }


                                                //
                                            }


                                        })
                                        .negativeButton("No, thanks", new DroidDialog.onNegativeListener() {
                                            @Override
                                            public void onNegative(Dialog droidDialog) {
                                                Toast.makeText(context, "No, thanks", Toast.LENGTH_SHORT).show();
                                                //
                                                droidDialog.cancel();
                                                //
                                            }
                                        })
                                        .color(ContextCompat.getColor(context, R.color.colorAccent), ContextCompat.getColor(context, R.color.white),
                                                ContextCompat.getColor(context, R.color.colorAccent))
                                        .show();


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

    private void onToken(TokenAPI tokenAPI) {

        RequestBody place_ = RequestBody.create(MediaType.parse("text/plain"),
                myTicketsList.get(positionOfDeleted).getFilmPlace());
        RequestBody code_ = RequestBody.create(MediaType.parse("text/plain"),
                myTicketsList.get(positionOfDeleted).getTicketCode());
        RequestBody status_ = RequestBody.create(MediaType.parse("text/plain"),
                3 + "");
        RequestBody cinema_id_ = RequestBody.create(MediaType.parse("text/plain"),
                myTicketsList.get(positionOfDeleted).getCinemaId() + "");
        RequestBody film_id_ = RequestBody.create(MediaType.parse("text/plain"),
                myTicketsList.get(positionOfDeleted).getFilmId() + "");
        RequestBody user_ = RequestBody.create(MediaType.parse("text/plain"),
                myTicketsList.get(positionOfDeleted).getUserId() + "");
        RequestBody date_ = RequestBody.create(MediaType.parse("text/plain"),
                myTicketsList.get(positionOfDeleted).getFilmDateTime());


        Call<TicketAPI> call = apiInterface.updateTicket(
                myTicketsList.get(positionOfDeleted).getTicketId(),place_, code_, status_, cinema_id_, film_id_, user_, date_, "Bearer " + tokenAPI.getAccess()
        );

        call.enqueue(new Callback<TicketAPI>() {
            @Override
            public void onResponse(Call<TicketAPI> call, Response<TicketAPI> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TicketAPI> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return myTicketsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView filmImage;
        TextView filmNameText;
        TextView filmDateTimeText;
        TextView filmPlaceText;
        TextView filmCinemaText;
        CardView cv;

        TagGroup status;

        public ViewHolder(View itemView) {

            super(itemView);

            filmImage = (ImageView) itemView.findViewById(R.id.filmImage);

            filmNameText = (TextView) itemView.findViewById(R.id.cinemaName);
            filmDateTimeText = (TextView) itemView.findViewById(R.id.cinemaAddress);
//            filmPlaceText = (TextView) itemView.findViewById(R.id.filmPlace);
            filmCinemaText = (TextView) itemView.findViewById(R.id.filmCinema);
            //
            status = itemView.findViewById(R.id.status);
            //

            cv = (CardView) itemView.findViewById(R.id.cv);
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