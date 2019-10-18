package com.cinema.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cinema.client.activity.AboutActivity;
import com.cinema.client.activity.LoginActivity;
import com.cinema.client.activity.TicketActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent =new Intent(this, LoginActivity.class);
//        Intent intent =new Intent(this, AboutActivity.class);
//        Intent intent =new Intent(this, TicketActivity.class);
        startActivity(intent);

    }
}
