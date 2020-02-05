package com.taxtelecom.arinamurasheva.addressbook.ContactList.Interactor;

import com.taxtelecom.arinamurasheva.addressbook.Model.Department;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;
import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;
import com.taxtelecom.arinamurasheva.addressbook.Observer.IPublisher;

import java.util.List;

public interface IContactListInteractor extends IPublisher {


    Department getContactListData();
    void fetchContactListData();
    Person getContact(List<Integer> routingList);
    String getErrorMessage();

}
