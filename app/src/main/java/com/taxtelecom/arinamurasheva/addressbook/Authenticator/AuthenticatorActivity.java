package com.taxtelecom.arinamurasheva.addressbook.Authenticator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.taxtelecom.arinamurasheva.addressbook.ContactList.View.ContactListActivity;
import com.taxtelecom.arinamurasheva.addressbook.R;

public class AuthenticatorActivity extends Activity {

    AuthenticatorPresenter presenter;

    private EditText mLoginEditText;
    private EditText mPasswordEditText;
    private TextView mErrorMessage;

    SharedPreferences mUserData;

    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authenticator_activity);

        if (presenter == null) {
            presenter = new AuthenticatorPresenter(this);
        }


        mUserData = this.getSharedPreferences(
                getString(R.string.auth_preference_file),
                Context.MODE_PRIVATE);

        checkUserDataPresence();

        mLoginEditText = findViewById(R.id.ev_user_login);
        mPasswordEditText = findViewById(R.id.ev_user_password);

        mErrorMessage = findViewById(R.id.tv_auth_error_message);

    }

    public void Login(View view) {
        String userLogin = mLoginEditText.getText().toString();
        String userPassword = mPasswordEditText.getText().toString();

        requestCredentialsConfirmation(userLogin, userPassword);
        saveUserData(userLogin, userPassword);
    }

    public void requestCredentialsConfirmation(String userLogin, String userPassword) {
        presenter.onGetConfirmCredentialsRequest(userLogin, userPassword);
    }

    public void goToContactListView() {
        Intent intent = new Intent(
                AuthenticatorActivity.this,
                ContactListActivity.class);

        startActivity(intent);
    }

    public void checkUserDataPresence() {
        String userLogin = mUserData.getString(USER_LOGIN, "");
        String userPassword = mUserData.getString(USER_PASSWORD, "");

        if(!userLogin.equals("") && !userPassword.equals("")) {
            requestCredentialsConfirmation(userLogin, userPassword);
        }
    }

    public void saveUserData(String userLogin, String userPassword) {


        SharedPreferences.Editor editor = mUserData.edit();
        editor.putString(USER_LOGIN, userLogin);
        editor.putString(USER_PASSWORD, userPassword);
        editor.apply();

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
