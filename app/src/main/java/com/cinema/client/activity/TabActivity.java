package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;

import com.cinema.client.R;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.armcha.playtablayout.core.PlayTabLayout;
import io.armcha.playtablayout.core.TouchableTabLayout;

public class TabActivity extends AppCompatActivity {


    @BindView(R.id.playTabLayout)
    PlayTabLayout playTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        ButterKnife.bind(this);
        playTabLayout.setColors(new int[]{                R.color.colorAccent,
                R.color.about_github_color,
                R.color.about_facebook_color,
                R.color.about_twitter_color});



        TouchableTabLayout touchableTabLayout = playTabLayout.getTabLayout();

        touchableTabLayout.setSelectedTabIndicatorColor(R.color.white);
        touchableTabLayout.setSelectedTabIndicatorHeight(7);
        touchableTabLayout.setTabMode(TabLayout.MODE_FIXED);
        touchableTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        touchableTabLayout.setTabTextColors(R.color.white,R.color.whatsapp);


        touchableTabLayout.getTabAt(0).setIcon(R.drawable.about_icon_email);
        touchableTabLayout.getTabAt(1).setIcon(R.drawable.about_icon_email);
        touchableTabLayout.getTabAt(2).setIcon(R.drawable.about_icon_email);
        touchableTabLayout.getTabAt(3).setIcon(R.drawable.about_icon_email);
        touchableTabLayout.getTabAt(4).setIcon(R.drawable.about_icon_email);


    }
}
