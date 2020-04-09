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


    private String filmName;
    private String filmDateTime;
    private String filmPlace;
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

    private int filmImg;


    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmDateTime() {
        return filmDateTime;
    }

    public void setFilmDateTime(String filmDateTime) {
        this.filmDateTime = filmDateTime;
    }

    public String getFilmPlace() {
        return filmPlace;
    }

    public void setFilmPlace(String filmPlace) {
        this.filmPlace = filmPlace;
    }

    public String getFilmCinema() {
        return filmCinema;
    }

    public void setFilmCinema(String filmCinema) {
        this.filmCinema = filmCinema;
    }

    public int getFilmImg() {
        return filmImg;
    }

    public void setFilmImg(int filmImg) {
        this.filmImg = filmImg;
    }
}
