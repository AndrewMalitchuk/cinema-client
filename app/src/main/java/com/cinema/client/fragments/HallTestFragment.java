package com.cinema.client.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.cinema.client.R;
import com.cinema.client.requests.entities.HallAPI;
import com.cinema.client.utils.HallRender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class HallTestFragment extends Fragment {

    @BindView(R.id.tableLayout1)
    TableLayout tableLayout1;

    @Getter
    @Setter
    private HallAPI hall;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hall_test, parent, false);
        ButterKnife.bind(this, view);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String json = bundle.getString("json");
            Gson gson = new GsonBuilder().create();
            hall = gson.fromJson(json, new TypeToken<HallAPI>() {
            }.getType());
        }
        return view;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tableLayout1 = new HallRender(getContext()).render(hall, tableLayout1);
    }

}
