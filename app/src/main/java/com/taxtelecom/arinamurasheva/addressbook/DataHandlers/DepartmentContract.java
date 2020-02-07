package com.taxtelecom.arinamurasheva.addressbook.DataHandlers;

import android.provider.BaseColumns;

public final class DepartmentContract {
    private DepartmentContract() {

    }

    public static class DepartmentEntry implements BaseColumns {

        public static final String TABLE_NAME = "department";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_NAME = "Name";

    }
}
