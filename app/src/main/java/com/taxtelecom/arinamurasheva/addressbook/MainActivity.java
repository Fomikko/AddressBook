package com.taxtelecom.arinamurasheva.addressbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taxtelecom.arinamurasheva.addressbook.model.Department;
import com.taxtelecom.arinamurasheva.addressbook.utilities.AddressBookJsonUtils;
import com.taxtelecom.arinamurasheva.addressbook.utilities.NetworkUtils;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

        loadDeptsList();

   }

    private void loadDeptsList() {
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

    public class FetchContactsTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(List<String> deptsList) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (deptsList != null) {
                showContactDataView();
                mAddressBookAdapter.setContactListData(deptsList);
            } else {
                showErrorMessage();
            }
        }

        @Override
        protected List<String> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String login = params[0];
            String password = params[1];

            URL AddressBookRequestUrl = NetworkUtils.buildUrl(login, password);

            try {
                String contactsSearchResults = NetworkUtils.getResponseFromHttp(AddressBookRequestUrl);
                List<ItemsGroup> simpleJsonDepartmentsList = AddressBookJsonUtils.getDeptsFromJson(contactsSearchResults);

                List<String> contactList = new ArrayList<>();
                for (ItemsGroup item : simpleJsonDepartmentsList) {
                    contactList.add(item.getName());
                }

                return contactList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
