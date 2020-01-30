package com.taxtelecom.arinamurasheva.addressbook.ContactList.View;

import android.content.SharedPreferences;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;

import java.io.Serializable;
import java.util.List;

public interface IContactListView {

    void requestDataLoad();
    void setContactListLayout(Item item);
    void showContactDataView();
    void showErrorMessage();
    void showLoadingIndicator();
    void logOutRequest();
    void goToLoginView();
    void goToContactView(Person person);
    void requestContactInfo(List<Integer> routingList);

}
