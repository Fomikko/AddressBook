package com.taxtelecom.arinamurasheva.addressbook.ContactList.Contractor;

import com.taxtelecom.arinamurasheva.addressbook.Observer.IPublisher;
import com.taxtelecom.arinamurasheva.addressbook.Model.Department;

import org.json.JSONException;

public interface IContactListContractor extends IPublisher {

    Department getDeptFromJson(String url) throws JSONException;
    Department getRootDepartment();
    void fetchContactListData(String url);

}
