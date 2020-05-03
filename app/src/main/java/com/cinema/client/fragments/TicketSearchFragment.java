package com.cinema.client.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.cinema.client.R;
import com.cinema.client.activity.TicketActivity;
import com.cinema.client.adapters.TicketSearchAdapter;
import com.cinema.client.entities.TicketItemSearch;
import com.cinema.client.etc.MyItem;
import com.cinema.client.etc.MySearchSuggestion;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.CinemaAPI;
import com.cinema.client.requests.entities.FilmAPI;
import com.cinema.client.requests.entities.TicketAPI;
import com.cinema.client.requests.entities.TimelineAPI;
import com.cinema.client.requests.entities.TokenAPI;
import com.pd.chocobar.ChocoBar;
import com.rw.loadingdialog.LoadingView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;


public class TicketSearchFragment extends Fragment {


    @BindView(R.id.myTicketsRecycleView)
    RecyclerView recyclerView;

    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    @BindView(R.id.frame)
//    SwipeRefreshLayout frame;
    FrameLayout frame;

    TicketSearchAdapter myTicketsAdapter;
    ArrayList<TicketItemSearch> myTicketsArrayList;

    List<TicketAPI> ticketList;

    LoadingView loadingView;


    private APIInterface apiInterface;

    public static final String ACCOUNT_PREF = "accountPref";
    private SharedPreferences sharedpreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ticket_search, container, false);
        ButterKnife.bind(this, view);
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        myTicketsArrayList = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //

        loadingView = new LoadingView.Builder(getContext())
                .setProgressColorResource(R.color.colorAccent)
                .setBackgroundColorRes(R.color.white)
                .setProgressStyle(LoadingView.ProgressStyle.CYCLIC)
                .attachTo(frame);

        loadingView.show();

        //


        //
        apiInterface = APIClient.getClient().create(APIInterface.class);

        sharedpreferences = getActivity().getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
        if (sharedpreferences != null) {
            String login = sharedpreferences.getString("login", null);
            String password = sharedpreferences.getString("password", null);
            int userId = sharedpreferences.getInt("userId", -1);


            ticketList = new ArrayList<>();

            RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"),
                    password);

            RequestBody login_ = RequestBody.create(MediaType.parse("text/plain"),
                    login);

            Observable<TokenAPI> tokenRx = apiInterface.refreshTokenRx(login_, password_);

            tokenRx.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(result -> result)
                    .subscribe(this::onToken);


        }
    }


    public void onToken(TokenAPI token) {
        int userId = sharedpreferences.getInt("userId", -1);
        Observable<List<TicketAPI>> listObservable = apiInterface.getTicketByUserIdRx(userId, "Bearer " + token.getAccess());
        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .doOnComplete(this::loadFinish)
                .subscribe(this::onTicketSuccess);

    }

    private void loadFinish() {

        ChocoBar.builder().setActivity(getActivity())
                .setText("Success!")
                .setDuration(ChocoBar.LENGTH_SHORT)
                .build()
                .show();

        loadingView.hide();

    }



    public void onTicketSuccess(List<TicketAPI> list) {





        ticketList=list;





        for (TicketAPI ticketAPI : list) {


            Call<TimelineAPI> callTimeline = apiInterface.getTimelineById(ticketAPI.getTimeline_id().intValue());


            try {
                TimelineAPI timelineAPI=callTimeline.execute().body();

                TicketItemSearch myTickets = new TicketItemSearch();

                Call<FilmAPI> filmAPICall = apiInterface.getFilmById(timelineAPI.getFilmId());
                FilmAPI filmAPI = null;
                Call<CinemaAPI> cinemaAPICall = apiInterface.getCinemaById(timelineAPI.getCinemaId());
                CinemaAPI cinemaAPI = null;
                try {
                    filmAPI = filmAPICall.execute().body();
                    cinemaAPI = cinemaAPICall.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //
                myTickets.setTicketId(ticketAPI.getId());
                myTickets.setFilmId(filmAPI.getId());
                myTickets.setFilmName(filmAPI.getTitle());
                myTickets.setCinemaId(timelineAPI.getCinemaId());
                myTickets.setUserId(ticketAPI.getUser());

                myTickets.setFilmTime(timelineAPI.getTime());
                myTickets.setFilmDate(timelineAPI.getDate());
                myTickets.setHallId(timelineAPI.getHallId());

                myTickets.setFilmPlace(ticketAPI.getPlace());
                myTickets.setFilmCinema(cinemaAPI.getName());
                myTickets.setFilmUrl(APIClient.HOST + filmAPI.getPicUrl());
                myTickets.setTicketCode(ticketAPI.getCode());
                myTickets.setStatus(ticketAPI.getStatus());
                myTickets.setTimelineId(timelineAPI.getId());
                //

                myTicketsArrayList.add(myTickets);

            } catch (IOException e) {
                e.printStackTrace();
            }




        }

        Collections.sort(myTicketsArrayList, new Comparator<TicketItemSearch>() {
            @Override
            public int compare(TicketItemSearch u1, TicketItemSearch u2) {
                return u1.getFilmDate().compareTo(u2.getFilmDate());
            }
        });



        myTicketsAdapter = new TicketSearchAdapter(myTicketsArrayList);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.myTicketsRecycleView);
        mSearchView = getActivity().findViewById(R.id.floating_search_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myTicketsAdapter);


        ArrayList<MySearchSuggestion> temp = new ArrayList<>();


        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {


                mSearchView.swapSuggestions(searchInLoadedData(newQuery));
            }
        });


        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                String suggestion = searchSuggestion.getBody();

                for (TicketItemSearch ticketItemSearch : myTicketsArrayList) {
                    if (suggestion.equals(ticketItemSearch.getFilmName())) {
                        Intent intent = new Intent(getContext(), TicketActivity.class);
                        intent.putExtra("ticketCode", ticketItemSearch.getTicketCode());
                        intent.putExtra("timeline_id",ticketItemSearch.getTimelineId());
                        intent.putExtra("film_id",ticketItemSearch.getFilmId());
                        intent.putExtra("cinema_id",ticketItemSearch.getCinemaId());
//                        intent.putExtra("datetime",ticketItemSearch.getFilmDateTime());
//                        intent.putExtra("datetime",ticketItemSearch.getFilmDateTime());

                        getContext().startActivity(intent);
                    }
                }

            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });

    }


    public void render() {

    }

    public ArrayList<MySearchSuggestion> searchInLoadedData(String value) {

        ArrayList<MySearchSuggestion> res = new ArrayList<>();

        for (TicketItemSearch myTicketsItem : myTicketsArrayList) {
            if (Pattern.compile(Pattern.quote(value), Pattern.CASE_INSENSITIVE).matcher(myTicketsItem.getFilmName()).find()) {
                res.add(new MySearchSuggestion(myTicketsItem.getFilmName()));
            }
        }

        return res;

    }



}
