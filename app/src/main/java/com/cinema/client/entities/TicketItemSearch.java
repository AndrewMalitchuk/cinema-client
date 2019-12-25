package com.cinema.client.entities;

public class TicketItemSearch {

    private String filmName;
    private String filmDateTime;
    private String filmPlace;
    private String filmCinema;

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
