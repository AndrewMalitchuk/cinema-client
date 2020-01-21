package com.cinema.client.etc;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cinema.client.R;
import com.cinema.client.activity.Main2Activity;
import com.pd.chocobar.ChocoBar;
import com.transferwise.sequencelayout.SequenceAdapter;
import com.transferwise.sequencelayout.SequenceStep;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StatusAdapter extends SequenceAdapter<MyItem> {


    private List<com.cinema.client.etc.MyItem> items;
    Context context;


    TextView selectedDateTimeTextView;
    String activeDate;

    public StatusAdapter (List<com.cinema.client.etc.MyItem> items, Context context){
        this.items=items;
        this.context=context;
    }

    public StatusAdapter (List<com.cinema.client.etc.MyItem> items, Context context,TextView selectedDateTimeTextView,String activeDate){
        this.items=items;
        this.context=context;
        this.selectedDateTimeTextView=selectedDateTimeTextView;
        this.activeDate=activeDate;
    }


    @Override
    public int getCount() {
        return items.size();
    }



    @Override
    public void bindView(SequenceStep sequenceStep, com.cinema.client.etc.MyItem myItem) {

        sequenceStep.setActive(myItem.isActive());
        sequenceStep.setAnchor(myItem.getFormattedDate());
        sequenceStep.setTitle(myItem.getTitle());
        sequenceStep.setAnchorMinWidth((int) pxFromDp(context,150));
        if(myItem.isSelected()) {
            sequenceStep.setTitleTextAppearance(R.style.TextAppearance_AppCompat_Title);
        }
        if (myItem.getSubtitle()!=null){
            sequenceStep.setSubtitle(myItem.getSubtitle());
        }

        Date curent=null;
        Date temp=null;
        try {
            // XXX
            curent=new SimpleDateFormat("dd.MM.yyy").parse(activeDate);
            temp=new SimpleDateFormat("dd.MM.yyy").parse(myItem.getFormattedDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(temp.after(curent) || temp.compareTo(curent)==0) {
            sequenceStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Toast.makeText(context,myItem.getTitle()+" "+myItem.getFormattedDate(),Toast.LENGTH_SHORT).show();
                    selectedDateTimeTextView.setText(myItem.getFormattedDate());
                }
            });
        }

    }

    @Override
    public com.cinema.client.etc.MyItem getItem(int i) {
        return items.get(i);
    }


    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
