package com.cinema.client.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
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
import com.cinema.client.etc.SitButton;
import com.cinema.client.fragments.HallTestFragment;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.AllHallAPI;
import com.cinema.client.requests.entities.HallAPI;
import com.cinema.client.requests.entities.HallCellAPI;
import com.cinema.client.requests.entities.TicketAPI;
import com.cinema.client.requests.entities.TokenAPI;
import com.droidbyme.dialoglib.DroidDialog;
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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomNavigation extends AppCompatActivity {


    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.toolbar4)
    Toolbar toolbar;

    @BindView(R.id.dummyTextView)
    TextView dummyTextView;

    MyTask mt;

    private APIInterface apiInterface;


    private List<String> selectedPlaces;


    @BindView(R.id.llProgressBar)
    View llProgressBar;


    private List<HallAPI> hall;

    private AllHallAPI currentHall;


    Gson gson = new Gson().newBuilder().create();


    public static final String ACCOUNT_PREF = "accountPref";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        ButterKnife.bind(this);

        //

        toolbar.setTitle("Loading...");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewNewCardActivity.class));
            }
        });

        //


        //


        //

        apiInterface = APIClient.getClient().create(APIInterface.class);

        int cinema_id = 9;

        Call<AllHallAPI> call = apiInterface.getHallByCinemaId(cinema_id);

        call.enqueue(new Callback<AllHallAPI>() {
            @Override
            public void onResponse(Call<AllHallAPI> call, Response<AllHallAPI> response) {

                if (response.isSuccessful()) {
                    hall = gson.fromJson(response.body().getHallJson(), new TypeToken<List<HallAPI>>() {
                    }.getType());

                    currentHall=response.body();

                    Log.d("NAME", response.body().getName());


                    toolbar.setTitle(response.body().getName());


                    Log.d("JSON", response.body().getHallJson());
                    Log.d("JSON", hall.get(0).getSector());
                    Log.d("JSON", hall.get(1).getSector());
                    Log.d("JSON", hall.get(2).getSector());


                }


            }

            @Override
            public void onFailure(Call<AllHallAPI> call, Throwable t) {

            }
        });


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


        //


        //


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hall_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.submit:
                ChocoBar.builder().setActivity(BottomNavigation.this)
                        .setText("Success!")
                        .setDuration(ChocoBar.LENGTH_SHORT)
                        .build()
                        .show();
                TextView textView = findViewById(R.id.selectedPlacesTextView);
                Log.d("submit", textView.getText().toString());


                // Ну я не знаю как сделать, SharedPref не работает, вооооооообще, да костыль, но если кто-то это читает, кроме меня -
                // - тогда скажи как сделать. Две сутки сижу с тем шердпред - ни чи го.
                // и вообще - это не костиль, а паллиатив

                String dummy = dummyTextView.getText().toString();

                if (dummy != null && dummy.length() != 0) {


                    Log.d("JSON", dummy);

                    Gson gson = new Gson().newBuilder().create();
                    List<HallCellAPI> list = gson.fromJson(dummy, new TypeToken<List<HallCellAPI>>() {
                    }.getType());
                    Log.d("JSON", list.size() + "");


                    DroidDialog dialog = new DroidDialog.Builder(BottomNavigation.this)
                            .icon(R.drawable.ic_ticket_black)
                            .title("Confirmation")
                            .content("Are you sure about buying this tickets?")
                            .cancelable(true, false)
                            .positiveButton("Yes, I'm sure", new DroidDialog.onPositiveListener() {
                                @Override
                                public void onPositive(Dialog droidDialog) {
                                    Toast.makeText(BottomNavigation.this, "Yes, I'm sure", Toast.LENGTH_SHORT).show();
                                    droidDialog.cancel();
                                    //
                                    List<Integer> leftFreeRemove=new ArrayList<>();
                                    List<Integer> centerFreeRemove=new ArrayList<>();
                                    List<Integer> rightFreeRemove=new ArrayList<>();
                                    for (HallCellAPI sitButton : list) {
                                        if (sitButton.getSector().equals("left")) {
                                            hall.get(0).getBought().add(sitButton);
                                            //
                                            leftFreeRemove.add(removePlace(hall.get(0).getFree(),sitButton));
                                            //
                                        } else if (sitButton.getSector().equals("center")) {
                                            hall.get(1).getBought().add(sitButton);
                                            //
                                            centerFreeRemove.add(removePlace(hall.get(1).getFree(),sitButton));
                                            //
                                        } else if (sitButton.getSector().equals("right")) {
                                            hall.get(2).getBought().add(sitButton);
                                            //
                                            rightFreeRemove.add(removePlace(hall.get(2).getFree(),sitButton));
                                            //
                                        }
                                    }

                                    // remove free
                                    for(Integer i:leftFreeRemove){
                                        hall.get(0).getFree().remove(i.intValue());
                                    }
                                    for(Integer i:centerFreeRemove){
                                        hall.get(1).getFree().remove(i.intValue());
                                    }
                                    for(Integer i:rightFreeRemove){
                                        hall.get(2).getFree().remove(i.intValue());
                                    }
                                    //


                                    // token
                                    sharedpreferences = getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
                                    if (sharedpreferences != null) {
                                        String login = sharedpreferences.getString("login", null);
                                        String password = sharedpreferences.getString("password", null);
                                        int userId = sharedpreferences.getInt("userId", -1);


                                        RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"),
                                                password);

                                        RequestBody login_ = RequestBody.create(MediaType.parse("text/plain"),
                                                login);

                                        Observable<TokenAPI> tokenRx = apiInterface.refreshTokenRx(login_, password_);

                                        tokenRx.subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .map(result -> result)
                                                .subscribe(this::onToken);


                                    }
                                    //






                                    //
                                }

                                private void onToken(TokenAPI tokenAPI) {

                                    RequestBody name_ = RequestBody.create(MediaType.parse("text/plain"),
                                            currentHall.getName());

                                    RequestBody hall_json_ = RequestBody.create(MediaType.parse("text/plain"),
                                            gson.toJson(hall));

                                    RequestBody cinema_id_ = RequestBody.create(MediaType.parse("text/plain"),
                                            currentHall.getCinemaId()+"");





                                    Call<AllHallAPI> call=apiInterface.updateHallByHallId(
                                            currentHall.getId(),
                                            name_,
                                            hall_json_,
                                            cinema_id_,
                                            "Bearer "+tokenAPI.getAccess()

                                    );

                                    call.enqueue(new Callback<AllHallAPI>() {
                                        @Override
                                        public void onResponse(Call<AllHallAPI> call, Response<AllHallAPI> response) {
                                            if(response.isSuccessful()){
                                                Log.d("!!!","Yeah");
                                                ChocoBar.builder().setActivity(BottomNavigation.this)
                                                        .setText("Success!")
                                                        .setDuration(ChocoBar.LENGTH_SHORT)
                                                        .green()
                                                        .show();

                                                getIntent().putExtra("hallPlace", textView.getText().toString());
                                                setResult(RESULT_OK, getIntent());
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<AllHallAPI> call, Throwable t) {

                                        }
                                    });


                                }
                            })
                            .negativeButton("No, it was a mistake", new DroidDialog.onNegativeListener() {
                                @Override
                                public void onNegative(Dialog droidDialog) {
                                    Toast.makeText(BottomNavigation.this, "No, it was a mistake", Toast.LENGTH_SHORT).show();
                                    //

                                    //
                                }
                            })
                            .color(ContextCompat.getColor(BottomNavigation.this, R.color.colorAccent), ContextCompat.getColor(BottomNavigation.this, R.color.white),
                                    ContextCompat.getColor(BottomNavigation.this, R.color.colorAccent))
                            .show();


//                    Log.d("JSON 0", hall.get(0).getBought().toString());
//                    Log.d("JSON 1", hall.get(1).getBought().toString());
//                    Log.d("JSON 2", hall.get(2).getBought().toString());


                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public int removePlace(List<HallCellAPI> list, HallCellAPI place){

//        for(HallCellAPI hallCellAPI:list){
        for(int i=0;i<list.size();i++){

            if(list.get(i).getCol()==place.getCol()&&list.get(i).getRow()==place.getRow()){
//                list.remove(hallCellAPI);
                Log.d("removePlace",i+"");
                return i;
            }
        }
        return -1;

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

