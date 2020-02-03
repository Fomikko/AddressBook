package com.taxtelecom.arinamurasheva.addressbook.Authenticator.Presenter;

public interface IAuthenticatorPresenter {
    void onGetCheckUserDataPresence();
    void onGetLogin(String userLogin, String userPassword);
}
