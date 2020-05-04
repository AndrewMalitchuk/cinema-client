package com.cinema.client.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class FilmItemSearch {

    @Getter
    @Setter
    private int filmId;

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
    private String filmImg;

}
