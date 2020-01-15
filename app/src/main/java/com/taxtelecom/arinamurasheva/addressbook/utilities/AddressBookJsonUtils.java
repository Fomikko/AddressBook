package com.taxtelecom.arinamurasheva.addressbook.utilities;

import android.content.Context;

import com.taxtelecom.arinamurasheva.addressbook.ItemsGroup;
import com.taxtelecom.arinamurasheva.addressbook.model.Department;
import com.taxtelecom.arinamurasheva.addressbook.model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Утилита для работы с данными JSON адресной книги.
 */
public class AddressBookJsonUtils {

    /**
     * Этот метод парсит JSON из веб-ответа и возвращает массив строк,
     * содержащих информацию об отделах и сотрудниках предприятия.
     */

/*    public static List<ItemsGroup<?>> getContactListDataFromJson(String addressBookJsonString) throws JSONException {

        *//*Данные, которые нам нужно получить об отделах.*//*
        List<Object> employees = new ArrayList<>();

        final String CL_EMPLOYEES = "Employees";

        *//*Имя человека.*//*
        final String CONTACT_NAME = "Name";

            JSONArray personsFromDeptArray = departmentJson.getJSONArray(CL_EMPLOYEES);

            String contactName;
            for (int j = 0; j < personsFromDeptArray.length(); j++) {
                JSONObject contactsData = personsFromDeptArray.getJSONObject(j);
                contactName = contactsData.getString(CONTACT_NAME);
                employees.add(contactName);
            }

            ItemsGroup<Object> dept = new ItemsGroup<>();
            dept.setItems(employees);

            parsedContactListData.add(dept);
        }

        return parsedContactListData;
    }*/

    public static List<ItemsGroup> getDeptsFromJson(String addressBookJsonString) throws JSONException {
        String item;

        /*Информация об отделах. Каждый отдел - это элемент массива "Departments".*/
        final String CL_DEPTS = "Departments";

        final String CL_DEPT_NAME = "Name";

        /*Массив строк с информацией о каждом отделе.*/
        JSONObject addressBookJson = new JSONObject(addressBookJsonString);

        JSONArray departmentsArray = addressBookJson.getJSONArray(CL_DEPTS);

        List<ItemsGroup> parsedDeptsList = new ArrayList<>(departmentsArray.length());


        for (int i = 0; i < departmentsArray.length(); i++) {
            JSONObject departmentJson = departmentsArray.getJSONObject(i);

            item = departmentJson.getString(CL_DEPT_NAME);

            ItemsGroup dept = new ItemsGroup(item);
            parsedDeptsList.add(dept);
        }

        return parsedDeptsList;

    }

/*    public static List<Object> getPersonStringsFromJson(JSONObject deptJsonString) throws JSONException {
        *//*Информация о сотрудниках. Каждый сотрудник - это элемент массива "Employees".*//*
        final String CL_EMPL = "Employees";

        *//*Информация о сотрудниках.*//*
        final String CONTACT_NAME = "Name";

        *//*Массив строк с информацией о каждом контакте.*//*
        JSONObject contactJson = new JSONObject(deptJsonString);

        JSONArray personsArray = contactJson.getJSONArray(CL_EMPL);

        List<ItemsGroup<?>> parsedPersonsData = new ArrayList<>(personsArray.length());

        for (int i = 0; i < personsArray.length(); i++) {
            JSONObject departmentsData = personsArray.getJSONObject(i);

            id = departmentsData.getLong(CL_DEPT_ID);
            name = departmentsData.getString(CL_DEPT_NAME);
            employees = getPersonStringsFromJson(id);

            ItemsGroup<Object> persons = new ItemsGroup<>();
            persons.setItems(employees);

            parsedPersonsData.add(persons);
        }

        return parsedPersonsData;

    }*/

    public static List<Object> getEmployeesFromJson(long deptId) {
        /*Информация о сотрудниках. Каждый сотрудник - это элемент массива "Employees".*/
        final String CL_EMPL = "Employees";

        /*Информация о сотрудниках.*/
        final String CONTACT_NAME = "Name";

        final String CONTACT_ID = "ID";

        final String CONTACT_TITLE = "Title";

        final String CONTACT_EMAIL = "Email";

        final String CONTACT_PHONE = "Phone";

        return null;
    }
}
