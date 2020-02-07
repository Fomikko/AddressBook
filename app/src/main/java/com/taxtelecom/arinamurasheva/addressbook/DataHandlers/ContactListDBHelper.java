package com.taxtelecom.arinamurasheva.addressbook.DataHandlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.taxtelecom.arinamurasheva.addressbook.DataHandlers.DepartmentContract.*;

public class ContactListDBHelper extends SQLiteOpenHelper {

    public static ContactListDBHelper instance;

    public static final String DATABASE_NAME = "ContactList.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DepartmentEntry.TABLE_NAME + " (" +
                    DepartmentEntry._ID + " INTEGER PRIMARY KEY, " +
                    DepartmentEntry.COLUMN_ID + " TEXT, " +
                    DepartmentEntry.COLUMN_NAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DepartmentEntry.TABLE_NAME;


    public static synchronized void init(Context context) {
        if (instance == null) {
            instance = new ContactListDBHelper(context);
        }
    }

    public static ContactListDBHelper getInstance() {
        return instance;
    }

    private ContactListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
