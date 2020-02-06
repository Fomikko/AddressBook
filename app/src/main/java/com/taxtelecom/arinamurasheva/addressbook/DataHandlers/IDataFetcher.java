package com.taxtelecom.arinamurasheva.addressbook.DataHandlers;

import android.graphics.Bitmap;

import com.taxtelecom.arinamurasheva.addressbook.Model.Department;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;

public interface IDataFetcher {

    Department getDepartment();
    Person getPerson();
    Bitmap getContactPhoto();
    boolean confirmCredentials();
    String getErrorMessage();

}
