package com.cinema.client.requests;

import android.content.Context;
import android.content.SharedPreferences;

import com.cinema.client.R;
import com.cinema.client.requests.entities.TokenAPI;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

public class TokenAuthenticator implements Authenticator {

    private Context context;

    private TokenHolder myServiceHolder;

    public TokenAuthenticator(Context context, TokenHolder myServiceHolder) {
        this.context = context;
        this.myServiceHolder = myServiceHolder;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        if (myServiceHolder == null) {
            return null;
        }
        SharedPreferences settings = context.getSharedPreferences("refreshToken", context.MODE_PRIVATE);

//        RequestBody username = RequestBody.create(MediaType.parse("text/plain"),
//                settings.getString("refreshToken", null));

        RequestBody refreshToken = RequestBody.create(MediaType.parse("text/plain"),
                settings.getString("refreshToken", null));


        retrofit2.Response retrofitResponse = myServiceHolder.getMyService().refreshToken(refreshToken).execute();
        if (retrofitResponse != null) {

            TokenAPI call= (TokenAPI) retrofitResponse.body();

            String newAccessToken = call.getAccess();
            return response.request().newBuilder()
                    .header("Authorization", newAccessToken)
                    .build();
        }
        return null;

    }
}
