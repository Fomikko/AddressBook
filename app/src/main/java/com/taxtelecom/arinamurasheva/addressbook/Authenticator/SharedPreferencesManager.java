package com.taxtelecom.arinamurasheva.addressbook.Authenticator;

import android.content.SharedPreferences;

import com.taxtelecom.arinamurasheva.addressbook.ContactList.Presenter.ContactListPresenter;
import com.taxtelecom.arinamurasheva.addressbook.ContactListApp;

import java.util.ArrayList;
import java.util.List;

public final class SharedPreferencesManager {

    private static SharedPreferencesManager instance;

    private final static List<ContactListPresenter> subscribers = new ArrayList<>();

    private SharedPreferencesManager() {

    }

    public static synchronized SharedPreferencesManager getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesManager();
        }

        return instance;
    }

    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";

    public String getUserLogin() {
        return ContactListApp.getUserData().getString(USER_LOGIN, "");
    }

    public String getUserPassword() {
        return ContactListApp.getUserData().getString(USER_PASSWORD, "");
    }



    public void saveUserData(String userLogin, String userPassword) {
        SharedPreferences.Editor editor = ContactListApp.getUserData().edit();
        editor.putString(USER_LOGIN, userLogin);
        editor.putString(USER_PASSWORD, userPassword);
        editor.apply();

    }

    public void removeUserData() {
        SharedPreferences.Editor editor = ContactListApp.getUserData().edit();
        editor.remove(USER_LOGIN);
        editor.remove(USER_PASSWORD);
        editor.apply();
        //notifyUserCredentialsRemoved();
    }

/*    public static void subscribe(ContactListPresenter subscriber) {
        synchronized (subscribers){
            subscribers.add(subscriber);
        }
    }*/

/*    public static void notifyUserCredentialsRemoved() {
        synchronized (subscribers) {
            for (ContactListPresenter subscriber : subscribers) {
                subscriber.onLoggedOut();
            }
        }
    }*/
}
