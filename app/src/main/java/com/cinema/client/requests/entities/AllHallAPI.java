package com.cinema.client.requests.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class AllHallAPI {

    @Getter
    @Setter
    @SerializedName("id")
    @Expose
    private Integer id;

    @Getter
    @Setter
    @SerializedName("name")
    @Expose
    private String name;

    @Getter
    @Setter
    @SerializedName("hall_json")
    @Expose
    private String hallJson;

    @Getter
    @Setter
    @SerializedName("cinema_id")
    @Expose
    private Integer cinemaId;


}
