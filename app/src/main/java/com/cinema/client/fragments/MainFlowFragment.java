package com.cinema.client.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.cinema.client.R;
import com.cinema.client.activity.AboutCinemaActivity;
import com.cinema.client.activity.AboutFilmActivity;
import com.cinema.client.activity.ErrorActivity;
import com.cinema.client.activity.Main3Activity;
import com.cinema.client.activity.PosterActivity;
import com.cinema.client.activity.SearchCinemaActivity;
import com.cinema.client.activity.SearchFilmActivity;
import com.cinema.client.adapters.DemoInfiniteAdapter;
import com.cinema.client.adapters.FavouriteCinemasAdapter;
import com.cinema.client.entities.CinemaItemSearch;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.CinemaAPI;
import com.cinema.client.requests.entities.FilmAPI;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.freegeek.android.materialbanner.MaterialBanner;
import com.freegeek.android.materialbanner.simple.SimpleBannerData;
import com.freegeek.android.materialbanner.simple.SimpleViewHolderCreator;
import com.freegeek.android.materialbanner.view.indicator.CirclePageIndicator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.liangfeizc.avatarview.AvatarView;
import com.pd.chocobar.ChocoBar;
import com.rw.loadingdialog.LoadingView;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static android.content.Context.MODE_PRIVATE;

public class MainFlowFragment extends Fragment {


    List<SimpleBannerData> list = new ArrayList<>();
    List<Integer> icons = new ArrayList<>();


    @BindView(R.id.linLayout)
    LinearLayout linLayout;

    @BindView(R.id.filmMoreMainFlowFragmentButton)
    Button filmMoreMainFlowFragmentButton;

    @BindView(R.id.textView23)
    TextView textView23;

    @BindView(R.id.refreshMainFlow)
    SwipeRefreshLayout refreshMainFlow;

//    @BindView(R.id.material_banner)
//    MaterialBanner<SimpleBannerData> materialBanner;
//
//    @BindView(R.id.material_banner_adds)
//    MaterialBanner<SimpleBannerData> adsBanner;


//    @BindView(R.id.)
//    ;

    @BindView(R.id.buttonCinema)
    Button buttonCinema;

    @BindView(R.id.buttonFilms)
    Button buttonFilms;


    @BindView(R.id.comedyAvatar)
    AvatarView comedyAvatar;

    @BindView(R.id.actionAvatar)
    AvatarView actionAvatar;

    @BindView(R.id.historicalAvatar)
    AvatarView historicalAvatar;

    @BindView(R.id.sciFiAvatar)
    AvatarView sciFiAvatar;

    @BindView(R.id.horrorAvatar)
    AvatarView horrorAvatar;


    @BindView(R.id.ivano_frankivsk)
    AvatarView ivano_frankivsk;

    @BindView(R.id.lviv)
    AvatarView lviv;

    @BindView(R.id.kiyv)
    AvatarView kiyv;

    @BindView(R.id.kharkiv)
    AvatarView kharkiv;

    @BindView(R.id.odessa)
    AvatarView odessa;


//
//    @BindView(R.id.)
//    ;

    private APIInterface apiInterface;

    private List<FilmAPI> films;


    @BindView(R.id.filmTitleMainFlowFragmentTextView)
    TextView filmTitleMainFlowFragmentTextView;

    @BindView(R.id.filmDateMainFlowFragmentTextView)
    TextView filmDateMainFlowFragmentTextView;

    @BindView(R.id.filmDurationMainFlowFragmentTextView)
    TextView filmDurationMainFlowFragmentTextView;


    @BindView(R.id.viewpager)
    LoopingViewPager viewPager;

    @BindView(R.id.favCinemasMainFlowFragmentRecyclerView)
    RecyclerView favCinemasMainFlowFragmentRecyclerView;

    @BindView(R.id.favCinemasMainFlowFragmentCardView)
    CardView favCinemasMainFlowFragmentCardView;

//    @BindView(R.id.reloadMainFlowFragmentSwipeRefreshLayout)
//    SwipeRefreshLayout reloadMainFlowFragmentSwipeRefreshLayout;


    private DemoInfiniteAdapter adapter;
    private FavouriteCinemasAdapter favouriteCinemasAdapter;

    public static final String FAVOURITE_CINEMAS_PREF = "favourite_cinema_pref";
    private SharedPreferences sharedpreferences;


    private SharedPreferences prefForCheckingFirstRun;

    LoadingView loadingView;


//    @BindView(R.id.frame)
//    FrameLayout frame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//
        View view = inflater.inflate(R.layout.fragment_main_flow, container, false);
        ButterKnife.bind(this, view);
        return view;


//
//        View inputFragmentView = inflater.inflate(R.layout.fragment_main_flow, container, false);
//
//
//        return inputFragmentView;
//        return inflater.inflate(R.layout.fragment_main_flow, container, false);


        //


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

//        initIndicator();
//        initData();

        //
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(linLayout)
                .setTransitionDuration(4000)
                .start();
        //


//        loadingView = new LoadingView.Builder(getContext())
//                .setProgressColorResource(R.color.colorAccent)
//                .setBackgroundColorRes(R.color.white)
//                .setProgressStyle(LoadingView.ProgressStyle.CYCLIC)
//                .attachTo(frame);
//
//        loadingView.show();

        //


        filmMoreMainFlowFragmentButton.setOnClickListener(e -> onAboutFilmClick(e));
        buttonCinema.setOnClickListener(e -> onFindCinemasButtonClick(e));
        buttonFilms.setOnClickListener(e -> onFindFilmsButtonClick(e));


        comedyAvatar.setOnClickListener(e -> onGenreIconClick(e));
        actionAvatar.setOnClickListener(e -> onGenreIconClick(e));
        historicalAvatar.setOnClickListener(e -> onGenreIconClick(e));
        sciFiAvatar.setOnClickListener(e -> onGenreIconClick(e));
        horrorAvatar.setOnClickListener(e -> onGenreIconClick(e));


        ivano_frankivsk.setOnClickListener(e -> onCityIconClick(e));
        lviv.setOnClickListener(e -> onCityIconClick(e));
        kiyv.setOnClickListener(e -> onCityIconClick(e));
        odessa.setOnClickListener(e -> onCityIconClick(e));
        kharkiv.setOnClickListener(e -> onCityIconClick(e));

        //

        apiInterface = APIClient.getClient().create(APIInterface.class);


        update();

        //
        refreshMainFlow.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
            }
        });
        //


        prefForCheckingFirstRun = getActivity().getSharedPreferences("com.cinema.client", MODE_PRIVATE);


        //


    }

    public void update() {
        sharedpreferences = getActivity().getSharedPreferences(FAVOURITE_CINEMAS_PREF, MODE_PRIVATE);

        if (sharedpreferences != null) {

//            SharedPreferences.Editor editor = sharedpreferences.edit();


            String fav_json = sharedpreferences.getString("fav_json", null);

            if (fav_json != null) {
                Gson gson = new GsonBuilder().create();
                List<Integer> favourite_cinema_id_list = gson.fromJson(fav_json, new TypeToken<List<Integer>>() {
                }.getType());


                if (favourite_cinema_id_list.size() == 0) {
                    favCinemasMainFlowFragmentCardView.setVisibility(View.GONE);
                } else {
                    favCinemasMainFlowFragmentCardView.setVisibility(View.VISIBLE);
                }

//                if(favourite_cinema_id_list.size()==0) {

                List<CinemaItemSearch> list = new ArrayList<>();

                for (Integer i : favourite_cinema_id_list) {
                    Call<CinemaAPI> call = apiInterface.getCinemaById(i);
                    try {
                        Response<CinemaAPI> temp = call.execute();

                        CinemaItemSearch cinemaItemSearch = new CinemaItemSearch();
                        cinemaItemSearch.setCinemaId(temp.body().getId());
                        cinemaItemSearch.setCinemaName(temp.body().getName());
                        cinemaItemSearch.setCinemaImg(APIClient.HOST + temp.body().getPicUrl());

                        list.add(cinemaItemSearch);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                favCinemasMainFlowFragmentRecyclerView.setHasFixedSize(false);

                FavouriteCinemasAdapter favouriteCinemasAdapter = new FavouriteCinemasAdapter(list);


                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false);
                favCinemasMainFlowFragmentRecyclerView.setLayoutManager(mLayoutManager);
                favCinemasMainFlowFragmentRecyclerView.setAdapter(favouriteCinemasAdapter);
//                }

            }


        }

        //

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

                setContent(films);


            }

            @Override
            public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(getContext(), ErrorActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onFindFilmsButtonClick(View view) {
        //XXX
//        refreshMainFlow.setRefreshing(false);
        Intent intent = new Intent(getActivity(), SearchFilmActivity.class);
        startActivity(intent);
    }

    public void onFindCinemasButtonClick(View view) {
        Intent intent = new Intent(getActivity(), SearchCinemaActivity.class);
        startActivity(intent);
    }

    public void onAboutFilmClick(View view) {
        Intent intent = new Intent(getActivity(), AboutFilmActivity.class);
        startActivity(intent);
    }

    public void onGenreIconClick(View view) {
        Intent intent;


        Call<List<FilmAPI>> call;
        switch (view.getId()) {
            case R.id.comedyAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                call = apiInterface.getFilmByGenre(1);
                call.enqueue(new Callback<List<FilmAPI>>() {
                    @Override
                    public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                        List<FilmAPI> films = response.body();

                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(films).getAsJsonArray();
                        JsonObject jsonObject = new JsonObject();
//                        jsonObject.add( myCustomArray);

                        intent.putExtra("json", myCustomArray.toString());
                        intent.putExtra("genre", "Comedy");
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                        call.cancel();
                        Intent intent = new Intent(getContext(), ErrorActivity.class);
                        startActivity(intent);
                    }
                });


//                startActivity(intent);
                break;
            case R.id.actionAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                call = apiInterface.getFilmByGenre(2);
                call.enqueue(new Callback<List<FilmAPI>>() {
                    @Override
                    public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                        List<FilmAPI> films = response.body();

                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(films).getAsJsonArray();
                        JsonObject jsonObject = new JsonObject();
//                        jsonObject.add( myCustomArray);

                        intent.putExtra("json", myCustomArray.toString());
                        intent.putExtra("genre", "Action");

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                        call.cancel();
                        Intent intent = new Intent(getContext(), ErrorActivity.class);
                        startActivity(intent);
                    }
                });


//                startActivity(intent);
                break;
            case R.id.historicalAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                call = apiInterface.getFilmByGenre(3);
                call.enqueue(new Callback<List<FilmAPI>>() {
                    @Override
                    public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                        List<FilmAPI> films = response.body();

                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(films).getAsJsonArray();
                        JsonObject jsonObject = new JsonObject();
//                        jsonObject.add( myCustomArray);

                        intent.putExtra("json", myCustomArray.toString());
                        intent.putExtra("genre", "Historical");

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                        call.cancel();
                        Intent intent = new Intent(getContext(), ErrorActivity.class);
                        startActivity(intent);
                    }
                });


//                startActivity(intent);
                break;
            case R.id.sciFiAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                call = apiInterface.getFilmByGenre(4);
                call.enqueue(new Callback<List<FilmAPI>>() {
                    @Override
                    public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                        List<FilmAPI> films = response.body();

                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(films).getAsJsonArray();
                        JsonObject jsonObject = new JsonObject();
//                        jsonObject.add( myCustomArray);

                        intent.putExtra("json", myCustomArray.toString());
                        intent.putExtra("genre", "Sci-Fi");

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                        call.cancel();
                        Intent intent = new Intent(getContext(), ErrorActivity.class);
                        startActivity(intent);
                    }
                });


//                startActivity(intent);
                break;
            case R.id.horrorAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                call = apiInterface.getFilmByGenre(5);
                call.enqueue(new Callback<List<FilmAPI>>() {
                    @Override
                    public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                        List<FilmAPI> films = response.body();

                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(films).getAsJsonArray();
                        JsonObject jsonObject = new JsonObject();
//                        jsonObject.add( myCustomArray);

                        intent.putExtra("json", myCustomArray.toString());
                        intent.putExtra("genre", "Horror");

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<FilmAPI>> call, Throwable t) {
                        call.cancel();
                        Intent intent = new Intent(getContext(), ErrorActivity.class);
                        startActivity(intent);
                    }
                });


//                startActivity(intent);
                break;
        }
    }

    public void onCityIconClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivano_frankivsk:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                intent.putExtra("cityId", 1);
                startActivity(intent);
                break;
            case R.id.lviv:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                intent.putExtra("cityId", 2);
                startActivity(intent);
                break;
            case R.id.kiyv:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                intent.putExtra("cityId", 3);
                startActivity(intent);
                break;
            case R.id.kharkiv:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                intent.putExtra("cityId", 4);
                startActivity(intent);
                break;
            case R.id.odessa:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                intent.putExtra("cityId", 5);
                startActivity(intent);
                break;
        }
    }


    public void setContent(List<FilmAPI> content) {


        ArrayList<FilmAPI> currentFilms = new ArrayList<>();

        currentFilms.add(content.get(0));
        currentFilms.add(content.get(1));
        currentFilms.add(content.get(2));


        adapter = new DemoInfiniteAdapter(getContext(), currentFilms, true);
        viewPager.setAdapter(adapter);

        viewPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
            @Override
            public void onIndicatorProgress(int selectingPosition, float progress) {
                filmTitleMainFlowFragmentTextView.setText(currentFilms.get(selectingPosition).getTitle());
                filmDateMainFlowFragmentTextView.setText("Date: " + currentFilms.get(selectingPosition).getDate());
                filmDurationMainFlowFragmentTextView.setText("Dur: " + currentFilms.get(selectingPosition).getDuration() + " min.");
                filmMoreMainFlowFragmentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), AboutFilmActivity.class);
                        intent.putExtra("filmId", currentFilms.get(selectingPosition).getId());
                        startActivity(intent);
                    }
                });

            }


            @Override
            public void onIndicatorPageChange(int newIndicatorPosition) {
//                indicatorView.setSelection(newIndicatorPosition);
            }
        });

        refreshMainFlow.setRefreshing(false);


    }


    public void firstrun() {
        new MaterialTapTargetPrompt.Builder(MainFlowFragment.this)
                .setTarget(R.id.filmMoreMainFlowFragmentButton)
                .setPrimaryText("New films")
                .setSecondaryText("Tap to see trailer")
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                            new MaterialTapTargetPrompt.Builder(MainFlowFragment.this)
                                    .setTarget(R.id.buttonCinema)
                                    .setPrimaryText("Search cinemas nearby")
                                    .setSecondaryText("Tap to start searching")
                                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                        @Override
                                        public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                                new MaterialTapTargetPrompt.Builder(MainFlowFragment.this)
                                                        .setTarget(R.id.buttonFilms)
                                                        .setPrimaryText("Search available films")
                                                        .setSecondaryText("Tap to search films")
                                                        .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                                                            @Override
                                                            public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state) {
                                                                if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                                                    ChocoBar.builder().setActivity(getActivity())
                                                                            .setText("Congratulations!\nFeel yourself at home!")
                                                                            .setDuration(ChocoBar.LENGTH_SHORT)
                                                                            .green()
                                                                            .show();
                                                                }
                                                            }
                                                        })
                                                        .show();
                                            }
                                        }
                                    })
                                    .show();
                        }
                    }
                })
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean previouslyStarted = prefs.getBoolean("firstrun", false);
        if (!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("firstrun", Boolean.TRUE);
            edit.commit();
            firstrun();
        }
    }
}
