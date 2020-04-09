package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

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

import io.reactivex.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.shadowfax.proswipebutton.ProSwipeButton;
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

    private SharedPreferences sharedpreferences_token;


    public static final String REFRESH_TOKEN_PREF = "refreshToken";

    private APIInterface apiInterface;

    public static final String ACCOUNT_PREF = "accountPref";
    private SharedPreferences sharedpreferences1;

    @BindView(R.id.frame)
    ConstraintLayout frame;


    LoadingView loadingView;

    //
    TimelineAPI temp = null;
    //

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

        toolbar.setTitle("Bill info");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AboutFilmActivity.class));
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

        //
//        String datetime = "2020-02-27T23:05:02+02:00";
//        datetimeBillActivityExtendedEditText.setText(datetime);
        //

        loadingView=new LoadingView.Builder(this)
                .setProgressColorResource(R.color.colorAccent)
                .setProgressStyle(LoadingView.ProgressStyle.CYCLIC)
                .attachTo(frame);


        //

        ProSwipeButton proSwipeBtn = (ProSwipeButton) findViewById(R.id.proswipebutton_main_error);
        proSwipeBtn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                // user has swiped the btn. Perform your async operation now
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        //
                        sharedpreferences1 = getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);

                        if (sharedpreferences1 != null) {
                            String token = sharedpreferences1.getString("token", null);
                            String login = sharedpreferences1.getString("login", null);
                            String password = sharedpreferences1.getString("password", null);
                            int userId = sharedpreferences1.getInt("userId", -1);


                            // TODO: add placesBillActivityExtendedEditText.getText() != null
                            // TODO: add datetimeBillActivityExtendedEditText.getText().length()!=0
                            if (cinemaNameBillActivityExtendedEditText.getText().length() != 0) {


                                final boolean[] success = {true};

                                Log.d("NEW", token + " " + login + " " + password + " " + userId);


                                RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"),
                                        password);

                                RequestBody login_ = RequestBody.create(MediaType.parse("text/plain"),
                                        login);

                                Call<TokenAPI> call = apiInterface.refreshToken(login_, password_);
                                call.enqueue(new Callback<TokenAPI>() {
                                    @Override
                                    public void onResponse(Call<TokenAPI> call, Response<TokenAPI> response) {

                                        String token = response.body().getAccess();
                                        Toast.makeText(BillActivity.this, token, Toast.LENGTH_SHORT).show();


                                        String ticketPlace = placesBillActivityExtendedEditText.getText().toString().split(";")[0];


                                            Log.d("ticketPlace", ticketPlace);


//                                            RequestBody place = RequestBody.create(MediaType.parse("text/plain"), placesBillActivityExtendedEditText.getText().toString().substring(0,5));
                                            RequestBody place = RequestBody.create(MediaType.parse("text/plain"), ticketPlace);
                                            RequestBody code = RequestBody.create(MediaType.parse("text/plain"), "");
                                            RequestBody status = RequestBody.create(MediaType.parse("text/plain"), "2");


                                            RequestBody timeline = RequestBody.create(MediaType.parse("text/plain"), timeline_id.toString());

                                            RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), sharedpreferences1.getInt("userId", -1) + "");

//                                        RequestBody date = RequestBody.create(MediaType.parse("text/plain"), datetimeBillActivityExtendedEditText.getText().toString());
//                                            RequestBody date = RequestBody.create(MediaType.parse("text/plain"), "2020-02-15 10:10:10");


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
//                                                    Toast.makeText(NewNewCardActivity.this, response.body().getCode(), Toast.LENGTH_SHORT).show();

                                                    if (response.isSuccessful()) {

//                                                        Toast.makeText(NewNewCardActivity.this, "KEK", Toast.LENGTH_SHORT).show();

//                                                        ;

//
                                                    } else {
                                                        success[0] = false;
                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<TicketAPI> call, Throwable t) {

                                                }
                                            });





                                    }

                                    @Override
                                    public void onFailure(Call<TokenAPI> call, Throwable t) {

                                    }
                                });


                                if (success[0] == true) {
                                    // task success! show TICK icon in ProSwipeButton
                                    proSwipeBtn.showResultIcon(true); // false if task failed
//                                                        //
                                    ChocoBar.builder().setActivity(BillActivity.this)
                                            .setText("Success!")
                                            .green()
                                            .setAction("Main Flow", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    startActivity(new Intent(BillActivity.this, Main3Activity.class));
                                                }
                                            })
                                            .show();
//                                                        //
//                                                        Intent intent=new Intent(NewNewCardActivity.this, Main3Activity.class);
//                                                        startActivity(intent);
//                                                        //
                                } else {
                                    // task success! show TICK icon in ProSwipeButton
                                    proSwipeBtn.showResultIcon(false); // false if task failed
                                    //
                                    ChocoBar.builder().setActivity(BillActivity.this)
                                            .setText("Something goes wrong!")
                                            .setDuration(ChocoBar.LENGTH_SHORT)
                                            .red()
                                            .show();
                                }


                                //

                            } else {
                                proSwipeBtn.showResultIcon(false);

                                ChocoBar.builder().setActivity(BillActivity.this)
                                        .setText("Check fields!")
                                        .setDuration(ChocoBar.LENGTH_SHORT)
                                        .red()
                                        .show();
                            }


                        }
                        //


                    }
                }, 2000);

            }
        });

//        proSwipeBtn.showResultIcon(true); //if task succeeds
//        proSwipeBtn.showResultIcon(false); //if task fails


    }

    private void initialize() {
        addCardButton = (Button) findViewById(R.id.add_card);
        cardContainer = (LinearLayout) findViewById(R.id.cardLayout);
//        getSupportActionBar().setTitle("Payment");
//        populate();
    }


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
                String cvv = creditCardView.getCVV();

                Intent intent = new Intent(BillActivity.this, CardEditActivity.class);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME, cardHolderName);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_NUMBER, cardNumber);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_EXPIRY, expiry);
                intent.putExtra(CreditCardUtils.EXTRA_CARD_SHOW_CARD_SIDE, CreditCardUtils.CARD_SIDE_BACK);
                intent.putExtra(CreditCardUtils.EXTRA_VALIDATE_EXPIRY_DATE, false);

                // start at the CVV activity to edit it as it is not being passed
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

//
                }
                break;
            case PICK_DATETIME_REQUEST:
                if (resultCode == RESULT_OK) {
                    String datetime = data.getStringExtra("datetime");
                    datetimeBillActivityExtendedEditText.setText(datetime);
                    Log.d("datetime",datetime);
                    //
                    //TODO: REMOVE
//                    datetimeBillActivityExtendedEditText.setText(datetime);
                    //

                    timeline_id = Integer.valueOf(data.getStringExtra("timeline_id"));
                    Log.d("timeline_id", timeline_id.toString());

                    String price=data.getStringExtra("price");
                    priceBillActivityExtendedEditText.setText(price);
                    Log.d("price",price);

                }
                break;
            case PICK_CINEMA_SEARCH_REQUEST:
                if (resultCode == RESULT_OK) {
                    cinemaName = data.getStringExtra("cinemaName");
                    cinemaId = data.getIntExtra("cinemaId", -1);
                    Log.d("CINEMA", cinemaName);
                    cinemaNameBillActivityExtendedEditText.setText(cinemaName);


                }
                break;
            case PICK_HALL_PLACE_REQUEST:
                if (resultCode == RESULT_OK) {
                    hallPlace = data.getStringExtra("hallPlace");
                    placesBillActivityExtendedEditText.setText(hallPlace);
                    Log.d("PLACE", hallPlace);

                    //

                    //

                }
                break;

        }


    }

    public void onChoosePlaceImageButtonClick(View view) {

        loadingView.show();

        Intent intent = new Intent(this, HallNavigationActivity.class);

        //XXX
        if(timeline_id==null){
            timeline_id=8;
        }


        Observable<TimelineAPI> call=apiInterface.getTimelineByIdRx(timeline_id);

         call.subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .map(result -> result)
                 .doOnComplete(() -> {
                     loadingView.hide();
                 })
                 .subscribe(timelineAPI -> {
                     intent.putExtra("id",timelineAPI.getHallId());
                     startActivityForResult(intent, PICK_HALL_PLACE_REQUEST);
                 });



    }

    public void onChooseCinemaImageButtonClick(View view) {

        DroidDialog dialog = new DroidDialog.Builder(BillActivity.this)
                .icon(R.drawable.ic_video_label_black_24dp)
                .title("Selected cinema")
                .content("Do you want to open your selected cinema instead of searching another cinema?")
                .cancelable(true, false)
                .positiveButton("Yes, I'm sure", new DroidDialog.onPositiveListener() {
                    @Override
                    public void onPositive(Dialog droidDialog) {
                        Toast.makeText(BillActivity.this, "Yes, I'm sure", Toast.LENGTH_SHORT).show();
                        droidDialog.cancel();
                        //
                        Intent intent = new Intent(BillActivity.this, SearchCinemaActivity.class);
//                        intent.putExtra("isForChoosing",true);
                        sharedpreferences = getSharedPreferences(FAVOURITE_CINEMAS_PREF, Context.MODE_PRIVATE);
                        intent.putExtra("selectedCinemasJson", sharedpreferences.getString("fav_json", null));
                        intent.putExtra("isForChoosing", true);
                        startActivityForResult(intent, PICK_CINEMA_SEARCH_REQUEST);
                        //
                    }
                })
                .negativeButton("No, let's search", new DroidDialog.onNegativeListener() {
                    @Override
                    public void onNegative(Dialog droidDialog) {
                        Toast.makeText(BillActivity.this, "No, let's search", Toast.LENGTH_SHORT).show();
                        //
                        Intent intent = new Intent(BillActivity.this, SearchCinemaActivity.class);
                        intent.putExtra("isForChoosing", true);
                        droidDialog.cancel();
//                        startActivity(intent);
                        startActivityForResult(intent, PICK_CINEMA_SEARCH_REQUEST);
                        //
                    }
                })
                .color(ContextCompat.getColor(BillActivity.this, R.color.colorAccent), ContextCompat.getColor(BillActivity.this, R.color.white),
                        ContextCompat.getColor(BillActivity.this, R.color.colorAccent))
                .show();


    }

    public void onChooseDateTimeImageButtonClick(View view) {
        Intent intent = new Intent(this, StatusActivity.class);
        // туть
        intent.putExtra("filmId", filmId);

        intent.putExtra("cinemaId", cinemaId);
        intent.putExtra("cinemaName", cinemaName);
        Log.d("filmId", filmId + "");
        intent.putExtra("isFilmTimeline", true);


        startActivityForResult(intent, PICK_DATETIME_REQUEST);
    }

}
