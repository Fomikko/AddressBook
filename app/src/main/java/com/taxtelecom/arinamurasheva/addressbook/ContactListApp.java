package com.taxtelecom.arinamurasheva.addressbook;

import android.app.Application;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;
import com.taxtelecom.arinamurasheva.addressbook.DataHandlers.ContactListDBHelper;

public final class ContactListApp extends Application {




    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferencesManager.getInstance().init(this);

        ContactListDBHelper.init(this);

    }


}
