package com.taxtelecom.arinamurasheva.addressbook;

import android.net.Uri;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlBuilder {

    private final static String TAXTELECOM_BASE_URL =
            "https://contact.taxsee.com/Contacts.svc/";

    private final static String PATH_CL = "GetAll";
    private final static String PATH_AUTH = "Hello";
    private final static String PATH_PHOTO = "GetWPhoto";

    private final static String PARAM_LOGIN = "login";
    private final static String PARAM_PASSWORD = "password";

    private final static String PARAM_ID = "id";

    /**
     * Строит URL для запроса к contact.taxsee.com.
     *
     * @param userLogin Логин для входа в систему.
     * @param userPassword Пароль для входа в систему.
     * @return URL, который используется для запроса к серверу.
     */

    public static String buildUrl(String path, String userLogin, String userPassword) {
        Uri builtUri = Uri.parse(TAXTELECOM_BASE_URL + path).buildUpon()
                .appendQueryParameter(PARAM_LOGIN, userLogin)
                .appendQueryParameter(PARAM_PASSWORD, userPassword)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url.toString();
    }

    public static String buildAuthUrl(String userLogin, String userPassword) {
        return buildUrl(PATH_AUTH, userLogin, userPassword);
    }

    public static String buildContactListUrl() {

        return buildInnerUrl(PATH_CL);
    }

    public static String buildContactPhotoUrl(String userId) {
        Uri builtUri = Uri.parse(buildInnerUrl(PATH_PHOTO)).buildUpon()
                .appendQueryParameter(PARAM_ID, userId)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url.toString();
    }

    private static String buildInnerUrl(String path) {
        SharedPreferencesManager manager = SharedPreferencesManager.getInstance();

        String userLogin;
        String userPassword;

        while ((userLogin = manager.getUserLogin()).equals("") & (userPassword = manager.getUserPassword()).equals("")) {
        }

        return buildUrl(path, userLogin, userPassword);

    }
}
