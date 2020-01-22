package com.taxtelecom.arinamurasheva.addressbook.model;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ContactListModel implements IContactListModel {

    private Department rootDepartment;

    private List<IJsonResponseSubscriber> jsonResponseSubscribers = new ArrayList<>();



    @Override
    public Department getRootDepartment() {
        return this.rootDepartment;
    }

    @Override
    public Department getDeptFromJson(String deptJsonString) throws JSONException {

        /*Получнеие корневого объекта (Отдел).*/
        JSONObject deptJsonObject = new JSONObject(deptJsonString);
        Log.d(this.getClass().getSimpleName(), "got department json object");
        return getDepartment(deptJsonObject);

    }

    private static Department getDepartment(JSONObject deptJsonObject) throws JSONException {

        Log.d("ContactListModel", "getting department!!!");

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

    private static Person getEmployee(JSONObject personJsonObject) throws JSONException {

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

        return new Person(personId, personName, personTitle, personEmail, personPhone);

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

    @Override
    public void fetchContactListData(String url) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseJsonString = response.body().string();

                Department dept = null;

                try {
                    Log.d(this.getClass().toString(), "getting department from json...");
                    dept = getDeptFromJson(responseJsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                rootDepartment = dept;

                notifySubscribers();

                Log.d("rootDepartment", rootDepartment.toString());
            }

        });

    }

    @Override
    public void subscribe(IJsonResponseSubscriber subscriber) {
        this.jsonResponseSubscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(IJsonResponseSubscriber subscriber) {
        this.jsonResponseSubscribers.remove(subscriber);
    }

    public void notifySubscribers() {
        for (IJsonResponseSubscriber subscriber : jsonResponseSubscribers) {
            subscriber.update();
        }
    }

}
