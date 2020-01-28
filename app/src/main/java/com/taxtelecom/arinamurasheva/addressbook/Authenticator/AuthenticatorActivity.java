package com.taxtelecom.arinamurasheva.addressbook.Authenticator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.taxtelecom.arinamurasheva.addressbook.ContactList.View.ContactListActivity;
import com.taxtelecom.arinamurasheva.addressbook.R;

public class AuthenticatorActivity extends AppCompatActivity {

    AuthenticatorPresenter presenter;

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

    public void goToContactListView() {
        Intent intent = new Intent(
                AuthenticatorActivity.this,
                ContactListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void checkUserDataPresence() {
        presenter.onGetCheckUserDataPresence();

    }

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
