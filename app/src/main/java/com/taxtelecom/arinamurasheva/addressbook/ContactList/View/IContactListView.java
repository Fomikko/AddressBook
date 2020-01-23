package com.taxtelecom.arinamurasheva.addressbook.ContactList.View;

public interface IContactListView {

    void requestDataLoad();
    void setContactListLayout(Item item);
    void showContactDataView();
    void showErrorMessage();
    void showLoadingIndicator();

}
