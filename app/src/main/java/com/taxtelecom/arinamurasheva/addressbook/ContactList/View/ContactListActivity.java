package com.taxtelecom.arinamurasheva.addressbook.ContactList.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.AuthenticatorActivity;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.Presenter.IContactListPresenter;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.Presenter.ContactListPresenter;
import com.taxtelecom.arinamurasheva.addressbook.R;

public class ContactListActivity extends AppCompatActivity implements IContactListView {

    IContactListPresenter presenter;

    private RecyclerView mRecyclerView;

    private ContactListAdapter mContactListAdapter;

    private TextView mErrorMessageDisplay;

    ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list_activity);


        if (presenter == null) {
            presenter = new ContactListPresenter(this);
        }

        mRecyclerView = findViewById(R.id.recyclerview_address_book);

        /*
        Это текстовое поле используется для отображения ошибок и
        будет спрятано, если ошибок нет.
        */
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mContactListAdapter = new ContactListAdapter();

        mRecyclerView.setAdapter(mContactListAdapter);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mLoadingIndicator.setVisibility(View.VISIBLE);

        requestDataLoad();

   }

    @Override
    public void requestDataLoad() {
        presenter.onGetDataLoadRequest();
    }

    @Override
    public void showContactDataView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }


    @Override
    public void setContactListLayout(final Item item) {
        ContactListActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mContactListAdapter.setContactListData(item);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == R.id.action_logout) {
            logOutRequest();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void logOutRequest() {
        presenter.onGetLogOutRequest();
    }

    @Override
    public void goToLoginView() {
        Intent mStartActivity = new Intent(
                ContactListActivity.this,
                AuthenticatorActivity.class);

        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mStartActivity);
    }
}
