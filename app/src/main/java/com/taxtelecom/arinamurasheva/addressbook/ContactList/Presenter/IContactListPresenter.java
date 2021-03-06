package com.taxtelecom.arinamurasheva.addressbook.ContactList.Presenter;

import java.util.List;

public interface IContactListPresenter {

    void onGetDataLoadRequest();
    void onGetLogOutRequest();
    void onGetContactInfoRequest(List<Integer> routingList);

}
