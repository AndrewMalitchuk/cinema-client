package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

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

        MyStory currentStory = null;
        MyStory previousStory = null;
        try {
            currentStory = new MyStory(
                    "https://static.posters.cz/image/750/%D0%9F%D0%BB%D0%B0%D0%BA%D0%B0%D1%82/blade-runner-2049-fire-ice-i50059.jpg",
                    fmt.parse("20-11-2019 10:00:00"),
                    "Blade Runner 2049"
            );
            previousStory = new MyStory(
                    "https://www.joblo.com/assets/images/joblo/posters/2019/08/joker-poster-main2.jpg",
                    fmt.parse("20-11-2019 10:00:00"),
                    "Joker"
            );
        } catch (ParseException e) {
            Intent intent=new Intent(StoriesActivity.this, ErrorActivity.class);
            intent.putExtra("isNetworkError",true);
            startActivity(intent);
            e.printStackTrace();
        }

        ArrayList<MyStory> myStories = new ArrayList<>();


         /*
          є список з юрлками і назвою тайтла
          коли ти нажимаєш на кнопочку - витягується позиція, і назву тайтлу передаєш в інтент для
          того щоб в актівіті загрузилась вся інфа
           */


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
//                        if(position==0){
                        Intent intent = new Intent(StoriesActivity.this, AboutFilmActivity.class);
                        startActivity(intent);

//                        }
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
