package com.cinema.client.requests;

import com.cinema.client.requests.entities.CinemaAPI;
import com.cinema.client.requests.entities.FilmAPI;
import com.cinema.client.requests.entities.RegistrationAPI;
import com.cinema.client.requests.entities.TicketAPI;
import com.cinema.client.requests.entities.TimelineAPI;
import com.cinema.client.requests.entities.TokenAPI;
import com.cinema.client.requests.entities.UserAPI;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIInterface {


    public static final String api_film = "/api/v1/film";
    public static final String api_ticket = "/api/v1/ticket/";
    public static final String api_cinema = "/api/v1/cinema";
    public static final String api_timeline = "/api/v1/timeline";
    public static final String api_registration = "/api/v1/create/";
    public static final String api_user = "/api/v1/user/";
    public static final String api_token = "/api/token/";


    @GET(api_film)
    Call<List<FilmAPI>> getFilms();

    @GET(api_film)
    Call<FilmAPI> getFilmById(@Query("id") int id);

    @GET(api_film)
    Observable<FilmAPI> getFilmByIdRx(@Query("id") int id);


    @GET(api_film)
    Call<FilmAPI> getFilmByTitle(@Query("title") String title);

    @GET(api_film)
    Call<List<FilmAPI>> getFilmByGenre(@Query("genre") int genre);


    @GET(api_ticket)
    Call<TicketAPI> getTicketByCode(@Query("code") String code, @Header("Authorization") String authHeader);

    @GET(api_ticket)
    Call<List<TicketAPI>> getTicketByUserId(@Query("user_id") int user_id, @Header("Authorization") String authHeader);

    @GET(api_ticket)
    Observable<List<TicketAPI>> getTicketByUserIdRx(@Query("user_id") int user_id, @Header("Authorization") String authHeader);

    @POST(api_ticket)
    Call<TicketAPI> updateTicketById(@Query("id") int id, @Body TicketAPI ticket);

    @Multipart
    @POST(api_ticket)
    Call<TicketAPI> createTicket(@Part("place") RequestBody place,
                                 @Part("code") RequestBody code,
                                 @Part("status") RequestBody status,
                                 @Part("cinema_id") RequestBody cinema_id,
                                 @Part("film_id") RequestBody film_id,
                                 @Part("user") RequestBody user,
                                 @Part("date") RequestBody date,
                                 @Header("Authorization") String authHeader);


    @Multipart
    @PUT(api_ticket)
    Call<TicketAPI> updateTicket(@Query("id") int id,
                                 @Part("place") RequestBody place,
                                 @Part("code") RequestBody code,
                                 @Part("status") RequestBody status,
                                 @Part("cinema_id") RequestBody cinema_id,
                                 @Part("film_id") RequestBody film_id,
                                 @Part("user") RequestBody user,
                                 @Part("date") RequestBody date,
                                 @Header("Authorization") String authHeader);


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

    @GET(api_timeline)
    Call<List<TimelineAPI>> getTimelineByDateAndCinemaId(@Query("date") String date, @Query("cinema_id") int cinema_id);

    @GET(api_timeline)
    Call<List<TimelineAPI>> getTimelineByDateAndCinemaIdAndFilmId(@Query("date") String date, @Query("cinema_id") int cinema_id, @Query("film_id") int film_id);


    @Multipart
    @POST(api_registration)
    Call<RegistrationAPI> createNewUser(
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("username") RequestBody username);


    @Multipart
    @POST(api_token)
    Call<TokenAPI> refreshToken(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password);

    @Multipart
    @POST(api_token)
    Observable<TokenAPI> refreshTokenRx(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password);


    @GET(api_user)
    Call<UserAPI> getCurrentUser(@Header("Authorization") String authHeader);

}
