package com.cinema.client.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
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
import com.liangfeizc.avatarview.AvatarView;

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

public class MainFlowFragment extends Fragment {


//    private static int[] images = {
//            R.drawable.once_upon_a_time,
//            R.drawable.drive_2011,
//            R.drawable.pulp_fiction};
//
//    private static int[] banner = {
//            R.drawable.once_upon_a_time,
//            R.drawable.drive_2011,
//            R.drawable.pulp_fiction};


//    private TextView textView;
//
//    private CirclePageIndicator circlePageIndicator;
//    private CirclePageIndicator circlePageIndicator1;
//    private LinePageIndicator linePageIndicator;


    List<SimpleBannerData> list = new ArrayList<>();
    List<Integer> icons = new ArrayList<>();


    @BindView(R.id.linLayout)
    LinearLayout linLayout;

    @BindView(R.id.filmMoreMainFlowFragmentButton)
    Button filmMoreMainFlowFragmentButton;

    @BindView(R.id.textView23)
    TextView textView23;

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

    @BindView(R.id.av1)
    AvatarView av1;

    @BindView(R.id.av2)
    AvatarView av2;

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

    private DemoInfiniteAdapter adapter;

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


        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(linLayout)
                .setTransitionDuration(4000)
                .start();
        //



        filmMoreMainFlowFragmentButton.setOnClickListener(e -> onAboutFilmClick(e));
        buttonCinema.setOnClickListener(e -> onFindCinemasButtonClick(e));
        buttonFilms.setOnClickListener(e -> onFindFilmsButtonClick(e));
        av1.setOnClickListener(e -> onSelectedCinemaClick(e));
        av2.setOnClickListener(e -> onSelectedCinemaClick(e));


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

        Call<List<FilmAPI>> call=apiInterface.getFilms();

        call.enqueue(new Callback<List<FilmAPI>>() {
            @Override
            public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {

                films=response.body();
                Log.d("FILMS", films.size()+"");

                Collections.sort(films, new Comparator<FilmAPI>() {
                    @Override
                    public int compare(FilmAPI filmAPI, FilmAPI t1) {
                        return  t1.getDate().compareTo(filmAPI.getDate());
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

        //




    }




//    private void initIndicator() {
//        circlePageIndicator = new CirclePageIndicator(getActivity());
//        circlePageIndicator.setStrokeColor(R.color.colorLoginActivityText);
//        circlePageIndicator.setFillColor(R.color.colorLoginActivityText);
//        circlePageIndicator.setRadius(MaterialBanner.dip2Pix(getActivity(), 3));
//        circlePageIndicator.setBetween(20);
//
//        linePageIndicator = new LinePageIndicator(getActivity());
//        linePageIndicator.setUnselectedColor(Color.BLACK);
////        linePageIndicator.setSelectedColor(Color.YELLOW);
//
//    }

//    private void initData() {
//        for (int i = 0; i < images.length; i++) {
//            SimpleBannerData simpleBannerData = new SimpleBannerData();
////            simpleBannerData.setTitle("Country road " + (i + 1));
//            simpleBannerData.setResId(images[i]);
//            list.add(simpleBannerData);
//
//        }
//    }


//    private void initIndicatorForBanner() {
//        circlePageIndicator1 = new CirclePageIndicator(getActivity());
//        circlePageIndicator1.setStrokeColor(R.color.colorLoginActivityText);
//        circlePageIndicator1.setFillColor(R.color.colorLoginActivityText);
//        circlePageIndicator1.setRadius(MaterialBanner.dip2Pix(getActivity(), 3));
//        circlePageIndicator1.setBetween(20);
//
//        linePageIndicator = new LinePageIndicator(getActivity());
//        linePageIndicator.setUnselectedColor(Color.BLACK);
//
//    }
//
//    private void initDataForBanner() {
//        for (int i = 0; i < banner.length; i++) {
//            SimpleBannerData simpleBannerData = new SimpleBannerData();
////            simpleBannerData.setTitle("Country road " + (i + 1));
//            simpleBannerData.setResId(banner[i]);
//            list.add(simpleBannerData);
//
//        }
//    }

    public void onFindFilmsButtonClick(View view) {
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


        Call<List<FilmAPI>>call;
        switch (view.getId()) {
            case R.id.comedyAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                call=apiInterface.getFilmByGenre(1);
                call.enqueue(new Callback<List<FilmAPI>>() {
                    @Override
                    public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                        List<FilmAPI> films=response.body();
//                        Log.d("POSTER",films.size()+"");

                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(films).getAsJsonArray();
                        JsonObject jsonObject = new JsonObject();
//                        jsonObject.add( myCustomArray);

                        intent.putExtra("json",myCustomArray.toString());
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
                Toast.makeText(getActivity(), "comedyAvatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                call=apiInterface.getFilmByGenre(2);
                call.enqueue(new Callback<List<FilmAPI>>() {
                    @Override
                    public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                        List<FilmAPI> films=response.body();
//                        Log.d("POSTER",films.size()+"");

                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(films).getAsJsonArray();
                        JsonObject jsonObject = new JsonObject();
//                        jsonObject.add( myCustomArray);

                        intent.putExtra("json",myCustomArray.toString());
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
                Toast.makeText(getActivity(), "comedyAvatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.historicalAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                call=apiInterface.getFilmByGenre(3);
                call.enqueue(new Callback<List<FilmAPI>>() {
                    @Override
                    public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                        List<FilmAPI> films=response.body();
//                        Log.d("POSTER",films.size()+"");

                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(films).getAsJsonArray();
                        JsonObject jsonObject = new JsonObject();
//                        jsonObject.add( myCustomArray);

                        intent.putExtra("json",myCustomArray.toString());
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
                Toast.makeText(getActivity(), "comedyAvatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sciFiAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                call=apiInterface.getFilmByGenre(4);
                call.enqueue(new Callback<List<FilmAPI>>() {
                    @Override
                    public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                        List<FilmAPI> films=response.body();
//                        Log.d("POSTER",films.size()+"");

                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(films).getAsJsonArray();
                        JsonObject jsonObject = new JsonObject();
//                        jsonObject.add( myCustomArray);

                        intent.putExtra("json",myCustomArray.toString());
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
                Toast.makeText(getActivity(), "comedyAvatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.horrorAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                call=apiInterface.getFilmByGenre(5);
                call.enqueue(new Callback<List<FilmAPI>>() {
                    @Override
                    public void onResponse(Call<List<FilmAPI>> call, Response<List<FilmAPI>> response) {
                        List<FilmAPI> films=response.body();
//                        Log.d("POSTER",films.size()+"");

                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(films).getAsJsonArray();
                        JsonObject jsonObject = new JsonObject();
//                        jsonObject.add( myCustomArray);

                        intent.putExtra("json",myCustomArray.toString());
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
                Toast.makeText(getActivity(), "comedyAvatar", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onCityIconClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivano_frankivsk:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                intent.putExtra("cityId",1);
                startActivity(intent);
                Toast.makeText(getActivity(), "ivano_frankivsk", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lviv:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                intent.putExtra("cityId",2);
                startActivity(intent);
                Toast.makeText(getActivity(), "lviv", Toast.LENGTH_SHORT).show();
                break;
            case R.id.kiyv:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                intent.putExtra("cityId",3);
                startActivity(intent);
                Toast.makeText(getActivity(), "kiyv", Toast.LENGTH_SHORT).show();
                break;
            case R.id.kharkiv:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                intent.putExtra("cityId",4);
                startActivity(intent);
                Toast.makeText(getActivity(), "kharkiv", Toast.LENGTH_SHORT).show();
                break;
            case R.id.odessa:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                intent.putExtra("cityId",5);
                startActivity(intent);
                Toast.makeText(getActivity(), "odessa", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onSelectedCinemaClick(View view) {
        Intent intent = new Intent(getActivity(), AboutCinemaActivity.class);
        startActivity(intent);
    }

    public void setContent(List<FilmAPI> content){

        Log.d("0",content.get(0).getDate());
        Log.d("0",content.get(0).getTitle());
        Log.d("1",content.get(1).getDate());
        Log.d("1",content.get(1).getTitle());
        Log.d("2",content.get(2).getDate());
        Log.d("2",content.get(2).getTitle());


        ArrayList<FilmAPI> currentFilms=new ArrayList<>();

        currentFilms.add(content.get(0));
        currentFilms.add(content.get(1));
        currentFilms.add(content.get(2));


        adapter = new DemoInfiniteAdapter(getContext(), currentFilms, true);
        viewPager.setAdapter(adapter);

        viewPager.setIndicatorPageChangeListener(new LoopingViewPager.IndicatorPageChangeListener() {
            @Override
            public void onIndicatorProgress(int selectingPosition, float progress) {
                filmTitleMainFlowFragmentTextView.setText(currentFilms.get(selectingPosition).getTitle());
                filmDateMainFlowFragmentTextView.setText(currentFilms.get(selectingPosition).getDate());
                filmDurationMainFlowFragmentTextView.setText(currentFilms.get(selectingPosition).getDuration()+"");
                filmMoreMainFlowFragmentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(),currentFilms.get(selectingPosition).getTitle(),Toast.LENGTH_SHORT).show();
                    }
                });

            }


            @Override
            public void onIndicatorPageChange(int newIndicatorPosition) {
//                indicatorView.setSelection(newIndicatorPosition);
            }
        });






    }
}
