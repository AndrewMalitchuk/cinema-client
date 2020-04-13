package com.cinema.client.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cinema.client.R;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.CinemaAPI;
import com.cinema.client.requests.entities.FilmAPI;
import com.cinema.client.requests.entities.TimelineAPI;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.liangfeizc.avatarview.AvatarView;
import com.pd.chocobar.ChocoBar;
import com.rw.loadingdialog.LoadingView;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import io.reactivex.Observable;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutCinemaActivity extends AppCompatActivity {


    @BindView(R.id.mScrollView)
    ScrollView scrollView;

    @BindView(R.id.linLayout)
    LinearLayout linLayout;

    @BindView(R.id.cv1)
    CardView cv1;

    @BindView(R.id.cv2)
    CardView cv2;

    @BindView(R.id.cv3)
    CardView cv3;


    @BindView(R.id.datePickerTimeline)
    DatePickerTimeline datePickerTimeline;


    @BindView(R.id.cinemaPictureCinemaActivityAvatarView)
    AvatarView cinemaPictureCinemaActivityAvatarView;

//    @BindView(R.id.cinemaNameCinemaActivityTextView)
//    TextView cinemaNameCinemaActivityTextView;


    @BindView(R.id.cinemaNameBigCinemaActivityTextView)
    TextView cinemaNameBigCinemaActivityTextView;

    @BindView(R.id.cinemaLocationCinemaActivityTextView)
    TextView cinemaLocationCinemaActivityTextView;

    @BindView(R.id.telephoneAboutCinemaTextView)
    TextView telephoneAboutCinemaTextView;


    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

//    @BindView(R.id.imageView5)
//    BlurImageView blurImageView;

    @BindView(R.id.imageView5)
    ImageView blurImageView;

//    private MapContainerView mapView;

    @BindView(R.id.relativeLayout2)
    ConstraintLayout relativeLayout2;

    LoadingView loadingView;


//    private MapView mapView;

    MapView map;
    private Marker marker;

    private APIInterface apiInterface;
    private CinemaAPI currentCinema;


    public static final String FAVOURITE_CINEMAS_PREF = "favourite_cinema_pref";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_cinema);

        ButterKnife.bind(this);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        myToolbar.setTitle("Loading...");
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //
                loadingView = new LoadingView.Builder(this)
                .setProgressColorResource(R.color.colorAccent)
                .setProgressStyle(LoadingView.ProgressStyle.CYCLIC)
                .attachTo(relativeLayout2);

        //


        cv1.setElevation(5);
//        cv2.setElevation(5);
//        cv3.setElevation(5);


        //

//        blurImageView.setBlur(10);


        //

        sharedpreferences = getSharedPreferences(FAVOURITE_CINEMAS_PREF, Context.MODE_PRIVATE);


        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(linLayout)
                .setTransitionDuration(4000)
                .start();


        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));


        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(false);
        map.setMultiTouchControls(true);

        marker = new Marker(map);

        //


        LocalDateTime date;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDateTime.now();
            datePickerTimeline.setInitialDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());

        }

        //
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                Intent intent = new Intent(AboutCinemaActivity.this, StatusActivity.class);
                intent.putExtra("cinemaName",currentCinema.getName() );

                Date date = new Date();
                date.setYear(year);
                date.setMonth(month);
                date.setDate(day);
                Toast.makeText(AboutCinemaActivity.this, ""+date.toString(), Toast.LENGTH_SHORT).show();



//                intent.putExtra("selectedDate",date.getTime());
                intent.putExtra("selectedDate",year+"-"+(month+1)+"-"+day);
                intent.putExtra("cinemaId",currentCinema.getId());
                intent.putExtra("isFilmTimeline",false);


                startActivity(intent);
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {

            }
        });


        //

        int id = getIntent().getIntExtra("cinemaId", -1);
        if (id == -1) {
            id = 9;
        }


        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<CinemaAPI> call = apiInterface.getCinemaById(id);
        call.enqueue(new Callback<CinemaAPI>() {
            @Override
            public void onResponse(Call<CinemaAPI> call, Response<CinemaAPI> response) {
                currentCinema = response.body();
                setContent(currentCinema);

            }

            @Override
            public void onFailure(Call<CinemaAPI> call, Throwable t) {

                call.cancel();
                Intent intent = new Intent(AboutCinemaActivity.this, ErrorActivity.class);
                intent.putExtra("isNetworkError",true);
                startActivity(intent);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cinema_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.maps_direction) {
            Uri gmmIntentUri = Uri.parse("geo:" + currentCinema.getGeoLat() + ", " + currentCinema.getGeoLon());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } else if (item.getItemId() == R.id.pin_action) {

            //
            SharedPreferences.Editor editor = sharedpreferences.edit();

            String json = sharedpreferences.getString("fav_json", null);

            Gson gson = new GsonBuilder().create();

            List<Integer> list;
            if (json == null) {
                list = new ArrayList<>();
            } else {
                list = gson.fromJson(json, new TypeToken<List<Integer>>() {
                }.getType());
            }

            if (list.contains(currentCinema.getId()) == false) {
                list.add(currentCinema.getId());
            }

            editor.remove("fav_json");
            editor.putString("fav_json", gson.toJson(list));
            editor.commit();

            //
            ChocoBar.builder().setActivity(AboutCinemaActivity.this)
                    .setText("Pin to favourite")
                    .setDuration(ChocoBar.LENGTH_SHORT)
                    .green()
                    .show();

        }
        return true;
    }

    public void onImageClick(View view) {
        Intent intent = new Intent(AboutCinemaActivity.this, ZoomImageActivity.class);
        intent.putExtra("url", currentCinema.getPicUrl());
        startActivity(intent);
    }

    public void onFabClick(View view) {


        loadingView.show();

        Observable<List<TimelineAPI>> callRx=apiInterface.getTimelineByCinemaIdRx(currentCinema.getId());

        callRx.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .doOnComplete(() -> loadingView.hide())
                .subscribe(this::onTimelineSuccess);








    }

    private void onTimelineSuccess(List<TimelineAPI> result) {



        try {





            List<FilmAPI> films = new ArrayList<>();

            Set<Integer> uniqueFilms = new HashSet<>();

            for (TimelineAPI timeline : result) {


                Call<FilmAPI> film = apiInterface.getFilmById(timeline.getFilmId());

                uniqueFilms.add(film.execute().body().getId());


            }


            List<FilmAPI> res = new ArrayList<>();
            for (Integer i : uniqueFilms) {

                res.add(apiInterface.getFilmById(i).execute().body());

            }


            Gson gson = new GsonBuilder().create();
            JsonArray myCustomArray = gson.toJsonTree(res).getAsJsonArray();
            JsonObject jsonObject = new JsonObject();

            Intent intent = new Intent(AboutCinemaActivity.this, PosterActivity.class);
            intent.putExtra("json", myCustomArray.toString());
            intent.putExtra("genre", currentCinema.getName());
            //
            intent.putExtra("cinemaId",currentCinema.getId());
            intent.putExtra("cinemaName",currentCinema.getName());
            //
            loadingView.hide();
            startActivity(intent);


        } catch (IOException e) {
            e.printStackTrace();
            Intent intent=new Intent(AboutCinemaActivity.this, ErrorActivity.class);
            intent.putExtra("isAppError",true);
            startActivity(intent);
        }

    }


    public void onPhoneClick(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + currentCinema.getTelephone()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        startActivity(intent);
    }

    public void setContent(CinemaAPI content) {

        Glide.with(this).load(APIClient.HOST + content.getPicUrl()).into(cinemaPictureCinemaActivityAvatarView);
//        cinemaNameCinemaActivityTextView.setText(content.getName());
        cinemaNameBigCinemaActivityTextView.setText(content.getName());
        myToolbar.setTitle(content.getName());
        cinemaLocationCinemaActivityTextView.setText(content.getAddress());
        telephoneAboutCinemaTextView.setText(content.getTelephone());
        SpannableString spannableString = new SpannableString(content.getTelephone());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        telephoneAboutCinemaTextView.setText(spannableString);
        telephoneAboutCinemaTextView.setOnClickListener(this::onPhoneClick);

        //
//        Glide.with(this).load(APIClient.HOST + content.getPicUrl()).into(blurImageView);
//        blurImageView.setBlur(25);
//        blurImageView.setBlurImageByUrl(APIClient.HOST + content.getPicUrl());
//
//        blurImageView.setBlurFactor(20);


//        Glide
//                .with(this)
//                .load(APIClient.HOST + content.getPicUrl())
//                .into(blurImageView);

//        BlurImage.withContext(AboutCinemaActivity.this)
//                .blurFromUri(APIClient.HOST + content.getPicUrl())
//                .into(blurImageView);



//        blurImageView.setImageResource();

//        Glide.with(this)
//                .load(APIClient.HOST + content.getPicUrl())
//                .apply(RequestOptions.bitmapTransform(BlurTransformation(25,3)))
//                .into(blurImageView);

        Glide.with(this)
                .load(APIClient.HOST + content.getPicUrl())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(blurImageView);
        //




        marker.setPosition(new GeoPoint(content.getGeoLat(), content.getGeoLon()));
        marker.setIcon(getResources().getDrawable(R.drawable.ic_ticket_black));
        marker.setTitle(content.getName());
        marker.showInfoWindow();
        map.getOverlays().add(marker);
        map.invalidate();


        final Context context = getApplicationContext();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        ScaleBarOverlay mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        map.getOverlays().add(mScaleBarOverlay);


        IMapController mapController = map.getController();
        mapController.setZoom(19);
        GeoPoint startPoint = new GeoPoint(content.getGeoLat(), content.getGeoLon());
        mapController.setCenter(startPoint);
        //


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
