package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.cinema.client.R;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.nonzeroapps.whatisnewdialog.NewItemDialog;
import com.nonzeroapps.whatisnewdialog.object.NewFeatureItem;

import java.util.ArrayList;


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
                        ArrayList<NewFeatureItem> arrayList = new ArrayList<>();

                        NewFeatureItem newFeatureItem = new NewFeatureItem();
                        newFeatureItem.setFeatureDesc("From now on, you can search all things you need - cinemas, films, tickets and even genre!");
                        newFeatureItem.setFeatureTitle("Searching");
                        newFeatureItem.setImageResource("https://media.giphy.com/media/cWVlY0EeQZOrm/source.gif");
                        arrayList.add(newFeatureItem);

                        NewFeatureItem newFeatureItem2 = new NewFeatureItem();
                        newFeatureItem2.setFeatureTitle("Notify");
                        newFeatureItem2.setFeatureDesc("Get notification when new film arrive and when your chosen film will be shown");
                        newFeatureItem2.setImageResource("https://media.giphy.com/media/huyZxIJvtqVeRp7QcS/source.gif");
                        arrayList.add(newFeatureItem2);

                        NewFeatureItem newFeatureItem3 = new NewFeatureItem();
                        newFeatureItem3.setFeatureTitle("Find in map");
                        newFeatureItem3.setFeatureDesc("Find your favourite cinema theatre easily via map!");
                        newFeatureItem3.setImageResource("https://media.giphy.com/media/3ov9jYkVbdGMo6UcG4/source.gif");
                        arrayList.add(newFeatureItem3);

                        NewFeatureItem newFeatureItem4 = new NewFeatureItem();
                        newFeatureItem4.setFeatureTitle("More information");
                        newFeatureItem4.setFeatureDesc("Watch trailer before choosing film.");
                        newFeatureItem4.setImageResource("https://media.giphy.com/media/Iyqv0kE4hUwYE/source.gif");
                        arrayList.add(newFeatureItem4);

                        NewItemDialog
                                .init(getApplicationContext())
                                .setVersionName("1.2.0")
                                .setDialogTitle("New Features of 0.1 Version!")
                                .setPositiveButtonTitle("Close")
                                .setCancelable(true)
//                                .setCancelable(false)
                                .setItems(arrayList)
                                .setCancelButtonListener(new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(WhatsNewActivity.this, "Close Clicked", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .showDialog(WhatsNewActivity.this);
                    }
                })
                .setNegativeText("No, thanks")
                .setNegativeTextColorResource(R.color.colorAccent)
                .onNegative(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(BottomDialog dialog) {


                    }
                })
                .setCancelable(false)
                .show();


    }


}
