package com.taxtelecom.arinamurasheva.addressbook.Authenticator.Presenter;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.Interactor.IAuthenticatorInteractor;
import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;
import com.taxtelecom.arinamurasheva.addressbook.Authenticator.Interactor.AuthenticatorInteractor;
import com.taxtelecom.arinamurasheva.addressbook.Authenticator.View.AuthenticatorActivity;
import com.taxtelecom.arinamurasheva.addressbook.Authenticator.View.IAuthenticatorView;
import com.taxtelecom.arinamurasheva.addressbook.Observer.EventManager;
import com.taxtelecom.arinamurasheva.addressbook.Observer.IEventSubscriber;

public class AuthenticatorPresenter implements IAuthenticatorPresenter, IEventSubscriber {

    private IAuthenticatorView _view;
    private IAuthenticatorInteractor _model;

    public AuthenticatorPresenter(AuthenticatorActivity view) {
        _view = view;
        _model = new AuthenticatorInteractor();

    }

    @Override
    public void onGetCheckUserDataPresence() {
        SharedPreferencesManager manager = SharedPreferencesManager.getInstance();

        String userLogin = manager.getUserLogin();
        String userPassword = manager.getUserPassword();

        if(!userLogin.equals("") && !userPassword.equals("")) {
           _view.goToContactListView();
        }
    }



    @Override
    public void onGetLogin(String userLogin, String userPassword) {
        _model.getEventManager().subscribe(EventManager.AUTH, this);
        _model.confirmCredentials(userLogin, userPassword);

    }

    @Override
    public void updateSuccess(String eventType) {
        _view.goToContactListView();
    }

    @Override
    public void updateFail(String eventType, String errorMessage) {
        _view.showErrorMessage(errorMessage);
    }

}
