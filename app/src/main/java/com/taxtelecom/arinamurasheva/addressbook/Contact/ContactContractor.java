package com.taxtelecom.arinamurasheva.addressbook.Contact;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ContactContractor {

    private final List<ContactPresenter> subscribers = new ArrayList<>();

    private Bitmap mPersonPhoto;

    public Bitmap getPersonPhoto() {
        return mPersonPhoto;
    }


    public void notifySucess() {
        synchronized (subscribers) {
            for (ContactPresenter presenter : subscribers) {
                presenter.updateSuccess();
            }
        }
    }

    public void notifyFail() {
        synchronized (subscribers) {
            for (ContactPresenter presenter : subscribers) {
                presenter.updateFail();
            }
        }
    }

    public void subscribe(ContactPresenter presenter) {
        synchronized (subscribers) {
            subscribers.add(presenter);
        }
    }

    public void fetchUserPhoto(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                notifyFail();

                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                InputStream inputStream = response.body().byteStream();
                mPersonPhoto = BitmapFactory.decodeStream(inputStream);

                notifySucess();

            }

        });
    }
}
