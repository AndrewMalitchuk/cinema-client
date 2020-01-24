package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        ButterKnife.bind(this);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AboutFilmActivity.class));
            }
        });


        List<MyItem> list = new ArrayList<>();

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<TimelineAPI>> call = apiInterface.getTimelineByCinemaIdAndFilmId(9, 12);

        call.enqueue(new Callback<List<TimelineAPI>>() {
            @Override
            public void onResponse(Call<List<TimelineAPI>> call, Response<List<TimelineAPI>> response) {

                try {
                    FilmAPI film = apiInterface.getFilmById(12).execute().body();
//                    HallAPI hall=apiInterface.getHallByCinemaId().execute().body();

                    String currentTime="15:00:00";



                    for (TimelineAPI timeline : response.body()) {
                        list.add(new MyItem(false, timeline.getTime(), film.getTitle(), false, "Ruby hall"));
                    }

                    Collections.sort(list, new Comparator<MyItem>() {
                        @Override
                        public int compare(MyItem u1, MyItem u2) {
                            return u1.getFormattedDate().compareTo(u2.getFormattedDate());
                        }
                    });


//                    sequenceLayout.setAdapter(new StatusAdapter(list, getApplicationContext()));

//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        sequenceLayout.setAdapter(new StatusAdapter(list, getApplicationContext(), selectedDateTimeTextView,
//                                DateTimeFormatter.ofPattern("hh:mm:ss").format(LocalDateTime.now())));
//                    }
                        sequenceLayout.setAdapter(new StatusAdapter(list, getApplicationContext(), selectedDateTimeTextView,currentTime));



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

//        Log.d("ITEM",list.size()+"");


//        List<MyItem> list = new ArrayList<>();
//
//        // потрібно зазначити лише той пункт тру, на якому має закінчитися прогрес
//
//        String lorem ="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
//
//
//        MyItem item1=new MyItem(false,"01.01.2001","lol1",false,null);
//        MyItem item2=new MyItem(false,"01.01.2002","lol1",false,null);
//        MyItem item3=new MyItem(true,"01.01.2003","lol1",true,lorem);
//        MyItem item4=new MyItem(false,"01.01.2004","lol1",false,null);
//        MyItem item5=new MyItem(false,"01.01.2005","lol1",false,null);
//        MyItem item6=new MyItem(false,"01.01.2006","lol1",false,null);
//        MyItem item7=new MyItem(false,"01.01.2007","lol1",false,null);
//        MyItem item8=new MyItem(false,"01.01.2008","lol1",false,null);
//        MyItem item9=new MyItem(false,"01.01.2009","lol1",false,null);
//        list.add(item1);
//        list.add(item2);
//        list.add(item4);
//        list.add(item5);
//        list.add(item6);
//        list.add(item3);
//        list.add(item7);
//        list.add(item8);
//        list.add(item9);
//        sequenceLayout.setAdapter(new StatusAdapter(list, this));
//        sequenceLayout.setAdapter(new StatusAdapter(list, this,selectedDateTimeTextView,"01.01.2003"));


//        ArrayList<String> spinnerContent = new ArrayList<>();
//        spinnerContent.add("kek #1");
//        spinnerContent.add("kek #2");
//        spinnerContent.add("kek #3");
//        spinnerContent.add("kek #4");
//        spinnerContent.add("kek #5");
//
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,spinnerContent);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//
//        spinner.setAdapter(spinnerAdapter);


        //
        DatePickerTimeline datePickerTimeline = findViewById(R.id.datePickerTimeline);
// Set a Start date (Default, 1 Jan 1970)
        LocalDateTime date;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDateTime.now();
            datePickerTimeline.setInitialDate(date.getYear(), date.getMonthValue()-1, date.getDayOfMonth());

        }
// Set a date Selected Listener
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                Date date=new Date();
                date.setYear(year);
                date.setMonth(month);
                date.setDate(day);

                Toast.makeText(StatusActivity.this, date.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
                // Do Something
            }
        });




// Disable date
//        Date[] dates = {Calendar.getInstance().getTime()};
//        datePickerTimeline.deactivateDates(dates);

        //


    }

    public void onFABClick(View view) {

        String selectedDate = selectedDateTimeTextView.getText().toString();

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
        }


    }

}
