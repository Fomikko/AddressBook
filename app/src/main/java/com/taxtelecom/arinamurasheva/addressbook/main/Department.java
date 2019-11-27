package com.taxtelecom.arinamurasheva.addressbook.main;

import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {

    private String id;
    private String name;
    private ArrayList<Person> mEmployees;

    public Department(String id, String name, ArrayList<Person> persons) {
        this.id = id;
        this.name = name;
        this.mEmployees = persons;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Person> getEmployees() {
        return mEmployees;
    }

    public void setEmployees(ArrayList<Person> employees) {
        this.mEmployees = employees;
    }
}
