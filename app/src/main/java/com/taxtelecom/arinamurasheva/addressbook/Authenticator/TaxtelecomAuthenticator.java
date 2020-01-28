package com.taxtelecom.arinamurasheva.addressbook.Authenticator;

import com.taxtelecom.arinamurasheva.addressbook.ContactListApp;
import com.taxtelecom.arinamurasheva.addressbook.utilities.NetworkUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TaxtelecomAuthenticator {

    public final List<AuthenticatorPresenter> subscribers = new ArrayList<>();



    public void confirmCredentials(final String userLogin, final String userPassword) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(NetworkUtils.buildAuthUrl(userLogin, userPassword))
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws
                    IOException {
                final String responseJsonString = response.body().string();

                JSONObject authJsonObject = null;
                try {
                    authJsonObject = new JSONObject(responseJsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String MESSAGE = "Message";
                final String SUCCESS = "Success";

                Boolean isValid = false;
                String errorMessage = null;
                try {
                    isValid = authJsonObject.getBoolean(SUCCESS);
                    errorMessage = authJsonObject.getString(MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (isValid) {
                    notifySubscribersSuccess();
                    SharedPreferencesManager.getInstance().saveUserData(userLogin, userPassword);

                } else {
                    notifySubscribersFail(errorMessage);
                }

            }

        });
    }

    public void subscribe(AuthenticatorPresenter subscriber) {
        synchronized (subscribers) {
            subscribers.add(subscriber);
        }
    }

    public void notifySubscribersSuccess() {
        synchronized (subscribers) {
            for (AuthenticatorPresenter s : subscribers) {
                s.updateSuccess();
            }
        }
    }

    public void notifySubscribersFail(String message) {
        synchronized (subscribers) {
            for (AuthenticatorPresenter s : subscribers) {
                s.updateFail(message);
            }
        }
    }
}

