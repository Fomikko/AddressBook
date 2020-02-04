package com.taxtelecom.arinamurasheva.addressbook.Contact.Presenter;

import com.taxtelecom.arinamurasheva.addressbook.Contact.Interactor.ContactInteractor;
import com.taxtelecom.arinamurasheva.addressbook.Contact.Interactor.IContactInteractor;
import com.taxtelecom.arinamurasheva.addressbook.Contact.View.ContactActivity;
import com.taxtelecom.arinamurasheva.addressbook.Contact.View.IContactView;
import com.taxtelecom.arinamurasheva.addressbook.Observer.IEventSubscriber;
import com.taxtelecom.arinamurasheva.addressbook.UrlBuilder;

public class ContactPresenter implements IContactPresenter, IEventSubscriber {

    private IContactView _view;
    private IContactInteractor _model;

    public ContactPresenter(ContactActivity view) {
        _view = view;
        _model = new ContactInteractor();
    }

    @Override
    public void updateSuccess(String eventType) {
        _view.setContactPhoto(_model.getContactPhoto());
    }

    @Override
    public void updateFail(String eventType) {
        //TODO Загрузить стандартную картинку.
    }

    @Override
    public void onGetPhotoRequest(String personId) {
        _model.events.subscribe(_model.CONTACT_PHOTO, this);
        _model.fetchContactPhoto(personId);
    }
}
