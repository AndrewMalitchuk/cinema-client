package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.cinema.client.R;
import com.cinema.client.adapters.FilmSearchAdapter;
import com.cinema.client.entities.FilmItemSearch;
import com.cinema.client.etc.MySearchSuggestion;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.FilmAPI;
import com.google.firebase.inappmessaging.internal.ApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFilmActivity extends AppCompatActivity {

    Toolbar toolbar;


    RecyclerView recyclerView;
    FilmSearchAdapter filmSearchAdapter;
    ArrayList<FilmItemSearch> filmItemSearchList;

    FloatingSearchView mSearchView;


    private List<FilmAPI> films;

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
                startActivity(new Intent(getApplicationContext(), Main3Activity.class));
            }
        });

        filmItemSearchList = new ArrayList<>();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<List<FilmAPI>> call = apiInterface.getFilms();

        call.enqueue(new Callback<List<FilmAPI>>() {
            @Override
            public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                films = response.body();


                for (FilmAPI film : films) {
                    FilmItemSearch filmItemSearch = new FilmItemSearch();

                    filmItemSearch.setFilmId(film.getId());

                    filmItemSearch.setFilmName(film.getTitle());
                    filmItemSearch.setFilmDateTime("Date: "+film.getDate());
                    filmItemSearch.setFilmPlace("Dur: "+film.getDuration().toString()+" min.");

                    String genre = "Loading...";

                    switch (film.getGenre()) {
                        case 1:
                            genre="Comedy";
                            break;
                        case 2:
                            genre="Action";
                            break;
                        case 3:
                            genre="Historical";
                            break;
                        case 4:
                            genre="Sci-Fi";
                            break;
                        case 5:
                            genre="Horror";
                            break;
                    }


                    filmItemSearch.setFilmCinema("Genre: "+genre);
                    filmItemSearch.setFilmImg(APIClient.HOST + film.getPicUrl());

                    filmItemSearchList.add(filmItemSearch);
                }

                Log.d("SFACT", filmItemSearchList.size() + "");


                Log.d("SFACT", filmItemSearchList.size() + "");


                recyclerView = (RecyclerView) findViewById(R.id.myTicketsRecycleView);

//        recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setHasFixedSize(false);
                recyclerView.setNestedScrollingEnabled(false);

                filmSearchAdapter = new FilmSearchAdapter(filmItemSearchList);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(filmSearchAdapter);


            }

            @Override
            public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(SearchFilmActivity.this, ErrorActivity.class);
                startActivity(intent);
            }
        });


        mSearchView = findViewById(R.id.floating_search_view);


        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {


                Log.d("NEW", newQuery);

//                ArrayList<MySearchSuggestion> temp=new ArrayList<>();
//                temp.add(new MySearchSuggestion("Film #1"));
//                temp.add(new MySearchSuggestion("Film #2"));
//                temp.add(new MySearchSuggestion("Film #3"));
//                temp.add(new MySearchSuggestion("Film #4"));
//                temp.add(new MySearchSuggestion("Film #5"));

                mSearchView.swapSuggestions(searchInLoadedData(newQuery));
            }
        });


        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Toast.makeText(SearchFilmActivity.this, searchSuggestion.getBody(), Toast.LENGTH_SHORT).show();

                Call<FilmAPI> call = apiInterface.getFilmByTitle(searchSuggestion.getBody());

                call.enqueue(new Callback<FilmAPI>() {
                    @Override
                    public void onResponse(Call<FilmAPI> call, Response<FilmAPI> response) {
                        Intent intent = new Intent(SearchFilmActivity.this, AboutFilmActivity.class);
                        intent.putExtra("filmId", response.body().getId());

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<FilmAPI> call, Throwable t) {
                        call.cancel();
                        Intent intent = new Intent(SearchFilmActivity.this, ErrorActivity.class);
                        startActivity(intent);
                    }
                });


            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });

    }

    public ArrayList<MySearchSuggestion> searchInLoadedData(String value) {

        ArrayList<MySearchSuggestion> res = new ArrayList<>();

        for (FilmAPI film : films) {
//            if(film.getTitle().conr){
            if (Pattern.compile(Pattern.quote(value), Pattern.CASE_INSENSITIVE).matcher(film.getTitle()).find()) {
                res.add(new MySearchSuggestion(film.getTitle()));
            }
        }

        return res;

    }

}
