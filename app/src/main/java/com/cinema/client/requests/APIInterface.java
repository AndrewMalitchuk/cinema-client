package com.cinema.client.requests;

import com.cinema.client.requests.entities.CinemaAPI;
import com.cinema.client.requests.entities.FilmAPI;
import com.cinema.client.requests.entities.TicketAPI;
import com.cinema.client.requests.entities.TimelineAPI;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {


    public static final String api_film = "/api/v1/film";
    public static final String api_ticket = "/api/v1/ticket";
    public static final String api_cinema = "/api/v1/cinema";
    public static final String api_timeline = "/api/v1/timeline";


    @GET(api_film)
    Call<List<FilmAPI>> getFilms();

    @GET(api_film)
    Call<FilmAPI> getFilmById(@Query("id") int id);

    @GET(api_film)
    Call<FilmAPI> getFilmByTitle(@Query("title") String title);

    @GET(api_film)
    Call<List<FilmAPI>> getFilmByGenre(@Query("genre") int genre);


    @GET(api_ticket)
    Call<TicketAPI> getTicketByCode(@Query("code") String code);


    @GET(api_cinema)
    Call<List<CinemaAPI>> getCinemas();

    @GET(api_cinema)
    Call<CinemaAPI> getCinemaById(@Query("id") int id);

    @GET(api_cinema)
    Call<CinemaAPI> getCinemaByName(@Query("name") String name);

    @GET(api_cinema)
    Call<List<CinemaAPI>> getCinemaByCity(@Query("city") int city);


    @GET(api_timeline)
    Call<List<TimelineAPI>> getTimeline();

    @GET(api_timeline)
    Call<List<TimelineAPI>> getTimelineByCinemaId(@Query("id") int id);

    @GET(api_timeline)
    Call<List<TimelineAPI>> getTimelineByCinemaIdAndFilmId(@Query("cinema_id") int cinema_id, @Query("film_id") int film_id);


}
