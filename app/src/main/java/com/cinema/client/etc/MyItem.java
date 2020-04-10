package com.cinema.client.etc;

import android.util.Log;

import com.cinema.client.requests.entities.TimelineAPI;

import lombok.Getter;
import lombok.Setter;

public class MyItem{

    private boolean isActive;
    private String formattedDate;
    @Getter
    @Setter
    private String formattedTime;
    private String title;
    private boolean isSelected;
    private String subtitle;

    //
    @Getter
    @Setter
    private TimelineAPI timelineAPI;
    //


    public MyItem(boolean isActive, String formattedDate,String formattedTime, String title, boolean isSelected, String subtitle) {
        this.isActive = isActive;
        this.formattedDate = formattedDate;
        this.formattedTime=formattedTime;
        this.title = title;
        this.isSelected = isSelected;
        this.subtitle = subtitle;
    }

    public MyItem(boolean isActive, String formattedDate, String formattedTime,String title, boolean isSelected, String subtitle, TimelineAPI timelineAPI) {
        this.isActive = isActive;
        this.formattedDate = formattedDate;
        this.formattedTime=formattedTime;
        this.title = title;
        this.isSelected = isSelected;
        this.subtitle = subtitle;
        this.timelineAPI=timelineAPI;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
