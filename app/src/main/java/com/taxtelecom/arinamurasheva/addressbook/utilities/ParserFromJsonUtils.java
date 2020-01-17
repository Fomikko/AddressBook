package com.taxtelecom.arinamurasheva.addressbook.utilities;

import android.util.Log;

import com.taxtelecom.arinamurasheva.addressbook.model.Department;
import com.taxtelecom.arinamurasheva.addressbook.model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParserFromJsonUtils {

    public static List<Department> parseFromJson(String deptJsonString) throws JSONException {

        JSONObject deptJsonObject = new JSONObject(deptJsonString);

        return getDeptsList(deptJsonObject);

    }

    private static List<Department> getDeptsList(JSONObject mainDeptJsonObject) throws JSONException {

        final String CL_DEPTS_LIST = "Departments";

        if (!mainDeptJsonObject.has(CL_DEPTS_LIST)) {
            return null;
        }

        JSONArray deptsJsonArray = mainDeptJsonObject.getJSONArray(CL_DEPTS_LIST);

        final String CL_DEPT_ID = "ID";
        final String CL_DEPT_NAME = "Name";

        String deptId;
        String deptName;

        int numOfDepts = deptsJsonArray.length();
        List<Department> parsedDeptsList = new ArrayList<>(numOfDepts);

        for (int i = 0; i < numOfDepts; i++) {
            JSONObject deptJsonObject = deptsJsonArray.getJSONObject(i);

            deptId = deptJsonObject.getString(CL_DEPT_ID);
            deptName = deptJsonObject.getString(CL_DEPT_NAME);

            Department dept = new Department(deptId, deptName);

            List<Person> personsList;
            List<Department> innerDepts;

            if ((personsList = getPersonsList(deptJsonObject)) != null) {
                dept.setEmployees(personsList);

            }

            /*Рекурсия.*/
            if ((innerDepts = getDeptsList(deptJsonObject)) != null) {
                dept.setDepartments(innerDepts);
            }

            parsedDeptsList.add(dept);

        }


        return parsedDeptsList;
    }

    private static List<Person> getPersonsList(JSONObject deptJsonObject) throws JSONException {

        final String DEPT_EMPLOYEES_LIST = "Employees";

        if (!deptJsonObject.has(DEPT_EMPLOYEES_LIST)) {
            return null;
        }

        JSONArray personsJsonArray = deptJsonObject.getJSONArray(DEPT_EMPLOYEES_LIST);

        /*Информация о сотрудниках.*/
        final String CL_CONTACT_ID = "ID";
        final String CL_CONTACT_NAME = "Name";
        final String CL_CONTACT_TITLE = "Title";
        final String CL_CONTACT_EMAIL = "Email";
        final String CL_CONTACT_PHONE = "Phone";

        String personId;
        String personName;
        String personTitle;
        String personEmail = null;
        String personPhone = null;

        int numOfEmpls = personsJsonArray.length();
        List<Person> parsedPersonsList = new ArrayList<>(numOfEmpls);

        for (int i = 0; i < numOfEmpls; i++) {
            JSONObject personJsonObject = personsJsonArray.getJSONObject(i);

            personId = personJsonObject.getString(CL_CONTACT_ID);
            personName = personJsonObject.getString(CL_CONTACT_NAME);
            personTitle = personJsonObject.getString(CL_CONTACT_TITLE);
            if (personJsonObject.has(CL_CONTACT_EMAIL)) {
                personEmail = personJsonObject.getString(CL_CONTACT_EMAIL);

            }
            if (personJsonObject.has(CL_CONTACT_PHONE)) {
                personPhone = personJsonObject.getString(CL_CONTACT_PHONE);

            }

            parsedPersonsList.add(new Person(personId, personName, personTitle, personEmail, personPhone));
        }

        return parsedPersonsList;
    }

    public static void printDeptsList(List<Department> deptsList) {
        for (Department d : deptsList) {
            System.out.println("DEPT " + d.getId() + " " + d.getName());

            List<Person> persons;
            if ((persons = d.getEmployees()) != null) {
                System.out.println("Сотрудники отдела " +  d.getName());
                for (Person p : persons) {
                    System.out.println(p.getId() + " " + p.getName());
                }
            }

            List<Department> innerDepts;
            if ((innerDepts = d.getDepartments()) != null) {
                System.out.println("Подотделы отдела " + d.getName());
                printDeptsList(innerDepts);
            }
        }
    }
}
