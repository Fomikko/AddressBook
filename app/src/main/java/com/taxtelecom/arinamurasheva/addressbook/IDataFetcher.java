package com.taxtelecom.arinamurasheva.addressbook;

import java.io.IOException;

import okhttp3.Response;

public interface IDataFetcher {

    Response fetchData(String url) throws IOException;

}
