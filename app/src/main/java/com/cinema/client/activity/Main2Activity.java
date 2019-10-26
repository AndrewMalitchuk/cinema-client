package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.cinema.client.R;
import com.freegeek.android.materialbanner.MaterialBanner;
import com.freegeek.android.materialbanner.simple.SimpleBannerData;
import com.freegeek.android.materialbanner.simple.SimpleViewHolderCreator;
import com.freegeek.android.materialbanner.view.indicator.CirclePageIndicator;
import com.freegeek.android.materialbanner.view.indicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private static int[] images = {
            R.drawable.once_upon_a_time,
            R.drawable.drive_2011,
            R.drawable.pulp_fiction};


    private MaterialBanner<SimpleBannerData> materialBanner;
    private TextView textView;

    private CirclePageIndicator circlePageIndicator;
    private LinePageIndicator linePageIndicator;


    List<SimpleBannerData> list = new ArrayList<>();
    List<Integer> icons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        materialBanner = findViewById(R.id.material_banner);
        textView = findViewById(R.id.hometown);

        initIndicator();
        initData();

        materialBanner.setPages(new SimpleViewHolderCreator(), list)
                .setIndicator(circlePageIndicator);

        materialBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
                textView.setText("My hometown: page " + ++position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //iconPageIndicator request these icons
        materialBanner.getAdapter().setIcons(icons);

        materialBanner.setOnItemClickListener(new MaterialBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(Main2Activity.this, "click:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        if (materialBanner.isTurning()) {
            materialBanner.stopTurning();
            Toast.makeText(this, "stop turning", Toast.LENGTH_SHORT).show();
        } else {
            materialBanner.startTurning(3000);
            Toast.makeText(this, "start turning", Toast.LENGTH_SHORT).show();
        }
        materialBanner.setIndicatorInside(!materialBanner.isIndicatorInside());

    }

    private void initIndicator() {
        circlePageIndicator = new CirclePageIndicator(this);
        circlePageIndicator.setStrokeColor(R.color.colorLoginActivityText);
        circlePageIndicator.setFillColor(R.color.colorLoginActivityText);
        circlePageIndicator.setRadius(MaterialBanner.dip2Pix(this, 3));
        circlePageIndicator.setBetween(20);

        linePageIndicator = new LinePageIndicator(this);
        linePageIndicator.setUnselectedColor(Color.BLACK);
//        linePageIndicator.setSelectedColor(Color.YELLOW);

    }

    private void initData() {
        for (int i = 0; i < images.length; i++) {
            SimpleBannerData simpleBannerData = new SimpleBannerData();
            simpleBannerData.setTitle("Country road " + (i + 1));
            simpleBannerData.setResId(images[i]);
            list.add(simpleBannerData);

        }
    }


}
