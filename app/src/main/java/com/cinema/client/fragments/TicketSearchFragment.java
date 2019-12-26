package com.cinema.client.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TicketSearchFragment extends Fragment {


    @BindView(R.id.myTicketsRecycleView)
    RecyclerView recyclerView;

    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    TicketSearchAdapter myTicketsAdapter;
    ArrayList<TicketItemSearch> myTicketsArrayList;


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
