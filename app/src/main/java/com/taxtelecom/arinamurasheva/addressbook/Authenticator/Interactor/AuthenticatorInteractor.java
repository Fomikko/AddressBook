package com.taxtelecom.arinamurasheva.addressbook.Authenticator.Interactor;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;
import com.taxtelecom.arinamurasheva.addressbook.JsonDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;
import com.taxtelecom.arinamurasheva.addressbook.UrlBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class AuthenticatorInteractor implements IAuthenticatorInteractor {

    private String errorMessage;

    private String eventType = EventManager.AUTH;

    private EventManager events;

    public AuthenticatorInteractor() {
        this.events = new EventManager(eventType);
    }

    @Override
    public void confirmCredentials(final String userLogin, final String userPassword) {

        final String url = UrlBuilder.buildAuthUrl(userLogin, userPassword);

        final String MESSAGE = "Message";
        final String SUCCESS = "Success";

        new Thread(new Runnable() {
            @Override
            public void run() {

                Response response = JsonDataFetcher.getInstance().fetchData(url);

                if (response != null) {

                    try {
                        String responseJsonString = response.body().string();
                        JSONObject authJsonObject = new JSONObject(responseJsonString);

                        boolean isValid = authJsonObject.getBoolean(SUCCESS);
                        errorMessage = authJsonObject.getString(MESSAGE);

                        if (isValid) {
                            events.notifySuccess(eventType);
                            SharedPreferencesManager.getInstance().saveUserData(userLogin, userPassword);

                        } else {
                            events.notifyFail(eventType);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    errorMessage = "Отсутствует интернет-соединение.";
                    events.notifyFail(eventType);

                }
            }
        }).start();

    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public EventManager getEvents() {
        return events;
    }
}

