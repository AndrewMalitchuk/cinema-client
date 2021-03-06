package com.cinema.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.cinema.client.R;
import com.cinema.client.etc.TimelineItem;
import com.cinema.client.etc.TimelineAdapter;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.FilmAPI;
import com.cinema.client.requests.entities.TimelineAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pd.chocobar.ChocoBar;
import com.rw.loadingdialog.LoadingView;
import com.transferwise.sequencelayout.SequenceLayout;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusActivity extends AppCompatActivity {

    @BindView(R.id.status)
    SequenceLayout sequenceLayout;

    @BindView(R.id.selectedDateTimeTextView)
    TextView selectedDateTimeTextView;

    @BindView(R.id.selectedTimelineStatusActivityTextView)
    TextView selectedTimelineStatusActivityTextView;

    @BindView(R.id.selectedPriceStatusActivityTextView)
    TextView selectedPriceStatusActivityTextView;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.toolbar7)
    Toolbar toolbar;

    @BindView(R.id.calendarView)
    CalendarView calendarView;

    private String dateStr = null;

    private APIInterface apiInterface;

    private boolean isFilmTimeline;

    private TimelineAPI currentTimeline;

    @BindView(R.id.frame)
    ConstraintLayout frame;

    @BindView(R.id.timelineFrame)
    ConstraintLayout timelineFrame;

    @BindView(R.id.selectedCardView)
    CardView selectedCardView;

    private LoadingView loadingView;

    private LoadingView timelineLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        ButterKnife.bind(this);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        apiInterface = APIClient.getClient().create(APIInterface.class);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String cinemaName = getIntent().getStringExtra("cinemaName");
        toolbar.setTitle(cinemaName);
        loadingView = new LoadingView.Builder(getApplication())
                .setProgressColorResource(R.color.colorAccent)
                .setBackgroundColorRes(R.color.white)
                .setProgressStyle(LoadingView.ProgressStyle.CYCLIC)
                .attachTo(frame);
        loadingView.show();
        timelineLoadingView = new LoadingView.Builder(getApplication())
                .setProgressColorResource(R.color.colorAccent)
                .setProgressStyle(LoadingView.ProgressStyle.CYCLIC)
                .attachTo(frame);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
        isFilmTimeline = getIntent().getBooleanExtra("isFilmTimeline", false);
        final int cinema_id = getIntent().getIntExtra("cinemaId", -1);
        final int film_id = getIntent().getIntExtra("filmId", -1);
        if (isFilmTimeline) {
            calendarView.setDate(new Date(new Date().getYear(), new Date().getMonth(), new Date().getDate()).getTime(), true, true);
            setTimeline(
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date(calendarView.getDate())),
                    cinema_id,
                    film_id);
        } else {
            floatingActionButton.hide();
            selectedCardView.setVisibility(View.GONE);
            try {
                calendarView.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(getIntent().getStringExtra("selectedDate")).getTime(), true, true);
            } catch (ParseException e) {
                e.printStackTrace();
                Intent intent = new Intent(StatusActivity.this, ErrorActivity.class);
                intent.putExtra("isAppError", true);
                startActivity(intent);
            }
            setTimeline(getIntent().getStringExtra("selectedDate"), cinema_id);
        }
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                timelineLoadingView.show();
                dateStr = year + "-" + (month + 1) + "-" + day;
                selectedDateTimeTextView.setText("Nothing yet...");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date temp = new Date();
                temp.setYear(year - 1900);
                temp.setMonth(month);
                temp.setDate(day);
                String selectedDate = simpleDateFormat.format(temp);
                if (isFilmTimeline) {
                    setTimeline(selectedDate, cinema_id, film_id);
                } else {
                    setTimeline(selectedDate, cinema_id);
                }
            }

        });
    }

    /**
     * Handler for FAB click
     *
     * @param view
     */
    public void onFABClick(View view) {
        String selectedDate = selectedDateTimeTextView.getText().toString();
        if (selectedDate.equals("Nothing yet...")) {
            ChocoBar.builder().setActivity(StatusActivity.this)
                    .setText("Please, select date and time!")
                    .setDuration(ChocoBar.LENGTH_SHORT)
                    .red()
                    .show();
        } else {
            ChocoBar.builder().setActivity(StatusActivity.this)
                    .setText("Selected date:\n" + selectedDateTimeTextView.getText().toString())
                    .setDuration(ChocoBar.LENGTH_SHORT)
                    .green()
                    .show();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(calendarView.getDate()));
            if (dateStr == null) {
                getIntent().putExtra("datetime", date + " " + selectedDateTimeTextView.getText().toString());
            } else {
                getIntent().putExtra("datetime", dateStr + " " + selectedDateTimeTextView.getText().toString());
            }
            getIntent().putExtra("timeline_id", selectedTimelineStatusActivityTextView.getText().toString());
            getIntent().putExtra("price", selectedPriceStatusActivityTextView.getText().toString());
            setResult(RESULT_OK, getIntent());
            finish();
        }
    }

    /**
     * Set content for Timeline
     *
     * @param date      current date
     * @param cinema_id linked Cinema's ID
     */
    private void setTimeline(String date, int cinema_id) {
        List<TimelineItem> list = new ArrayList<>();
        Call<List<TimelineAPI>> call = apiInterface.getTimelineByDateAndCinemaId(date, cinema_id);
        call.enqueue(new Callback<List<TimelineAPI>>() {

            @Override
            public void onResponse(Call<List<TimelineAPI>> call, Response<List<TimelineAPI>> response) {
                try {
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date = new Date();
                    String currentTime = dateFormat.format(date);
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDate = dateFormat.format(date);
                    for (TimelineAPI timeline : response.body()) {
                        FilmAPI film = apiInterface.getFilmById(timeline.getFilmId()).execute().body();
                        list.add(new TimelineItem(false, timeline.getDate(), timeline.getTime(), film.getTitle(), false, timeline.getPrice() + " ₴"));
                    }
                    if (list.size() == 0) {
                        ChocoBar.builder().setActivity(StatusActivity.this)
                                .setText("There are no films for selected date")
                                .setDuration(ChocoBar.LENGTH_SHORT)
                                .red()
                                .show();
                        sequenceLayout.removeAllSteps();
                    } else {
                        Collections.sort(list, new Comparator<TimelineItem>() {

                            @Override
                            public int compare(TimelineItem u1, TimelineItem u2) {
                                return u1.getFormattedDate().compareTo(u2.getFormattedDate());
                            }

                        });
                        sequenceLayout.setAdapter(new TimelineAdapter(list, getApplicationContext(), selectedDateTimeTextView, selectedTimelineStatusActivityTextView, selectedPriceStatusActivityTextView, currentDate, currentTime));
                    }
                    sequenceLayout.setAdapter(new TimelineAdapter(list, getApplicationContext(), selectedDateTimeTextView, selectedTimelineStatusActivityTextView, selectedPriceStatusActivityTextView, currentDate, currentTime));
                    loadingView.hide();
                    timelineLoadingView.hide();
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent intent = new Intent(StatusActivity.this, ErrorActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<TimelineAPI>> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(StatusActivity.this, ErrorActivity.class);
                startActivity(intent);
                ;
            }

        });
    }

    /**
     * Set content for Timeline
     *
     * @param date      current date
     * @param cinema_id linked Cinema's ID
     * @param film_id   linked Film's ID
     */
    private void setTimeline(String date, int cinema_id, int film_id) {
        List<TimelineItem> list = new ArrayList<>();
        Call<List<TimelineAPI>> call = apiInterface.getTimelineByDateAndCinemaIdAndFilmId(date, cinema_id, film_id);
        call.enqueue(new Callback<List<TimelineAPI>>() {

            @Override
            public void onResponse(Call<List<TimelineAPI>> call, Response<List<TimelineAPI>> response) {
                try {
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    Date date = new Date();
                    String currentTime = dateFormat.format(date);
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDate = dateFormat.format(date);
                    for (TimelineAPI timeline : response.body()) {
                        FilmAPI film = apiInterface.getFilmById(timeline.getFilmId()).execute().body();
                        list.add(new TimelineItem(false, timeline.getDate(), timeline.getTime(), film.getTitle(), false, timeline.getPrice() + " ₴", timeline));
                    }
                    if (list.size() == 0) {
                        ChocoBar.builder().setActivity(StatusActivity.this)
                                .setText("There are no films for selected date")
                                .setDuration(ChocoBar.LENGTH_SHORT)
                                .red()
                                .show();
                        sequenceLayout.removeAllSteps();
                    } else {
                        Collections.sort(list, new Comparator<TimelineItem>() {

                            @Override
                            public int compare(TimelineItem u1, TimelineItem u2) {
                                return u1.getFormattedDate().compareTo(u2.getFormattedDate());
                            }

                        });
                        TimelineAdapter statusAdapter = new TimelineAdapter(
                                list,
                                getApplicationContext(),
                                selectedDateTimeTextView,
                                selectedTimelineStatusActivityTextView,
                                selectedPriceStatusActivityTextView,
                                currentDate,
                                currentTime);
                        statusAdapter.setFilmTimeline(true);
                        sequenceLayout.setAdapter(statusAdapter);
                    }
                    loadingView.hide();
                    timelineLoadingView.hide();
                } catch (IOException e) {
                    e.printStackTrace();
                    Intent intent = new Intent(StatusActivity.this, ErrorActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<TimelineAPI>> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(StatusActivity.this, ErrorActivity.class);
                intent.putExtra("isNetworkError", true);
                startActivity(intent);
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
