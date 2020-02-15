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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.cinema.client.R;
import com.cinema.client.activity.MyTicketsActivity;
import com.cinema.client.activity.TicketActivity;
import com.cinema.client.adapters.TicketSearchAdapter;
import com.cinema.client.entities.TicketItemSearch;
import com.cinema.client.etc.MySearchSuggestion;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.TicketAPI;
import com.cinema.client.requests.entities.TokenAPI;
import com.pd.chocobar.ChocoBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TicketSearchFragment extends Fragment {


    @BindView(R.id.myTicketsRecycleView)
    RecyclerView recyclerView;

    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    TicketSearchAdapter myTicketsAdapter;
    ArrayList<TicketItemSearch> myTicketsArrayList;


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
        apiInterface = APIClient.getClient().create(APIInterface.class);

        sharedpreferences = getActivity().getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
        if (sharedpreferences != null) {
//            String token = sharedpreferences.getString("token", null);
            String login = sharedpreferences.getString("login", null);
            String password = sharedpreferences.getString("password", null);
            int userId = sharedpreferences.getInt("userId", -1);

            RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"),
                    password);

            RequestBody login_ = RequestBody.create(MediaType.parse("text/plain"),
                    login);

            Call<TokenAPI> call = apiInterface.refreshToken(login_, password_);

            call.enqueue(new Callback<TokenAPI>() {
                @Override
                public void onResponse(Call<TokenAPI> call, Response<TokenAPI> response) {
                    try {
                        List<TicketAPI> ticketList= apiInterface.getTicketByUserId(userId,response.body().getAccess()).execute().body();

                        Log.d("LIST",ticketList.size()+"");


                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<TokenAPI> call, Throwable t) {

                }
            });


        }
        //


        for (int i = 0; i < 10; i++) {
            TicketItemSearch myTickets = new TicketItemSearch();

            myTickets.setFilmName("Film #" + i);
            myTickets.setFilmDateTime("Date #" + i);
            myTickets.setFilmPlace("Place #" + i);
            myTickets.setFilmCinema("Cinema #" + i);
            myTickets.setFilmImg(R.drawable.once_upon_a_time);

            myTicketsArrayList.add(myTickets);
        }


        myTicketsAdapter = new TicketSearchAdapter(myTicketsArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myTicketsAdapter);


        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                ArrayList<MySearchSuggestion> temp = new ArrayList<>();
                temp.add(new MySearchSuggestion("Ticket #1"));
                temp.add(new MySearchSuggestion("Ticket #2"));
                temp.add(new MySearchSuggestion("Ticket #3"));
                temp.add(new MySearchSuggestion("Ticket #4"));
                temp.add(new MySearchSuggestion("Ticket #5"));

                mSearchView.swapSuggestions(temp);
            }
        });

        //for menu in floating menu view
        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

            }

        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Toast.makeText(getActivity(), searchSuggestion.getBody(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                startActivity(intent);

            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });
    }
}
