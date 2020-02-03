package com.taxtelecom.arinamurasheva.addressbook;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonDataFetcher implements IDataFetcher {

    private final OkHttpClient client = new OkHttpClient();

    private String url;

    public JsonDataFetcher(String url) {
        this.url = url;
    }

    @Override
    public String fetchData(String url) {
        return dataFetcherThread();
    }

    private String dataFetcherThread() {

        final String responseString = "";

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        responseString.concat(response.body().string());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //TODO Это не будет работать. Переделать для работы в бэке.
        thread.start();

        return responseString;
    }

}
