package com.taxtelecom.arinamurasheva.addressbook.utilities;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {
    final static String TAXTELECOM_BASE_URL =
            "https://contact.taxsee.com/Contacts.svc/";

    final static String PATH_CL = "GetAll";
    final static String PATH_AUTH = "Hello";

    final static String PARAM_LOGIN = "login";
    final static String PARAM_PASSWORD = "password";

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

    public static String buildContactListUrl(String userLogin, String userPassword) {
        return buildUrl(PATH_CL, userLogin, userPassword);
    }

}
