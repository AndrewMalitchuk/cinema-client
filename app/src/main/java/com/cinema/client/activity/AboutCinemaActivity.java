package com.cinema.client.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;


import com.cinema.client.MainActivity;
import com.cinema.client.R;
import com.devs.readmoreoption.ReadMoreOption;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

public class AboutCinemaActivity extends AppCompatActivity {



    @BindView(R.id.textView4)
    TextView textView4;

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

//    private MapContainerView mapView;


//    private MapView mapView;

    MapView map = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_cinema);

        ButterKnife.bind(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
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


        IMapController mapController = map.getController();
        mapController.setZoom(16.5);
        GeoPoint startPoint = new GeoPoint(48.931572, 24.697925);
        mapController.setCenter(startPoint);


        //
        Marker marker=new Marker(map);

        marker.setPosition(new GeoPoint(48.931572, 24.697925));
        marker.setIcon(getResources().getDrawable(R.drawable.ic_ticket_black));
        marker.setTitle("lorem ipsum dolor sit amet");
        marker.showInfoWindow();
        map.getOverlays().add(marker);
        map.invalidate();


        final Context context = getApplicationContext();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        ScaleBarOverlay mScaleBarOverlay = new ScaleBarOverlay(map);
        mScaleBarOverlay.setCentred(true);
//play around with these about_film_menu to get the location on screen in the right place for your application
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        map.getOverlays().add(mScaleBarOverlay);




        //
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                Log.d("Date:",year+" "+month+" "+day);
                Intent intent = new Intent(AboutCinemaActivity.this,StatusActivity.class);
                startActivity(intent);
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {

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
        if(item.getItemId() == R.id.maps_direction) {
            Uri gmmIntentUri = Uri.parse("geo:48.931572, 24.697925");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        return true;
    }

    public void onImageClick(View view){
        Intent intent=new Intent(AboutCinemaActivity.this, ZoomImageActivity.class);
        startActivity(intent);
    }

    public void onFabClick(View view){
        Intent intent=new Intent(AboutCinemaActivity.this, PosterActivity.class);
        startActivity(intent);
    }

}
