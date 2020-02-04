package com.taxtelecom.arinamurasheva.addressbook.ContactList.Interactor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taxtelecom.arinamurasheva.addressbook.JsonDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.Model.Department;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;
import com.taxtelecom.arinamurasheva.addressbook.IDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.UrlBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

public class ContactListInteractor implements IContactListInteractor {

    private Department mContactListData;

    @Override
    public Department getContactListData() {
        return this.mContactListData;
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

                String responseJsonString = null;
                try {
                    responseJsonString = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (responseJsonString != null) {
                    mContactListData = getDeptFromJson(responseJsonString);
                    events.notifySuccess(IContactListInteractor.CONTACT_LIST);
                } else {
                    events.notifyFail(IContactListInteractor.CONTACT_LIST);

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

}
