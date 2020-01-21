
package com.cinema.client.requests.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
public class FilmAPI {

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

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
//
//    public Integer getDuration() {
//        return duration;
//    }
//
//    public void setDuration(Integer duration) {
//        this.duration = duration;
//    }
//
//    public Integer getGenre() {
//        return genre;
//    }
//
//    public void setGenre(Integer genre) {
//        this.genre = genre;
//    }
//
//    public String getVideoUrl() {
//        return videoUrl;
//    }
//
//    public void setVideoUrl(String videoUrl) {
//        this.videoUrl = videoUrl;
//    }
//
//    public String getPicUrl() {
//        return picUrl;
//    }
//
//    public void setPicUrl(String picUrl) {
//        this.picUrl = picUrl;
//    }


}
