package com.taxtelecom.arinamurasheva.addressbook.Contact.Interactor;

import android.graphics.Bitmap;

import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;
import com.taxtelecom.arinamurasheva.addressbook.Observer.IPublisher;

public interface IContactInteractor extends IPublisher {

    void fetchContactPhoto(String contactId);
    Bitmap getContactPhoto();

}
