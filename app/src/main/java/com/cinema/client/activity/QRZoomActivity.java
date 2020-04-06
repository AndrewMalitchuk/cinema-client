package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.cinema.client.R;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QRZoomActivity extends AppCompatActivity {

    @BindView(R.id.layout)
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrzoom);

        ButterKnife.bind(this);

        PhotoView photoView = (PhotoView) findViewById(R.id.filmPosterZoomActivityPhotoView);
        photoView.setImageResource(R.drawable.ic_ticket_accent);


        String text=getIntent().getStringExtra("QR");
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,1024,1024);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            photoView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }



//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        );

        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(layout)
                .setTransitionDuration(4000)
                .start();

    }
}
