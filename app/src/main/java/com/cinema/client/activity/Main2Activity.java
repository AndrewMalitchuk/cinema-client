package com.cinema.client.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brouding.simpledialog.SimpleDialog;
import com.cinema.client.MainActivity;
import com.cinema.client.R;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.freegeek.android.materialbanner.MaterialBanner;
import com.freegeek.android.materialbanner.simple.SimpleBannerData;
import com.freegeek.android.materialbanner.simple.SimpleViewHolderCreator;
import com.freegeek.android.materialbanner.view.indicator.CirclePageIndicator;
import com.freegeek.android.materialbanner.view.indicator.LinePageIndicator;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mehdi.shortcut.interfaces.IReceiveStringExtra;
import com.mehdi.shortcut.model.Shortcut;
import com.mehdi.shortcut.util.ShortcutUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.markdownview.Config;
import es.dmoral.markdownview.MarkdownView;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class Main2Activity extends AppCompatActivity {

    Toolbar toolbar;

    private static int[] images = {
            R.drawable.once_upon_a_time,
            R.drawable.drive_2011,
            R.drawable.pulp_fiction};

    private static int[] banner = {
            R.drawable.once_upon_a_time,
            R.drawable.drive_2011,
            R.drawable.pulp_fiction};


    private MaterialBanner<SimpleBannerData> materialBanner;
    private MaterialBanner<SimpleBannerData> adsBanner;
    private TextView textView;

    private CirclePageIndicator circlePageIndicator;
    private CirclePageIndicator circlePageIndicator1;
    private LinePageIndicator linePageIndicator;


    List<SimpleBannerData> list = new ArrayList<>();
    List<Integer> icons = new ArrayList<>();


    @BindView(R.id.linLayout)
    LinearLayout linLayout;

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.button21)
    Button button21;

    @BindView(R.id.textView23)
    TextView textView23;

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        materialBanner = findViewById(R.id.material_banner);
        adsBanner = findViewById(R.id.material_banner_adds);
//        textView = findViewById(R.id.hometown);

        ButterKnife.bind(this);

        initIndicator();
        initData();

        //
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Hello, $username!");
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_settings:
                        MarkdownView markdownView = new MarkdownView(Main2Activity.this);
                        markdownView.loadFromText("# Hello!\n ## Hello!");
                        Config defaultConfig = Config.getDefaultConfig();
                        defaultConfig.setDefaultMargin(25);
                        markdownView.setCurrentConfig(defaultConfig);
                        new SimpleDialog.Builder(Main2Activity.this)
                                .setTitle("Need help?")
                                .setCustomView(markdownView)
                                .setBtnConfirmText("Thanks!")
                                .setBtnConfirmTextSizeDp(16)
                                .setBtnConfirmTextColor("#1fd1ab")
                                .show();
                        break;
                    case R.id.action_feedback:
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", "cinema.app.feedback@gmail.com", null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cinema-App Feedback");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));

                    case R.id.action_change_name:

                        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
                        builder.setTitle("Your information");
                        // set the custom layout
                        final View customLayout = getLayoutInflater().inflate(R.layout.change_name_dialog, null);
                        builder.setView(customLayout);
                        // add a button
                        builder.setPositiveButton("Great!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // send data from the AlertDialog to the Activity
//                                EditText editText = customLayout.findViewById(R.id.editText);
//                                sendDialogDataToActivity(editText.getText().toString());
                            }
                        });
                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;
                    case R.id.action_logout:
                        AlertDialog.Builder builderLogout = new AlertDialog.Builder(Main2Activity.this);
                        builderLogout.setTitle("Logout");
                        builderLogout.setMessage("Are you sure about this?");
                        // add the buttons
                        builderLogout.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(Main2Activity.this, "Continue", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builderLogout.setNegativeButton("Cancel", null);
                        // create and show the alert dialog
                        AlertDialog dialogLogout = builderLogout.create();
                        dialogLogout.show();
                        break;
                }


                return false;
            }
        });

        //


        //
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(linLayout)
                .setTransitionDuration(4000)
                .start();
        //

        materialBanner.setPages(new SimpleViewHolderCreator(), list)
                .setIndicator(circlePageIndicator);

        materialBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
//                textView.setText("My hometown: page " + ++position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //iconPageIndicator request these icons
        materialBanner.getAdapter().setIcons(icons);

        materialBanner.setOnItemClickListener(new MaterialBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(Main2Activity.this, "click:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        if (materialBanner.isTurning()) {
            materialBanner.stopTurning();
            Toast.makeText(this, "stop turning", Toast.LENGTH_SHORT).show();
        } else {
            materialBanner.startTurning(3000);
            Toast.makeText(this, "start turning", Toast.LENGTH_SHORT).show();
        }
        materialBanner.setIndicatorInside(!materialBanner.isIndicatorInside());


        //
        adsBanner.setPages(new SimpleViewHolderCreator(), list)
                .setIndicator(circlePageIndicator1);

        adsBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
//                textView.setText("My hometown: page " + ++position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //iconPageIndicator request these icons
        adsBanner.getAdapter().setIcons(icons);

        adsBanner.setOnItemClickListener(new MaterialBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(Main2Activity.this, "click:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        if (adsBanner.isTurning()) {
            adsBanner.stopTurning();
            Toast.makeText(this, "stop turning", Toast.LENGTH_SHORT).show();
        } else {
            adsBanner.startTurning(3000);
            Toast.makeText(this, "start turning", Toast.LENGTH_SHORT).show();
        }
        adsBanner.setIndicatorInside(!adsBanner.isIndicatorInside());
        //
        //
        ShortcutUtils shortcutUtils = new ShortcutUtils(this);

        Shortcut dynamicShortcut = new Shortcut.ShortcutBuilder()
                .setShortcutIcon(R.drawable.ic_email_black_24dp)
                .setShortcutId("dynamicShortcutId")
                .setShortcutLongLabel("dynamicShortcutLongLable")
                .setShortcutShortLabel("dynamicShortcutShortLabel")
                .setIntentAction("dynamicShortcutIntentAction")
                .setIntentStringExtraKey("dynamicShortcutKey")
                .setIntentStringExtraValue("dynamicShortcutValue")
                .build();
        shortcutUtils.addDynamicShortCut(dynamicShortcut, new IReceiveStringExtra() {
            @Override
            public void onReceiveStringExtra(String stringExtraKey, String stringExtraValue) {
                String intent = getIntent().getStringExtra(stringExtraKey);
                if (intent != null) {
                    if (intent.equals("dynamicShortcutValue")) {
                        //write any code here
                    }
                }
            }
        });
        //


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.center_side_of_hall:
                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.blue));
                        break;
                    case R.id.left_side_of_hall:
                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        break;
                    case R.id.right_side_of_hall:
                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.green));
                        break;
                }

                return true;
            }
        });

        //
//        new MaterialTapTargetPrompt.Builder(Main2Activity.this)
//                .setTarget(R.id.button21)
//                .setPrimaryText("Send your first email")
//                .setSecondaryText("Tap the envelope to start composing your first email")
//                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
//                {
//                    @Override
//                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
//                    {
//                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
//                        {
//                            new MaterialTapTargetPrompt.Builder(Main2Activity.this)
//                                    .setTarget(R.id.textView23)
//                                    .setPrimaryText("Send your first email")
//                                    .setSecondaryText("Tap the envelope to start composing your first email")
//                                    .setIcon(R.drawable.ic_account_circle_black_24dp)
//                                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
//                                    {
//                                        @Override
//                                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
//                                        {
//                                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
//                                            {
//                                                // User has pressed the prompt target
//                                            }
//                                        }
//                                    })
//                                    .show();
//                        }
//                    }
//                })
//                .show();


        //

    }

    private void initIndicator() {
        circlePageIndicator = new CirclePageIndicator(this);
        circlePageIndicator.setStrokeColor(R.color.colorLoginActivityText);
        circlePageIndicator.setFillColor(R.color.colorLoginActivityText);
        circlePageIndicator.setRadius(MaterialBanner.dip2Pix(this, 3));
        circlePageIndicator.setBetween(20);

        linePageIndicator = new LinePageIndicator(this);
        linePageIndicator.setUnselectedColor(Color.BLACK);
//        linePageIndicator.setSelectedColor(Color.YELLOW);

    }

    private void initData() {
        for (int i = 0; i < images.length; i++) {
            SimpleBannerData simpleBannerData = new SimpleBannerData();
//            simpleBannerData.setTitle("Country road " + (i + 1));
            simpleBannerData.setResId(images[i]);
            list.add(simpleBannerData);

        }
    }


    private void initIndicatorForBanner() {
        circlePageIndicator1 = new CirclePageIndicator(this);
        circlePageIndicator1.setStrokeColor(R.color.colorLoginActivityText);
        circlePageIndicator1.setFillColor(R.color.colorLoginActivityText);
        circlePageIndicator1.setRadius(MaterialBanner.dip2Pix(this, 3));
        circlePageIndicator1.setBetween(20);

        linePageIndicator = new LinePageIndicator(this);
        linePageIndicator.setUnselectedColor(Color.BLACK);
//        linePageIndicator.setSelectedColor(Color.YELLOW);

    }

    private void initDataForBanner() {
        for (int i = 0; i < banner.length; i++) {
            SimpleBannerData simpleBannerData = new SimpleBannerData();
//            simpleBannerData.setTitle("Country road " + (i + 1));
            simpleBannerData.setResId(banner[i]);
            list.add(simpleBannerData);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    private void sendDialogDataToActivity(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    public void onFindFilmsButtonClick(View view){
        Intent intent = new Intent(this,SearchFilmActivity.class);
        startActivity(intent);
    }
    public void onFindCinemasButtonClick(View view){
        Intent intent = new Intent(this,SearchCinemaActivity.class);
        startActivity(intent);
    }

}
