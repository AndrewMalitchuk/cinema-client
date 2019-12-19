package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cinema.client.R;
import com.cinema.client.etc.SwipeCardAdapter;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);

        ButterKnife.bind(this);


        // Gradient
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(linearLayout)
                .setTransitionDuration(4000)
                .start();


        adapter = new SwipeCardAdapter();

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        SwipeableTouchHelperCallback swipeableTouchHelperCallback =
                new SwipeableTouchHelperCallback(new OnItemSwiped() {

                    @Override
                    public void onItemSwiped() {
                        adapter.removeTopItem();
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
        recyclerView.setAdapter(adapter = new SwipeCardAdapter());




//        Button button = findViewById(R.id.swipe);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemTouchHelper.swipe(recyclerView.findViewHolderForAdapterPosition(0), ItemTouchHelper.DOWN);
//            }
//        });
    }
}