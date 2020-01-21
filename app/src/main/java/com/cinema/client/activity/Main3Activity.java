package com.cinema.client.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.Bindable;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.brouding.simpledialog.SimpleDialog;
import com.cinema.client.MainActivity;
import com.cinema.client.R;
import com.cinema.client.fragments.HallTestFragment;
import com.cinema.client.fragments.MainFlowFragment;
import com.cinema.client.fragments.TicketSearchFragment;
import com.developer.mtextfield.ExtendedEditText;
import com.droidbyme.dialoglib.DroidDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.keiferstone.nonet.NoNet;
import com.mehdi.shortcut.interfaces.IReceiveStringExtra;
import com.mehdi.shortcut.model.Shortcut;
import com.mehdi.shortcut.util.ShortcutUtils;
import com.pd.chocobar.ChocoBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.markdownview.Config;
import es.dmoral.markdownview.MarkdownView;
import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class Main3Activity extends AppCompatActivity {


    @BindView(R.id.toolbar10)
    Toolbar toolbar;

    @BindView(R.id.llProgressBar)
    View llProgressBar;

    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;


    private SharedPreferences pref;
    private SharedPreferences prefForCheckingFirstRun;
    private SharedPreferences.Editor editor;

    private boolean firstRun = false;


    MyTask mt;

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
                        markdownView.loadFromText("# Hello!\n ## Hello!");
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
                                "mailto", "cinema.app.feedback@gmail.com", null));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cinema-App Feedback");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
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
                                ExtendedEditText userCity = customLayout.findViewById(R.id.extended_edit_text2);
//                                sendDialogDataToActivity(editText.getText().toString());
                                editor.putString("user_name", userName.getText().toString());
                                editor.putString("user_city", userCity.getText().toString());
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
                                        Intent intent = getIntent();
                                        finish();
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

        Shortcut posterShortcut = new Shortcut.ShortcutBuilder()
                .setShortcutIcon(R.drawable.ic_video_label_black_24dp)
                .setShortcutId("myPosterShortcut")
                .setShortcutLongLabel("Poster")
                .setShortcutShortLabel("Poster")
                .setIntentAction("myPosterShortcut")
                .setIntentStringExtraKey("myPosterShortcut")
                .setIntentStringExtraValue("myPosterShortcut")
                .build();



        shortcutUtils.addDynamicShortCut(dynamicShortcut, new IReceiveStringExtra() {
            @Override
            public void onReceiveStringExtra(String stringExtraKey, String stringExtraValue) {
                String intent = getIntent().getStringExtra(stringExtraKey);
                if (intent != null) {
                    if (intent.equals("myTicketsShortcut")) {
                        Intent ticketIntent = new Intent(Main3Activity.this,MyTicketsActivity.class);
                        startActivity(ticketIntent);
                    }
                }
            }
        });

        shortcutUtils.addDynamicShortCut(posterShortcut, new IReceiveStringExtra() {
            @Override
            public void onReceiveStringExtra(String stringExtraKey, String stringExtraValue) {
                String intent = getIntent().getStringExtra(stringExtraKey);
                if (intent != null) {
                    if (intent.equals("myPosterShortcut")) {
                        Intent ticketIntent = new Intent(Main3Activity.this,PosterActivity.class);
                        startActivity(ticketIntent);
                    }
                }
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction ft;
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

                        ft = getSupportFragmentManager().beginTransaction();

//        HallTestFragment hallTestFragment = new HallTestFragment();

                        MainFlowFragment mainFlowFragment=new MainFlowFragment();

                        ft.replace(R.id.fragment, mainFlowFragment);
                        ft.commit();

                        break;
                    case R.id.right_side_of_hall:
                        bottomNavigationView.setBackgroundColor(getResources().getColor(R.color.green));

                        ft = getSupportFragmentManager().beginTransaction();

//        HallTestFragment hallTestFragment = new HallTestFragment();

                        TicketSearchFragment ticketSearchFragment=new TicketSearchFragment();

                        ft.replace(R.id.fragment, ticketSearchFragment);
                        ft.commit();


                        break;
                }

                return true;
            }
        });


        if (prefForCheckingFirstRun.getBoolean("firstrun", true) == true) {

            //
            new MaterialTapTargetPrompt.Builder(Main3Activity.this)
                    .setTarget(R.id.button21)
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

        MainFlowFragment mainFlowFragment=new MainFlowFragment();

        ft.replace(R.id.fragment, mainFlowFragment);
        ft.commit();



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
}
