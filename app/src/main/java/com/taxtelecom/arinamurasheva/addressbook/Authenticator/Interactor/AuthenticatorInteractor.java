package com.taxtelecom.arinamurasheva.addressbook.Authenticator.Interactor;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;
import com.taxtelecom.arinamurasheva.addressbook.IDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.JsonDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.UrlBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticatorInteractor implements IAuthenticatorInteractor {

    private String errorMessage;

    @Override
    public void confirmCredentials(String userLogin, String userPassword) {

        String url = UrlBuilder.buildAuthUrl(userLogin, userPassword);

        IDataFetcher dataFetcher = new JsonDataFetcher(url);

        final String MESSAGE = "Message";
        final String SUCCESS = "Success";

        boolean isValid = false;

        try {
            String responseJsonString = dataFetcher.fetchData(url);
            JSONObject authJsonObject = new JSONObject(responseJsonString);

            isValid = authJsonObject.getBoolean(SUCCESS);
            errorMessage = authJsonObject.getString(MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (isValid) {
            events.notifySuccess(AUTH);
            SharedPreferencesManager.getInstance().saveUserData(userLogin, userPassword);

        } else {
            events.notifyFail(AUTH);
        }
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}

