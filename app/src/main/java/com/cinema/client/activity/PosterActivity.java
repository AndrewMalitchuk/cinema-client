package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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


        // Gradient
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(linearLayout)
                .setTransitionDuration(4000)
                .start();




        Intent i = getIntent();

        String genre=i.getStringExtra("genre");
        toolbar.setTitle(genre);

        String json =i.getStringExtra("json");
        Log.d("json",json);
        Gson gson = new GsonBuilder().create();

        List<FilmAPI> videos = gson.fromJson(json, new TypeToken<List<FilmAPI>>(){}.getType());
//        Log.d("LIST",videos.get(0).toString());

//        adapter.setFilms(videos);
        adapter = new SwipeCardAdapter(videos,getApplicationContext());
        Log.d("ADPT",adapter.getFilms().size()+" "+adapter.getFilms().get(0).getTitle());




        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        SwipeableTouchHelperCallback swipeableTouchHelperCallback =
                new SwipeableTouchHelperCallback(new OnItemSwiped() {

                    @Override
                    public void onItemSwiped() {
                        adapter.removeTopItem();


                        Log.d("Count R",recyclerView.getChildCount()+"");
                        Log.d("Count A",adapter.getItemCount()+"");

                        if(adapter.getItemCount()==0){
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
                })
                {
                    @Override
                    public int getAllowedSwipeDirectionsMovementFlags(RecyclerView.ViewHolder viewHolder) {
                        return ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.DOWN;
                    }

                }

                ;
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeableTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setLayoutManager(new SwipeableLayoutManager().setAngle(10)
                .setAnimationDuratuion(450)
                .setMaxShowCount(3)
                .setScaleGap(0.1f)
                .setTransYGap(0));
        recyclerView.setAdapter(adapter = new SwipeCardAdapter(videos,getApplicationContext()));






//        Button button = findViewById(R.id.swipe);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemTouchHelper.swipe(recyclerView.findViewHolderForAdapterPosition(0), ItemTouchHelper.DOWN);
//            }
//        });
    }
}