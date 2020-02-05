package com.taxtelecom.arinamurasheva.addressbook.Authenticator.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.Presenter.AuthenticatorPresenter;
import com.taxtelecom.arinamurasheva.addressbook.Authenticator.Presenter.IAuthenticatorPresenter;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.View.ContactListActivity;
import com.taxtelecom.arinamurasheva.addressbook.R;

public class AuthenticatorActivity extends AppCompatActivity implements IAuthenticatorView {

    IAuthenticatorPresenter presenter;

    private EditText mLoginEditText;
    private EditText mPasswordEditText;
    private TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authenticator_activity);

        if (presenter == null) {
            presenter = new AuthenticatorPresenter(this);
        }

        checkUserDataPresence();

        mLoginEditText = findViewById(R.id.ev_user_login);
        mPasswordEditText = findViewById(R.id.ev_user_password);

        mErrorMessage = findViewById(R.id.tv_auth_error_message);

    }

    public void Login(View view) {

        String userLogin = mLoginEditText.getText().toString();
        String userPassword = mPasswordEditText.getText().toString();

        presenter.onGetLogin(userLogin, userPassword);

    }

    @Override
    public void goToContactListView() {
        Intent mContactListActivity = new Intent(
                AuthenticatorActivity.this,
                ContactListActivity.class);
        mContactListActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mContactListActivity);
    }

    @Override
    public void checkUserDataPresence() {
        presenter.onGetCheckUserDataPresence();

    }

    @Override
    public void showErrorMessage(final String message) {

        AuthenticatorActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mErrorMessage.setText(message);
                mErrorMessage.setVisibility(View.VISIBLE);
            }
        });
    }

}
