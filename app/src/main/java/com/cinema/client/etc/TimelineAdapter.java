package com.cinema.client.etc;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cinema.client.R;
import com.transferwise.sequencelayout.SequenceAdapter;
import com.transferwise.sequencelayout.SequenceStep;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class TimelineAdapter extends SequenceAdapter<TimelineItem> {

    @Getter
    @Setter
    private List<TimelineItem> items;

    Context context;

    TextView selectedDateTimeTextView;

    TextView selectedTimelineStatusActivityTextView;

    TextView selectedPriceStatusActivityTextView;

    @Getter
    @Setter
    String activeDate;

    @Getter
    @Setter
    String activeTime;

    @Getter
    @Setter
    boolean isFilmTimeline;

    public TimelineAdapter(List<TimelineItem> items, Context context, TextView selectedDateTimeTextView, TextView selectedTimelineStatusActivityTextView, TextView selectedPriceStatusActivityTextView, String activeDate, String activeTime) {
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
    public void bindView(SequenceStep sequenceStep, TimelineItem myItem) {
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
        try {
            curentDate = new SimpleDateFormat("yyyy-MM-dd").parse(activeDate);
            tempDate = new SimpleDateFormat("yyyy-MM-dd").parse(myItem.getFormattedDate());
            curent = new SimpleDateFormat("HH:mm:ss").parse(activeTime);
            temp = new SimpleDateFormat("HH:mm:ss").parse(myItem.getFormattedTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tempDate.before(curentDate)) {
            sequenceStep.setActive(false);
            sequenceStep.setSelected(false);
        } else if (tempDate.after(curentDate) || tempDate.compareTo(curentDate) == 0) {
            if (temp.after(curent)) {
                sequenceStep.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        selectedDateTimeTextView.setText(myItem.getFormattedTime());
                        try {
                            selectedTimelineStatusActivityTextView.setText(myItem.getTimelineAPI().getId() + "");
                            selectedPriceStatusActivityTextView.setText(myItem.getTimelineAPI().getPrice() + "");
                        } catch (Exception e) {

                        }
                    }

                });
            }
        }
    }

    @Override
    public TimelineItem getItem(int i) {
        return items.get(i);
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
