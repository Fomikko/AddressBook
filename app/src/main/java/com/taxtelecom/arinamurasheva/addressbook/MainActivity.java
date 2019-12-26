package com.taxtelecom.arinamurasheva.addressbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taxtelecom.arinamurasheva.addressbook.utilities.AddressBookJsonUtils;
import com.taxtelecom.arinamurasheva.addressbook.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private AddressBookAdapter mAddressBookAdapter;

    private TextView mErrorMessageDisplay;

    ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_address_book);

        /*Это текстовое поле используется для отображения ошибок и будет спрятано, если ошибок нет.*/
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAddressBookAdapter = new AddressBookAdapter();

        mRecyclerView.setAdapter(mAddressBookAdapter);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        loadContactData();

    }

    private void loadContactData() {
        showContactDataView();

        String[] userData = {"test_user", "test_pass"};
        new FetchContactsTask().execute(userData);
    }

    private void showContactDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    public class FetchContactsTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String[] contactData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (contactData != null) {
                showContactDataView();
                mAddressBookAdapter.setContactData(contactData);
            } else {
                showErrorMessage();
            }
        }

        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String login = params[0];
            String password = params[1];

            URL AddressBookRequestUrl = NetworkUtils.buildUrl(login, password);

            try {
                String contactsSearchResults = NetworkUtils.getResponseFromHttp(AddressBookRequestUrl);
                String[] simpleJsonWeatherData = AddressBookJsonUtils.getContactStringsFromJson(MainActivity.this, contactsSearchResults);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
    }

}
