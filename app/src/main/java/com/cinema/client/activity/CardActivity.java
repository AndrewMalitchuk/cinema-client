package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.cinema.client.R;
import com.manojbhadane.PaymentCardView;

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);


        PaymentCardView paymentCard = (PaymentCardView) findViewById(R.id.creditCard);

        //Callbacks
        paymentCard.setOnPaymentCardEventListener(new PaymentCardView.OnPaymentCardEventListener() {
            @Override
            public void onCardDetailsSubmit(String month, String year, String cardNumber, String cvv) {

                Toast.makeText(CardActivity.this, "Great!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(String error) {
                Toast.makeText(CardActivity.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelClick() {

            }
        });

    }
}
