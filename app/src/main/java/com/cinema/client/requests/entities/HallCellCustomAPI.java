package com.cinema.client.requests.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class HallCellCustomAPI {

    @Getter
    @Setter
    @SerializedName("old_row")
    @Expose
    private Integer oldRow;

    @Getter
    @Setter
    @SerializedName("old_col")
    @Expose
    private Integer oldCol;

    @Getter
    @Setter
    @SerializedName("new_row")
    @Expose
    private Integer newRow;

    @Getter
    @Setter
    @SerializedName("new_col")
    @Expose
    private Integer newCol;

}
