package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cinema.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HallActivity extends AppCompatActivity {


//    @BindView(R.id.linearLayout1)
//    LinearLayout linearLayout1;

    @BindView(R.id.tableLayout1)
    TableLayout tableLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);


        ButterKnife.bind(this);

        //

        int row=20,col=10;

        for(int i =0; i<row; i++){
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView temp1=new TextView(this);
            temp1.setText(i+"");
            tr.addView(temp1);
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );

            final float scale1 = this.getResources().getDisplayMetrics().density;
            int margin = (int) (10 * scale1 + 0.5f);

            params.setMargins(margin, 0, margin, 0);
            temp1.setLayoutParams(params);



            for(int j=0;j<col;j++){
                Button b = new Button(this);
                b.setText(j+"");
//                b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                final float scale = this.getResources().getDisplayMetrics().density;
                int pixels = (int) (50 * scale + 0.5f);

                b.setLayoutParams(new TableRow.LayoutParams(pixels, pixels));
                /* Add Button to row. */

                // Available
//                if(true){
//                    b.getBackground().setColorFilter(Color.parseColor("#FF4CAF50"), PorterDuff.Mode.MULTIPLY);
//                    b.setTextColor(Color.parseColor("#FFFAFAFA"));
//                }

                // Booked
//                if(true){
//                    b.getBackground().setColorFilter(Color.parseColor("#FDD835"), PorterDuff.Mode.MULTIPLY);
//                    b.setTextColor(Color.parseColor("#FFFAFAFA"));
//                }


                if(j==5 && i==5){
//                    b.setBackgroundColor(Color.parseColor("#FFFAFAFA"));
//                    b.setTextColor(Color.parseColor("#FFFAFAFA"));

                    b.getBackground().setColorFilter(Color.parseColor("#FFFAFAFA"), PorterDuff.Mode.MULTIPLY);
                    b.setTextColor(Color.parseColor("#FFFAFAFA"));
                    b.setEnabled(false);

                }


                tr.addView(b);


            }

            TextView temp2=new TextView(this);
            temp2.setText(i+"");
            temp2.setLayoutParams(params);
            tr.addView(temp2);

            tableLayout1.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        }


    }
}
