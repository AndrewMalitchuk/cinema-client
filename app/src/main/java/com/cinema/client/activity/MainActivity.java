package com.cinema.client.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.brouding.simpledialog.SimpleDialog;
import com.cinema.client.R;
import com.cinema.client.fragments.MainFlowFragment;
import com.cinema.client.fragments.TicketSearchFragment;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.FilmAPI;
import com.developer.mtextfield.ExtendedEditText;
import com.droidbyme.dialoglib.DroidDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.keiferstone.nonet.NoNet;
import com.mehdi.shortcut.interfaces.IReceiveStringExtra;
import com.mehdi.shortcut.model.Shortcut;
import com.mehdi.shortcut.util.ShortcutUtils;
import com.nonzeroapps.whatisnewdialog.NewItemDialog;
import com.nonzeroapps.whatisnewdialog.object.NewFeatureItem;
import com.pd.chocobar.ChocoBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar10)
    Toolbar toolbar;

    @BindView(R.id.llProgressBar)
    View llProgressBar;

    @BindView(R.id.expMenu)
    ExpandedMenuView expandedMenuView;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    MyTask mt;

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
            toolbar.setTitle("Hello, Tyler!");
        }
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        MarkdownView markdownView = new MarkdownView(MainActivity.this);
                        markdownView.loadFromText("HELP!\nI need somebody");
                        Config defaultConfig = Config.getDefaultConfig();
                        defaultConfig.setDefaultMargin(25);
                        markdownView.setCurrentConfig(defaultConfig);
                        new SimpleDialog.Builder(MainActivity.this)
                                .setTitle("Need help?")
                                .setContent(getResources().getString(R.string.help_text))
                                .setBtnConfirmText("Thanks!")
                                .setBtnConfirmTextSizeDp(16)
                                .setBtnConfirmTextColor("#D81B60")
                                .show();
                        break;
                    case R.id.action_feedback:
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", "cinema.app.diploma@gmail.com", null));
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
                        break;
                    case R.id.action_change_name:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Your information");
                        final View customLayout = getLayoutInflater().inflate(R.layout.change_name_dialog, null);
                        builder.setView(customLayout);
                        builder.setPositiveButton("Great!", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ExtendedEditText userName = customLayout.findViewById(R.id.extended_edit_text1);
                                editor.putString("user_name", userName.getText().toString());
                                editor.commit();
                                ChocoBar.builder().setActivity(MainActivity.this)
                                        .setText("Success!")
                                        .setDuration(ChocoBar.LENGTH_SHORT)
                                        .build()
                                        .show();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }

                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;
                    case R.id.action_about_developer:
                        startActivity(new Intent(MainActivity.this, AboutDeveloperActivity.class));
                        break;
                    case R.id.action_logout:
                        new DroidDialog.Builder(MainActivity.this)
                                .icon(R.drawable.ic_exit_to_app_black_24dp)
                                .title("Logout")
                                .content("Are you sure about this?")
                                .cancelable(true, false)
                                .positiveButton("Yes, I'm sure", new DroidDialog.onPositiveListener() {

                                    @Override
                                    public void onPositive(Dialog droidDialog) {
                                        getApplicationContext().getSharedPreferences("ACCOUNT_PREF", 0).edit().clear().commit();
                                        getApplicationContext().getSharedPreferences("firstrun", 0).edit().clear().commit();
                                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().clear().commit();
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }

                                })
                                .negativeButton("No", new DroidDialog.onNegativeListener() {

                                    @Override
                                    public void onNegative(Dialog droidDialog) {
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }

                                })
                                .color(ContextCompat.getColor(MainActivity.this, R.color.colorAccent), ContextCompat.getColor(MainActivity.this, R.color.white),
                                        ContextCompat.getColor(MainActivity.this, R.color.colorAccent))
                                .show();
                        break;
                    case R.id.action_whats_new:
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
                                .setItems(arrayList)
                                .setCancelButtonListener(new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }

                                })
                                .showDialog(MainActivity.this);
                        break;
                }
                return false;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "fcm_default_channel";
            String channelName = "Weather";
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                    }
                });
        mt = new MainActivity.MyTask();
        ShortcutUtils shortcutUtils = new ShortcutUtils(this);
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
        Shortcut searchFilmsShortcut = new Shortcut.ShortcutBuilder()
                .setShortcutIcon(R.drawable.ic_videocam_black_24dp)
                .setShortcutId("mySearchFilmsShortcut")
                .setShortcutLongLabel("Search Films")
                .setShortcutShortLabel("Search Films")
                .setIntentAction("mySearchFilmsShortcut")
                .setIntentStringExtraKey("mySearchFilmsShortcut")
                .setIntentStringExtraValue("mySearchFilmsShortcut")
                .build();
        Shortcut searchCinemaShortcut = new Shortcut.ShortcutBuilder()
                .setShortcutIcon(R.drawable.ic_personal_video_black_24dp)
                .setShortcutId("mySearchCinemasShortcut")
                .setShortcutLongLabel("Search Cinemas")
                .setShortcutShortLabel("Search Cinemas")
                .setIntentAction("mySearchCinemasShortcut")
                .setIntentStringExtraKey("mySearchCinemasShortcut")
                .setIntentStringExtraValue("mySearchCinemasShortcut")
                .build();
        shortcutUtils.addDynamicShortCut(searchFilmsShortcut, new IReceiveStringExtra() {

            @Override
            public void onReceiveStringExtra(String stringExtraKey, String stringExtraValue) {
                String intent = getIntent().getStringExtra(stringExtraKey);
                if (intent != null) {
                    if (intent.equals("mySearchFilmsShortcut")) {
                        Intent ticketIntent = new Intent(MainActivity.this, SearchFilmActivity.class);
                        startActivity(ticketIntent);
                    }
                }
            }

        });
        shortcutUtils.addDynamicShortCut(searchCinemaShortcut, new IReceiveStringExtra() {

            @Override
            public void onReceiveStringExtra(String stringExtraKey, String stringExtraValue) {
                String intent = getIntent().getStringExtra(stringExtraKey);
                if (intent != null) {
                    if (intent.equals("mySearchCinemasShortcut")) {
                        Intent ticketIntent = new Intent(MainActivity.this, SearchCinemaActivity.class);
                        startActivity(ticketIntent);
                    }
                }
            }

        });
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
                                Intent intent = new Intent(MainActivity.this, AboutFilmActivity.class);
                                intent.putExtra("filmId", randomFilmId);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                                call.cancel();
                                Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
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
                                Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                                startActivity(intent);
                            }

                        });
                    }
                }

            }

        });
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
                        Call<List<FilmAPI>> call = apiInterface.getFilms();
                        call.enqueue(new Callback<List<FilmAPI>>() {

                            @Override
                            public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                                films = response.body();
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
                                Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                                startActivity(intent);
                            }

                        });
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
                                Intent intent = new Intent(MainActivity.this, AboutFilmActivity.class);
                                intent.putExtra("filmId", randomFilmId);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                                call.cancel();
                                Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
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
        ChocoBar.builder().setActivity(MainActivity.this)
                .setText("Welcome back, " + pref.getString("user_name", null))
                .setDuration(ChocoBar.LENGTH_SHORT)
                .build()
                .show();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        MainFlowFragment mainFlowFragment = new MainFlowFragment();
        ft.replace(R.id.fragment, mainFlowFragment);
        ft.commit();
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Launch stories with available films from passed list
     *
     * @param films list of films for displaying
     */
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
                myStories.add(temp);
                i++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
            intent.putExtra("isAppError", true);
            startActivity(intent);
        }
        new StoryView.Builder(getSupportFragmentManager())
                .setStoriesList(myStories)
                .setStoryDuration(5000)
                .setStoryClickListeners(new StoryClickListeners() {

                    @Override
                    public void onDescriptionClickListener(int position) {
                        Intent intent = new Intent(MainActivity.this, AboutFilmActivity.class);
                        intent.putExtra("filmId", films.get(position).getId());
                        startActivity(intent);
                    }

                    @Override
                    public void onTitleIconClickListener(int position) {

                    }

                })
                .build()
                .show();
    }

    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            llProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Intent intent = new Intent(MainActivity.this, ErrorActivity.class);
                intent.putExtra("isAppError", true);
                startActivity(intent);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            llProgressBar.setVisibility(View.GONE);
        }

    }

    /**
     * Get random number from available set
     *
     * @param input available numbers
     * @return random number
     */
    public int getRandomNum(List<Integer> input) {
        Random r = new Random();
        int nextRandomNumberIndex = r.nextInt(input.size());
        return input.get(nextRandomNumberIndex);
    }

    @Override
    public void onBackPressed() {

    }

}
