package com.cinema.client.etc;

import android.content.Context;

import com.cinema.client.R;
import com.transferwise.sequencelayout.SequenceAdapter;
import com.transferwise.sequencelayout.SequenceStep;

import java.util.List;

public class StatusAdapter extends SequenceAdapter<MyItem> {


    private List<com.cinema.client.etc.MyItem> items;
    Context context;

    public StatusAdapter (List<com.cinema.client.etc.MyItem> items, Context context){
        this.items=items;
        this.context=context;
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

    }

    @Override
    public com.cinema.client.etc.MyItem getItem(int i) {
        return items.get(i);
    }


    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

}
