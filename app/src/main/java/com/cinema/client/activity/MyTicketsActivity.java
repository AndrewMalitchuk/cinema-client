package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.cinema.client.R;
import com.cinema.client.etc.MySearchSuggestion;
import com.cinema.client.entities.TicketItemSearch;
import com.cinema.client.adapters.TicketSearchAdapter;

import java.util.ArrayList;

public class MyTicketsActivity extends AppCompatActivity {

    Toolbar toolbar;

    RecyclerView recyclerView;
    TicketSearchAdapter myTicketsAdapter;
    ArrayList<TicketItemSearch> myTicketsArrayList;

    FloatingSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);

        toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("Tickets");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            }
        });

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

        recyclerView = (RecyclerView) findViewById(R.id.myTicketsRecycleView);
        mSearchView = findViewById(R.id.floating_search_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myTicketsAdapter);


        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                ArrayList<MySearchSuggestion> temp=new ArrayList<>();
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
                Toast.makeText(MyTicketsActivity.this, searchSuggestion.getBody(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MyTicketsActivity.this, TicketActivity.class);
                startActivity(intent);

            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });

    }
}