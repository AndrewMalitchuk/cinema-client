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


    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.linLayout)
    LinearLayout linLayout;

    @BindView(R.id.imageView2)
    ImageView imageView2;

    @BindView(R.id.cv1)
            CardView cv1;

    @BindView(R.id.cv2)
    CardView cv2;

    @BindView(R.id.cv3)
    CardView cv3;


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

        imageView2.setElevation(20);
        cv1.setElevation(5);
//        cv2.setElevation(5);
//        cv3.setElevation(5);


        //
        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setTags(new String[]{"Tag1", "Tag2", "Tag3"});


        ratingBar.setNumStars(5);
        ratingBar.setRating(3.5f);
        ratingBar.isIndicator();


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
//play around with these values to get the location on screen in the right place for your application
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        map.getOverlays().add(mScaleBarOverlay);




        //
        ReadMoreOption readMoreOption = new ReadMoreOption.Builder(this)
                .textLength(3, ReadMoreOption.TYPE_LINE) // OR
                //.textLength(300, ReadMoreOption.TYPE_CHARACTER)
                .moreLabel("MORE")
                .lessLabel("LESS")
                .moreLabelColor(Color.RED)
                .lessLabelColor(Color.BLUE)
                .labelUnderLine(true)
                .expandAnimation(true)
                .build();

//        readMoreOption.addReadMoreTo(textView4, getString(R.string.long_desc));
        readMoreOption.addReadMoreTo(textView4, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Elementum tempus egestas sed sed risus pretium quam vulputate dignissim. Malesuada pellentesque elit eget gravida cum. Duis ultricies lacus sed turpis tincidunt id. Scelerisque eu ultrices vitae auctor eu augue ut lectus arcu. Mattis vulputate enim nulla aliquet porttitor lacus. Fermentum et sollicitudin ac orci phasellus egestas tellus rutrum tellus. Blandit volutpat maecenas volutpat blandit aliquam etiam erat. Facilisis sed odio morbi quis commodo odio aenean. Ridiculus mus mauris vitae ultricies leo. Eget lorem dolor sed viverra ipsum nunc aliquet bibendum enim. Ipsum dolor sit amet consectetur. Consectetur a erat nam at lectus urna. Aenean et tortor at risus viverra adipiscing at in tellus. Nibh praesent tristique magna sit amet purus gravida quis. Ornare lectus sit amet est placerat in egestas erat imperdiet. Morbi tincidunt ornare massa eget egestas purus viverra accumsan. Interdum velit euismod in pellentesque massa placerat.  Risus ultricies tristique nulla aliquet enim. At augue eget arcu dictum. Semper eget duis at tellus at urna. A cras semper auctor neque. Est pellentesque elit ullamcorper dignissim cras tincidunt lobortis. Leo urna molestie at elementum eu. Ut eu sem integer vitae justo eget magna fermentum. Id leo in vitae turpis. Ultricies lacus sed turpis tincidunt id aliquet risus. Aliquam ultrices sagittis orci a scelerisque. Tellus at urna condimentum mattis pellentesque id nibh tortor id. Sit amet facilisis magna etiam tempor orci. Sit amet luctus venenatis lectus magna fringilla. Ac ut consequat semper viverra nam libero. Est ullamcorper eget nulla facilisi etiam dignissim diam. Tempus quam pellentesque nec nam aliquam. Enim diam vulputate ut pharetra sit amet aliquam.  Integer quis auctor elit sed. Luctus venenatis lectus magna fringilla urna. Sit amet volutpat consequat mauris. Sed turpis tincidunt id aliquet risus feugiat. Eu feugiat pretium nibh ipsum consequat. Ultricies integer quis auctor elit sed vulputate mi sit amet. Non enim praesent elementum facilisis leo. Ut tortor pretium viverra suspendisse potenti nullam ac tortor. Purus non enim praesent elementum. Risus at ultrices mi tempus imperdiet nulla malesuada pellentesque. Sit amet mauris commodo quis imperdiet massa. Lectus magna fringilla urna porttitor rhoncus dolor. Orci a scelerisque purus semper eget. Elementum facilisis leo vel fringilla est ullamcorper eget nulla. Pharetra convallis posuere morbi leo urna molestie at. Nascetur ridiculus mus mauris vitae ultricies.\n");



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
}
