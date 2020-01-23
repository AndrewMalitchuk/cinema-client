package com.cinema.client.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brouding.simpledialog.SimpleDialog;
import com.bumptech.glide.annotation.GlideModule;
import com.cinema.client.R;
import com.cinema.client.entities.Hall;
import com.cinema.client.fragments.HallTestFragment;
import com.cinema.client.requests.entities.HallAPI;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nonzeroapps.whatisnewdialog.NewItemDialog;
import com.nonzeroapps.whatisnewdialog.object.NewFeatureItem;
import com.pd.chocobar.ChocoBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.markdownview.Config;
import es.dmoral.markdownview.MarkdownView;
import iammert.com.library.Status;
import iammert.com.library.StatusView;

public class BottomNavigation extends AppCompatActivity {


    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.toolbar4)
    Toolbar toolbar;



//    private ProgressDialog mProgressDialog;
    MyTask mt;

//    @BindView(R.id.status)
//    StatusView statusView;

    //
//    SimpleDialog simpleDialog;
    //

    @BindView(R.id.llProgressBar)
    View llProgressBar;


    String json_text="[\n" +
            "   {\n" +
            "      \"sector\":\"left\",\n" +
            "      \"row\":2,\n" +
            "      \"col\":2,\n" +
            "      \"disabled\":[\n" +
            "\n" +
            "      ],\n" +
            "      \"custom\":[\n" +
            "\n" +
            "      ]\n" +
            "   },\n" +
            "   {\n" +
            "      \"sector\":\"center\",\n" +
            "      \"row\":4,\n" +
            "      \"col\":4,\n" +
            "      \"disabled\":[\n" +
            "         {\n" +
            "            \"row\":1,\n" +
            "            \"col\":1\n" +
            "         },\n" +
            "         {\n" +
            "            \"row\":1,\n" +
            "            \"col\":4\n" +
            "         },\n" +
            "         {\n" +
            "            \"row\":2,\n" +
            "            \"col\":1\n" +
            "         },\n" +
            "         {\n" +
            "            \"row\":2,\n" +
            "            \"col\":4\n" +
            "         },\n" +
            "         {\n" +
            "            \"row\":3,\n" +
            "            \"col\":1\n" +
            "         },\n" +
            "         {\n" +
            "            \"row\":3,\n" +
            "            \"col\":4\n" +
            "         },\n" +
            "         {\n" +
            "            \"row\":4,\n" +
            "            \"col\":1\n" +
            "         },\n" +
            "         {\n" +
            "            \"row\":4,\n" +
            "            \"col\":4\n" +
            "         }\n" +
            "      ],\n" +
            "      \"custom\":[\n" +
            "\n" +
            "      ]\n" +
            "   },\n" +
            "   {\n" +
            "      \"sector\":\"right\",\n" +
            "      \"row\":6,\n" +
            "      \"col\":6,\n" +
            "      \"disabled\":[\n" +
            "         {\n" +
            "            \"row\":1,\n" +
            "            \"col\":1\n" +
            "         },\n" +
            "         {\n" +
            "            \"row\":1,\n" +
            "            \"col\":2\n" +
            "         },\n" +
            "         {\n" +
            "            \"row\":1,\n" +
            "            \"col\":5\n" +
            "         },\n" +
            "         {\n" +
            "            \"row\":1,\n" +
            "            \"col\":6\n" +
            "         }\n" +
            "      ],\n" +
            "      \"custom\":[\n" +
            "         {\n" +
            "            \"old_row\":1,\n" +
            "            \"old_col\":3,\n" +
            "            \"new_row\":1,\n" +
            "            \"new_col\":1\n" +
            "         },\n" +
            "         {\n" +
            "            \"old_row\":1,\n" +
            "            \"old_col\":4,\n" +
            "            \"new_row\":1,\n" +
            "            \"new_col\":2\n" +
            "         }\n" +
            "      ]\n" +
            "   }\n" +
            "]";


    Gson gson=new Gson().newBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        ButterKnife.bind(this);

        //

        toolbar.setTitle("Ruby hall");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewNewCardActivity.class));
            }
        });


        List<HallAPI> hall=gson.fromJson(json_text,new TypeToken<List<HallAPI>>(){}.getType());

        //


//        mProgressDialog = new ProgressDialog(this);


        //
//        simpleDialog = new SimpleDialog.Builder(this)
//                .setContent("The hall is loading...", 7)
//                // .showProgress must be set true if you want ProgressDialog
//                .showProgress(true)     // Default GIF is in the library (R.raw.simple_dialog_progress_default)
//                //.setProgressGIF(R.raw.simple_dialog_progress_default)
//                .setBtnCancelText("Cancel")
//                .setBtnCancelTextColor("#2861b0")
//                .show();
//                .build();

        //



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.left_side_of_hall:
                        Toast.makeText(BottomNavigation.this, "left_side_of_hall", Toast.LENGTH_SHORT).show();

                        mt = new MyTask();
                        mt.setJson("left_json");
                        mt.setJson(gson.toJson(hall.get(0)));
                        mt.execute();

                        break;
                    case R.id.center_side_of_hall:
                        Toast.makeText(BottomNavigation.this, "center_side_of_hall", Toast.LENGTH_SHORT).show();

                        mt = new MyTask();
//                        mt.setJson("center_json");
//                        mt.setJson(json_text);
                        mt.setJson(gson.toJson(hall.get(1)));
                        mt.execute();


                        break;
                    case R.id.right_side_of_hall:
                        Toast.makeText(BottomNavigation.this, "right_side_of_hall", Toast.LENGTH_SHORT).show();


//
//                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.testFragment, new HallTestFragment());
//                        ft.commit();

                        mt = new MyTask();
//                        mt.setJson("right_json");
                        mt.setJson(gson.toJson(hall.get(2)));
                        mt.execute();


                        break;

                }
                return true;
            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hall_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.submit:
                ChocoBar.builder().setActivity(BottomNavigation.this)
                        .setText("Success!")
                        .setDuration(ChocoBar.LENGTH_SHORT)
                        .build()
                        .show();
                TextView textView=findViewById(R.id.selectedPlacesTextView);
                Log.d("submit",textView.getText().toString());
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    class MyTask extends AsyncTask<Void, Void, Void> {


        String json;

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mProgressDialog.setMessage("Working ...");
//            mProgressDialog.show();
//            statusView.setStatus(iammert.com.library.Status.LOADING);
//            simpleDialog.show();
            llProgressBar.setVisibility(View.VISIBLE);
            // https://medium.com/@therajanmaurya/progress-bar-instead-progress-dialog-baa5d72c2860

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(2);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                HallTestFragment hallTestFragment = new HallTestFragment();
                Bundle bundle = new Bundle();
                bundle.putString("json", getJson());
                hallTestFragment.setArguments(bundle);

                ft.replace(R.id.testFragment, hallTestFragment);
                ft.commit();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            mProgressDialog.dismiss();
//            statusView.setStatus(iammert.com.library.Status.COMPLETE);
//            simpleDialog.dismiss();
            llProgressBar.setVisibility(View.GONE);
            // https://medium.com/@therajanmaurya/progress-bar-instead-progress-dialog-baa5d72c2860


        }
    }


}

