package com.cinema.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cinema.client.activity.AboutActivity;
import com.cinema.client.activity.AboutDeveloperActivity;
import com.cinema.client.activity.AboutFilmActivity;
import com.cinema.client.activity.BottomNavigation;
import com.cinema.client.activity.CardActivity;
import com.cinema.client.activity.HallActivity;
import com.cinema.client.activity.LoginActivity;
import com.cinema.client.activity.NewCardActivity;
import com.cinema.client.activity.SignUpActivity;
import com.cinema.client.activity.StartupActivity;
import com.cinema.client.activity.TabActivity;
import com.cinema.client.activity.TicketActivity;
import com.devs.acr.AutoErrorReporter;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        AutoErrorReporter.get(getApplication())
//                .setEmailAddresses("yourdeveloper@gmail.com")
//                .setEmailSubject("Auto Crash Report")
//                .start();

//        Intent intent =new Intent(this, LoginActivity.class);
//        Intent intent =new Intent(this, AboutActivity.class);
//        Intent intent =new Intent(this, TicketActivity.class);
//        startActivity(intent);


    }

    public void login(View view){
        Intent intent =new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void signup(View view){
        Intent intent =new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void ticket(View view){
        Intent intent =new Intent(this, TicketActivity.class);
        startActivity(intent);
    }

    public void crash(View view){
//        Intent intent =new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        float x = 10/0;


    }

    public void aboutFilm(View view){
        Intent intent =new Intent(this, AboutFilmActivity.class);
        startActivity(intent);
    }

    public void card(View view){
        Intent intent =new Intent(this, NewCardActivity.class);
        startActivity(intent);
    }

    public void startup(View view){
        Intent intent =new Intent(this, StartupActivity.class);
        startActivity(intent);
    }

    public void table(View view){
        Intent intent =new Intent(this, HallActivity.class);
        startActivity(intent);
    }

    public void bottom(View view){
        Intent intent =new Intent(this, BottomNavigation.class);
        startActivity(intent);
    }

    public void developer(View view){
        Intent intent =new Intent(this, AboutDeveloperActivity.class);
        startActivity(intent);
    }

    public void permisionShower(View view){
        Intent intent =new Intent(this, TabActivity.class);
        startActivity(intent);

    }



}
