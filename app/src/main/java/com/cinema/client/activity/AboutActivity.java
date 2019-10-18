package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.cinema.client.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Element versionElement = new Element();
        versionElement.setTitle("Version 6.2");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.ic_ticket_black)
                .setDescription(getResources().getString(R.string.descriptionAboutActivityString))
                .addItem(versionElement)
                .addGroup(getResources().getString(R.string.connectWithUsAboutActivityString))
                .addEmail("elmehdi.sakout@gmail.com")
                .addWebsite("http://medyo.github.io/")
                .addFacebook("the.medy")
                .addPlayStore("com.ideashower.readitlater.pro")
                .addGitHub("medyo")
                .create();

        setContentView(aboutPage);

    }
}
