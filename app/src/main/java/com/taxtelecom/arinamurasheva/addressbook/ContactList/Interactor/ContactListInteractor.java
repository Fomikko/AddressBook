package com.taxtelecom.arinamurasheva.addressbook.ContactList.Interactor;

import com.taxtelecom.arinamurasheva.addressbook.DataHandlers.IDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.DataHandlers.JsonDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.Model.Department;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;
import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;
import com.taxtelecom.arinamurasheva.addressbook.UrlBuilder;

import java.util.List;

public class ContactListInteractor implements IContactListInteractor {

    private Department mContactListData;

    private EventManager eventManager;

    private String eventType = EventManager.CONTACT_LIST;

    public ContactListInteractor() {

        this.eventManager = new EventManager(eventType);

    }

    @Override
    public Department getContactListData() {
        return mContactListData;
    }


    @Override
    public void fetchContactListData() {

        final String url = UrlBuilder.buildContactListUrl();

        new Thread(new Runnable() {
            @Override
            public void run() {

                IDataFetcher dataFetcher = new JsonDataFetcher(url);

                Department dept = dataFetcher.getDepartment();
                if (dept != null) {

                    mContactListData = dept;
                    eventManager.notifySuccess(eventType);

                } else {
                    eventManager.notifyFail(eventType, dataFetcher.getErrorMessage());
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
    public EventManager getEventManager() {
        return eventManager;
    }
}
