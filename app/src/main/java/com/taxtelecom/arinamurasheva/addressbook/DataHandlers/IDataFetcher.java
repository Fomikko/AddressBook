package com.taxtelecom.arinamurasheva.addressbook.DataHandlers;

import android.graphics.Bitmap;

import com.taxtelecom.arinamurasheva.addressbook.Model.Department;

public interface IDataFetcher {

    Department getDepartment();
    Bitmap getContactPhoto();
    String getErrorMessage();

}
