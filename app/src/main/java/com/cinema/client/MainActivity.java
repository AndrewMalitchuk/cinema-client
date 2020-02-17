package com.cinema.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cinema.client.activity.AboutCinemaActivity;
import com.cinema.client.activity.AboutDeveloperActivity;
import com.cinema.client.activity.AboutFilmActivity;
import com.cinema.client.activity.BottomNavigation;
import com.cinema.client.activity.ErrorActivity;
import com.cinema.client.activity.HallActivity;
import com.cinema.client.activity.LoginActivity;
import com.cinema.client.activity.Main2Activity;
import com.cinema.client.activity.Main3Activity;
import com.cinema.client.activity.MyTicketsActivity;
import com.cinema.client.activity.NewNewCardActivity;
import com.cinema.client.activity.PosterActivity;
import com.cinema.client.activity.QRZoomActivity;
import com.cinema.client.activity.SignUpActivity;
import com.cinema.client.activity.SplashActivity;
import com.cinema.client.activity.StartupActivity;
import com.cinema.client.activity.StatusActivity;
import com.cinema.client.activity.StoriesActivity;
import com.cinema.client.activity.TicketActivity;
import com.cinema.client.activity.WhatsNewActivity;
import com.cinema.client.activity.ZoomImageActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;


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

        // XXX !!!
        // https://github.com/firebase/quickstart-android/tree/bf928f5b7385637bf14fd91505429322951d3914/messaging

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = "fcm_default_channel";
            String channelName = "Weather";
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("MAIN ACTIVITY", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("MAIN ACTIVITY", token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });


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
        Intent intent =new Intent(this, Main3Activity.class);
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
        intent.putExtra("isNetworkError",true);
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
