package com.taxtelecom.arinamurasheva.addressbook.Authenticator;

public class AuthenticatorPresenter {
    private AuthenticatorActivity _view;
    private TaxtelecomAuthenticator _model;

    public AuthenticatorPresenter(AuthenticatorActivity view) {
        _view = view;
        _model = new TaxtelecomAuthenticator();
    }

    public void onGetConfirmCredentialsRequest(String userLogin, String userPassword) {
        _model.confirmCredentials(userLogin, userPassword);
        _model.subscribe(this);
    }

    public void updateSuccess() {
        _view.goToContactListView();
    }

    public void updateFail(String message) {
        _view.showErrorMessage(message);
    }
}
