package com.taxtelecom.arinamurasheva.addressbook.Authenticator.Interactor;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;
import com.taxtelecom.arinamurasheva.addressbook.DataHandlers.IDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.DataHandlers.JsonDataFetcher;
import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;
import com.taxtelecom.arinamurasheva.addressbook.UrlBuilder;

public class AuthenticatorInteractor implements IAuthenticatorInteractor {

    private String eventType = EventManager.AUTH;

    private EventManager eventManager;

    public AuthenticatorInteractor() {
        this.eventManager = new EventManager(eventType);
    }

    @Override
    public void confirmCredentials(final String userLogin, final String userPassword) {

        final String url = UrlBuilder.buildAuthUrl(userLogin, userPassword);

        new Thread(new Runnable() {
            @Override
            public void run() {

                IDataFetcher dataFetcher = new JsonDataFetcher(url);
                boolean isValid = dataFetcher.confirmCredentials();

                if (isValid) {

                    eventManager.notifySuccess(eventType);
                    SharedPreferencesManager.getInstance().saveUserData(userLogin, userPassword);

                } else {
                    eventManager.notifyFail(eventType, dataFetcher.getErrorMessage());

                }
            }
        }).start();

    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }
}

