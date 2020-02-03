package com.taxtelecom.arinamurasheva.addressbook.Contact.Interactor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.taxtelecom.arinamurasheva.addressbook.IDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.JsonDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.UrlBuilder;

import java.io.IOException;

public class ContactInteractor implements IContactInteractor {

    private Bitmap mPersonPhoto;

    @Override
    public void fetchContactPhoto(String contactId) {

        String url = UrlBuilder.buildContactPhotoUri(contactId);
        IDataFetcher dataFetcher = new JsonDataFetcher(url);

        try {
            mPersonPhoto = BitmapFactory.decodeFile(dataFetcher.fetchData(url));
            events.notifySuccess(IContactInteractor.CONTACT_DATA);
        } catch (IOException e) {
            e.printStackTrace();
            events.notifyFail(IContactInteractor.CONTACT_DATA);
        }
    }

    @Override
    public Bitmap getContactPhoto() {
        return mPersonPhoto;
    }

}
