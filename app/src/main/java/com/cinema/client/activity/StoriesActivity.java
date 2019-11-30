package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.cinema.client.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

public class StoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        //
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");

        MyStory currentStory=null;
        MyStory previousStory=null;
        try {
            currentStory = new MyStory(
                    "https://media.kg-portal.ru/movies/b/bladerunner2/posters/bladerunner2_30.jpg",
                    fmt.parse("20-11-2019 10:00:00"),
                    "Blade Runner 2049"
            );
            previousStory = new MyStory(
                    "https://www.joblo.com/assets/images/joblo/posters/2019/08/joker-poster-main2.jpg",
                    fmt.parse("20-11-2019 10:00:00"),
                    "Joker"
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayList<MyStory> myStories = new ArrayList<>();


        myStories.add(previousStory);
        myStories.add(currentStory);

        new StoryView.Builder(getSupportFragmentManager())
                .setStoriesList(myStories) // Required
                .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                .setTitleText("Blade runner 2049") // Default is Hidden
                .setSubtitleText("Cat meme") // Default is Hidden
                .setTitleLogoUrl("https://i.pinimg.com/originals/f8/27/ed/f827ed9a704146f65b96226f430abf3c.png") // Default is Hidden
                .setStoryClickListeners(new StoryClickListeners() {
                    @Override
                    public void onDescriptionClickListener(int position) {
                        //your action
                    }

                    @Override
                    public void onTitleIconClickListener(int position) {
                        //your action
                    }
                }) // Optional Listeners
                .build() // Must be called before calling show method
                .show();

    }
}
