package com.cinema.client.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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
import com.cinema.client.activity.Main3Activity;
import com.cinema.client.activity.TicketActivity;
import com.cinema.client.entities.TicketItemSearch;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.AllHallAPI;
import com.cinema.client.requests.entities.HallAPI;
import com.cinema.client.requests.entities.HallCellAPI;
import com.cinema.client.requests.entities.HallCellCustomAPI;
import com.cinema.client.requests.entities.TicketAPI;
import com.cinema.client.requests.entities.TokenAPI;
import com.droidbyme.dialoglib.DroidDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    DroidDialog dialog;


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
        String date = myTickets.getFilmDate();
        String time = myTickets.getFilmTime();
        holder.filmDateTimeText.setText(date + " " + time);

        //


//        holder.filmDateTimeText.setText(myTickets.getFilmDateTime());
//        holder.filmPlaceText.setText(myTickets.getFilmPlace());
        holder.filmCinemaText.setText(myTickets.getFilmCinema());

        switch (myTickets.getStatus()) {
            case 1:
                // Returned

                holder.status.setTags("Returned", myTicketsList.get(position).getFilmPlace());

                break;
            case 2:
                // Active

                holder.status.setTags("Active", myTicketsList.get(position).getFilmPlace());

                break;
            case 3:
                // Canceled

                holder.status.setTags("Canceled", myTicketsList.get(position).getFilmPlace());

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
                //
                intent.putExtra("ticketCode", myTicketsList.get(position).getTicketCode());
                intent.putExtra("timeline_id", myTicketsList.get(position).getTimelineId());
                intent.putExtra("film_id", myTicketsList.get(position).getFilmId());
                intent.putExtra("cinema_id", myTicketsList.get(position).getCinemaId());
                intent.putExtra("datetime", myTicketsList.get(position).getFilmDateTime());
                //
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

                                if (myTicketsList.get(position).getStatus() ==2) {

                                    positionOfDeleted = position;


                                    dialog = new DroidDialog.Builder(context)
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

                                                        // update ticket
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
                                                        //


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
                                }else{

                                }


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
        RequestBody timeline_id_ = RequestBody.create(MediaType.parse("text/plain"),
                myTicketsList.get(positionOfDeleted).getTimelineId() + "");
        RequestBody user_ = RequestBody.create(MediaType.parse("text/plain"),
                myTicketsList.get(positionOfDeleted).getUserId() + "");


        Call<TicketAPI> call = apiInterface.updateTicket(
                myTicketsList.get(positionOfDeleted).getTicketId(),
                place_,
                code_,
                status_,
                timeline_id_,
                user_,
                "Bearer " + tokenAPI.getAccess()
        );

        call.enqueue(new Callback<TicketAPI>() {
            @Override
            public void onResponse(Call<TicketAPI> call, Response<TicketAPI> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TicketAPI> call, Throwable t) {

            }
        });


        Call<AllHallAPI> callPrevHall = apiInterface.getHallById(myTicketsList.get(positionOfDeleted).getHallId());
        callPrevHall.enqueue(new Callback<AllHallAPI>() {
            @Override
            public void onResponse(Call<AllHallAPI> call, Response<AllHallAPI> response) {
                AllHallAPI hall = response.body();

                HallCellAPI hallCellAPI = new HallCellAPI();

                hallCellAPI.setRow(Integer.valueOf(myTicketsList.get(positionOfDeleted).getFilmPlace().split("-")[1]));
                hallCellAPI.setCol(Integer.valueOf(myTicketsList.get(positionOfDeleted).getFilmPlace().split("-")[2]));

                Gson gson = new Gson().newBuilder().create();

                Log.d("hallCellAPI", hallCellAPI.toString());


                List<HallAPI> hallAPI;

                //

                if (myTicketsList.get(positionOfDeleted).getFilmPlace().split("-")[0].equals("l")) {
//                    hallCellAPI.setSector("left");

                    hallAPI = gson.fromJson(hall.getHallJson(), new TypeToken<List<HallAPI>>() {

                    }.getType());


                    Log.d("ABSOLUTE", getAbsolute(hallAPI.get(0).getCustom(), hallCellAPI).toString());

                    Log.d("hall 0", removePlace(hallAPI.get(0).getBought(), getAbsolute(hallAPI.get(0).getCustom(), hallCellAPI)) + "");


                    hallAPI.get(0).getBought().remove(removePlace(hallAPI.get(0).getBought(), getAbsolute(hallAPI.get(0).getCustom(), hallCellAPI)));

                    hallAPI.get(0).getFree().add(getAbsolute(hallAPI.get(0).getCustom(), hallCellAPI));


                    hall.setHallJson(gson.toJson(hallAPI));

                    Log.d("NEW JSON", hall.getHallJson());

                } else if (myTicketsList.get(positionOfDeleted).getFilmPlace().split("-")[0].equals("c")) {
//                    hallCellAPI.setSector("center");

                    hallAPI = gson.fromJson(hall.getHallJson(), new TypeToken<List<HallAPI>>() {

                    }.getType());


                    Log.d("ABSOLUTE", getAbsolute(hallAPI.get(1).getCustom(), hallCellAPI).toString());

                    Log.d("hall 1", removePlace(hallAPI.get(1).getBought(), getAbsolute(hallAPI.get(1).getCustom(), hallCellAPI)) + "");


                    hallAPI.get(1).getBought().remove(removePlace(hallAPI.get(1).getBought(), getAbsolute(hallAPI.get(1).getCustom(), hallCellAPI)));

                    hallAPI.get(1).getFree().add(getAbsolute(hallAPI.get(1).getCustom(), hallCellAPI));


                    hall.setHallJson(gson.toJson(hallAPI));

                    Log.d("NEW JSON", hall.getHallJson());

                } else if (myTicketsList.get(positionOfDeleted).getFilmPlace().split("-")[0].equals("r")) {
//                    hallCellAPI.setSector("right");

                    hallAPI = gson.fromJson(hall.getHallJson(), new TypeToken<List<HallAPI>>() {

                    }.getType());


                    Log.d("ABSOLUTE", getAbsolute(hallAPI.get(2).getCustom(), hallCellAPI).toString());

                    Log.d("hall 2", removePlace(hallAPI.get(2).getBought(), getAbsolute(hallAPI.get(2).getCustom(), hallCellAPI)) + "");


                    hallAPI.get(2).getBought().remove(removePlace(hallAPI.get(2).getBought(), getAbsolute(hallAPI.get(2).getCustom(), hallCellAPI)));

                    hallAPI.get(2).getFree().add(getAbsolute(hallAPI.get(2).getCustom(), hallCellAPI));


                    hall.setHallJson(gson.toJson(hallAPI));

                    Log.d("NEW JSON", hall.getHallJson());

                }

                Log.d("OUT", hall.getHallJson());


                //
                RequestBody name_ = RequestBody.create(MediaType.parse("text/plain"),
                        hall.getName());

                RequestBody hall_json_ = RequestBody.create(MediaType.parse("text/plain"),
                        hall.getHallJson());

                RequestBody cinema_id_ = RequestBody.create(MediaType.parse("text/plain"),
                        hall.getCinemaId() + "");


                Call<AllHallAPI> callUpdate = apiInterface.updateHallByHallId(
                        hall.getId(),
                        name_,
                        hall_json_,
                        cinema_id_,
                        "Bearer " + tokenAPI.getAccess()

                );

                callUpdate.enqueue(new Callback<AllHallAPI>() {
                    @Override
                    public void onResponse(Call<AllHallAPI> call, Response<AllHallAPI> response) {
                        if (response.isSuccessful()) {
                            Log.d("!!!", "Yeah");


                            dialog.dialog.cancel();
                            context.startActivity(new Intent(context, Main3Activity.class));

                        }
                    }

                    @Override
                    public void onFailure(Call<AllHallAPI> call, Throwable t) {

                    }
                });
                //


            }

            @Override
            public void onFailure(Call<AllHallAPI> call, Throwable t) {

            }
        });

    }


    public int removePlace(List<HallCellAPI> list, HallCellAPI place) {

//        for(HallCellAPI hallCellAPI:list){
        for (int i = 0; i < list.size(); i++) {

            Log.d("removePlace", list.get(i).toString());

            if (list.get(i).getCol() == place.getCol() && list.get(i).getRow() == place.getRow()) {
//                list.remove(hallCellAPI);
                Log.d("removePlace", i + "");
                return i;
            }
        }
        return -1;

    }

    public HallCellAPI getAbsolute(List<HallCellCustomAPI> list, HallCellAPI place) {

        for (HallCellCustomAPI hallCellCustomAPI : list) {
            if (hallCellCustomAPI.getNewCol() == place.getCol() && hallCellCustomAPI.getNewRow() == place.getRow()) {
                HallCellAPI hallCellAPI = new HallCellAPI();
                hallCellAPI.setRow(hallCellCustomAPI.getOldRow());
                hallCellAPI.setCol(hallCellCustomAPI.getOldCol());
                return hallCellAPI;
            }
        }
        return place;

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

}