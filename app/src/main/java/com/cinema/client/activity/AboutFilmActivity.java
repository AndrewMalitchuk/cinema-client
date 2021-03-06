package com.cinema.client.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.cinema.client.R;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.FilmAPI;
import com.devs.readmoreoption.ReadMoreOption;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.rw.loadingdialog.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gujun.android.taggroup.TagGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutFilmActivity extends AppCompatActivity {

    APIInterface apiInterface;

    @BindView(R.id.cinemaLocationCinemaActivityTextView)
    TextView filmDescriptionFilmActivityEditText;

    @BindView(R.id.mScrollView)
    ScrollView scrollView;

    @BindView(R.id.floatingActionButton2)
    FloatingActionButton fab;

    @BindView(R.id.linLayout)
    LinearLayout linLayout;

    @BindView(R.id.cinemaNameBigCinemaActivityTextView)
    AppCompatTextView filmTitleFilmActivityEditText;

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    @BindView(R.id.telephoneAboutCinemaTextView)
    AppCompatTextView dateFilmActivityEditText;

    @BindView(R.id.youtubePreviewFilmActivityPlayerView)
    YouTubePlayerView youtubePreviewFilmActivityPlayerView;

    @BindView(R.id.filmPosterFilmActivityImageView)
    KenBurnsView filmPosterFilmActivityImageView;

    @BindView(R.id.additionalInfoFilmActivityTagGroup)
    TagGroup additionalInfoFilmActivityTagGroup;

    private ReadMoreOption readMoreOption;

    private FilmAPI currentFilm;

    private LoadingView loadingView;

    @BindView(R.id.frame)
    RelativeLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_film);
        ButterKnife.bind(this);
        myToolbar.setTitle(R.string.loadingMessage);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
        loadingView = new LoadingView.Builder(getApplication())
                .setProgressColorResource(R.color.colorAccent)
                .setBackgroundColorRes(R.color.white)
                .setProgressStyle(LoadingView.ProgressStyle.CYCLIC)
                .attachTo(frame);

        loadingView.show();
        getLifecycle().addObserver(youtubePreviewFilmActivityPlayerView);
        youtubePreviewFilmActivityPlayerView.enableBackgroundPlayback(false);
        youtubePreviewFilmActivityPlayerView.setEnableAutomaticInitialization(false);
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(linLayout)
                .setTransitionDuration(4000)
                .start();
        readMoreOption = new ReadMoreOption.Builder(this)
                .textLength(3, ReadMoreOption.TYPE_LINE)
                .moreLabel("MORE")
                .lessLabel("LESS")
                .moreLabelColor(R.color.colorAccent)
                .lessLabelColor(Color.BLUE)
                .labelUnderLine(true)
                .expandAnimation(true)
                .build();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        int id = getIntent().getIntExtra("filmId", -1);
        if (id == -1) {
            id = 12;
        }
        Call<FilmAPI> call = apiInterface.getFilmById(id);
        call.enqueue(new Callback<FilmAPI>() {

            @Override
            public void onResponse(Call<FilmAPI> call, Response<FilmAPI> response) {
                currentFilm = response.body();
                setContent(currentFilm);
            }

            @Override
            public void onFailure(Call<FilmAPI> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(AboutFilmActivity.this, ErrorActivity.class);
                intent.putExtra("isNetworkError", true);
                startActivity(intent);
            }

        });
    }

    /**
     * Opens activity for zooming film's poster
     *
     * @param view
     */
    public void onImageClick(View view) {
        Intent intent = new Intent(this, ZoomImageActivity.class);
        intent.putExtra("url", currentFilm.getPicUrl());
        startActivity(intent);
    }

    /**
     * The handler for FAB click; launch BillActivity
     *
     * @param view
     */
    public void onFabClick(View view) {
        Intent intent = new Intent(AboutFilmActivity.this, BillActivity.class);
        intent.putExtra("filmTitle", currentFilm.getTitle());
        intent.putExtra("filmId", currentFilm.getId());
        String cinemaName = getIntent().getStringExtra("cinemaName");
        int cinemaId = getIntent().getIntExtra("cinemaId", -1);
        if (cinemaId != -1 && cinemaName != null) {
            intent.putExtra("cinemaId", cinemaId);
            intent.putExtra("cinemaName", cinemaName);
        }
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_film_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, currentFilm.getTitle());
                share.putExtra(Intent.EXTRA_TEXT, currentFilm.getVideoUrl());
                startActivity(Intent.createChooser(share, "cinema-app"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * The method that set content for activity.
     *
     * @param content  current Cinema entity
     */
    public void setContent(FilmAPI content) {
        if (content != null) {
            Glide.with(this).load(APIClient.HOST + content.getPicUrl()).into(filmPosterFilmActivityImageView);
            filmTitleFilmActivityEditText.setText(content.getTitle());
            myToolbar.setTitle(content.getTitle());
            readMoreOption.addReadMoreTo(filmDescriptionFilmActivityEditText, content.getDescription());
            dateFilmActivityEditText.setText(content.getDate());
            additionalInfoFilmActivityTagGroup.setTags(content.getDuration().toString() + " min.");
            youtubePreviewFilmActivityPlayerView.getYouTubePlayerWhenReady(e -> {
                e.cueVideo(content.getVideoUrl().split("v=")[1], 0);
            });
            loadingView.hide();
        } else {
            Intent intent = new Intent(this, ErrorActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
