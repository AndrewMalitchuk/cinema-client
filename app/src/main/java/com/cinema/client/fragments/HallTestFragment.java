package com.cinema.client.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cinema.client.MainActivity;
import com.cinema.client.R;
import com.cinema.client.activity.BottomNavigation;
import com.cinema.client.entities.Hall;
import com.cinema.client.requests.entities.FilmAPI;
import com.cinema.client.requests.entities.HallAPI;
import com.cinema.client.utils.HallJsonParser;
import com.cinema.client.utils.HallRender;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HallTestFragment extends Fragment {

    @BindView(R.id.tableLayout1)
    TableLayout tableLayout1;


    private HallAPI hall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment

        View view = inflater.inflate(R.layout.fragment_hall_test, parent, false);
        ButterKnife.bind(this, view);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String json=bundle.getString("json");
            Log.d("json",json);

            Gson gson = new GsonBuilder().create();
            hall=gson.fromJson(json, new TypeToken<HallAPI>(){}.getType());

            Log.d("L",hall.getSector());

//            this.hall=hall.get(2);

        }

        //

        //

        return view;


    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);


//        MyTask mt = new MyTask();
//        mt.execute();

        //
//        test();
//        test(new HallJsonParser().jsonFooBar());

//        tableLayout1=new HallRender(getContext()).render(new HallJsonParser().jsonFooBar(),tableLayout1);

        ArrayList<String> list=new ArrayList<>();

        tableLayout1=new HallRender(getContext()).render(hall,tableLayout1);
//        tableLayout1=new HallRender(getActivity()).render(hall,tableLayout1);
//        tableLayout1=new HallRender(getContext()).render(hall,tableLayout1);
//        HallRender hallRender=new HallRender(getContext());
//        hallRender.render(new HallJsonParser().jsonFooBar(),tableLayout1);
//        hallRender.setRow();






    }







}
