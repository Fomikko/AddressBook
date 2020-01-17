package com.taxtelecom.arinamurasheva.addressbook.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String TAXTELECOM_BASE_URL =
            "https://contact.taxsee.com/Contacts.svc/GetAll";

    final static String PARAM_LOGIN = "login";

    final static String PARAM_PASSWORD = "password";

    /**
     * Строит URL для запроса к contact.taxsee.com.
     *
     * @param taxtelecomLogin Логин для входа в систему.
     * @param taxtelecomPassword Пароль для входа в систему.
     * @return URL, который используется для запроса к серверу.
     */
    public static String buildUrl(String taxtelecomLogin, String taxtelecomPassword) {
        Uri builtUri = Uri.parse(TAXTELECOM_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_LOGIN, taxtelecomLogin)
                .appendQueryParameter(PARAM_PASSWORD, taxtelecomPassword)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url.toString();
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url URL, откуда нужно получить ответ.
     * @return Содержание ответа HTTP.
     */
/*    public static String getResponseFromHttp(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }*/

}
