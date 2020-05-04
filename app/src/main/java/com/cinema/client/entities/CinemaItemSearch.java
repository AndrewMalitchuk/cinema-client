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

}
