package com.taxtelecom.arinamurasheva.addressbook.model;

import org.json.JSONException;

import java.io.IOException;

public interface IContactListModel {

    Department fetchContactListData(String url) throws JSONException;

}
