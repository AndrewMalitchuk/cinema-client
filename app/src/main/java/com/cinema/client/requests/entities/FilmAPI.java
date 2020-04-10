
package com.cinema.client.requests.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
public class FilmAPI  {
//public class FilmAPI  implements Parcelable {

    @Getter
    @Setter
    @SerializedName("id")
    @Expose
    private Integer id;

    @Getter
    @Setter
    @SerializedName("title")
    @Expose
    private String title;

    @Getter
    @Setter
    @SerializedName("description")
    @Expose
    private String description;

    @Getter
    @Setter
    @SerializedName("date")
    @Expose
    private String date;

    @Getter
    @Setter
    @SerializedName("duration")
    @Expose
    private Integer duration;

    @Getter
    @Setter
    @SerializedName("genre")
    @Expose
    private Integer genre;

    @Getter
    @Setter
    @SerializedName("video_url")
    @Expose
    private String videoUrl;

    @Getter
    @Setter
    @SerializedName("pic_url")
    @Expose
    private String picUrl;

    /**
     * No args constructor for use in serialization
     */
    public FilmAPI() {
    }

    /**
     * @param date
     * @param duration
     * @param picUrl
     * @param videoUrl
     * @param genre
     * @param description
     * @param id
     * @param title
     */
    public FilmAPI(Integer id, String title, String description, String date, Integer duration, Integer genre, String videoUrl, String picUrl) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.duration = duration;
        this.genre = genre;
        this.videoUrl = videoUrl;
        this.picUrl = picUrl;
    }

    protected FilmAPI(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        title = in.readString();
        description = in.readString();
        date = in.readString();
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        if (in.readByte() == 0) {
            genre = null;
        } else {
            genre = in.readInt();
        }
        videoUrl = in.readString();
        picUrl = in.readString();
    }




}
