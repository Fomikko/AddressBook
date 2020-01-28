package com.taxtelecom.arinamurasheva.addressbook;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;

public final class ContactListApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferencesManager.getInstance().init(this);

    }

}
