package com.taxtelecom.arinamurasheva.addressbook.ContactList.Contractor;

import com.taxtelecom.arinamurasheva.addressbook.Model.Person;
import com.taxtelecom.arinamurasheva.addressbook.Observer.IPublisher;
import com.taxtelecom.arinamurasheva.addressbook.Model.Department;

import org.json.JSONException;

import java.util.List;

public interface IContactListContractor extends IPublisher {

    Department getDeptFromJson(String url) throws JSONException;
    Department getRootDepartment();
    void fetchContactListData(String url);

    void subscribeFotUserInfo();
    void notifyUserInfo();

    Person getUserId(List<Integer> routingList);

}
