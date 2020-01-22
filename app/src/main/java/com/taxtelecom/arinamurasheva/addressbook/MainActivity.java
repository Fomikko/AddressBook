package com.taxtelecom.arinamurasheva.addressbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taxtelecom.arinamurasheva.addressbook.Presenter.IMainPresenter;
import com.taxtelecom.arinamurasheva.addressbook.Presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements IMainView {

    IMainPresenter presenter;

    private RecyclerView mRecyclerView;

    private AddressBookAdapter mAddressBookAdapter;

    private TextView mErrorMessageDisplay;

    ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (presenter == null) {
            presenter = new MainPresenter(this);
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

        mAddressBookAdapter = new AddressBookAdapter();

        mRecyclerView.setAdapter(mAddressBookAdapter);

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
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAddressBookAdapter.setContactListData(item);

            }
        });

    }




}
