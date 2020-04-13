package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.cinema.client.R;
import com.cinema.client.adapters.CinemaSearchAdapter;
import com.cinema.client.adapters.FavouriteCinemasAdapter;
import com.cinema.client.adapters.FilmSearchAdapter;
import com.cinema.client.entities.CinemaItemSearch;
import com.cinema.client.entities.FilmItemSearch;
import com.cinema.client.etc.MySearchSuggestion;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.CinemaAPI;
import com.cinema.client.requests.entities.FilmAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pd.chocobar.ChocoBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCinemaActivity extends AppCompatActivity {

    Toolbar toolbar;


    RecyclerView recyclerView;
    CinemaSearchAdapter cinemaSearchAdapter;
    ArrayList<CinemaItemSearch> cinemaItemSearchList;

    FloatingSearchView mSearchView;

    private List<CinemaAPI> cinemas;

    private boolean isForChoosing = false;
//    private boolean isForSelected=false;

    private String selectedCinemas;
    public static final String FAVOURITE_CINEMAS_PREF = "favourite_cinema_pref";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cinema);

        toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("Cinemas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        //


        //

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        int cityId = getIntent().getIntExtra("cityId", -1);

        isForChoosing = getIntent().getBooleanExtra("isForChoosing", false);
//        isForSelected=getIntent().getBooleanExtra("isForSelected",false);

        cinemaItemSearchList = new ArrayList<>();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<List<CinemaAPI>> call;

        String fav_json = getIntent().getStringExtra("selectedCinemasJson");

        if (fav_json != null) {
            Gson gson = new GsonBuilder().create();
            List<Integer> favourite_cinema_id_list = gson.fromJson(fav_json, new TypeToken<List<Integer>>() {
            }.getType());


            if (favourite_cinema_id_list.size() != 0) {

                List<CinemaAPI> favouriteCinemasList = new ArrayList<>();

                for (Integer i : favourite_cinema_id_list) {

                    Call<CinemaAPI> currentCinema = apiInterface.getCinemaById(i);
                    try {

                        CinemaAPI cinemaAPI = currentCinema.execute().body();
                        favouriteCinemasList.add(cinemaAPI);


                    } catch (IOException e) {
                        e.printStackTrace();
                        Intent intent=new Intent(SearchCinemaActivity.this, ErrorActivity.class);
                        intent.putExtra("isAppError",true);
                        startActivity(intent);
                    }


                }


                for (CinemaAPI cinema : favouriteCinemasList) {
                    CinemaItemSearch cinemaItemSearch = new CinemaItemSearch();

                    cinemaItemSearch.setCinemaId(cinema.getId());
                    cinemaItemSearch.setCinemaName(cinema.getName());
                    cinemaItemSearch.setCinemaAddress(cinema.getAddress());
                    cinemaItemSearch.setCinemaImg(APIClient.HOST + cinema.getPicUrl());

                    cinemaItemSearchList.add(cinemaItemSearch);
                }




                recyclerView = (RecyclerView) findViewById(R.id.myTicketsRecycleView);

//        recyclerView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setHasFixedSize(false);
                recyclerView.setNestedScrollingEnabled(false);

                if (isForChoosing) {

                    cinemaSearchAdapter = new CinemaSearchAdapter(cinemaItemSearchList, true, getIntent(), this);

                } else {

                    cinemaSearchAdapter = new CinemaSearchAdapter(cinemaItemSearchList);
                }
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(cinemaSearchAdapter);


            } else {
                ChocoBar.builder().setActivity(SearchCinemaActivity.this)
                        .setText("There are no favourite cinemas at all")
                        .setDuration(ChocoBar.LENGTH_SHORT)
                        .orange()
                        .show();
            }


        }


        if (fav_json == null) {
            if (cityId == -1) {
                call = apiInterface.getCinemas();
            } else {
                call = apiInterface.getCinemaByCity(cityId);
            }


            Activity activity = this;
            call.enqueue(new Callback<List<CinemaAPI>>() {
                @Override
                public void onResponse(Call<List<CinemaAPI>> call, Response<List<CinemaAPI>> response) {
                    cinemas = response.body();


                    for (CinemaAPI cinema : cinemas) {
                        CinemaItemSearch cinemaItemSearch = new CinemaItemSearch();

                        cinemaItemSearch.setCinemaId(cinema.getId());
                        cinemaItemSearch.setCinemaName(cinema.getName());
                        cinemaItemSearch.setCinemaAddress("Address: \n"+cinema.getAddress());
                        cinemaItemSearch.setCinemaImg(APIClient.HOST + cinema.getPicUrl());

                        cinemaItemSearchList.add(cinemaItemSearch);
                    }



                    recyclerView = (RecyclerView) findViewById(R.id.myTicketsRecycleView);

//        recyclerView.setItemAnimator(new DefaultItemAnimator());

                    recyclerView.setHasFixedSize(false);
                    recyclerView.setNestedScrollingEnabled(false);

                    if (isForChoosing) {

                        cinemaSearchAdapter = new CinemaSearchAdapter(cinemaItemSearchList, true, getIntent(), activity);

                    } else {

                        cinemaSearchAdapter = new CinemaSearchAdapter(cinemaItemSearchList);
                    }
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(cinemaSearchAdapter);


                }

                @Override
                public void onFailure(Call<List<CinemaAPI>> call, Throwable t) {
                    call.cancel();
                    Intent intent = new Intent(SearchCinemaActivity.this, ErrorActivity.class);
                    intent.putExtra("isNetworkError",true);
                    startActivity(intent);

                }
            });

        }


        mSearchView = findViewById(R.id.floating_search_view);


        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                mSearchView.swapSuggestions(searchInLoadedData(newQuery));
            }
        });


        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Call<CinemaAPI> call = apiInterface.getCinemaByName(searchSuggestion.getBody());
                call.enqueue(new Callback<CinemaAPI>() {
                    @Override
                    public void onResponse(Call<CinemaAPI> call, Response<CinemaAPI> response) {
                        if (isForChoosing) {
                            getIntent().putExtra("cinemaName", response.body().getName());
                            getIntent().putExtra("cinemaId", response.body().getId());
                            setResult(RESULT_OK, getIntent());
                            finish();
                        } else {
                            Intent intent = new Intent(SearchCinemaActivity.this, AboutCinemaActivity.class);
                            intent.putExtra("cinemaId", response.body().getId());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<CinemaAPI> call, Throwable t) {
                        call.cancel();
                        Intent intent = new Intent(SearchCinemaActivity.this, ErrorActivity.class);
                        intent.putExtra("isNetworkError",true);
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

        for (CinemaAPI cinema : cinemas) {
//            if(film.getTitle().conr){
            if (Pattern.compile(Pattern.quote(value), Pattern.CASE_INSENSITIVE).matcher(cinema.getName()).find()) {
                res.add(new MySearchSuggestion(cinema.getName()));
            }
        }

        return res;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}