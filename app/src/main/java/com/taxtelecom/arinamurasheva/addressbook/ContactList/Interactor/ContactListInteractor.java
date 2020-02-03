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

        String url = UrlBuilder.buildContactListUrl();
        IDataFetcher dataFetcher = new JsonDataFetcher(url);

        try {

            mContactListData = getDeptFromJson(dataFetcher.fetchData(url));
            events.notifySuccess(IContactListInteractor.CONTACT_LIST);

        } catch (IOException e) {

            e.printStackTrace();
            events.notifyFail(IContactListInteractor.CONTACT_LIST);
        }

    }

    /*Метод с помощью routingList позволяет найти заданного в обходном листе человека.*/
    @Override
    public Person getContact(List<Integer> routingList) {

        int listSize = routingList.size();
        Department bufferDept = mContactListData;

        System.out.println("ROUTING LIST");
        for (Integer integer : routingList) {
            System.out.println(integer);
        }

        for (int i = 0; i < listSize - 1; i++) {
            int curNum = routingList.get(i);

            bufferDept = bufferDept.getDepartments().get(curNum);
            System.out.println("bufferDept " + bufferDept);

        }

        Person person = (bufferDept.getEmployees()).get(routingList.get(listSize - 1));

        System.out.println("PERSON " + person.getId());

        return person;

    }

}
