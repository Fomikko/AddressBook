package com.taxtelecom.arinamurasheva.addressbook;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;

public final class ContactListApp extends Application {

    private static SharedPreferences mUserData;

    private static SharedPreferencesManager sharedPreferencesManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mUserData = this.getSharedPreferences(
                getString(R.string.auth_preference_file),
                Context.MODE_PRIVATE);

        sharedPreferencesManager = SharedPreferencesManager.getInstance();

    }

    public static SharedPreferences getUserData() {
        return mUserData;
    }

    public static SharedPreferencesManager getSharedPreferencesManager() {
        return sharedPreferencesManager;
    }

}
