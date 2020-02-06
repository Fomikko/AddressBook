package com.taxtelecom.arinamurasheva.addressbook.Contact.Presenter;

import com.taxtelecom.arinamurasheva.addressbook.Contact.Interactor.ContactInteractor;
import com.taxtelecom.arinamurasheva.addressbook.Contact.Interactor.IContactInteractor;
import com.taxtelecom.arinamurasheva.addressbook.Contact.View.ContactActivity;
import com.taxtelecom.arinamurasheva.addressbook.Contact.View.IContactView;
import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;
import com.taxtelecom.arinamurasheva.addressbook.Observer.IEventSubscriber;

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
        _view.showContactPhoto();
    }

    @Override
    public void updateFail(String eventType, String errorMessage) {
        //TODO Загрузить стандартную картинку.
    }

    @Override
    public void onGetPhotoRequest(String personId) {
        _view.showPhotoLoadingIndicator();
        _model.getEventManager().subscribe(EventManager.CONTACT_PHOTO, this);
        _model.fetchContactPhoto(personId);
    }
}
