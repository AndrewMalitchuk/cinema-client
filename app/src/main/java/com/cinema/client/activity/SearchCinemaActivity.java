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
import com.cinema.client.etc.MyTickets;
import com.cinema.client.etc.MyTicketsAdapter;

import java.util.ArrayList;

public class SearchCinemaActivity extends AppCompatActivity {

    Toolbar toolbar;


    RecyclerView recyclerView;
    MyTicketsAdapter myTicketsAdapter;
    ArrayList<MyTickets> myTicketsArrayList;

    FloatingSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cinema);

        toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("Hello, $username!");
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
            MyTickets myTickets = new MyTickets();

            myTickets.setFilmName("Film #" + i);
            myTickets.setFilmDateTime("Date #" + i);
            myTickets.setFilmPlace("Place #" + i);
            myTickets.setFilmCinema("Cinema #" + i);
            myTickets.setFilmImg(R.drawable.once_upon_a_time);

            myTicketsArrayList.add(myTickets);
        }


        myTicketsAdapter = new MyTicketsAdapter(myTicketsArrayList);

        recyclerView = (RecyclerView) findViewById(R.id.myTicketsRecycleView);
        mSearchView = findViewById(R.id.floating_search_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myTicketsAdapter);


        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                ArrayList<MySearchSuggestion> temp=new ArrayList<>();
                temp.add(new MySearchSuggestion("kek #1"));
                temp.add(new MySearchSuggestion("kek #2"));
                temp.add(new MySearchSuggestion("kek #3"));
                temp.add(new MySearchSuggestion("kek #4"));
                temp.add(new MySearchSuggestion("kek #5"));

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
                Toast.makeText(SearchCinemaActivity.this, searchSuggestion.getBody(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });

    }
}