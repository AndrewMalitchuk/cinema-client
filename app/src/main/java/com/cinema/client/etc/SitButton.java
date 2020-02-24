package com.cinema.client.etc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.cinema.client.requests.entities.HallCellAPI;

import lombok.Getter;
import lombok.Setter;

@SuppressLint("AppCompatCustomView")
public class SitButton extends Button {

    @Getter
    @Setter
    private HallCellAPI absoluteLocation;


    @Getter
    @Setter
    private HallCellAPI relativeLocation;



    public SitButton(Context context) {
        super(context);
    }

    public SitButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SitButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SitButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public String absoluteLocationToString(){
        return absoluteLocation.getSector().charAt(0)+"-"+absoluteLocation.getRow()+"-"+absoluteLocation.getCol();
    }

    public String relativeLocationToString(){
        return relativeLocation.getSector().charAt(0)+"-"+relativeLocation.getRow()+"-"+relativeLocation.getCol();

    }


}
