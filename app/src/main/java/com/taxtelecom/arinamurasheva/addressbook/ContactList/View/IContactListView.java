package com.taxtelecom.arinamurasheva.addressbook.ContactList.View;

import com.taxtelecom.arinamurasheva.addressbook.Model.Person;

import java.util.List;

public interface IContactListView {

    void requestDataLoad();
    void setContactListLayout(Item item);
    void showContactDataView();
    void showErrorMessage();
    void showLoadingIndicator();
    void requestLogOut();
    void goToLoginView();
    void goToContactView(Person person);
    void requestContactInfo(List<Integer> routingList);

}
