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

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cinema.client.R;
import com.cinema.client.activity.MainActivity;
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
        String date = myTickets.getFilmDate();
        String time = myTickets.getFilmTime();
        holder.filmDateTimeText.setText(date + " " + time);
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
        Glide.with(context).load(myTickets.getFilmUrl()).into(holder.filmImage);
        holder.cv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TicketActivity.class);
                intent.putExtra("ticketCode", myTicketsList.get(position).getTicketCode());
                intent.putExtra("ticketCode", myTicketsList.get(position).getTicketCode());
                intent.putExtra("timeline_id", myTicketsList.get(position).getTimelineId());
                intent.putExtra("film_id", myTicketsList.get(position).getFilmId());
                intent.putExtra("cinema_id", myTicketsList.get(position).getCinemaId());
                intent.putExtra("datetime", myTicketsList.get(position).getFilmDateTime());
                context.startActivity(intent);
            }

        });
        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.cv);
                popupMenu.getMenuInflater().inflate(R.menu.ticket_context_menu2, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.cancel:
                                if (myTicketsList.get(position).getStatus() == 2) {
                                    positionOfDeleted = position;
                                    dialog = new DroidDialog.Builder(context)
                                            .icon(R.drawable.ic_video_label_black_24dp)
                                            .title("Confirm your action")
                                            .content("Are you really want to delete this ticket?")
                                            .cancelable(true, false)
                                            .positiveButton("Yes, I'm sure", new DroidDialog.onPositiveListener() {

                                                @Override
                                                public void onPositive(Dialog droidDialog) {
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

                                                }

                                            })
                                            .negativeButton("No, thanks", new DroidDialog.onNegativeListener() {

                                                @Override
                                                public void onNegative(Dialog droidDialog) {
                                                    droidDialog.cancel();
                                                }

                                            })
                                            .color(ContextCompat.getColor(context, R.color.colorAccent), ContextCompat.getColor(context, R.color.white),
                                                    ContextCompat.getColor(context, R.color.colorAccent))
                                            .show();
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

    /**
     * Handel for received token
     *
     * @param tokenAPI received token
     */
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
                List<HallAPI> hallAPI;
                if (myTicketsList.get(positionOfDeleted).getFilmPlace().split("-")[0].equals("l")) {
                    hallAPI = gson.fromJson(hall.getHallJson(), new TypeToken<List<HallAPI>>() {
                    }.getType());
                    hallAPI.get(0).getBought().remove(removePlace(hallAPI.get(0).getBought(), getAbsolute(hallAPI.get(0).getCustom(), hallCellAPI)));
                    hallAPI.get(0).getFree().add(getAbsolute(hallAPI.get(0).getCustom(), hallCellAPI));
                    hall.setHallJson(gson.toJson(hallAPI));
                } else if (myTicketsList.get(positionOfDeleted).getFilmPlace().split("-")[0].equals("c")) {
                    hallAPI = gson.fromJson(hall.getHallJson(), new TypeToken<List<HallAPI>>() {
                    }.getType());
                    hallAPI.get(1).getBought().remove(removePlace(hallAPI.get(1).getBought(), getAbsolute(hallAPI.get(1).getCustom(), hallCellAPI)));
                    hallAPI.get(1).getFree().add(getAbsolute(hallAPI.get(1).getCustom(), hallCellAPI));
                    hall.setHallJson(gson.toJson(hallAPI));
                } else if (myTicketsList.get(positionOfDeleted).getFilmPlace().split("-")[0].equals("r")) {
                    hallAPI = gson.fromJson(hall.getHallJson(), new TypeToken<List<HallAPI>>() {
                    }.getType());
                    hallAPI.get(2).getBought().remove(removePlace(hallAPI.get(2).getBought(), getAbsolute(hallAPI.get(2).getCustom(), hallCellAPI)));
                    hallAPI.get(2).getFree().add(getAbsolute(hallAPI.get(2).getCustom(), hallCellAPI));
                    hall.setHallJson(gson.toJson(hallAPI));
                }
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
                            dialog.dialog.cancel();
                            context.startActivity(new Intent(context, MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<AllHallAPI> call, Throwable t) {

                    }

                });

            }

            @Override
            public void onFailure(Call<AllHallAPI> call, Throwable t) {

            }

        });

    }

    /**
     * Remove place/seat from list of available
     *
     * @param list  available list
     * @param place place to remove
     * @return -1 if operation is unsuccessfula
     */
    public int removePlace(List<HallCellAPI> list, HallCellAPI place) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCol() == place.getCol() && list.get(i).getRow() == place.getRow()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Get absolute address of seat instead of relative
     *
     * @param list  available list
     * @param place place to search with relative address
     * @return absolute address
     */
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

        TextView filmCinemaText;

        CardView cv;

        TagGroup status;

        public ViewHolder(View itemView) {
            super(itemView);
            filmImage = (ImageView) itemView.findViewById(R.id.filmImage);
            filmNameText = (TextView) itemView.findViewById(R.id.cinemaName);
            filmDateTimeText = (TextView) itemView.findViewById(R.id.cinemaAddress);
            filmCinemaText = (TextView) itemView.findViewById(R.id.filmCinema);
            status = itemView.findViewById(R.id.status);
            cv = (CardView) itemView.findViewById(R.id.cv);
        }

    }

}