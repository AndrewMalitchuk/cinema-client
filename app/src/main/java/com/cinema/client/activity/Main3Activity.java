package com.cinema.client.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.brouding.simpledialog.SimpleDialog;
import com.cinema.client.R;
import com.cinema.client.etc.CinemaAppAccountAuthenticator;
import com.cinema.client.fragments.MainFlowFragment;
import com.cinema.client.fragments.TicketSearchFragment;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.FilmAPI;
import com.developer.mtextfield.ExtendedEditText;
import com.droidbyme.dialoglib.DroidDialog;
import com.google.android.gms.common.internal.AccountType;
import com.keiferstone.nonet.NoNet;
import com.mehdi.shortcut.interfaces.IReceiveStringExtra;
import com.mehdi.shortcut.model.Shortcut;
import com.mehdi.shortcut.util.ShortcutUtils;
import com.pd.chocobar.ChocoBar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.markdownview.Config;
import es.dmoral.markdownview.MarkdownView;
import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;
import pro.midev.expandedmenulibrary.ExpandedMenuClickListener;
import pro.midev.expandedmenulibrary.ExpandedMenuItem;
import pro.midev.expandedmenulibrary.ExpandedMenuView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;


public class Main3Activity extends AppCompatActivity {


    @BindView(R.id.toolbar10)
    Toolbar toolbar;

    @BindView(R.id.llProgressBar)
    View llProgressBar;

    @BindView(R.id.expMenu)
    ExpandedMenuView expandedMenuView;


//    @BindView(R.id.navigation)
//    BottomNavigationView bottomNavigationView;


    private SharedPreferences pref;
    private SharedPreferences prefForCheckingFirstRun;
    private SharedPreferences.Editor editor;

    private boolean firstRun = false;

    public static final String ACCOUNT_PREF = "accountPref";


    MyTask mt;

    Calendar now = Calendar.getInstance();

    TimePickerDialog tpd;
    DatePickerDialog dpd;


    private APIInterface apiInterface;

    private List<FilmAPI> films;

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ButterKnife.bind(this);

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
                        MarkdownView markdownView = new MarkdownView(Main3Activity.this);
                        markdownView.loadFromText("# Hello!\n ## Hello!\n **сюда кинь вступ з звіту**");
                        Config defaultConfig = Config.getDefaultConfig();
                        defaultConfig.setDefaultMargin(25);
                        markdownView.setCurrentConfig(defaultConfig);
                        new SimpleDialog.Builder(Main3Activity.this)
                                .setTitle("Need help?")
                                .setCustomView(markdownView)
                                .setBtnConfirmText("Thanks!")
                                .setBtnConfirmTextSizeDp(16)
                                .setBtnConfirmTextColor("#1fd1ab")
                                .show();
                        break;
                    case R.id.action_feedback:
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", "cinema.app.diploma@gmail.com", null));
//                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cinema-App Feedback");
//                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
                        break;
                    case R.id.action_change_name:

                        AlertDialog.Builder builder = new AlertDialog.Builder(Main3Activity.this);
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

//                                sendDialogDataToActivity(editText.getText().toString());
                                editor.putString("user_name", userName.getText().toString());
//                                editor.putString("user_city", userCity.getText().toString());
                                editor.commit(); // commit changes

                                ChocoBar.builder().setActivity(Main3Activity.this)
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
                    case R.id.action_about_developer:
                        startActivity(new Intent(Main3Activity.this, AboutDeveloperActivity.class));
                        break;
                    case R.id.action_logout:
                        new DroidDialog.Builder(Main3Activity.this)
                                .icon(R.drawable.ic_exit_to_app_black_24dp)
                                .title("Logout")
                                .content("Are you sure about this?")
                                .cancelable(true, false)
                                .positiveButton("Yes, I'm sure", new DroidDialog.onPositiveListener() {
                                    @Override
                                    public void onPositive(Dialog droidDialog) {
                                        Toast.makeText(Main3Activity.this, "Yes, I'm sure", Toast.LENGTH_SHORT).show();
                                        //

                                        pref.edit().clear();
                                        pref.edit().commit();

                                        getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE).edit().clear();
                                        getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE).edit().commit();


                                        Intent intent = new Intent(Main3Activity.this, LoginActivity.class);

                                        startActivity(intent);
                                        //
                                    }
                                })
                                .negativeButton("No", new DroidDialog.onNegativeListener() {
                                    @Override
                                    public void onNegative(Dialog droidDialog) {
                                        Toast.makeText(Main3Activity.this, "No", Toast.LENGTH_SHORT).show();
                                        //
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                        //
                                    }
                                })
                                .color(ContextCompat.getColor(Main3Activity.this, R.color.colorAccent), ContextCompat.getColor(Main3Activity.this, R.color.white),
                                        ContextCompat.getColor(Main3Activity.this, R.color.colorAccent))
                                .show();

                        break;
                }


                return false;
            }
        });

        //


        mt = new Main3Activity.MyTask();
        //
        ShortcutUtils shortcutUtils = new ShortcutUtils(this);

        Shortcut dynamicShortcut = new Shortcut.ShortcutBuilder()
                .setShortcutIcon(R.drawable.ic_ticket_black)
                .setShortcutId("myTicketsShortcut")
                .setShortcutLongLabel("My Tickets")
                .setShortcutShortLabel("My Tickets")
                .setIntentAction("myTicketsShortcut")
                .setIntentStringExtraKey("myTicketsShortcut")
                .setIntentStringExtraValue("myTicketsShortcut")
                .build();


        Shortcut randomShortcut = new Shortcut.ShortcutBuilder()
                .setShortcutIcon(R.drawable.ic_random_black_24dp)
                .setShortcutId("myRandomShortcut")
                .setShortcutLongLabel("Random")
                .setShortcutShortLabel("Random")
                .setIntentAction("myRandomShortcut")
                .setIntentStringExtraKey("myRandomShortcut")
                .setIntentStringExtraValue("myRandomShortcut")
                .build();

        Shortcut storiesShortcut = new Shortcut.ShortcutBuilder()
                .setShortcutIcon(R.drawable.ic_stories_black_24dp)
                .setShortcutId("myStoriesShortcut")
                .setShortcutLongLabel("Stories")
                .setShortcutShortLabel("Stories")
                .setIntentAction("myStoriesShortcut")
                .setIntentStringExtraKey("myStoriesShortcut")
                .setIntentStringExtraValue("myStoriesShortcut")
                .build();


        shortcutUtils.addDynamicShortCut(dynamicShortcut, new IReceiveStringExtra() {
            @Override
            public void onReceiveStringExtra(String stringExtraKey, String stringExtraValue) {
                String intent = getIntent().getStringExtra(stringExtraKey);
                if (intent != null) {
                    if (intent.equals("myTicketsShortcut")) {
                        Intent ticketIntent = new Intent(Main3Activity.this, MyTicketsActivity.class);
                        startActivity(ticketIntent);
                    }
                }
            }
        });


        // TODO:
        shortcutUtils.addDynamicShortCut(randomShortcut, new IReceiveStringExtra() {
            @Override
            public void onReceiveStringExtra(String stringExtraKey, String stringExtraValue) {
                String intent = getIntent().getStringExtra(stringExtraKey);
                if (intent != null) {
                    if (intent.equals("myRandomShortcut")) {
                        apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<List<FilmAPI>> callFilms = apiInterface.getFilms();
                        callFilms.enqueue(new Callback<List<FilmAPI>>() {
                            @Override
                            public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                                List<Integer> input = new ArrayList<>();
                                for (FilmAPI film : response.body()) {
                                    input.add(film.getId());
                                }
                                int randomFilmId = getRandomNum(input);
                                Intent intent = new Intent(Main3Activity.this, AboutFilmActivity.class);
                                intent.putExtra("filmId", randomFilmId);
                                startActivity(intent);

                            }

                            @Override
                            public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                                call.cancel();
                                Intent intent = new Intent(Main3Activity.this, ErrorActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                }


            }
        });

        shortcutUtils.addDynamicShortCut(storiesShortcut, new IReceiveStringExtra() {
            @Override
            public void onReceiveStringExtra(String stringExtraKey, String stringExtraValue) {
                String intent = getIntent().getStringExtra(stringExtraKey);
                if (intent != null) {
                    if (intent.equals("myStoriesShortcut")) {

                        apiInterface = APIClient.getClient().create(APIInterface.class);
                        Call<List<FilmAPI>> call = apiInterface.getFilms();

                        call.enqueue(new Callback<List<FilmAPI>>() {
                            @Override
                            public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {

                                films = response.body();
                                Log.d("FILMS", films.size() + "");

                                Collections.sort(films, new Comparator<FilmAPI>() {
                                    @Override
                                    public int compare(FilmAPI filmAPI, FilmAPI t1) {
                                        return t1.getDate().compareTo(filmAPI.getDate());
                                    }
                                });

                                stories(films);


                            }

                            @Override
                            public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                                call.cancel();
                                Intent intent = new Intent(Main3Activity.this, ErrorActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                }

            }
        });


        // Naviagation with BottomNavBar
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                FragmentTransaction ft;
//                switch (item.getItemId()) {
//                    case R.id.center_side_of_hall:
//                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.blue));
//
//                        //
//
//
//                        Call<List<FilmAPI>> call=apiInterface.getFilms();
//
//                        call.enqueue(new Callback<List<FilmAPI>>() {
//                            @Override
//                            public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
//
//                                films=response.body();
//                                Log.d("FILMS", films.size()+"");
//
//                                Collections.sort(films, new Comparator<FilmAPI>() {
//                                    @Override
//                                    public int compare(FilmAPI filmAPI, FilmAPI t1) {
//                                        return  t1.getDate().compareTo(filmAPI.getDate());
//                                    }
//                                });
//
//                                stories(films);
//
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
//                                call.cancel();
//                                Intent intent = new Intent(Main3Activity.this, ErrorActivity.class);
//                                startActivity(intent);
//                            }
//                        });
//
//
//
//
//                        bottomNavigationView.setSelectedItemId(R.id.left_side_of_hall);
//                        bottomNavigationView.setSelected(true);
//
//                        //
//
//
//                        break;
//                    case R.id.left_side_of_hall:
//                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//
//                        ft = getSupportFragmentManager().beginTransaction();
//
////        HallTestFragment hallTestFragment = new HallTestFragment();
//
//                        MainFlowFragment mainFlowFragment = new MainFlowFragment();
//
//                        ft.replace(R.id.fragment, mainFlowFragment);
//                        ft.commit();
//
//                        break;
//                    case R.id.right_side_of_hall:
//                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.green));
//
//                        ft = getSupportFragmentManager().beginTransaction();
//
////        HallTestFragment hallTestFragment = new HallTestFragment();
//
//                        TicketSearchFragment ticketSearchFragment = new TicketSearchFragment();
//
//                        ft.replace(R.id.fragment, ticketSearchFragment);
//                        ft.commit();
//
//
//                        break;
//                }
//
//                return true;
//            }
//        });


        // Navigation with this 'awesome' button
        expandedMenuView.setIcons(
                new ExpandedMenuItem(R.drawable.ic_stories_24dp, "Stories"),
                new ExpandedMenuItem(R.drawable.ic_random_white_24dp, "Random"),
                new ExpandedMenuItem(R.drawable.ic_ticket_white, "Tickets"),
                new ExpandedMenuItem(R.drawable.ic_main_page_24dp, "Main")
        );

        expandedMenuView.setOnItemClickListener(new ExpandedMenuClickListener() {
            @Override
            public void onItemClick(int i) {

                FragmentTransaction ft;
                switch (i) {
                    case 0:

                        //


                        Call<List<FilmAPI>> call = apiInterface.getFilms();

                        call.enqueue(new Callback<List<FilmAPI>>() {
                            @Override
                            public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {

                                films = response.body();
                                Log.d("FILMS", films.size() + "");

                                Collections.sort(films, new Comparator<FilmAPI>() {
                                    @Override
                                    public int compare(FilmAPI filmAPI, FilmAPI t1) {
                                        return t1.getDate().compareTo(filmAPI.getDate());
                                    }
                                });

                                stories(films);


                            }

                            @Override
                            public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                                call.cancel();
                                Intent intent = new Intent(Main3Activity.this, ErrorActivity.class);
                                startActivity(intent);
                            }
                        });


                        //


                        break;
                    case 1:


                        Call<List<FilmAPI>> callFilms = apiInterface.getFilms();
                        callFilms.enqueue(new Callback<List<FilmAPI>>() {
                            @Override
                            public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                                List<Integer> input = new ArrayList<>();
                                for (FilmAPI film : response.body()) {
                                    input.add(film.getId());
                                }
                                int randomFilmId = getRandomNum(input);
                                Intent intent = new Intent(Main3Activity.this, AboutFilmActivity.class);
                                intent.putExtra("filmId", randomFilmId);
                                startActivity(intent);

                            }

                            @Override
                            public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                                call.cancel();
                                Intent intent = new Intent(Main3Activity.this, ErrorActivity.class);
                                startActivity(intent);
                            }
                        });


                        break;
                    case 2:
                        ft = getSupportFragmentManager().beginTransaction();
                        TicketSearchFragment ticketSearchFragment = new TicketSearchFragment();
                        ft.replace(R.id.fragment, ticketSearchFragment);
                        ft.commit();
                        break;
                    case 3:
                        ft = getSupportFragmentManager().beginTransaction();
                        MainFlowFragment mainFlowFragment = new MainFlowFragment();
                        ft.replace(R.id.fragment, mainFlowFragment);
                        ft.commit();

                        break;

                }

            }
        });


        if (prefForCheckingFirstRun.getBoolean("firstrun", true) == true) {

            //
            new MaterialTapTargetPrompt.Builder(Main3Activity.this)
                    .setTarget(R.id.filmMoreMainFlowFragmentButton)
                    .setPrimaryText("Send your first email")
                    .setSecondaryText("Tap the envelope to start composing your first email")
                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                        @Override
                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                new MaterialTapTargetPrompt.Builder(Main3Activity.this)
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
        ChocoBar.builder().setActivity(Main3Activity.this)
                .setText("Welcome back, " + pref.getString("user_name", null))
                .setDuration(ChocoBar.LENGTH_SHORT)
                .build()
                .show();

        //

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

//        HallTestFragment hallTestFragment = new HallTestFragment();

        MainFlowFragment mainFlowFragment = new MainFlowFragment();

        ft.replace(R.id.fragment, mainFlowFragment);
        ft.commit();

        //

//        // in method (?)


//        Intent intent = new Intent(this, AboutFilmActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getResources().getString(R.string.cinemaAppNotify))
//                .setSmallIcon(R.drawable.ic_ticket_accent)
//                .setContentTitle(getString(R.string.filmReminderNotificationMainActivityString))
//                .setContentText("It's Fight Club in Kosmos cinema")
//                .setContentIntent(pendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_HIGH);
//
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//
//
//        // notification_id (?)
//        notificationManager.notify(10, builder.build());

        //

        Calendar cal = new GregorianCalendar(2020, 0, 23, 13, 40, 00);


//        dpd = DatePickerDialog.newInstance(
//                Main3Activity.this,
//                now.get(Calendar.YEAR),
//                now.get(Calendar.MONTH),
//                now.get(Calendar.DAY_OF_MONTH)
//        );
//
//        tpd = TimePickerDialog.newInstance(
//                Main3Activity.this,
//                now.get(Calendar.HOUR_OF_DAY),
//                now.get(Calendar.MINUTE),
//                now.get(Calendar.SECOND),
//                false
//        );
//
//        dpd.show(getFragmentManager(), "Datepickerdialog");


//        Удивительно, но работает
//        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
//                .title("title")
//                .content("content")
//                .color(255, 0, 0, 255)
//                .led_color(255, 255, 255, 255)
//                .time(cal)
//                .key("test")
//                .addAction(new Intent(this, AboutFilmActivity.class), "Open", true, false)
//                .large_icon(R.drawable.ic_ticket_accent)
//                .rrule("FREQ=MINUTELY;INTERVAL=5;COUNT=2")
//                .build();

        //
        apiInterface = APIClient.getClient().create(APIInterface.class);


        //
        // Account Manager stuff
        // https://www.zoftino.com/android-account-manager-&-create-custom-account-type

//        AccountManager manager = AccountManager.get(getApplicationContext());
//        final Account account = new Account(userNameFromPreferences, "com.cinema.app");
//
////        new CinemaAppAccountAuthenticator(this).addAccountFromCredentials()
//
//        boolean success = manager.addAccountExplicitly(account, getSharedPreferences("accountPref", Context.MODE_PRIVATE).getString("password", null), null);
//        if (success) {
//            Log.d("ACCOUNT", "Account created");
//        } else {
//            Log.d("ACCOUNT", "Account creation failed. Look at previous logs to investigate");
//        }

        //


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);


        return super.onCreateOptionsMenu(menu);
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
                        Toast.makeText(Main3Activity.this, myStories.get(position).getDescription(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Main3Activity.this, AboutFilmActivity.class);
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

    public void stories(List<FilmAPI> films) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyy-MM-dd");


        ArrayList<MyStory> myStories = new ArrayList<>();


         /*
          є список з юрлками і назвою тайтла
          коли ти нажимаєш на кнопочку - витягується позиція, і назву тайтлу передаєш в інтент для
          того щоб в актівіті загрузилась вся інфа
           */

        int i = 0;

        try {
            for (FilmAPI film : films) {
                if (i == 5)
                    break;
                MyStory temp = new MyStory();
                temp.setDate(fmt.parse(film.getDate()));
                temp.setDescription(film.getTitle());
                temp.setUrl(APIClient.HOST + film.getPicUrl());
//                temp.setDate(film.getId().toString());
                Log.d("STR", temp.toString());

                myStories.add(temp);
                i++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        myStories.add(previousStory);
//        myStories.add(currentStory);

        new StoryView.Builder(getSupportFragmentManager())
                .setStoriesList(myStories) // Required
                .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
//                .setTitleText("New films") // Default is Hidden
//                .setSubtitleText("") // Default is Hidden
//                .setTitleLogoUrl("https://i.pinimg.com/originals/f8/27/ed/f827ed9a704146f65b96226f430abf3c.png") // Default is Hidden
//                .setTitleLogoUrl()
                .setStoryClickListeners(new StoryClickListeners() {
                    @Override
                    public void onDescriptionClickListener(int position) {
                        Toast.makeText(Main3Activity.this, myStories.get(position).getDescription(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Main3Activity.this, AboutFilmActivity.class);

                        intent.putExtra("filmId", films.get(position).getId());
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

//    @Override
//    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        now.set(Calendar.YEAR,year);
//        now.set(Calendar.MONTH,monthOfYear);
//        now.set(Calendar.DAY_OF_MONTH,dayOfMonth);
//        tpd.show(getFragmentManager(), "Timepickerdialog");
//    }
//
//    @Override
//    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
//        now.set(Calendar.HOUR_OF_DAY,hourOfDay);
//        now.set(Calendar.MINUTE,minute);
//        now.set(Calendar.SECOND,second);
//        Log.d("YEAR",now.get(Calendar.YEAR)+"");
//        Log.d("MONTH",now.get(Calendar.MONTH)+"");
//        Log.d("DAY_OF_MONTH",now.get(Calendar.DAY_OF_MONTH)+"");
//        Log.d("HOUR_OF_DAY",now.get(Calendar.HOUR_OF_DAY)+"");
//        Log.d("MINUTE",now.get(Calendar.MINUTE)+"");
//        Log.d("SECOND",now.get(Calendar.SECOND)+"");
//        Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
//        intent.putExtra("test","I am a String");
//        NotifyMe notifyMe = new NotifyMe.Builder(getApplicationContext())
//                .title("title")
//                .content("content")
//                .color(255,0,0,255)
//                .led_color(255,255,255,255)
//                .time(now)
//                .addAction(intent,"Snooze",false)
//                .key("test")
//                .addAction(new Intent(),"Dismiss",true,false)
//                .addAction(intent,"Done")
//                .large_icon(R.mipmap.ic_launcher_round)
//                .rrule("FREQ=MINUTELY;INTERVAL=5;COUNT=2")
//                .build();
//    }


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

    public int getRandomNum(List<Integer> input) {
        Random r = new Random();
        int nextRandomNumberIndex = r.nextInt(input.size());
        return input.get(nextRandomNumberIndex);
    }
}
