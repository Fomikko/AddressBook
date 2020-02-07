package com.taxtelecom.arinamurasheva.addressbook.DataHandlers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.BaseColumns;

import com.taxtelecom.arinamurasheva.addressbook.DataHandlers.DepartmentContract.DepartmentEntry;
import com.taxtelecom.arinamurasheva.addressbook.Model.Department;

public class SQLiteDataHandler implements IDataFetcher, IDataSaver {

    private String errorMessage = "Нет сохраненных данных на устройстве. Загружаю по сети...";

    private ContactListDBHelper dbHelper;

    public SQLiteDataHandler() {

        dbHelper = ContactListDBHelper.getInstance();

    }

    @Override
    public Department getDepartment() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                DepartmentEntry.COLUMN_ID,
                DepartmentEntry.COLUMN_NAME
        };

        String selection = DepartmentEntry.COLUMN_NAME + " = ?";
        String[] selectionArgs = { "Все" };

        Cursor cursor = db.query(
                DepartmentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.getCount() == 0) {
            return null;
        }

        String itemId = null;
        String itemName = null;

        while(cursor.moveToNext()) {

            itemId = cursor.getString(
                    cursor.getColumnIndexOrThrow(DepartmentEntry.COLUMN_ID));
            itemName = cursor.getString(
                    cursor.getColumnIndexOrThrow(DepartmentEntry.COLUMN_NAME));

            }

        cursor.close();

        return new Department(itemId, itemName);

    }

    @Override
    public Bitmap getContactPhoto() {
        return null;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public void postDepartment(Department dept) {

        String deptId = dept.getId();
        String deptName = dept.getName();
        //String innerDepts = "заглушка";

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DepartmentEntry.COLUMN_ID, deptId);
        values.put(DepartmentEntry.COLUMN_NAME, deptName);

        System.out.println("DEPT " + deptId + " " + deptName);
        //values.put(DepartmentEntry.COLUMN_DEPARTMENTS, innerDepts);

        long newRowId = db.insert(DepartmentEntry.TABLE_NAME, null, values);
    }
}
