package com.cinema.client.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.cinema.client.R;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.TicketAPI;
import com.cinema.client.requests.entities.TimelineAPI;
import com.cinema.client.requests.entities.TokenAPI;
import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cooltechworks.creditcarddesign.CreditCardView;
import com.developer.mtextfield.ExtendedEditText;
import com.droidbyme.dialoglib.DroidDialog;
import com.pd.chocobar.ChocoBar;
import com.rw.loadingdialog.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.shadowfax.proswipebutton.ProSwipeButton;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillActivity extends AppCompatActivity {

    private final int CREATE_NEW_CARD = 0;
    private final int PICK_DATETIME_REQUEST = 1;
    private final int PICK_CINEMA_SEARCH_REQUEST = 2;
    private final int PICK_HALL_PLACE_REQUEST = 3;

    private LinearLayout cardContainer;
    private LinearLayout cardLayout;
    private Button addCardButton;

    @BindView(R.id.imageButton2)
    ImageButton imageButton;

    @BindView(R.id.toolbar5)
    Toolbar toolbar;

    @BindView(R.id.filmTitleBillActivityExtendedEditText)
    ExtendedEditText filmTitleBillActivityExtendedEditText;

    @BindView(R.id.datetimeBillActivityExtendedEditText)
    ExtendedEditText datetimeBillActivityExtendedEditText;

    @BindView(R.id.cinemaNameBillActivityExtendedEditText)
    ExtendedEditText cinemaNameBillActivityExtendedEditText;

    @BindView(R.id.placesBillActivityExtendedEditText)
    ExtendedEditText placesBillActivityExtendedEditText;

    @BindView(R.id.priceBillActivityExtendedEditText)
    ExtendedEditText priceBillActivityExtendedEditText;

    private int filmId;

    private int cinemaId;

    private String cinemaName;

    private String hallPlace;

    private Integer timeline_id;

    public static final String FAVOURITE_CINEMAS_PREF = "favourite_cinema_pref";

    private SharedPreferences sharedpreferences;

    private APIInterface apiInterface;

    public static final String ACCOUNT_PREF = "accountPref";

    private SharedPreferences sharedpreferences1;

    @BindView(R.id.frame)
    ConstraintLayout frame;

    LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        initialize();
        listeners();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(getResources().getString(R.string.titleActivityToolbar).toString());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }

        });
        apiInterface = APIClient.getClient().create(APIInterface.class);
        filmTitleBillActivityExtendedEditText.setText(getIntent().getStringExtra("filmTitle"));
        filmId = getIntent().getIntExtra("filmId", 12);
        if (getIntent().getStringExtra("cinemaName") != null && getIntent().getIntExtra("cinemaId", -1) != -1) {
            cinemaId = getIntent().getIntExtra("cinemaId", -1);
            cinemaName = getIntent().getStringExtra("cinemaName");
            cinemaNameBillActivityExtendedEditText.setText(cinemaName);
        }
        loadingView = new LoadingView.Builder(this)
                .setProgressColorResource(R.color.colorAccent)
                .setProgressStyle(LoadingView.ProgressStyle.CYCLIC)
                .attachTo(frame);
        ProSwipeButton proSwipeBtn = (ProSwipeButton) findViewById(R.id.proswipebutton_main_error);
        proSwipeBtn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sharedpreferences1 = getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
                        if (sharedpreferences1 != null) {
                            String token = sharedpreferences1.getString("token", null);
                            String login = sharedpreferences1.getString("login", null);
                            String password = sharedpreferences1.getString("password", null);
                            if (cinemaNameBillActivityExtendedEditText.getText().length() != 0) {
                                final boolean[] success = {true};
                                RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"),
                                        password);
                                RequestBody login_ = RequestBody.create(MediaType.parse("text/plain"),
                                        login);
                                Call<TokenAPI> call = apiInterface.refreshToken(login_, password_);
                                call.enqueue(new Callback<TokenAPI>() {

                                    @Override
                                    public void onResponse(Call<TokenAPI> call, Response<TokenAPI> response) {
                                        String token = response.body().getAccess();
                                        String ticketPlace = placesBillActivityExtendedEditText.getText().toString().split(";")[0];
                                        RequestBody place = RequestBody.create(MediaType.parse("text/plain"), ticketPlace);
                                        RequestBody code = RequestBody.create(MediaType.parse("text/plain"), "");
                                        RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "2");
                                        RequestBody timeline = RequestBody.create(MediaType.parse("text/plain"), timeline_id.toString());
                                        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), sharedpreferences1.getInt("userId", -1) + "");
                                        Call<TicketAPI> newTicket = apiInterface.createTicket(
                                                place,
                                                code,
                                                status,
                                                userId,
                                                timeline,
                                                "Bearer " + token);
                                        newTicket.enqueue(new Callback<TicketAPI>() {

                                            @Override
                                            public void onResponse(Call<TicketAPI> call, Response<TicketAPI> response) {
                                                if (response.isSuccessful()) {

                                                } else {
                                                    success[0] = false;
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<TicketAPI> call, Throwable t) {
                                                Intent intent = new Intent(BillActivity.this, ErrorActivity.class);
                                                intent.putExtra("isNetworkError", true);
                                                startActivity(intent);

                                            }

                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<TokenAPI> call, Throwable t) {
                                        Intent intent = new Intent(BillActivity.this, ErrorActivity.class);
                                        intent.putExtra("isNetworkError", true);
                                        startActivity(intent);
                                    }
                                });

                                if (success[0] == true) {
                                    proSwipeBtn.showResultIcon(true);
                                    ChocoBar.builder().setActivity(BillActivity.this)
                                            .setText("Success!")
                                            .green()
                                            .setAction("Main Flow", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    startActivity(new Intent(BillActivity.this, MainActivity.class));
                                                }
                                            })
                                            .show();
                                } else {
                                    proSwipeBtn.showResultIcon(false);
                                    ChocoBar.builder().setActivity(BillActivity.this)
                                            .setText("Something goes wrong!")
                                            .setDuration(ChocoBar.LENGTH_SHORT)
                                            .red()
                                            .show();
                                }
                            } else {
                                proSwipeBtn.showResultIcon(false);
                                ChocoBar.builder().setActivity(BillActivity.this)
                                        .setText("Check fields!")
                                        .setDuration(ChocoBar.LENGTH_SHORT)
                                        .red()
                                        .show();
                            }
                        }
                    }
                }, 2000);
            }
        });
    }

    /**
     * Initialize Card's stuff according to documentation
     */
    private void initialize() {
        addCardButton = (Button) findViewById(R.id.add_card);
        cardContainer = (LinearLayout) findViewById(R.id.cardLayout);
    }

    /**
     * Listener for Card's stuff
     */
    private void listeners() {
        addCardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BillActivity.this, CardEditActivity.class);
                startActivityForResult(intent, CREATE_NEW_CARD);
            }

        });
    }

    private void addCardListener(final int index, CreditCardView creditCardView) {
        creditCardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CreditCardView creditCardView = (CreditCardView) v;
                String cardNumber = creditCardView.getCardNumber();
                String expiry = creditCardView.getExpiry();
                String cardHolderName = creditCardView.getCardHolderName();
                Intent intent = new Intent(BillActivity.this, CardEditActivity.class);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME, cardHolderName);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_NUMBER, cardNumber);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_EXPIRY, expiry);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_SHOW_CARD_SIDE, CreditCardUtils.CARD_SIDE_BACK);
                intent.putExtra(CreditCardUtils.EXTRA_VALIDATE_EXPIRY_DATE, false);
                intent.putExtra(CreditCardUtils.EXTRA_ENTRY_START_PAGE, CreditCardUtils.CARD_CVV_PAGE);
                startActivityForResult(intent, index);
            }

        });
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case CREATE_NEW_CARD:
                if (resultCode == RESULT_OK) {
                    String name = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
                    String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
                    String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
                    String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);
                    CreditCardView creditCardView = new CreditCardView(this);
                    creditCardView.setCVV(cvv);
                    creditCardView.setCardHolderName(name);
                    creditCardView.setCardExpiry(expiry);
                    creditCardView.setCardNumber(cardNumber);
                    cardContainer.addView(creditCardView);
                    int index = cardContainer.getChildCount() - 1;
                    addCardListener(index, creditCardView);
                    addCardButton.setVisibility(View.GONE);
                }
                break;
            case PICK_DATETIME_REQUEST:
                if (resultCode == RESULT_OK) {
                    String datetime = data.getStringExtra("datetime");
                    datetimeBillActivityExtendedEditText.setText(datetime);
                    timeline_id = Integer.valueOf(data.getStringExtra("timeline_id"));
                    String price = data.getStringExtra("price");
                    priceBillActivityExtendedEditText.setText(price);
                }
                break;
            case PICK_CINEMA_SEARCH_REQUEST:
                if (resultCode == RESULT_OK) {
                    cinemaName = data.getStringExtra("cinemaName");
                    cinemaId = data.getIntExtra("cinemaId", -1);
                    cinemaNameBillActivityExtendedEditText.setText(cinemaName);
                }
                break;
            case PICK_HALL_PLACE_REQUEST:
                if (resultCode == RESULT_OK) {
                    hallPlace = data.getStringExtra("hallPlace");
                    placesBillActivityExtendedEditText.setText(hallPlace);
                }
                break;
        }
    }

    /**
     * Handler for choosing place in hall
     *
     * @param view
     */
    public void onChoosePlaceImageButtonClick(View view) {
        loadingView.show();
        Intent intent = new Intent(this, HallNavigationActivity.class);
        if (timeline_id == null) {
            timeline_id = 8;
        }
        Observable<TimelineAPI> call = apiInterface.getTimelineByIdRx(timeline_id);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .doOnComplete(() -> {
                    loadingView.hide();
                })
                .subscribe(timelineAPI -> {
                    intent.putExtra("id", timelineAPI.getHallId());
                    startActivityForResult(intent, PICK_HALL_PLACE_REQUEST);
                });
    }

    /**
     * Handler for choosing cinema
     * @param view
     */
    public void onChooseCinemaImageButtonClick(View view) {
        DroidDialog dialog = new DroidDialog.Builder(BillActivity.this)
                .icon(R.drawable.ic_video_label_black_24dp)
                .title("Selected cinema")
                .content("Do you want to open your selected cinema instead of searching another cinema?")
                .cancelable(true, false)
                .positiveButton("Yes, I'm sure", new DroidDialog.onPositiveListener() {

                    @Override
                    public void onPositive(Dialog droidDialog) {
                        droidDialog.cancel();
                        Intent intent = new Intent(BillActivity.this, SearchCinemaActivity.class);
                        sharedpreferences = getSharedPreferences(FAVOURITE_CINEMAS_PREF, Context.MODE_PRIVATE);
                        intent.putExtra("selectedCinemasJson", sharedpreferences.getString("fav_json", null));
                        intent.putExtra("isForChoosing", true);
                        startActivityForResult(intent, PICK_CINEMA_SEARCH_REQUEST);
                    }

                })
                .negativeButton("No, let's search", new DroidDialog.onNegativeListener() {

                    @Override
                    public void onNegative(Dialog droidDialog) {
                        Intent intent = new Intent(BillActivity.this, SearchCinemaActivity.class);
                        intent.putExtra("isForChoosing", true);
                        droidDialog.cancel();
                        startActivityForResult(intent, PICK_CINEMA_SEARCH_REQUEST);
                    }

                })
                .color(ContextCompat.getColor(BillActivity.this, R.color.colorAccent), ContextCompat.getColor(BillActivity.this, R.color.white),
                        ContextCompat.getColor(BillActivity.this, R.color.colorAccent))
                .show();
    }

    /**
     * Handler for choosing date&time from Timeline
     * @param view
     */
    public void onChooseDateTimeImageButtonClick(View view) {
        Intent intent = new Intent(this, StatusActivity.class);
        intent.putExtra("filmId", filmId);
        intent.putExtra("cinemaId", cinemaId);
        intent.putExtra("cinemaName", cinemaName);
        intent.putExtra("isFilmTimeline", true);
        startActivityForResult(intent, PICK_DATETIME_REQUEST);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
