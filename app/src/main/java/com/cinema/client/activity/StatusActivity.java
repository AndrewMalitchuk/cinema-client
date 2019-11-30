package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.cinema.client.R;
import com.cinema.client.etc.MyItem;
import com.cinema.client.etc.StatusAdapter;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        ButterKnife.bind(this);

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
        sequenceLayout.setAdapter(new StatusAdapter(list, this));


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
}
