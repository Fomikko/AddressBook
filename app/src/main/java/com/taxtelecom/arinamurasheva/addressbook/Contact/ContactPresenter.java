package com.taxtelecom.arinamurasheva.addressbook.Contact;

import com.taxtelecom.arinamurasheva.addressbook.utilities.NetworkUtils;

public class ContactPresenter {

    ContactActivity _view;
    ContactContractor _model;

    public ContactPresenter(ContactActivity view) {
        _view = view;
        _model = new ContactContractor();
    }

    public void updateSuccess() {
        _view.setPersonPhoto(_model.getPersonPhoto());
    }

    public void updateFail() {
        //TODO Загрузить стандартную картинку.
    }

    public void onGetPhotoRequest(String personId) {
        _model.fetchUserPhoto(NetworkUtils.buildContactPhotoUri(personId));
        _model.subscribe(this);
    }
}
