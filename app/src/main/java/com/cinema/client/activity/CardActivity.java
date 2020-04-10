package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cinema.client.R;
import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cooltechworks.creditcarddesign.CreditCardView;

public class CardActivity extends AppCompatActivity {


    private final int CREATE_NEW_CARD = 0;

    private LinearLayout cardContainer;
    private Button addCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);



        initialize();
        listeners();

    }


    //
    private void initialize() {
        addCardButton = (Button) findViewById(R.id.add_card);
        cardContainer = (LinearLayout) findViewById(R.id.card_container);
//        getSupportActionBar().setTitle("Payment");
        populate();
    }

    private void populate() {
        CreditCardView sampleCreditCardView = new CreditCardView(this);

        String name = "Glarence Zhao";
        String cvv = "420";
        String expiry = "01/18";
        String cardNumber = "4242424242424242";

        sampleCreditCardView.setCVV(cvv);
        sampleCreditCardView.setCardHolderName(name);
        sampleCreditCardView.setCardExpiry(expiry);
        sampleCreditCardView.setCardNumber(cardNumber);

        cardContainer.addView(sampleCreditCardView);
        int index = cardContainer.getChildCount() - 1;
        addCardListener(index, sampleCreditCardView);
    }

    private void listeners() {
        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CardActivity.this, CardEditActivity.class);
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

                Intent intent = new Intent(CardActivity.this, CardEditActivity.class);
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

}
