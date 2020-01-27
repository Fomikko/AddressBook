package com.taxtelecom.arinamurasheva.addressbook.ContactList.View;

import android.content.SharedPreferences;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;

public interface IContactListView {

    void requestDataLoad();
    void setContactListLayout(Item item);
    void showContactDataView();
    void showErrorMessage();
    void showLoadingIndicator();
    void logOutRequest();
    void goToLoginView();


}
