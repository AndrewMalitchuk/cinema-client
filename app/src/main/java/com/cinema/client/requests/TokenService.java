package com.cinema.client.requests;

import com.cinema.client.requests.entities.TokenAPI;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface TokenService {

    public static final String api_token = "/api/token/";

    @Multipart
    @POST(api_token)
    Call<TokenAPI> refreshToken(
            @Part("refresh") RequestBody token);


}
