package com.taxtelecom.arinamurasheva.addressbook.ContactList.Interactor;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taxtelecom.arinamurasheva.addressbook.JsonDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.Model.Department;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;
import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;
import com.taxtelecom.arinamurasheva.addressbook.UrlBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

public class ContactListInteractor implements IContactListInteractor {

    private Department mContactListData;

    private String errorMessage;

    private EventManager events;

    private String eventType = EventManager.CONTACT_LIST;

    public ContactListInteractor() {

        this.events = new EventManager(eventType);

    }

    @Override
    public Department getContactListData() {
        return mContactListData;
    }

    private Department getDeptFromJson(String deptJsonString) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(deptJsonString, Department.class);
    }

    @Override
    public void fetchContactListData() {

        final String url = UrlBuilder.buildContactListUrl();

        new Thread(new Runnable() {
            @Override
            public void run() {

                Response response = JsonDataFetcher.getInstance().fetchData(url);

                if (response != null) {

                    String responseJsonString = null;
                    try {
                        responseJsonString = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mContactListData = getDeptFromJson(responseJsonString);
                    events.notifySuccess(eventType);

                } else {
                    errorMessage = "Отсутствует интернет-соединение.";
                    events.notifyFail(eventType);
                }
            }
        }).start();
    }

    /*Метод с помощью routingList позволяет найти заданного в обходном листе человека.*/
    @Override
    public Person getContact(List<Integer> routingList) {

        int listSize = routingList.size();
        Department bufferDept = mContactListData;

        for (int i = 0; i < listSize - 1; i++) {

            int curNum = routingList.get(i);

            bufferDept = bufferDept.getDepartments().get(curNum);

        }

        return (bufferDept.getEmployees()).get(routingList.get(listSize - 1));

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
