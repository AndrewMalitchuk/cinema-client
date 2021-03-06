package com.cinema.client.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.cinema.client.R;
import com.cinema.client.fragments.HallTestFragment;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.AllHallAPI;
import com.cinema.client.requests.entities.HallAPI;
import com.cinema.client.requests.entities.HallCellAPI;
import com.cinema.client.requests.entities.TokenAPI;
import com.droidbyme.dialoglib.DroidDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pd.chocobar.ChocoBar;
import com.rw.loadingdialog.LoadingView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HallNavigationActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.toolbar4)
    Toolbar toolbar;

    @BindView(R.id.dummyTextView)
    TextView dummyTextView;

    MyTask mt;

    private APIInterface apiInterface;

    @BindView(R.id.llProgressBar)
    View llProgressBar;

    @BindView(R.id.frame)
    FrameLayout frame;

    private List<HallAPI> hall;

    private AllHallAPI currentHall;

    LoadingView loadingView;

    int easterEggClick = 0;

    Gson gson = new Gson().newBuilder().create();

    public static final String ACCOUNT_PREF = "accountPref";

    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_navigation);
        ButterKnife.bind(this);
        toolbar.setTitle(getResources().getString(R.string.loadingMessage).toString());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
        loadingView = new LoadingView.Builder(this)
                .setProgressColorResource(R.color.colorAccent)
                .setProgressStyle(LoadingView.ProgressStyle.CYCLIC)
                .attachTo(frame);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        int cinema_id = getIntent().getIntExtra("id", -1);
        Observable<AllHallAPI> callRx = apiInterface.getHallByIdRx(cinema_id);
        callRx.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .doOnComplete(() -> {
                    mt = new MyTask();
                    mt.setJson("left_json");
                    mt.setJson(gson.toJson(hall.get(0)));
                    mt.execute();
                })
                .subscribe(allHallAPI -> {
                    hall = gson.fromJson(allHallAPI.getHallJson(), new TypeToken<List<HallAPI>>() {
                    }.getType());
                    currentHall = allHallAPI;
                    toolbar.setTitle(allHallAPI.getName());
                });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.left_side_of_hall:
                        mt = new MyTask();
                        mt.setJson("left_json");
                        mt.setJson(gson.toJson(hall.get(0)));
                        mt.execute();
                        break;
                    case R.id.center_side_of_hall:
                        mt = new MyTask();
                        mt.setJson(gson.toJson(hall.get(1)));
                        mt.execute();
                        break;
                    case R.id.right_side_of_hall:
                        mt = new MyTask();
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
        getMenuInflater().inflate(R.menu.hall_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                final Dialog customDialog = new Dialog(HallNavigationActivity.this);
                customDialog.setContentView(R.layout.hall_help_dialog);
                customDialog.setTitle("Title...");
                Button dialogButton = (Button) customDialog.findViewById(R.id.button6);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });
                Button easterEggButton = (Button) customDialog.findViewById(R.id.button2);
                easterEggButton.setOnClickListener(e -> {
                    easterEggClick++;
                    if (easterEggClick == 10) {
                        Toast.makeText(this, "10 clicks. It might be easter egg, isn't it?", Toast.LENGTH_SHORT).show();
                    }
                });
                customDialog.show();
                break;
            case R.id.submit:
                ChocoBar.builder().setActivity(HallNavigationActivity.this)
                        .setText("Success!")
                        .setDuration(ChocoBar.LENGTH_SHORT)
                        .build()
                        .show();
                TextView textView = findViewById(R.id.selectedPlacesTextView);
                // Ну я не знаю как сделать, SharedPref не работает, вооооооообще, да костыль, но если кто-то это читает, кроме меня -
                // - тогда скажи как сделать. Две сутки сижу с тем шердпред - ни чи го.
                // и вообще - это не костиль, а паллиатив
                String dummy = dummyTextView.getText().toString();
                if (dummy != null && dummy.length() != 0) {
                    Gson gson = new Gson().newBuilder().create();
                    List<HallCellAPI> list = gson.fromJson(dummy, new TypeToken<List<HallCellAPI>>() {
                    }.getType());
                    DroidDialog dialog = new DroidDialog.Builder(HallNavigationActivity.this)
                            .icon(R.drawable.ic_ticket_black)
                            .title("Confirmation")
                            .content("Are you sure about buying this tickets?")
                            .cancelable(true, false)
                            .positiveButton("Yes, I'm sure", new DroidDialog.onPositiveListener() {
                                @Override
                                public void onPositive(Dialog droidDialog) {
                                    droidDialog.cancel();
                                    List<Integer> leftFreeRemove = new ArrayList<>();
                                    List<Integer> centerFreeRemove = new ArrayList<>();
                                    List<Integer> rightFreeRemove = new ArrayList<>();
                                    for (HallCellAPI sitButton : list) {
                                        if (sitButton.getSector().equals("left")) {
                                            hall.get(0).getBought().add(sitButton);
                                            leftFreeRemove.add(removePlace(hall.get(0).getFree(), sitButton));
                                        } else if (sitButton.getSector().equals("center")) {
                                            hall.get(1).getBought().add(sitButton);
                                            centerFreeRemove.add(removePlace(hall.get(1).getFree(), sitButton));
                                        } else if (sitButton.getSector().equals("right")) {
                                            hall.get(2).getBought().add(sitButton);
                                            rightFreeRemove.add(removePlace(hall.get(2).getFree(), sitButton));
                                        }
                                    }
                                    for (Integer i : leftFreeRemove) {
                                        hall.get(0).getFree().remove(i.intValue());
                                    }
                                    for (Integer i : centerFreeRemove) {
                                        hall.get(1).getFree().remove(i.intValue());
                                    }
                                    for (Integer i : rightFreeRemove) {
                                        hall.get(2).getFree().remove(i.intValue());
                                    }
                                    sharedpreferences = getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
                                    if (sharedpreferences != null) {
                                        String login = sharedpreferences.getString("login", null);
                                        String password = sharedpreferences.getString("password", null);
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
                                }

                                private void onToken(TokenAPI tokenAPI) {
                                    getIntent().putExtra("hallPlace", textView.getText().toString());
                                    setResult(RESULT_OK, getIntent());
                                    finish();
                                    RequestBody name_ = RequestBody.create(MediaType.parse("text/plain"),
                                            currentHall.getName());
                                    RequestBody hall_json_ = RequestBody.create(MediaType.parse("text/plain"),
                                            gson.toJson(hall));
                                    RequestBody cinema_id_ = RequestBody.create(MediaType.parse("text/plain"),
                                            currentHall.getCinemaId() + "");
                                    Call<AllHallAPI> call = apiInterface.updateHallByHallId(
                                            currentHall.getId(),
                                            name_,
                                            hall_json_,
                                            cinema_id_,
                                            "Bearer " + tokenAPI.getAccess()
                                    );
                                    call.enqueue(new Callback<AllHallAPI>() {

                                        @Override
                                        public void onResponse(Call<AllHallAPI> call, Response<AllHallAPI> response) {
                                            if (response.isSuccessful()) {
                                                ChocoBar.builder().setActivity(HallNavigationActivity.this)
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
                                            startActivity(new Intent(HallNavigationActivity.this, ErrorActivity.class));

                                        }

                                    });
                                }
                            })
                            .negativeButton("No, it was a mistake", new DroidDialog.onNegativeListener() {

                                @Override
                                public void onNegative(Dialog droidDialog) {

                                }

                            })
                            .color(ContextCompat.getColor(
                                    HallNavigationActivity.this,
                                    R.color.colorAccent),
                                    ContextCompat.getColor(
                                            HallNavigationActivity.this,
                                            R.color.white),
                                    ContextCompat.getColor(
                                            HallNavigationActivity.this,
                                            R.color.colorAccent))
                            .show();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Remove selected places from canvas
     *
     * @param list
     * @param place
     * @return
     */
    public int removePlace(List<HallCellAPI> list, HallCellAPI place) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getCol() == place.getCol() && list.get(i).getRow() == place.getRow()) {
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
            loadingView.show();
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
                Intent intent = new Intent(HallNavigationActivity.this, ErrorActivity.class);
                intent.putExtra("isAppError", true);
                startActivity(intent);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            loadingView.hide();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

