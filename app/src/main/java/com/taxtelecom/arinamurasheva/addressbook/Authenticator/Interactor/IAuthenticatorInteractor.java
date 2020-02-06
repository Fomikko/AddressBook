package com.taxtelecom.arinamurasheva.addressbook.Authenticator.Interactor;

import com.taxtelecom.arinamurasheva.addressbook.Observer.IPublisher;

public interface IAuthenticatorInteractor extends IPublisher {

    void confirmCredentials(String userLogin, String userPassword);

}
