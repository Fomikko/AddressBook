package com.taxtelecom.arinamurasheva.addressbook.model;

import org.json.JSONException;

public interface IContactListModel {

    Department getDeptFromJson(String url) throws JSONException;
    Department getRootDepartment();
    void fetchContactListData(String url);
    void subscribe(IJsonResponseSubscriber subscriber);
    void unsubscribe(IJsonResponseSubscriber subscriber);
    void notifySubscribers();

}
