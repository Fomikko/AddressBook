package com.taxtelecom.arinamurasheva.addressbook.Authenticator.Interactor;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;
import com.taxtelecom.arinamurasheva.addressbook.JsonDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.UrlBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class AuthenticatorInteractor implements IAuthenticatorInteractor {

    private String errorMessage;

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
                            events.notifySuccess(AUTH);
                            SharedPreferencesManager.getInstance().saveUserData(userLogin, userPassword);

                        } else {
                            events.notifyFail(AUTH);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    errorMessage = "Отсутствует интернет-соединение.";
                    events.notifyFail(AUTH);

                }
            }
        }).start();

    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}

