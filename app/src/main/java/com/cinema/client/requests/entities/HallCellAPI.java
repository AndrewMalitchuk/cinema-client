package com.cinema.client.requests.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class HallCellAPI {

    //TODO: нормально сделай
    @Getter
    @Setter
    private String sector;

    @Getter
    @Setter
    @SerializedName("row")
    @Expose
    private Integer row;

    @Getter
    @Setter
    @SerializedName("col")
    @Expose
    private Integer col;


}
