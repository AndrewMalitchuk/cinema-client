package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cinema.client.R;

public class TicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
    }


    public void onQRCodeClick(View view){
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
    }
}
