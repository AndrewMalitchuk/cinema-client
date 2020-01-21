package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cinema.client.R;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TicketActivity extends AppCompatActivity {


    @BindView(R.id.ticketLinearLayout)
    LinearLayout ticketLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        ButterKnife.bind(this);

        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(ticketLinearLayout)
                .setTransitionDuration(4000)
                .start();


        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


    }


    public void onQRCodeClick(View view){
        Intent intent=new Intent(this,QRZoomActivity.class);
        startActivity(intent);
    }
}
