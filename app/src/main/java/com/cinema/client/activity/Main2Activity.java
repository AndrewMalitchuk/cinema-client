package com.cinema.client.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brouding.simpledialog.SimpleDialog;
import com.cinema.client.R;
import com.developer.mtextfield.ExtendedEditText;
import com.droidbyme.dialoglib.DroidDialog;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.freegeek.android.materialbanner.MaterialBanner;
import com.freegeek.android.materialbanner.simple.SimpleBannerData;
import com.freegeek.android.materialbanner.simple.SimpleViewHolderCreator;
import com.freegeek.android.materialbanner.view.indicator.CirclePageIndicator;
import com.freegeek.android.materialbanner.view.indicator.LinePageIndicator;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.keiferstone.nonet.NoNet;
import com.mehdi.shortcut.interfaces.IReceiveStringExtra;
import com.mehdi.shortcut.model.Shortcut;
import com.mehdi.shortcut.util.ShortcutUtils;
import com.pd.chocobar.ChocoBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.markdownview.Config;
import es.dmoral.markdownview.MarkdownView;
import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;
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

    @BindView(R.id.llProgressBar)
    View llProgressBar;


    private SharedPreferences pref;
    private SharedPreferences prefForCheckingFirstRun;
    private SharedPreferences.Editor editor;

    private boolean firstRun = false;


    MyTask mt;

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
        pref = getApplicationContext().getSharedPreferences("UserData", 0);
        editor = pref.edit();

        prefForCheckingFirstRun = getSharedPreferences("com.cinema.client", MODE_PRIVATE);


        //
        NoNet.configure()
                .endpoint("https://google.com")
                .timeout(5)
                .connectedPollFrequency(60)
                .disconnectedPollFrequency(1);

        NoNet.monitor(this)
                .poll()
                .snackbar();
        //

        //

        //
        toolbar = findViewById(R.id.toolbar10);
        String userNameFromPreferences = pref.getString("user_name", null);
        if (userNameFromPreferences != null && !userNameFromPreferences.equals("")) {
            toolbar.setTitle("Hello, " + userNameFromPreferences);
        } else {
            toolbar.setTitle("Hello, $username!");
        }
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
                        break;
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
                                ExtendedEditText userName = customLayout.findViewById(R.id.extended_edit_text1);
                                ExtendedEditText userCity = customLayout.findViewById(R.id.extended_edit_text2);
//                                sendDialogDataToActivity(editText.getText().toString());
                                editor.putString("user_name", userName.getText().toString());
                                editor.putString("user_city", userCity.getText().toString());
                                editor.commit(); // commit changes

                                ChocoBar.builder().setActivity(Main2Activity.this)
                                        .setText("Success!")
                                        .setDuration(ChocoBar.LENGTH_SHORT)
                                        .build()
                                        .show();

                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        });
                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;
                    case R.id.action_logout:
                        new DroidDialog.Builder(Main2Activity.this)
                                .icon(R.drawable.ic_exit_to_app_black_24dp)
                                .title("Logout")
                                .content("Are you sure about this?")
                                .cancelable(true, false)
                                .positiveButton("Yes, I'm sure", new DroidDialog.onPositiveListener() {
                                    @Override
                                    public void onPositive(Dialog droidDialog) {
                                        Toast.makeText(Main2Activity.this, "Yes, I'm sure", Toast.LENGTH_SHORT).show();
                                        //
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                        //
                                    }
                                })
                                .negativeButton("No", new DroidDialog.onNegativeListener() {
                                    @Override
                                    public void onNegative(Dialog droidDialog) {
                                        Toast.makeText(Main2Activity.this, "No", Toast.LENGTH_SHORT).show();
                                        //
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                        //
                                    }
                                })
                                .color(ContextCompat.getColor(Main2Activity.this, R.color.colorAccent), ContextCompat.getColor(Main2Activity.this, R.color.white),
                                        ContextCompat.getColor(Main2Activity.this, R.color.colorAccent))
                                .show();

                        break;
                }


                return false;
            }
        });

        //


        mt = new MyTask();
//        mt.execute();

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
//        ShortcutUtils shortcutUtils = new ShortcutUtils(this);
//
//        Shortcut dynamicShortcut = new Shortcut.ShortcutBuilder()
//                .setShortcutIcon(R.drawable.ic_email_black_24dp)
//                .setShortcutId("dynamicShortcutId")
//                .setShortcutLongLabel("dynamicShortcutLongLable")
//                .setShortcutShortLabel("dynamicShortcutShortLabel")
//                .setIntentAction("dynamicShortcutIntentAction")
//                .setIntentStringExtraKey("dynamicShortcutKey")
//                .setIntentStringExtraValue("dynamicShortcutValue")
//                .build();
//        shortcutUtils.addDynamicShortCut(dynamicShortcut, new IReceiveStringExtra() {
//            @Override
//            public void onReceiveStringExtra(String stringExtraKey, String stringExtraValue) {
//                String intent = getIntent().getStringExtra(stringExtraKey);
//                if (intent != null) {
//                    if (intent.equals("dynamicShortcutValue")) {
//                        //write any code here
//                    }
//                }
//            }
//        });
        //


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.center_side_of_hall:
                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.blue));

                        //
                        stories();

                        bottomNavigationView.setSelectedItemId(R.id.left_side_of_hall);
                        bottomNavigationView.setSelected(true);

                        //


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


        if (prefForCheckingFirstRun.getBoolean("firstrun", true) == true) {

            //
            new MaterialTapTargetPrompt.Builder(Main2Activity.this)
                    .setTarget(R.id.button21)
                    .setPrimaryText("Send your first email")
                    .setSecondaryText("Tap the envelope to start composing your first email")
                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                        @Override
                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                new MaterialTapTargetPrompt.Builder(Main2Activity.this)
                                        .setTarget(R.id.textView23)
                                        .setPrimaryText("Send your first email")
                                        .setSecondaryText("Tap the envelope to start composing your first email")
                                        .setIcon(R.drawable.ic_account_circle_black_24dp)
                                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                            @Override
                                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                                    // User has pressed the prompt target
                                                }
                                            }
                                        })
                                        .show();
                            }
                        }
                    })
                    .show();
        }

        //
        ChocoBar.builder().setActivity(Main2Activity.this)
                .setText("Welcome back, " + pref.getString("user_name", null))
                .setDuration(ChocoBar.LENGTH_SHORT)
                .build()
                .show();

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

    public void onFindFilmsButtonClick(View view) {
        Intent intent = new Intent(this, SearchFilmActivity.class);
        startActivity(intent);
    }

    public void onFindCinemasButtonClick(View view) {
        Intent intent = new Intent(this, SearchCinemaActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefForCheckingFirstRun.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefForCheckingFirstRun.edit().putBoolean("firstrun", false).commit();
        }
    }


    public void onAboutFilmClick(View view) {
        Intent intent = new Intent(this, AboutFilmActivity.class);
        startActivity(intent);
    }


    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mProgressDialog.setMessage("Working ...");
//            mProgressDialog.show();
//            statusView.setStatus(iammert.com.library.Status.LOADING);
//            simpleDialog.show();
            llProgressBar.setVisibility(View.VISIBLE);
            // https://medium.com/@therajanmaurya/progress-bar-instead-progress-dialog-baa5d72c2860

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(2);
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.testFragment, new HallTestFragment());
//                ft.commit();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            mProgressDialog.dismiss();
//            statusView.setStatus(iammert.com.library.Status.COMPLETE);
//            simpleDialog.dismiss();
            llProgressBar.setVisibility(View.GONE);
            // https://medium.com/@therajanmaurya/progress-bar-instead-progress-dialog-baa5d72c2860


        }
    }

    public void onGenreIconClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.comedyAvatar:
                intent = new Intent(Main2Activity.this, PosterActivity.class);
                startActivity(intent);
                Toast.makeText(this, "comedyAvatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionAvatar:
                intent = new Intent(Main2Activity.this, PosterActivity.class);
                startActivity(intent);
                Toast.makeText(this, "actionAvatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.historicalAvatar:
                intent = new Intent(Main2Activity.this, PosterActivity.class);
                startActivity(intent);
                Toast.makeText(this, "historicalAvatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sciFiAvatar:
                intent = new Intent(Main2Activity.this, PosterActivity.class);
                startActivity(intent);
                Toast.makeText(this, "sciFiAvatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.horrorAvatar:
                intent = new Intent(Main2Activity.this, PosterActivity.class);
                startActivity(intent);
                Toast.makeText(this, "horrorAvatar", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onCityIconClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivano_frankivsk:
                intent = new Intent(Main2Activity.this, SearchCinemaActivity.class);
                startActivity(intent);
                Toast.makeText(this, "ivano_frankivsk", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lviv:
                intent = new Intent(Main2Activity.this, SearchCinemaActivity.class);
                startActivity(intent);
                Toast.makeText(this, "lviv", Toast.LENGTH_SHORT).show();
                break;
            case R.id.kiyv:
                intent = new Intent(Main2Activity.this, SearchCinemaActivity.class);
                startActivity(intent);
                Toast.makeText(this, "kiyv", Toast.LENGTH_SHORT).show();
                break;
            case R.id.kharkiv:
                intent = new Intent(Main2Activity.this, SearchCinemaActivity.class);
                startActivity(intent);
                Toast.makeText(this, "kharkiv", Toast.LENGTH_SHORT).show();
                break;
            case R.id.odessa:
                intent = new Intent(Main2Activity.this, SearchCinemaActivity.class);
                startActivity(intent);
                Toast.makeText(this, "odessa", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void stories() {
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
                        Toast.makeText(Main2Activity.this, myStories.get(position).getDescription(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Main2Activity.this, AboutFilmActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onTitleIconClickListener(int position) {
                        //your action
                    }

                }) // Optional Listeners
                .build() // Must be called before calling show method
                .show();
    }

    public void onSelectedCinemaClick(View view){
        Intent intent=new Intent(this,AboutCinemaActivity.class);
        startActivity(intent);
    }

}
