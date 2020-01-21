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
import com.cinema.client.adapters.FilmSearchAdapter;
import com.cinema.client.entities.FilmItemSearch;
import com.cinema.client.etc.MySearchSuggestion;

import java.util.ArrayList;

public class SearchFilmActivity extends AppCompatActivity {

    Toolbar toolbar;


    RecyclerView recyclerView;
    FilmSearchAdapter filmSearchAdapter;
    ArrayList<FilmItemSearch> filmItemSearchList;

    FloatingSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_film);

        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("Films");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            }
        });


        filmItemSearchList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            FilmItemSearch filmItemSearch = new FilmItemSearch();

            filmItemSearch.setFilmName("Film #" + i);
            filmItemSearch.setFilmDateTime("Date #" + i);
            filmItemSearch.setFilmPlace("Place #" + i);
            filmItemSearch.setFilmCinema("Cinema #" + i);
            filmItemSearch.setFilmImg(R.drawable.once_upon_a_time);

            filmItemSearchList.add(filmItemSearch);
        }


        filmSearchAdapter = new FilmSearchAdapter(filmItemSearchList);

        recyclerView = (RecyclerView) findViewById(R.id.myTicketsRecycleView);
        mSearchView = findViewById(R.id.floating_search_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(filmSearchAdapter);


        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                ArrayList<MySearchSuggestion> temp=new ArrayList<>();
                temp.add(new MySearchSuggestion("Film #1"));
                temp.add(new MySearchSuggestion("Film #2"));
                temp.add(new MySearchSuggestion("Film #3"));
                temp.add(new MySearchSuggestion("Film #4"));
                temp.add(new MySearchSuggestion("Film #5"));

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
                Toast.makeText(SearchFilmActivity.this, searchSuggestion.getBody(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchFilmActivity.this,AboutFilmActivity.class);
                startActivity(intent);

            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });

    }
}
