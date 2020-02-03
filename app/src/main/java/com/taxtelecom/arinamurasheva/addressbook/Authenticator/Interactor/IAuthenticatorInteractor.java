package com.taxtelecom.arinamurasheva.addressbook.Authenticator.Interactor;

import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;

public interface IAuthenticatorInteractor {

    String AUTH = "auth";

    EventManager events = new EventManager(AUTH);

    void confirmCredentials(String userLogin, String userPassword);
    String getErrorMessage();
}
