package com.cinema.client.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cinema.client.R;
import com.cinema.client.etc.MyItem;
import com.cinema.client.etc.StatusAdapter;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.FilmAPI;
import com.cinema.client.requests.entities.HallAPI;
import com.cinema.client.requests.entities.TimelineAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pd.chocobar.ChocoBar;
import com.transferwise.sequencelayout.SequenceLayout;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusActivity extends AppCompatActivity {


    @BindView(R.id.status)
    SequenceLayout sequenceLayout;

//    @BindView(R.id.spinner)
//    Spinner spinner;

    @BindView(R.id.selectedDateTimeTextView)
    TextView selectedDateTimeTextView;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.toolbar7)
    Toolbar toolbar;

    @BindView(R.id.calendarView)
    CalendarView calendarView;

//    @BindView(R.id.datePickerTimeline)
//    DatePickerTimeline datePickerTimeline;


    private String dateStr;

    private APIInterface apiInterface;

    private boolean isFilmTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        ButterKnife.bind(this);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        apiInterface = APIClient.getClient().create(APIInterface.class);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String cinemaName = getIntent().getStringExtra("cinemaName");
        toolbar.setTitle(cinemaName);





        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AboutFilmActivity.class));
            }
        });

        isFilmTimeline=getIntent().getBooleanExtra("isFilmTimeline",false);


        final int cinema_id = getIntent().getIntExtra("cinemaId", -1);
        final int film_id = getIntent().getIntExtra("filmId", -1);

//        if (cinema_id == -1) {
//            cinema_id = 9;
//        }
//        if (film_id == -1) {
//            film_id = 12;
//        }


        if(isFilmTimeline){

            calendarView.setDate(new Date(new Date().getYear(),new Date().getMonth(),new Date().getDate()).getTime(), true, true);

            setTimeline(
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date(calendarView.getDate())),
                    cinema_id,
                    film_id);

        }else{

            try {
                calendarView.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(getIntent().getStringExtra("selectedDate")).getTime(), true, true);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.d("DATE",getIntent().getStringExtra("selectedDate"));

            setTimeline(getIntent().getStringExtra("selectedDate"),cinema_id);
        }



//        List<MyItem> list = new ArrayList<>();
//
//        apiInterface = APIClient.getClient().create(APIInterface.class);
//        Call<List<TimelineAPI>> call = apiInterface.getTimelineByCinemaIdAndFilmId(cinema_id, film_id);
//
//        call.enqueue(new Callback<List<TimelineAPI>>() {
//            @Override
//            public void onResponse(Call<List<TimelineAPI>> call, Response<List<TimelineAPI>> response) {
//
//
//                try {
//                    FilmAPI film = apiInterface.getFilmById(film_id).execute().body();
////                    HallAPI hall=apiInterface.getHallByCinemaId().execute().body();
//
//                    String currentTime = "15:00:00";
//
//
//                    for (TimelineAPI timeline : response.body()) {
//                        list.add(new MyItem(false, timeline.getTime(), film.getTitle(), false, "Ruby hall"));
//                    }
//
//                    Collections.sort(list, new Comparator<MyItem>() {
//                        @Override
//                        public int compare(MyItem u1, MyItem u2) {
//                            return u1.getFormattedDate().compareTo(u2.getFormattedDate());
//                        }
//                    });
//
//
////                    sequenceLayout.setAdapter(new StatusAdapter(list, getApplicationContext()));
//
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////                        sequenceLayout.setAdapter(new StatusAdapter(list, getApplicationContext(), selectedDateTimeTextView,
////                                DateTimeFormatter.ofPattern("hh:mm:ss").format(LocalDateTime.now())));
////                    }
//                    sequenceLayout.setAdapter(new StatusAdapter(list, getApplicationContext(), selectedDateTimeTextView, currentTime));
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Intent intent = new Intent(StatusActivity.this, ErrorActivity.class);
//                    startActivity(intent);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<TimelineAPI>> call, Throwable t) {
//                call.cancel();
//                Intent intent = new Intent(StatusActivity.this, ErrorActivity.class);
//                startActivity(intent);
//            }
//        });


        //

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                Toast.makeText(StatusActivity.this, day+" "+(month+1)+" "+year, Toast.LENGTH_SHORT).show();
                selectedDateTimeTextView.setText("Nothing yet...");

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

                Date temp=new Date();
                temp.setYear(year-1900);
                temp.setMonth(month);
                temp.setDate(day);

                String selectedDate=simpleDateFormat.format(temp);

                if(isFilmTimeline){
                    setTimeline(selectedDate,cinema_id,film_id);
                }else{
                    setTimeline(selectedDate,cinema_id);
                }

            }
        });

    }

    public void onFABClick(View view) {

        String selectedDate = selectedDateTimeTextView.getText().toString();

        if (calendarView.getDate()<= new Date(new Date().getYear(),new Date().getMonth(),new Date().getDate()).getTime()){
            ChocoBar.builder().setActivity(StatusActivity.this)
                    .setText("Please, check your date!")
                    .setDuration(ChocoBar.LENGTH_SHORT)
                    .red()
                    .show();
        }else {
            if (selectedDate.equals("Nothing yet...")) {

                ChocoBar.builder().setActivity(StatusActivity.this)
                        .setText("Please, select date and time!")
                        .setDuration(ChocoBar.LENGTH_SHORT)
                        .red()
                        .show();
            } else {

                ChocoBar.builder().setActivity(StatusActivity.this)
                        .setText("Selected date:\n" + selectedDateTimeTextView.getText().toString())
                        .setDuration(ChocoBar.LENGTH_SHORT)
                        .green()
                        .show();

                String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date(calendarView.getDate()));

                getIntent().putExtra("datetime", date + " " + selectedDateTimeTextView.getText().toString());
                setResult(RESULT_OK, getIntent());
                finish();


            }
        }


    }

    private void setTimeline(String date,int cinema_id){

        Log.d("INPUT",date+" "+cinema_id);

        List<MyItem> list = new ArrayList<>();
        Call<List<TimelineAPI>> call = apiInterface.getTimelineByDateAndCinemaId(date,cinema_id);

        call.enqueue(new Callback<List<TimelineAPI>>() {
            @Override
            public void onResponse(Call<List<TimelineAPI>> call, Response<List<TimelineAPI>> response) {


                try {

//                    HallAPI hall=apiInterface.getHallByCinemaId().execute().body();

                    String currentTime = "00:00:00";


                    for (TimelineAPI timeline : response.body()) {

                        FilmAPI film = apiInterface.getFilmById(timeline.getFilmId()).execute().body();

                        list.add(new MyItem(false, timeline.getTime(), film.getTitle(), false, "Ruby hall"));
                    }

                    if(list.size()==0){
                        ChocoBar.builder().setActivity(StatusActivity.this)
                                .setText("There are no films for selected date")
                                .setDuration(ChocoBar.LENGTH_SHORT)
                                .red()
                                .show();
                        sequenceLayout.removeAllSteps();
                    }else {

                        Collections.sort(list, new Comparator<MyItem>() {
                            @Override
                            public int compare(MyItem u1, MyItem u2) {
                                return u1.getFormattedDate().compareTo(u2.getFormattedDate());
                            }
                        });


                        sequenceLayout.setAdapter(new StatusAdapter(list, getApplicationContext(), selectedDateTimeTextView, currentTime));
                    }


                    sequenceLayout.setAdapter(new StatusAdapter(list, getApplicationContext(), selectedDateTimeTextView, currentTime));


                } catch (IOException e) {
                    e.printStackTrace();
                    Intent intent = new Intent(StatusActivity.this, ErrorActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<List<TimelineAPI>> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(StatusActivity.this, ErrorActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setTimeline(String date,int cinema_id,int film_id){

        Log.d("INPUT",date+" "+cinema_id+" "+film_id);

        List<MyItem> list = new ArrayList<>();
        Call<List<TimelineAPI>> call = apiInterface.getTimelineByDateAndCinemaIdAndFilmId(date,cinema_id,film_id);

        call.enqueue(new Callback<List<TimelineAPI>>() {
            @Override
            public void onResponse(Call<List<TimelineAPI>> call, Response<List<TimelineAPI>> response) {


                try {

//                    HallAPI hall=apiInterface.getHallByCinemaId().execute().body();

                    String currentTime = "00:00:00";


                    for (TimelineAPI timeline : response.body()) {

                        FilmAPI film = apiInterface.getFilmById(timeline.getFilmId()).execute().body();

                        list.add(new MyItem(false, timeline.getTime(), film.getTitle(), false, "Ruby hall"));
                    }

                    if(list.size()==0){
                        ChocoBar.builder().setActivity(StatusActivity.this)
                                .setText("There are no films for selected date")
                                .setDuration(ChocoBar.LENGTH_SHORT)
                                .red()
                                .show();
                        sequenceLayout.removeAllSteps();
                    }else {

                        Collections.sort(list, new Comparator<MyItem>() {
                            @Override
                            public int compare(MyItem u1, MyItem u2) {
                                return u1.getFormattedDate().compareTo(u2.getFormattedDate());
                            }
                        });


                        sequenceLayout.setAdapter(new StatusAdapter(list, getApplicationContext(), selectedDateTimeTextView, currentTime));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Intent intent = new Intent(StatusActivity.this, ErrorActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<List<TimelineAPI>> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(StatusActivity.this, ErrorActivity.class);
                startActivity(intent);
            }
        });
    }

}
