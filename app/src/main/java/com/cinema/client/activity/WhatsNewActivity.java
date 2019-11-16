package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
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
import android.widget.Toast;

import com.brouding.simpledialog.SimpleDialog;
import com.cinema.client.MainActivity;
import com.cinema.client.R;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.nonzeroapps.whatisnewdialog.NewItemDialog;
import com.nonzeroapps.whatisnewdialog.object.NewFeatureItem;

import java.util.ArrayList;

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


                        ArrayList<NewFeatureItem> arrayList = new ArrayList<>();

                        NewFeatureItem newFeatureItem = new NewFeatureItem();
                        newFeatureItem.setFeatureDesc("From now on, you can search all things with keys. For searching please go to ");
                        newFeatureItem.setFeatureTitle("Searching");
                        newFeatureItem.setImageResource(R.drawable.pulp_fiction);
                        arrayList.add(newFeatureItem);

                        NewFeatureItem newFeatureItem2 = new NewFeatureItem();
                        newFeatureItem2.setFeatureTitle("Feature 2");
                        newFeatureItem2.setFeatureDesc("You waited long for this feature, we know that!!!\n\n From now on, you can follow your friend with our application. This makes our application super and cool. Don't believe my words, try and see it. If you want another features like this please contact with us via e-mail or feedback button.");
                        newFeatureItem2.setImageResource("https://media.giphy.com/media/l3q2K5jinAlChoCLS/giphy.gif");
                        arrayList.add(newFeatureItem2);

                        NewItemDialog
                                .init(getApplicationContext())
                                .setVersionName("1.2.0")
                                .setDialogTitle("New Features of 1.2.0 Version!")
                                .setPositiveButtonTitle("Close")
                                .setNeutralButtonTitle("Show Me Later")
                                .setCancelable(false)
                                .setItems(arrayList)
                                .setCancelButtonListener(new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(WhatsNewActivity.this, "Close Clicked", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setShowLaterButtonListener(new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(WhatsNewActivity.this, "Remind Me Later Clicked", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .showDialog(WhatsNewActivity.this);



                    }
                })
                .setCancelable(false)
                .show();


    }






}
