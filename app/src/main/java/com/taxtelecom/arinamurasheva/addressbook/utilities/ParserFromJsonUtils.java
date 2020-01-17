package com.taxtelecom.arinamurasheva.addressbook.utilities;

import com.taxtelecom.arinamurasheva.addressbook.model.Department;
import com.taxtelecom.arinamurasheva.addressbook.model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParserFromJsonUtils {

    public static Department parseFromJson(String deptJsonString) throws JSONException {

        /*Получнеие корневого объекта (Отдел).*/
        JSONObject deptJsonObject = new JSONObject(deptJsonString);

        return getDepartment(deptJsonObject);

    }


    private static Department getDepartment(JSONObject deptJsonObject) throws JSONException {

        /*Информация об отделе.*/
        final String CL_DEPT_ID = "ID";
        final String CL_DEPT_NAME = "Name";

        String deptId;
        String deptName;

        deptId = deptJsonObject.getString(CL_DEPT_ID);
        deptName = deptJsonObject.getString(CL_DEPT_NAME);

        Department dept = new Department(deptId, deptName);

        List<Department> innerDepts;
        if ((innerDepts = getDeptsList(deptJsonObject)) != null) {
            dept.setDepartments(innerDepts);
        }

        List<Person> employees;
        if ((employees = getEmplsList(deptJsonObject)) != null) {
            dept.setEmployees(employees);

        }

        return dept;
    }

    private static List<Department> getDeptsList(JSONObject deptJsonObject) throws JSONException {

        final String CL_DEPTS_LIST = "Departments";

        if (!deptJsonObject.has(CL_DEPTS_LIST)) {
            return null;
        }

        JSONArray deptsJsonArray = deptJsonObject.getJSONArray(CL_DEPTS_LIST);

        int numOfDepts = deptsJsonArray.length();
        List<Department> parsedDeptsList = new ArrayList<>(numOfDepts);

        for (int i = 0; i < numOfDepts; i++) {

            JSONObject innerDeptJsonObject = deptsJsonArray.getJSONObject(i);

            Department innerDept = getDepartment(innerDeptJsonObject);
            parsedDeptsList.add(innerDept);

        }

        return parsedDeptsList;
    }

    private static List<Person> getEmplsList(JSONObject deptJsonObject) throws JSONException {

        final String DEPT_EMPLOYEES_LIST = "Employees";

        if (!deptJsonObject.has(DEPT_EMPLOYEES_LIST)) {
            return null;
        }

        JSONArray personsJsonArray = deptJsonObject.getJSONArray(DEPT_EMPLOYEES_LIST);

        int numOfEmpls = personsJsonArray.length();
        List<Person> parsedPersonsList = new ArrayList<>(numOfEmpls);

        for (int i = 0; i < numOfEmpls; i++) {
            JSONObject personJsonObject = personsJsonArray.getJSONObject(i);

            Person person = getEmployee(personJsonObject);

            parsedPersonsList.add(person);
        }

        return parsedPersonsList;
    }

    public static Person getEmployee(JSONObject personJsonObject) throws JSONException {

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

        personId = personJsonObject.getString(CL_CONTACT_ID);
        personName = personJsonObject.getString(CL_CONTACT_NAME);
        personTitle = personJsonObject.getString(CL_CONTACT_TITLE);

        if (personJsonObject.has(CL_CONTACT_EMAIL)) {
            personEmail = personJsonObject.getString(CL_CONTACT_EMAIL);
        }
        if (personJsonObject.has(CL_CONTACT_PHONE)) {
            personPhone = personJsonObject.getString(CL_CONTACT_PHONE);
        }

        Person person = new Person(personId, personName, personTitle, personEmail, personPhone);

        return person;

    }

    public static void printDeptsList(List<Department> deptsList) {
        for (Department d : deptsList) {
            System.out.println("DEPT " + d.getId() + " " + d.getName());

            List<Department> innerDepts;
            if ((innerDepts = d.getDepartments()) != null) {
                System.out.println("Подотделы отдела " + d.getName());
                printDeptsList(innerDepts);
            }
            List<Person> persons;
            if ((persons = d.getEmployees()) != null) {
                System.out.println("Сотрудники отдела " +  d.getName());
                for (Person p : persons) {
                    System.out.println(p.getId() + " " + p.getName());
                }
            }


        }
    }
}
