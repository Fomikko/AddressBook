package com.taxtelecom.arinamurasheva.addressbook.DataHandlers;

import android.graphics.Bitmap;

import com.taxtelecom.arinamurasheva.addressbook.Model.Department;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;

public class SQLLiteDataHandler implements IDataFetcher {

    @Override
    public Department getDepartment() {
        return null;
    }

    @Override
    public Person getPerson() {
        return null;
    }

    @Override
    public Bitmap getContactPhoto() {
        return null;
    }

    @Override
    public boolean confirmCredentials() {
        return false;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }
}
