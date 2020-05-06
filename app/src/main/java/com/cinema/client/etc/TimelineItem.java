package com.cinema.client.etc;

import com.cinema.client.requests.entities.TimelineAPI;

import lombok.Getter;
import lombok.Setter;

public class TimelineItem {

    @Getter
    @Setter
    private boolean isActive;

    @Getter
    @Setter
    private String formattedDate;

    @Getter
    @Setter
    private String formattedTime;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private boolean isSelected;

    @Getter
    @Setter
    private String subtitle;

    @Getter
    @Setter
    private TimelineAPI timelineAPI;

    public TimelineItem(boolean isActive, String formattedDate, String formattedTime, String title, boolean isSelected, String subtitle) {
        this.isActive = isActive;
        this.formattedDate = formattedDate;
        this.formattedTime = formattedTime;
        this.title = title;
        this.isSelected = isSelected;
        this.subtitle = subtitle;
    }

    public TimelineItem(boolean isActive, String formattedDate, String formattedTime, String title, boolean isSelected, String subtitle, TimelineAPI timelineAPI) {
        this.isActive = isActive;
        this.formattedDate = formattedDate;
        this.formattedTime = formattedTime;
        this.title = title;
        this.isSelected = isSelected;
        this.subtitle = subtitle;
        this.timelineAPI = timelineAPI;
    }

}
