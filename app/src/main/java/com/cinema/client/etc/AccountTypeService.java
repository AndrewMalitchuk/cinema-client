package com.cinema.client.etc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

// Можно удалить
public class AccountTypeService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        CinemaAppAccountAuthenticator authenticator = new CinemaAppAccountAuthenticator(this);
        return authenticator.getIBinder();
    }
}