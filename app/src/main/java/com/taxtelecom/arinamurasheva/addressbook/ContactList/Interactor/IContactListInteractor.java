package com.taxtelecom.arinamurasheva.addressbook.ContactList.Interactor;

import com.taxtelecom.arinamurasheva.addressbook.Model.Department;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;
import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;

import java.util.List;

public interface IContactListInteractor {

    String CONTACT_LIST = "contact list";

    EventManager events = new EventManager(CONTACT_LIST);

    Department getContactListData();
    void fetchContactListData();
    Person getContact(List<Integer> routingList);

}
