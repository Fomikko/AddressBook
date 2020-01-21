package com.taxtelecom.arinamurasheva.addressbook.Presenter;

import android.view.View;

import com.taxtelecom.arinamurasheva.addressbook.IMainView;
import com.taxtelecom.arinamurasheva.addressbook.MainActivity;
import com.taxtelecom.arinamurasheva.addressbook.model.Department;
import com.taxtelecom.arinamurasheva.addressbook.model.IContactListModel;
import com.taxtelecom.arinamurasheva.addressbook.model.ContactListModel;
import com.taxtelecom.arinamurasheva.addressbook.utilities.NetworkUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainPresenter implements IMainPresenter {

    private IMainView _view;
    private IContactListModel _model;


    public MainPresenter (IMainView view) {
        _view = view;
        _model = new ContactListModel();

    }

    @Override
    public void onGetDataLoadRequest() {
        String login = "test_user";
        String password = "test_pass";

        String contactListRequestUrl = NetworkUtils.buildUrl(login, password);

        fetchContactListData(contactListRequestUrl);

    }

    @Override
    public void fetchContactListData(String url) {


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
            }



            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseJsonString = response.body().string();

                Department dept = null;

                try {
                    dept = _model.fetchContactListData(responseJsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (dept != null) {
                    _view.showContactDataView();
                    _view.setContactListLayout(dept);

                } else {
                    _view.showErrorMessage();
                }
            }

        });
    }


}
