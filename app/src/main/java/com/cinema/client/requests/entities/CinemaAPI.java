package com.cinema.client.requests.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CinemaAPI {

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
    @SerializedName("address")
    @Expose
    private String address;

    @Getter
    @Setter
    @SerializedName("city")
    @Expose
    private String city;

    @Getter
    @Setter
    @SerializedName("telephone")
    @Expose
    private String telephone;

    @Getter
    @Setter
    @SerializedName("geo_lat")
    @Expose
    private Double geoLat;

    @Getter
    @Setter
    @SerializedName("geo_lon")
    @Expose
    private Double geoLon;

    @Getter
    @Setter
    @SerializedName("pic_url")
    @Expose
    private String picUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public CinemaAPI() {
    }

    /**
     *
     * @param picUrl
     * @param address
     * @param city
     * @param name
     * @param geoLat
     * @param telephone
     * @param id
     * @param geoLon
     */
    public CinemaAPI(Integer id, String name, String address, String city, String telephone, Double geoLat, Double geoLon, String picUrl) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        this.geoLat = geoLat;
        this.geoLon = geoLon;
        this.picUrl = picUrl;
    }



}