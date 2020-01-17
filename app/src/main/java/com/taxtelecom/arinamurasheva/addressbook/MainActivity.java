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
import com.taxtelecom.arinamurasheva.addressbook.utilities.NetworkUtils;
import com.taxtelecom.arinamurasheva.addressbook.utilities.ParserFromJsonUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.taxtelecom.arinamurasheva.addressbook.utilities.ParserFromJsonUtils.parseFromJson;
import static com.taxtelecom.arinamurasheva.addressbook.utilities.ParserFromJsonUtils.printDeptsList;

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

        mLoadingIndicator.setVisibility(View.VISIBLE);

        String login = "test_user";
        String password = "test_pass";

        String addressBookRequestUrl = NetworkUtils.buildUrl(login, password);

        try {
            run(addressBookRequestUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

   }

   void run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

           Request request = new Request.Builder()
                   .url(url)
                   .build();

           client.newCall(request).enqueue(new Callback() {

               @Override
               public void onFailure(@NotNull Call call, @NotNull IOException e) {

                   /*
                    * TODO Исправить ошибку. Отключить интернет на телефоне, открыть приложение.
                    * Only the original thread that created a view hierarchy can touch its views.
                    */

                   showErrorMessage();

                   call.cancel();
               }

               @Override
               public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                   final String responseJsonString = response.body().string();

                   MainActivity.this.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {

                           mLoadingIndicator.setVisibility(View.INVISIBLE);
                           setContactListLayout(responseJsonString);

                       }
                   });
               }
           });
   }

    private void setContactListLayout(String jsonString) {

        List <Department> deptsList = null;
        try {
            deptsList = parseFromJson(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (deptsList != null) {
            //printDeptsList(deptsList);
            showContactDataView();
            mAddressBookAdapter.setContactListData(AddressBookAdapter.deptsToItemsList(deptsList));
        } else {
            showErrorMessage();
        }
    }

    private void showContactDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

}
