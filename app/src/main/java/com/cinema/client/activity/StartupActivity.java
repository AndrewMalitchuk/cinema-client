package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.cinema.client.R;
import com.cuneytayyildiz.onboarder.OnboarderActivity;
import com.cuneytayyildiz.onboarder.OnboarderPage;
import com.cuneytayyildiz.onboarder.utils.OnboarderPageChangeListener;

import java.util.Arrays;
import java.util.List;

public class StartupActivity extends OnboarderActivity implements OnboarderPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<OnboarderPage> pages = Arrays.asList(
                new OnboarderPage.Builder()
                        .title("Donut")
                        .description("Android 1.6")
                        .imageResourceId( R.drawable.about_icon_email)
                        .backgroundColorId(R.color.about_background_color)
                        .titleColorId(R.color.about_item_text_color)
                        .descriptionColorId(R.color.about_item_text_color)
                        .multilineDescriptionCentered(true)
                        .build(),

                // No need to write all of them :P

                new OnboarderPage.Builder()
                        .title("Oreo")
                        .description("Android 8.0")
                        .imageResourceId( R.drawable.about_icon_email)
                        .backgroundColor(R.color.colorAccent)
                        .titleColor(R.color.about_item_text_color)
                        .descriptionColor(R.color.about_item_text_color)
                        .multilineDescriptionCentered(true)
                        .build()
        );
        setOnboarderPageChangeListener(this);
        initOnboardingPages(pages);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    @Override
    public void onFinishButtonPressed() {
        // implement your logic, save induction has done to sharedPrefs
        Toast.makeText(this, "Finish button was pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageChanged(int position) {
        Toast.makeText(this, "onPageChanged: " + position, Toast.LENGTH_SHORT).show();
    }
}
