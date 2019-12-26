package com.taxtelecom.arinamurasheva.addressbook.utilities;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Утилита для работы с данными JSON адресной книги.
 */
public class AddressBookJsonUtils {

    /**
     * Этот метод парсит JSON из веб-ответа и возвращает массив строк,
     * содержащих информацию об отделах и сотрудниках предприятия.
     */

    public static String[] getContactStringsFromJson(Context context, String contactJsonString) throws JSONException {

        /*Информация об отделах. Каждый отдел - это элемент массива "Departments".*/
        final String CL_DEPTS = "Departments";

        /*Информация о сотрудниках. Каждый сотрудник - это элемент массива "Employees".*/
        final String CL_EMPL = "Employees";

        /*Информация о сотрудниках.*/
        //final String CL_ID = "ID";

        final String CL_DEPT_NAME = "Name";

        //final String CL_TITLE = "Title";

        //final String CL_EMAIL = "Email";

        //final String CL_PHONE = "Phone";

        String[] parsedContactData = null;

        /*Массив строк с информацией о каждом контакте.*/
        JSONObject addressBookJson = new JSONObject(contactJsonString);

        JSONArray departmentsArray = addressBookJson.getJSONArray(CL_DEPTS);

        parsedContactData = new String[departmentsArray.length()];

        for (int i = 0; i < departmentsArray.length(); i++) {
            /*Это данные, которые мы хотим получить.*/
            long id;
            String name;
            String title;
            String email;
            String phone;

            JSONObject departmentsData = departmentsArray.getJSONObject(i);
            parsedContactData[i] = departmentsData.getString(CL_DEPT_NAME);
            Log.d("Отдел", parsedContactData[i]);
        }

        return parsedContactData;
    }
}
