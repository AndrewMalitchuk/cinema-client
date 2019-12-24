package com.cinema.client.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.cinema.client.R;
import com.cinema.client.activity.HallActivity;
import com.cinema.client.activity.Main2Activity;
import com.cinema.client.entities.Hall;
import com.pd.chocobar.ChocoBar;

import java.util.ArrayList;

public class HallRender {


    Context context;
    String sector;


    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public HallRender(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("UserPlaces", 0);
        editor = pref.edit();

    }

    enum Status {
        DISABLED,
        FREE,
        BOOKED,
        BOUGHT
    }


    public TableLayout render(Hall hall, TableLayout tableLayout) {
        int row = hall.getRow();
        int col = hall.getCol();
        sector=hall.getSector();

        for (int i = 0; i < row; i++) {
            TableRow tr = new TableRow(context);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView temp1 = new TextView(context);
            temp1.setText(i + 1 + "");
            tr.addView(temp1);
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );

            final float scale1 = context.getResources().getDisplayMetrics().density;
            int margin = (int) (10 * scale1 + 0.5f);

            params.setMargins(margin, 0, margin, 0);
            temp1.setLayoutParams(params);


            for (int j = 0; j < col; j++) {
                Button b = new Button(context);
//                b.setText(j +1+ "");
                final float scale = context.getResources().getDisplayMetrics().density;
                int pixels = (int) (50 * scale + 0.5f);

                b.setLayoutParams(new TableRow.LayoutParams(pixels, pixels));
                b.setText((i +1)+ "-"+(j+1));

//
//                if (j == 5 && i == 5) {
//                    b.getBackground().setColorFilter(Color.parseColor("#FFFAFAFA"), PorterDuff.Mode.MULTIPLY);
//                    b.setTextColor(Color.parseColor("#FFFAFAFA"));
//                    b.setEnabled(false);
//                }

//                setCellStatus(b,Status.FREE);

                setCellStatus(b,hall.getBooked(),Status.BOOKED,i,j);
                setCellStatus(b,hall.getFree(),Status.FREE,i,j);
                setCellStatus(b,hall.getDisabled(),Status.DISABLED,i,j);
                setCellStatus(b,hall.getBought(),Status.BOUGHT,i,j);



                tr.addView(b);


            }

            TextView temp2 = new TextView(context);
            temp2.setText(i + 1+ "");
            temp2.setLayoutParams(params);
            tr.addView(temp2);

            tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));



        }




        return tableLayout;

    }

    public void setCellStatus(Button cell, Status status) {

        switch (status) {
            case DISABLED:
                cell.getBackground().setColorFilter(Color.parseColor("#FFFAFAFA"), PorterDuff.Mode.MULTIPLY);
                cell.setTextColor(Color.parseColor("#FFFAFAFA"));
                cell.setEnabled(false);
                break;
            case FREE:
                cell.getBackground().setColorFilter(Color.parseColor("#FF4CAF50"), PorterDuff.Mode.MULTIPLY);
                cell.setTextColor(Color.parseColor("#FFFAFAFA"));
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Toast.makeText(context, cell.getText().toString(), Toast.LENGTH_SHORT).show();

                        editor.putString("place", cell.getText().toString());
                        TextView textView=((Activity) context).findViewById(R.id.selectedPlacesTextView);

                        String prevContent=textView.getText().toString();
                        if(!prevContent.equals("Please, select place. ")){
                            textView.setText(prevContent+"; "+sector.charAt(0)+"-"+cell.getText());

                        }else {

                            textView.setText(sector.charAt(0)+"-"+cell.getText());
                        }
                        cell.setText("✓");
                    }
                });
                break;
            case BOOKED:
                cell.getBackground().setColorFilter(Color.parseColor("#FDD835"), PorterDuff.Mode.MULTIPLY);
                cell.setTextColor(Color.parseColor("#FFFAFAFA"));
                break;
            case BOUGHT:
                cell.getBackground().setColorFilter(Color.parseColor("#F44336"), PorterDuff.Mode.MULTIPLY);
                cell.setTextColor(Color.parseColor("#FFFAFAFA"));
                break;
        }

    }

    public void setCellStatus(Button button, ArrayList<Hall.Cell> cells, Status status, int i, int j){

        for(Hall.Cell cell :cells){
            int row=cell.getRow();
            int col=cell.getCol();


            if(row==i && col==j){
                setCellStatus(button,status);
            }

        }

    }


}
