package com.cinema.client.requests;

import com.cinema.client.requests.entities.CinemaAPI;
import com.cinema.client.requests.entities.FilmAPI;
import com.cinema.client.requests.entities.TicketAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {




    @GET("/api/v1/film")
    Call<FilmAPI> getFilmById(@Query("id") int id);

    @GET("/api/v1/ticket")
    Call<TicketAPI> getTicketByCode(@Query("code") String code);

    @GET("/api/v1/cinema")
    Call<CinemaAPI> getCinemaById(@Query("id") int id);

}
