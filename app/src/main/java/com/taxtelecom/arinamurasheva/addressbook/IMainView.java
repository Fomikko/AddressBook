package com.taxtelecom.arinamurasheva.addressbook;

import com.taxtelecom.arinamurasheva.addressbook.model.Department;

public interface IMainView {

    void requestDataLoad();
    void setContactListLayout(Department dept);
    void showContactDataView();
    void showErrorMessage();
    void showLoadingIndicator();

}
