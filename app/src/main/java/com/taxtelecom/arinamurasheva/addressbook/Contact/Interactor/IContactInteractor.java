package com.taxtelecom.arinamurasheva.addressbook.Contact.Interactor;

import android.graphics.Bitmap;

import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;

public interface IContactInteractor {
    String CONTACT_DATA = "contact data";

    EventManager events = new EventManager(CONTACT_DATA);

    void fetchContactPhoto(String contactId);
    Bitmap getContactPhoto();
}
