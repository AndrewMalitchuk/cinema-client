package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinema.client.R;
import com.cinema.client.etc.SwipeCardAdapter;
import com.cinema.client.requests.entities.FilmAPI;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rw.loadingdialog.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import swipeable.com.layoutmanager.OnItemSwiped;
import swipeable.com.layoutmanager.SwipeableLayoutManager;
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback;
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper;

public class PosterActivity extends AppCompatActivity {

    private SwipeCardAdapter adapter;


    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @BindView(R.id.stop)
    LinearLayout stop;

    @BindView(R.id.imageView4)
    ImageView imageView4;

    @BindView(R.id.textView31)
    TextView textView31;

    @BindView(R.id.toolbar10)
    Toolbar toolbar;

    @BindView(R.id.frame)
    ConstraintLayout frame;


    LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        ButterKnife.bind(this);


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AboutCinemaActivity.class));
            }
        });

        //
        stop.setVisibility(View.GONE);
        imageView4.setVisibility(View.GONE);
        textView31.setVisibility(View.GONE);
        //

        //
        loadingView = new LoadingView.Builder(this)
                .setProgressColorResource(R.color.colorAccent)
                .setProgressStyle(LoadingView.ProgressStyle.CYCLIC)
                .attachTo(frame);
        loadingView.hide();

        //



        // Gradient
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(linearLayout)
                .setTransitionDuration(4000)
                .start();


        Intent i = getIntent();

        String genre = i.getStringExtra("genre");
        toolbar.setTitle(genre);

        String json = i.getStringExtra("json");
        Gson gson = new GsonBuilder().create();

        List<FilmAPI> videos = gson.fromJson(json, new TypeToken<List<FilmAPI>>() {
        }.getType());

//        adapter.setFilms(videos);
        adapter = new SwipeCardAdapter(videos, getApplicationContext(),loadingView);


        //
        adapter.setLoadingView(loadingView);
        //

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        SwipeableTouchHelperCallback swipeableTouchHelperCallback =
                new SwipeableTouchHelperCallback(new OnItemSwiped() {

                    @Override
                    public void onItemSwiped() {
                        adapter.removeTopItem();


                        if (adapter.getItemCount() == 0) {
                            stop.setVisibility(View.VISIBLE);
                            imageView4.setVisibility(View.VISIBLE);
                            textView31.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onItemSwipedLeft() {
                        Log.e("SWIPE", "LEFT");
                    }

                    @Override
                    public void onItemSwipedRight() {
                        Log.e("SWIPE", "RIGHT");
                    }

                    @Override
                    public void onItemSwipedUp() {
                        Log.e("SWIPE", "UP");
                    }


                    @Override
                    public void onItemSwipedDown() {
                        Log.e("SWIPE", "DOWN");
                    }
                }) {
                    @Override
                    public int getAllowedSwipeDirectionsMovementFlags(RecyclerView.ViewHolder viewHolder) {
                        return ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.DOWN;
                    }

                };
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeableTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new SwipeableLayoutManager().setAngle(10)
                .setAnimationDuratuion(450)
                .setMaxShowCount(3)
                .setScaleGap(0.1f)
                .setTransYGap(0));
//        recyclerView.setAdapter(adapter = new SwipeCardAdapter(videos,getApplicationContext()));


        String cinemaName = getIntent().getStringExtra("cinemaName");
        int cinemaId = getIntent().getIntExtra("cinemaId", -1);
        if (cinemaId != -1 && cinemaName != null) {
            recyclerView.setAdapter(adapter = new SwipeCardAdapter(videos, getApplicationContext(), cinemaName, cinemaId,loadingView));
        } else {

            recyclerView.setAdapter(adapter = new SwipeCardAdapter(videos, getApplicationContext(),loadingView));
        }


//        Button button = findViewById(R.id.swipe);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemTouchHelper.swipe(recyclerView.findViewHolderForAdapterPosition(0), ItemTouchHelper.DOWN);
//            }
//        });
    }
}