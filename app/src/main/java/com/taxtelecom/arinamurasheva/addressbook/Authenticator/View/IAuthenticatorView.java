package com.taxtelecom.arinamurasheva.addressbook.Authenticator.View;

public interface IAuthenticatorView {
    void goToContactListView();
    void checkUserDataPresence();
    void showErrorMessage(final String message);

}
