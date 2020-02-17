package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.cinema.client.R;
import com.cinema.client.etc.MySearchSuggestion;
import com.cinema.client.entities.TicketItemSearch;
import com.cinema.client.adapters.TicketSearchAdapter;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.TicketAPI;
import com.cinema.client.requests.entities.TokenAPI;
import com.google.firebase.inappmessaging.internal.ApiClient;
import com.pd.chocobar.ChocoBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTicketsActivity extends AppCompatActivity {

//    Toolbar toolbar;
//
//    RecyclerView recyclerView;
//    TicketSearchAdapter myTicketsAdapter;
//    ArrayList<TicketItemSearch> myTicketsArrayList;
//
//    FloatingSearchView mSearchView;
//
//    private APIInterface apiInterface;
//
//    public static final String ACCOUNT_PREF = "accountPref";
//    private SharedPreferences sharedpreferences;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_tickets);
//
//        toolbar = findViewById(R.id.toolbar3);
//        toolbar.setTitle("Tickets");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
//            }
//        });
//
//        myTicketsArrayList = new ArrayList<>();
//
//
//        apiInterface = APIClient.getClient().create(APIInterface.class);
//
//        sharedpreferences = getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
//        if (sharedpreferences != null) {
////            String token = sharedpreferences.getString("token", null);
//            String login = sharedpreferences.getString("login", null);
//            String password = sharedpreferences.getString("password", null);
//            int userId = sharedpreferences.getInt("userId", -1);
//
//            RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"),
//                    password);
//
//            RequestBody login_ = RequestBody.create(MediaType.parse("text/plain"),
//                    login);
//
////            ChocoBar.builder().setActivity(MyTicketsActivity.this)
////                    .setText("Something goes wrong!")
////                    .setDuration(ChocoBar.LENGTH_SHORT)
////                    .red()
////                    .show();
//
//            Observable<TokenAPI> tokenRx = apiInterface.refreshTokenRx(login_, password_);
//            tokenRx.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .map(result -> result.getAccess())
//                    .subscribe(this::onTokenSuccess, this::onTokenFail);
//
//
//        }
//
//
////
//
//
////        for (int i = 0; i < 10; i++) {
////            TicketItemSearch myTickets = new TicketItemSearch();
////
////            myTickets.setFilmName("Film #" + i);
////            myTickets.setFilmDateTime("Date #" + i);
////            myTickets.setFilmPlace("Place #" + i);
////            myTickets.setFilmCinema("Cinema #" + i);
////            myTickets.setFilmImg(R.drawable.once_upon_a_time);
////
////            myTicketsArrayList.add(myTickets);
////        }
////
////
////        myTicketsAdapter = new TicketSearchAdapter(myTicketsArrayList);
////
////        recyclerView = (RecyclerView) findViewById(R.id.myTicketsRecycleView);
////        mSearchView = findViewById(R.id.floating_search_view);
////        recyclerView.setLayoutManager(new LinearLayoutManager(this));
////        recyclerView.setItemAnimator(new DefaultItemAnimator());
////        recyclerView.setAdapter(myTicketsAdapter);
////
////
////        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
////            @Override
////            public void onSearchTextChanged(String oldQuery, final String newQuery) {
////
////                ArrayList<MySearchSuggestion> temp = new ArrayList<>();
////                temp.add(new MySearchSuggestion("Ticket #1"));
////                temp.add(new MySearchSuggestion("Ticket #2"));
////                temp.add(new MySearchSuggestion("Ticket #3"));
////                temp.add(new MySearchSuggestion("Ticket #4"));
////                temp.add(new MySearchSuggestion("Ticket #5"));
////
////                mSearchView.swapSuggestions(temp);
////            }
////        });
////
////        //for menu in floating menu view
////        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
////            @Override
////            public void onActionMenuItemSelected(MenuItem item) {
////
////            }
////
////        });
////
////        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
////            @Override
////            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
////                Toast.makeText(MyTicketsActivity.this, searchSuggestion.getBody(), Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(MyTicketsActivity.this, TicketActivity.class);
////                startActivity(intent);
////
////            }
////
////            @Override
////            public void onSearchAction(String currentQuery) {
////
////            }
////        });
//
//    }
//
//    public void onTokenSuccess(String token) {
//
//        int userId = sharedpreferences.getInt("userId", -1);
//
//        Call<List<TicketAPI>> call = apiInterface.getTicketByUserId(userId, token);
//        call.enqueue(new Callback<List<TicketAPI>>() {
//            @Override
//            public void onResponse(Call<List<TicketAPI>> call, Response<List<TicketAPI>> response) {
//
//                for (TicketAPI ticketAPI : response.body()) {
//                    TicketItemSearch myTickets = new TicketItemSearch();
//
//                    myTickets.setFilmName("Film #" +ticketAPI.getFilmId());
//                    myTickets.setFilmDateTime(ticketAPI.getDate());
//                    myTickets.setFilmPlace(ticketAPI.getPlace());
//                    myTickets.setFilmCinema(ticketAPI.getCinemaId() + "");
//                    myTickets.setFilmImg(R.drawable.once_upon_a_time);
//
//                    myTicketsArrayList.add(myTickets);
//                }
//
//
//                myTicketsAdapter = new TicketSearchAdapter(myTicketsArrayList);
//
//                recyclerView = (RecyclerView) findViewById(R.id.myTicketsRecycleView);
//                mSearchView = findViewById(R.id.floating_search_view);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                recyclerView.setItemAnimator(new DefaultItemAnimator());
//                recyclerView.setAdapter(myTicketsAdapter);
//
//
//                mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
//                    @Override
//                    public void onSearchTextChanged(String oldQuery, final String newQuery) {
//
//                        ArrayList<MySearchSuggestion> temp = new ArrayList<>();
//                        temp.add(new MySearchSuggestion("Ticket #1"));
//                        temp.add(new MySearchSuggestion("Ticket #2"));
//                        temp.add(new MySearchSuggestion("Ticket #3"));
//                        temp.add(new MySearchSuggestion("Ticket #4"));
//                        temp.add(new MySearchSuggestion("Ticket #5"));
//
//                        mSearchView.swapSuggestions(temp);
//                    }
//                });
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<TicketAPI>> call, Throwable t) {
//
//            }
//        });
//
//
//    }
//
//    public void onTokenFail(Throwable throwable) {
//
//    }


}