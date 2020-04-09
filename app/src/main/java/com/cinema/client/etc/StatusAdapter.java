package com.cinema.client.etc;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cinema.client.R;
import com.cinema.client.requests.entities.TimelineAPI;
import com.pd.chocobar.ChocoBar;
import com.transferwise.sequencelayout.SequenceAdapter;
import com.transferwise.sequencelayout.SequenceStep;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class StatusAdapter extends SequenceAdapter<MyItem> {


    private List<com.cinema.client.etc.MyItem> items;
    Context context;


    TextView selectedDateTimeTextView;
    TextView selectedTimelineStatusActivityTextView;
    TextView selectedPriceStatusActivityTextView;
    String activeDate;
    String activeTime;
    List<TimelineAPI> list;

    @Getter
    @Setter
    boolean isFilmTimeline;

//    public StatusAdapter(List<com.cinema.client.etc.MyItem> items, Context context) {
//        this.items = items;
//        this.context = context;
//    }
//
//    public StatusAdapter(List<com.cinema.client.etc.MyItem> items, Context context, TextView selectedDateTimeTextView, String activeDate, String activeTime) {
//        this.items = items;
//        this.context = context;
//        this.selectedDateTimeTextView = selectedDateTimeTextView;
//        this.activeDate = activeDate;
//        this.activeTime = activeTime;
//    }

    public StatusAdapter(List<com.cinema.client.etc.MyItem> items, Context context, TextView selectedDateTimeTextView, TextView selectedTimelineStatusActivityTextView, TextView selectedPriceStatusActivityTextView, String activeDate, String activeTime) {
        this.items = items;
        this.context = context;
        this.selectedDateTimeTextView = selectedDateTimeTextView;
        this.selectedTimelineStatusActivityTextView = selectedTimelineStatusActivityTextView;
        this.selectedPriceStatusActivityTextView = selectedPriceStatusActivityTextView;

        this.activeDate = activeDate;
        this.activeTime = activeTime;

    }


    @Override
    public int getCount() {
        return items.size();
    }


    @Override
    public void bindView(SequenceStep sequenceStep, com.cinema.client.etc.MyItem myItem) {

        sequenceStep.setActive(myItem.isActive());
        sequenceStep.setAnchor(myItem.getFormattedTime());
        sequenceStep.setTitle(myItem.getTitle());
        sequenceStep.setAnchorMinWidth((int) pxFromDp(context, 150));
        if (myItem.isSelected()) {
            sequenceStep.setTitleTextAppearance(R.style.TextAppearance_AppCompat_Title);
        }
        if (myItem.getSubtitle() != null) {
            sequenceStep.setSubtitle(myItem.getSubtitle());
        }

        Date curent = null;
        Date temp = null;
        Date curentDate = null;
        Date tempDate = null;
//        Time curent=null;
//        Time temp=null;
        try {
            // XXX
//            curent=new SimpleDateFormat("dd.MM.yyy").parse(activeDate);
//            temp=new SimpleDateFormat("dd.MM.yyy").parse(myItem.getFormattedDate());

            Log.d("Active Date", activeDate);
            Log.d("Formatter Date", myItem.getFormattedDate());

            curentDate = new SimpleDateFormat("yyyy-MM-dd").parse(activeDate);
            Log.d("current", curentDate.toString());

            tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(myItem.getFormattedDate());
            Log.d("temp", tempDate.toString());

            //

            Log.d("Active Date", activeTime);
            Log.d("Formatter Date", myItem.getFormattedDate());

            curent = new SimpleDateFormat("HH:mm:ss").parse(activeTime);
            Log.d("current", curent.toString());

            temp = new SimpleDateFormat("HH:mm:ss").parse(myItem.getFormattedTime());
            Log.d("temp", temp.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("KEK", tempDate + " | " + curentDate);

        if (tempDate.before(curentDate)) {

            Log.d("KEK", "before");

            sequenceStep.setActive(false);
            sequenceStep.setSelected(false);

        } else if (tempDate.after(curentDate) || tempDate.compareTo(curentDate) == 0) {

            Log.d("KEK", "after");

            Log.d("KEK", temp + " | " + curent);


            if (temp.after(curent)) {

                Log.d("KEK", "after time");


                sequenceStep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                Toast.makeText(context,myItem.getTitle()+" "+myItem.getFormattedDate(),Toast.LENGTH_SHORT).show();
                        selectedDateTimeTextView.setText(myItem.getFormattedTime());

                        Log.d("TimelineAPI", myItem.getTimelineAPI().toString());

                        try {
                            Log.d("null", (selectedTimelineStatusActivityTextView == null) + "");

//                            selectedTimelineStatusActivityTextView.setText(myItem.getTimelineAPI().getId());
//
//                            selectedTimelineStatusActivityTextView.setText(myItem.getTimelineAPI().getPrice() + "");

                            selectedTimelineStatusActivityTextView.setText(myItem.getTimelineAPI().getId() + "");
                            selectedPriceStatusActivityTextView.setText(myItem.getTimelineAPI().getPrice() + "");

                        } catch (Exception e) {

                        }
                    }
                });


//                if (temp.before(curent) == true) {
//                    sequenceStep.setActive(true);
//                    sequenceStep.setSelected(true);
//                }


            }


        }

        Log.d("isFilmTimeline", isFilmTimeline + "");
//        if (isFilmTimeline) {
//
//            if (true) {
//                sequenceStep.setActive(true);
//                sequenceStep.setSelected(true);
//            }
//
//            sequenceStep.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                Toast.makeText(context,myItem.getTitle()+" "+myItem.getFormattedDate(),Toast.LENGTH_SHORT).show();
//                    selectedDateTimeTextView.setText(myItem.getFormattedTime());
//                    Log.d("TimelineAPI", myItem.getTimelineAPI().toString() + "");
////                    Log.d("TimelineAPI",myItem.getTimelineAPI().getId()+"");
//                    selectedTimelineStatusActivityTextView.setText(myItem.getTimelineAPI().getId() + "");
//                    selectedPriceStatusActivityTextView.setText(myItem.getTimelineAPI().getPrice() + "");
//                }
//            });
//        }

    }

    @Override
    public com.cinema.client.etc.MyItem getItem(int i) {
        return items.get(i);
    }


    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
