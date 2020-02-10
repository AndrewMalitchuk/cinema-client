package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cinema.client.R;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.CinemaAPI;
import com.cinema.client.requests.entities.FilmAPI;
import com.cinema.client.requests.entities.TicketAPI;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
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



    private APIInterface apiInterface;
    private TicketAPI curentTicket;

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


        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        apiInterface = APIClient.getClient().create(APIInterface.class);

        String code="4a39efc6fc7e6b4ed772e3941cf86964";

        Call<TicketAPI> call=apiInterface.getTicketByCode(code);
        call.enqueue(new Callback<TicketAPI>() {
            @Override
            public void onResponse(Call<TicketAPI> call, Response<TicketAPI> response) {

                curentTicket=response.body();

                Log.d("TICKET",curentTicket.toString());


               setContent(curentTicket);


            }

            @Override
            public void onFailure(Call<TicketAPI> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(TicketActivity.this,ErrorActivity.class);
                startActivity(intent);
            }
        });





    }


    public void onQRCodeClick(View view){
        Intent intent=new Intent(this,QRZoomActivity.class);
        intent.putExtra("QR",curentTicket.getCode());
        startActivity(intent);
    }

    public void setContent(TicketAPI content){



        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content.getCode(), BarcodeFormat.QR_CODE,1024,1024);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCodeTicketActivityImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        qrCodeTicketActivityEditText.setText(content.getCode());


        Call<FilmAPI> callFilmApi=apiInterface.getFilmById(content.getFilmId());
        callFilmApi.enqueue(new Callback<FilmAPI>() {
            @Override
            public void onResponse(Call<FilmAPI> call, Response<FilmAPI> response) {

                FilmAPI content=response.body();

                filmTitleTicketActivityEditText.setText(content.getTitle());
            }

            @Override
            public void onFailure(Call<FilmAPI> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(TicketActivity.this,ErrorActivity.class);
                startActivity(intent);
            }
        });

        Call<CinemaAPI> callCinemaApi=apiInterface.getCinemaById(content.getCinemaId());
        callCinemaApi.enqueue(new Callback<CinemaAPI>() {
            @Override
            public void onResponse(Call<CinemaAPI> call, Response<CinemaAPI> response) {
                CinemaAPI content=response.body();
                cinemaNameTicketActivityEditText.setText(content.getName());

            }

            @Override
            public void onFailure(Call<CinemaAPI> call, Throwable t) {
                call.cancel();
                Intent intent = new Intent(TicketActivity.this,ErrorActivity.class);
                startActivity(intent);
            }
        });






        //
        String temp_datetime=content.getDate();
        String temp_date=temp_datetime.split("T")[0];
        String temp_time=temp_datetime.split("T")[1].substring(0,5);

        //

        filmDateTicketActivityEditText.setText(temp_date);
        filmTimeTicketActivityEditText.setText(temp_time);
        placeTicketActivityEditText.setText(content.getPlace());



    }




}
