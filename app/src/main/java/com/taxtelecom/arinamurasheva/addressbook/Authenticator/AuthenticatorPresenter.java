package com.taxtelecom.arinamurasheva.addressbook.Authenticator;

public class AuthenticatorPresenter {
    private AuthenticatorActivity _view;
    private TaxtelecomAuthenticator _model;

    AuthenticatorPresenter(AuthenticatorActivity view) {
        _view = view;
        _model = new TaxtelecomAuthenticator();

    }

    void onGetCheckUserDataPresence() {
        SharedPreferencesManager manager = SharedPreferencesManager.getInstance();

        String userLogin = manager.getUserLogin();
        String userPassword = manager.getUserPassword();

        if(!userLogin.equals("") && !userPassword.equals("")) {
           _view.goToContactListView();
        }
    }

    void updateSuccess() {
        _view.goToContactListView();
    }

    void updateFail(String message) {
        _view.showErrorMessage(message);
    }

    void onGetLogin(String userLogin, String userPassword) {
        _model.confirmCredentials(userLogin, userPassword);
        _model.subscribe(this);

    }


}
