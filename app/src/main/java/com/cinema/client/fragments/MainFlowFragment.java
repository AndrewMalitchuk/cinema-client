package com.cinema.client.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cinema.client.R;
import com.cinema.client.activity.AboutCinemaActivity;
import com.cinema.client.activity.AboutFilmActivity;
import com.cinema.client.activity.PosterActivity;
import com.cinema.client.activity.SearchCinemaActivity;
import com.cinema.client.activity.SearchFilmActivity;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.freegeek.android.materialbanner.MaterialBanner;
import com.freegeek.android.materialbanner.simple.SimpleBannerData;
import com.freegeek.android.materialbanner.simple.SimpleViewHolderCreator;
import com.freegeek.android.materialbanner.view.indicator.CirclePageIndicator;
import com.freegeek.android.materialbanner.view.indicator.LinePageIndicator;
import com.liangfeizc.avatarview.AvatarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFlowFragment extends Fragment {


    private static int[] images = {
            R.drawable.once_upon_a_time,
            R.drawable.drive_2011,
            R.drawable.pulp_fiction};

    private static int[] banner = {
            R.drawable.once_upon_a_time,
            R.drawable.drive_2011,
            R.drawable.pulp_fiction};


    //    private MaterialBanner<SimpleBannerData> materialBanner;
//    private MaterialBanner<SimpleBannerData> adsBanner;
    private TextView textView;

    private CirclePageIndicator circlePageIndicator;
    private CirclePageIndicator circlePageIndicator1;
    private LinePageIndicator linePageIndicator;


    List<SimpleBannerData> list = new ArrayList<>();
    List<Integer> icons = new ArrayList<>();


    @BindView(R.id.linLayout)
    LinearLayout linLayout;

    @BindView(R.id.button21)
    Button button21;

    @BindView(R.id.textView23)
    TextView textView23;

    @BindView(R.id.material_banner)
    MaterialBanner<SimpleBannerData> materialBanner;

    @BindView(R.id.material_banner_adds)
    MaterialBanner<SimpleBannerData> adsBanner;


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


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        initIndicator();
        initData();


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
                Toast.makeText(getActivity(), "click:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        if (materialBanner.isTurning()) {
            materialBanner.stopTurning();
            Toast.makeText(getActivity(), "stop turning", Toast.LENGTH_SHORT).show();
        } else {
            materialBanner.startTurning(3000);
            Toast.makeText(getActivity(), "start turning", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "click:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        if (adsBanner.isTurning()) {
            adsBanner.stopTurning();
            Toast.makeText(getActivity(), "stop turning", Toast.LENGTH_SHORT).show();
        } else {
            adsBanner.startTurning(3000);
            Toast.makeText(getActivity(), "start turning", Toast.LENGTH_SHORT).show();
        }
        adsBanner.setIndicatorInside(!adsBanner.isIndicatorInside());
        //


        //

//        button21.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onAboutFilmClick(view);
//            }
//        });

        button21.setOnClickListener(e -> onAboutFilmClick(e));
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

    }


    private void initIndicator() {
        circlePageIndicator = new CirclePageIndicator(getActivity());
        circlePageIndicator.setStrokeColor(R.color.colorLoginActivityText);
        circlePageIndicator.setFillColor(R.color.colorLoginActivityText);
        circlePageIndicator.setRadius(MaterialBanner.dip2Pix(getActivity(), 3));
        circlePageIndicator.setBetween(20);

        linePageIndicator = new LinePageIndicator(getActivity());
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
        circlePageIndicator1 = new CirclePageIndicator(getActivity());
        circlePageIndicator1.setStrokeColor(R.color.colorLoginActivityText);
        circlePageIndicator1.setFillColor(R.color.colorLoginActivityText);
        circlePageIndicator1.setRadius(MaterialBanner.dip2Pix(getActivity(), 3));
        circlePageIndicator1.setBetween(20);

        linePageIndicator = new LinePageIndicator(getActivity());
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
        switch (view.getId()) {
            case R.id.comedyAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "comedyAvatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "actionAvatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.historicalAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "historicalAvatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sciFiAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "sciFiAvatar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.horrorAvatar:
                intent = new Intent(getActivity(), PosterActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "horrorAvatar", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onCityIconClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivano_frankivsk:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "ivano_frankivsk", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lviv:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "lviv", Toast.LENGTH_SHORT).show();
                break;
            case R.id.kiyv:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "kiyv", Toast.LENGTH_SHORT).show();
                break;
            case R.id.kharkiv:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "kharkiv", Toast.LENGTH_SHORT).show();
                break;
            case R.id.odessa:
                intent = new Intent(getActivity(), SearchCinemaActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "odessa", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onSelectedCinemaClick(View view) {
        Intent intent = new Intent(getActivity(), AboutCinemaActivity.class);
        startActivity(intent);
    }
}
