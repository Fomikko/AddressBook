package com.taxtelecom.arinamurasheva.addressbook.DataHandlers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taxtelecom.arinamurasheva.addressbook.Model.Department;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonDataFetcher implements IDataFetcher {

    private String url;

    private String errorMessage;

    public JsonDataFetcher(String url) {
        this.url = url;
    }

    private Response fetchData() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {

            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                return response;
            }

        } catch (IOException e) {
            errorMessage = "Ошибка сети.";
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public Department getDepartment() {

        Response response = fetchData();

        Department dept = null;
        String responseJsonString = null;

        if (response != null) {
            try {
                responseJsonString = fetchData().body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (responseJsonString != null) {

                dept = parseJsonToDept(responseJsonString);

            }
        }

        return dept;
    }

    private Department parseJsonToDept(String deptJsonString) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(deptJsonString, Department.class);

    }

    @Override
    public Bitmap getContactPhoto() {
        Response response = fetchData();

        if (response != null) {
            InputStream inputStream = response.body().byteStream();
            return BitmapFactory.decodeStream(inputStream);

        } else {
            return null;
        }
    }

    @Override
    public Person getPerson() {
        return null;
    }

    @Override
    public boolean confirmCredentials() {

        Response response = fetchData();

        final String MESSAGE = "Message";
        final String SUCCESS = "Success";

        boolean isValid = false;

        if (response != null) {
            try {
                String responseJsonString = response.body().string();
                JSONObject authJsonObject = new JSONObject(responseJsonString);

                isValid = authJsonObject.getBoolean(SUCCESS);
                errorMessage = authJsonObject.getString(MESSAGE);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            errorMessage = "Ошибка сети.";
        }

        return isValid;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
