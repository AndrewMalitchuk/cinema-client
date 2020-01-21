package com.cinema.client.entities;

public class CinemaItemSearch {

    private String cinemaName;
    private String cinemaAddress;
    private int cinemaImg;

    public CinemaItemSearch(String cinemaName, String cinemaAddress, int cinemaImg) {
        this.cinemaName = cinemaName;
        this.cinemaAddress = cinemaAddress;
        this.cinemaImg = cinemaImg;
    }

    public CinemaItemSearch() {
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public int getCinemaImg() {
        return cinemaImg;
    }

    public void setCinemaImg(int cinemaImg) {
        this.cinemaImg = cinemaImg;
    }
}
