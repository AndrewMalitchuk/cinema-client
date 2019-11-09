package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brouding.simpledialog.SimpleDialog;
import com.cinema.client.R;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.markdownview.Config;
import es.dmoral.markdownview.MarkdownView;


public class WhatsNewActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_new);






        new BottomDialog.Builder(this)
                .setTitle("What's new?")
                .setContent("Hello, dude!\nWant to see our new features?")
                .setPositiveText("Yeah, of course")
                .setPositiveBackgroundColorResource(R.color.colorPrimary)
                .setPositiveTextColorResource(android.R.color.white)
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        Log.d("BottomDialogs", "Do something!");


                        MarkdownView markdownView = new MarkdownView(WhatsNewActivity.this);
                        markdownView.loadFromText("# Hello!\n ## Hello!");


                        Config defaultConfig = Config.getDefaultConfig();

                        defaultConfig.setDefaultMargin(25);

                        markdownView.setCurrentConfig(defaultConfig);


                        new SimpleDialog.Builder(WhatsNewActivity.this)
                                .setTitle("New feautures:")
                                .setCustomView(markdownView)
                                .setBtnConfirmText("Check!")
                                .setBtnConfirmTextSizeDp(16)
                                .setBtnConfirmTextColor("#1fd1ab")
                                .show();

                    }
                })
                .setNegativeText("Life is too short for this, thanks")
                .setNegativeTextColorResource(R.color.colorAccent)
                .onNegative(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {
                        Log.d("BottomDialogs", "Do something!");
                    }
                })
                .setCancelable(false)
                .show();


    }






}
