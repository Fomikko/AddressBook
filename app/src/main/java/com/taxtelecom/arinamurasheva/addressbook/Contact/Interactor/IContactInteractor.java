package com.taxtelecom.arinamurasheva.addressbook.Contact.Interactor;

import android.graphics.Bitmap;

import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;

public interface IContactInteractor {
    String CONTACT_PHOTO = "contact [hoto";

    EventManager events = new EventManager(CONTACT_PHOTO);

    void fetchContactPhoto(String contactId);
    Bitmap getContactPhoto();
}
