package com.cinema.client.entities;

import lombok.Getter;
import lombok.Setter;

public class TicketItemSearch {

    @Getter
    @Setter
    private int filmId;

    @Getter
    @Setter
    private int ticketId;

    @Getter
    @Setter
    private int cinemaId;

    @Getter
    @Setter
    private int userId;


    @Getter
    @Setter
    String filmDate;

    @Getter
    @Setter
    String filmTime;

    @Getter
    @Setter
    private String filmName;

    @Getter
    @Setter
    private String filmDateTime;

    @Getter
    @Setter
    private String filmPlace;

    @Getter
    @Setter
    private String filmCinema;

    @Getter
    @Setter
    private String ticketCode;

    @Getter
    @Setter
    private String filmUrl;

    @Getter
    @Setter
    private Integer timelineId;

    @Getter
    @Setter
    private Integer hallId;

    @Getter
    @Setter
    private int status;

    @Getter
    @Setter
    private int filmImg;

}
