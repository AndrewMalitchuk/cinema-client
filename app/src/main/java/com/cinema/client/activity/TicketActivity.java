package com.cinema.client.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.cinema.client.R;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.CinemaAPI;
import com.cinema.client.requests.entities.FilmAPI;
import com.cinema.client.requests.entities.TicketAPI;
import com.cinema.client.requests.entities.TimelineAPI;
import com.cinema.client.requests.entities.TokenAPI;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.rw.loadingdialog.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketActivity extends AppCompatActivity {

    @BindView(R.id.ticketLinearLayout)
    LinearLayout ticketLinearLayout;

    @BindView(R.id.qrCodeTicketActivityImageView)
    ImageView qrCodeTicketActivityImageView;

    @BindView(R.id.qrCodeTicketActivityEditText)
    AppCompatTextView qrCodeTicketActivityEditText;

    @BindView(R.id.filmTitleTicketActivityEditText)
    AppCompatTextView filmTitleTicketActivityEditText;

    @BindView(R.id.cinemaNameTicketActivityEditText)
    AppCompatTextView cinemaNameTicketActivityEditText;

    @BindView(R.id.filmDateTicketActivityEditText)
    AppCompatTextView filmDateTicketActivityEditText;

    @BindView(R.id.filmTimeTicketActivityEditText)
    AppCompatTextView filmTimeTicketActivityEditText;

    @BindView(R.id.placeTicketActivityEditText)
    AppCompatTextView placeTicketActivityEditText;

    @BindView(R.id.frame)
    ConstraintLayout frame;

    @BindView(R.id.textView32)
    TextView textView32;

    private APIInterface apiInterface;

    private TicketAPI curentTicket;

    public static final String ACCOUNT_PREF = "accountPref";

    private SharedPreferences sharedpreferences;

    int timeline_id;

    LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        ButterKnife.bind(this);
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(ticketLinearLayout)
                .setTransitionDuration(4000)
                .start();
        apiInterface = APIClient.getClient().create(APIInterface.class);
        loadingView = new LoadingView.Builder(this)
                .setProgressColorResource(R.color.colorAccent)
                .setProgressStyle(LoadingView.ProgressStyle.CYCLIC)
                .attachTo(frame);
        timeline_id = getIntent().getIntExtra("timeline_id", -1);
        sharedpreferences = getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
        if (sharedpreferences != null) {
            String login = sharedpreferences.getString("login", null);
            String password = sharedpreferences.getString("password", null);
            RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"),
                    password);
            RequestBody login_ = RequestBody.create(MediaType.parse("text/plain"),
                    login);
            Observable<TokenAPI> tokenRx = apiInterface.refreshTokenRx(login_, password_);
            tokenRx.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(result -> result)
                    .subscribe(this::onToken);
        }
    }

    /**
     * Handler for received Token
     *
     * @param tokenAPI
     */
    private void onToken(TokenAPI tokenAPI) {
        String ticketCode = getIntent().getStringExtra("ticketCode");
        Call<TicketAPI> call = apiInterface.getTicketByCode(ticketCode, "Bearer " + tokenAPI.getAccess());
        call.enqueue(new Callback<TicketAPI>() {

            @Override
            public void onResponse(Call<TicketAPI> call, Response<TicketAPI> response) {
                curentTicket = response.body();
                setContent(curentTicket);
            }

            @Override
            public void onFailure(Call<TicketAPI> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(TicketActivity.this, ErrorActivity.class);
                startActivity(intent);
            }

        });
    }

    /**
     * Handler for QR code click
     *
     * @param view
     */
    public void onQRCodeClick(View view) {
        Intent intent = new Intent(this, QRZoomActivity.class);
        intent.putExtra("QR", curentTicket.getCode());
        startActivity(intent);
    }

    /**
     * Set content for activity
     *
     * @param content content to display
     */
    public void setContent(TicketAPI content) {
        loadingView.show();
        Observable<TimelineAPI> call = apiInterface.getTimelineByIdRx(timeline_id);
        call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .doOnComplete(() -> {
                    loadingView.hide();


                })
                .subscribe(timelineAPI -> {
                    filmDateTicketActivityEditText.setText(timelineAPI.getDate());
                    filmTimeTicketActivityEditText.setText(timelineAPI.getTime());

                });
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content.getCode(), BarcodeFormat.QR_CODE, 1024, 1024);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCodeTicketActivityImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Intent intent = new Intent(TicketActivity.this, ErrorActivity.class);
            startActivity(intent);
            e.printStackTrace();
        }
        qrCodeTicketActivityEditText.setText(content.getCode());
        Call<FilmAPI> callFilmApi = apiInterface.getFilmById(getIntent().getIntExtra("film_id", -1));
        callFilmApi.enqueue(new Callback<FilmAPI>() {

            @Override
            public void onResponse(Call<FilmAPI> call, Response<FilmAPI> response) {
                FilmAPI content = response.body();
                filmTitleTicketActivityEditText.setText(content.getTitle());
            }

            @Override
            public void onFailure(Call<FilmAPI> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(TicketActivity.this, ErrorActivity.class);
                intent.putExtra("isNetworkError", true);
                startActivity(intent);
            }

        });
        Call<CinemaAPI> callCinemaApi = apiInterface.getCinemaById(getIntent().getIntExtra("cinema_id", -1));
        callCinemaApi.enqueue(new Callback<CinemaAPI>() {

            @Override
            public void onResponse(Call<CinemaAPI> call, Response<CinemaAPI> response) {
                CinemaAPI content = response.body();
                cinemaNameTicketActivityEditText.setText(content.getName());
            }

            @Override
            public void onFailure(Call<CinemaAPI> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(TicketActivity.this, ErrorActivity.class);
                intent.putExtra("isNetworkError", true);
                startActivity(intent);
            }

        });
        placeTicketActivityEditText.setText(content.getPlace());
        textView32.setOnClickListener(e -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
