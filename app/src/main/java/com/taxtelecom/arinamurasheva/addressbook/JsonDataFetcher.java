package com.taxtelecom.arinamurasheva.addressbook;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonDataFetcher implements IDataFetcher {

    private static JsonDataFetcher instance;

    private JsonDataFetcher() {

    }

    public static synchronized JsonDataFetcher getInstance() {
        if (instance == null) {
            instance = new JsonDataFetcher();
        }

        return instance;
    }

    @Override
    public Response fetchData(String url) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return response;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }


}
