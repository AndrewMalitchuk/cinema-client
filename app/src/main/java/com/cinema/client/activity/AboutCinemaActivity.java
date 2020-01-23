package com.cinema.client.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.cinema.client.MainActivity;
import com.cinema.client.R;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.CinemaAPI;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.liangfeizc.avatarview.AvatarView;
import com.pd.chocobar.ChocoBar;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @BindView(R.id.cinemaNameCinemaActivityTextView)
    TextView cinemaNameCinemaActivityTextView;


    @BindView(R.id.cinemaNameBigCinemaActivityTextView)
    TextView cinemaNameBigCinemaActivityTextView;

    @BindView(R.id.cinemaLocationCinemaActivityTextView)
    TextView cinemaLocationCinemaActivityTextView;

    @BindView(R.id.telephoneAboutCinemaTextView)
    TextView telephoneAboutCinemaTextView;


    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

//    private MapContainerView mapView;


//    private MapView mapView;

    MapView map;
    private Marker marker;

    private APIInterface apiInterface;
    private CinemaAPI currentCinema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_cinema);

        ButterKnife.bind(this);

        myToolbar.setTitle("Loading...");
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        cv1.setElevation(5);
//        cv2.setElevation(5);
//        cv3.setElevation(5);


        //

//        blurImageView.setBlur(10);


        //


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


        //
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                Log.d("Date:", year + " " + month + " " + day);
                Intent intent = new Intent(AboutCinemaActivity.this, StatusActivity.class);
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
                Log.d("CINEMA", currentCinema.toString());
                setContent(currentCinema);

            }

            @Override
            public void onFailure(Call<CinemaAPI> call, Throwable t) {
                Log.d("#!","AboutCinemaActivity");

                call.cancel();
                Intent intent = new Intent(AboutCinemaActivity.this, ErrorActivity.class);
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
        startActivity(intent);
    }

    public void onFabClick(View view) {
        Intent intent = new Intent(AboutCinemaActivity.this, PosterActivity.class);
        startActivity(intent);
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
        cinemaNameCinemaActivityTextView.setText(content.getName());
        cinemaNameBigCinemaActivityTextView.setText(content.getName());
        myToolbar.setTitle(content.getName());
        cinemaLocationCinemaActivityTextView.setText(content.getAddress());
        telephoneAboutCinemaTextView.setText(content.getTelephone());
        SpannableString spannableString = new SpannableString(content.getTelephone());
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        telephoneAboutCinemaTextView.setText(spannableString);
        telephoneAboutCinemaTextView.setOnClickListener(this::onPhoneClick);

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
        mapController.setZoom(16.5);
        GeoPoint startPoint = new GeoPoint(content.getGeoLat(), content.getGeoLon());
        mapController.setCenter(startPoint);
        //


    }

}
