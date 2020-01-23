package com.cinema.client.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CinemaItemSearch {

    @Getter
    @Setter
    private int cinemaId;

    @Getter
    @Setter
    private String cinemaName;

    @Getter
    @Setter
    private String cinemaAddress;

    @Getter
    @Setter
    private String cinemaImg;

    public CinemaItemSearch(int cinemaId,String cinemaName, String cinemaAddress, String cinemaImg) {
        this.cinemaId=cinemaId;
        this.cinemaName = cinemaName;
        this.cinemaAddress = cinemaAddress;
        this.cinemaImg = cinemaImg;
    }

    public CinemaItemSearch() {
    }


}
