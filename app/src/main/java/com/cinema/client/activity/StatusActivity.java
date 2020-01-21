package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.cinema.client.R;
import com.cinema.client.etc.MyItem;
import com.cinema.client.etc.StatusAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pd.chocobar.ChocoBar;
import com.transferwise.sequencelayout.SequenceLayout;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        // потрібно зазначити лише той пункт тру, на якому має закінчитися прогрес

        String lorem ="Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";


        MyItem item1=new MyItem(false,"01.01.2001","lol1",false,null);
        MyItem item2=new MyItem(false,"01.01.2002","lol1",false,null);
        MyItem item3=new MyItem(true,"01.01.2003","lol1",true,lorem);
        MyItem item4=new MyItem(false,"01.01.2004","lol1",false,null);
        MyItem item5=new MyItem(false,"01.01.2005","lol1",false,null);
        MyItem item6=new MyItem(false,"01.01.2006","lol1",false,null);
        MyItem item7=new MyItem(false,"01.01.2007","lol1",false,null);
        MyItem item8=new MyItem(false,"01.01.2008","lol1",false,null);
        MyItem item9=new MyItem(false,"01.01.2009","lol1",false,null);
        list.add(item1);
        list.add(item2);
        list.add(item4);
        list.add(item5);
        list.add(item6);
        list.add(item3);
        list.add(item7);
        list.add(item8);
        list.add(item9);
//        sequenceLayout.setAdapter(new StatusAdapter(list, this));
        sequenceLayout.setAdapter(new StatusAdapter(list, this,selectedDateTimeTextView,"01.01.2003"));




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
        datePickerTimeline.setInitialDate(2019, 3, 21);
// Set a date Selected Listener
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                // Do Something
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

    public void onFABClick(View view){

        String selectedDate=selectedDateTimeTextView.getText().toString();

        if(selectedDate.equals("Nothing yet...")){
            ChocoBar.builder().setActivity(StatusActivity.this)
                    .setText("Please, select date and time!")
                    .setDuration(ChocoBar.LENGTH_SHORT)
                    .red()
                    .show();
        }else{
            ChocoBar.builder().setActivity(StatusActivity.this)
                    .setText("Selected date:\n"+selectedDateTimeTextView.getText().toString())
                    .setDuration(ChocoBar.LENGTH_SHORT)
                    .green()
                    .show();
        }


    }

}
