package com.cinema.client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cinema.client.activity.AboutActivity;
import com.cinema.client.activity.AboutCinema2;
import com.cinema.client.activity.AboutCinemaActivity;
import com.cinema.client.activity.AboutDeveloperActivity;
import com.cinema.client.activity.AboutFilmActivity;
import com.cinema.client.activity.BottomNavigation;
import com.cinema.client.activity.CardActivity;
import com.cinema.client.activity.ErrorActivity;
import com.cinema.client.activity.HallActivity;
import com.cinema.client.activity.LoginActivity;
import com.cinema.client.activity.Main2Activity;
import com.cinema.client.activity.MyTicketsActivity;
import com.cinema.client.activity.NewCardActivity;
import com.cinema.client.activity.NewNewCardActivity;
import com.cinema.client.activity.PosterActivity;
import com.cinema.client.activity.QRZoomActivity;
import com.cinema.client.activity.SignUpActivity;
import com.cinema.client.activity.SplashActivity;
import com.cinema.client.activity.StartupActivity;
import com.cinema.client.activity.StatusActivity;
import com.cinema.client.activity.StoriesActivity;
import com.cinema.client.activity.TabActivity;
import com.cinema.client.activity.TicketActivity;
import com.cinema.client.activity.TimelineActivity;
import com.cinema.client.activity.WhatsNewActivity;
import com.cinema.client.activity.ZoomImageActivity;
import com.cinema.client.etc.MyTickets;
import com.devs.acr.AutoErrorReporter;

import java.sql.Time;


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
        Intent intent =new Intent(this, NewNewCardActivity.class);
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

    public void zoom(View view){
        Intent intent =new Intent(this, ZoomImageActivity.class);
        startActivity(intent);

    }

    public void zoomQr(View view){
        Intent intent =new Intent(this, QRZoomActivity.class);
        startActivity(intent);

    }

    public void mainActivity(View view){
        Intent intent =new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    public void splashActivity(View view){
        Intent intent =new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

    public void statusActivity(View view){
        Intent intent =new Intent(this, StatusActivity.class);
        startActivity(intent);
    }

    public void whatsNewActivity(View view){
        Intent intent =new Intent(this, WhatsNewActivity.class);
        startActivity(intent);
    }

    public void errorActivity(View view){
        Intent intent =new Intent(this, ErrorActivity.class);
        startActivity(intent);
    }

    public void aboutCinemaActivity(View view){
        Intent intent =new Intent(this, AboutCinemaActivity.class);
        startActivity(intent);
    }

    public void timelineActivity(View view){
        Intent intent =new Intent(this, StoriesActivity.class);
        startActivity(intent);
    }

    public void aboutCinema2(View view){
        Intent intent =new Intent(this, PosterActivity.class);
        startActivity(intent);
    }

    public void myTickets(View view){
        Intent intent =new Intent(this, MyTicketsActivity.class);
        startActivity(intent);
    }


}
