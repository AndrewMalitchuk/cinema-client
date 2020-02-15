package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.cinema.client.R;
import com.medialablk.easygifview.EasyGifView;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);


        EasyGifView easyGifView = (EasyGifView) findViewById(R.id.easyGifView);
        easyGifView.setGifFromResource(R.drawable.cat_fail_0); //Your own GIF file from Resources


        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    public void onUnderstandButtonClick(View view){
        Intent intent=new Intent(this,Main3Activity.class);
        startActivity(intent);
    }

}
