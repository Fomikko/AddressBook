package com.taxtelecom.arinamurasheva.addressbook.Contact.Interactor;

import android.graphics.Bitmap;

import com.taxtelecom.arinamurasheva.addressbook.DataHandlers.IDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.DataHandlers.JSONDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;
import com.taxtelecom.arinamurasheva.addressbook.UrlBuilder;

public class ContactInteractor implements IContactInteractor {

    private Bitmap mPersonPhoto;

    private EventManager eventManager;

    private String eventType = EventManager.CONTACT_PHOTO;

    public ContactInteractor() {
        eventManager = new EventManager(eventType);
    }

    @Override
    public void fetchContactPhoto(String contactId) {

        final String url = UrlBuilder.buildContactPhotoUrl(contactId);

        new Thread(new Runnable() {
            @Override
            public void run() {

                IDataFetcher dataFetcher = new JSONDataFetcher(url);
                mPersonPhoto = dataFetcher.getContactPhoto();
                eventManager.notifySuccess(eventType);

            }
        }).start();
    }

    @Override
    public Bitmap getContactPhoto() {
        return mPersonPhoto;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }
}
