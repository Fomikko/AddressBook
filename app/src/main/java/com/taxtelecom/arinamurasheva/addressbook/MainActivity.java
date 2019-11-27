package com.taxtelecom.arinamurasheva.addressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taxtelecom.arinamurasheva.addressbook.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mContactListTextView;

    private TextView mErrorMessageDisplay;

    ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContactListTextView = findViewById(R.id.contact_list_display);

        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        makeContactListQuery();
    }

    private void makeContactListQuery() {
        URL contactListSearchUrl = NetworkUtils.buildUrl("test_user", "test_pass");
        new TaxtelecomQueryTask().execute(contactListSearchUrl);
    }

    private void showJsonDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mContactListTextView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mContactListTextView.setVisibility(View.INVISIBLE);
    }

    public class TaxtelecomQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String contactsSearchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (contactsSearchResults != null && !contactsSearchResults.equals("")) {
                mContactListTextView.setText(contactsSearchResults);
                showJsonDataView();
            } else {
                showErrorMessage();
            }
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String contactsSearchResults = null;
            try {
                contactsSearchResults = NetworkUtils.getResponseFromHttp(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return contactsSearchResults;
        }
    }

}
