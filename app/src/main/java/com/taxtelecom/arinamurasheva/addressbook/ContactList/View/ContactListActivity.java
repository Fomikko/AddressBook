package com.taxtelecom.arinamurasheva.addressbook.ContactList.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.View.AuthenticatorActivity;
import com.taxtelecom.arinamurasheva.addressbook.Contact.View.ContactActivity;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.Presenter.IContactListPresenter;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.Presenter.ContactListPresenter;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;
import com.taxtelecom.arinamurasheva.addressbook.R;

import java.util.List;

public class ContactListActivity extends AppCompatActivity implements IContactListView {

    IContactListPresenter presenter;

    private RecyclerView mRecyclerView;

    private ContactListAdapter mContactListAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list_activity);

        if (presenter == null) {
            presenter = new ContactListPresenter(this);
        }

        mRecyclerView = findViewById(R.id.recyclerview_address_book);

        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mContactListAdapter = new ContactListAdapter();

        mContactListAdapter.setUserInfoListener(this);

        mRecyclerView.setAdapter(mContactListAdapter);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        requestDataLoad();


   }



   /*
    * Методы для отображения и скрытия элементов Activity.
    */

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
        showErrorMessage(getString(R.string.error_message));
    }

    public void showErrorMessage(final String errorMessage) {
        ContactListActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mErrorMessageDisplay.setText(errorMessage);
                mLoadingIndicator.setVisibility(View.INVISIBLE);
                mErrorMessageDisplay.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    /*
     * Листенеры.
     */

    @Override
    public void requestDataLoad() {
        presenter.onGetDataLoadRequest();
    }

    @Override
    public void requestLogOut() {
        presenter.onGetLogOutRequest();
    }


    @Override
    public void requestContactInfo(List<Integer> routingList) {
        presenter.onGetContactInfoRequest(routingList);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == R.id.action_logout) {
            requestLogOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
     * Манипуляция данными.
     */

    @Override
    public void setContactListLayout(final Item item) {
        ContactListActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mContactListAdapter.setContactListData(item);
            }
        });
    }


    /*
     * Переходы на другие Activity.
     */

    @Override
    public void goToLoginView() {
        Intent mLoginActivity = new Intent(
                ContactListActivity.this,
                AuthenticatorActivity.class);

        mLoginActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mLoginActivity);
    }

    @Override
    public void goToContactView(Person person) {
        Intent mContactActivity = new Intent(
                ContactListActivity.this,
                ContactActivity.class);
        mContactActivity.putExtra("person", person);
        startActivity(mContactActivity);
    }

}
