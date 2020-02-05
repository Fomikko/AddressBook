package com.taxtelecom.arinamurasheva.addressbook.Contact.Interactor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.taxtelecom.arinamurasheva.addressbook.JsonDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;
import com.taxtelecom.arinamurasheva.addressbook.UrlBuilder;

import java.io.InputStream;

import okhttp3.Response;

public class ContactInteractor implements IContactInteractor {

    private Bitmap mPersonPhoto;

    private EventManager events;

    private String eventType = EventManager.CONTACT_PHOTO;

    public ContactInteractor() {
        events = new EventManager(eventType);
    }

    @Override
    public void fetchContactPhoto(String contactId) {

        final String url = UrlBuilder.buildContactPhotoUrl(contactId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = JsonDataFetcher.getInstance().fetchData(url);

                InputStream inputStream = response.body().byteStream();
                mPersonPhoto = BitmapFactory.decodeStream(inputStream);

                events.notifySuccess(eventType);

                //events.notifyFail(eventType);

            }
        }).start();
    }

    @Override
    public Bitmap getContactPhoto() {
        return mPersonPhoto;
    }

    @Override
    public EventManager getEvents() {
        return events;
    }
}
