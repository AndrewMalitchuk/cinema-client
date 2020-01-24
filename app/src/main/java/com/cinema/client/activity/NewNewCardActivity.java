package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.cinema.client.MainActivity;
import com.cinema.client.R;
import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cooltechworks.creditcarddesign.CreditCardView;
import com.droidbyme.dialoglib.DroidDialog;
import com.pd.chocobar.ChocoBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.shadowfax.proswipebutton.ProSwipeButton;

public class NewNewCardActivity extends AppCompatActivity {

    private final int CREATE_NEW_CARD = 0;

    private LinearLayout cardContainer;
    private LinearLayout cardLayout;
    private Button addCardButton;

    @BindView(R.id.imageButton2)
    ImageButton imageButton;

    @BindView(R.id.toolbar5)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_new_card);
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


        ProSwipeButton proSwipeBtn = (ProSwipeButton) findViewById(R.id.proswipebutton_main_error);
        proSwipeBtn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                // user has swiped the btn. Perform your async operation now
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // task success! show TICK icon in ProSwipeButton
                        proSwipeBtn.showResultIcon(true); // false if task failed
                        //
                        ChocoBar.builder().setActivity(NewNewCardActivity.this)
                                .setText("Success!")
                                .setDuration(ChocoBar.LENGTH_SHORT)
                                .green()
                                .show();

                        Intent intent = new Intent(NewNewCardActivity.this, MyTicketsActivity.class);
                        startActivity(intent);
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

                Intent intent = new Intent(NewNewCardActivity.this, CardEditActivity.class);
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

                Intent intent = new Intent(NewNewCardActivity.this, CardEditActivity.class);
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
        if (resultCode == RESULT_OK) {
//            Debug.printToast("Result Code is OK", getApplicationContext());

            String name = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            if (reqCode == CREATE_NEW_CARD) {

                CreditCardView creditCardView = new CreditCardView(this);

                creditCardView.setCVV(cvv);
                creditCardView.setCardHolderName(name);
                creditCardView.setCardExpiry(expiry);
                creditCardView.setCardNumber(cardNumber);

                cardContainer.addView(creditCardView);
                int index = cardContainer.getChildCount() - 1;
                addCardListener(index, creditCardView);

                addCardButton.setVisibility(View.GONE);

            } else {

                CreditCardView creditCardView = (CreditCardView) cardContainer.getChildAt(reqCode);

                creditCardView.setCardExpiry(expiry);
                creditCardView.setCardNumber(cardNumber);
                creditCardView.setCardHolderName(name);
                creditCardView.setCVV(cvv);

            }
        }

    }

    public void onChoosePlaceImageButtonClick(View view) {
        Intent intent = new Intent(this, BottomNavigation.class);
        startActivity(intent);
    }

    public void onChooseCinemaImageButtonClick(View view) {

        new DroidDialog.Builder(NewNewCardActivity.this)
                .icon(R.drawable.ic_video_label_black_24dp)
                .title("Selected cinema")
                .content("Do you want to open your selected cinema instead of searching another cinema?")
                .cancelable(true, false)
                .positiveButton("Yes, I'm sure", new DroidDialog.onPositiveListener() {
                    @Override
                    public void onPositive(Dialog droidDialog) {
                        Toast.makeText(NewNewCardActivity.this, "Yes, I'm sure", Toast.LENGTH_SHORT).show();
                        //
//                        Intent intent = new Intent(NewNewCardActivity.this, SearchCinemaActivity.class);
//                        startActivity(intent);
                        //
                    }
                })
                .negativeButton("No, let's search", new DroidDialog.onNegativeListener() {
                    @Override
                    public void onNegative(Dialog droidDialog) {
                        Toast.makeText(NewNewCardActivity.this, "No, let's search", Toast.LENGTH_SHORT).show();
                        //
                        Intent intent = new Intent(NewNewCardActivity.this, SearchCinemaActivity.class);
                        startActivity(intent);
                        //
                    }
                })
                .color(ContextCompat.getColor(NewNewCardActivity.this, R.color.colorAccent), ContextCompat.getColor(NewNewCardActivity.this, R.color.white),
                        ContextCompat.getColor(NewNewCardActivity.this, R.color.colorAccent))
                .show();


    }

    public void onChooseDateTimeImageButtonClick(View view) {
        Intent intent = new Intent(this, StatusActivity.class);
        // туть

        startActivity(intent);
    }

}
