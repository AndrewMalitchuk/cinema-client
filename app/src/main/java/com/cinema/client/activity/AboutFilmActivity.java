package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.cinema.client.MainActivity;
import com.cinema.client.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skyhope.materialtagview.TagView;
import com.skyhope.materialtagview.enums.TagSeparator;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;

public class AboutFilmActivity extends AppCompatActivity {


    @BindView(R.id.mScrollView)
    ScrollView scrollView;

    @BindView(R.id.floatingActionButton2)
    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_film);

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


        //
        TagGroup mTagGroup = (TagGroup) findViewById(R.id.tag_group);
        mTagGroup.setTags(new String[]{"Tag1", "Tag2", "Tag3"});





    }
}
