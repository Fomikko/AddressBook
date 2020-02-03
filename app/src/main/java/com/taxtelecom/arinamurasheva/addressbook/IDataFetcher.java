package com.taxtelecom.arinamurasheva.addressbook;

import java.io.IOException;

public interface IDataFetcher {

    String fetchData(String url) throws IOException;

}
