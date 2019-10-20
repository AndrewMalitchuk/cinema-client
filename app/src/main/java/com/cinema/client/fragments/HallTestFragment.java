package com.cinema.client.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cinema.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HallTestFragment extends Fragment {

    @BindView(R.id.tableLayout1)
    TableLayout tableLayout1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment

        View view = inflater.inflate(R.layout.fragment_hall_test, parent, false);
        ButterKnife.bind(this, view);

        return view;




    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        int row = 20, col = 10;

        for (int i = 0; i < row; i++) {
            TableRow tr = new TableRow(getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView temp1 = new TextView(getContext());
            temp1.setText(i + "");
            tr.addView(temp1);
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );

            final float scale1 = this.getResources().getDisplayMetrics().density;
            int margin = (int) (10 * scale1 + 0.5f);

            params.setMargins(margin, 0, margin, 0);
            temp1.setLayoutParams(params);


            for (int j = 0; j < col; j++) {
                Button b = new Button(getContext());
                b.setText(j + "");
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


                if (j == 5 && i == 5) {
//                    b.setBackgroundColor(Color.parseColor("#FFFAFAFA"));
//                    b.setTextColor(Color.parseColor("#FFFAFAFA"));

                    b.getBackground().setColorFilter(Color.parseColor("#FFFAFAFA"), PorterDuff.Mode.MULTIPLY);
                    b.setTextColor(Color.parseColor("#FFFAFAFA"));
                    b.setEnabled(false);

                }


                tr.addView(b);


            }

            TextView temp2 = new TextView(getContext());
            temp2.setText(i + "");
            temp2.setLayoutParams(params);
            tr.addView(temp2);

            tableLayout1.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));


        }

    }
}
