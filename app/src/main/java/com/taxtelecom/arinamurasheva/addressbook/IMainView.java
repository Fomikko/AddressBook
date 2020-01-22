package com.taxtelecom.arinamurasheva.addressbook;

import com.taxtelecom.arinamurasheva.addressbook.model.Department;

import java.util.List;

public interface IMainView {

    void requestDataLoad();
    void setContactListLayout(Item item);
    void showContactDataView();
    void showErrorMessage();
    void showLoadingIndicator();

}
