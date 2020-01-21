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
import com.cinema.client.adapters.CinemaSearchAdapter;
import com.cinema.client.entities.CinemaItemSearch;
import com.cinema.client.etc.MySearchSuggestion;

import java.util.ArrayList;

public class SearchCinemaActivity extends AppCompatActivity {

    Toolbar toolbar;


    RecyclerView recyclerView;
    CinemaSearchAdapter cinemaSearchAdapter;
    ArrayList<CinemaItemSearch> cinemaItemSearchList;

    FloatingSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cinema);

        toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("Cinemas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            }
        });


        cinemaItemSearchList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            CinemaItemSearch cinemaItemSearch = new CinemaItemSearch();
            cinemaItemSearch.setCinemaName("Cinema #"+i);
            cinemaItemSearch.setCinemaAddress("Address #"+i);
            cinemaItemSearch.setCinemaImg(R.drawable.kosmos_cinema);

            cinemaItemSearchList.add(cinemaItemSearch);
        }


        cinemaSearchAdapter = new CinemaSearchAdapter(cinemaItemSearchList);

        recyclerView = (RecyclerView) findViewById(R.id.myTicketsRecycleView);
        mSearchView = findViewById(R.id.floating_search_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cinemaSearchAdapter);


        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                ArrayList<MySearchSuggestion> temp=new ArrayList<>();
                temp.add(new MySearchSuggestion("Cinema #1"));
                temp.add(new MySearchSuggestion("Cinema #2"));
                temp.add(new MySearchSuggestion("Cinema #3"));
                temp.add(new MySearchSuggestion("Cinema #4"));
                temp.add(new MySearchSuggestion("Cinema #5"));

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
                Intent intent = new Intent(SearchCinemaActivity.this,AboutCinemaActivity.class);
                startActivity(intent);
            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });

    }
}