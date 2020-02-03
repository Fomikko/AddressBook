package com.taxtelecom.arinamurasheva.addressbook.Authenticator;

import android.content.Context;
import android.content.SharedPreferences;

public final class SharedPreferencesManager {

    private SharedPreferences mUserData;

    private static SharedPreferencesManager instance;

    private SharedPreferencesManager() {

    }

    public void init(Context context) {
        mUserData = context.getSharedPreferences(
                "com.taxtelecom.arinamurasheva.addressbook.PreferenceFileAuth",
                Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesManager getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesManager();
        }

        return instance;
    }

    public SharedPreferences getUserData() {
        return mUserData;
    }

    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";

    public String getUserLogin() {
        return mUserData.getString(USER_LOGIN, "");
    }

    public String getUserPassword() {
        return mUserData.getString(USER_PASSWORD, "");
    }



    public void saveUserData(String userLogin, String userPassword) {
        SharedPreferences.Editor editor = mUserData.edit();
        editor.putString(USER_LOGIN, userLogin);
        editor.putString(USER_PASSWORD, userPassword);
        editor.apply();

    }

    public void removeUserData() {
        SharedPreferences.Editor editor = mUserData.edit();
        editor.remove(USER_LOGIN);
        editor.remove(USER_PASSWORD);
        editor.apply();
    }

}
