package com.cinema.client.requests.entities;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class HallAPI {

    @Getter
    @Setter
    @SerializedName("sector")
    @Expose
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

    @Getter
    @Setter
    @SerializedName("disabled")
    @Expose
    private List<HallCellAPI> disabled = null;

    @Getter
    @Setter
    @SerializedName("booked")
    @Expose
    private List<HallCellAPI> booked = null;

    @Getter
    @Setter
    @SerializedName("free")
    @Expose
    private List<HallCellAPI> free = null;


    @Getter
    @Setter
    @SerializedName("bought")
    @Expose
    private List<HallCellAPI> bought = null;

    @Getter
    @Setter
    @SerializedName("custom")
    @Expose
    private List<HallCellCustomAPI> custom = null;

}
