package com.cinema.client.requests.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class TicketAPI {

    @Getter
    @Setter
    @SerializedName("id")
    @Expose
    private Integer id;

    @Getter
    @Setter
    @SerializedName("place")
    @Expose
    private String place;

    @Getter
    @Setter
    @SerializedName("code")
    @Expose
    private String code;

    @Getter
    @Setter
    @SerializedName("date")
    @Expose
    private String date;

    @Getter
    @Setter
    @SerializedName("status")
    @Expose
    private Integer status;

    @Getter
    @Setter
    @SerializedName("cinema_id")
    @Expose
    private Integer cinemaId;

    @Getter
    @Setter
    @SerializedName("film_id")
    @Expose
    private Integer filmId;

    @Getter
    @Setter
    @SerializedName("user")
    @Expose
    private Integer user;

    /**
     * No args constructor for use in serialization
     *
     */
    private TicketAPI() {
    }

    /**
     *
     * @param date
     * @param code
     * @param cinemaId
     * @param filmId
     * @param id
     * @param place
     * @param user
     * @param status
     */
    private TicketAPI(Integer id, String place, String code, String date, Integer status, Integer cinemaId, Integer filmId, Integer user) {
        super();
        this.id = id;
        this.place = place;
        this.code = code;
        this.date = date;
        this.status = status;
        this.cinemaId = cinemaId;
        this.filmId = filmId;
        this.user = user;
    }

}